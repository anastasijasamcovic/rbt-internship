//package rs.rbt.internship.admin.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.SecurityFilterChain
//import rs.rbt.internship.admin.config.filter.APIKeyAuthenticationFilter
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig {
//
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        http
//            .authorizeHttpRequests { auth ->
//                auth.anyRequest()
//            }
//            .addFilterBefore(APIKeyAuthenticationFilter())
//        return http.build()
//    }
//
//}
//
