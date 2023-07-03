package me.jakobkraus.ocits.datacenter;

import me.jakobkraus.ocits.Simulation;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.hosts.Host;
import org.cloudsimplus.hosts.HostSimple;
import org.cloudsimplus.provisioners.ResourceProvisionerSimple;
import org.cloudsimplus.resources.Pe;
import org.cloudsimplus.resources.PeSimple;
import org.cloudsimplus.schedulers.vm.VmSchedulerTimeShared;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class DatacenterUtils {
    public static GlobalDatacenter createDatacenter(Country country) {
        var hosts = IntStream.range(0, Simulation.HOSTS_PER_DATACENTER)
                .mapToObj(i -> DatacenterUtils.createHost()).toList();

        return new GlobalDatacenter(Simulation.getSimulation(), country, hosts);
    }

    public static Host createHost() {
        var peList = new ArrayList<Pe>();
        for (int i = 0; i < Simulation.HOST_CORES; i++)
            peList.add(new PeSimple(Simulation.HOST_MIPS));

        return new HostSimple(Simulation.HOST_RAM, Simulation.HOST_BW, Simulation.HOST_STORAGE, peList)
                .setRamProvisioner(new ResourceProvisionerSimple())
                .setBwProvisioner(new ResourceProvisionerSimple())
                .setVmScheduler(new VmSchedulerTimeShared());
    }
}
