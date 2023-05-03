package com.sber.java13spring.java13springproject;

import com.sber.java13spring.java13springproject.libraryproject.dto.AuthorDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.BookDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.BookWithAuthorsDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.model.Genre;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface BookTestData {
    BookDTO BOOK_DTO_1 = new BookDTO("bookTitle1",
                                     "publishDate1",
                                     1,
                                     1,
                                     "storagePlace1",
                                     "onlineCopyPath1",
                                     "publish1",
                                     "description1",
                                     Genre.DRAMA,
                                     new HashSet<>(),
                                     false);
    
    BookDTO BOOK_DTO_2 = new BookDTO("bookTitle2",
                                     "publishDate2",
                                     2,
                                     2,
                                     "storagePlace2",
                                     "onlineCopyPath2",
                                     "publish2",
                                     "description2",
                                     Genre.NOVEL,
                                     new HashSet<>(),
                                     false);
    
    List<BookDTO> BOOK_DTO_LIST = Arrays.asList(BOOK_DTO_1, BOOK_DTO_2);
    
    Book BOOK_1 = new Book("bookTitle1",
                           LocalDate.now(),
                           1,
                           1,
                           "publish1",
                           "storagePlace1",
                           "onlineCopyPath1",
                           "description",
                           Genre.DRAMA,
                           new HashSet<>(),
                           new HashSet<>());
    Book BOOK_2 = new Book("bookTitle2",
                           LocalDate.now(),
                           2,
                           2,
                           "publish2",
                           "storagePlace2",
                           "onlineCopyPath2",
                           "description2",
                           Genre.NOVEL,
                           new HashSet<>(),
                           new HashSet<>());
    
    List<Book> BOOK_LIST = Arrays.asList(BOOK_1, BOOK_2);
    
    Set<AuthorDTO> AUTHORS = new HashSet<>(AuthorTestData.AUTHOR_DTO_LIST);
    BookWithAuthorsDTO BOOK_WITH_AUTHORS_DTO_1 = new BookWithAuthorsDTO(BOOK_1, AUTHORS);
    BookWithAuthorsDTO BOOK_WITH_AUTHORS_DTO_2 = new BookWithAuthorsDTO(BOOK_2, AUTHORS);
    
    List<BookWithAuthorsDTO> BOOK_WITH_AUTHORS_DTO_LIST = Arrays.asList(BOOK_WITH_AUTHORS_DTO_1, BOOK_WITH_AUTHORS_DTO_2);
}
