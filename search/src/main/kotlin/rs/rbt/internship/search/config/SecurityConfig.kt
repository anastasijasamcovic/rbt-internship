//package rs.rbt.internship.search.config
//
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.HttpMethod
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
//
//
//@EnableWebSecurity
//@Configuration
//class SecurityConfig<UserDetails> {
//    @Bean
//    fun encoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    @Throws(java.lang.Exception::class)
//    @Bean
//    fun configure(): InMemoryUserDetailsManager {
//        val user: org.springframework.security.core.userdetails.UserDetails = User.builder()
//            .username("user1@rbt.rs")
//            .password("123")
//            .roles("USER")
//            .build()
//        return InMemoryUserDetailsManager(user)
//    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        http
//            .authorizeHttpRequests { auth ->
//                auth.anyRequest().authenticated().and()
//            }
//            .httpBasic()
//        return http.build()
//    }
//
//}