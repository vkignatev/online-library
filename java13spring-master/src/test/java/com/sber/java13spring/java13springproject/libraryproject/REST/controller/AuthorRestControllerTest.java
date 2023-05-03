package com.sber.java13spring.java13springproject.libraryproject.REST.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sber.java13spring.java13springproject.libraryproject.dto.AddBookDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.AuthorDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class AuthorRestControllerTest
      extends CommonTestREST {
    
    private static Long createdAuthorID;
    
    @Test
    @Order(0)
    protected void listAll() throws Exception {
        log.info("Тест по просмотра всех авторов через REST начат успешно");
        String result = mvc.perform(
                    get("/rest/authors/getAll")
                          .headers(headers)
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON))
              .andDo(print())
              .andExpect(status().is2xxSuccessful())
              .andExpect(jsonPath("$.*", hasSize(greaterThan(0))))
              .andReturn()
              .getResponse()
              .getContentAsString();
        List<AuthorDTO> authorDTOS = objectMapper.readValue(result, new TypeReference<List<AuthorDTO>>() {});
        authorDTOS.forEach(a -> log.info(a.toString()));
        log.info("Тест по просмотра всех авторов через REST закончен успешно");
    }
    
    @Test
    @Order(1)
    protected void createObject() throws Exception {
        log.info("Тест по созданию автора через REST начат успешно");
        //Создаем нового автора для создания через контроллер (тест дата)
        AuthorDTO authorDTO = new AuthorDTO("REST_TestAuthorFio", "2023-01-01", "Test Description", new HashSet<>(), false);
        
        /*
        Вызываем метод создания (POST) в контроллере, передаем ссылку на REST API в MOCK.
        В headers передаем токен для авторизации (под админом, смотри родительский класс).
        Ожидаем, что статус ответа будет успешным и что в ответе есть поле ID, а далее возвращаем контент как строку
        Все это мы конвертируем в AuthorDTO при помощи ObjectMapper от библиотеки Jackson.
        Присваиваем в статическое поле ID созданного автора, чтобы далее с ним работать.
         */
        AuthorDTO result = objectMapper.readValue(mvc.perform(post("/rest/authors/add")
                                                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                    .headers(super.headers)
                                                                    .content(asJsonString(authorDTO))
                                                                    .accept(MediaType.APPLICATION_JSON_VALUE))
                                                        .andExpect(status().is2xxSuccessful())
                                                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                                                        .andReturn()
                                                        .getResponse()
                                                        .getContentAsString(),
                                                  AuthorDTO.class);
        createdAuthorID = result.getId();
        log.info("Тест по созданию автора через REST закончен успешно " + result);
        /*
        можно запустить один тест и по цепочке вызывать остальные:
        updateAuthor(createdAuthorID);
         */
    }
    
    @Test
    @Order(2)
    protected void updateObject() throws Exception {
        log.info("Тест по обновления автора через REST начат успешно");
        //получаем нашего автора созданного (если запускать тесты подряд), если отдельно - создаем отдельную тест дату для апдейта
        AuthorDTO existingAuthor = objectMapper.readValue(mvc.perform(get("/rest/authors/getOneById")
                                                                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                            .headers(super.headers)
                                                                            .param("id", String.valueOf(createdAuthorID))
                                                                            .accept(MediaType.APPLICATION_JSON_VALUE))
                                                                .andExpect(status().is2xxSuccessful())
                                                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                                                                .andReturn()
                                                                .getResponse()
                                                                .getContentAsString(),
                                                          AuthorDTO.class);
        //обновляем поля
        existingAuthor.setAuthorFio("REST_TestAuthorFioUPDATED");
        existingAuthor.setDescription("Test Description UPDATED");
        
        //вызываем update через REST API
        mvc.perform(put("/rest/authors/update")
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .headers(super.headers)
                          .content(asJsonString(existingAuthor))
                          .param("id", String.valueOf(createdAuthorID))
                          .accept(MediaType.APPLICATION_JSON_VALUE))
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        log.info("Тест по обновления автора через REST закончен успешно");
    }
    
    @Test
    @Order(3)
    void addBook() throws Exception {
        log.info("Тест по добавлению книги автору через REST начат успешно");
        AddBookDTO addBookDTO = new AddBookDTO(11L, createdAuthorID);
        String result = mvc.perform(post("/rest/authors/addBook")
                                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                                          .headers(super.headers)
                                          .content(asJsonString(addBookDTO))
                                          .accept(MediaType.APPLICATION_JSON_VALUE))
              .andDo(print())
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();
        
        AuthorDTO author = objectMapper.readValue(result, AuthorDTO.class);
        log.info("Тест по добавлению книги автору через REST завершен успешно с результатом {}",
                 author);
    }
    
    @Test
    @Order(4)
    protected void deleteObject() throws Exception {
        log.info("Тест по удалению автора через REST начат успешно");
        mvc.perform(delete("/rest/authors/delete/{id}", createdAuthorID)
                          .headers(headers)
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        AuthorDTO existingAuthor = objectMapper.readValue(mvc.perform(get("/rest/authors/getOneById")
                                                                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                            .headers(super.headers)
                                                                            .param("id", String.valueOf(createdAuthorID))
                                                                            .accept(MediaType.APPLICATION_JSON_VALUE))
                                                                .andExpect(status().is2xxSuccessful())
                                                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                                                                .andReturn()
                                                                .getResponse()
                                                                .getContentAsString(),
                                                          AuthorDTO.class);
        
        assertTrue(existingAuthor.isDeleted());
        log.info("Тест по удалению автора через REST завершен успешно");
        mvc.perform(
                    delete("/rest/authors/delete/hard/{id}", createdAuthorID)
                          .headers(headers)
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        log.info("Данные очищены");
    }
    
}
