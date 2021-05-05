package ru.miet.example.grpc.chat.repo.generic;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;

public interface ChatUserRepository extends ReactiveCrudRepository<ChatUser, Long> {
    Mono<ChatUser> findByUsername(String username);
}
