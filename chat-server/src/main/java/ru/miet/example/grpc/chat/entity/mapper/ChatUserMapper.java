package ru.miet.example.grpc.chat.entity.mapper;

import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.entity.column.ChatUserColumn;

import java.util.Map;

public final class ChatUserMapper {
    private ChatUserMapper() throws IllegalAccessException {
        throw new IllegalAccessException("utility class");
    }

    public static ChatUser fromMap(Map<String, Object> map) {
        ChatUser chatUser = new ChatUser();
        map.forEach((column, value) -> {
            if (ChatUserColumn.ID.equals(column)) {
                chatUser.setId((Long) value);
            } else if (ChatUserColumn.USERNAME.equals(column)) {
                chatUser.setUsername((String) value);
            } else if (ChatUserColumn.PASSWORD.equals(column)) {
                chatUser.setPassword((String) value);
            } else if (ChatUserColumn.FIRST_NAME.equals(column)) {
                chatUser.setFirstName((String) value);
            } else if (ChatUserColumn.LAST_NAME.equals(column)) {
                chatUser.setLastName((String) value);
            } else if (ChatUserColumn.ADDITIONAL_DESC.equals(column)) {
                chatUser.setAdditionalDescription((String) value);
            }
        });
        return chatUser;
    }
}
