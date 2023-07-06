package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.cloudlets.FunctionCloudlet;
import me.jakobkraus.ocits.cloudlets.FunctionStatus;
import me.jakobkraus.ocits.datacenter.GlobalVm;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.listeners.EventInfo;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private final List<Country> countriesWithFunction;
    private final List<FunctionCloudlet> functionCloudlets = new ArrayList<>();
    private final String applicationName;

    public Application(List<Country> countriesWithFunction, String applicationName) {
        this.countriesWithFunction = countriesWithFunction;
        this.applicationName = applicationName;

        var broker = Simulation.getBroker();

        var vms = this.countriesWithFunction.stream()
            .map(country -> new GlobalVm(Simulation.VM_MIPS_CAPACITY, Simulation.VM_CORES, country)
                .setRam(Simulation.VM_RAM)
                .setBw(Simulation.VM_BANDWIDTH)
                .setSize(Simulation.VM_SIZE)
            ).toList();

        broker.submitVmList(vms);
    }

    public void process(EventInfo info) {
        for (FunctionCloudlet functionCloudlet : this.functionCloudlets) functionCloudlet.process(info);
    }

    public void request(EventInfo info, User user) {
        var closestAvailableCountry = Simulation.getCountryCostMapping()
            .getClosestCountry(user.getCountry(), this.countriesWithFunction);

        var availableCloudlets = this.functionCloudlets.stream()
            .filter(cloudlet -> cloudlet.getCountry() == closestAvailableCountry)
            .filter(cloudlet -> cloudlet.getFunctionStatus() == FunctionStatus.Idling).toList();

        if (!availableCloudlets.isEmpty()) {
            var cloudlet = availableCloudlets.get(0);
            cloudlet.setRespondTo(user);
            cloudlet.setFunctionStatus(FunctionStatus.Requested, info);
            cloudlet.setRequestedSince(info.getTime());
            return;
        }

        var newCloudlet = createFunction(closestAvailableCountry, info.getTime());
        newCloudlet.setRespondTo(user);
        this.addFunctionCloudlet(newCloudlet);
    }

    public void addFunctionCloudlet(FunctionCloudlet cloudlet) {
        this.functionCloudlets.add(cloudlet);
        Simulation.getBroker().submitCloudlet(cloudlet);
    }

    public void removeFunctionCloudlet(FunctionCloudlet cloudlet) {
        this.functionCloudlets.remove(cloudlet);
    }

    public FunctionCloudlet createFunction(Country country, double startupTime) {
        final long length = -1;
        final long fileSize = Simulation.FUNCTION_SIZE;

        return (FunctionCloudlet) new FunctionCloudlet(length, 1, country, this, startupTime)
            .setFileSize(fileSize)
            .setOutputSize(fileSize);
    }

    public String getApplicationName() {
        return this.applicationName;
    }
}
