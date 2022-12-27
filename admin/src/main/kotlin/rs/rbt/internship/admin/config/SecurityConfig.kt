package rs.rbt.internship.admin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import rs.rbt.internship.admin.config.filter.ApiKeyAuthenticationFilter
import rs.rbt.internship.admin.config.token.ApiKeyAuthenticationToken


@Configuration
@EnableWebSecurity
class SecurityConfig {

//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        println("anaaaa")
//        http
//            .authorizeHttpRequests { auth ->
//                auth.anyRequest().hasRole("USER")
//            }
//            .addFilterBefore(APIKeyAuthenticationFilter())
//        return http.build()
//    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain?{
        val filter = ApiKeyAuthenticationFilter("api-key")
        filter.setAuthenticationManager { authentication ->
            val principal = authentication.principal as String
            println(principal)
            if (principal != "123abc") {
                throw BadCredentialsException("The API key was not found or not the expected value.")
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



