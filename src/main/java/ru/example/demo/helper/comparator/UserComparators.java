package ru.example.demo.helper.comparator;

import ru.example.demo.domain.User;
import ru.example.demo.helper.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


public final class UserComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }
    
    static final Comparator<User> EMPTY_COMPARATOR = (e1, e2) -> 0;

    static Map<Key, Comparator<User>> map = new HashMap<>();
    

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(User::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(User::getId)
                                                           .reversed());

    }

    public static Comparator<User> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
    
    public static Comparator<User> getEmptyComparator() {
        return EMPTY_COMPARATOR;
    }

    private UserComparators() {
    }
}