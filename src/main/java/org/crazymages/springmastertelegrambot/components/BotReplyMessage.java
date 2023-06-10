package org.crazymages.springmastertelegrambot.components;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:/config/message.properties")
@Data
public class BotReplyMessage {

    @Value("${message.start}")
    private String startMessage;

    @Value("${message.help}")
    private String helpMessage;

    @Value("${message.registration}")
    private String registrationMessage;

    @Value("${message.deletion}")
    private String deletionMessage;
}
