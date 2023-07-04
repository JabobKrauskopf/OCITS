package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.cloudlets.FunctionCloudlet;
import me.jakobkraus.ocits.cloudlets.FunctionStatus;
import me.jakobkraus.ocits.datacenter.GlobalVm;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.listeners.EventInfo;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.utilizationmodels.UtilizationModelFull;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private final List<Country> countriesWithFrontend;
    private final List<FunctionCloudlet> functionCloudlets = new ArrayList<>();
    private final String applicationName;

    public Application(List<Country> countriesWithFrontend, String applicationName) {
        this.countriesWithFrontend = countriesWithFrontend;
        this.applicationName = applicationName;

        var broker = Simulation.getBroker();

        var vms = this.countriesWithFrontend.stream()
                .map(country -> new GlobalVm(10000, 2, country)
                        .setRam(Simulation.VM_RAM)
                        .setBw(Simulation.VM_BANDWIDTH)
                        .setSize(Simulation.VM_SIZE)
                ).toList();

        broker.submitVmList(vms);
    }

    public void process(EventInfo info) {
        for (int i = 0; i < this.functionCloudlets.size(); i++)
            this.functionCloudlets.get(i).process(info);
    }

    public void request(EventInfo info, User user) {
        var closestAvailableCountry = Simulation.getCountryCostMapping()
                .getClosestCountry(user.getCountry(), this.countriesWithFrontend);

        var availableCloudlets = this.functionCloudlets.stream()
                .filter(cloudlet -> cloudlet.getCountry() == closestAvailableCountry)
                .filter(cloudlet -> cloudlet.getFunctionStatus() == FunctionStatus.Idling).toList();

        if (!availableCloudlets.isEmpty()) {
            var cloudlet = availableCloudlets.get(0);
            cloudlet.setRespondTo(user);
            cloudlet.setFunctionStatus(FunctionStatus.Requested);
            cloudlet.setRequestedSince(info.getTime());
            return;
        }

        var newCloudlet = createFrontend(closestAvailableCountry, info.getTime());
        newCloudlet.setRespondTo(user);
        this.addFrontendCloudlet(newCloudlet);
    }

    public void addFrontendCloudlet(FunctionCloudlet cloudlet) {
        this.functionCloudlets.add(cloudlet);
        Simulation.getBroker().submitCloudlet(cloudlet);
    }

    public void removeFrontendCloudlet(FunctionCloudlet cloudlet) {
        this.functionCloudlets.remove(cloudlet);
    }

    public FunctionCloudlet createFrontend(Country country, double startupTime) {
        final long length = -1;
        final long fileSize = 50000000; // 50 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return (FunctionCloudlet) new FunctionCloudlet(length, 1, country, this, startupTime)
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
