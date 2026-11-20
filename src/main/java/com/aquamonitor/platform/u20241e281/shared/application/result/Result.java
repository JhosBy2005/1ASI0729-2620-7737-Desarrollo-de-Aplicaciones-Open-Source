package com.aquamonitor.platform.u20241e281.shared.application.result;

import java.util.Optional;
import java.util.function.Function;

/**
 * Generic application-layer outcome wrapper: either a {@link Success} with a value
 * or a {@link Failure} with error information.
 *
 * @param <T> success value type
 * @param <E> failure value type
 */
public sealed interface Result<T, E> permits Result.Success, Result.Failure {

    /**
     * Creates a successful result.
     *
     * @param value success value
     * @param <T>   success value type
     * @param <E>   failure value type
     * @return success result
     */
    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    /**
     * Creates a failed result.
     *
     * @param error failure value
     * @param <T>   success value type
     * @param <E>   failure value type
     * @return failure result
     */
    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    /**
     * Checks whether this result is successful.
     *
     * @return {@code true} when this result is successful
     */
    default boolean isSuccess() {
        return this instanceof Success<T, E>;
    }

    /**
     * Checks whether this result is a failure.
     *
     * @return {@code true} when this result is a failure
     */
    default boolean isFailure() {
        return this instanceof Failure<T, E>;
    }

    /**
     * Gets the success value, if any.
     *
     * @return the success value, or an empty optional when this is a failure
     */
    default Optional<T> success() {
        return this instanceof Success<T, E>(T value) ? Optional.ofNullable(value) : Optional.empty();
    }

    /**
     * Gets the failure value, if any.
     *
     * @return the failure value, or an empty optional when this is a success
     */
    default Optional<E> failure() {
        return this instanceof Failure<T, E>(E error) ? Optional.ofNullable(error) : Optional.empty();
    }

    /**
     * Folds this result into a single value.
     *
     * @param onSuccess mapper for a successful value
     * @param onFailure mapper for a failure value
     * @param <R>       folded return type
     * @return the folded value
     */
    default <R> R fold(Function<? super T, ? extends R> onSuccess, Function<? super E, ? extends R> onFailure) {
        return switch (this) {
            case Success<T, E>(T value) -> onSuccess.apply(value);
            case Failure<T, E>(E error) -> onFailure.apply(error);
        };
    }

    /**
     * Successful result.
     *
     * @param value success value
     * @param <T>   success value type
     * @param <E>   failure value type
     */
    record Success<T, E>(T value) implements Result<T, E> {
    }

    /**
     * Failed result.
     *
     * @param error failure value
     * @param <T>   success value type
     * @param <E>   failure value type
     */
    record Failure<T, E>(E error) implements Result<T, E> {
    }
}
