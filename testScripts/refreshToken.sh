params='{"token": '\"$1\"'}'
echo params = "$params"
grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto AuthService.proto \
  --plaintext \
  -d "$params" \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.AuthService/refreshToken
