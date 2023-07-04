package me.jakobkraus.ocits.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    public static void saveFunctionLogsToCsv(String fileName) throws FileNotFoundException {
        var csvFile = new File(fileName);
        try (var writer = new PrintWriter(csvFile)) {
            writer.println(FunctionPayload.getCsvHeader());
            functionLogs.stream().map(FunctionPayload::toString).forEach(writer::println);
        }
    }

    public static void saveUserLogsToCsv(String fileName) throws FileNotFoundException {
        var csvFile = new File(fileName);
        try (var writer = new PrintWriter(csvFile)) {
            writer.println(UserPayload.getCsvHeader());
            userLogs.stream().map(UserPayload::toString).forEach(writer::println);
        }
    }
}
