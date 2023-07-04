package me.jakobkraus.ocits.logging;

import me.jakobkraus.ocits.cloudlets.FunctionCloudlet;
import me.jakobkraus.ocits.cloudlets.FunctionStatus;
import org.cloudsimplus.listeners.EventInfo;

public class FunctionPayload {
    private final FunctionCloudlet cloudlet;
    private final FunctionStatus status;
    private final EventInfo info;
    public FunctionPayload(FunctionCloudlet cloudlet, FunctionStatus status, EventInfo info) {
        this.cloudlet = cloudlet;
        this.status = status;
        this.info = info;
    }

    @Override
    public String toString() {
        return "";
    }
}
