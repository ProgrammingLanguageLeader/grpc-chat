package ru.miet.example.grpc.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.miet.example.grpc.chat.entity.column.ChatUserColumn;

import javax.persistence.*;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_user")
public class ChatUser {

    @org.springframework.data.annotation.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ChatUserColumn.ID)
    private Long id;

    @Column(name = ChatUserColumn.USERNAME, nullable = false, unique = true)
    private String username;

    @Column(name = ChatUserColumn.PASSWORD, nullable = false)
    private String password;

    @Column(name = ChatUserColumn.FIRST_NAME)
    private String firstName;

    @Column(name = ChatUserColumn.LAST_NAME)
    private String lastName;

    @Column(name = ChatUserColumn.ADDITIONAL_DESC)
    private String additionalDescription;
}
