@echo off
echo 1 - BUILDING PROJECT DOCKER IMAGE
docker build -t koffiange/paiya-eventmanager:latest .

echo 2 - PUSHING DOCKER IMAGE
docker push

echo 3 - RUNNING DOCKER COMPOSE 
cd cicd
docker compose up -d --force-recreate

echo PROCESS COMPLETED