package pv243.peaktogether.filters;

import org.picketlink.Identity;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 17.6.13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    @Inject
    private Identity identity;

    private Set<String> allowedPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedPages = new HashSet<String>();
        allowedPages.add("/login.jsf");
        allowedPages.add("/register.jsf");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest)servletRequest).getServletPath();
        String context = ((HttpServletRequest)servletRequest).getContextPath();

        if(identity.isLoggedIn() && allowedPages.contains(path)) {
            ((HttpServletResponse)servletResponse).sendRedirect(context + "/login.jsf");
        } else if (!identity.isLoggedIn()) {
            if(!path.startsWith("/javax.faces.resource") && !allowedPages.contains(path)) {
                ((HttpServletResponse)servletResponse).sendRedirect(context + "/login.jsf");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        allowedPages.clear();
        allowedPages = null;
    }
}
