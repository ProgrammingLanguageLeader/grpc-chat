package ru.miet.example.grpc.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Value("${chat.grpc.port}")
    public Integer port;
}
