package ru.miet.example.grpc.chat.service;

import com.google.common.collect.Sets;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.entity.Message;
import ru.miet.example.grpc.chat.jwt.JwtAuthFacade;
import ru.miet.example.grpc.chat.repo.custom.ChatRepository;
import ru.miet.example.grpc.chat.repo.generic.ChatUserRepository;
import ru.miet.example.grpc.chat.repo.generic.MessageRepository;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.GetMessageRequest;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.GetMessageResponse;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.SendMessageRequest;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.SendMessageResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@AllArgsConstructor
@GrpcService
public class MessageService extends MessageServiceGrpc.MessageServiceImplBase {

    static class MessageServiceException extends RuntimeException {
        MessageServiceException(String message) {
            super(message);
        }
    }

    private final JwtAuthFacade jwtAuthFacade;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatUserRepository chatUserRepository;

    @Override
    public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
        log.debug("send start: request={}", request);
        jwtAuthFacade.isUserAuthenticated(request.getToken())
                .flatMap(senderUserDetails -> {
                    ChatUser sender = senderUserDetails.getUser();
                    Mono<Chat> chatMono = Mono.empty();
                    if (request.getChatId() != 0) {
                        chatMono = chatRepository.findById(request.getChatId());
                    } else if (request.getRecipientId() != 0) {
                        chatMono = chatUserRepository.findById(request.getRecipientId())
                                .switchIfEmpty(Mono.error(new MessageServiceException("recipientId is not valid")))
                                .flatMap(recipient -> {
                                    Chat chat = new Chat().withMembers(Sets.newHashSet(sender, recipient));
                                    return chatRepository.searchByMemberIds(Sets.newHashSet(sender.getId(), request.getRecipientId()))
                                            .switchIfEmpty(chatRepository.save(chat));
                                });
                    }
                    chatMono.switchIfEmpty(Mono.error(new MessageServiceException("chatId is not valid")));
                    return Mono.zip(chatMono, Mono.just(sender));
                })
                .map(chatRecipient -> new Message()
                        .withChat(chatRecipient.getT1())
                        .withSender(chatRecipient.getT2())
                        .withText(request.getText())
                        .withCreatedAt(LocalDateTime.now()))
                .flatMap(messageRepository::save)
                .map(message -> {
                    Instant createdAtInstant = message.getCreatedAt().toInstant(ZoneOffset.UTC);
                    Timestamp createdAtTimestamp = Timestamp.newBuilder()
                            .setSeconds(createdAtInstant.getEpochSecond())
                            .setNanos(createdAtInstant.getNano())
                            .build();
                    return SendMessageResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.SUCCESS)
                            .setMessage(MessageServiceOuterClass.Message.newBuilder()
                                    .setId(message.getId())
                                    .setChatId(message.getChat().getId())
                                    .setSenderId(message.getSender().getId())
                                    .setText(message.getText())
                                    .setCreatedTime(createdAtTimestamp)
                                    .build())
                            .build();
                })
                .onErrorResume(throwable -> {
                    log.error("send error: ", throwable);
                    return Mono.just(SendMessageResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDesc(throwable.getMessage())
                            .build());
                })
                .doOnNext(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    log.debug("send end: response={}", response);
                })
                .subscribe();
    }

    @Override
    public void getMessage(GetMessageRequest request, StreamObserver<GetMessageResponse> responseObserver) {
        log.info("get start: request={}", request);
        responseObserver.onCompleted();
    }
}
