package me.jakobkraus.ocits.logging;

import me.jakobkraus.ocits.cloudlets.FunctionCloudlet;
import me.jakobkraus.ocits.cloudlets.FunctionStatus;
import me.jakobkraus.ocits.datacenter.GlobalDatacenter;

public class FunctionPayload {
    private final FunctionCloudlet cloudlet;
    private final FunctionStatus status;
    private final double time;

    public FunctionPayload(FunctionCloudlet cloudlet, FunctionStatus status, double time) {
        this.cloudlet = cloudlet;
        this.status = status;
        this.time = time;
    }

    public static String getCsvHeader() {
        return String.join(",",
            "cloudletId",
            "application",
            "expectedCountry",
            "actualCountry",
            "status",
            "time"
        );
    }

    @Override
    public String toString() {
        return String.join(",",
            String.valueOf(cloudlet.getId()),
            cloudlet.getApplication().getApplicationName(),
            cloudlet.getCountry().toString(),
            ((GlobalDatacenter) cloudlet.getVm().getHost().getDatacenter()).getCountry().toString(),
            status.toString(),
            String.valueOf(time)
        );
    }
}
