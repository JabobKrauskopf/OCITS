package me.jakobkraus.ocits.datacenter;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.cloudlets.GlobalCloudlet;
import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.core.CloudSimPlus;
import org.cloudsimplus.datacenters.Datacenter;
import org.cloudsimplus.vms.Vm;

public class GlobalDatacenterBroker extends DatacenterBrokerSimple {
    public GlobalDatacenterBroker(CloudSimPlus simulation) {
        super(simulation);
    }

    public GlobalDatacenterBroker(CloudSimPlus simulation, String name) {
        super(simulation, name);
    }

    @Override
    protected Datacenter defaultDatacenterMapper(Datacenter lastDatacenter, Vm vm) {
        if(getDatacenterList().isEmpty()) {
            throw new IllegalStateException("No datacenters were created.");
        }

        var dataCenterList = getDatacenterList();

        if (dataCenterList.stream().anyMatch(datacenter -> !(datacenter instanceof GlobalDatacenter)) || !(vm instanceof GlobalVm globalVm)){
            return super.defaultDatacenterMapper(lastDatacenter, vm);
        }

        var globalDatacenterList = dataCenterList.stream()
                .map(datacenter -> (GlobalDatacenter) datacenter).toList();
        var datacenterCountries = globalDatacenterList.stream()
                .map(GlobalDatacenter::getCountry).toList();

        var closestCountry = Simulation.getCountryCostMapping()
                .getClosestCountry(globalVm.getCountry(), datacenterCountries);

        return globalDatacenterList.stream().filter(datacenter -> datacenter.getCountry() == closestCountry)
                .findFirst().orElse(null);
    }

    @Override
    protected Vm defaultVmMapper(Cloudlet cloudlet) {
        if (cloudlet.isBoundToVm()) {
            return cloudlet.getVm();
        }

        if (!(cloudlet instanceof GlobalCloudlet globalCloudlet)) {
            return super.defaultVmMapper(cloudlet);
        }

        var vmList = getVmExecList().stream()
                .filter(vm -> vm.getHost().getDatacenter() instanceof GlobalDatacenter)
                .toList();

        if (vmList.isEmpty()) {
            return Vm.NULL;
        }

        var datacenterCountries = vmList.stream()
                .map(vm -> ((GlobalDatacenter) vm.getHost().getDatacenter()).getCountry()).toList();

        var closestCountry = Simulation.getCountryCostMapping()
                .getClosestCountry(globalCloudlet.getCountry(), datacenterCountries);

        return vmList.stream()
                .filter(vm -> ((GlobalDatacenter) vm.getHost().getDatacenter()).getCountry() == closestCountry)
                .findFirst().orElse(Vm.NULL);
    }
}
