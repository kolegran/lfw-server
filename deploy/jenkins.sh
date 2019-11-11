docker run \
  -u root \
  -p 8081:8080 \
  -v jenkins-data:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v "$HOME":/home \
  --name jblue \
  jenkinsci/blueocean