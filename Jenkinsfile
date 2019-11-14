pipeline {
    agent any
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'openjdk:11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh '''
                    docker build -t kolegran/lfw-server
                '''
            }
        }
        stage('Publish') {
            steps {
                sh '''
                    docker push kolegran/lfw-server:latest
                '''
            }
        }
    }
}