package com.fil.githubapiexample.adapter;

public interface RecyclerAdapter<T> {
    void add(int position, T item);

    void remove(int position);

    void update(int position, T item);
}
