package com.travelbooking.db.repositories;

import com.travelbooking.db.model.UserAccount;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class UserAccountRepository extends AbstractDAO<UserAccount> {
    public UserAccountRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<UserAccount> findAll() {
        return list(namedTypedQuery("UserAccount.findAll"));
    }

    public UserAccount save(UserAccount userAccount) {
        return persist(userAccount);
    }

    public Optional<UserAccount> findByUsernameAndPassword(String username, String password) {
        return Optional.ofNullable(uniqueResult(
                namedTypedQuery("UserAccount.findByUsernameAndPassword")
                        .setParameter("username", username)
                        .setParameter("password", password)));
    }
}
