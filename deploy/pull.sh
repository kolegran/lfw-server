#!/bin/bash

docker stop lfw-server && docker rm lfw-server
docker pull kolegran/lfw-server
docker run --name lfw-server --network=host kolegran/lfw-server