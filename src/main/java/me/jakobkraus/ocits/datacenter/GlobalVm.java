package me.jakobkraus.ocits.datacenter;

import me.jakobkraus.ocits.global.Country;
import org.cloudsimplus.schedulers.cloudlet.CloudletScheduler;
import org.cloudsimplus.vms.VmSimple;

public class GlobalVm extends VmSimple {
    private Country country;
    public GlobalVm(double mipsCapacity, long pesNumber, Country country) {
        super(mipsCapacity, pesNumber);
        this.country = country;
    }

    public GlobalVm(double mipsCapacity, long pesNumber, Country country, CloudletScheduler cloudletScheduler) {
        super(mipsCapacity, pesNumber, cloudletScheduler);
        this.country = country;
    }

    public GlobalVm(long id, double mipsCapacity, Country country, long pesNumber) {
        super(id, mipsCapacity, pesNumber);
        this.country = country;
    }

    public GlobalVm(long id, long mipsCapacity, Country country, long pesNumber) {
        super(id, mipsCapacity, pesNumber);
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }
}
