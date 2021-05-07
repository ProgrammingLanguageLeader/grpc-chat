package ru.miet.example.grpc.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.relational.core.mapping.Embedded;
import ru.miet.example.grpc.chat.entity.column.ChatColumn;
import ru.miet.example.grpc.chat.entity.column.ChatMemberColumn;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {

    @org.springframework.data.annotation.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ChatColumn.ID)
    private Long id;

    @Column(name = ChatColumn.NAME)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "chat_member",
            joinColumns = @JoinColumn(name = ChatMemberColumn.CHAT_ID),
            inverseJoinColumns = @JoinColumn(name = ChatMemberColumn.MEMBER_ID))
    private Set<ChatUser> members = new HashSet<>();
}
