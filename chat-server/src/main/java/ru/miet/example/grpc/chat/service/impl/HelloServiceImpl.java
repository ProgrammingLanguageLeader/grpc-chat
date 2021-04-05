package ru.miet.example.grpc.chat.service.impl;

import io.grpc.stub.StreamObserver;
import ru.miet.example.grpc.chat.service.HelloService.HelloRequest;
import ru.miet.example.grpc.chat.service.HelloService.HelloResponse;
import ru.miet.example.grpc.chat.service.HelloWorldServiceGrpc.HelloWorldServiceImplBase;

import java.text.MessageFormat;

public class HelloServiceImpl extends HelloWorldServiceImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = MessageFormat.format("Hello, {0} {1}",
                request.getFirstName(),
                request.getLastName());
        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
