package com.cooksys.group_project_1_team_1.services.impl;

import com.cooksys.group_project_1_team_1.entities.Credentials;
import com.cooksys.group_project_1_team_1.entities.Hashtag;
import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.entities.User;
import com.cooksys.group_project_1_team_1.exceptions.BadRequestException;
import com.cooksys.group_project_1_team_1.exceptions.NotAuthorizedException;
import com.cooksys.group_project_1_team_1.exceptions.NotFoundException;
import com.cooksys.group_project_1_team_1.mappers.CredentialsMapper;
import com.cooksys.group_project_1_team_1.mappers.HashtagMapper;
import com.cooksys.group_project_1_team_1.mappers.TweetMapper;
import com.cooksys.group_project_1_team_1.mappers.UserMapper;
import com.cooksys.group_project_1_team_1.models.HashtagDto;
import com.cooksys.group_project_1_team_1.models.TweetRequestDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.models.UserResponseDto;
import com.cooksys.group_project_1_team_1.repositories.HashtagRepository;
import com.cooksys.group_project_1_team_1.repositories.TweetRepository;
import com.cooksys.group_project_1_team_1.repositories.UserRepository;
import com.cooksys.group_project_1_team_1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final HashtagMapper hashtagMapper;
    private final HashtagRepository hashtagRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CredentialsMapper credentialsMapper;

    private User verifyCredentials(Credentials credentials) {
        if (credentials.getUsername() == null || credentials.getPassword() == null) {
            throw new NotAuthorizedException("Must provide username and password");
        }

        User user = userRepository.findByCredentialsUsername(credentials.getUsername());
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Credentials userCredentials = user.getCredentials();
        if (!credentials.getPassword().equals(userCredentials.getPassword())) {
            throw new NotAuthorizedException("Incorrect password provided");
        }
        return user;
    }

    private List<Hashtag> getHashtagsFromString(String string, Tweet tweet) {
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(string);
        List<String> matches = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group(1));
        }

        for (String match : matches) {
            // Get hashtag from DB
            Hashtag hashtag = hashtagRepository.findByLabelIgnoreCase(match);
            long now = System.currentTimeMillis();

            // If hashtag doesn't exist in DB, create and add
            if (hashtag == null) {
                Hashtag hashtagToSave = new Hashtag();
                List<Tweet> tweets = new ArrayList<>();
                tweets.add(tweet);

                hashtagToSave.setLabel(match);
                hashtagToSave.setFirstUsed(new Timestamp(now));
                hashtagToSave.setLastUsed(new Timestamp(now));
                hashtagToSave.setTweets(tweets);

                hashtags.add(hashtagRepository.saveAndFlush(hashtagToSave));
            } else {
                List<Tweet> tweets = hashtag.getTweets();
                tweets.add(tweet);
                hashtag.setTweets(tweets);
                hashtag.setLastUsed(new Timestamp(now));

                hashtags.add(hashtagRepository.saveAndFlush(hashtag));
            }
        }

        return hashtags;
    }

    // Searches a given string (using regex) and returns a list of any @mentions
    // (Users) found
    private List<User> getMentionsFromString(String string, Tweet tweet) {
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(string);
        List<String> matches = new ArrayList<>();
        List<User> users = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group(1));
        }

        for (String match : matches) {
            User user = userRepository.findByCredentialsUsername(match);
            if (user == null) {
                continue;
            }

            List<Tweet> tweetsMentionedIn = user.getTweetMentions();
            tweetsMentionedIn.add(tweet);
            user.setTweetMentions(tweetsMentionedIn);
            users.add(userRepository.saveAndFlush(user));
        }

        return users;
    }

    @Override
    public List<TweetResponseDto> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findAllByDeletedFalse();
        tweets.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return tweetMapper.entitiesToResponseDtos(tweets);
    }

    @Override
    public TweetResponseDto getTweet(Long id) {
        Optional<Tweet> optionalTweet= tweetRepository.findByIdAndDeletedFalse(id);
        if (optionalTweet.isEmpty()){
            throw new NotFoundException("Tweet with id "+id+" can not be found");
        }
        Tweet tweet=optionalTweet.get();
        TweetResponseDto tweetDTO = tweetMapper.entityToResponseDto(tweet);
        tweetDTO.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
        return tweetDTO;

    }

    @Override
    public Tweet getTweetFromDb(Long id) {
        Optional<Tweet> potentialTweet=tweetRepository.findById(id);
        if (potentialTweet.isEmpty()){
            throw new NotFoundException("Tweet with id "+id+" can not be found");
        }
        return potentialTweet.get();
    }

    @Override
    public List<HashtagDto> getTweetHashtags(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(optionalTweet.isEmpty())
            throw new NotFoundException("Unable To Find Tweet With ID " + id);
        Tweet tweet = optionalTweet.get();
        List<Hashtag> hashtags = tweet.getHashtags();
        for (Hashtag hash : hashtags) {
            hash.setLabel(hash.getLabel().replace("#", ""));
        }
        return hashtagMapper.entitiesToDtos(hashtags);
    }

    @Override
    public List<UserResponseDto> getTweetLikes(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(optionalTweet.isEmpty())
            throw new NotFoundException("Unable To Find Tweet With ID " + id);
        Tweet tweet = optionalTweet.get();
        List<User> users = tweet.getLikes();
        List<UserResponseDto> replyDTOs = new ArrayList<>();
        for(User likes : users)
            if(!likes.isDeleted()) {
                String userName = likes.getCredentials().getUsername();
                UserResponseDto tempLikesDTO = userMapper.entityToResponseDto(likes);
                tempLikesDTO.setUsername(userName);
                replyDTOs.add(tempLikesDTO);
            }
        return replyDTOs;
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        Credentials credentials = credentialsMapper.requestDtoToEntity(tweetRequestDto.getCredentials());
        if (credentials == null) {
            throw new BadRequestException("Must provide credentials");
        }

        String content = tweetRequestDto.getContent();

        if (content == null || content.length() == 0) {
            throw new BadRequestException("Unable to create tweet without content");
        }

        User user = verifyCredentials(credentials);

        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthor(user);
        tweet.setContent(content);
        tweet.setPosted(new Timestamp(System.currentTimeMillis()));
        tweet.setHashtags(getHashtagsFromString(content, tweet));
        tweet.setMentions(getMentionsFromString(content, tweet));
        return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(tweet));
    }


}
