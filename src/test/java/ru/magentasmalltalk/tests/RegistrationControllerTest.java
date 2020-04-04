package ru.magentasmalltalk.tests;

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
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.UserRoles;
import ru.magentasmalltalk.web.configurations.WebConfiguration;
import ru.magentasmalltalk.web.viewmodels.RegistrationFormViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void registrationFormTest() throws Exception {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("");
        registrationFormViewModel.setPassword("");
        registrationFormViewModel.setName("");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/register")
        ).andExpect(status().isOk())
                .andExpect(model().attribute("form", registrationFormViewModel))
                .andExpect(view().name("users/register"))
                .andReturn();
    }
}
