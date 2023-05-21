package org.crazymages.springmastertelegrambot.database;

public interface UserDatabaseService {

    void updateDB(Long chatId, String firstName, String userName);
}
