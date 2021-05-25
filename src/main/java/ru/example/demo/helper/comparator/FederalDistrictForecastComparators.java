package ru.example.demo.helper.comparator;

import ru.example.demo.domain.FederalDistrictForecast;
import ru.example.demo.helper.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


public final class FederalDistrictForecastComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<FederalDistrictForecast> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<FederalDistrictForecast>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(FederalDistrictForecast::getYear));
        map.put(new Key("id", Direction.desc), Comparator.comparing(FederalDistrictForecast::getYear)
                                                           .reversed());

    }

    public static Comparator<FederalDistrictForecast> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<FederalDistrictForecast> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private FederalDistrictForecastComparators() {
    }
}