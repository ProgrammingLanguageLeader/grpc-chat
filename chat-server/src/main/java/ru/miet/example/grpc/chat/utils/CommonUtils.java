package ru.miet.example.grpc.chat.utils;

import ru.miet.example.grpc.chat.exception.ChatException;

public final class CommonUtils {
    private CommonUtils() throws IllegalAccessException {
        throw new IllegalAccessException("utility class");
    }

    public static <T extends Exception> String getErrorMessage(Throwable throwable) {
        return getErrorMessage(throwable, ChatException.class);
    }

    public static <T extends Exception> String getErrorMessage(Throwable throwable, Class<T> applicableException) {
        String errorMessage = "Internal server error";
        if (applicableException.isInstance(throwable) && throwable.getMessage() != null) {
            errorMessage = throwable.getMessage();
        }
        return errorMessage;
    }
}
