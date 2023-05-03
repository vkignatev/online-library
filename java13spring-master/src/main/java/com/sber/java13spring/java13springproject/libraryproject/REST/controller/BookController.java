package com.sber.java13spring.java13springproject.libraryproject.REST.controller;

import com.sber.java13spring.java13springproject.libraryproject.dto.BookDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.service.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/books")
@Tag(name = "Книги",
     description = "Контроллер для работы с книгами библиотеки")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
//localhost:9090/api/rest/books
public class BookController
      extends GenericController<Book, BookDTO> {

    public BookController(BookService bookService) {
        super(bookService);
    }

//    @Operation(description = "Добавить автора к книге", method = "addAuthor")
//    @RequestMapping(value = "/addAuthor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Book> addAuthor(@RequestParam(value = "bookId") Long bookId,
//                                          @RequestParam(value = "authorId") Long authorId) {
//        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Книга с переданным ID не найдена"));
//        Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("Автор с таким ID не найден"));
//        book.getAuthors().add(author);
//        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.save(book));
//    }
}
