package com.cinema.service.mapper;

import com.cinema.model.Movie;
import com.cinema.model.dto.MovieRequestDto;
import com.cinema.model.dto.MovieResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieResponseDto mapToDto(Movie movie) {
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setTitle(movie.getTitle());
        movieResponseDto.setDescription(movie.getDescription());
        return movieResponseDto;
    }

    public Movie mapToMovie(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDescription(movieRequestDto.getDescription());
        return movie;
    }
}
