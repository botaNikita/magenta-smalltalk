package ru.magentasmalltalk.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.magentasmalltalk.TestConfiguration;
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.web.configurations.WebConfiguration;
import ru.magentasmalltalk.web.viewmodels.LoginFormViewModel;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class, WebConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class LoginControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UsersDAO usersDAO;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void loginFormViewTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/login")
        ).andExpect(status().isOk())
         .andExpect(view().name("users/login"))
         .andReturn();
    }

    @Test
    public void loginFormViewWithSessionTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/login")
                        .sessionAttr("userId", "test")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(request().sessionAttribute("userId", "test"))
                .andReturn();
    }

    @Test
    public void loginFormValidTest() throws Exception {
        User user = usersDAO.createUser("test-user", "test-password");

        LoginFormViewModel loginFormViewModel = new LoginFormViewModel();
        loginFormViewModel.setLogin("test-user");
        loginFormViewModel.setPassword("test-password");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .flashAttr("form", loginFormViewModel)
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(request().sessionAttribute("userId", user.getId()))
                .andReturn();
    }

    @Test
    public void loginFormInvalidTest() throws Exception {
        User user = usersDAO.createUser("test-user", "test-password");

        LoginFormViewModel loginFormViewModel = new LoginFormViewModel();
        loginFormViewModel.setLogin("test-user");
        loginFormViewModel.setPassword("wrong-password");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .flashAttr("form", loginFormViewModel)
        ).andExpect(status().isOk())
                .andExpect(view().name("users/login"))
                .andReturn();
    }

    @Test
    public void loginFormAlreadyLoggedInTest() throws Exception {
        User user = usersDAO.createUser("test-user", "test-password");

        LoginFormViewModel loginFormViewModel = new LoginFormViewModel();
        loginFormViewModel.setLogin("test-user");
        loginFormViewModel.setPassword("test-password");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .flashAttr("form", loginFormViewModel)
                        .sessionAttr("userId", "test")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(request().sessionAttribute("userId", "test"))
                .andReturn();
    }
}
