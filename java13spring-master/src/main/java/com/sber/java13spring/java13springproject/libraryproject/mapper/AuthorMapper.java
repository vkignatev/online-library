package com.sber.java13spring.java13springproject.libraryproject.mapper;

import com.sber.java13spring.java13springproject.libraryproject.dto.AuthorDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.Author;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;
import com.sber.java13spring.java13springproject.libraryproject.repository.BookRepository;
import com.sber.java13spring.java13springproject.libraryproject.utils.DateFormatter;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorMapper
      extends GenericMapper<Author, AuthorDTO> {
    private final BookRepository bookRepository;
    
    protected AuthorMapper(ModelMapper modelMapper,
                           BookRepository bookRepository) {
        super(modelMapper, Author.class, AuthorDTO.class);
        this.bookRepository = bookRepository;
    }
    
    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Author.class, AuthorDTO.class)
              .addMappings(m -> m.skip(AuthorDTO::setBooksIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(AuthorDTO.class, Author.class)
              .addMappings(m -> m.skip(Author::setBooks)).setPostConverter(toEntityConverter())
              .addMappings(m -> m.skip(Author::setBirthDate)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(AuthorDTO source, Author destination) {
        if (!Objects.isNull(source.getBooksIds())) {
            destination.setBooks(new HashSet<>(bookRepository.findAllById(source.getBooksIds())));
        }
        else {
            destination.setBooks(Collections.emptySet());
        }
        destination.setBirthDate(DateFormatter.formatStringToDate(source.getBirthDate()));
    }
    
    @Override
    protected void mapSpecificFields(Author source, AuthorDTO destination) {
        destination.setBooksIds(getIds(source));
    }
    
    protected Set<Long> getIds(Author author) {
        return Objects.isNull(author) || Objects.isNull(author.getBooks())
               ? null
               : author.getBooks().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toSet());
    }
}
