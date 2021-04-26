package ru.miet.example.grpc.chat.service;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.entity.Message;
import ru.miet.example.grpc.chat.jwt.JwtAuthFacade;
import ru.miet.example.grpc.chat.repo.ChatRepository;
import ru.miet.example.grpc.chat.repo.MessageRepository;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.GetMessageRequest;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.GetMessageResponse;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.SendMessageRequest;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.SendMessageResponse;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@GrpcService
public class MessageService extends MessageServiceGrpc.MessageServiceImplBase {
    private final JwtAuthFacade jwtAuthFacade;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @Override
    public void send(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
        log.info("send start: request={}", request);
        jwtAuthFacade.isUserAuthenticated(request.getToken())
                .flatMap(chatUserDetails -> {
                    Long membersId = chatUserDetails.getUser().getId();
                    Mono<Chat> chatMono = chatRepository.findByIdAndMemberId(request.getChatId(), membersId);
                    return Mono.zip(Mono.just(chatUserDetails.getUser()), chatMono);
                })
                .map(userAndChatTuple -> new Message()
                        .withSender(userAndChatTuple.getT1())
                        .withText(request.getText())
                        .withChat(userAndChatTuple.getT2())
                        .withCreatedAt(LocalDateTime.now()))
                .doOnNext(messageRepository::save)
                .subscribe();
    }

    @Override
    public void get(GetMessageRequest request, StreamObserver<GetMessageResponse> responseObserver) {
        log.info("get start: request={}", request);
        super.get(request, responseObserver);
    }
}
