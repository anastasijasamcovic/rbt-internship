package rs.rbt.internship.admin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import rs.rbt.internship.admin.config.filter.ApiKeyAuthenticationFilter
import rs.rbt.internship.admin.constants.SecurityConstants.Companion.BAD_CREDENTIALS


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Value("\${api-key-header}")
    private val API_KEY_HEADER: String = ""

    @Value("\${api-key-value}")
    private val API_KEY_VALUE: String = ""

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        val filter = ApiKeyAuthenticationFilter(API_KEY_HEADER)
        filter.setAuthenticationManager { authentication ->
            val principal = authentication.principal as String
            if (principal != API_KEY_VALUE) {
                throw BadCredentialsException(BAD_CREDENTIALS)
            }
            authentication.isAuthenticated = true
            authentication
        }

        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilter(filter)
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated()
            }

        return http.build()
    }

}



