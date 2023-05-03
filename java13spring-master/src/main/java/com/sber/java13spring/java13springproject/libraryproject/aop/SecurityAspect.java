package com.sber.java13spring.java13springproject.libraryproject.aop;

import com.sber.java13spring.java13springproject.libraryproject.annotation.MySecuredAnnotation;
import com.sber.java13spring.java13springproject.libraryproject.constants.Errors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
public class SecurityAspect {
    
    @Pointcut("@annotation(com.sber.java13spring.java13springproject.libraryproject.annotation.MySecuredAnnotation)")
    public void secureAnnotation() {
    }
    
    @Around("secureAnnotation()")
    public Object beforeCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature methodSignature = joinPoint.getSignature();
        MethodSignature signature = (MethodSignature) methodSignature;
        Method method = joinPoint.getTarget()
              .getClass()
              .getMethod(signature.getMethod().getName(),
                         signature.getMethod().getParameterTypes());
        MySecuredAnnotation mySecuredAnnotation = method.getAnnotation(MySecuredAnnotation.class);
        List<String> rolesFromAnnotation = Arrays.asList(mySecuredAnnotation.value());
        List<String> userRoles =
              SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (checkAuthority(rolesFromAnnotation, userRoles)) {
            throw new AccessDeniedException(Errors.REST.ACCESS_ERROR_MESSAGE);
        }
        return joinPoint.proceed();
    }
    
    private boolean checkAuthority(List<String> rolesFromAnnotation,
                                   List<String> userRoles) {
        return Collections.disjoint(rolesFromAnnotation, userRoles);
    }
    
}
