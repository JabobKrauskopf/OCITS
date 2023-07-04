package me.jakobkraus.ocits.logging;

import me.jakobkraus.ocits.application.User;
import me.jakobkraus.ocits.application.UserStatus;
import org.cloudsimplus.listeners.EventInfo;

public class UserPayload {
    private final User user;
    private final UserStatus status;
    private final EventInfo info;
    public UserPayload(User user, UserStatus status, EventInfo info) {
        this.user = user;
        this.status = status;
        this.info = info;
    }

    @Override
    public String toString() {
        return "";
    }
}
