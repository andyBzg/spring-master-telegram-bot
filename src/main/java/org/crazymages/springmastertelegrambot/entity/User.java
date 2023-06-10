package org.crazymages.springmastertelegrambot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tg_users")
public class User {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "username")
    private String userName;



}
