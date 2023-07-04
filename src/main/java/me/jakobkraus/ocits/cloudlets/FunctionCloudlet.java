package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.application.Application;
import me.jakobkraus.ocits.application.User;
import me.jakobkraus.ocits.global.Country;
import me.jakobkraus.ocits.logging.FunctionPayload;
import me.jakobkraus.ocits.logging.ResultLogger;
import org.cloudsimplus.listeners.EventInfo;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class FunctionCloudlet extends ApplicationCloudlet {
    private final double startTime;
    private FunctionStatus functionStatus = FunctionStatus.Starting;
    private double executingSince;
    private double requestedSince;
    private double respondingSince;
    private double idlingSince;
    private User respondTo;

    public FunctionCloudlet(long length, int pesNumber, Country country, Application application, double startTime, UtilizationModel utilizationModel) {
        super(length, pesNumber, country, application, utilizationModel);
        this.startTime = startTime;
        ResultLogger.log(new FunctionPayload(this, FunctionStatus.Starting, startTime));
    }

    public FunctionCloudlet(long length, int pesNumber, Country country, Application application, double startTime) {
        super(length, pesNumber, country, application);
        this.startTime = startTime;
    }

    public FunctionCloudlet(long length, long pesNumber, Country country, Application application, double startTime) {
        super(length, pesNumber, country, application);
        this.startTime = startTime;
    }

    public FunctionCloudlet(long id, long length, long pesNumber, Country country, Application application, double startTime) {
        super(id, length, pesNumber, country, application);
        this.startTime = startTime;
    }

    public void setRespondTo(User respondTo) {
        this.respondTo = respondTo;
    }

    public void process(EventInfo info) {
        switch (this.functionStatus) {
            case Starting -> processStarting(info);
            case Executing -> processExecuting(info);
            case Requested -> processRequested(info);
            case Responding -> processResponding(info);
            case Idling -> processIdling(info);
        }
    }

    public void processStarting(EventInfo info) {
        if (info.getTime() - this.startTime < Simulation.FUNCTION_STARTUP_TIME)
            return;

        this.setFunctionStatus(FunctionStatus.Requested, info);
        this.setRequestedSince(info.getTime());
    }

    public void processExecuting(EventInfo info) {
        if (info.getTime() - this.executingSince < Simulation.FUNCTION_EXECUTION_TIME)
            return;

        this.setFunctionStatus(FunctionStatus.Responding, info);
        this.respondingSince = info.getTime();
    }

    public void processRequested(EventInfo info) {
        var requestCost = Simulation.getCountryCostMapping().getCost(this.respondTo.getCountry(), this.country);
        if (info.getTime() - this.requestedSince < requestCost)
            return;

        this.setFunctionStatus(FunctionStatus.Executing, info);
        this.executingSince = info.getTime();
    }

    public void processResponding(EventInfo info) {
        var responseCost = Simulation.getCountryCostMapping().getCost(this.respondTo.getCountry(), this.country);
        if (info.getTime() - this.respondingSince < responseCost)
            return;

        this.setFunctionStatus(FunctionStatus.Idling, info);
        this.idlingSince = info.getTime();
        this.respondTo.respond(info);
    }

    public void processIdling(EventInfo info) {
        if (info.getTime() - this.idlingSince < Simulation.FUNCTION_IDLE_TIME)
            return;

        this.setLength(this.getFinishedLengthSoFar());
        this.setFinishTime(info.getTime());
        this.setFunctionStatus(FunctionStatus.Finished, info);
        this.application.removeFrontendCloudlet(this);
    }

    public void setFunctionStatus(FunctionStatus functionStatus, EventInfo info) {
        this.functionStatus = functionStatus;
        ResultLogger.log(new FunctionPayload(this, functionStatus, info.getTime()));
    }

    public void setRequestedSince(double time) {
        this.requestedSince = time;
    }

    public FunctionStatus getFunctionStatus() {
        return this.functionStatus;
    }
}
