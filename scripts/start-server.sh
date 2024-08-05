#!/bin/bash

echo "--------------- 배포 시작 ---------------"
docker stop badang-server || true
docker rm badang-server || true
dock pull 058264467328.dkr.ecr.ap-northeast-2.amazonaws.com/badang-server:latest
docker run -d --name badang-server -p 8080:8080 058264467328.dkr.ecr.ap-northeast-2.amazonaws.com/badang-server:latest
echo "--------------- 배포 완료 ---------------"