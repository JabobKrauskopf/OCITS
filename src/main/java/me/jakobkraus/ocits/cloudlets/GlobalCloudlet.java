package me.jakobkraus.ocits.cloudlets;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.cloudlets.CloudletSimple;
import org.cloudsimplus.utilizationmodels.UtilizationModel;

public class GlobalCloudlet extends CloudletSimple {
    protected Country country;

    public GlobalCloudlet(long length, int pesNumber, Country country, UtilizationModel utilizationModel) {
        super(length, pesNumber, utilizationModel);
        this.country = country;
    }

    public GlobalCloudlet(long length, int pesNumber, Country country) {
        super(length, pesNumber);
        this.country = country;
    }

    public GlobalCloudlet(long length, long pesNumber, Country country) {
        super(length, pesNumber);
        this.country = country;
    }

    public GlobalCloudlet(long id, long length, long pesNumber, Country country) {
        super(id, length, pesNumber);
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }
}
