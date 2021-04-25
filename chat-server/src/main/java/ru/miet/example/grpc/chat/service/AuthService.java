package ru.miet.example.grpc.chat.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {
    @Override
    public void login(AuthServiceOuterClass.LoginRequest request,
                      StreamObserver<AuthServiceOuterClass.LoginResponse> responseObserver) {
        super.login(request, responseObserver);
    }

    @Override
    public void logout(AuthServiceOuterClass.LogoutRequest request,
                       StreamObserver<Empty> responseObserver) {
        super.logout(request, responseObserver);
    }

    @Override
    public void refreshToken(AuthServiceOuterClass.RefreshTokenRequest request,
                             StreamObserver<AuthServiceOuterClass.RefreshTokenResponse> responseObserver) {
        super.refreshToken(request, responseObserver);
    }
}
