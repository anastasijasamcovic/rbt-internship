package rs.rbt.internship.admin.config.filter

import jakarta.servlet.http.HttpServletRequest

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter




class ApiKeyAuthenticationFilter(private val headerName: String) : AbstractPreAuthenticatedProcessingFilter() {
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any {
        return request.getHeader(headerName)
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any? {
        return null
    }
}