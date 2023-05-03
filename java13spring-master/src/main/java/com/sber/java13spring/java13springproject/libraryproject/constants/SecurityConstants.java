package com.sber.java13spring.java13springproject.libraryproject.constants;

import java.util.List;

public interface SecurityConstants {
    
    class REST {
        public static List<String> BOOKS_WHITE_LIST = List.of("/rest/books",
                                                              "/rest/books/search",
                                                              "/rest/books/{id}");
        
        public static List<String> AUTHORS_WHITE_LIST = List.of("/rest/authors",
                                                                "/rest/authors/search",
                                                                "/rest/books/search/author",
                                                                "/rest/authors/{id}");
        
        public static List<String> USERS_WHITE_LIST = List.of("/rest/users/auth",
                                                              "/rest/users/registration",
                                                              "/rest/users/remember-password",
                                                              "/rest/users/change-password");
        
        public static List<String> AUTHORS_PERMISSION_LIST = List.of("/rest/authors/add",
                                                                     "/rest/authors/update",
//                                                                     "/rest/authors/delete/**",
                                                                     "/rest/authors/delete/{id}"
                                                                     );
        
        public static List<String> BOOKS_PERMISSION_LIST = List.of("/rest/books/add",
                                                                   "/rest/books/update",
                                                                   "/rest/books/delete/**",
                                                                   "/rest/books/delete/{id}",
                                                                   "/rest/books/download/{bookId}");
        
        public static List<String> USERS_PERMISSION_LIST = List.of("/rest/rent/book/*");
    }
    
    List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
                                                "/js/**",
                                                "/css/**",
                                                "/",
                                                // -- Swagger UI v3 (OpenAPI)
                                                "/swagger-ui/**",
                                                "/webjars/bootstrap/5.0.2/**",
                                                "/v3/api-docs/**",
                                                "/error");
    
    List<String> BOOKS_WHITE_LIST = List.of("/books",
                                            "/books/search",
                                            "/books/{id}");
    
    List<String> AUTHORS_WHITE_LIST = List.of("/authors",
                                              "/authors/search",
                                              "/books/search/author",
                                              "/authors/{id}");
    List<String> BOOKS_PERMISSION_LIST = List.of("/books/add",
                                                 "/books/update",
                                                 "/books/delete",
                                                 "/books/download/{bookId}");
    
    List<String> AUTHORS_PERMISSION_LIST = List.of("/authors/add",
                                                   "/authors/update",
                                                   "/authors/delete");
    
    List<String> USERS_WHITE_LIST = List.of("/login",
                                            "/users/registration",
                                            "/users/remember-password",
                                            "/users/change-password");
    
    List<String> USERS_PERMISSION_LIST = List.of("/rent/book/*");
}
