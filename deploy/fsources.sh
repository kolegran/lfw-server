#!/bin/bash

repository_url='https://github.com/kolegran/lfw-server.git'
git_dir='lfw-server'

if [ -d ${git_dir} ]
then
	rm -rf ${git_dir:?}
fi

git clone ${repository_url} && cd ${git_dir} || exit

docker build -t kolegran/lfw-server .
docker stop lfw-server && docker rm lfw-server
docker run --name lfw-server --network=host kolegran/lfw-server