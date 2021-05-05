package ru.miet.example.grpc.chat.repo.custom.impl;

import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Chat;
import ru.miet.example.grpc.chat.entity.ChatUser;
import ru.miet.example.grpc.chat.entity.column.ChatColumn;
import ru.miet.example.grpc.chat.entity.column.ChatMemberColumn;
import ru.miet.example.grpc.chat.entity.column.ChatUserColumn;
import ru.miet.example.grpc.chat.entity.mapper.ChatMapper;
import ru.miet.example.grpc.chat.entity.mapper.ChatUserMapper;
import ru.miet.example.grpc.chat.repo.custom.AbstractCustomRepository;
import ru.miet.example.grpc.chat.repo.custom.ChatRepository;
import ru.miet.example.grpc.chat.repo.generic.GenericChatRepository;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository("customChatRepository")
@Primary
public class ChatRepositoryImpl extends AbstractCustomRepository<Chat, Long> implements ChatRepository {

    private static final String INSERT_CHAT_HQL = MessageFormat.format("INSERT INTO chat " +
                    "({1}) VALUES (:{1}) " +
                    "RETURNING {0}",
            ChatColumn.ID,
            ChatColumn.NAME);

    private static final String UPDATE_CHAT_HQL = MessageFormat.format("UPDATE chat SET " +
                    "{1}=:{1} " +
                    "WHERE {0}=:{0}",
            ChatColumn.ID,
            ChatColumn.NAME);

    private static final String SELECT_CHAT_MEMBERS_HQL = MessageFormat.format("SELECT chat_member.* " +
                    "FROM chat_member " +
                    "INNER JOIN chat_user ON chat_user.{0} = chat_member.{1} " +
                    "WHERE chat_member.{2} = :{2}",
            ChatUserColumn.ID,
            ChatMemberColumn.MEMBER_ID,
            ChatMemberColumn.CHAT_ID);

    private static final String DELETE_CHAT_MEMBERS_HQL = MessageFormat.format("DELETE FROM chat_member " +
                    "WHERE chat_member.{0} = :{0} " +
                    "AND chat_member.{1} IN (:{1})",
            ChatMemberColumn.CHAT_ID,
            ChatMemberColumn.MEMBER_ID);

    private final DatabaseClient databaseClient;

    public ChatRepositoryImpl(@Qualifier("genericChatRepository") GenericChatRepository chatRepository,
                              DatabaseClient databaseClient) {
        super(chatRepository);
        this.databaseClient = databaseClient;
    }

