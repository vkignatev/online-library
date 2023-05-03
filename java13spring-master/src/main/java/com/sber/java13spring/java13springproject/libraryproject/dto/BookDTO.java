package com.sber.java13spring.java13springproject.libraryproject.dto;

import com.sber.java13spring.java13springproject.libraryproject.model.Author;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.model.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO
      extends GenericDTO {
    
    private String bookTitle;
    private String publishDate;
    private Integer pageCount;
    private Integer amount;
    private String storagePlace;
    private String onlineCopyPath;
    private String publish;
    private String description;
    private Genre genre;
    private Set<Long> authorsIds;
    private boolean isDeleted;
    
    public BookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        //из entity делаем DTO
        bookDTO.setBookTitle(book.getBookTitle());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setId(book.getId());
        bookDTO.setPageCount(book.getPageCount());
        bookDTO.setPublishDate(book.getPublishDate().toString());
        Set<Author> authors = book.getAuthors();
        Set<Long> authorIds = new HashSet<>();
        if (authors != null && authors.size() > 0) {
            authors.forEach(a -> authorIds.add(a.getId()));
        }
        bookDTO.setAuthorsIds(authorIds);
    }
}
