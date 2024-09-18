package com.allane.vehicleleasingbackend.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.tuple.Pair;

public class Functionals {

    private Functionals() {
    }

    /**
     * Allow the creation of array literals from expressions. Use <code>a(1, 2, 3, 4)</code> instead of
     * <code>new Integer[] { 1, 2, 3, 4 }</code>.
     */
    @SafeVarargs
    public static <A> A[] a(A... a) {
        return a;
    }

    public static <T, K> Map<K, T> lookupOf(Stream<T> items, Function<T, K> keyMapper) {
        return items.collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <T, K> Map<K, T> lookupOf(Stream<T> items, Function<T, K> keyMapper,
            BinaryOperator<T> mergeFunction) {
        return items.collect(Collectors.toMap(keyMapper, Function.identity(), mergeFunction));
    }

    public static <T, K> Map<K, T> lookupOf(Iterable<T> items, Function<T, K> keyMapper) {
        return lookupOf(StreamSupport.stream(items.spliterator(), false), keyMapper);
    }

    public static <T, K> Map<K, T> lookupOf(Iterable<T> items, Function<T, K> keyMapper,
            BinaryOperator<T> mergeFunction) {
        return lookupOf(StreamSupport.stream(items.spliterator(), false), keyMapper, mergeFunction);
    }

    /**
     * Convenience method for easily mapping items into a list.
     */
    public static <T, R> List<R> mapItems(Stream<T> items, Function<T, R> itemMapper) {
        return items.map(itemMapper).collect(Collectors.toList());
    }

    /**
     * Convenience method for easily mapping items into a list.
     */
    public static <T, R> List<R> mapItems(Iterable<T> items, Function<T, R> itemMapper) {
        return mapItems(StreamSupport.stream(items.spliterator(), false), itemMapper);
    }

    public static <R> List<R> mapItems(IntStream items, IntFunction<R> itemMapper) {
        return items.mapToObj(itemMapper).collect(Collectors.toList());
    }

    public static <R> List<R> mapItems(int[] items, IntFunction<R> itemMapper) {
        return mapItems(Arrays.stream(items), itemMapper);
    }

    /**
     * Map value merge function that always uses the old value. Use this with
     * {@link Collectors#toMap(Function, Function, BinaryOperator)}.
     */
    public static <T> BinaryOperator<T> oldestWinsMerger() {
        return (lhs, rhs) -> lhs;
    }

    /**
     * Map value merge function that always uses the new value. Use this with
     * {@link Collectors#toMap(Function, Function, BinaryOperator)}.
     */
    public static <T> BinaryOperator<T> newestWinsMerger() {
        return (lhs, rhs) -> rhs;
    }

    /**
     * Convenience method for easily mapping items into a set.
     */
    public static <T, R> Set<R> mapItemsAsSet(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    /**
     * Convenience method to get a list of distinct objects, where the distinct object is evaluated based on the
     * provided {@code distinctFunction}.
     * <p>
     * Two objects are treated as distinct if the provided {@code distinctFunction} returns different values when
     * applied to each of the two objects
     */
    public static <T, R> Function<List<T>, List<T>> distinctList(Function<T, R> distinctFunction) {
        return source -> {
            Map<R, T> mappedValues = lookupOf(source, distinctFunction, Functionals.oldestWinsMerger());
            return new ArrayList<>(mappedValues.values());
        };
    }

    /**
     * Stream filter operator to allow conditional distinct().
     *
     * @see https://stackoverflow.com/a/27872852
     */
    public static <T> Predicate<T> distinctBy(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = ConcurrentHashMap.newKeySet();
        return item -> seen.add(keyExtractor.apply(item));
    }

    /**
     * Convenience method for testing that all values of given items match.
     */
    public static <T, V> boolean allValuesMatch(Iterable<T> items, Function<T, V> accessor) {
        final ValueHolder<V> holder = new ValueHolder<>();
        return StreamSupport.stream(items.spliterator(), false).map(accessor).allMatch(value -> {
            if (holder.isSet()) {
                return Objects.equals(holder.value(), value);
            } else {
                holder.setValue(value);
                return true;
            }
        });
    }

    /**
     * Convenience method for testing that no values of given items match.
     */
    public static <T, V> boolean noValuesMatch(Iterable<T> items, Function<T, V> accessor) {
        final Set<V> seenValues = new HashSet<>();
        return StreamSupport.stream(items.spliterator(), false).map(accessor).allMatch(seenValues::add);
    }

    static final class ValueHolder<T> {

        private boolean valueSet = false;
        private T value = null;

        public boolean isSet() {
            return valueSet;
        }

        public void setValue(T value) {
            this.value = value;
            valueSet = true;
        }

        public T value() {
            return value;
        }
    }

    /**
     * Stream collector producing a list with its items ordered by their natural ordering.
     */
    public static <T extends Comparable<T>> Collector<T, ?, List<T>> toListOrdered() {
        return toListOrdered(Comparator.naturalOrder());
    }

    /**
     * Stream collector producing a list with its items ordered by the passed comparator.
     */
    public static <T> Collector<T, ?, List<T>> toListOrdered(Comparator<? super T> comparator) {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            list.sort(comparator);
            return list;
        });
    }

