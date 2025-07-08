package com.moviesPrime.service;

import com.moviesPrime.controller.response.MovieResponse;
import com.moviesPrime.entity.Category;
import com.moviesPrime.entity.Movie;
import com.moviesPrime.entity.Streaming;
import com.moviesPrime.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieSericeTest {
    @Mock
    private MovieRepository repository;

    @Mock
    private CategoryService categoryService;

    @Mock StreamingService streamingService;

    @InjectMocks
    private MovieService service;

    @Test
    public void testGetAllMovie(){
        List<Category> categories = Arrays.asList(
                new Category(1L,"Ação"),
                new Category(2L, "Sci-fi")
        );
        List<Streaming> streamings = Arrays.asList(
                new Streaming(1L, "Disney+"),
                new Streaming(2L,"Netflix")
        );

        List<Movie> moviesfake = Arrays.asList(
                new Movie(1L,"Star Wars 1", "Guerra nas Estrelas", LocalDate.of(1997,5,4), 4.5, LocalDateTime.of(2025,1,1,0,0),LocalDateTime.of(2025,1,1,0,0),categories,streamings),
                new Movie(2L,"Star Wars 2", "Guerra nas Estrelas", LocalDate.of(1997,5,4), 4.5, LocalDateTime.of(2025,1,1,0,0),LocalDateTime.of(2025,1,1,0,0),categories,streamings)
        );

        when(repository.findAll()).thenReturn(moviesfake);

        List<Movie> movies = service.findAllMovies();

        assertEquals(2,categories.size());
    }

    @Test
    public void testSaveMovie(){

        List<Category> categories = Arrays.asList(
                new Category(1L,"Ação"),
                new Category(2L, "Sci-fi")
        );
        List<Streaming> streamings = Arrays.asList(
                new Streaming(1L, "Disney+"),
                new Streaming(2L,"Netflix")
        );

        Movie moviefake = new Movie(
                1L,
                "Star Wars 1",
                "Guerra nas Estrelas",
                LocalDate.of(1997,5,4),
                4.5,
                LocalDateTime.of(2025,1,1,0,0),
                LocalDateTime.of(2025,1,1,0,0),
                categories,
                streamings
        );

         when(repository.save(any(Movie.class))).thenReturn(moviefake);

        Movie savedMovie = service.saveMovie(moviefake);

        assertEquals(moviefake, savedMovie);

    }


}
