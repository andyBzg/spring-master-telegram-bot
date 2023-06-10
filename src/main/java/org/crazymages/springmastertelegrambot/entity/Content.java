package org.crazymages.springmastertelegrambot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "content_table")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "name")
    private String name;

    @Column(name = "applying")
    private String applying;

    @Column(name = "description")
    private String description;

}
