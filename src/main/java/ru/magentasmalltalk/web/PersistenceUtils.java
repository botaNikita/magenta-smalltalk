package ru.magentasmalltalk.web;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;

public class PersistenceUtils {
    public static EntityManager createManager(ServletContext context) {
        EntityManagerFactory factory = (EntityManagerFactory)context.getAttribute("factory");
        if (factory == null) {
            throw new IllegalStateException("No EntityManagerFactory found in the context");
        }
        return factory.createEntityManager();
    }
}
