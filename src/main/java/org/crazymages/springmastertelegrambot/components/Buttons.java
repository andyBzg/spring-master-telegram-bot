package org.crazymages.springmastertelegrambot.components;

import org.crazymages.springmastertelegrambot.enums.Command;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {

    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton(Command.START.getName());
    private static final InlineKeyboardButton REGISTER_BUTTON = new InlineKeyboardButton(Command.REGISTER.getName());
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton(Command.HELP.getName());

    private Buttons() {
    }

    public static InlineKeyboardMarkup inlineKeyboardMarkup() {
        START_BUTTON.setCallbackData(Command.START.getCmd());
        HELP_BUTTON.setCallbackData(Command.HELP.getCmd());
        REGISTER_BUTTON.setCallbackData(Command.REGISTER.getCmd());

        List<InlineKeyboardButton> rowInLine = List.of(START_BUTTON, HELP_BUTTON);
        List<InlineKeyboardButton> secondRow = List.of(REGISTER_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInLine, secondRow);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        markupInLine.setKeyboard(rowsInLine);

        return markupInLine;
    }

}
