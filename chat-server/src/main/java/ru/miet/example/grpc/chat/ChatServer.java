package ru.miet.example.grpc.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.miet.example.grpc.chat.props")
@EnableR2dbcRepositories("ru.miet.example.grpc.chat.repo.generic")
public class ChatServer {
    public static void main(String[] args) {
        SpringApplication.run(ChatServer.class, args);
    }
}
