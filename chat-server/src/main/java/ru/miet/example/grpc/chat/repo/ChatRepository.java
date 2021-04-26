package ru.miet.example.grpc.chat.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;

public interface ChatRepository extends ReactiveCrudRepository<Chat, Long> {
    @Query("SELECT chat.* FROM chat " +
            "   INNER JOIN chat.chat_members chat_members " +
            "       ON chat_members.chat_id = chat.id " +
            "WHERE chat_members.members_id = :membersId")
    Mono<Chat> findByIdAndMemberId(Long id, Long membersId);
}
