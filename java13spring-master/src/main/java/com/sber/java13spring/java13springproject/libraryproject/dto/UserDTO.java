package com.sber.java13spring.java13springproject.libraryproject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO
      extends GenericDTO {
    private String login;
    private String password;
    private String email;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String address;
    private RoleDTO role;
    private String changePasswordToken;
    private Set<Long> userBooksRent;
    private boolean isDeleted;
}
