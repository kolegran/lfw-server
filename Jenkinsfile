pipeline {
    environment {
        // set creds through Jenkins UI
        registry = "kolegran/lfw-server"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }

    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Publish') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    def image = docker.image("${dockerImage.imageName()}")
                    image.pull()
                    image.run()
                }
            }
        }
    }
}