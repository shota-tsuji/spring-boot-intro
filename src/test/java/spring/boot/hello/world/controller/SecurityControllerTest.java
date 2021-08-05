package spring.boot.hello.world.controller;

import static org.junit.jupiter.api.Assertions.*;
import javax.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import spring.boot.hello.world.model.SiteUser;
import spring.boot.hello.world.util.Role;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("登録エラーの場合エラーが表示される")
    void whenThereIsRegistrationErrorExpectToSeeErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
        .flashAttr("user", new SiteUser())
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    @DisplayName("管理者ユーザとして登録する場合成功する")
    void whenRegisteringAsAdminUserExpectToSucceed() throws Exception {
        SiteUser user = new SiteUser();
        user.setUsername("管理者ユーザ");
        user.setPassword("password");
        user.setEmail("admin@example.com");
        user.setGender(0);
        user.setRole(Role.ADMIN.name());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/register")
                    .flashAttr("user", user).with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?register"))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    @DisplayName("管理者ユーザでログインした場合ユーザ一覧が表示される")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void whenLoggedInAsAdminUserExpectToSeeListOfUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("ユーザ一覧")))
                .andExpect(MockMvcResultMatchers.view().name("list"));
    }
}