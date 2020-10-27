package com.cinema.dao;

import com.cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieSessionDao {
    List<MovieSession> findSessionsByIdAndDate(Long movieId, LocalDate date);

    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> findById(Long id);
}
