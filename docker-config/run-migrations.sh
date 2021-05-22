#!/usr/bin/env bash

while getopts u:p:d: flag; do
  case "${flag}" in
  u) username=${OPTARG} ;;
  p) password=${OPTARG} ;;
  d) database_name=${OPTARG} ;;
  *)
    echo "usage: $0 [-u] username [-p] password [-d] database-name"
    exit 1
    ;;
  esac
done

if [ -z "$username" ]; then
  read -rp "enter username: " username
else
  echo "Username: $username"
fi

if [ -z "$database_name" ]; then
  read -rp "enter database name: " database_name
else
  echo "Database name: $database_name"
fi

if [ -z "$password" ]; then
  read -rsp "enter password: " password
fi

changelog_path=$(realpath ../chat-server/src/main/resources/db/changelog)
echo "changelog absolute path: $changelog_path"
docker run --rm --net=host \
  -v "$changelog_path:/liquibase/db/changelog" \
  liquibase/liquibase \
  --driver=org.postgresql.Driver \
  --url="jdbc:postgresql://127.0.0.1:5432/$database_name?currentSchema=chat" \
  --changeLogFile=/db/changelog/db.changelog-master.yaml \
  --username="$username" \
  --password="$password" \
  update
