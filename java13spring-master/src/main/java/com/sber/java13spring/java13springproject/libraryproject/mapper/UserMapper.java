package com.sber.java13spring.java13springproject.libraryproject.mapper;

import com.sber.java13spring.java13springproject.libraryproject.dto.BookDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.UserDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.Book;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;
import com.sber.java13spring.java13springproject.libraryproject.model.User;
import com.sber.java13spring.java13springproject.libraryproject.repository.BookRentInfoRepository;
import com.sber.java13spring.java13springproject.libraryproject.utils.DateFormatter;
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
public class UserMapper
      extends GenericMapper<User, UserDTO> {
    
    private BookRentInfoRepository bookRentInfoRepository;
    
    protected UserMapper(ModelMapper modelMapper,
                         BookRentInfoRepository bookRentInfoRepository) {
        super(modelMapper, User.class, UserDTO.class);
        this.bookRentInfoRepository = bookRentInfoRepository;
    }
    
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
              .addMappings(m -> m.skip(UserDTO::setUserBooksRent)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
              .addMappings(m -> m.skip(User::setBookRentInfos)).setPostConverter(toEntityConverter())
              .addMappings(m -> m.skip(User::setBirthDate)).setPostConverter(toEntityConverter());
    }
    
    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getUserBooksRent())) {
            destination.setBookRentInfos(new HashSet<>(bookRentInfoRepository.findAllById(source.getUserBooksRent())));
        }
        else {
            destination.setBookRentInfos(Collections.emptySet());
        }
        destination.setBirthDate(DateFormatter.formatStringToDate(source.getBirthDate()));
    }
    
    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setUserBooksRent(getIds(source));
    }
    
    @Override
    protected Set<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getBookRentInfos())
               ? null
               : entity.getBookRentInfos().stream()
                     .map(GenericModel::getId)
                     .collect(Collectors.toSet());
    }
}
