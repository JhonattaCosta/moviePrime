package com.moviesPrime.repository;

import com.moviesPrime.entity.Category;
import com.moviesPrime.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findMovieByCategories(List<Category> categories);
}
