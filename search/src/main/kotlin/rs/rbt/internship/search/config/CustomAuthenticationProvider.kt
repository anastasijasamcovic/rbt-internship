package rs.rbt.internship.search.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import rs.rbt.internship.data.service.implementation.EmployeeService

@Component
class CustomAuthenticationProvider: AuthenticationProvider {
    @Autowired
    private lateinit var employeeService: EmployeeService
    override fun authenticate(authentication: Authentication): Authentication? {
        val username: String = authentication.name
        val password: String = authentication.credentials.toString()

        val employee = employeeService.findEmployeeByEmail(username)
        return if(employee.password == password){
            UsernamePasswordAuthenticationToken(
                username, password, ArrayList()
            )
        } else{
            null
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}