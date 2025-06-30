package com.moviesPrime.service;

import com.moviesPrime.controller.response.MovieResponse;
import com.moviesPrime.entity.Movie;
import com.moviesPrime.repository.MovieRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieSericeTest {
    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieService service;
}
