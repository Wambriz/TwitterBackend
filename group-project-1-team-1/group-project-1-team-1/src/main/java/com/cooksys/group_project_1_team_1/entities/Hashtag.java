package com.cooksys.group_project_1_team_1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String label;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp firstUsed;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp lastUsed;

    @ManyToMany(mappedBy = "hashtags")
    List<Tweet> tweets=new ArrayList<>();
}
