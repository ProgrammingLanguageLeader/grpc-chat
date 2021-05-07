package ru.miet.example.grpc.chat.repo.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Getter
public abstract class AbstractCustomRepository<T, ID> implements ReactiveCrudRepository<T, ID> {
    private final ReactiveCrudRepository<T, ID> defaultRepository;

    @Override
    public <S extends T> Mono<S> save(S s) {
        return defaultRepository.save(s);
    }

    @Override
    public <S extends T> Flux<S> saveAll(Iterable<S> iterable) {
        return defaultRepository.saveAll(iterable);
    }

    public <S extends T> Flux<S> saveAll(Publisher<S> publisher) {
        return defaultRepository.saveAll(publisher);
    }

    @Override
    public Mono<T> findById(ID id) {
        return defaultRepository.findById(id);
    }

    @Override
    public Mono<T> findById(Publisher<ID> publisher) {
        return defaultRepository.findById(publisher);
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return defaultRepository.existsById(id);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> publisher) {
        return defaultRepository.existsById(publisher);
    }

    @Override
    public Flux<T> findAll() {
        return defaultRepository.findAll();
    }

    @Override
    public Flux<T> findAllById(Iterable<ID> iterable) {
        return defaultRepository.findAllById(iterable);
    }

    @Override
    public Flux<T> findAllById(Publisher<ID> publisher) {
        return defaultRepository.findAllById(publisher);
    }

    @Override
    public Mono<Long> count() {
        return defaultRepository.count();
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return defaultRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> publisher) {
        return defaultRepository.deleteById(publisher);
    }

    @Override
    public Mono<Void> delete(T t) {
        return defaultRepository.delete(t);
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> iterable) {
        return defaultRepository.deleteAll(iterable);
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends T> publisher) {
        return defaultRepository.deleteAll(publisher);
    }

    @Override
    public Mono<Void> deleteAll() {
        return defaultRepository.deleteAll();
    }
}
