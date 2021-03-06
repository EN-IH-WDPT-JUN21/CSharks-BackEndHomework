package com.csharks.moviesbackend.repository;

import com.csharks.moviesbackend.dao.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

    Optional<Movies> findByTitleId(String titleId);

    void deleteByTitleId(String titleId);

}
