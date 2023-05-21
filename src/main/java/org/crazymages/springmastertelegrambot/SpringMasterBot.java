package org.crazymages.springmastertelegrambot;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.crazymages.springmastertelegrambot.components.Buttons;
import org.crazymages.springmastertelegrambot.config.BotConfig;
import org.crazymages.springmastertelegrambot.database.UserDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class SpringMasterBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserDatabaseService userDatabaseService;


    @Autowired
    public SpringMasterBot(BotConfig config, UserDatabaseService userDatabaseService) {
        this.config = config;
        this.userDatabaseService = userDatabaseService;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {

        long chatId;
        long userId;
        String firstName;
        String userName;
        String receivedMessage;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            firstName = update.getMessage().getFrom().getFirstName();
            userName = update.getMessage().getFrom().getUserName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, firstName, userName);
            }
        }
        else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userName = update.getCallbackQuery().getFrom().getUserName();
            firstName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtils(receivedMessage, chatId, firstName, userName);
        }

    }

    private void botAnswerUtils(String receivedMessage, long chatId, String firstName, String userName) {

        switch (receivedMessage) {
            case "/start" -> startBot(chatId, firstName);
            case "/help" -> sendHelpText(chatId, "help command");
            case "/register" -> userDatabaseService.updateDB(chatId, firstName, userName);
            default -> log.info("Unexpected message");
        }
    }

    private void sendHelpText(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hi, " + userName + "! I'm Spring Master Bot.");
        message.setReplyMarkup(Buttons.inlineKeyboardMarkup());

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
