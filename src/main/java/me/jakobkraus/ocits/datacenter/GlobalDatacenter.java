package me.jakobkraus.ocits.datacenter;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.allocationpolicies.VmAllocationPolicy;
import org.cloudsimplus.core.Simulation;
import org.cloudsimplus.datacenters.DatacenterSimple;
import org.cloudsimplus.hosts.Host;
import org.cloudsimplus.resources.DatacenterStorage;
import org.cloudsimplus.resources.SanStorage;

import java.util.List;

public class GlobalDatacenter extends DatacenterSimple {
    private final Country country;

    public GlobalDatacenter(Simulation simulation, Country country, List<? extends Host> hostList) {
        super(simulation, hostList);
        this.country = country;
    }

    public GlobalDatacenter(Simulation simulation, Country country, List<? extends Host> hostList, VmAllocationPolicy vmAllocationPolicy) {
        super(simulation, hostList, vmAllocationPolicy);
        this.country = country;
    }

    public GlobalDatacenter(Simulation simulation, Country country, VmAllocationPolicy vmAllocationPolicy) {
        super(simulation, vmAllocationPolicy);
        this.country = country;
    }

    public GlobalDatacenter(Simulation simulation, Country country, List<? extends Host> hostList, VmAllocationPolicy vmAllocationPolicy, List<SanStorage> storageList) {
        super(simulation, hostList, vmAllocationPolicy, storageList);
        this.country = country;
    }

    public GlobalDatacenter(Simulation simulation, Country country, List<? extends Host> hostList, VmAllocationPolicy vmAllocationPolicy, DatacenterStorage storage) {
        super(simulation, hostList, vmAllocationPolicy, storage);
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }
}
