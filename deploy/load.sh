#!/bin/bash

cd ..

docker load < lfw-server.tar
docker stop lfw-server && docker rm lfw-server
docker run --name lfw-server --network=host kolegran/lfw-server