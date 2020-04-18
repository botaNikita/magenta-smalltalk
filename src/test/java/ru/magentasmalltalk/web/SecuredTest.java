package ru.magentasmalltalk.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.magentasmalltalk.TestConfiguration;
import ru.magentasmalltalk.web.configurations.WebConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class, WebConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class SecuredTest {

    @Autowired
    private AdminService adminService;

    @Test(expected = AccessDeniedException.class)
    @WithAnonymousUser
    public void banWithoutLogin() {
        adminService.banLatestUsers();
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void banWithLogin() {
        adminService.banLatestUsers();
    }
}