    @Override
    public <S extends Chat> Mono<S> save(S savingChat) {
        String saveHql = UPDATE_CHAT_HQL;
        boolean isInsert = savingChat.getId() == null;
        if (isInsert) {
            saveHql = INSERT_CHAT_HQL;
        }
        DatabaseClient.GenericExecuteSpec sqlSpec = databaseClient.sql(saveHql)
                .filter((statement, executeFunction) -> {
                    if (isInsert) {
                        return statement.execute();
                    }
                    return statement.returnGeneratedValues(ChatColumn.ID).execute();
                });
        if (!isInsert) {
            sqlSpec = sqlSpec.bind(ChatColumn.ID, savingChat.getId());
        }

        if (savingChat.getName() != null) {
            sqlSpec = sqlSpec.bind(ChatColumn.NAME, savingChat.getName());
        } else {
            sqlSpec = sqlSpec.bindNull(ChatColumn.NAME, String.class);
        }

        Mono<Chat> chatMono = sqlSpec.fetch()
                .first()
                .map(rows -> savingChat.withId((Long) rows.get(ChatColumn.ID)))
                .switchIfEmpty(Mono.just(savingChat))
                .flatMap(chat -> {
                    Flux<ChatUser> currentMembers = Flux.empty();
                    if (!isInsert) {
                        currentMembers = databaseClient.sql(SELECT_CHAT_MEMBERS_HQL)
                                .bind(ChatMemberColumn.CHAT_ID, savingChat.getId())
                                .fetch()
                                .all()
                                .map(ChatUserMapper::fromMap);
                    }
                    return Mono.zip(Mono.just(chat), currentMembers.collectList());
                })
                .flatMap(chatMembersTuple -> {
                    Chat chat = chatMembersTuple.getT1();
                    Set<ChatUser> currentChatMembers = new HashSet<>(chatMembersTuple.getT2());
                    Sets.SetView<ChatUser> membersToAdd = Sets.difference(chat.getMembers(), currentChatMembers);
                    Sets.SetView<ChatUser> membersToRemove = Sets.difference(currentChatMembers, chat.getMembers());
                    Mono<Void> insertMono = Mono.empty();
                    if (!membersToAdd.isEmpty()) {
                        String insertChatMembersHql = getInsertChatMembersHql(chat.getId(), membersToAdd);
                        log.debug("insertChatMembersHql = {}", insertChatMembersHql);
                        insertMono = databaseClient.sql(insertChatMembersHql)
                                .fetch()
                                .all()
                                .then();
                    }
                    Mono<Void> deleteMono = Mono.empty();
                    if (!membersToRemove.isEmpty()) {
                        String removeChatMembersHql = membersToRemove.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(", "));
                        log.debug("removeChatMembersHql = {}", removeChatMembersHql);
                        deleteMono = databaseClient.sql(DELETE_CHAT_MEMBERS_HQL)
                                .bind(ChatMemberColumn.MEMBER_ID, removeChatMembersHql)
                                .fetch()
                                .all()
                                .then();
                    }
                    return insertMono.and(deleteMono)
                            .thenReturn(chat);
                });
        return (Mono<S>) chatMono;
    }

    private static String getInsertChatMembersHql(Long chatId, Collection<ChatUser> members) {
        final String statementHql = MessageFormat.format("INSERT INTO chat_member ({0}, {1}) VALUES ",
                ChatMemberColumn.CHAT_ID,
                ChatMemberColumn.MEMBER_ID);
        String valuesHql = members.stream()
                .map(ChatUser::getId)
                .map(memberId -> MessageFormat.format("({0}, {1})", chatId, memberId))
                .collect(Collectors.joining(", "));
        return statementHql + valuesHql;
    }

    @Override
    public Mono<Chat> findById(Long id) {
        final String chatHql = MessageFormat.format("SELECT chat.* " +
                        "FROM chat " +
                        "WHERE chat.{0} = :{0}",
                ChatColumn.ID);
        final String chatMembersHql = MessageFormat.format("SELECT chat_user.* " +
                        "FROM chat_member " +
                        "INNER JOIN chat_user ON chat_user.{1} = chat_member.{2} " +
                        "WHERE chat_member.{0} = :{0}",
                ChatMemberColumn.CHAT_ID,
                ChatUserColumn.ID,
                ChatMemberColumn.MEMBER_ID);
        Mono<Chat> chatMono = databaseClient.sql(chatHql)
                .bind(ChatColumn.ID, id)
                .fetch()
                .first()
                .map(ChatMapper::fromMap);
        Flux<ChatUser> membersFlux = databaseClient.sql(chatMembersHql)
                .bind(ChatMemberColumn.CHAT_ID, id)
                .fetch()
                .all()
                .map(ChatUserMapper::fromMap);
        return chatMono.flatMap(chat -> Mono.zip(Mono.just(chat), membersFlux.collectList()))
                .map(chatAndMembers -> {
                    Chat chat = chatAndMembers.getT1();
                    List<ChatUser> members = chatAndMembers.getT2();
                    return chat.withMembers(new HashSet<>(members));
                });
    }

    @Override
    public Mono<Chat> findById(Publisher<Long> publisher) {
        return Mono.from(publisher).flatMap(this::findById);
    }

    @Override
    public Mono<Chat> searchByMemberIds(@NonNull Set<Long> memberIds) {
        final String chatIdHql = MessageFormat.format("SELECT chat.{0} " +
                        "FROM chat " +
                        "INNER JOIN chat_member ON chat.{0} = chat_member.{1} " +
                        "WHERE chat.{0} IN ( " +
                        "SELECT chat.{0} " +
                        "FROM chat " +
                        "INNER JOIN chat_member ON chat.{0} = chat_member.{1} " +
                        "WHERE chat_member.{2} IN (:{2}) " +
                        ") " +
                        "GROUP BY chat.{0} " +
                        "HAVING count(1) = {3}",
                ChatColumn.ID,
                ChatMemberColumn.CHAT_ID,
                ChatMemberColumn.MEMBER_ID,
                memberIds.size());
        DatabaseClient.GenericExecuteSpec executeSpec = databaseClient.sql(chatIdHql);
        if (memberIds.size() == 1) {
            Long singleMemberId = memberIds.stream().findFirst().get();
            executeSpec = executeSpec.bind(ChatMemberColumn.MEMBER_ID, singleMemberId);
        } else {
            String memberIdsString = memberIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            executeSpec = executeSpec.bind(ChatMemberColumn.MEMBER_ID, memberIdsString);
        }
        Mono<Long> chatIdMono = executeSpec.fetch()
                .first()
                .map(row -> (Long) row.get(ChatColumn.ID));
        return findById(chatIdMono);
    }
}
