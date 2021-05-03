package es.neifi.clientservice.client.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

public class SimpleCorsFilter{


/*	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse httpResponse = (HttpServletResponse)response;
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		httpResponse.setHeader("Access-Control-Allow-Origin", "/*");//TODO cambiar en producccion real
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,OPTIONS,DELETE");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");

		if(HttpMethod.OPTIONS.name().equalsIgnoreCase(httpRequest.getMethod())) {
			httpResponse.setStatus(HttpStatus.OK.value());
		}else{
			chain.doFilter(request, response);
		};
	}*/
	
}
