grpcurl --plaintext \
  -d '{"username": "dm_shorokhov", "password": "1qaz2wsx", "firstName": "Dmitry", "lastName": "Shorokhov"}' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.RegisterService/register

grpcurl --plaintext -d '{"username": "dm_shorokhov", "password": "1qaz2wsx", "firstName": "Dmitry", "lastName": "Shorokhov"}' localhost:8080 ru.miet.example.grpc.chat.service.RegisterService/register
