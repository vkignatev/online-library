package com.sber.java13spring.java13springproject.libraryproject.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "books_seq", allocationSize = 1)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class Book
      extends GenericModel {
    
    @Column(name = "title", nullable = false)
    private String bookTitle;
    
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;
    
    @Column(name = "page_count")
    private Integer pageCount;
    
    @Column(name = "amount", nullable = false)
    private Integer amount;
    
    @Column(name = "publish")
    private String publish;
    
    @Column(name = "storage_place", nullable = false)
    private String storagePlace;
    
    @Column(name = "online_copy_path")
    private String onlineCopyPath;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "genre", nullable = false)
    @Enumerated
    private Genre genre;
    
    /*
    @OneToMany(cascade = CascadeType.PERSIST)
    PERSIST:
    Book -> many Reviews (книга и оценки) - отношение 1-М (М-1)
    Book newB = new Book("title");
    Review r1 = new Review("Awesome");
    Review r2 = new Review("Very GOOD");
    newB.addReview(r1);
    newB.addReview(r2);
    (newB.addAll(r1, r2));
    bookRepository.save(book);
    =>
    как результат будет 3 записи:
    insert into books()....;
    insert into reviews()....;
    insert into reviews()....;
    
     @OneToMany(cascade = CascadeType.MERGE)
     MERGE:
     Book book = bookRepository.getOne(1);
     book.setDescription("12312312");
     Review r1 = reviewRepository.getOne(1);
     r1.setText("Fine");
     bookRepository.save(book);
     =>
     update books set description = ?....;
     update reviews set text = ? ....;
     
      @OneToMany(cascade = CascadeType.REMOVE)
      REMOVE:
      Book book = bookRepository.getOne(1);
      bookRepository.delete(book);
      =>
      delete from reviews where id = ?
      delete from reviews where id = ?
      delete from books where id = ?
      --orphanRemoval = true =>
      Book book = bookRepository.getOne(1);
      book.removeReview(book.getReviews().get(0));
      --delete from reviews where review_id = ?
      TODO: про CascadeType и FetchType:
      1) https://sysout.ru/kak-rabotaet-orphanremoval/
      2) https://sysout.ru/tipy-cascade-primer-na-hibernate-i-spring-boot/
      3) https://www.baeldung.com/jpa-cascade-types (!!!!!)
      4) https://coderlessons.com/articles/java/rukovodstvo-dlia-nachinaiushchikh-po-jpa-i-hibernate-cascade-types
      5) https://www.baeldung.com/hibernate-lazy-eager-loading
      6) https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.entity-graph - про графы
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
               joinColumns = @JoinColumn(name = "book_id"), foreignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"),
               inverseJoinColumns = @JoinColumn(name = "author_id"), inverseForeignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"))
    //@JsonBackReference
    private Set<Author> authors;
    
    @OneToMany(mappedBy = "book", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<BookRentInfo> bookRentInfos;
}
