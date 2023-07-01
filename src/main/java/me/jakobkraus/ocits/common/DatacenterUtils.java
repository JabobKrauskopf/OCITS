package me.jakobkraus.ocits.common;

import me.jakobkraus.ocits.Simulation;
import org.cloudsimplus.datacenters.network.NetworkDatacenter;
import org.cloudsimplus.hosts.network.NetworkHost;
import org.cloudsimplus.network.switches.EdgeSwitch;
import org.cloudsimplus.provisioners.ResourceProvisionerSimple;
import org.cloudsimplus.resources.Pe;
import org.cloudsimplus.resources.PeSimple;
import org.cloudsimplus.schedulers.vm.VmSchedulerTimeShared;

import java.util.ArrayList;
import java.util.List;

public class DatacenterUtils {
    public static NetworkDatacenter createDatacenter() {
        var host = DatacenterUtils.createHost();
        var hosts = List.of(host);
        var simulation = Simulation.getSimulation();
        var datacenter =  new NetworkDatacenter(simulation, hosts);
        DatacenterUtils.setupInternalNetwork(datacenter);
        return datacenter;
    }

    public static NetworkHost createHost() {
        final long mips = 1000; // capacity of each CPU core (in Million Instructions per Second)
        final int ram = 2048; // host memory (Megabyte)
        final long storage = 1000000000; // host storage
        final long bw = 1000; // Gigabit Connection

        final var peList = new ArrayList<Pe>();
        for (int i = 0; i < 8; i++)
            peList.add(new PeSimple(mips));

        return (NetworkHost) new NetworkHost(ram, bw, storage, peList)
                .setRamProvisioner(new ResourceProvisionerSimple())
                .setBwProvisioner(new ResourceProvisionerSimple())
                .setVmScheduler(new VmSchedulerTimeShared());
    }

    public static void setupInternalNetwork(NetworkDatacenter datacenter) {
        var simulation = Simulation.getSimulation();
        var edgeSwitch = new EdgeSwitch(simulation, datacenter);

        datacenter.addSwitch(edgeSwitch);

        for (NetworkHost host : datacenter.getHostList()) {
            edgeSwitch.connectHost(host);
        }
    }
}
