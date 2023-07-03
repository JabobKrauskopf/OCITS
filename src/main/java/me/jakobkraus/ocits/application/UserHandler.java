package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;

import java.util.List;
import java.util.stream.IntStream;

public class UserHandler {
    public static List<User> createUsers() {
        return Simulation.USERS_PER_COUNTRY.entrySet().stream().flatMap(entry ->
            IntStream.range(0, entry.getValue()).mapToObj(i -> new User(entry.getKey()))
        ).toList();
    }
}
