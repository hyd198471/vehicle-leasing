package com.allane.vehicleleasingbackend.common;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Functionals {

    public static <T, R> List<R> mapItems(Stream<T> items, Function<T, R> itemMapper) {
        return items.map(itemMapper).collect(Collectors.toList());
    }

    /**
     * Convenience method for easily mapping items into a list.
     */
    public static <T, R> List<R> mapItems(Iterable<T> items, Function<T, R> itemMapper) {
        return mapItems(StreamSupport.stream(items.spliterator(), false), itemMapper);
    }
}
