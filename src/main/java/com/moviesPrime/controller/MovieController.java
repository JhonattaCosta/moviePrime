package com.moviesPrime.controller;

import com.moviesPrime.controller.request.MovieRequest;
import com.moviesPrime.controller.response.MovieResponse;
import com.moviesPrime.entity.Movie;
import com.moviesPrime.mapper.MovieMapper;
import com.moviesPrime.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("moviesprime/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@RequestBody MovieRequest request) {
        Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(request));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(savedMovie));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAllMovies(){
        return ResponseEntity.ok(movieService.findAllMovies()
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());

    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findMovieId(@PathVariable Long id){
        return movieService.findMovieId(id)
                .map( movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovieId(@PathVariable Long id,@RequestBody MovieRequest request){
        return movieService.updateMovieId(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieId (@PathVariable Long id){
        movieService.deleteMovieId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
