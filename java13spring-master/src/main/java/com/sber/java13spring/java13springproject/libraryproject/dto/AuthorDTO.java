package com.sber.java13spring.java13springproject.libraryproject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorDTO
      extends GenericDTO {
    private String authorFio;
    private String birthDate;
    private String description;
    private Set<Long> booksIds;
    private boolean isDeleted;
}
