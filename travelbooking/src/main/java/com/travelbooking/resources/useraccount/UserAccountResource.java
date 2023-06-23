package com.travelbooking.resources.useraccount;

import com.travelbooking.core.services.useraccount.UserAccountService;
import com.travelbooking.db.model.UserAccount;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/users-account")
@Produces(MediaType.APPLICATION_JSON)
public class UserAccountResource {

    private final UserAccountService userAccountService;

    public UserAccountResource(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GET
    @UnitOfWork
    public List<UserAccountDto> getAllUsers(@Auth UserAccount userAccount) {
        return userAccountService.getUsers();
    }

    @POST
    @UnitOfWork
    public UserAccountDto postUserAccount(UserAccountDto userAccountDto) {
        return userAccountService.createUserAccount(userAccountDto);
    }

}
