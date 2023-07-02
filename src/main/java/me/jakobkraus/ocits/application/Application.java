package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.Simulation;
import org.cloudsimplus.brokers.DatacenterBroker;
import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.cloudlets.Cloudlet;
import org.cloudsimplus.cloudlets.CloudletSimple;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.utilizationmodels.UtilizationModelFull;
import org.cloudsimplus.vms.network.NetworkVm;

import java.util.List;

public class Application {
    public static int VM_RAM = 100;
    public static int VM_BANDWIDTH = 1000;
    public static int VM_SIZE = 1000;
    private final DatacenterBroker broker;
    private final Cloudlet databaseCloudlet;
    private Cloudlet frontendCloudlet;
    private final NetworkVm vm;

    public Application() {
        var simulation = Simulation.getSimulation();
        this.broker = new DatacenterBrokerSimple(simulation);

        this.databaseCloudlet = createDatabase();

        this.databaseCloudlet.setSubmissionDelay(0.2);

        this.vm = new NetworkVm(100, 1);
        this.vm.setRam(Application.VM_RAM).setBw(Application.VM_BANDWIDTH).setSize(Application.VM_SIZE);

        broker.submitVmList(List.of(this.vm));
        broker.submitCloudletList(List.of(this.databaseCloudlet, this.frontendCloudlet));
    }

    public void request() {

    }

    public Cloudlet createDatabase() {
        final long length = -1;
        final long fileSize = 150000000; // 150 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return new CloudletSimple(length, 1)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
    }

    public Cloudlet createFrontend() {
        final long length = 1;
        final long fileSize = 50000000; // 50 MB

        final var utilizationFull = new UtilizationModelFull();
        final var utilizationDynamic = new UtilizationModelDynamic(0.1);

        return new CloudletSimple(length, 1)
                .setFileSize(fileSize)
                .setOutputSize(fileSize)
                .setUtilizationModelCpu(utilizationFull)
                .setUtilizationModelRam(utilizationDynamic)
                .setUtilizationModelBw(utilizationDynamic);
    }

    public DatacenterBroker getBroker() {
        return this.broker;
    }

    public Cloudlet getDatabaseCloudlet() {
        return this.databaseCloudlet;
    }
    public Cloudlet getFrontendCloudlet() {
        return this.frontendCloudlet;
    }

    public NetworkVm getVm() {
        return this.vm;
    }
}
