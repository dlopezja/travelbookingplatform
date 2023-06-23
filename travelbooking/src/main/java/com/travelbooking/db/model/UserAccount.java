package com.travelbooking.db.model;

import com.travelbooking.resources.useraccount.UserAccountDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.security.Principal;
import java.util.UUID;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
        @NamedQuery(name = "UserAccount.findByUsernameAndPassword",
                query = "SELECT u FROM UserAccount u WHERE u.username = :username and u.password = :password")
})
public class UserAccount implements Principal, Serializable {

    @Id
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    public UserAccount() {
    }

    public UserAccount(UserAccountDto userAccountDto) {
        this.id = UUID.randomUUID().getLeastSignificantBits();
        this.email = userAccountDto.getEmail();
        this.fullName = userAccountDto.getFullName();
        this.username = userAccountDto.getUsername();
        this.password = userAccountDto.getPassword();
    }

    public UserAccountDto toDto() {
        return new UserAccountDto(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setAge(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return username;
    }
}