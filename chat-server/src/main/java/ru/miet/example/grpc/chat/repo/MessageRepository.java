package ru.miet.example.grpc.chat.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.miet.example.grpc.chat.entity.Message;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
}
