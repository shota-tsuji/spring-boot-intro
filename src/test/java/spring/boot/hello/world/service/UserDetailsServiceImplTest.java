package spring.boot.hello.world.service;

import static org.junit.jupiter.api.Assertions.*;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import spring.boot.hello.world.model.SiteUser;
import spring.boot.hello.world.repository.SiteUserRepository;
import spring.boot.hello.world.util.Role;

@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {

    @Autowired
    SiteUserRepository repository;

    @Autowired
    UserDetailsServiceImpl service;

    @Test
    @DisplayName("ユーザ名が存在するときユーザ名を返す")
    void whenUsernameExistsExceptToGetUserDetails() {
        String username = "Harada";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword("password");
        user.setEmail("harada@example.com");
        user.setRole(Role.USER.name());

        repository.save(user);
        UserDetails actual = service.loadUserByUsername(username);

        assertEquals(user.getUsername(), actual.getUsername());
    }

    @Test
    @DisplayName("ユーザ名が存在しない場合例外がスローされる")
    void WhenUsernameDoesNotExistsThrowException() {
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("Harada"));
    }
}