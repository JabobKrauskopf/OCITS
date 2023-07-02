package me.jakobkraus.ocits.global;

import java.util.List;
import java.util.Map;

public class CountryCostMapping {
    private static final Map<Country, Map<Country, Double>> mapping =
            Map.of(
                    Country.Germany, Map.of(
                            Country.Germany, .0,
                            Country.USA, 0.1,
                            Country.China, 0.15,
                            Country.Australia, 0.3,
                            Country.Ukraine, 0.02,
                            Country.SouthAfrica, 0.1,
                            Country.Canada, 0.1,
                            Country.Mexico, 0.11,
                            Country.Brazil, 0.13
                    ),
                    Country.USA, Map.of(
                            Country.Germany, 0.1,
                            Country.USA, .0,
                            Country.China, 0.15,
                            Country.Australia, 0.2,
                            Country.Ukraine, 0.12,
                            Country.SouthAfrica, 0.15,
                            Country.Canada, 0.01,
                            Country.Mexico, 0.01,
                            Country.Brazil, 0.03
                    ),
                    Country.China, Map.of(
                            Country.Germany, 0.15,
                            Country.USA, 0.15,
                            Country.China, .0,
                            Country.Australia, 0.07,
                            Country.Ukraine, 0.148,
                            Country.SouthAfrica, 0.1,
                            Country.Canada, 0.17,
                            Country.Mexico, 0.14,
                            Country.Brazil, 0.13
                    ),
                    Country.Australia, Map.of(
                            Country.Germany, 0.2,
                            Country.USA, 0.2,
                            Country.China, 0.07,
                            Country.Australia, .0,
                            Country.Ukraine, 0.18,
                            Country.SouthAfrica, 0.1,
                            Country.Canada, 0.22,
                            Country.Mexico, 0.18,
                            Country.Brazil, 0.16
                    ),
                    Country.Ukraine, Map.of(
                            Country.Germany, 0.02,
                            Country.USA, 0.12,
                            Country.China, 0.148,
                            Country.Australia, 0.18,
                            Country.Ukraine, .0,
                            Country.SouthAfrica, 0.12,
                            Country.Canada, 0.12,
                            Country.Mexico, 0.13,
                            Country.Brazil, 0.14
                    ),
                    Country.SouthAfrica, Map.of(
                            Country.Germany, 0.1,
                            Country.USA, 0.15,
                            Country.China, 0.1,
                            Country.Australia, 0.1,
                            Country.Ukraine, 0.12,
                            Country.SouthAfrica, .0,
                            Country.Canada, 0.11,
                            Country.Mexico, 0.09,
                            Country.Brazil, 0.08
                    ),
                    Country.Canada, Map.of(
                            Country.Germany, 0.1,
                            Country.USA, 0.01,
                            Country.China, 0.17,
                            Country.Australia, 0.22,
                            Country.Ukraine, 0.12,
                            Country.SouthAfrica, 0.11,
                            Country.Canada, .0,
                            Country.Mexico, 0.02,
                            Country.Brazil, 0.03
                    ),
                    Country.Mexico, Map.of(
                            Country.Germany, 0.11,
                            Country.USA, 0.01,
                            Country.China, 0.14,
                            Country.Australia, 0.18,
                            Country.Ukraine, 0.13,
                            Country.SouthAfrica, 0.09,
                            Country.Canada, 0.02,
                            Country.Mexico, .0,
                            Country.Brazil, 0.01
                    ),
                    Country.Brazil, Map.of(
                            Country.Germany, 0.13,
                            Country.USA, 0.15,
                            Country.China, 0.13,
                            Country.Australia, 0.16,
                            Country.Ukraine, 0.14,
                            Country.SouthAfrica, 0.08,
                            Country.Canada, 0.03,
                            Country.Mexico, 0.01,
                            Country.Brazil, .0
                    )
            );

    public static double getCost(Country from, Country to) {
        return CountryCostMapping.mapping.get(from).get(to);
    }

    public static Country getClosestCountry(Country to, List<Country> comparison) {
        return comparison.stream().reduce(null,
                (smallest, element) ->
                        getCost(smallest, element) > getCost(to, element) ? to : smallest
        );
    }
}
