package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.listeners.EventInfo;

public class User {
    private final Country country;
    private final long startTime;
    private final double frequency; // in s between requests
    private final int requestCount;
    private final Application application;

    private UserStatus status = UserStatus.Prestart;
    private double lastRequest;

    public User(Country country, int startTime, double frequency, int requestCount, Application application) {
        this.country = country;
        this.startTime = startTime;
        this.frequency = frequency;
        this.requestCount = requestCount;
        this.application = application;
        this.lastRequest = startTime - frequency;
    }

    public void process(EventInfo info) {
        switch (this.status) {
            case Idling -> processIdling(info);
            case Prestart -> processPrestart(info);
        }
    }

    public void processIdling(EventInfo info) {
        if (info.getTime() - this.lastRequest < frequency)
            return;

        this.application.request(info, this);
        this.setStatus(UserStatus.Waiting);
    }
    public void processPrestart(EventInfo info) {
        if (info.getTime() < this.startTime)
            return;

        this.setStatus(UserStatus.Idling);
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public Country getCountry() {
        return this.country;
    }

    public void respond(EventInfo info) {
        this.lastRequest = info.getTime();
        this.setStatus(UserStatus.Idling);
    }
}
