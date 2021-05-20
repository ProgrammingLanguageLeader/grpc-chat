params='{"token": '\"$1\"', "pageParams": {"number": 0, "size": 20}}'
echo params = "$params"
C:\grpcurl_1.8.1_windows_x86_64\grpcurl.exe -import-path ../chat-protobuf/src/main/proto \
  -proto ChatService.proto \
  --plaintext \
  -d "$params" \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.ChatService/getChats
