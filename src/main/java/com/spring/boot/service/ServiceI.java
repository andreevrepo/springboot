package com.spring.boot.service;

import java.util.List;
import java.util.Optional;

/*
    Сервис включающий в себя общую логику для crud операций из DAO
 */
public interface ServiceI<E> {
    void add(E e);
    void delete(long id);
    void update(E e);
    Optional<E> get(long id);
    E get(String field);
    List<E> getAll();
}
