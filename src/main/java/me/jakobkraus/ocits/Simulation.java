package me.jakobkraus.ocits;

import me.jakobkraus.ocits.common.Application;
import me.jakobkraus.ocits.common.DatacenterUtils;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.cloudlets.network.CloudletTask;
import org.cloudsimplus.core.CloudSimPlus;
import org.cloudsimplus.listeners.EventInfo;
import org.cloudsimplus.listeners.EventListener;

public class Simulation {
    public static int SIMULATION_LENGTH = 100000;

    private static final CloudSimPlus simulation = new CloudSimPlus();

    public static CloudSimPlus getSimulation() {
        return simulation;
    }

    public static void clockTickHandler(EventInfo info) {
        // System.out.println(info.getTime());
    }

    public static void main(String[] args) {
        var datacenter = DatacenterUtils.createDatacenter();
        var application = new Application();

        Simulation.simulation.addOnClockTickListener(Simulation::clockTickHandler);

        Simulation.simulation.start();

        new CloudletsTableBuilder(application.getBroker().getCloudletFinishedList()).build();
    }
}