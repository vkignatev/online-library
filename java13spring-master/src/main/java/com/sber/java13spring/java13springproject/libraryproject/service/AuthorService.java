package com.sber.java13spring.java13springproject.libraryproject.service;

import com.sber.java13spring.java13springproject.libraryproject.constants.Errors;
import com.sber.java13spring.java13springproject.libraryproject.dto.AddBookDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.AuthorDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.BookDTO;
import com.sber.java13spring.java13springproject.libraryproject.exception.MyDeleteException;
import com.sber.java13spring.java13springproject.libraryproject.mapper.AuthorMapper;
import com.sber.java13spring.java13springproject.libraryproject.model.Author;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;
import com.sber.java13spring.java13springproject.libraryproject.repository.AuthorRepository;
import com.sber.java13spring.java13springproject.libraryproject.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthorService
      extends GenericService<Author, AuthorDTO> {
    
    private final AuthorRepository authorRepository;
    private final BookService bookService;
    
    protected AuthorService(AuthorRepository authorRepository,
                            AuthorMapper authorMapper,
                            BookService bookService) {
        super(authorRepository, authorMapper);
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }
    
    public Page<AuthorDTO> searchAuthors(final String fio,
                                         Pageable pageable) {
        Page<Author> authors = authorRepository.findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse(fio, pageable);
        List<AuthorDTO> result = mapper.toDTOs(authors.getContent());
        return new PageImpl<>(result, pageable, authors.getTotalElements());
    }
    
    public void addBook(AddBookDTO addBookDTO) {
        AuthorDTO author = getOne(addBookDTO.getAuthorId());
        bookService.getOne(addBookDTO.getBookId());
        author.getBooksIds().add(addBookDTO.getBookId());
        update(author);
    }
    
    @Override
    public void deleteSoft(Long objectId) throws MyDeleteException {
        Author author = authorRepository.findById(objectId).orElseThrow(
              () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        boolean authorCanBeDeleted = authorRepository.checkAuthorForDeletion(objectId);
        if (authorCanBeDeleted) {
            markAsDeleted(author);
            Set<Book> books = author.getBooks();
            if (books != null && books.size() > 0) {
                books.forEach(this::markAsDeleted);
            }
            authorRepository.save(author);
        }
        else {
            throw new MyDeleteException(Errors.Authors.AUTHOR_DELETE_ERROR);
        }
    }
    
    public void restore(Long objectId) {
        Author author = authorRepository.findById(objectId).orElseThrow(
              () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        unMarkAsDeleted(author);
        Set<Book> books = author.getBooks();
        if (books != null && books.size() > 0) {
            books.forEach(this::unMarkAsDeleted);
        }
        authorRepository.save(author);
    }
}
