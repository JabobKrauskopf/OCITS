package me.jakobkraus.ocits;

import org.cloudsimplus.brokers.DatacenterBrokerSimple;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.cloudsimplus.cloudlets.CloudletSimple;
import org.cloudsimplus.core.CloudSimPlus;
import org.cloudsimplus.datacenters.DatacenterSimple;
import org.cloudsimplus.hosts.HostSimple;
import org.cloudsimplus.resources.PeSimple;
import org.cloudsimplus.utilizationmodels.UtilizationModelDynamic;
import org.cloudsimplus.vms.VmSimple;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        var simulation = new CloudSimPlus();

        var broker0 = new DatacenterBrokerSimple(simulation);

        long ram = 10000;
        long storage = 10000;
        long bw = 100000;

        var host0 = new HostSimple(ram, bw, storage, List.of(new PeSimple(20000)));

        var dc0 = new DatacenterSimple(simulation, List.of(host0));

        var vm0 = new VmSimple(1000, 1);
        vm0.setRam(1000).setBw(1000).setSize(1000);

        var utilizationModel = new UtilizationModelDynamic(0.5);
        var cloudlet0 = new CloudletSimple(10000, 1, utilizationModel);
        var cloudlet1 = new CloudletSimple(10000, 1, utilizationModel);
        var cloudletList = List.of(cloudlet0, cloudlet1);

        broker0.submitVmList(List.of(vm0));
        broker0.submitCloudletList(cloudletList);

        simulation.start();

        new CloudletsTableBuilder(broker0.getCloudletFinishedList()).build();
    }
}