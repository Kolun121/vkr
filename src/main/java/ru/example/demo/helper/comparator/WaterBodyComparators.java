package ru.example.demo.helper.comparator;

import ru.example.demo.domain.WaterBody;
import ru.example.demo.helper.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


public final class WaterBodyComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<WaterBody> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<WaterBody>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(WaterBody::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(WaterBody::getId)
                                                           .reversed());

    }

    public static Comparator<WaterBody> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<WaterBody> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private WaterBodyComparators() {
    }
}