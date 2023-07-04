package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.listeners.EventInfo;

public class User {
    private final Country country;
    private final long startTime;
    private final double period; // in s
    private final int maxRequestCount;
    private final Application application;

    private UserStatus status = UserStatus.Prestart;
    private double lastRequest;
    private int currentRequestCount;

    public User(Country country, int startTime, double period, int maxRequestCount, Application application) {
        this.country = country;
        this.startTime = startTime;
        this.period = period;
        this.maxRequestCount = maxRequestCount;
        this.application = application;
        this.lastRequest = startTime - period;
    }

    public void process(EventInfo info) {
        switch (this.status) {
            case Idling -> processIdling(info);
            case Prestart -> processPrestart(info);
        }
    }

    public void processIdling(EventInfo info) {
        if (info.getTime() - this.lastRequest < this.period)
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
        this.currentRequestCount++;

        if (this.currentRequestCount >= this.maxRequestCount) {
            this.setStatus(UserStatus.Finished);
            return;
        }

        this.setStatus(UserStatus.Idling);
    }
}
