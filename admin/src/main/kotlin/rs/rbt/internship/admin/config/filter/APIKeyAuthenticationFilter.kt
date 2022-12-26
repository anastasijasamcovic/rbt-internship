package rs.rbt.internship.admin.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import rs.rbt.internship.admin.config.token.ApiKeyAuthenticationToken
import java.io.IOException
import java.util.*


class APIKeyAuthenticationFilter : jakarta.servlet.Filter {

    private val AUTH_METHOD = "api-key"

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        if (request is HttpServletRequest && response is HttpServletResponse) {
            val apiKey = getApiKey(request)
            if (apiKey != null) {
                if (apiKey == "my-valid-api-key") {
                    val apiToken = ApiKeyAuthenticationToken(apiKey, AuthorityUtils.NO_AUTHORITIES)
                    SecurityContextHolder.getContext().authentication = apiToken
                } else {
                    response.status = 401
                    response.writer.write("Invalid API Key")
                    return
                }
            }
        }
        chain.doFilter(request, response)
    }

    private fun getApiKey(httpRequest: HttpServletRequest): String? {
        var apiKey: String? = null
        var authHeader = httpRequest.getHeader("Authorization")
        if (authHeader != null) {
            authHeader = authHeader.trim { it <= ' ' }
            if (authHeader.lowercase(Locale.getDefault()).startsWith("$AUTH_METHOD ")) {
                apiKey = authHeader.substring(AUTH_METHOD.length).trim { it <= ' ' }
            }
        }
        return apiKey
    }


}