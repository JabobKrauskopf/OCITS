package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.application.Application;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class DatabaseCloudlet extends ApplicationCloudlet {
    public DatabaseCloudlet(long length, int pesNumber, Country country, Application application, UtilizationModel utilizationModel) {
        super(length, pesNumber, country, application, utilizationModel);
    }

    public DatabaseCloudlet(long length, int pesNumber, Country country, Application application) {
        super(length, pesNumber, country, application);
    }

    public DatabaseCloudlet(long length, long pesNumber, Country country, Application application) {
        super(length, pesNumber, country, application);
    }

    public DatabaseCloudlet(long id, long length, long pesNumber, Country country, Application application) {
        super(id, length, pesNumber, country, application);
    }
}
