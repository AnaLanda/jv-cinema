package com.cinema;

import com.cinema.exceptions.AuthenticationException;
import com.cinema.lib.Injector;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.model.User;
import com.cinema.security.AutheticationService;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");
    private static final String DATE = "20201021";
    private static final String DATE_TIME = "2020-10-21T10:15:30";

    public static void main(String[] args) throws AuthenticationException {
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
        movieSession1.setCinemaHall(hall1);
        movieSession1.setShowTime(LocalDateTime.parse(DATE_TIME,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(movie);
        movieSession2.setCinemaHall(hall2);
        movieSession2.setShowTime(LocalDateTime.parse(DATE_TIME,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        System.out.printf("All available sessions: \n");
        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.parse(DATE, DateTimeFormatter.BASIC_ISO_DATE))
                .forEach(System.out::println);

        AutheticationService autheticationService =
                (AutheticationService) injector.getInstance(AutheticationService.class);
        User user1 = new User();
        user1.setEmail("user@gmail.com");
        user1.setPassword("password");
        System.out.println("User1 has been registered: "
                + autheticationService.register(user1.getEmail(), user1.getPassword()));
        System.out.println("User1 has logged in: "
                + autheticationService.login(user1.getEmail(), user1.getPassword()));

        UserService userService =
                (UserService) injector.getInstance(UserService.class);
        User userFromDb = userService.findByEmail("user@gmail.com").get();
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.registerNewShoppingCart(userFromDb);
        shoppingCartService.addSession(movieSession1, userFromDb);
        System.out.println("Cart with tix: " + shoppingCartService.getByUser(userFromDb));
        shoppingCartService.clear(shoppingCartService.getByUser(userFromDb));
        System.out.println("Empty cart: " + shoppingCartService.getByUser(userFromDb));
    }
}
