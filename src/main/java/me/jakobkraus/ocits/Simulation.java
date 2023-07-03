package me.jakobkraus.ocits;

import me.jakobkraus.ocits.application.Application;
import me.jakobkraus.ocits.cloudlets.ApplicationCloudlet;
import me.jakobkraus.ocits.cloudlets.DatabaseCloudlet;
import me.jakobkraus.ocits.cloudlets.FrontendCloudlet;
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
import java.util.Map;

public class Simulation {
    public static int SIMULATION_LENGTH = 7200; // Simulate 2h
    public static int HOSTS_PER_DATACENTER = 5;
    public static int HOST_MIPS = 10000;
    public static int HOST_RAM = 131072; // 128GB
    public static int HOST_BW = 100000; // 100 Gb
    public static int HOST_CORES = 128; // 128 Threads
    public static long HOST_STORAGE = 10000000L; // 10 TB

    public static int VM_RAM = 8192; // 8GB
    public static int VM_BANDWIDTH = 1000; // 1 Gb
    public static int VM_SIZE = 4000; // 4 GB

    public static Map<Country, Integer> USERS_PER_COUNTRY = Map.of(
            Country.Germany, 10,
            Country.USA, 10,
            Country.Australia, 5
    );


    private static final CloudSimPlus simulation = new CloudSimPlus();
    private static final GlobalDatacenterBroker broker = new GlobalDatacenterBroker(simulation);
    private static final CountryCostMapping countryCostMapping = new CountryCostMapping();

    private static final List<Application> applications = List.of(
            new Application(List.of(
                    Country.Germany,
                    Country.Canada,
                    Country.Australia
            ), List.of(
                    Country.Germany
            ), "Application1")
    );

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
        applications.forEach(Application::process);
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
        if (cloudlet instanceof DatabaseCloudlet)
            return "Database";
        else if (cloudlet instanceof FrontendCloudlet)
            return "Frontend";
        else
            return "N/A";
    }

    public static String getCloudletApplication(final Cloudlet cloudlet) {
        if (!(cloudlet instanceof ApplicationCloudlet))
            return "N/A";
        return ((ApplicationCloudlet) cloudlet).getApplicationName();
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