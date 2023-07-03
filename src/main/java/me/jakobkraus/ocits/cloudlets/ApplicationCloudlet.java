package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class ApplicationCloudlet extends GlobalCloudlet{
    protected String applicationName;
    public ApplicationCloudlet(long length, int pesNumber, Country country, String applicationName, UtilizationModel utilizationModel) {
        super(length, pesNumber, country, utilizationModel);
        this.applicationName = applicationName;
    }

    public ApplicationCloudlet(long length, int pesNumber, Country country, String applicationName) {
        super(length, pesNumber, country);
        this.applicationName = applicationName;
    }

    public ApplicationCloudlet(long length, long pesNumber, Country country, String applicationName) {
        super(length, pesNumber, country);
        this.applicationName = applicationName;
    }

    public ApplicationCloudlet(long id, long length, long pesNumber, Country country, String applicationName) {
        super(id, length, pesNumber, country);
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return this.applicationName;
    }
}
