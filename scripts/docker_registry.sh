docker run -d -p 5002:5000 --name docker-registry registry:2

# build, tag & push to the artifacts storage
docker build -t lfw-server:latest .
docker tag lfw-server external_ip:5002/lfw-server:latest
docker push external_ip:5002/lfw-server:latest

# pull from the artifacts storage
docker pull external_ip:5002/lfw-server:latest