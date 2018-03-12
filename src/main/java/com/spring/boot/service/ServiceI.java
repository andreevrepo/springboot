package com.spring.boot.service;

import java.util.List;
import java.util.Optional;

/**
 * Created by AndrewPC on 07.03.2018.
 * Time is: 19:15
 */
public interface ServiceI<E> {
    void add(E e);
    void delete(long id);
    void update(E e);
    Optional<E> get(long id);
    E get(String field);
    List<E> getAll();
}
