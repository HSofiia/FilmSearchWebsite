package be.kdg.film_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeHttpRequests(
            auths -> auths
                    .requestMatchers(regexMatcher("^/(home|films|actors|directors|extraFilmInfo|extraActorInfo)"))
                        .permitAll()
                    .requestMatchers(
                            antMatcher(HttpMethod.GET, "/js/**"),
                            antMatcher(HttpMethod.GET, "/css/**"),
                            antMatcher(HttpMethod.GET, "/webjars/**"),
                            regexMatcher(HttpMethod.GET, "\\.ico$"))
                        .permitAll()
                    .requestMatchers(
                            antMatcher(HttpMethod.GET, "/api/**"),
                            antMatcher(HttpMethod.GET, "/films/search"),
                            antMatcher(HttpMethod.GET, "/directors/search"),
                            antMatcher(HttpMethod.GET, "/actors/search")) // Permit access to /actors/search endpoint
                    .permitAll()
                    .requestMatchers(antMatcher(HttpMethod.GET, "/"))
                        .permitAll()
                    .anyRequest()
                        .authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                        .defaultSuccessUrl("/home")
                    .permitAll())
            .exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(
                    (request, response, exception) -> {
                        if (request.getRequestURI().startsWith("/api")) {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        } else {
                            response.sendRedirect(request.getContextPath() + "/login");
                        }
                    })
            );
        // @formatter:on
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
