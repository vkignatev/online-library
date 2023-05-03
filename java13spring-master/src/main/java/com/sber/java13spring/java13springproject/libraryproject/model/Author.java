package com.sber.java13spring.java13springproject.libraryproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "authors_seq", allocationSize = 1)
//@Where(clause = "is_deleted = false")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class Author
      extends GenericModel {
    @Column(name = "fio", nullable = false)
    private String authorFio;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @Column(name = "description")
    private String description;

//    @ManyToMany(mappedBy = "authors")
//    private Set<Book> books;
    
    //чтобы не было главной/не главной таблицы
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    //@JsonIgnore //убирает рекурсию
    //@JsonManagedReference //убирает рекурсию в связке с JsonBackReference, но не будет работать десериализация
    @JoinTable(
          name = "books_authors",
          joinColumns = @JoinColumn(name = "author_id"), foreignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"),
          inverseJoinColumns = @JoinColumn(name = "book_id"), inverseForeignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"))
    private Set<Book> books;
}
