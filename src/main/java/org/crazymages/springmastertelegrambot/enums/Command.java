package org.crazymages.springmastertelegrambot.enums;

public enum Command {

    START("Start", "/start", "start bot"),
    REGISTER("Register", "/register", "register new user"),
    HELP("Help", "/help", "bot info");

    final String name;
    final String cmd;
    final String description;

    Command(String name, String cmd, String description) {
        this.name = name;
        this.cmd = cmd;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDescription() {
        return description;
    }
}
