package ru.miet.example.grpc.chat.service.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.jwt.JwtAuthFacade;
import ru.miet.example.grpc.chat.repo.generic.GenericChatUserRepository;
import ru.miet.example.grpc.chat.service.Common;
import ru.miet.example.grpc.chat.service.UserSearchServiceGrpc;
import ru.miet.example.grpc.chat.service.UserSearchServiceOuterClass.UserSearchRequest;
import ru.miet.example.grpc.chat.service.UserSearchServiceOuterClass.UserSearchResponse;
import ru.miet.example.grpc.chat.utils.CommonUtils;

import java.text.MessageFormat;

@Slf4j
@AllArgsConstructor
@GrpcService
public class UserSearchService extends UserSearchServiceGrpc.UserSearchServiceImplBase {
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final JwtAuthFacade jwtAuthFacade;
    private final GenericChatUserRepository chatUserRepository;

    @Override
    public void search(UserSearchRequest request, StreamObserver<UserSearchResponse> responseObserver) {
        log.debug("search start: request={}", request);
        jwtAuthFacade.isUserAuthenticated(request.getToken())
                .flatMapMany(senderUserDetails -> {
                    PageRequest pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);
                    String usernameLikeExpression = MessageFormat.format("%{0}%", request.getUsername());
                    return chatUserRepository.findByUsernameLike(usernameLikeExpression, pageable);
                })
                .zipWith(Flux.range(0, Integer.MAX_VALUE))
                .reduce(UserSearchResponse.newBuilder()
                                .setStatusCode(Common.StatusCode.SUCCESS),
                        (responseBuilder, userWithIndex) -> {
                            ChatUser userEntity = userWithIndex.getT1();
                            Integer index = userWithIndex.getT2();
                            return responseBuilder.addUsers(index, CommonUtils.convertToUserMessage(userEntity));
                        })
                .map(UserSearchResponse.Builder::build)
                .onErrorResume(throwable -> {
                    log.error("getMessages error: ", throwable);
                    return Mono.just(UserSearchResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDesc(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .subscribe(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    log.debug("search end: response={}", response);
                });
    }
}
