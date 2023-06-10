package org.crazymages.springmastertelegrambot.database;

import org.crazymages.springmastertelegrambot.entity.User;

public interface UserDatabaseService {

    void updateDB(Long chatId, String firstName, String userName);

    User getUserData(long chatId);

    void deleteUser(long chatId);

}
