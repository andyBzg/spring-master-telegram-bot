package org.crazymages.springmastertelegrambot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crazymages.springmastertelegrambot.buttons.Buttons;
import org.crazymages.springmastertelegrambot.components.BotReplyMessage;
import org.crazymages.springmastertelegrambot.database.UserDatabaseService;
import org.crazymages.springmastertelegrambot.service.CommandResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class CommandResponseServiceImpl implements CommandResponseService {
    private final UserDatabaseService userDatabaseService;
    private final BotReplyMessage botReplyMessage;

    @Autowired
    public CommandResponseServiceImpl(UserDatabaseService userDatabaseService, BotReplyMessage botReplyMessage) {
        this.userDatabaseService = userDatabaseService;
        this.botReplyMessage = botReplyMessage;
    }

    @Override
    public SendMessage handleTextCommand(Update update) {
        String receivedMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getFrom().getFirstName();
        String userName = update.getMessage().getFrom().getUserName();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        return getBotReplyMessage(receivedMessage, chatId, firstName, userName, message);
    }

    @Override
    public SendMessage handleButtonCommand(Update update, String callbackQuery) {
        SendMessage message = new SendMessage();

        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String firstName = update.getCallbackQuery().getFrom().getFirstName();
        String userName = update.getCallbackQuery().getFrom().getUserName();
        message.setChatId(chatId);

        return getBotReplyMessage(callbackData, chatId, firstName, userName, message);
    }

    private SendMessage getBotReplyMessage(String receivedMessage, Long chatId, String firstName, String userName, SendMessage message) {
        switch (receivedMessage) {
            case "/start" -> {
                message.setText(String.format(botReplyMessage.getStartMessage(), firstName));
                message.setReplyMarkup(Buttons.inlineKeyboardMarkup());
            }
            case "/help" -> message.setText(botReplyMessage.getHelpMessage());
            case "/register" -> {
                userDatabaseService.updateDB(chatId, firstName, userName);
                message.setText(botReplyMessage.getRegistrationMessage());
            }
            case "/getmydata" -> message.setText(userDatabaseService.getUserData(chatId).toString());
            case "/deletemydata" -> {
                userDatabaseService.deleteUser(chatId);
                message.setText(botReplyMessage.getDeletionMessage());
            }
            default -> log.info("Unexpected message");
        }
        return message;
    }

}
