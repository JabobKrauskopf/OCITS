package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class FrontendCloudlet extends ApplicationCloudlet {
    public FrontendCloudlet(long length, int pesNumber, Country country, String applicationName, UtilizationModel utilizationModel) {
        super(length, pesNumber, country, applicationName, utilizationModel);
    }

    public FrontendCloudlet(long length, int pesNumber, Country country, String applicationName) {
        super(length, pesNumber, country, applicationName);
    }

    public FrontendCloudlet(long length, long pesNumber, Country country, String applicationName) {
        super(length, pesNumber, country, applicationName);
    }

    public FrontendCloudlet(long id, long length, long pesNumber, Country country, String applicationName) {
        super(id, length, pesNumber, country, applicationName);
    }
}
