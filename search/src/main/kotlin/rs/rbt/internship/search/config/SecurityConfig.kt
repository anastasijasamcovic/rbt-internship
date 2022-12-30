package rs.rbt.internship.search.config


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import rs.rbt.internship.data.service.implementation.EmployeeService


@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Autowired
    private lateinit var authProvider: CustomAuthenticationProvider

    @Bean
    @Throws(java.lang.Exception::class)
    fun authManager(http: HttpSecurity): AuthenticationManager? {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.authenticationProvider(authProvider)
        return authenticationManagerBuilder.build()
    }
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()
            .authorizeHttpRequests { auth ->
                auth.anyRequest().authenticated()
            }
            .httpBasic()
        return http.build()
    }

}

private fun AuthenticationManagerBuilder.authenticationProvider(authProvider: CustomAuthenticationProvider): CustomAuthenticationProvider {
    return CustomAuthenticationProvider()
}
