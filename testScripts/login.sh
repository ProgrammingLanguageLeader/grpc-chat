grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto AuthService.proto \
  --plaintext \
  -d '{"username": "dm_shorokhov", "password": "1qaz2wsx"}' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.AuthService/login
