package org.crazymages.springmastertelegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandResponseService {

    SendMessage handleIncomingCommand(Update update);

    SendMessage handleButtonCommand(Update update, String callbackQuery);
}
