package com.sber.java13spring.java13springproject.libraryproject.service;

import com.sber.java13spring.java13springproject.libraryproject.dto.GenericDTO;
import com.sber.java13spring.java13springproject.libraryproject.exception.MyDeleteException;
import com.sber.java13spring.java13springproject.libraryproject.mapper.GenericMapper;
import com.sber.java13spring.java13springproject.libraryproject.model.GenericModel;
import com.sber.java13spring.java13springproject.libraryproject.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Абстрактный сервис, который хранит в себе реализацию CRUD операций по-умолчанию.
 * Если реализация отличная от того, что представлено в этом классе,
 * то она переопределяется в реализации конкретного сервиса.
 *
 * @param <T> - Сущность, с которой мы работаем.
 * @param <N> - DTO, которую мы будем отдавать/принимать дальше.
 */
@Service
public abstract class GenericService<T extends GenericModel, N extends GenericDTO> {
    
    //Инжектим абстрактный репозиторий для работы с базой данных
    protected final GenericRepository<T> repository;
    //Инжектим абстрактный маппер для преобразований из DTO -> Entity, и обратно.
    protected final GenericMapper<T, N> mapper;
    
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected GenericService(GenericRepository<T> repository,
                             GenericMapper<T, N> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    /**
     * Метод, возвращающий полный список всех сущностей.
     *
     * @return Список сконвертированных сущностей в DTO
     */
    public List<N> listAll() {
        return mapper.toDTOs(repository.findAll());
    }
    
    public Page<N> listAll(Pageable pageable) {
        Page<T> objects = repository.findAll(pageable);
        List<N> result = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }
    
    public Page<N> listAllNotDeleted(Pageable pageable) {
        Page<T> preResult = repository.findAllByIsDeletedFalse(pageable);
        List<N> result = mapper.toDTOs(preResult.getContent());
        return new PageImpl<>(result, pageable, preResult.getTotalElements());
    }
    
    public List<N> listAllNotDeleted() {
        return mapper.toDTOs(repository.findAllByIsDeletedFalse());
    }
    
    /***
     * Получить информацию о конкретном объекте/сущности по ID.
     *
     * @param id - идентификатор сущности для поиска.
     * @return - конкретная сущность в формате DTO
     */
    public N getOne(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new NotFoundException("Данных по заданному id: " + id + " не найдены")));
    }
    
    /***
     * Создание сущности в БД.
     *
     * @param object - информация о сущности/объекте.
     * @return - сохраненная в БД сущность в формате DTO.
     */
    public N create(N object) {
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(object)));
    }
    
    /***
     * Обновление сущности в БД.
     *
     * @param object - информация о сущности/объекте.
     * @return - обновленная в БД сущность в формате DTO.
     */
    public N update(N object) {
        return mapper.toDTO(repository.save(mapper.toEntity(object)));
    }
    
    /***
     * Софт Удаление сущности (пометка на удаление).
     *
     * @param id - идентификатор сущности, которая должна быть помечена на удаление.
     */
    public void deleteSoft(Long id) throws MyDeleteException {
        T obj = repository.findById(id).orElseThrow(() -> new NotFoundException("Объект не найден с айди: " + id));
        markAsDeleted(obj);
        repository.save(obj);
    }
    
    /**
     * Восстановление помеченной записи, как удаленной.
     *
     * @param id - идентификатор сущности, которая должна быть восстановлена
     */
    public void restore(Long id) {
        T obj = repository.findById(id).orElseThrow(() -> new NotFoundException("Объект не найден с айди: " + id));
        unMarkAsDeleted(obj);
        repository.save(obj);
    }
    
    /***
     * Хард Удаление сущности из БД.
     *
     * @param id - идентификатор сущности, которая должна быть удалена.
     */
    public void deleteHard(Long id) throws MyDeleteException {
        repository.deleteById(id);
    }
    
    public void markAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(true);
        genericModel.setDeletedWhen(LocalDateTime.now());
        genericModel.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    public void unMarkAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(false);
        genericModel.setDeletedWhen(null);
        genericModel.setDeletedBy(null);
    }
}
