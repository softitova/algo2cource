package veb;

public interface IntegerSet {
    int NO = -1;

    void add(long x);

    void remove(long x);

    long next(long x);

    long prev(long x);

    long getMin();

    long getMax();
}

