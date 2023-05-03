//package com.sber.java13spring.java13springproject.dbexample;
//
//import com.sber.java13spring.java13springproject.dbexample.dao.BookDaoBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import static com.sber.java13spring.java13springproject.dbexample.constants.DBConsts.*;
//
//@Configuration
//@ComponentScan
//public class MyDBConfigContext {
//
//    @Bean
//    @Scope("singleton")
//    public Connection newConnection() throws SQLException {
//        return DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + ":" + PORT + "/" + DB,
//                                           USER, PASSWORD);
//    }
//
////    @Bean
////    public BookDaoBean bookDaoBean() throws SQLException {
////        return new BookDaoBean(newConnection());
////    }
//
//    //TODO: https://habr.com/ru/post/490586/ - что такое спринг и примеры DAO
//    //TODO: https://docs.spring.io/spring-framework/docs/5.2.x/spring-framework-reference/core.html#beans-factory-scopes - области видимости бинов
//
//
//}
