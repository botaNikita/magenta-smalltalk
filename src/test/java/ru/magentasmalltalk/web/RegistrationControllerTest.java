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
import ru.magentasmalltalk.model.UserRoles;
import ru.magentasmalltalk.web.configurations.WebConfiguration;
import ru.magentasmalltalk.web.viewmodels.RegistrationFormViewModel;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class, WebConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class RegistrationControllerTest {
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
    public void registrationFormViewTest() throws Exception {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("");
        registrationFormViewModel.setPassword("");
        registrationFormViewModel.setName("");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/register")
        ).andExpect(status().isOk())
                .andExpect(model().attribute("form", registrationFormViewModel))
                .andExpect(view().name("users/register"))
                .andReturn();
    }

    @Test
    public void registrationFormViewWithSessionTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/register")
                        .sessionAttr("userId", "test")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(request().sessionAttribute("userId", "test"))
                .andReturn();
    }

    @Test
    public void registrationFormValidTest() throws Exception {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("TestUser");
        registrationFormViewModel.setPassword("qwerty123");
        registrationFormViewModel.setName("TestUser");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("form", registrationFormViewModel)
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(request().sessionAttribute("userName", "TestUser"))
                .andReturn();
    }

    @Test
    public void registrationFormNoLoginTest() throws Exception {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("");
        registrationFormViewModel.setPassword("qwerty123");
        registrationFormViewModel.setName("TestUser2");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("form", registrationFormViewModel)
        ).andExpect(status().isOk())
                .andExpect(view().name("users/register"))
                .andReturn();
    }

    @Test
    public void registrationFormNoPasswordTest() throws Exception {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("TestUser3");
        registrationFormViewModel.setPassword("");
        registrationFormViewModel.setName("TestUser3");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("form", registrationFormViewModel)
        ).andExpect(status().isOk())
                .andExpect(view().name("users/register"))
                .andReturn();
    }

    @Test
    public void registrationFormNoNameTest() throws Exception {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("TestUser4");
        registrationFormViewModel.setPassword("qwerty123");
        registrationFormViewModel.setName("");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("form", registrationFormViewModel)
        ).andExpect(status().isOk())
                .andExpect(view().name("users/register"))
                .andReturn();
    }

    @Test
    public void registrationFormAlreadyLoggedInTest() throws Exception {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("TestUser5");
        registrationFormViewModel.setPassword("qwerty123");
        registrationFormViewModel.setName("TestUser5");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("form", registrationFormViewModel)
                        .sessionAttr("userId", "test")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(request().sessionAttribute("userId", "test"))
                .andReturn();
    }
}
