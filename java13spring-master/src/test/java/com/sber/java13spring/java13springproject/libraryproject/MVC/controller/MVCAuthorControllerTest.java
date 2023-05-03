package com.sber.java13spring.java13springproject.libraryproject.MVC.controller;

import com.sber.java13spring.java13springproject.libraryproject.dto.AuthorDTO;
import com.sber.java13spring.java13springproject.libraryproject.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
public class MVCAuthorControllerTest
      extends CommonTestMVC {
    
    //Создаем нового автора для создания через контроллер (тест дата)
    private final AuthorDTO authorDTO = new AuthorDTO("MVC_TestAuthorFio", "2023-01-01", "Test Description", new HashSet<>(), false);
    private final AuthorDTO authorDTOUpdated = new AuthorDTO("MVC_TestAuthorFio_UPDATED", "2023-01-01", "Test Description", new HashSet<>(), false);
    
    @Autowired
    private AuthorService authorService;
    
    /**
     * Метод, тестирующий просмотр всех авторов через MVC-контроллер.
     * Ожидаем, что результат ответа будет просто любой 200 статус.
     * Проверяем, что view, на которое нас перенаправит контроллер, при удачном вызове - это как раз показ всех авторов
     * Так-же, ожидаем, что в модели будет атрибут authors.
     *
     * @throws Exception - любая ошибка
     */
    @Test
    @DisplayName("Просмотр всех авторов через MVC контроллер, тестирование 'authors/'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void listAll() throws Exception {
        log.info("Тест по выбору всех автора через MVC начат");
        MvcResult result = mvc.perform(get("/authors")
                                             .param("page", "1")
                                             .param("size", "5")
                                             .contentType(MediaType.APPLICATION_JSON_VALUE)
                                             .accept(MediaType.APPLICATION_JSON_VALUE)
                                      )
              .andDo(print())
              .andExpect(status().is2xxSuccessful())
              .andExpect(view().name("authors/viewAllAuthors"))
              .andExpect(model().attributeExists("authors"))
              .andReturn();
    }
    
    /**
     * Метод, тестирующий создание автора через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном создании
     *
     * @throws Exception - любая ошибка
     */
    @Test
    @DisplayName("Создание автора через MVC контроллер, тестирование 'authors/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по созданию автора через MVC начат успешно");
        mvc.perform(post("/authors/add")
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .flashAttr("authorForm", authorDTO)
                          .accept(MediaType.APPLICATION_JSON_VALUE)
                          .with(csrf()))
              .andDo(print())
              .andExpect(status().is3xxRedirection())
              .andExpect(view().name("redirect:/authors"))
              .andExpect(redirectedUrlTemplate("/authors"))
              .andExpect(redirectedUrl("/authors"));
        log.info("Тест по созданию автора через MVC закончен успешно");
    }
    
    @Order(2)
    @Test
    @DisplayName("Обновление автора через MVC контроллер, тестирование 'authors/update'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    //@WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "andy_user")
    @Override
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению автора через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "authorFio"));
        AuthorDTO foundAuthorForUpdate = authorService.searchAuthors(authorDTO.getAuthorFio(), pageRequest).getContent().get(0);
        foundAuthorForUpdate.setAuthorFio(authorDTOUpdated.getAuthorFio());
        mvc.perform(post("/authors/update")
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .flashAttr("authorForm", foundAuthorForUpdate)
                          .accept(MediaType.APPLICATION_JSON_VALUE)
                   )
              .andDo(print())
              .andExpect(status().is3xxRedirection())
              .andExpect(view().name("redirect:/authors"))
              .andExpect(redirectedUrl("/authors"));
        log.info("Тест по обновлению автора через MVC закончен успешно");
    }
    
    @Order(3)
    @Test
    @DisplayName("Софт удаление автора через MVC контроллер, тестирование 'authors/delete'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void deleteObject() throws Exception {
        log.info("Тест по soft удалению автора через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "authorFio"));
        AuthorDTO foundAuthorForDelete = authorService.searchAuthors(authorDTOUpdated.getAuthorFio(), pageRequest).getContent().get(0);
        foundAuthorForDelete.setDeleted(true);
        mvc.perform(get("/authors/delete/{id}", foundAuthorForDelete.getId())
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .accept(MediaType.APPLICATION_JSON_VALUE)
                   )
              .andDo(print())
              .andExpect(status().is3xxRedirection())
              .andExpect(view().name("redirect:/authors"))
              .andExpect(redirectedUrl("/authors"));
        AuthorDTO deletedAuthor = authorService.getOne(foundAuthorForDelete.getId());
        assertTrue(deletedAuthor.isDeleted());
        log.info("Тест по soft удалению автора через MVC закончен успешно");
        authorService.deleteHard(foundAuthorForDelete.getId());
    }
}
