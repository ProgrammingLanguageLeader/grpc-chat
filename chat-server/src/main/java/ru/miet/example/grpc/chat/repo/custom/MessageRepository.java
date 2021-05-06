package ru.miet.example.grpc.chat.repo.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import ru.miet.example.grpc.chat.entity.Message;
import ru.miet.example.grpc.chat.repo.generic.GenericMessageRepository;

public interface MessageRepository extends GenericMessageRepository {
    Flux<Message> findByChatId(Long chatId, Sort sort, Pageable pageable);
}
