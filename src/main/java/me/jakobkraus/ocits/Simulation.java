package me.jakobkraus.ocits;

import me.jakobkraus.ocits.application.Application;
import me.jakobkraus.ocits.common.DatacenterUtils;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.core.CloudSimPlus;
import org.cloudsimplus.listeners.EventInfo;

public class Simulation {
    public static int SIMULATION_LENGTH = 7200; // Simulate 2h
    public static String[] COUNTRIES = new String[]{"Germany", "USA", "China", "Australia", "Ukraine"};

    private static final CloudSimPlus simulation = new CloudSimPlus();

    public static CloudSimPlus getSimulation() {
        return simulation;
    }

    public static void clockTickHandler(EventInfo info, Application application) {
        // System.out.println(application.getFrontendCloudlet().getFinishedLengthSoFar());
    }

    public static void main(String[] args) {
        var datacenter = DatacenterUtils.createDatacenter();
        var application = new Application();

        Simulation.simulation.addOnClockTickListener(eventInfo -> {
            clockTickHandler(eventInfo, application);
        });

        Simulation.simulation.terminateAt(Simulation.SIMULATION_LENGTH);
        Simulation.simulation.start();

        new CloudletsTableBuilder(application.getBroker().getCloudletFinishedList()).build();
    }
}