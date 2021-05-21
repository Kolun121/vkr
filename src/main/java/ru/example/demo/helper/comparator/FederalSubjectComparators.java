package ru.example.demo.helper.comparator;

import ru.example.demo.domain.FederalSubject;
import ru.example.demo.helper.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


public final class FederalSubjectComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<FederalSubject> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<FederalSubject>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(FederalSubject::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(FederalSubject::getId)
                                                           .reversed());

    }

    public static Comparator<FederalSubject> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<FederalSubject> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private FederalSubjectComparators() {
    }
}