    /**
     * Like {@link List#equals(Object)} but supporting a custom comparator.
     */
    public static <T> boolean listsEqual(List<T> lhs, List<T> rhs, Comparator<T> comparator) {
        if (lhs == rhs) {
            return true;
        } else if (lhs == null || rhs == null || lhs.size() != rhs.size()) {
            return false;
        }

        var lhsIt = lhs.listIterator();
        var rhsIt = rhs.listIterator();
        while (lhsIt.hasNext() && rhsIt.hasNext()) {
            T lhsVal = lhsIt.next();
            T rhsVal = rhsIt.next();
            if (lhsVal != rhsVal && comparator.compare(lhsVal, rhsVal) != 0) {
                return false;
            }
        }
        return !(lhsIt.hasNext() || rhsIt.hasNext());
    }

    /**
     * Group {@code Iterable<T> source} by key resulted from the {@code Function<T, K> function}
     */
    public static <T, K> Map<K, List<T>> groupBy(Iterable<T> source, Function<T, K> function) {
        return StreamSupport.stream(source.spliterator(), false)
                .collect(Collectors.groupingBy(function, Collectors.toList()));
    }

    /**
     * Safe wrap {@code item} in a list. If {@code item} is null then an empty immutable {@code List<T>} will be
     * returned
     */
    public static <T> List<T> asList(T item) {
        return item == null
                ? List.of()
                : List.of(item);
    }

    /**
     * filters the items stream according to the Predicate itemFilter and returns the result as an List
     */
    public static <T> List<T> filterItems(Stream<T> items, Predicate<T> itemFilter) {
        return items.filter(itemFilter).collect(Collectors.toList());
    }

    /**
     * uses the method filterItems to filter an Iterable of T with the given Filter
     */
    public static <T> List<T> filterItems(Iterable<T> items, Predicate<T> itemFilter) {
        return filterItems(StreamSupport.stream(items.spliterator(), false), itemFilter);
    }

    /**
     * uses the method filterItems to filter an an amount of T items with the given Filter
     */
    @SafeVarargs
    public static <T> List<T> filterItemsBy(Predicate<T> itemFilter, T... items) {
        return filterItems(Arrays.stream(items), itemFilter);
    }

    /**
     * Provide a collector that creates a map having as keys the result of the {@code keyMapper} function and having
     * always only one value for each key.
     * <p/>
     * If many values with the same key will be found, only the old value will be returned
     */
    public static <T, K> Collector<T, ?, Map<K, T>> toUniqueMap(Function<T, K> keyMapper) {
        return Collectors.toMap(keyMapper, Function.identity(), Functionals.oldestWinsMerger());
    }

