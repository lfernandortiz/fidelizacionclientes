package com.dromedicas.web.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dromedicas.view.beans.LoginBeanService;

/**
 * Servlet Filter implementation class LoginFileter
 */
@WebFilter("/*")
public class LoginFileter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFileter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		
		// Obtengo el bean que representa el usuario desde el scope session
		LoginBeanService loginBean = (LoginBeanService) req.getSession().getAttribute("loginService");

		// Proceso la URL que esta requiriendo el cliente
		String urlStr = req.getRequestURL().toString().toLowerCase();
		
		System.out.println("URL: " + urlStr);
		
		boolean noProteger = noProteger(urlStr);
		System.out.println(urlStr + " - desprotegido=[" + noProteger + "]");
		

		// Si no requiere proteccian continuo normalmente.
		if (noProteger(urlStr)) {
			chain.doFilter(request, response);
			return;
		}

		// El usuario no esta logueado
		if (loginBean == null || !loginBean.estaLogeado()) {
			res.sendRedirect(req.getContextPath() + "/login.xhtml");
			return;
		}

		// El recurso requiere proteccion, pero el usuario ya esta logueado.
		chain.doFilter(request, response);
	}
	
	
	private boolean noProteger(String urlStr) {

		/*
		 * Este es un buen lugar para colocar y programar todos los patrones que
		 * creamos convenientes para determinar cuales de los recursos no
		 * requieren proteccion. Sin duda que habra que crear un mecanizmo tal
		 * que se obtengan de un archivo de configuracion o algo que no requiera
		 * compilacion.
		 */
		if (urlStr.endsWith("login.xhtml"))
			return true;
		
		if (urlStr.indexOf("/javax.faces.resource/") != -1)
			return true;
		
		if (urlStr.indexOf("webservice") != -1)
			return true;
		
		return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
