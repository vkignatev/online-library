package com.sber.java13spring.java13springproject.libraryproject.repository;

import com.sber.java13spring.java13springproject.libraryproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository
      extends GenericRepository<User> {
    
    //select * from users where login = ?
//    @Query(nativeQuery = true, value = "select * from users where login = :login")
    User findUserByLogin(String login);
    
    User findUserByLoginAndIsDeletedFalse(String login);
    
    User findUserByEmail(String email);
    
    User findUserByChangePasswordToken(String token);
    
    @Query(nativeQuery = true,
           value = """
                 select u.*
                 from users u
                 where u.first_name ilike '%' || coalesce(:firstName, '%') || '%'
                 and u.last_name ilike '%' || coalesce(:lastName, '%') || '%'
                 and u.login ilike '%' || coalesce(:login, '%') || '%'
                  """)
    Page<User> searchUsers(String firstName,
                           String lastName,
                           String login,
                           Pageable pageable);
    
    @Query(nativeQuery = true,
           value = """
                 select email
                 from users u join book_rent_info bri on u.id = bri.user_id
                 where bri.return_date >= now()
                 and bri.returned = false
                 and u.is_deleted = false
                 """)
    List<String> getDelayedEmails();
}
