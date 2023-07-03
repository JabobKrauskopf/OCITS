package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class DatabaseCloudlet extends ApplicationCloudlet {
    public DatabaseCloudlet(long length, int pesNumber, Country country, String applicationName, UtilizationModel utilizationModel) {
        super(length, pesNumber, country, applicationName, utilizationModel);
    }

    public DatabaseCloudlet(long length, int pesNumber, Country country, String applicationName) {
        super(length, pesNumber, country, applicationName);
    }

    public DatabaseCloudlet(long length, long pesNumber, Country country, String applicationName) {
        super(length, pesNumber, country, applicationName);
    }

    public DatabaseCloudlet(long id, long length, long pesNumber, Country country, String applicationName) {
        super(id, length, pesNumber, country, applicationName);
    }
}
