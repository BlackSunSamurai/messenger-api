package com.example.messenger.api.filters

import com.example.messenger.api.service.TokenAuthenticationService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws

class JWTAuthenticationFilter : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest?, res: ServletResponse?, filterChain: FilterChain) {
        val authentication = TokenAuthenticationService
            .getAuthentication(req as HttpServletRequest)
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(req, res)
    }
}