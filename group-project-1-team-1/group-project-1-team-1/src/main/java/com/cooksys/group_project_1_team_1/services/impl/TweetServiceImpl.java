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
import com.cooksys.group_project_1_team_1.models.*;
import com.cooksys.group_project_1_team_1.repositories.HashtagRepository;
import com.cooksys.group_project_1_team_1.repositories.TweetRepository;
import com.cooksys.group_project_1_team_1.repositories.UserRepository;
import com.cooksys.group_project_1_team_1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
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
        List<Hashtag> hashtags = new ArrayList<>();
        long now = System.currentTimeMillis();

        while (matcher.find()) {
            String label = matcher.group(1);
            Hashtag hashtag = hashtagRepository.findByLabelIgnoreCase(label);

            if (hashtag == null) {
                hashtag = new Hashtag();
                hashtag.setLabel(label);
                hashtag.setFirstUsed(new Timestamp(now));
                hashtag.setLastUsed(new Timestamp(now));
                hashtag.setTweets(new ArrayList<>());
            }

            hashtag.getTweets().add(tweet);
            hashtag.setLastUsed(new Timestamp(now));
            hashtags.add(hashtagRepository.saveAndFlush(hashtag));
        }

        return hashtags;
    }


    private List<User> getMentionsFromString(String string, Tweet tweet) {
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(string);
        List<User> users = new ArrayList<>();

        while (matcher.find()) {
            String username = matcher.group(1);
            User user = userRepository.findByCredentialsUsername(username);
            if (user != null) {
                user.getTweetMentions().add(tweet);
                users.add(userRepository.saveAndFlush(user));
            }
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
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new NotFoundException("No tweet exists with that ID.");
        }
        return tweetMapper.entityToResponseDto(tweet.get());

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

    @Override
    public List<TweetResponseDto> getTweetReposts(Long id) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if (tweetOptional.isEmpty()) {
            throw new NotFoundException("No tweet exists with provided ID!");
        }
        // Get entity
        Tweet tweet = tweetOptional.get();
        List<Tweet> reposts = tweet.getReposts().stream()
                .filter(repost -> !repost.isDeleted()) // filter non deleted reposts
                .sorted(Comparator.comparing(Tweet::getPosted).reversed())  // sorting
                .collect(Collectors.toList());

        // Convert list to Tweet entities
        return tweetMapper.entitiesToResponseDtos(reposts);
    }

    @Override
    public ContextDto getTweetContext(Long id) {
        Optional<Tweet> tweetOptional = tweetRepository.findByIdAndDeletedFalse(id);
        if (tweetOptional.isEmpty()) {
            throw new NotFoundException("No tweet exists with provided ID!");
        }
        Tweet tweet = tweetOptional.get();
        // Before context
        List<TweetResponseDto> before = getBeforeContext(tweet);
        // After context
        List<TweetResponseDto> after = getAfterContext(tweet);

        ContextDto contextDto = new ContextDto();
        contextDto.setTarget(tweetMapper.entityToResponseDto(tweet));
        contextDto.setBefore(before);
        contextDto.setAfter(after);
        return contextDto;
    }

    @Override
    public Object getTweetMentions(Long id) {
        Optional<Tweet> tweetOptional = tweetRepository.findByIdAndDeletedFalse(id);
        if (tweetOptional.isEmpty()) {
            throw new NotFoundException("No tweet exists with provided ID!");
        }
        Tweet tweet = tweetOptional.get();

        // Filter out user that are not deleted
        List<User> mentionedUsers = tweet.getMentions().stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());

        return userMapper.entitiesToResponseDtos(mentionedUsers);
    }

    private List<TweetResponseDto> getBeforeContext(Tweet tweet) {
        List<Tweet> beforeTweets = new ArrayList<>();
        Tweet current = tweet.getInReplyTo();
        while (current != null && !current.isDeleted()) {
            beforeTweets.add(current);
            current = current.getInReplyTo();
        }
        Collections.reverse(beforeTweets);
        return tweetMapper.entitiesToResponseDtos(beforeTweets);
    }

    private List<TweetResponseDto> getAfterContext(Tweet tweet) {
        List<Tweet> afterTweets = new ArrayList<>();
        Queue<Tweet> queue = new LinkedList<>(tweet.getReplies());
        while (!queue.isEmpty()) {
            Tweet current = queue.poll();
            if (current != null && !current.isDeleted()) {
                afterTweets.add(current);
                queue.addAll(current.getReplies());
            }
        }
        return tweetMapper.entitiesToResponseDtos(afterTweets);
    }
}
