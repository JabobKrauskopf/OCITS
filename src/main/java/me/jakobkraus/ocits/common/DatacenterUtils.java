package me.jakobkraus.ocits.common;

import me.jakobkraus.ocits.Simulation;
import org.cloudsimplus.datacenters.network.NetworkDatacenter;
import org.cloudsimplus.hosts.network.NetworkHost;
import org.cloudsimplus.network.switches.EdgeSwitch;

import java.util.ArrayList;

public class DatacenterUtils {
    public static NetworkDatacenter createDatacenter() {
        var hosts = new ArrayList<NetworkHost>();
        var simulation = Simulation.getSimulation();
        var datacenter =  new NetworkDatacenter(simulation, hosts);
        setupInternalNetwork(datacenter);
        return datacenter;
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
