package ru.miet.example.grpc.chat.repo.generic;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.miet.example.grpc.chat.entity.Message;

public interface GenericMessageRepository extends ReactiveCrudRepository<Message, Long> {
}
