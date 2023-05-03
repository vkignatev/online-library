package com.sber.java13spring.java13springproject.libraryproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRentInfoDTO
      extends GenericDTO {
    
    //    private UserDTO user;
    private LocalDateTime rentDate;
    private LocalDateTime returnDate;
    private Boolean returned;
    private Integer rentPeriod;
    private Long bookId;
    private Long userId;
    private BookDTO bookDTO;
    
}
