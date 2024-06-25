package com.cooksys.group_project_1_team_1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_table_id",nullable = false)
    private User author;

    @Column(nullable = false)
    private boolean deleted;

    private String content;

    @ManyToOne
    @JoinColumn(name = "reply_to_fk_id")
    private Tweet inReplyTo;

    @OneToMany(mappedBy = "inReplyTo")
    private List<Tweet> replies=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "repost_of_fk_id")
    private Tweet repostOf;

    @OneToMany(mappedBy = "repostOf")
    private List<Tweet> reposts=new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tweet_hashtags",joinColumns = @JoinColumn(name = "tweet_id"),inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags=new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_likes",joinColumns = @JoinColumn(name = "tweet_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes=new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_mentions",joinColumns = @JoinColumn(name = "tweet_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> mentions=new ArrayList<>();
}
