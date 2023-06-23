package com.travelbooking.core.services.useraccount;

import com.travelbooking.core.kafka.KafkaProducerService;
import com.travelbooking.db.model.UserAccount;
import com.travelbooking.db.repositories.UserAccountRepository;
import com.travelbooking.resources.useraccount.UserAccountDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class UserAccountService {

    // TODO (daniel.lopezj): How can we inject the Repository interface from Spring Data JPA?
//    private final UserRepository userRepository;

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public UserAccountDto createUserAccount(UserAccountDto userAccountDto) {
        UserAccount userAccountSaved = userAccountRepository.save(new UserAccount(userAccountDto));

        // send email after user is saved
        KafkaProducerService kafkaProducerService = new KafkaProducerService();
        kafkaProducerService.sendMessageToTopic("travelbooking", userAccountSaved.getEmail());

        return userAccountSaved.toDto();
    }

    @Transactional
    public List<UserAccountDto> getUsers() {
        return userAccountRepository.findAll().stream().map(UserAccount::toDto).collect(Collectors.toList());
    }
}