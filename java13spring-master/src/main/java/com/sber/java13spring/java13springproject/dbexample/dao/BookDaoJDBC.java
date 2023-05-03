//package com.sber.java13spring.java13springproject.dbexample.dao;
//
//import com.sber.java13spring.java13springproject.dbexample.DB.DBApp;
//import com.sber.java13spring.java13springproject.dbexample.model.Book;
//
//import java.sql.*;
//
//public class BookDaoJDBC {
//    //select * from book where id = ?
//    //JDBC - Java DataBase Connection
//    public Book findBookById(Integer bookId) {
//        try (Connection connection = DBApp.INSTANCE.newConnection()) {
//            if (connection != null) {
//                System.out.println("ура! мы подключились к БД!!!!");
//            }
//            else {
//                System.out.println("база данных отдыхает, не трогайте!");
//            }
//            PreparedStatement selectQuery = connection.prepareStatement("select * from books where id = ?");
//            selectQuery.setInt(1, bookId);
//            ResultSet resultSet = selectQuery.executeQuery();
//            while (resultSet.next()) {
//                Book book = new Book();
//                book.setBookAuthor(resultSet.getString("author"));
//                book.setBookTitle(resultSet.getString("title"));
//                book.setDateAdded(resultSet.getDate("date_added"));
//                System.out.println(book);
//                return book;
//            }
//        }
//        catch (SQLException e) {
//            System.out.println("Error:" + e.getMessage());
//        }
//        return null;
//    }
//
//    //public List<Books> findBooksByTitle(String title){}
//
////    public Connection newConnection() throws SQLException {
////        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/local_db",
////                                           "postgres", "12345");
////    }
//}
