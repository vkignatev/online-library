package com.sber.java13spring.java13springproject.libraryproject.service;


import com.sber.java13spring.java13springproject.AuthorTestData;
import com.sber.java13spring.java13springproject.libraryproject.dto.AddBookDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.AuthorDTO;
import com.sber.java13spring.java13springproject.libraryproject.exception.MyDeleteException;
import com.sber.java13spring.java13springproject.libraryproject.mapper.AuthorMapper;
import com.sber.java13spring.java13springproject.libraryproject.model.Author;
import com.sber.java13spring.java13springproject.libraryproject.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AuthorServiceTest
      extends GenericTest<Author, AuthorDTO> {
    
    public AuthorServiceTest() {
        super();
        BookService bookService = Mockito.mock(BookService.class);
        repository = Mockito.mock(AuthorRepository.class);
        mapper = Mockito.mock(AuthorMapper.class);
        service = new AuthorService((AuthorRepository) repository, (AuthorMapper) mapper, bookService);
    }
    
    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(repository.findAll()).thenReturn(AuthorTestData.AUTHOR_LIST);
        Mockito.when(mapper.toDTOs(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);
        List<AuthorDTO> authorDTOS = service.listAll();
        log.info("Testing getAll(): " + authorDTOS);
        assertEquals(AuthorTestData.AUTHOR_LIST.size(), authorDTOS.size());
    }
    
    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        AuthorDTO authorDTO = service.getOne(1L);
        log.info("Testing getOne(): " + authorDTO);
        assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
    }
    
    @Order(3)
    @Test
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        AuthorDTO authorDTO = service.create(AuthorTestData.AUTHOR_DTO_1);
        log.info("Testing create(): " + authorDTO);
        assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
    }
    
    @Order(4)
    @Test
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        AuthorDTO authorDTO = service.update(AuthorTestData.AUTHOR_DTO_1);
        log.info("Testing update(): " + authorDTO);
        assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
    }
    
    @Order(5)
    @Test
    @Override
    protected void delete() throws MyDeleteException {
        Mockito.when(((AuthorRepository) repository).checkAuthorForDeletion(1L)).thenReturn(true);
//        Mockito.when(authorRepository.checkAuthorForDeletion(2L)).thenReturn(false);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        log.info("Testing delete() before: " + AuthorTestData.AUTHOR_1.isDeleted());
        service.deleteSoft(1L);
        log.info("Testing delete() after: " + AuthorTestData.AUTHOR_1.isDeleted());
        assertTrue(AuthorTestData.AUTHOR_1.isDeleted());
    }
    
    @Order(6)
    @Test
    @Override
    protected void restore() {
        AuthorTestData.AUTHOR_3.setDeleted(true);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_3)).thenReturn(AuthorTestData.AUTHOR_3);
        Mockito.when(repository.findById(3L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_3));
        log.info("Testing restore() before: " + AuthorTestData.AUTHOR_3.isDeleted());
        ((AuthorService) service).restore(3L);
        log.info("Testing restore() after: " + AuthorTestData.AUTHOR_3.isDeleted());
        assertFalse(AuthorTestData.AUTHOR_3.isDeleted());
    }
    
    @Order(7)
    @Test
    void searchAuthors() {
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "authorFio"));
        Mockito.when(((AuthorRepository) repository).findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse("authorFio1", pageRequest))
              .thenReturn(new PageImpl<>(AuthorTestData.AUTHOR_LIST));
        Mockito.when(mapper.toDTOs(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);
        Page<AuthorDTO> authorDTOList = ((AuthorService) service).searchAuthors("authorFio1", pageRequest);
        log.info("Testing searchAuthors(): " + authorDTOList);
        assertEquals(AuthorTestData.AUTHOR_DTO_LIST, authorDTOList.getContent());
    }
    
    @Order(8)
    @Test
    void addBook() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(service.getOne(1L)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        ((AuthorService) service).addBook(new AddBookDTO(1L, 1L));
        log.info("Testing addBook(): " + AuthorTestData.AUTHOR_DTO_1.getBooksIds());
        assertTrue(AuthorTestData.AUTHOR_DTO_1.getBooksIds().size() >= 1);
    }
    
    @Order(9)
    @Test
    protected void getAllNotDeleted() {
        AuthorTestData.AUTHOR_3.setDeleted(true);
        List<Author> authors = AuthorTestData.AUTHOR_LIST.stream().filter(Predicate.not(Author::isDeleted)).toList();
        Mockito.when(repository.findAllByIsDeletedFalse()).thenReturn(authors);
        Mockito.when(mapper.toDTOs(authors)).thenReturn(
              AuthorTestData.AUTHOR_DTO_LIST.stream().filter(Predicate.not(AuthorDTO::isDeleted)).toList());
        List<AuthorDTO> authorDTOS = service.listAllNotDeleted();
        log.info("Testing getAllNotDeleted(): " + authorDTOS);
        assertEquals(authors.size(), authorDTOS.size());
    }
    
}
