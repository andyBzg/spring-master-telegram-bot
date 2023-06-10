package org.crazymages.springmastertelegrambot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {

    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton(ButtonData.START.getName());
    private static final InlineKeyboardButton REGISTER_BUTTON = new InlineKeyboardButton(ButtonData.REGISTER.getName());
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton(ButtonData.HELP.getName());

    private Buttons() {
    }

    public static InlineKeyboardMarkup inlineKeyboardMarkup() {
        START_BUTTON.setCallbackData(ButtonData.START.getCallbackData());
        HELP_BUTTON.setCallbackData(ButtonData.HELP.getCallbackData());
        REGISTER_BUTTON.setCallbackData(ButtonData.REGISTER.getCallbackData());

        List<InlineKeyboardButton> rowInLine = List.of(START_BUTTON, HELP_BUTTON);
        List<InlineKeyboardButton> secondRow = List.of(REGISTER_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInLine, secondRow);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        markupInLine.setKeyboard(rowsInLine);

        return markupInLine;
    }

}
