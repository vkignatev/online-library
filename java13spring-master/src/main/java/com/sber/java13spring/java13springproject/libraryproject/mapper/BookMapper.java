package com.sber.java13spring.java13springproject.libraryproject.mapper;

import com.sber.java13spring.java13springproject.libraryproject.dto.BookDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;
import com.sber.java13spring.java13springproject.libraryproject.repository.AuthorRepository;
import com.sber.java13spring.java13springproject.libraryproject.utils.DateFormatter;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper
      extends GenericMapper<Book, BookDTO> {
    private final AuthorRepository authorRepository;
    
    protected BookMapper(ModelMapper mapper, AuthorRepository authorRepository) {
        super(mapper, Book.class, BookDTO.class);
        this.authorRepository = authorRepository;
    }
    
    @PostConstruct
    @Override
    public void setupMapper() {
        modelMapper.createTypeMap(Book.class, BookDTO.class)
              .addMappings(m -> m.skip(BookDTO::setAuthorsIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(BookDTO.class, Book.class)
              .addMappings(m -> m.skip(Book::setAuthors)).setPostConverter(toEntityConverter())
              .addMappings(m -> m.skip(Book::setPublishDate)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(BookDTO source, Book destination) {
        if (!Objects.isNull(source.getAuthorsIds())) {
            destination.setAuthors(new HashSet<>(authorRepository.findAllById(source.getAuthorsIds())));
        }
        else {
            destination.setAuthors(Collections.emptySet());
        }
        destination.setPublishDate(DateFormatter.formatStringToDate(source.getPublishDate()));
    }
    
    @Override
    protected void mapSpecificFields(Book source, BookDTO destination) {
        destination.setAuthorsIds(getIds(source));
    }
    
    protected Set<Long> getIds(Book book) {
        return Objects.isNull(book) || Objects.isNull(book.getAuthors())
               ? null
               : book.getAuthors().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toSet());
    }
}
