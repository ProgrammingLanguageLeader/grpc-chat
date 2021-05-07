package ru.miet.example.grpc.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.miet.example.grpc.chat.entity.column.MessageColumn;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {

    @org.springframework.data.annotation.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MessageColumn.ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = MessageColumn.CHAT_ID)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = MessageColumn.SENDER_ID)
    private ChatUser sender;

    @Column(name = MessageColumn.TEXT, nullable = false)
    private String text;

    @Column(name = MessageColumn.CREATED_AT, nullable = false)
    private LocalDateTime createdAt;
}
