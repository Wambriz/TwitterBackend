package com.cooksys.group_project_1_team_1.repositories;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findAllByAuthorIdAndDeletedFalse(Long authorId);

    List<Tweet> findAllByDeletedFalse();

    Optional<Tweet> findByIdAndDeletedFalse(Long id);
}
