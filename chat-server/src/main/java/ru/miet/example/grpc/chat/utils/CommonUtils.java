package ru.miet.example.grpc.chat.utils;

import com.google.protobuf.Timestamp;
import ru.miet.example.grpc.chat.exception.ChatException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    public static Timestamp convertToTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
