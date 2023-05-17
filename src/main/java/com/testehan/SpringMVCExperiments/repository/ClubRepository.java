package com.testehan.SpringMVCExperiments.repository;

import com.testehan.SpringMVCExperiments.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    // java automatically generates an implementation and a SQL query for finding a Club based on the "title" field from
    // the Club class
    Optional<Club> findByTitle(String title);

    // this will generate a Query with 2 fields...TODO test this out if it works..
    Optional<Club> findByTitleAndContent(String title,String content);

    // TODO this search functionality works if the keyword matches the exact case. This should work with ignore case
    @Query("SELECT c from Club c WHERE c.title LIKE CONCAT('%', :keyword, '%')")
    List<Club> searchClubs(String keyword);

}