    /**
     * Combine all items from {@code source} with each other and return first not null and not empty result from applied
     * {@code function} <br>
     * <br>
     *
     * @return Optional.empty() if {@code function} doesn't provide any result
     */
    public static <T, E> Optional<E> findCombining(List<T> source, BiFunction<T, T, Optional<E>> function) {
        for (int i = 0; i < source.size() - 1; i++) {
            T left = source.get(i);
            for (int j = i + 1; j < source.size(); j++) {
                T right = source.get(j);

                Optional<E> result = function.apply(left, right);
                if (result != null && result.isPresent()) {
                    return result;
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Determine the presence of items in either of the passed collections.
     *
     * @param lhsColl    left-hand side of items to compare.
     * @param rhsColl    right-hand side of items to compare.
     * @param comparator comparator to detect if two items are equal wrt determining their presence in the collections.
     * @return a map containing lists of those items that are present either on only the {@link ItemPresence#LEFT},
     * only on the {@link ItemPresence#RIGHT} or on {@link ItemPresence#BOTH} sides.
     */
    public static <T> Map<ItemPresence, List<T>> determinePresence(Collection<T> lhsColl, Collection<T> rhsColl,
            Comparator<T> comparator) {
        final List<T> inBoth = new ArrayList<>();
        final List<T> inLeft = new ArrayList<>();
        final List<T> inRight = new ArrayList<>(rhsColl);

        if (rhsColl.isEmpty()) {
            inLeft.addAll(lhsColl);
        } else {
            label_outerLoop:
            for (T lhs : lhsColl) {
                // re-impl Collection.contains with custom comparator and smart found removal
                for (Iterator<T> rhsIter = inRight.iterator(); rhsIter.hasNext(); ) {
                    T rhs = rhsIter.next();
                    if (comparator.compare(lhs, rhs) == 0) {
                        inBoth.add(lhs);
                        rhsIter.remove();
                        continue label_outerLoop;
                    }
                }
                inLeft.add(lhs);
            }
        }

        final Map<ItemPresence, List<T>> result = new EnumMap<>(ItemPresence.class);
        result.put(ItemPresence.LEFT, inLeft);
        result.put(ItemPresence.RIGHT, inRight);
        result.put(ItemPresence.BOTH, inBoth);
        return result;
    }

    /**
     * Collector for finding the min/max values of ranges.
     *
     * @param minVal      the absolute minimum value, serves as starting value for upper bound.
     * @param lowerMapper accessor for the lower bound of an item.
     * @param maxVal      the absolute maximum value, serves as starting value for lower bound.
     * @param upperMapper accessor for the upper bound of an item.
     * @param <T>         the item type to process.
     * @param <R>         the range value type to find min/max on.
     * @return the found min/max values of all collected items.
     */
    public static <T, R extends Comparable<? super R>> Collector<T, ?, Optional<Pair<R, R>>> minMaxOf(
            R minVal, Function<T, R> lowerMapper,
            R maxVal, Function<T, R> upperMapper) {
        return Collector.of(
                () -> new MinMaxCalc<>(maxVal, minVal), // CAUTION: swapped initialisation
                (result, curr) -> result.update(lowerMapper.apply(curr), upperMapper.apply(curr)),
                MinMaxCalc::update,
                result -> result.getCount() == 0
                        ? Optional.empty()
                        : Optional.of(Pair.of(result.getMinValue(), result.getMaxValue())));
    }

    private static class MinMaxCalc<T extends Comparable<? super T>> {
        private long count;
        private T minValue;
        private T maxValue;

        public MinMaxCalc(T minValue, T maxValue) {
            count = 0;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public long getCount() {
            return count;
        }

        public T getMinValue() {
            return minValue;
        }

        public T getMaxValue() {
            return maxValue;
        }

        public void update(T modMin, T modMax) {
            update(1, modMin, modMax);
        }

        public MinMaxCalc<T> update(MinMaxCalc<T> other) {
            update(other.count, other.minValue, other.maxValue);
            return this;
        }

        private void update(long modCount, T modMin, T modMax) {
            count += modCount;
            minValue = minValue.compareTo(modMin) <= 0
                    ? minValue
                    : modMin;
            maxValue = maxValue.compareTo(modMax) >= 0
                    ? maxValue
                    : modMax;
        }
    }
}
