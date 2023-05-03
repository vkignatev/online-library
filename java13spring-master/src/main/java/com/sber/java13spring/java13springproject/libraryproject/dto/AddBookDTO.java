package com.sber.java13spring.java13springproject.libraryproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddBookDTO {
    Long bookId;
    Long authorId;
}
