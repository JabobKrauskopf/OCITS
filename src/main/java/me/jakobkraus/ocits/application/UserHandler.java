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
            var countries = Country.values();
            var countryIndex = new Random().nextInt(countries.length);
            var country = countries[countryIndex];

            var randomTime = new Random()
                .nextGaussian() * (Simulation.USER_START_TIME_STANDARD_DEVIATION) + (Simulation.USER_START_TIME_MEAN);

            long startTime;

            if (randomTime < 0)
                startTime = 0;
            else if (randomTime > Simulation.SIMULATION_LENGTH)
                startTime = Simulation.SIMULATION_LENGTH;
            else
                startTime = (long) randomTime;

            var period = new Random().nextFloat(
                Simulation.MAXIMUM_USER_PERIOD - Simulation.MINIMUM_USER_PERIOD + 1
            ) + Simulation.MINIMUM_USER_PERIOD;

            var maxRequests = new Random().nextInt(
                Simulation.MAXIMUM_USER_MAX_REQUESTS - Simulation.MINIMUM_USER_MAX_REQUESTS + 1
            ) + Simulation.MINIMUM_USER_MAX_REQUESTS;

            userList.add(new User(userId++, country, startTime, period, maxRequests, application));
        }

        return userList;
    }
}
