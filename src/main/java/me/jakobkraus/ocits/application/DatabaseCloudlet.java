package me.jakobkraus.ocits.application;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.cloudlets.CloudletSimple;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class DatabaseCloudlet extends CloudletSimple {
    public static int MAX_CONNECTIONS = 5;
    private int currentConnections = 0;
    private Country country;
    public DatabaseCloudlet(long length, int pesNumber, Country country, UtilizationModel utilizationModel) {
        super(length, pesNumber, utilizationModel);
        this.country = country;
    }

    public DatabaseCloudlet(long length, int pesNumber, Country country) {
        super(length, pesNumber);
        this.country = country;
    }

    public DatabaseCloudlet(long length, long pesNumber, Country country) {
        super(length, pesNumber);
        this.country = country;
    }

    public DatabaseCloudlet(long id, long length, long pesNumber, Country country) {
        super(id, length, pesNumber);
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }
}
