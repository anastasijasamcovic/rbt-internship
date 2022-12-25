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
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
//
//
//@EnableWebSecurity
//@Configuration
//class SecurityConfig {
////    @Bean
////    fun encoder(): PasswordEncoder {
////        return BCryptPasswordEncoder()
////    }
////
////    @Throws(java.lang.Exception::class)
////    @Bean
////    fun configure(): InMemoryUserDetailsManager {
////        val user: UserDetails = User.builder()
////            .username("user")
////            .password("password")
////            .roles("USER")
////            .build()
////        return InMemoryUserDetailsManager(user)
////    }
////
////    @Bean
////    @Throws(Exception::class)
////    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
////        http
////            .authorizeHttpRequests(
////                Customizer { auth->
////                    auth.anyRequest().authenticated().and().httpBasic()
////                }
////            )
////            .httpBasic(withDefaults())
////        return http.build()
////    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        val filter: AbstractAuthenticationProcessingFilter = CustomizedAuthenticationFilter(authenticationManager())
//        filter.setFilterProcessesUrl("/api/login")
//        http.csrf().disable()
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/login").permitAll()
//        http.authorizeRequests().anyRequest().authenticated()
//        http.addFilter(filter)
//        return http.build()
//    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManager(): AuthenticationManager? {
//        return configuration.getAuthenticationManager()
//    }
//
//    @Autowired
//    @Throws(Exception::class)
//    fun configure(builder: AuthenticationManagerBuilder) {
//        builder.userDetailsService<UserDetailsService>(userService).passwordEncoder(BCryptPasswordEncoder())
//    }
//
//
//
//
//
//
//
////    @Bean
////    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
////        http {
////            securityMatcher("/api/**")
////            authorizeRequests {
////                authorize(anyRequest, hasRole("ADMIN"))
////            }
////            httpBasic { }
////        }
////        return http.build()
////    }
//
//
//
//}