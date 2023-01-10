package rs.rbt.internship.admin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import rs.rbt.internship.admin.config.filter.ApiKeyAuthenticationFilter
import rs.rbt.internship.admin.constants.SecurityConstants.Companion.API_KEY_HEADER
import rs.rbt.internship.admin.constants.SecurityConstants.Companion.API_KEY_VALUE
import rs.rbt.internship.admin.constants.SecurityConstants.Companion.BAD_CREDENTIALS


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain?{
        val filter = ApiKeyAuthenticationFilter(API_KEY_HEADER)
        filter.setAuthenticationManager { authentication ->
            val principal = authentication.principal as String
            println(principal)
            if (principal != API_KEY_VALUE) {
                throw BadCredentialsException(BAD_CREDENTIALS)
            }
            authentication.isAuthenticated = true
            authentication
        }

        http.csrf().disable()
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated()
            }
            .addFilter(filter)

        return http.build()
    }

}



