package me.jakobkraus.ocits;

import me.jakobkraus.ocits.application.Application;
import me.jakobkraus.ocits.application.User;
import me.jakobkraus.ocits.application.UserHandler;
import me.jakobkraus.ocits.cloudlets.ApplicationCloudlet;
import me.jakobkraus.ocits.cloudlets.FunctionCloudlet;
import me.jakobkraus.ocits.cloudlets.GlobalCloudlet;
import me.jakobkraus.ocits.datacenter.DatacenterUtils;
import me.jakobkraus.ocits.datacenter.GlobalDatacenter;
import me.jakobkraus.ocits.datacenter.GlobalDatacenterBroker;
import me.jakobkraus.ocits.datacenter.GlobalVm;
import me.jakobkraus.ocits.global.Country;
import me.jakobkraus.ocits.global.CountryCostMapping;
import org.cloudsimplus.brokers.DatacenterBroker;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.builders.tables.TextTableColumn;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.core.CloudSimPlus;
import org.cloudsimplus.listeners.EventInfo;

import java.util.List;

public class Simulation {
    public static final int SIMULATION_LENGTH = 7200; // Simulate 2h
    public static final int HOSTS_PER_DATACENTER = 5;
    public static final int HOST_MIPS = 10000;
    public static final int HOST_RAM = 131072; // 128GB
    public static final int HOST_BW = 100000; // 100 Gb
    public static final int HOST_CORES = 128; // 128 Threads
    public static final long HOST_STORAGE = 10000000L; // 10 TB

    public static final int VM_RAM = 8192; // 8GB
    public static final int VM_BANDWIDTH = 1000; // 1 Gb
    public static final int VM_SIZE = 4000; // 4 GB

    public static final double FUNCTION_STARTUP_TIME = 0.3;
    public static final double FUNCTION_IDLE_TIME = 7;
    public static final double FUNCTION_EXECUTION_TIME = 2;

    private static final CloudSimPlus simulation = new CloudSimPlus();
    private static final GlobalDatacenterBroker broker = new GlobalDatacenterBroker(simulation);
    private static final CountryCostMapping countryCostMapping = new CountryCostMapping();

    private static final List<Application> applications = List.of(
            new Application(List.of(
                    Country.Germany
            ), "Application1")
//            new Application(List.of(
//                    Country.USA,
//                    Country.SouthAfrica,
//                    Country.Australia
//            ), List.of(
//                    Country.Germany
//            ), "Application2")
    );
    private static final List<User> users = applications.stream()
            .flatMap(application -> UserHandler.createUsers(application).stream()).toList();

    public static void main(String[] args) {
        DatacenterUtils.createDatacenter(Country.Germany);
        DatacenterUtils.createDatacenter(Country.USA);
        DatacenterUtils.createDatacenter(Country.China);

        simulation.addOnClockTickListener(Simulation::clockTickHandler);

        simulation.terminateAt(Simulation.SIMULATION_LENGTH);
        simulation.start();

        new CloudletsTableBuilder(broker.getCloudletFinishedList())
                .addColumn(new TextTableColumn("   DC   ", " Country "), Simulation::getDatacenterCountry)
                .addColumn(new TextTableColumn("VM Expected", "  Country  "), Simulation::getVmCountry)
                .addColumn(new TextTableColumn("Cloudlet Expected", "     Country     "), Simulation::getCloudletCountry)
                .addColumn(new TextTableColumn("Cloudlet Type", "   Country   "), Simulation::getCloudletType)
                .addColumn(new TextTableColumn("Application", "  Country  "), Simulation::getCloudletApplication)
                .build();
    }

    public static void clockTickHandler(EventInfo info) {
        for (var application : applications)
            application.process(info);

        for (var user : users)
            user.process(info);
    }

    public static String getDatacenterCountry(final Cloudlet cloudlet) {
        var datacenter = cloudlet.getVm().getHost().getDatacenter();
        if (!(datacenter instanceof GlobalDatacenter))
            return "N/A";
        return ((GlobalDatacenter) datacenter).getCountry().toString();
    }

    public static String getVmCountry(final Cloudlet cloudlet) {
        var vm = cloudlet.getVm();
        if (!(vm instanceof GlobalVm))
            return "N/A";
        return ((GlobalVm) vm).getCountry().toString();
    }

    public static String getCloudletCountry(final Cloudlet cloudlet) {
        if (!(cloudlet instanceof GlobalCloudlet))
            return "N/A";
        return ((GlobalCloudlet) cloudlet).getCountry().toString();
    }

    public static String getCloudletType(final Cloudlet cloudlet) {
        if (cloudlet instanceof FunctionCloudlet)
            return "Frontend";
        else
            return "N/A";
    }

    public static String getCloudletApplication(final Cloudlet cloudlet) {
        if (!(cloudlet instanceof ApplicationCloudlet))
            return "N/A";
        return ((ApplicationCloudlet) cloudlet).getApplication().getApplicationName();
    }

    public static CloudSimPlus getSimulation() {
        return simulation;
    }

    public static DatacenterBroker getBroker() {
        return broker;
    }

    public static CountryCostMapping getCountryCostMapping() {
        return countryCostMapping;
    }
}