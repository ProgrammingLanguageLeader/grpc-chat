package ru.miet.example.grpc.chat.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUserDetails;
import ru.miet.example.grpc.chat.exception.ChatException;
import ru.miet.example.grpc.chat.service.ChatUserDetailsService;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Component
public class JwtAuthFacade {
    private final JwtAdapter jwtAdapter;
    private final ChatUserDetailsService chatUserDetailsService;

    public Mono<ChatUserDetails> isUserAuthenticated(String checkToken) {
        return Mono.just(checkToken)
                .flatMap(token -> {
                    try {
                        return Mono.just(jwtAdapter.getUsernameFromToken(token));
                    } catch (RuntimeException e) {
                        return Mono.empty();
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(chatUserDetailsService::findByUsernameExt)
                .filter(userDetails -> jwtAdapter.validateToken(checkToken, userDetails))
                .switchIfEmpty(Mono.error(new ChatException("token is not valid")));
    }
}
