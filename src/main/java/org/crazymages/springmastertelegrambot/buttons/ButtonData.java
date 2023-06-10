package org.crazymages.springmastertelegrambot.buttons;

public enum ButtonData {

    START("Start", "/start"),
    HELP("Help", "/help"),
    REGISTER("Register", "/register");

    private final String name;
    private final String callbackData;

    ButtonData(String name, String callbackData) {
        this.name = name;
        this.callbackData = callbackData;
    }

    public String getName() {
        return name;
    }

    public String getCallbackData() {
        return callbackData;
    }
}
