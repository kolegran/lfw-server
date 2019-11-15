pipeline {
    environment {
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
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
    }
}