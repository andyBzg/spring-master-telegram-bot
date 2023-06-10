package org.crazymages.springmastertelegrambot.database.impl;

import lombok.extern.slf4j.Slf4j;
import org.crazymages.springmastertelegrambot.database.UserDatabaseService;
import org.crazymages.springmastertelegrambot.entity.User;
import org.crazymages.springmastertelegrambot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
public class UserDatabaseServiceImpl implements UserDatabaseService {

    private final UserRepository userRepository;

    @Autowired
    public UserDatabaseServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void updateDB(Long chatId, String firstName, String userName) {
        if (userRepository.findById(chatId).isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(firstName);
            user.setUserName(userName);
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            log.info("Added to DB: " + user);
        }
        else {
            log.info("User is already registered.");
        }
    }

    @Override
    public User getUserData(long chatId) {
        Optional<User> optionalUser = userRepository.findById(chatId);
        return optionalUser.orElseGet(User::new);
    }

    @Override
    public void deleteUser(long chatId) {
        userRepository.deleteById(chatId);
        log.info("User deleted: " + chatId);
    }
}
