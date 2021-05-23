package ru.miet.example.grpc.chat.service.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.exception.ChatException;
import ru.miet.example.grpc.chat.jwt.JwtAdapter;
import ru.miet.example.grpc.chat.jwt.JwtAuthFacade;
import ru.miet.example.grpc.chat.service.AuthServiceGrpc;
import ru.miet.example.grpc.chat.service.AuthServiceOuterClass.LoginRequest;
import ru.miet.example.grpc.chat.service.AuthServiceOuterClass.LoginResponse;
import ru.miet.example.grpc.chat.service.AuthServiceOuterClass.RefreshTokenRequest;
import ru.miet.example.grpc.chat.service.ChatUserDetailsService;
import ru.miet.example.grpc.chat.service.Common;
import ru.miet.example.grpc.chat.utils.CommonUtils;

import static ru.miet.example.grpc.chat.service.AuthServiceOuterClass.RefreshTokenResponse;

@Slf4j
@AllArgsConstructor
@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {
    private final ChatUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAdapter jwtAdapter;
    private final JwtAuthFacade jwtAuthFacade;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        log.debug("login start: username={}", request.getUsername());
        userDetailsService.findByUsernameExt(request.getUsername())
                .filter(userDetails -> passwordEncoder.matches(request.getPassword(), userDetails.getPassword()))
                .map(userDetails -> {
                    String token = jwtAdapter.generateToken(userDetails);
                    ChatUser user = userDetails.getUser();
                    return LoginResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.SUCCESS)
                            .setToken(token)
                            .setUser(CommonUtils.convertToUserMessage(user))
                            .build();
                })
                .switchIfEmpty(Mono.error(new ChatException("login or password is wrong")))
                .onErrorResume(throwable -> {
                    log.error("login error: ", throwable);
                    return Mono.just(LoginResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDescription(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .doOnNext(response -> {
                    log.debug("login end: username={} responseToken={}", request.getUsername(), response.getToken());
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .subscribe();
    }

    @Override
    public void refreshToken(RefreshTokenRequest request, StreamObserver<RefreshTokenResponse> responseObserver) {
        log.debug("refreshToken start: requestToken={}", request.getToken());
        jwtAuthFacade.isUserAuthenticated(request.getToken())
                .map(userDetails -> {
                    String token = jwtAdapter.generateToken(userDetails);
                    return RefreshTokenResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.SUCCESS)
                            .setToken(token)
                            .build();
                })
                .onErrorResume(throwable -> {
                    log.error("refreshToken error: ", throwable);
                    return Mono.just(RefreshTokenResponse.newBuilder()
                            .setStatusCode(Common.StatusCode.ERROR)
                            .setStatusDescription(CommonUtils.getErrorMessage(throwable))
                            .build());
                })
                .doOnNext(response -> {
                    log.debug("refreshToken end: responseToken={}", response.getToken());
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                })
                .subscribe();
    }
}
