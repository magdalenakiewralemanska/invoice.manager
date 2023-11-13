package my.project.invoicemanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfiguration {

    private static final String[] PUBLIC_URLS = {};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_URLS).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.OPTIONS).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE, "user/delete/**").hasAnyAuthority("DELETE:USER"));
        http.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.DELETE, "customer/delete/**").hasAnyAuthority("DELETE:CUSTOMER"));
        http.exceptionHandling(exception -> exception.accessDeniedHandler(null).authenticationEntryPoint(null));
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        return http.build();
    }
}
