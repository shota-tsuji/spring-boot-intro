package spring.boot.hello.world.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.boot.hello.world.model.SiteUser;
import spring.boot.hello.world.repository.SiteUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SiteUserRepository userRepository;

    public UserDetailsServiceImpl(SiteUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SiteUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return createUserDetails(user);
    }

    public User createUserDetails(SiteUser user) {
        // 権限を表す文字列"ROLE_XXX"を渡す
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
