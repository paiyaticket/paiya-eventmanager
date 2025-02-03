#!/bin/bash
./gradlew clean build && 
docker build -t paiya/paiya-eventmanager:latest . &&
cd cicd &&
docker compose up -d --force-recreate