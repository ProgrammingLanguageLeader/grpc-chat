./grpcurl -import-path ../chat-protobuf/src/main/proto \
  -proto AuthService.proto \
  --plaintext \
  -d '{"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkbV9zaG9yb2tob3YiLCJleHAiOjE2MTk5MDQ1ODAsImlhdCI6MTYxOTkwMDk4MH0.bcx8UHnxuXFfj3euraVgjdKSHKV1szUaJZUjcnpm7MSkysS3pGPZ3l26UfJUvxSNN0GuRD4HLvrhJioFqszUfg"}' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.AuthService/refreshToken
