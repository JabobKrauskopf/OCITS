package me.jakobkraus.ocits.common;

import me.jakobkraus.ocits.Simulation;
import org.cloudsimplus.brokers.DatacenterBroker;
import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.cloudlets.CloudletSimple;
import org.cloudsimplus.cloudlets.network.CloudletExecutionTask;
import org.cloudsimplus.cloudlets.network.NetworkCloudlet;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.utilizationmodels.UtilizationModelFull;
import org.cloudsimplus.vms.network.NetworkVm;

import java.util.List;

public class Application {
    private final DatacenterBroker broker;
    private final List<Cloudlet> cloudlets;
    private final NetworkVm vm;

    public Application() {
        var simulation = Simulation.getSimulation();
        this.broker = new DatacenterBrokerSimple(simulation);

//        var ex0 = new CloudletExecutionTask(0, 10000);
//        var ex1 = new CloudletExecutionTask(1, 10000);
//        ex0.setMemory(100);
//        ex1.setMemory(100);

        var databaseCloudlet = createDatabase();
        var frontendCloudlet = createFrontend();

        this.cloudlets = List.of(databaseCloudlet, frontendCloudlet);
        this.vm = new NetworkVm(100, 1);
        this.vm.setRam(100).setBw(1000).setSize(1000);

        broker.submitVmList(List.of(this.vm));
        broker.submitCloudletList(this.cloudlets);
    }

    public NetworkCloudlet createDatabase() {
        final long length = 10;
        final long fileSize = 150000000; // 150 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return (NetworkCloudlet) new NetworkCloudletFixed(length, 1)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
    }

    public NetworkCloudlet createFrontend() {
        final long length = 10;
        final long fileSize = 50000000; // 50 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return (NetworkCloudlet) new NetworkCloudletFixed(length, 1)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
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
