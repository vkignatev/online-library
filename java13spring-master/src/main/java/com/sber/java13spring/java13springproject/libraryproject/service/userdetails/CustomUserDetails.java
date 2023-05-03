package com.sber.java13spring.java13springproject.libraryproject.service.userdetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


//header = { "alg": "HS256", "typ": "JWT"}
//payload = { "userId": "b08f86af-35da-48f2-8fab-cef3904660bd" }
//signature = const SECRET_KEY = 'cAtwa1kkEy'
// =>
/*
// header eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
// payload eyJ1c2VySWQiOiJiMDhmODZhZi0zNWRhLTQ4ZjItOGZhYi1jZWYzOTA0NjYwYmQifQ
// signature -xN_h82PHVTCMA9vdoHrcZxH-x5mb11y1537t3rGzcM
 */

// JWT Token/ GWT - Google Web Toolkit
// eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJiMDhmODZhZi0zNWRhLTQ4ZjItOGZhYi1jZWYzOTA0NjYwYmQifQ.-xN_h82PHVTCMA9vdoHrcZxH-x5mb11y1537t3rGzcM
@AllArgsConstructor
@Builder
public class CustomUserDetails
      implements UserDetails {
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String username;
    private final Integer id;
    private final Boolean enabled;
    private final Boolean accountNotExpired;
    private final Boolean accountNotLocked;
    private final Boolean credentialsNotExpired;
    
    public CustomUserDetails(final Integer id,
                             final String username,
                             final String password,
                             final Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNotExpired = true;
        this.accountNotLocked = true;
        this.credentialsNotExpired = true;
        this.enabled = true;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNotExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNotLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNotExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    public Integer getUserId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "{\"user_id\":\"" + id + "\"," +
               "\"username\":\"" + username + "\"," +
               "\"user_role\":\"" + authorities + "\"," +
               "\"user_password\":\"" + password + "\"}";
    }
}
