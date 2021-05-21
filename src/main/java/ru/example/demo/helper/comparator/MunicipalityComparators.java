package ru.example.demo.helper.comparator;

import ru.example.demo.domain.Municipality;
import ru.example.demo.helper.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


public final class MunicipalityComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<Municipality> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<Municipality>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(Municipality::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(Municipality::getId)
                                                           .reversed());

    }

    public static Comparator<Municipality> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<Municipality> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private MunicipalityComparators() {
    }
}