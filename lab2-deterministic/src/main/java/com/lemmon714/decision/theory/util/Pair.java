package com.lemmon714.decision.theory.util;

public class Pair<T> {

    private final Object[] pair;

    public Pair(T left, T right) {
        this.pair = new Object[2];
        this.pair[0] = left;
        this.pair[1] = right;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) pair[index];
    }
}
