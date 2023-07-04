package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.global.Country;

import java.util.List;

public class UserHandler {
    public static List<User> createUsers(Application application) {
        return List.of(
                new User(Country.Germany, 0, 3, 700, application)
//                new User(Country.Brazil, 0, 5, 9000, application),
//                new User(Country.Mexico, 1200, 2, 2700, application)
        );
    }
}
