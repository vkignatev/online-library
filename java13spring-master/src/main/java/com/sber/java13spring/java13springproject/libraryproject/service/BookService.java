package com.sber.java13spring.java13springproject.libraryproject.service;

import com.sber.java13spring.java13springproject.libraryproject.constants.Errors;
import com.sber.java13spring.java13springproject.libraryproject.dto.BookDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.BookSearchDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.BookWithAuthorsDTO;
import com.sber.java13spring.java13springproject.libraryproject.exception.MyDeleteException;
import com.sber.java13spring.java13springproject.libraryproject.mapper.BookMapper;
import com.sber.java13spring.java13springproject.libraryproject.mapper.BookWithAuthorsMapper;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.repository.BookRepository;
import com.sber.java13spring.java13springproject.libraryproject.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BookService
      extends GenericService<Book, BookDTO> {
    //  Инжектим конкретный репозиторий для работы с таблицей books
    private final BookRepository repository;
    private final BookWithAuthorsMapper bookWithAuthorsMapper;
    
    protected BookService(BookRepository repository,
                          BookMapper mapper,
                          BookWithAuthorsMapper bookWithAuthorsMapper) {
        //Передаем этот репозиторй в абстрактный севрис,
        //чтобы он понимал с какой таблицей будут выполняться CRUD операции
        super(repository, mapper);
        this.repository = repository;
        this.bookWithAuthorsMapper = bookWithAuthorsMapper;
    }
    
    public Page<BookWithAuthorsDTO> getAllBooksWithAuthors(Pageable pageable) {
        Page<Book> booksPaginated = repository.findAll(pageable);
        List<BookWithAuthorsDTO> result = bookWithAuthorsMapper.toDTOs(booksPaginated.getContent());
        return new PageImpl<>(result, pageable, booksPaginated.getTotalElements());
    }
    
    public Page<BookWithAuthorsDTO> getAllNotDeletedBooksWithAuthors(Pageable pageable) {
        Page<Book> booksPaginated = repository.findAllByIsDeletedFalse(pageable);
        List<BookWithAuthorsDTO> result = bookWithAuthorsMapper.toDTOs(booksPaginated.getContent());
        return new PageImpl<>(result, pageable, booksPaginated.getTotalElements());
    }
    
    public BookWithAuthorsDTO getBookWithAuthors(Long id) {
        return bookWithAuthorsMapper.toDTO(mapper.toEntity(super.getOne(id)));
    }
    
    public Page<BookWithAuthorsDTO> findBooks(BookSearchDTO bookSearchDTO,
                                              Pageable pageable) {
        String genre = bookSearchDTO.getGenre() != null ? String.valueOf(bookSearchDTO.getGenre().ordinal()) : null;
        Page<Book> booksPaginated = repository.searchBooks(genre,
                                                           bookSearchDTO.getBookTitle(),
                                                           bookSearchDTO.getAuthorFio(),
                                                           pageable
                                                          );
        List<BookWithAuthorsDTO> result = bookWithAuthorsMapper.toDTOs(booksPaginated.getContent());
        return new PageImpl<>(result, pageable, booksPaginated.getTotalElements());
    }
    
    // files/books/year/month/day/file_name_{id}_{created_when}.txt
    // files/книга_id.pdf
    public BookDTO create(final BookDTO object,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file);
        object.setOnlineCopyPath(fileName);
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(object)));
    }
    
    public BookDTO update(final BookDTO object,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file);
        object.setOnlineCopyPath(fileName);
        return mapper.toDTO(repository.save(mapper.toEntity(object)));
    }
    
    @Override
    public void deleteSoft(Long id) throws MyDeleteException {
        Book book = repository.findById(id).orElseThrow(
              () -> new NotFoundException("Книги с заданным ID=" + id + " не существует"));
//        boolean bookCanBeDeleted = repository.findBookByIdAndBookRentInfosReturnedFalseAndIsDeletedFalse(id) == null;
        boolean bookCanBeDeleted = repository.checkBookForDeletion(id);
        if (bookCanBeDeleted) {
            if (book.getOnlineCopyPath() != null && !book.getOnlineCopyPath().isEmpty()) {
                FileHelper.deleteFile(book.getOnlineCopyPath());
            }
            markAsDeleted(book);
            repository.save(book);
        }
        else {
            throw new MyDeleteException(Errors.Books.BOOK_DELETE_ERROR);
        }
    }
    
    public void restore(Long objectId) {
        Book book = repository.findById(objectId).orElseThrow(
              () -> new NotFoundException("Книги с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(book);
        repository.save(book);
    }
}
