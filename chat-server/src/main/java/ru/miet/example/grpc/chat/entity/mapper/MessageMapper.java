package ru.miet.example.grpc.chat.entity.mapper;

import ru.miet.example.grpc.chat.entity.Message;
import ru.miet.example.grpc.chat.entity.column.MessageColumn;

import java.time.LocalDateTime;
import java.util.Map;

public final class MessageMapper {
    private MessageMapper() throws IllegalAccessException {
        throw new IllegalAccessException("utility class");
    }

    public static Message fromMap(Map<String, Object> map) {
        Message message = new Message();
        map.forEach((column, value) -> {
            if (MessageColumn.ID.equals(column)) {
                message.setId((Long) value);
            } else if (MessageColumn.CREATED_AT.equals(column)) {
                message.setCreatedAt((LocalDateTime) value);
            } else if (MessageColumn.TEXT.equals(column)) {
                message.setText((String) value);
            }
        });
        return message;
    }
}
