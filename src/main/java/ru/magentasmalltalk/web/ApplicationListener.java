package ru.magentasmalltalk.web;

import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProdPersistenceUnit");
        sce.getServletContext().setAttribute("factory", factory);

        EntityManager manager = factory.createEntityManager();
//        try {
            UsersDAO usersDAO = new UsersDAO(manager);
            User user = usersDAO.findUserByLogin("admin");
            if (user == null) {
                usersDAO.createUser("admin", "admin", "Admin", UserRoles.ADMIN);
            }
//        } finally {
//            manager.close();
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManagerFactory factory = (EntityManagerFactory)sce.getServletContext().getAttribute("factory");
        if (factory != null) {
            factory.close();
        }
    }
}
