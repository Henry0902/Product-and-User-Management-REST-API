package com.project.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.table.UserInfo;
import com.project.table.UserModule;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private List<UserModule> userModules;

  // Constructor, getters, and setters

    // Add this method
//    @Getter
//    private List<UserModule> userModuleMenus; // Add this line


  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(UserInfo userInfo) {
//    List<GrantedAuthority> authorities = userInfo.getRoles().stream()
//            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
//            .collect(Collectors.toList());
    return new UserDetailsImpl(
            userInfo.getId(),
            userInfo.getUsername(),
            userInfo.getEmail(),
            userInfo.getPassword(),
            null
            );
  }

  //getUserModuleMenus

    @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
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
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
