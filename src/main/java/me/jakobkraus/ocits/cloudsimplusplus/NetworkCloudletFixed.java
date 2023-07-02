package me.jakobkraus.ocits.cloudsimplusplus;

import org.cloudsimplus.cloudlets.CloudletAbstract;
import org.cloudsimplus.cloudlets.network.NetworkCloudlet;

import java.util.ArrayList;

public class NetworkCloudletFixed extends NetworkCloudlet {
    public NetworkCloudletFixed(long length, int pesNumber) {
        super(length, pesNumber);
    }

    public NetworkCloudletFixed(int id, long length, int pesNumber) {
        super(id, length, pesNumber);
    }

    @Override
    public boolean isFinished() {
        // This reflection fixes an issue when creating a NetworkCloudlet. See: https://github.com/cloudsimplus/cloudsimplus/issues/455
        try {
            var tasksField = NetworkCloudlet.class.getDeclaredField("tasks");
            tasksField.setAccessible(true);
            if (tasksField.get(this) == null) {
                tasksField.set(this, new ArrayList<>());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return super.isFinished();
    }

    @Override
    public long getLength() {
        try {
            var lenghtField = CloudletAbstract.class.getDeclaredField("length");
            lenghtField.setAccessible(true);
            return (long) lenghtField.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
