package ru.miet.example.grpc.chat.service.grpc;

import com.google.common.collect.Sets;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.entity.Message;
import ru.miet.example.grpc.chat.exception.ChatException;
import ru.miet.example.grpc.chat.jwt.JwtAuthFacade;
import ru.miet.example.grpc.chat.repo.custom.ChatRepository;
import ru.miet.example.grpc.chat.repo.custom.MessageRepository;
import ru.miet.example.grpc.chat.repo.generic.GenericChatUserRepository;
import ru.miet.example.grpc.chat.service.Common;
import ru.miet.example.grpc.chat.service.MessageServiceGrpc;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.GetMessagesRequest;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.GetMessagesResponse;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.SendMessageRequest;
import ru.miet.example.grpc.chat.service.MessageServiceOuterClass.SendMessageResponse;
import ru.miet.example.grpc.chat.utils.CommonUtils;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@GrpcService
public class MessageService extends MessageServiceGrpc.MessageServiceImplBase {
    private final JwtAuthFacade jwtAuthFacade;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final GenericChatUserRepository chatUserRepository;

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
                                .switchIfEmpty(Mono.error(new ChatException("recipientId is not valid")))
                                .flatMap(recipient -> {
                                    Chat chat = new Chat().withMembers(Sets.newHashSet(sender, recipient));
                                    return chatRepository.searchByMemberIdsWithoutName(Sets.newHashSet(sender.getId(), request.getRecipientId()))
                                            .switchIfEmpty(chatRepository.save(chat));
                                });
                    }
                    chatMono.switchIfEmpty(Mono.error(new ChatException("chatId is not valid")));
                    return Mono.zip(chatMono, Mono.just(sender));
                })
                .map(chatRecipient -> new Message()
                        .withChat(chatRecipient.getT1())
                        .withSender(chatRecipient.getT2())
                        .withText(request.getText())
                        .withCreatedAt(LocalDateTime.now()))
                .flatMap(messageRepository::save)
                .map(message -> {
                    Timestamp createdAtTimestamp = CommonUtils.convertToTimestamp(message.getCreatedAt());
                    ChatUser messageSender = message.getSender();
                    Common.User responseSender = Common.User.newBuilder()
                            .setId(messageSender.getId())
                            .setUsername(messageSender.getUsername())
                            .setFirstName(messageSender.getFirstName())
                            .setLastName(messageSender.getLastName())
                            .build();
                    return SendMessageResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.SUCCESS)
                            .setMessage(MessageServiceOuterClass.Message.newBuilder()
                                    .setId(message.getId())
                                    .setChatId(message.getChat().getId())
                                    .setSender(responseSender)
                                    .setText(message.getText())
                                    .setCreatedTime(createdAtTimestamp)
                                    .build())
                            .build();
                })
                .onErrorResume(throwable -> {
                    log.error("send error: ", throwable);
                    return Mono.just(SendMessageResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDesc(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .subscribe(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    log.debug("send end: response={}", response);
                });
    }

    @Override
    public void getMessages(GetMessagesRequest request, StreamObserver<GetMessagesResponse> responseObserver) {
        log.debug("getMessages start: request={}", request);
        jwtAuthFacade.isUserAuthenticated(request.getToken())
                .flatMapMany(senderUserDetails -> {
                    Common.PageParams pageParams = request.getPageParams();
                    PageRequest pageRequest = PageRequest.of(pageParams.getNumber(), pageParams.getSize());
                    return messageRepository.findByChatId(request.getChatId(), pageRequest);
                })
                .zipWith(Flux.range(0, Integer.MAX_VALUE))
                .reduce(GetMessagesResponse.newBuilder()
                                .setStatusCode(Common.StatusCode.SUCCESS)
                                .setChatId(request.getChatId()),
                        (responseBuilder, messageWithIndex) -> {
                            Message messageEntity = messageWithIndex.getT1();
                            Integer index = messageWithIndex.getT2();
                            ChatUser sender = messageEntity.getSender();
                            MessageServiceOuterClass.Message message = MessageServiceOuterClass.Message.newBuilder()
                                    .setId(messageEntity.getId())
                                    .setChatId(messageEntity.getChat().getId())
                                    .setCreatedTime(CommonUtils.convertToTimestamp(messageEntity.getCreatedAt()))
                                    .setText(messageEntity.getText())
                                    .setSender(Common.User.newBuilder()
                                            .setId(sender.getId())
                                            .setUsername(sender.getUsername())
                                            .setFirstName(sender.getFirstName())
                                            .setLastName(sender.getLastName())
                                            .build())
                                    .build();
                            return responseBuilder.addMessages(index, message);
                        })
                .map(GetMessagesResponse.Builder::build)
                .onErrorResume(throwable -> {
                    log.error("getMessages error: ", throwable);
                    return Mono.just(GetMessagesResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDesc(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .subscribe(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    log.debug("getMessages end: response={}", response);
                });
    }
}
