package edu.phystech.hw3;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InverseMapKeyValuesTest {

    public static <K, V> Map<V, Collection<K>> inverse(Map<? extends K, ? extends V> map) {
        Map<V, Collection<K>> result = new HashMap<>();
        map.forEach((key, value) -> result.computeIfAbsent(value, ignored -> new ArrayList<>()).add(key));
        return result;
    }

    @Test
    void noDuplicateValuesTest() {
        Map<Integer, String> originalMap = Map.of(1, "one", 2, "two");
        Map<String, Collection<Integer>> expectedMap = Map.of("one", List.of(1), "two", List.of(2));

        Map<String, Collection<Integer>> actualMap = inverse(originalMap);

        Assertions.assertEquals(expectedMap.size(), actualMap.size());

        expectedMap.forEach((key, value) -> {
            var entry = actualMap.get(key);
            Assertions.assertNotNull(entry);
            Assertions.assertEquals(new HashSet<>(value), new HashSet<>(entry));
        });

    }

    @Test
    void duplicateValuesTest() {
        Map<Integer, String> originalMap = Map.of(1, "one", 2, "two", 3, "two");
        Map<String, Collection<Integer>> expectedMap = Map.of("one", List.of(1), "two", List.of(2, 3));

        Map<String, Collection<Integer>> actualMap = inverse(originalMap);

        Assertions.assertEquals(expectedMap.size(), actualMap.size());

        expectedMap.forEach((key, value) -> {
            var entry = actualMap.get(key);
            Assertions.assertNotNull(entry);
            Assertions.assertEquals(new HashSet<>(value), new HashSet<>(entry));
        });

    }
}

