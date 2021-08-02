package spring.boot.hello.world.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // パスワードの暗号化用にbcryptを使用します
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を無視するパスを指定する
        web.ignoring().antMatchers("/css/**", "/img/**", "/webjars/**");
    }

    /**
     * リクエストに対する設定を行う.
     * @param http HttpSecurity
     * @throws Exception 例外
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // /loginをアクセス可能にする
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").and()
                // logout時のURLを指定
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()
                // Remember-Meの認証を許可することでブラウザを再度開いた場合にもログインしたままにできる
                .rememberMe();
    }

    /**
     * ユーザに関する設定を行う.
     * @param auth AuthenticationManagerBuilder
     * @throws Exception 例外
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_ADMIN").and()
                .withUser("user")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER");
    }
}
