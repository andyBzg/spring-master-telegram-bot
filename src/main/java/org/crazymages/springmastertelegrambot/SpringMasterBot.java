package org.crazymages.springmastertelegrambot;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.crazymages.springmastertelegrambot.buttons.Buttons;
import org.crazymages.springmastertelegrambot.config.BotConfig;
import org.crazymages.springmastertelegrambot.database.UserDatabaseService;
import org.crazymages.springmastertelegrambot.entity.User;
import org.crazymages.springmastertelegrambot.enums.Text;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String receivedMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getFrom().getFirstName();
            String userName = update.getMessage().getFrom().getUserName();

            handleCommand(receivedMessage, chatId, firstName, userName);
        } else if (update.hasCallbackQuery()) {
            handleButtonCallback(update);
        }
    }

    public void handleButtonCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String firstName = update.getCallbackQuery().getFrom().getFirstName();
        String userName = update.getCallbackQuery().getFrom().getUserName();

        handleCommand(callbackData, chatId, firstName, userName);
    }

    public void startBot(long chatId, String firstName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format(Text.START_TEXT, firstName));
        message.setReplyMarkup(Buttons.inlineKeyboardMarkup());
        executeSendMessage(message);
    }

    public void sendHelpText(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        executeSendMessage(message);
    }

    public void executeSendMessage(SendMessage message) {
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void handleCommand(String receivedMessage, long chatId, String firstName, String userName) {

        switch (receivedMessage) {
            case "/start" -> startBot(chatId, firstName);
            case "/help" -> sendHelpText(chatId, Text.HELP_TEXT);
            case "/register" -> userDatabaseService.updateDB(chatId, firstName, userName);
            case "/getmydata" -> getUserDataFromDB(chatId);
            case "/deletemydata" -> userDatabaseService.deleteUser(chatId);
            default -> log.info("Unexpected message");
        }
    }

    private void getUserDataFromDB(long chatId) {
        User user = userDatabaseService.getUserData(chatId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(user.toString());
        executeSendMessage(sendMessage);
    }
}
