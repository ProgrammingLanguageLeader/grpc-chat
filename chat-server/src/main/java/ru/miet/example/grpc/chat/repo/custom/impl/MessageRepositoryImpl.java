package ru.miet.example.grpc.chat.repo.custom.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.Message;
import ru.miet.example.grpc.chat.entity.column.ChatColumn;
import ru.miet.example.grpc.chat.entity.column.MessageColumn;
import ru.miet.example.grpc.chat.repo.custom.AbstractCustomRepository;
import ru.miet.example.grpc.chat.repo.custom.MessageRepository;
import ru.miet.example.grpc.chat.repo.generic.GenericMessageRepository;

import java.text.MessageFormat;

@Slf4j
@Repository("customMessageRepository")
@Primary
public class MessageRepositoryImpl extends AbstractCustomRepository<Message, Long> implements MessageRepository {

    private static final String INSERT_MESSAGE_HQL = MessageFormat.format("INSERT INTO message " +
                    "({1}, {2}, {3}, {4}) " +
                    "VALUES (:{1}, :{2}, :{3}, :{4}) " +
                    "RETURNING {0}",
            MessageColumn.ID,
            MessageColumn.TEXT,
            MessageColumn.CREATED_AT,
            MessageColumn.CHAT_ID,
            MessageColumn.SENDER_ID);

    private static final String UPDATE_MESSAGE_HQL = MessageFormat.format("UPDATE message SET " +
                    "{1}=:{1}, " +
                    "{2}=:{2}, " +
                    "{3}=:{3}, " +
                    "{4}=:{4} " +
                    "WHERE {0}=:{0}",
            MessageColumn.ID,
            MessageColumn.TEXT,
            MessageColumn.CREATED_AT,
            MessageColumn.CHAT_ID,
            MessageColumn.SENDER_ID);

    private final DatabaseClient databaseClient;

    public MessageRepositoryImpl(@Qualifier("genericMessageRepository") GenericMessageRepository defaultRepository,
                                 DatabaseClient databaseClient) {
        super(defaultRepository);
        this.databaseClient = databaseClient;
    }

    @Override
    public <S extends Message> Mono<S> save(S savingMessage) {
        log.debug("save start: id={}", savingMessage.getId());
        boolean isInsert = savingMessage.getId() == null;
        String hql = UPDATE_MESSAGE_HQL;
        if (isInsert) {
            hql = INSERT_MESSAGE_HQL;
        }
        DatabaseClient.GenericExecuteSpec sqlSpec = databaseClient.sql(hql)
                .filter((statement, executeFunction) -> {
                    if (isInsert) {
                        return statement.execute();
                    }
                    return statement.returnGeneratedValues(ChatColumn.ID).execute();
                })
                .bind(MessageColumn.TEXT, savingMessage.getText())
                .bind(MessageColumn.CREATED_AT, savingMessage.getCreatedAt())
                .bind(MessageColumn.CHAT_ID, savingMessage.getChat().getId())
                .bind(MessageColumn.SENDER_ID, savingMessage.getSender().getId());
        if (!isInsert) {
            sqlSpec = sqlSpec.bind(MessageColumn.ID, savingMessage.getId());
        }
        return (Mono<S>) sqlSpec.fetch()
                .first()
                .map(rows -> savingMessage.withId((Long) rows.get(MessageColumn.ID)))
                .switchIfEmpty(Mono.just(savingMessage))
                .doOnNext(message -> log.debug("save end: id={}", message.getId()));
    }

    @Override
    public Flux<Message> findByChatId(Long chatId, Sort sort, Pageable pageable) {
        return null;
    }
}
