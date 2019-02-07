package com.example.jeremy.smartcalculator.utils.containers;

import java.util.List;

public interface IContainer<T> {
    void add(T t);
    T get(int position);
    List<T> getAll();
    void clear();
    int size();
}
