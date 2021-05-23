params='{"token": '\"$1\"', "username": "v"}'
echo params = "$params"
grpcurl -import-path ../chat-protobuf/src/main/proto \
  --proto UserSearchService.proto \
  --plaintext \
  -d "$params" \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.UserSearchService/search
