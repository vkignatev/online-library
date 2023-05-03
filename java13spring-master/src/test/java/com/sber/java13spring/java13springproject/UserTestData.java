package com.sber.java13spring.java13springproject;

import com.sber.java13spring.java13springproject.libraryproject.dto.RoleDTO;
import com.sber.java13spring.java13springproject.libraryproject.dto.UserDTO;
import com.sber.java13spring.java13springproject.libraryproject.model.Role;
import com.sber.java13spring.java13springproject.libraryproject.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public interface UserTestData {
    
    UserDTO USER_DTO = new UserDTO("login",
                                   "password",
                                   "email",
                                   "birthDate",
                                   "firstName",
                                   "lastName",
                                   "middleName",
                                   "phone",
                                   "address",
                                   new RoleDTO(),
                                   "changePasswordToken",
                                   new HashSet<>(),
                                   false
    );
    
    List<UserDTO> USER_DTO_LIST = List.of(USER_DTO);
    
    User USER = new User("login",
                         "password",
                         "email",
                         LocalDate.now(),
                         "firstName",
                         "lastName",
                         "middleName",
                         "phone",
                         "address",
                         "changePasswordToken",
                         new Role(),
                         new HashSet<>()
    );
    
    List<User> USER_LIST = List.of(USER);
}
