package ru.example.demo.helper.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import ru.example.demo.domain.MunicipalityType;
import ru.example.demo.helper.paging.Direction;

public final class MunicipalityTypeComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<MunicipalityType> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<MunicipalityType>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(MunicipalityType::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(MunicipalityType::getId)
                                                           .reversed());

    }

    public static Comparator<MunicipalityType> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<MunicipalityType> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private MunicipalityTypeComparators() {
    }
}