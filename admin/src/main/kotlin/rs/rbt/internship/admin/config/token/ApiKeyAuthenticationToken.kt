package rs.rbt.internship.admin.config.token

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class ApiKeyAuthenticationToken(private val apiKey: String, authorities: MutableCollection<out GrantedAuthority>?) : AbstractAuthenticationToken(
    authorities
) {
    override fun setAuthenticated(authenticated: Boolean) {
        super.setAuthenticated(true)
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): String {
        return apiKey
    }
}