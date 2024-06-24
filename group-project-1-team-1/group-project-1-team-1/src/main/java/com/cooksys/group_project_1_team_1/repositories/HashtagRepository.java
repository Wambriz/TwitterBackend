package com.cooksys.group_project_1_team_1.repositories;

import com.cooksys.group_project_1_team_1.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag,Long> {

    Hashtag findByLabelIgnoreCase(String label);
}
