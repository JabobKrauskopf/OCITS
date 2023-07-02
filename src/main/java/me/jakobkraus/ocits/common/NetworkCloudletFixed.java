package me.jakobkraus.ocits.common;

import org.cloudsimplus.cloudlets.network.NetworkCloudlet;

import java.util.ArrayList;

public class NetworkCloudletFixed extends NetworkCloudlet {
    private final long length;
    public NetworkCloudletFixed(long length, int pesNumber) {
        super(length, pesNumber);
        this.length = length;
    }

    public NetworkCloudletFixed(int id, long length, int pesNumber) {
        super(id, length, pesNumber);
        this.length = length;
    }

    @Override
    public boolean isFinished() {
        try {
            var tasksField = NetworkCloudlet.class.getDeclaredField("tasks");
            tasksField.setAccessible(true);
            if (tasksField.get(this) == null) {
                tasksField.set(this, new ArrayList<>());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        var finished = super.isFinished();
        System.out.println(finished);
        return finished;
    }
}
