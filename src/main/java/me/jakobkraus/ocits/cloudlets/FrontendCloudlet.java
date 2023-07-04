package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.application.Application;
import me.jakobkraus.ocits.application.User;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.listeners.EventInfo;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class FrontendCloudlet extends ApplicationCloudlet {
    private final double startTime;
    private FunctionStatus functionStatus = FunctionStatus.Starting;
    private double startExecuting;
    private double waitingSince;
    private double idlingSince;
    private User respondTo;

    public FrontendCloudlet(long length, int pesNumber, Country country, Application application, double startTime, UtilizationModel utilizationModel) {
        super(length, pesNumber, country, application, utilizationModel);
        this.startTime = startTime;
    }

    public FrontendCloudlet(long length, int pesNumber, Country country, Application application, double startTime) {
        super(length, pesNumber, country, application);
        this.startTime = startTime;
    }

    public FrontendCloudlet(long length, long pesNumber, Country country, Application application, double startTime) {
        super(length, pesNumber, country, application);
        this.startTime = startTime;
    }

    public FrontendCloudlet(long id, long length, long pesNumber, Country country, Application application, double startTime) {
        super(id, length, pesNumber, country, application);
        this.startTime = startTime;
    }

    public void setRespondTo(User respondTo) {
        this.respondTo = respondTo;
    }

    public void process(EventInfo info) {
        switch (this.functionStatus) {
            case Starting -> processStarting(info);
            case Waiting -> processWaiting(info);
            case Executing -> processExecuting(info);
            case Idling -> processIdling(info);
        }
    }

    public void processStarting(EventInfo info) {
        if (info.getTime() - this.startTime < Simulation.FUNCTION_STARTUP_TIME)
            return;

        this.setFunctionStatus(FunctionStatus.Executing);
        this.setStartExecuting(info.getTime());
    }
    public void processExecuting(EventInfo info) {
        if (info.getTime() - this.startExecuting < Simulation.FUNCTION_EXECUTION_TIME) {
            return;
        }

        this.setFunctionStatus(FunctionStatus.Idling);
        this.idlingSince = info.getTime();
        this.respondTo.respond(info);
    }
    public void processWaiting(EventInfo info) {

    }
    public void processIdling(EventInfo info) {
        if (info.getTime() - this.idlingSince < Simulation.FUNCTION_IDLE_TIME)
            return;

        this.setLength(this.getFinishedLengthSoFar());
        this.setFinishTime(info.getTime());
        this.setFunctionStatus(FunctionStatus.Finished);
        this.application.removeFrontendCloudlet(this);
    }

    public void setFunctionStatus(FunctionStatus functionStatus) {
        this.functionStatus = functionStatus;
    }

    public void setStartExecuting(double time) {
        this.startExecuting = time;
    }

    public FunctionStatus getFunctionStatus() {
        return this.functionStatus;
    }
}
