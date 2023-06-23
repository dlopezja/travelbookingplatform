package com.travelbooking;

import com.travelbooking.core.auth.DbAuthenticatorService;
import com.travelbooking.config.TravelBookingConfig;
import com.travelbooking.core.services.useraccount.UserAccountService;
import com.travelbooking.db.model.UserAccount;
import com.travelbooking.db.repositories.UserAccountRepository;
import com.travelbooking.resources.travel.TravelResource;
import com.travelbooking.resources.useraccount.UserAccountResource;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.SessionFactory;

public class TravelBookingApplication extends Application<TravelBookingConfig> {

    private final HibernateBundle<TravelBookingConfig> hibernateBundle
            = new HibernateBundle<>(
            UserAccount.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(
                TravelBookingConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new TravelBookingApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<TravelBookingConfig> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(TravelBookingConfig travelBookingConfig, Environment environment) throws Exception {
        UserAccountRepository userAccountRepository = new UserAccountRepository(hibernateBundle.getSessionFactory());
        UserAccountResource userAccountResource = new UserAccountResource(new UserAccountService(userAccountRepository));

        final DbAuthenticatorService authenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(DbAuthenticatorService.class,
                        new Class<?>[]{UserAccountRepository.class, SessionFactory.class},
                        new Object[]{userAccountRepository, hibernateBundle.getSessionFactory()});

        // register authenticator
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<UserAccount>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer((userAccount, s, containerRequestContext) -> true)
                        .setRealm("SECURITY REALM")
                        .buildAuthFilter()));

        // register auth filter validator
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserAccount.class));

        // register user account resource
        environment.jersey().register(userAccountResource);

        // register travel resource
        environment.jersey().register(new TravelResource());
    }
}
