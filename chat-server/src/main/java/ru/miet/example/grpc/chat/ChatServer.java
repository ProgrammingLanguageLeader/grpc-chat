package ru.miet.example.grpc.chat;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.miet.example.grpc.chat.service.impl.HelloServiceImpl;

import java.io.IOException;

@SpringBootApplication
public class ChatServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(ChatServer.class, args);
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new HelloServiceImpl())
                .build();
        server.start();
        server.awaitTermination();
    }

}
