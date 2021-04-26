grpcurl --plaintext \
  -d '{"username": "dm_shorokhov", "password": "1qaz2wsx"}' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.AuthService/login

grpcurl --plaintext -d '{"username": "dm_shorokhov", "password": "1qaz2wsx"}' localhost:8080 ru.miet.example.grpc.chat.service.AuthService/login

grpcurl --plaintext \
  -d '{"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkbV9zaG9yb2tob3YiLCJleHAiOjE2MTkzODI0MjEsImlhdCI6MTYxOTM3ODgyMX0.GqQOIaDgFsPJbTRJxmJQ_eMlbEQIPThu7w_RZVfQM-ulCBSRetxUe6DnJ-LCDe2acgNhB1saioxHOd-RSi7ATw"}' \
  localhost:8080 \
  ru.miet.example.grpc.chat.service.AuthService/refreshToken
