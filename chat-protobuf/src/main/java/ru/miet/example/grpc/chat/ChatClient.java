package ru.miet.example.grpc.chat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.miet.example.grpc.chat.service.HelloService.HelloRequest;
import ru.miet.example.grpc.chat.service.HelloService.HelloResponse;
import ru.miet.example.grpc.chat.service.HelloWorldServiceGrpc;
import ru.miet.example.grpc.chat.service.HelloWorldServiceGrpc.HelloWorldServiceBlockingStub;

import java.util.Scanner;

public class ChatClient {
    public static void main(String[] argv) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        HelloWorldServiceBlockingStub stub = HelloWorldServiceGrpc.newBlockingStub(channel);
        Scanner scanner = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("Your full name: ");
            userInput = scanner.nextLine();
            if (userInput.equals("!q")) {
                break;
            }
            String[] nameSplit = userInput.split(" ");
            if (nameSplit.length != 2) {
                System.out.println("incorrect input");
                continue;
            }
            HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                    .setFirstName(nameSplit[0])
                    .setLastName(nameSplit[1])
                    .build());
            System.out.println(helloResponse.toString());
        }
        System.out.println("Shutting down...");
        channel.shutdown();
    }
}
