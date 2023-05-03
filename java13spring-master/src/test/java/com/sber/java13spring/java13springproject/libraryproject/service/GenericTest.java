package com.sber.java13spring.java13springproject.libraryproject.service;

import com.sber.java13spring.java13springproject.libraryproject.dto.GenericDTO;
import com.sber.java13spring.java13springproject.libraryproject.exception.MyDeleteException;
import com.sber.java13spring.java13springproject.libraryproject.mapper.GenericMapper;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;
import com.sber.java13spring.java13springproject.libraryproject.repository.GenericRepository;
import com.sber.java13spring.java13springproject.libraryproject.service.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

//TODO: https://habr.com/ru/post/444982/ - Mokito
public abstract class GenericTest<E extends GenericModel, D extends GenericDTO> {
    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;
    
    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(CustomUserDetails
                                                                                                           .builder()
                                                                                                           .username("USER"),
                                                                                                     null,
                                                                                                     null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    protected abstract void getAll();
    
    protected abstract void getOne();
    
    protected abstract void create();
    
    protected abstract void update();
    
    protected abstract void delete() throws MyDeleteException;
    
    protected abstract void restore();
    
    protected abstract void getAllNotDeleted();
}
