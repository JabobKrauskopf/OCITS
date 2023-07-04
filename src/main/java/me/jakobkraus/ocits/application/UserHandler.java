package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.global.Country;

import java.util.List;

public class UserHandler {
    private static long userId = 0;

    public static List<User> createUsers(Application application) {
        return List.of(
                new User(userId++, Country.Germany, 0, 3, 10, application),
                new User(userId++, Country.Germany, 0, 0.1, 7, application),
                new User(userId++, Country.Brazil, 0, 5, 9000, application)
//                new User(Country.Mexico, 1200, 2, 2700, application)
        );
    }
}
