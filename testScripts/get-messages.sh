params='{"token": '\"$1\"', "chatId": 20, "pageParams": {"number": 0, "size": 20}}'
echo params = "$params"
./grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto MessageService.proto \
  --plaintext \
  -d "$params" \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.MessageService/getMessages
