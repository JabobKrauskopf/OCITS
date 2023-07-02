package me.jakobkraus.ocits.common;

import me.jakobkraus.ocits.Simulation;
import org.cloudsimplus.brokers.DatacenterBroker;
import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.cloudlets.network.CloudletExecutionTask;
import org.cloudsimplus.cloudlets.network.NetworkCloudlet;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.utilizationmodels.UtilizationModelFull;
import org.cloudsimplus.vms.network.NetworkVm;

import java.util.List;

public class Application {
    public static int VM_RAM = 100;
    public static int VM_BANDWIDTH = 1000;
    public static int VM_SIZE = 1000;
    public static double DATABASE_RAM_PERCENTAGE = 0.90;
    private final DatacenterBroker broker;
    private final NetworkCloudlet databaseCloudlet;
    private NetworkCloudlet frontendCloudlet;
    private final NetworkVm vm;

    public Application() {
        var simulation = Simulation.getSimulation();
        this.broker = new DatacenterBrokerSimple(simulation);

        // Keep the Database running the whole time
        var ex0 = new CloudletExecutionTask(0, Simulation.SIMULATION_LENGTH);
        ex0.setMemory((long) (Application.VM_RAM * Application.DATABASE_RAM_PERCENTAGE));
        var ex1 = new CloudletExecutionTask(0, 10);
        ex1.setMemory((long) (Application.VM_RAM * (1 - Application.DATABASE_RAM_PERCENTAGE)));


        this.databaseCloudlet = createDatabase();
        this.frontendCloudlet = createFrontend();
        databaseCloudlet.addTask(ex0);
        // frontendCloudlet.addTask(ex1);

        this.vm = new NetworkVm(100, 1);
        this.vm.setRam(Application.VM_RAM).setBw(Application.VM_BANDWIDTH).setSize(Application.VM_SIZE);

        broker.submitVmList(List.of(this.vm));
        broker.submitCloudletList(List.of(this.databaseCloudlet, this.frontendCloudlet));
    }

    public NetworkCloudlet createDatabase() {
        final long length = 1;
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
        final long length = 1;
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

    public NetworkCloudlet getDatabaseCloudlet() {
        return this.databaseCloudlet;
    }
    public NetworkCloudlet getFrontendCloudlet() {
        return this.frontendCloudlet;
    }

    public NetworkVm getVm() {
        return this.vm;
    }
}
