package ru.magentasmalltalk.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
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
import ru.magentasmalltalk.web.configurations.WebConfiguration;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter securityFilter;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(securityFilter)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
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
    @WithMockUser(username="test-user", roles = "USER")
    public void loginFormViewWithSessionTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/login")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andReturn();
    }

//    @Test
//    @Ignore
//    public void loginFormValidTest() throws Exception {
//        User user = usersDAO.createUser("test-user", "test-password");
//
//        LoginFormViewModel loginFormViewModel = new LoginFormViewModel();
//        loginFormViewModel.setLogin("test-user");
//        loginFormViewModel.setPassword("test-password");
//
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/login")
//                        .flashAttr("form", loginFormViewModel)
//        ).andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/"))
//                .andExpect(request().sessionAttribute("userId", user.getId()))
//                .andReturn();
//    }

//    @Test
//    @Ignore
//    public void loginFormInvalidTest() throws Exception {
//        User user = usersDAO.createUser("test-user", "test-password");
//
//        LoginFormViewModel loginFormViewModel = new LoginFormViewModel();
//        loginFormViewModel.setLogin("test-user");
//        loginFormViewModel.setPassword("wrong-password");
//
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/login")
//                        .flashAttr("form", loginFormViewModel)
//        ).andExpect(status().isOk())
//                .andExpect(view().name("users/login"))
//                .andReturn();
//    }

//    @Test
//    @Ignore
//    public void loginFormAlreadyLoggedInTest() throws Exception {
//        User user = usersDAO.createUser("test-user", "test-password");
//
//        LoginFormViewModel loginFormViewModel = new LoginFormViewModel();
//        loginFormViewModel.setLogin("test-user");
//        loginFormViewModel.setPassword("test-password");
//
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/login")
//                        .flashAttr("form", loginFormViewModel)
//                        .sessionAttr("userId", "test")
//        ).andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/"))
//                .andExpect(request().sessionAttribute("userId", "test"))
//                .andReturn();
//    }
}
