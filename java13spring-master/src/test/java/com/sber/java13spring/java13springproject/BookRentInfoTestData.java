package com.sber.java13spring.java13springproject;

import com.sber.java13spring.java13springproject.libraryproject.dto.BookRentInfoDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.BookRentInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRentInfoTestData {
    
    BookRentInfoDTO BOOK_RENT_INFO_DTO = new BookRentInfoDTO(LocalDateTime.now(),
                                                             LocalDateTime.now(),
                                                             false,
                                                             14,
                                                             1L,
                                                             1L,
                                                             null);
    
    List<BookRentInfoDTO> BOOK_RENT_INFO_DTO_LIST = List.of(BOOK_RENT_INFO_DTO);
    
    BookRentInfo BOOK_RENT_INFO = new BookRentInfo(null,
                                                   null,
                                                   LocalDateTime.now(),
                                                   LocalDateTime.now(),
                                                   false,
                                                   14);
    
    List<BookRentInfo> BOOK_RENT_INFO_LIST = List.of(BOOK_RENT_INFO);
}
