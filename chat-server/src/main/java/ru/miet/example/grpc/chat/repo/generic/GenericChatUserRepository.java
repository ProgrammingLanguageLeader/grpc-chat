package ru.miet.example.grpc.chat.repo.generic;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUser;

public interface GenericChatUserRepository extends ReactiveCrudRepository<ChatUser, Long> {
    Mono<ChatUser> findByUsername(String username);

    Flux<ChatUser> findByUsernameLike(String username, Pageable pageable);
}
