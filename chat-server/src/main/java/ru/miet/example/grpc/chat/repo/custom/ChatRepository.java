package ru.miet.example.grpc.chat.repo.custom;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.repo.generic.GenericChatRepository;

import java.util.Set;

public interface ChatRepository extends GenericChatRepository {
    /**
     * Searches chat with empty name by member ids:
     * {@param memberIds} size must be equal to the number of members of the chat.
     * It should be used to find chat with only 2 members when sending message.
     *
     * @param memberIds set of user ids
     * @return found chat or empty mono if nothing was found
     */
    Mono<Chat> searchByMemberIdsWithoutName(@NonNull Set<Long> memberIds);

    /**
     * Searches chat by member id ordered by last message's creation time of the chat
     *
     * @param memberId user id
     * @param pageable paging parameters
     * @return found chats without embedded params
     */
    Flux<Chat> searchByMemberId(@NonNull Long memberId, @NonNull Pageable pageable);

    /**
     * Gets chat members by chat id
     *
     * @param chatId chat id
     * @return found chat members
     */
    Flux<ChatUser> getChatMembers(@NonNull Long chatId, @NonNull Pageable pageable);
}
