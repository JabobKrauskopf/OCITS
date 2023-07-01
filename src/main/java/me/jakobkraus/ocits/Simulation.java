package me.jakobkraus.ocits;

import me.jakobkraus.ocits.common.Application;
import me.jakobkraus.ocits.common.DatacenterUtils;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.core.CloudSimPlus;

public class Simulation {
    public static int SIMULATION_LENGTH = 100000;

    public static CloudSimPlus simulation = new CloudSimPlus();

    public static CloudSimPlus getSimulation() {
        return simulation;
    }

    public static void main(String[] args) {
        var datacenter = DatacenterUtils.createDatacenter();
        var application = new Application();

        Simulation.simulation.start();

        new CloudletsTableBuilder(application.getBroker().getCloudletFinishedList()).build();
    }
}