#!/bin/bash
./gradlew clean build && 
docker build -t paiya/paiya-accountmanager:latest . 
&& docker compose up -d --force-recreate