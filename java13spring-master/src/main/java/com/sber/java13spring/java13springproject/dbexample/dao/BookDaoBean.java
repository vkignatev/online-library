//package com.sber.java13spring.java13springproject.dbexample.dao;
//
//import com.sber.java13spring.java13springproject.dbexample.model.Book;
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@Component
//public class BookDaoBean {
//
//    private final Connection connection;
//
//    public BookDaoBean(Connection connection) {
//        this.connection = connection;
//    }
//
//    public Book findBookById(Integer bookId) throws SQLException {
//        PreparedStatement selectQuery = connection.prepareStatement("select * from books where id = ?");
//        selectQuery.setInt(1, bookId);
//        ResultSet resultSet = selectQuery.executeQuery();
//        Book book = new Book();
//        while (resultSet.next()) {
//            book.setBookAuthor(resultSet.getString("author"));
//            book.setBookTitle(resultSet.getString("title"));
//            book.setDateAdded(resultSet.getDate("date_added"));
//            System.out.println(book);
//        }
//        return book;
//    }
//
//    //TODO: Почитать про бины https://www.baeldung.com/spring-component-annotation
//
//}
