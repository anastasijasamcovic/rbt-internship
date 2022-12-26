package rs.rbt.internship.search.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class SecurityConfig<UserDetails> {
    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(java.lang.Exception::class)
    @Bean
    fun configure(): InMemoryUserDetailsManager {
        val user: org.springframework.security.core.userdetails.UserDetails = User.builder()
            .username("user1@rbt.rs")
            .password(encoder().encode("123"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests { auth ->
                auth.anyRequest().hasRole("USER")
            }
            .httpBasic()
        return http.build()
    }

}