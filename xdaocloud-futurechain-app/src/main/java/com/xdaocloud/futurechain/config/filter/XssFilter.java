package com.xdaocloud.futurechain.config.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(filterName = "xssFilter", urlPatterns = "/**")
public class XssFilter implements Filter {

    FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig)
        throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException,
        ServletException {
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)request), response);
    }

}