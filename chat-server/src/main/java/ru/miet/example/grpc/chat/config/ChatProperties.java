package ru.miet.example.grpc.chat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "chat")
@Getter
@Setter
public class ChatProperties {
    private GRPCProperties grpc;

    @Getter
    @Setter
    public static class GRPCProperties {
        private int port = 8080;
    }
}
