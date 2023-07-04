package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.cloudlets.DatabaseCloudlet;
import me.jakobkraus.ocits.cloudlets.FrontendCloudlet;
import me.jakobkraus.ocits.cloudlets.FunctionStatus;
import me.jakobkraus.ocits.datacenter.GlobalVm;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.listeners.EventInfo;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.utilizationmodels.UtilizationModelFull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Application {
    private final List<Country> countriesWithDatabase;
    private final List<Country> countriesWithFrontend;
    private final List<DatabaseCloudlet> databaseCloudlets;
    private final List<FrontendCloudlet> frontendCloudlets = new ArrayList<>();
    private final String applicationName;

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

    public void process(EventInfo info) {
        for (int i = 0; i < this.frontendCloudlets.size(); i++)
            this.frontendCloudlets.get(i).process(info);
    }

    public void request(EventInfo info, User user) {
        var closestAvailableCountry = Simulation.getCountryCostMapping()
                .getClosestCountry(user.getCountry(), this.countriesWithFrontend);

        var availableCloudlets = this.frontendCloudlets.stream()
                .filter(cloudlet -> cloudlet.getCountry() == closestAvailableCountry)
                .filter(cloudlet -> cloudlet.getFunctionStatus() == FunctionStatus.Idling).toList();

        if (!availableCloudlets.isEmpty()) {
            var cloudlet = availableCloudlets.get(0);
            cloudlet.setRespondTo(user);
            cloudlet.setFunctionStatus(FunctionStatus.Executing);
            cloudlet.setStartExecuting(info.getTime());
            System.out.println("In here");
            return;
        }

        System.out.println("Out here");

        var newCloudlet = createFrontend(closestAvailableCountry, info.getTime());
        newCloudlet.setRespondTo(user);
        this.addFrontendCloudlet(newCloudlet);
    }

    public void addFrontendCloudlet (FrontendCloudlet cloudlet) {
        this.frontendCloudlets.add(cloudlet);
        Simulation.getBroker().submitCloudlet(cloudlet);
    }

    public void removeFrontendCloudlet (FrontendCloudlet cloudlet) {
        this.frontendCloudlets.remove(cloudlet);
    }

    public DatabaseCloudlet createDatabase(Country country) {
        final long length = -1;
        final long fileSize = 150000000; // 150 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return (DatabaseCloudlet) new DatabaseCloudlet(length, 1, country, this)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
    }

    public FrontendCloudlet createFrontend(Country country, double startupTime) {
        final long length = Simulation.SIMULATION_LENGTH;
        final long fileSize = 50000000; // 50 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return (FrontendCloudlet) new FrontendCloudlet(length, 1, country, this, startupTime)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
    }

    public String getApplicationName() {
        return this.applicationName;
    }
}
