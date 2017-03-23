package veb;

public interface IntegerSet {
    int NO = -1;

    void add(int x);

    void remove(int x);

    int next(int x);

    int prev(int x);

    int getMin();

    int getMax();
}

