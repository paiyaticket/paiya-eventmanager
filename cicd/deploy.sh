#!/bin/bash
./gradlew clean build && 
docker build -t paiya/paiya-eventmanager:latest . &&
docker compose up -d --force-recreate