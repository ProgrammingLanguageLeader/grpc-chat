./grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto MessageService.proto \
  --plaintext \
  -d '{"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkbV9zaG9yb2tob3YiLCJleHAiOjE2MjAyMjE1ODUsImlhdCI6MTYyMDIxNzk4NX0.GYL94T-9i6EzJuTbZI8y3DmsZkanKGHJqbFHfq_0DqOAUHgIk4XmIty1QEr9UQvApVRfz2ZTknBW3MeFjy29jA", "text": "Еще одно сообщение", "chatId": "20"}' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.MessageService/sendMessage
