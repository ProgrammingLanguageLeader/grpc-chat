package ru.miet.example.grpc.chat.service.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.exception.ChatException;
import ru.miet.example.grpc.chat.repo.generic.GenericChatUserRepository;
import ru.miet.example.grpc.chat.service.Common;
import ru.miet.example.grpc.chat.service.RegisterServiceGrpc;
import ru.miet.example.grpc.chat.service.RegisterServiceOuterClass.RegisterRequest;
import ru.miet.example.grpc.chat.service.RegisterServiceOuterClass.RegisterResponse;
import ru.miet.example.grpc.chat.utils.CommonUtils;

@Slf4j
@AllArgsConstructor
@GrpcService
public class RegisterService extends RegisterServiceGrpc.RegisterServiceImplBase {
    private final GenericChatUserRepository chatUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request,
                         StreamObserver<RegisterResponse> responseObserver) {
        log.debug("register call start: username={}", request.getUsername());
        ChatUser newUser = new ChatUser()
                .withUsername(request.getUsername())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withAdditionalDescription(request.getAdditionalDescription());
        chatUserRepository.findByUsername(request.getUsername())
                .defaultIfEmpty(newUser)
                .flatMap(user -> {
                    if (user.getId() != null) {
                        return Mono.error(new ChatException("That username is taken. Try another"));
                    }
                    return chatUserRepository.save(newUser)
                            .map(createdUser -> {
                                log.info("user {} registered", createdUser.getUsername());
                                return RegisterResponse.newBuilder()
                                        .setStatusCode(Common.StatusCode.SUCCESS)
                                        .build();
                            });
                })
                .onErrorResume(throwable -> {
                    log.error("register error: ", throwable);
                    return Mono.just(RegisterResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDesc(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .doOnNext(response -> {
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    log.debug("register call end: username={} responseStatus={}", request.getUsername(), response.getStatusCode());
                })
                .subscribe();
    }
}
