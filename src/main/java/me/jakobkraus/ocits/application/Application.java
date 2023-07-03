package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.cloudlets.DatabaseCloudlet;
import me.jakobkraus.ocits.cloudlets.FrontendCloudlet;
import me.jakobkraus.ocits.cloudlets.GlobalCloudlet;
import me.jakobkraus.ocits.datacenter.GlobalVm;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.cloudlets.CloudletSimple;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.utilizationmodels.UtilizationModelFull;

import java.util.List;
import java.util.stream.Stream;

public class Application {
    private final List<Country> countriesWithDatabase;
    private final List<Country> countriesWithFrontend;
    private final List<Cloudlet> databaseCloudlets;
    private List<Cloudlet> frontendCloudlets;
    private String applicationName;

    public Application(List<Country> countriesWithDatabase, List<Country> countriesWithFrontend, String applicationName) {
        this.countriesWithDatabase = countriesWithDatabase;
        this.countriesWithFrontend = countriesWithFrontend;
        this.applicationName = applicationName;

        var broker = Simulation.getBroker();

        this.databaseCloudlets = countriesWithDatabase.stream().map(this::createDatabase).toList();

        var vms = Stream.concat(this.countriesWithDatabase.stream(), this.countriesWithFrontend.stream())
                .map(country -> new GlobalVm(10000, 2, country)
                        .setRam(Simulation.VM_RAM)
                        .setBw(Simulation.VM_BANDWIDTH)
                        .setSize(Simulation.VM_SIZE)
                ).toList();

        broker.submitVmList(vms);
        broker.submitCloudletList(this.databaseCloudlets);
    }

    public void process() {

    }

    public void request(Country country) {

    }

    public Cloudlet createDatabase(Country country) {
        final long length = -1;
        final long fileSize = 150000000; // 150 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return new DatabaseCloudlet(length, 1, country, this.applicationName)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
    }

    public Cloudlet createFrontend(Country country) {
        final long length = 1;
        final long fileSize = 50000000; // 50 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return new FrontendCloudlet(length, 1, country, this.applicationName)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
    }
}
