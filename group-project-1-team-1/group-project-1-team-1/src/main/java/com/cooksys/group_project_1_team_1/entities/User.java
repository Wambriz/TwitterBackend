package com.cooksys.group_project_1_team_1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "user_table")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp joined;

    private boolean deleted=false;

    @Embedded
    private Profile profile;

    @Embedded
    private Credentials credentials;

    @ManyToMany
    @JoinTable(name = "followers_following",joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

    @ManyToMany
    @JoinTable(name = "user_likes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tweet_id"))
    private List<Tweet> tweetLikes;

    @ManyToMany(mappedBy = "mentions")
    private List<Tweet> tweetMentions;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    public void addLikedTweet(Tweet tweet) {
        if (!this.tweetLikes.contains(tweet)) {
            this.tweetLikes.add(tweet);
            tweet.getLikes().add(this);
        }
    }


}
