package ru.miet.example.grpc.chat.settings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GRPCSettings {
    @Value("${chat.grpc.keep-alive-time:5}")
    public int keepAliveTime;

    @Value("${chat.grpc.keep-alive-timeout:30}")
    public int keepAliveTimeout;
}
