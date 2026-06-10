package edu.phystech.hw3.result;

import java.util.function.Function;

/**
 * @author kzlv4natoly
 */
public final class Success<T> implements Result<T> {
    private final T value;
    public Success(T value) {
        this.value = value;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public T getOrDefault(T defaultValue) {
        return value;
    }

    @Override
    public Throwable getExceptionOrNull() {
        return null;
    }

    @Override
    public <R> Result<R> map(Function<T, R> transform) {
        return new Success<>(transform.apply(value));
    }

}
