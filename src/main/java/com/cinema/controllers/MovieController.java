package com.cinema.controllers;

import com.cinema.model.Movie;
import com.cinema.model.dto.MovieRequestDto;
import com.cinema.model.dto.MovieResponseDto;
import com.cinema.service.MovieService;
import com.cinema.service.mapper.MovieMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private static final Logger log = Logger.getLogger(MovieController.class);
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping
    public Movie addMovie(@RequestBody MovieRequestDto movieRequestDto) {
        log.info("Trying to add MovieRequestDto " + movieRequestDto);
        Movie movie = movieService.add(movieMapper.mapToMovie(movieRequestDto));
        log.info("Successfully added MovieRequestDto " + movieRequestDto);
        return movie;
    }

    @GetMapping
    public List<MovieResponseDto> getAllMovies() {
        log.info("Trying to get MovieResponseDtos for all users");
        List<MovieResponseDto> allMovieResponseDtos = movieService.getAll().stream()
                .map(movieMapper::mapToDto)
                .collect(Collectors.toList());
        log.info("Successfully retrieved all MovieResponseDtos");
        return allMovieResponseDtos;
    }
}
