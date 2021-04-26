package ru.miet.example.grpc.chat.settings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSettings {
    @Value("${chat.jwt.token-validity-time:3600}")
    public long tokenValidityTime;

    @Value("${chat.jwt.secret}")
    public String secret;
}
