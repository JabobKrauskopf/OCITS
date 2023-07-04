package me.jakobkraus.ocits.logging;

import me.jakobkraus.ocits.application.User;
import me.jakobkraus.ocits.application.UserStatus;

public class UserPayload {
    private final User user;
    private final UserStatus status;
    private final double time;

    public UserPayload(User user, UserStatus status, double time) {
        this.user = user;
        this.status = status;
        this.time = time;
    }

    public static String getCsvHeader() {
        return String.join(",", "userId", "Status", "time");
    }

    @Override
    public String toString() {
        return String.join(",", String.valueOf(user.getId()), status.toString(), String.valueOf(time));
    }
}
