package com.cinema;

import com.cinema.lib.Injector;
import com.cinema.model.Movie;
import com.cinema.service.MovieService;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Ferris Bueller's Day Off");
        movie.setDescription("A 1986 American teen comedy film starring Matthew Broderick as "
                + "Ferris Bueller, a high-school slacker who skips school for a day in Chicago");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);
    }
}
