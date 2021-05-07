CREATE DATABASE "grpc-chat";
GRANT ALL PRIVILEGES ON DATABASE "grpc-chat" TO "grpc-chat-user";

\connect "grpc-chat";
CREATE SCHEMA IF NOT EXISTS "chat" AUTHORIZATION "grpc-chat-user";
GRANT ALL PRIVILEGES ON SCHEMA "chat" TO "grpc-chat-user";
