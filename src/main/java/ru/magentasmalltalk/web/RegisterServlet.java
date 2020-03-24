package ru.magentasmalltalk.web;

import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        req.getRequestDispatcher("/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String role = req.getParameter("role");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        EntityManager manager = factory.createEntityManager();
        UsersDAO usersDAO = new UsersDAO(manager);

        if (login != null && password != null) {
            User user = usersDAO.createUser(login, password, name, UserRoles.valueOf(role));
            if (user == null) {
                resp.sendRedirect("register");
                return;
            }
            req.getSession().setAttribute("userName", user.getName());
            req.getSession().setAttribute("userId", user.getId());
            req.getSession().setAttribute("isAdmin", user.getRole() == UserRoles.ADMIN);
            resp.sendRedirect(req.getContextPath());
        } else {
            resp.sendRedirect("register");
        }
    }
}


