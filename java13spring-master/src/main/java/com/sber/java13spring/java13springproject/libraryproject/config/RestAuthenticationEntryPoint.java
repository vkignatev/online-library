package com.sber.java13spring.java13springproject.libraryproject.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint
      implements AuthenticationEntryPoint {
    
    private HandlerExceptionResolver resolverException;
    
    @Autowired
    public void setResolverException(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolverException = resolver;
    }
    
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
                                                                       ServletException {
        resolverException.resolveException(request, response, null, authException);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("Неавторизованная попытка выполнить запрещенный метод!");
    }
}
