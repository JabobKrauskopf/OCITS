package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.global.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserHandler {
    private static long userId = 0;

    public static List<User> createUsers(Application application) {
        var userList = new ArrayList<User>();

        for (int i = 0; i < Simulation.NUMBER_OF_USERS; i++) {
            var countryIndex = new Random().nextInt(9);

            var country = switch (countryIndex) {
                case 1 -> Country.USA;
                case 2 -> Country.China;
                case 3 -> Country.Australia;
                case 4 -> Country.Ukraine;
                case 5 -> Country.SouthAfrica;
                case 6 -> Country.Canada;
                case 7 -> Country.Mexico;
                case 8 -> Country.Brazil;
                default -> Country.Germany;
            };

            // Set start time randomly (between 0 and 39999)
            var startTime = new Random().nextInt(40000);

            // Set period randomly (between 1.0 and 10.0)
            var period = new Random().nextFloat(10) + 1;

            // Set max requests randomly (between 25 and 1000)
            var maxRequests = new Random().nextInt(976) + 25;

            userList.add(new User(userId++, country, startTime, period, maxRequests, application));
        }

        return userList;
    }
}
