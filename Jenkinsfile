pipeline {
    agent {
        node {
            label 'docker'
        }
    }
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
                    ./gradlew clean build
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