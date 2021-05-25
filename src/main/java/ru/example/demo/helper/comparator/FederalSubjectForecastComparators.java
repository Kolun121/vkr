package ru.example.demo.helper.comparator;

import ru.example.demo.domain.FederalSubjectForecast;
import ru.example.demo.helper.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


public final class FederalSubjectForecastComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<FederalSubjectForecast> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<FederalSubjectForecast>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(FederalSubjectForecast::getYear));
        map.put(new Key("id", Direction.desc), Comparator.comparing(FederalSubjectForecast::getYear)
                                                           .reversed());

    }

    public static Comparator<FederalSubjectForecast> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<FederalSubjectForecast> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private FederalSubjectForecastComparators() {
    }
}