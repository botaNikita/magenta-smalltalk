package ru.magentasmalltalk.db;

import javax.persistence.EntityManager;
import java.util.Objects;

public class BaseDAO<T> {
    protected EntityManager manager;

    protected BaseDAO(EntityManager manager) {
        Objects.requireNonNull(manager, "Entity manager shouldn't be null");
        this.manager = manager;
    }

    protected T persist(T t){
        manager.getTransaction().begin();
        try {
            manager.persist(t);
        } catch (Throwable ex) {
            manager.getTransaction().rollback();
            throw ex;
        }
        manager.getTransaction().commit();
        return t;
    }
}
