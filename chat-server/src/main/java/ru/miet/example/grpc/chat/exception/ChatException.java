package ru.miet.example.grpc.chat.exception;

public class ChatException extends RuntimeException {
    public ChatException(String message) {
        super(message);
    }

    ChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
