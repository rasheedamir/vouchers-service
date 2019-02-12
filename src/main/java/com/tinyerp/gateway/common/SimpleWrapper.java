package com.tinyerp.gateway.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = SimpleWrapperSerializer.class)
public abstract class SimpleWrapper<T> {

    protected final T value;

    protected SimpleWrapper(final T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SimpleWrapper<?> that = (SimpleWrapper<?>) o;

        return value.equals(that.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
