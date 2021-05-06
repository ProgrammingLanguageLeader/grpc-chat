package ru.miet.example.grpc.chat.repo.generic;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;

public interface GenericChatUserRepository extends ReactiveCrudRepository<ChatUser, Long> {
    Mono<ChatUser> findByUsername(String username);
}
