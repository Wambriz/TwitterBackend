package com.cooksys.group_project_1_team_1.repositories;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
