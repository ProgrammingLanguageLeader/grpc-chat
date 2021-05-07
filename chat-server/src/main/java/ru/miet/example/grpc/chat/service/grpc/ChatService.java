package ru.miet.example.grpc.chat.service.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.jwt.JwtAuthFacade;
import ru.miet.example.grpc.chat.repo.custom.ChatRepository;
import ru.miet.example.grpc.chat.service.ChatServiceGrpc;
import ru.miet.example.grpc.chat.service.ChatServiceOuterClass;
import ru.miet.example.grpc.chat.service.ChatServiceOuterClass.GetChatsRequest;
import ru.miet.example.grpc.chat.service.ChatServiceOuterClass.GetMembersRequest;
import ru.miet.example.grpc.chat.service.Common;
import ru.miet.example.grpc.chat.service.Common.PageParams;
import ru.miet.example.grpc.chat.utils.CommonUtils;

import static ru.miet.example.grpc.chat.service.ChatServiceOuterClass.GetChatsResponse;
import static ru.miet.example.grpc.chat.service.ChatServiceOuterClass.GetMembersResponse;

@Slf4j
@AllArgsConstructor
@GrpcService
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    private final JwtAuthFacade jwtAuthFacade;
    private final ChatRepository chatRepository;

    @Override
    public void getChats(GetChatsRequest request, StreamObserver<GetChatsResponse> responseObserver) {
        log.debug("getChats start: request={}", request);
        jwtAuthFacade.isUserAuthenticated(request.getToken())
                .flatMapMany(chatUserDetails -> {
                    ChatUser user = chatUserDetails.getUser();
                    PageParams pageParams = request.getPageParams();
                    PageRequest pageRequest = PageRequest.of(pageParams.getNumber(), pageParams.getSize());
                    return chatRepository.searchByMemberId(user.getId(), pageRequest);
                })
                .zipWith(Flux.range(0, Integer.MAX_VALUE))
                .reduce(GetChatsResponse.newBuilder(),
                        (responseBuilder, chatWithIndex) -> {
                            Chat chat = chatWithIndex.getT1();
                            Integer index = chatWithIndex.getT2();
                            String name = chat.getName();
                            if (name == null) {
                                name = "";
                            }
                            ChatServiceOuterClass.Chat chatMsg = ChatServiceOuterClass.Chat.newBuilder()
                                    .setId(chat.getId())
                                    .setName(name)
                                    .build();
                            return responseBuilder.addChats(index, chatMsg);
                        })
                .map(GetChatsResponse.Builder::build)
                .onErrorResume(throwable -> {
                    log.error("getChats error: ", throwable);
                    return Mono.just(GetChatsResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDesc(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .subscribe(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    log.debug("getChats end: response={}", response);
                });
    }

    @Override
    public void getMembers(GetMembersRequest request, StreamObserver<GetMembersResponse> responseObserver) {
        log.debug("getMembers start: request={}", request);
        jwtAuthFacade.isUserAuthenticated(request.getToken())
                .flatMapMany(chatUserDetails -> {
                    PageParams pageParams = request.getPageParams();
                    PageRequest pageRequest = PageRequest.of(pageParams.getNumber(), pageParams.getSize());
                    return chatRepository.getChatMembers(request.getChatId(), pageRequest);
                })
                .zipWith(Flux.range(0, Integer.MAX_VALUE))
                .reduce(GetMembersResponse.newBuilder(),
                        (responseBuilder, memberWithIndex) -> {
                            ChatUser member = memberWithIndex.getT1();
                            Integer index = memberWithIndex.getT2();
                            Common.User user = Common.User.newBuilder()
                                    .setId(member.getId())
                                    .setUsername(member.getUsername())
                                    .setFirstName(member.getFirstName())
                                    .setLastName(member.getLastName())
                                    .build();
                            return responseBuilder.addMembers(index, user);
                        })
                .map(GetMembersResponse.Builder::build)
                .onErrorResume(throwable -> {
                    log.error("getMembers error: ", throwable);
                    return Mono.just(GetMembersResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDesc(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .subscribe(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    log.debug("getMembers end: response={}", response);
                });
    }
}
