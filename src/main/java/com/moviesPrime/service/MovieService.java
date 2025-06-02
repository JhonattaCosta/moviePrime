package com.moviesPrime.service;

import com.moviesPrime.entity.Category;
import com.moviesPrime.entity.Movie;
import com.moviesPrime.entity.Streaming;
import com.moviesPrime.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public Movie saveMovie(Movie movie){
        movie.setCategories(this.findCategoies(movie.getCategories()));
        movie.setStreamings(this.findStreamings(movie.getStreamings()));
        return repository.save(movie);
    }

    public List<Movie> findAllMovies(){
        return repository.findAll();
    }

    public List<Category> findCategoies(List<Category> categories){
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(category ->
                categoryService.findCategoryId(category.getId()).ifPresent(categoriesFound::add)
        );
        return categoriesFound;
    }

    public List<Streaming> findStreamings(List<Streaming> streamings){
        List<Streaming> streamingFound = new ArrayList<>();
        streamings.forEach(streaming -> streamingService.findStreamingId(streaming.getId()).ifPresent(streamingFound::add));
        return streamingFound;
    }

    public Optional<Movie> findMovieId(Long id){
        return repository.findById(id);
    }



    public void deleteMovieId(Long id){
        repository.deleteById(id);
    }

}
