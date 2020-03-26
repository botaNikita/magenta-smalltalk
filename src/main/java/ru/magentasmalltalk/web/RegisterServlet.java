package ru.magentasmalltalk.web;

import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;
import ru.magentasmalltalk.web.viewmodels.RegistrationFormViewModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("");
        registrationFormViewModel.setPassword("");
        registrationFormViewModel.setName("");
        List<UserRoles> userRoles = new LinkedList<>();
        userRoles.add(UserRoles.ADMIN);
        userRoles.add(UserRoles.USER);
        registrationFormViewModel.setUserRoles(userRoles);
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        req.setAttribute("form", registrationFormViewModel);

        req.getRequestDispatcher("/pages/users/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getSession().getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String role = req.getParameter("role");

        if (login != null && password != null) {

            EntityManager manager = PersistenceUtils.createManager(req.getServletContext());
            User user;

            try {
                UsersDAO usersDAO = new UsersDAO(manager);
                user = usersDAO.createUser(login, password, name, UserRoles.valueOf(role));
            } finally {
                manager.close();
            }

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


