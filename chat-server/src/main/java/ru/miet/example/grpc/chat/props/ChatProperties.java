package ru.miet.example.grpc.chat.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "chat")
@Getter
@Setter
public class ChatProperties {
    private GRPCProperties grpc;
    private JWTProperties jwt;

    @Getter
    @Setter
    public static class GRPCProperties {
        // keep alive time in seconds
        private int keepAliveTime = 30;

        // keep alive timeout in seconds
        private int keepAliveTimeout = 5;
    }

    @Getter
    @Setter
    public static class JWTProperties {
        // secret key for JWT generation
        private String secret;

        // token validity time in seconds
        private long tokenValidityTime = 3600;
    }
}
