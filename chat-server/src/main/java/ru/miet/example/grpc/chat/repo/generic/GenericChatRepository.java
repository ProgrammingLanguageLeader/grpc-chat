package ru.miet.example.grpc.chat.repo.generic;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.miet.example.grpc.chat.entity.Chat;

public interface GenericChatRepository extends ReactiveCrudRepository<Chat, Long> {
}
