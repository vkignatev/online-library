package com.sber.java13spring.java13springproject.libraryproject.mapper;

import com.sber.java13spring.java13springproject.libraryproject.dto.BookWithAuthorsDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;
import com.sber.java13spring.java13springproject.libraryproject.repository.AuthorRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookWithAuthorsMapper
      extends GenericMapper<Book, BookWithAuthorsDTO> {
    
    private final AuthorRepository authorRepository;
    
    protected BookWithAuthorsMapper(ModelMapper mapper,
                                    AuthorRepository authorRepository
                                   ) {
        super(mapper, Book.class, BookWithAuthorsDTO.class);
        this.authorRepository = authorRepository;
    }
    
    @Override
    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Book.class, BookWithAuthorsDTO.class)
              .addMappings(m -> m.skip(BookWithAuthorsDTO::setAuthorsIds)).setPostConverter(toDtoConverter());
        
        modelMapper.createTypeMap(BookWithAuthorsDTO.class, Book.class)
              .addMappings(m -> m.skip(Book::setAuthors)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(BookWithAuthorsDTO source, Book destination) {
        destination.setAuthors(new HashSet<>(authorRepository.findAllById(source.getAuthorsIds())));
    }
    
    @Override
    protected void mapSpecificFields(Book source, BookWithAuthorsDTO destination) {
        destination.setAuthorsIds(getIds(source));
    }
    
    @Override
    protected Set<Long> getIds(Book entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getId())
               ? null
               : entity.getAuthors().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toSet());
    }
}
