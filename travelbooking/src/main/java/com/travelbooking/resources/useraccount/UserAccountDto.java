package com.travelbooking.resources.useraccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travelbooking.db.model.UserAccount;

public class UserAccountDto {

    @JsonProperty(value = "id")
    private long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("username")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserAccountDto() {}

    public UserAccountDto(long id, String fullName, String email, String username) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
    }

    public UserAccountDto(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.email=  userAccount.getEmail();
        this.fullName = userAccount.getFullName();
        this.username = userAccount.getUsername();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
}
