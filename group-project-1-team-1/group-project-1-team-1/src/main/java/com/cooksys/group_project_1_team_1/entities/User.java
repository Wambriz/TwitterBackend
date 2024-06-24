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
    List<User> followers;

    @ManyToMany(mappedBy = "followers")
    List<User> following;

    @ManyToMany(mappedBy = "likes")
    List<Tweet> tweetLikes;

    @ManyToMany(mappedBy = "mentions")
    List<Tweet> tweetMentions;

    @OneToMany(mappedBy = "author")
    List<Tweet> tweets;
}