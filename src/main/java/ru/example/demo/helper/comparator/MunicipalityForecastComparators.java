package ru.example.demo.helper.comparator;

import ru.example.demo.domain.MunicipalityForecast;
import ru.example.demo.helper.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


public final class MunicipalityForecastComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<MunicipalityForecast> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<MunicipalityForecast>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(MunicipalityForecast::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(MunicipalityForecast::getId)
                                                           .reversed());

    }

    public static Comparator<MunicipalityForecast> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<MunicipalityForecast> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private MunicipalityForecastComparators() {
    }
}