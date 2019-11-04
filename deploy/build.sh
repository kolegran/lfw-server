#!/bin/bash

cd ..

docker build -t kolegran/lfw-server:latest .

# push image to the Docker Hub
docker push kolegran/lfw-server:latest

# save image to the archive and copy it on the remote server
umask 0000
docker save kolegran/lfw-server > lfw-server.tar
#setfacl -m u:kolegran:rwx lfw-server.tar
scp lfw-server.tar kolegran@external_ip:/home/kolegran/containers