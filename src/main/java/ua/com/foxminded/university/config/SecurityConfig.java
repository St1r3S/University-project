package ua.com.foxminded.university.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.com.foxminded.university.model.user.UserRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/disciplines/**").hasAuthority(UserRole.ADMIN.getValue())
                .requestMatchers("/educators/**").hasAuthority(UserRole.ADMIN.getValue())
                .requestMatchers("/groups/**").hasAuthority(UserRole.ADMIN.getValue())
                .requestMatchers(HttpMethod.GET, "/schedule/*/delete/**", "/schedule/*/edit/*/*/delete/**").hasAuthority(UserRole.ADMIN.getValue())
                .requestMatchers(HttpMethod.GET, "/schedule/**").hasAnyAuthority(
                        UserRole.ADMIN.getValue(),
                        UserRole.STUDENT.getValue(),
                        UserRole.STAFF.getValue(),
                        UserRole.EDUCATOR.getValue()
                )
                .requestMatchers(HttpMethod.POST, "/schedule/**").hasAuthority(UserRole.ADMIN.getValue())
                .requestMatchers("/staff/**").hasAuthority(UserRole.ADMIN.getValue())
                .requestMatchers("/students/**").hasAuthority(UserRole.ADMIN.getValue())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login").permitAll()
                .defaultSuccessUrl("/auth/success")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                .logoutSuccessUrl("/auth/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/auth/forbidden")
                .and().build();
    }


    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
