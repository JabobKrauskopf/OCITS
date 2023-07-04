package me.jakobkraus.ocits.logging;

import java.util.ArrayList;
import java.util.List;

public class ResultLogger {
    private static final List<FunctionPayload> functionLogs = new ArrayList<>();
    private static final List<UserPayload> userLogs = new ArrayList<>();

    public static void log(FunctionPayload payload) {
        functionLogs.add(payload);
    }

    public static void log(UserPayload payload) {
        userLogs.add(payload);
    }
}
