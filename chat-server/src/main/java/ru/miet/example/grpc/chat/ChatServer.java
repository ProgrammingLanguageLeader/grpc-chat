package ru.miet.example.grpc.chat;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.miet.example.grpc.chat.config.ChatConfig;
import ru.miet.example.grpc.chat.service.impl.HelloServiceImpl;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.miet.example.grpc.chat.config")
public class ChatServer implements CommandLineRunner {

    private final ChatConfig chatConfig;

    public ChatServer(ChatConfig chatConfig) {
        this.chatConfig = chatConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatServer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("server.grpc.port = " + chatConfig.port);
        Server server = ServerBuilder
                .forPort(chatConfig.port)
                .addService(new HelloServiceImpl())
                .build();
        server.start();
        server.awaitTermination();
    }
}
