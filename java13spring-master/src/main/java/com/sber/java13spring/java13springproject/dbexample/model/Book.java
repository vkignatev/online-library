package com.sber.java13spring.java13springproject.dbexample.model;

import lombok.*;

import java.util.Date;
//POJO - Plain Old Java Object
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Setter(AccessLevel.NONE)
    //@Getter(AccessLevel.NONE)
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private Date dateAdded;
}
