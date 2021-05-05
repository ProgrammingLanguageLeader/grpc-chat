package ru.miet.example.grpc.chat.entity.mapper;

import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.entity.column.ChatColumn;

import java.util.Map;

public final class ChatMapper {
    private ChatMapper() throws IllegalAccessException {
        throw new IllegalAccessException("utility class");
    }

    public static Chat fromMap(Map<String, Object> map) {
        Chat chat = new Chat();
        map.forEach((column, value) -> {
            if (ChatColumn.ID.equals(column)) {
                chat.setId((Long) value);
            } else if (ChatColumn.NAME.equals(column)) {
                chat.setName((String) value);
            }
        });
        return chat;
    }
}
