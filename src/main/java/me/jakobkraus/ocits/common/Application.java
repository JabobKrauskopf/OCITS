package me.jakobkraus.ocits.common;

import me.jakobkraus.ocits.Simulation;
import org.cloudsimplus.brokers.DatacenterBroker;
import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.cloudlets.network.NetworkCloudlet;
import org.cloudsimplus.vms.network.NetworkVm;

import java.util.List;

public class Application {
    private final DatacenterBroker broker;
    private final List<Cloudlet> cloudlets;
    private final NetworkVm vm;

    public Application() {
        var simulation = Simulation.getSimulation();
        this.broker = new DatacenterBrokerSimple(simulation);

        var cl0 = new NetworkCloudlet(1, 1000);
        var cl1 = new NetworkCloudlet(1, 1000);

        this.cloudlets = List.of(cl0, cl1);
        this.vm = new NetworkVm(1, 100, 100);
        this.vm.setRam(10000).setBw(1000).setSize(10);

        broker.submitVmList(List.of(this.vm));
        broker.submitCloudletList(this.cloudlets);
    }

    public DatacenterBroker getBroker() {
        return this.broker;
    }

    public List<Cloudlet> getCloudlets() {
        return this.cloudlets;
    }

    public NetworkVm getVm() {
        return this.vm;
    }
}
