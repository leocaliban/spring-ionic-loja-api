package com.leocaliban.loja.api.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * Classe responsável pela exposição do header location

 * @author Leocaliban
 *
 * 4 de ago de 2018
 */
@Component
public class HeaderExposureFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}

	@Override
	//Intercepta todas as requisições, expõe o header location e retoma o ciclo da requisição
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse r = (HttpServletResponse) response;
		r.addHeader("access-control-expose-headers", "location");
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {

	}

}
