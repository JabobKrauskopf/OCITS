package me.jakobkraus.ocits.container;

import me.jakobkraus.ocits.common.Application;
import me.jakobkraus.ocits.common.DatacenterUtils;
import org.cloudsimplus.datacenters.network.NetworkDatacenter;

import java.util.List;

public class ContainerExample {
    private final List<Application> applications;
    private final List<NetworkDatacenter> datacenters;
    public ContainerExample() {
        var datacenter = DatacenterUtils.createDatacenter();
        var application = new Application();

        this.applications = List.of(application);
        this.datacenters = List.of(datacenter);
    }

    public List<Application> getApplications() {
        return this.applications;
    }

    public List<NetworkDatacenter> getDatacenters() {
        return this.datacenters;
    }
}
