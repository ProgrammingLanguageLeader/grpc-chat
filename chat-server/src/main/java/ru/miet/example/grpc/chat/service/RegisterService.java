package ru.miet.example.grpc.chat.service;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.repo.generic.ChatUserRepository;
import ru.miet.example.grpc.chat.service.RegisterServiceOuterClass.RegisterRequest;
import ru.miet.example.grpc.chat.service.RegisterServiceOuterClass.RegisterResponse;

@Slf4j
@AllArgsConstructor
@GrpcService
public class RegisterService extends RegisterServiceGrpc.RegisterServiceImplBase {

    static class RegisterServiceException extends RuntimeException {
        RegisterServiceException(String message) {
            super(message);
        }
    }

    private final ChatUserRepository chatUserRepository;
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
                        return Mono.error(new RegisterServiceException("That username is taken. Try another"));
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
                            .setStatusDesc(throwable.getMessage())
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
