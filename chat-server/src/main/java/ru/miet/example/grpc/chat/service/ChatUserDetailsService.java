package ru.miet.example.grpc.chat.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.miet.example.grpc.chat.entity.ChatUserDetails;
import ru.miet.example.grpc.chat.repo.UserRepository;

@Service
public class ChatUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    public ChatUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findByUsernameExt(username).cast(UserDetails.class);
    }

    public Mono<ChatUserDetails> findByUsernameExt(String username) {
        return userRepository.findByUsername(username)
                .map(ChatUserDetails::new);
    }
}
