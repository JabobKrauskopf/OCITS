package me.jakobkraus.ocits.global;

import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.List;

public class CountryCostMapping {
    private final MultiKeyMap<Country, Double> mapping;

    public CountryCostMapping() {
        this.mapping = new MultiKeyMap<>();

        mapping.put(Country.Germany, Country.USA, 0.1);
        mapping.put(Country.Germany, Country.China, 0.15);
        mapping.put(Country.Germany, Country.Australia, 0.22);
        mapping.put(Country.Germany, Country.Ukraine, 0.02);
        mapping.put(Country.Germany, Country.SouthAfrica, 0.1);
        mapping.put(Country.Germany, Country.Canada, 0.1);
        mapping.put(Country.Germany, Country.Mexico, 0.11);
        mapping.put(Country.Germany, Country.Brazil, 0.13);

        mapping.put(Country.USA, Country.China, 0.15);
        mapping.put(Country.USA, Country.Australia, 0.2);
        mapping.put(Country.USA, Country.Ukraine, 0.12);
        mapping.put(Country.USA, Country.SouthAfrica, 0.15);
        mapping.put(Country.USA, Country.Canada, 0.01);
        mapping.put(Country.USA, Country.Mexico, 0.01);
        mapping.put(Country.USA, Country.Brazil, 0.02);

        mapping.put(Country.China, Country.Australia, 0.07);
        mapping.put(Country.China, Country.Ukraine, 0.148);
        mapping.put(Country.China, Country.SouthAfrica, 0.1);
        mapping.put(Country.China, Country.Canada, 0.17);
        mapping.put(Country.China, Country.Mexico, 0.14);
        mapping.put(Country.China, Country.Brazil, 0.13);

        mapping.put(Country.Australia, Country.Ukraine, 0.18);
        mapping.put(Country.Australia, Country.SouthAfrica, 0.1);
        mapping.put(Country.Australia, Country.Canada, 0.21);
        mapping.put(Country.Australia, Country.Mexico, 0.19);
        mapping.put(Country.Australia, Country.Brazil, 0.18);

        mapping.put(Country.Ukraine, Country.SouthAfrica, 0.12);
        mapping.put(Country.Ukraine, Country.Canada, 0.12);
        mapping.put(Country.Ukraine, Country.Mexico, 0.13);
        mapping.put(Country.Ukraine, Country.Brazil, 0.14);

        mapping.put(Country.SouthAfrica, Country.Canada, 0.11);
        mapping.put(Country.SouthAfrica, Country.Mexico, 0.09);
        mapping.put(Country.SouthAfrica, Country.Brazil, 0.08);

        mapping.put(Country.Canada, Country.Mexico, 0.02);
        mapping.put(Country.Canada, Country.Brazil, 0.03);

        mapping.put(Country.Mexico, Country.Brazil, 0.01);
    }

    public double getCost(Country from, Country to) {
        if (from == to) {
            return .0;
        }

        var value = this.mapping.get(from, to);
        if (value == null) {
            return this.mapping.get(to, from);
        }

        return value;
    }

    public Country getClosestCountry(Country to, List<Country> comparison) {
        return comparison.stream().reduce(Country.Germany,
            (smallest, element) -> this.getCost(smallest, to) > this.getCost(element, to) ? element : smallest
        );
    }
}
