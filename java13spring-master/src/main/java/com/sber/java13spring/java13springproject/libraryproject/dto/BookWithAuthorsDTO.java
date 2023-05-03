package com.sber.java13spring.java13springproject.libraryproject.dto;

import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookWithAuthorsDTO
      extends BookDTO {
    public BookWithAuthorsDTO(Book book, Set<AuthorDTO> authors) {
        super(book);
        this.authors = authors;
    }
    private Set<AuthorDTO> authors;
}
