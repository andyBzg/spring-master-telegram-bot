package org.crazymages.springmastertelegrambot;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.crazymages.springmastertelegrambot.config.BotConfig;
import org.crazymages.springmastertelegrambot.service.CommandResponseService;
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
    private final CommandResponseService commandResponseService;


    @Autowired
    public SpringMasterBot(BotConfig config, CommandResponseService commandResponseService) {
        this.config = config;
        this.commandResponseService = commandResponseService;
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
        SendMessage message;
        if (update.hasMessage() && update.getMessage().hasText() && update.hasCallbackQuery()) {
            message = commandResponseService.handleTextCommand(update);
            executeSendMessage(message);
        } else if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            message = commandResponseService.handleButtonCommand(update, callbackQuery);
            executeSendMessage(message);
        }
    }

    public void executeSendMessage(SendMessage message) {
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
