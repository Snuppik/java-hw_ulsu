package edu.phystech.hw3;

import java.util.List;
import java.util.ArrayList;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class Functional {

    public static <T, R> List<R> map(List<? extends T> collection, Function<? super T, ? extends R> function) {
        List<R> result = new ArrayList<>(collection.size());
        for (T element : collection) {
            result.add(function.apply(element));
        }
        return result;
    }

    public static <T> T reduce(List<T> collection, BinaryOperator<T> operator, T identity) {
        T result = identity;
        for (T element : collection) {
            result = operator.apply(result, element);
        }
        return result;
    }
}




