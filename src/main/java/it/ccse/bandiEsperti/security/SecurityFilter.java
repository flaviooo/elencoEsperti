package it.ccse.bandiEsperti.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter{
	private String userSessionName;
	private String redirectUrl;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ServletRequest req, 
			ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        HttpSession session = request.getSession(false);
        
        if (session==null || session.getAttribute(this.userSessionName)==null) {
        	response.sendRedirect(this.redirectUrl);
        	return;
        }else{
            chain.doFilter(req, res);
        }
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.userSessionName = config.getInitParameter("userSessionName");
		this.redirectUrl = config.getInitParameter("redirectUrl");
		
		if (this.userSessionName==null || this.redirectUrl==null){
			throw new ServletException("Parametri filtro Sicurezza non validi");
		}
		
	}

}
