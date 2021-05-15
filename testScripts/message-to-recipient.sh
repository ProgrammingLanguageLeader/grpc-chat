params='{"token": '\"$1\"', "text": "test self message", "recipientId": "1"}'
echo params = "$params"
./grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto MessageService.proto \
  --plaintext \
  -d '' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.MessageService/sendMessage
