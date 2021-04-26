package ru.miet.example.grpc.chat.config;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.miet.example.grpc.chat.settings.GrpcSettings;

import java.util.concurrent.TimeUnit;

@Configuration
public class ServerConfig {
    private final GrpcSettings chatSettings;

    public ServerConfig(GrpcSettings chatSettings) {
        this.chatSettings = chatSettings;
    }

    @Bean
    public GrpcServerConfigurer keepAliveServerConfigurer() {
        return serverBuilder -> {
            if (serverBuilder instanceof NettyServerBuilder) {
                ((NettyServerBuilder) serverBuilder)
                        .keepAliveTime(chatSettings.keepAliveTime, TimeUnit.SECONDS)
                        .keepAliveTimeout(chatSettings.keepAliveTimeout, TimeUnit.SECONDS)
                        .permitKeepAliveWithoutCalls(true);
            }
        };
    }
}
