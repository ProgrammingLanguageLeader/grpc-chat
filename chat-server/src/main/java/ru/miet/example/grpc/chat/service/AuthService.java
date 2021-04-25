package ru.miet.example.grpc.chat.service;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.jwt.JWTAdapter;
import ru.miet.example.grpc.chat.service.AuthServiceOuterClass.LoginRequest;
import ru.miet.example.grpc.chat.service.AuthServiceOuterClass.LoginResponse;
import ru.miet.example.grpc.chat.service.AuthServiceOuterClass.RefreshTokenRequest;

import java.util.Objects;

import static ru.miet.example.grpc.chat.service.AuthServiceOuterClass.RefreshTokenResponse;

@Slf4j
@AllArgsConstructor
@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {

    public static class AuthServiceException extends RuntimeException {
        AuthServiceException(String message) {
            super(message);
        }
    }

    private final ChatUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTAdapter jwtAdapter;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        log.info("login start: username={}", request.getUsername());
        userDetailsService.findByUsername(request.getUsername())
                .filter(userDetails -> passwordEncoder.matches(request.getPassword(), userDetails.getPassword()))
                .map(userDetails -> {
                    String token = jwtAdapter.generateToken(userDetails);
                    return LoginResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.SUCCESS)
                            .setToken(token)
                            .build();
                })
                .switchIfEmpty(Mono.error(new AuthServiceException("login or password is wrong")))
                .onErrorResume(throwable -> {
                    log.error("login error: ", throwable);
                    return Mono.just(LoginResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDescription(throwable.getMessage())
                            .build());
                })
                .doOnNext(response -> {
                    log.info("login end: username={} responseToken={}", request.getUsername(), response.getToken());
                    responseObserver.onNext(response);
                })
                .subscribe();
    }

    @Override
    public void refreshToken(RefreshTokenRequest request, StreamObserver<RefreshTokenResponse> responseObserver) {
        log.info("refreshToken start: requestToken={}", request.getToken());
        Mono.just(request.getToken())
                .flatMap(token -> {
                    try {
                        return Mono.just(jwtAdapter.getUsernameFromToken(request.getToken()));
                    } catch (RuntimeException e) {
                        return Mono.empty();
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(userDetailsService::findByUsername)
                .filter(userDetails -> jwtAdapter.validateToken(request.getToken(), userDetails))
                .map(userDetails -> {
                    String token = jwtAdapter.generateToken(userDetails);
                    return RefreshTokenResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.SUCCESS)
                            .setToken(token)
                            .build();
                })
                .switchIfEmpty(Mono.error(new AuthServiceException("token is not valid")))
                .onErrorResume(throwable -> {
                    log.error("refreshToken error: ", throwable);
                    return Mono.just(RefreshTokenResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDescription(throwable.getMessage())
                            .build());
                })
                .doOnNext(response -> {
                    log.info("refreshToken end: username={} responseToken={}", request.getToken(), response.getToken());
                    responseObserver.onNext(response);
                })
                .subscribe();
    }
}
