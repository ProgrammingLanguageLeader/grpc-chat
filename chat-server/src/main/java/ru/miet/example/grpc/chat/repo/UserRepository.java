package ru.miet.example.grpc.chat.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;

public interface UserRepository extends ReactiveCrudRepository<ChatUser, Long> {
    Mono<ChatUser> findByUsername(String username);
}
