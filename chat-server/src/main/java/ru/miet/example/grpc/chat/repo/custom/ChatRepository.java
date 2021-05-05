package ru.miet.example.grpc.chat.repo.custom;

import lombok.NonNull;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.repo.generic.GenericChatRepository;

import java.util.Set;

public interface ChatRepository extends GenericChatRepository {
    Mono<Chat> searchByMemberIds(@NonNull Set<Long> memberId);
}
