package org.penguin.project.tutorial.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class SecurityUserDetails implements UserDetails {

    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUserDetails(String id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static SecurityUserDetails build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRoles() != null) {
            authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(StringUtils.isEmpty(role.getName())? "ROLE_USER" : role.getName().startsWith("ROLE_")?role.getName(): ("ROLE_"+role.getName())))
                    .collect(Collectors.toList());
        }
        return new SecurityUserDetails(user.getId(), user.getUsername(), user.getEmail(), user.getEncryptedPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getId() {
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
        SecurityUserDetails user = (SecurityUserDetails) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public String toString() {
        return "SecurityUserDetails{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
