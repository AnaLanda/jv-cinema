package com.cinema;

import com.cinema.lib.Injector;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");
    private static final String DATE = "20201021";

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Ferris Bueller's Day Off");
        movie.setDescription("A 1986 American teen comedy film starring Matthew Broderick as "
                + "Ferris Bueller, a high-school slacker who skips school for a day in Chicago");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService)injector.getInstance(CinemaHallService.class);
        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(30);
        hall1.setDescription("A newly revamped VIP hall with a 3D screen and reclining armchairs");
        cinemaHallService.add(hall1);
        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(150);
        hall2.setDescription("A standard hall");
        cinemaHallService.add(hall2);
        System.out.printf("All cinema halls: \n");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(movie);
        movieSession1.setCinemaHall(hall1);
        movieSession1.setShowTime(LocalDate.parse(DATE, DateTimeFormatter.BASIC_ISO_DATE));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        System.out.printf("All available sessions: \n");
        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.parse(DATE, DateTimeFormatter.BASIC_ISO_DATE))
                .forEach(System.out::println);
    }
}
