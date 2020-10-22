package com.cinema;

import com.cinema.config.AppConfig;
import com.cinema.exceptions.AuthenticationException;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.model.User;
import com.cinema.security.AuthenticationService;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    private static final String DATE = "20201021";
    private static final String DATE_TIME = "2020-10-21T10:15:30";

    public static void main(String[] args) throws AuthenticationException, InterruptedException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Movie movie = new Movie();
        movie.setTitle("Ferris Bueller's Day Off");
        movie.setDescription("A 1986 American teen comedy film starring Matthew Broderick as "
                + "Ferris Bueller, a high-school slacker who skips school for a day in Chicago");
        MovieService movieService = context.getBean(MovieService.class);
        movieService.add(movie);
        movieService.getAll().forEach(log::info);

        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(30);
        hall1.setDescription("A newly revamped VIP hall with a 3D screen and reclining armchairs");
        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);
        cinemaHallService.add(hall1);
        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(150);
        hall2.setDescription("A standard hall");
        cinemaHallService.add(hall2);
        log.info("All cinema halls: \n");
        cinemaHallService.getAll().forEach(log::info);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setCinemaHall(hall1);
        movieSession1.setShowTime(LocalDateTime.parse(DATE_TIME,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(movie);
        movieSession2.setCinemaHall(hall2);
        movieSession2.setShowTime(LocalDateTime.parse(DATE_TIME,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        MovieSessionService movieSessionService = context.getBean(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        log.info("All available sessions: \n");
        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.parse(DATE, DateTimeFormatter.BASIC_ISO_DATE))
                .forEach(log::info);

        User user1 = new User();
        user1.setEmail("user@gmail.com");
        user1.setPassword("password");
        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
        log.info("A user has been registered: "
                + authenticationService.register(user1.getEmail(), user1.getPassword()));
        try {
            authenticationService.login(user1.getEmail(), user1.getPassword());
            log.info("A user successfully logged in.");
        } catch (AuthenticationException e) {
            log.warn("A user failed to log in. AuthenticationException: ", e);
        }

        UserService userService = context.getBean(UserService.class);
        User userFromDb = userService.findByEmail("user@gmail.com").get();
        ShoppingCartService shoppingCartService = context.getBean(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession1, userFromDb);
        log.info("Cart with tix: " + shoppingCartService.getByUser(userFromDb));

        OrderService orderService = context.getBean(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUser(userFromDb).getTickets(),
                userFromDb);
        shoppingCartService.addSession(movieSession1, userFromDb);
        shoppingCartService.addSession(movieSession2, userFromDb);
        Thread.sleep(5000);
        orderService.completeOrder(shoppingCartService.getByUser(userFromDb).getTickets(),
                userFromDb);
        log.info("User's orders: " + orderService.getOrderHistory(userFromDb));
        log.info("Empty cart: " + shoppingCartService.getByUser(userFromDb));
    }
}
