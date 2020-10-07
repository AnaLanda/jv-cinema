package com.cinema.dao;

import com.cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao {
    List<MovieSession> findSessionsByIdAndDate(Long movieId, LocalDate date);

    MovieSession add(MovieSession movieSession);
}
