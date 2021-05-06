params='{"token": '\"$1\"', "pageParams": {"number": 0, "size": 20}}'
echo params = "$params"
./grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto ChatService.proto \
  --plaintext \
  -d "$params" \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.ChatService/getChats
