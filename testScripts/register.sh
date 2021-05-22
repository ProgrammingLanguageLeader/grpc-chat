grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto RegisterService.proto \
  -plaintext \
  -d '{"username": "ed_eliseev", "password": "1qaz2wsx", "firstName": "Eduard", "lastName": "Eliseev"}' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.RegisterService/register
