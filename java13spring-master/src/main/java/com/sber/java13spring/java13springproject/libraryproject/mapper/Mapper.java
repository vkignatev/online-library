package com.sber.java13spring.java13springproject.libraryproject.mapper;

import com.sber.java13spring.java13springproject.libraryproject.dto.GenericDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);
    
    List<E> toEntities(List<D> dtos);
    
    D toDTO(E entity);
    
    List<D> toDTOs(List<E> entities);
}
