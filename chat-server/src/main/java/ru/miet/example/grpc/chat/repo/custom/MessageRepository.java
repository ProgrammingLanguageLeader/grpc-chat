package ru.miet.example.grpc.chat.repo.custom;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import ru.miet.example.grpc.chat.entity.Message;
import ru.miet.example.grpc.chat.repo.generic.GenericMessageRepository;

import java.util.Set;

public interface MessageRepository extends GenericMessageRepository {
    Flux<Message> findByChatId(Long chatId, Pageable pageable);

    Flux<Message> findLastMessagesByChatIds(Set<Long> chatIds);
}
