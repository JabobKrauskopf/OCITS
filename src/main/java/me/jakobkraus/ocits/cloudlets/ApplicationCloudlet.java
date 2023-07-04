package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.application.Application;
import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class ApplicationCloudlet extends GlobalCloudlet {
    protected Application application;

    public ApplicationCloudlet(long length, int pesNumber, Country country, Application application, UtilizationModel utilizationModel) {
        super(length, pesNumber, country, utilizationModel);
        this.application = application;
    }

    public ApplicationCloudlet(long length, int pesNumber, Country country, Application application) {
        super(length, pesNumber, country);
        this.application = application;
    }

    public ApplicationCloudlet(long length, long pesNumber, Country country, Application application) {
        super(length, pesNumber, country);
        this.application = application;
    }

    public ApplicationCloudlet(long id, long length, long pesNumber, Country country, Application application) {
        super(id, length, pesNumber, country);
        this.application = application;
    }

    public Application getApplication() {
        return this.application;
    }
}
