package com.travelbooking.core.auth;

import com.travelbooking.db.model.UserAccount;
import com.travelbooking.db.repositories.UserAccountRepository;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

import java.util.Optional;

public class DbAuthenticatorService implements Authenticator<BasicCredentials, UserAccount> {

    private final UserAccountRepository userAccountRepository;
    private final SessionFactory sessionFactory;

    public DbAuthenticatorService(UserAccountRepository userAccountRepository, SessionFactory sessionFactory) {
        this.userAccountRepository = userAccountRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    @UnitOfWork
    public Optional<UserAccount> authenticate(BasicCredentials cred) throws AuthenticationException {
        try (Session session = sessionFactory.openSession()) {
            ManagedSessionContext.bind(session);
            return userAccountRepository.findByUsernameAndPassword(cred.getUsername(), cred.getPassword());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ManagedSessionContext.unbind(sessionFactory);
        }

        return Optional.empty();
    }
}
