pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'docker build -t kolegran/lfw-server .'
            }
        }
        stage('Publish') {
            steps {
                sh 'docker push kolegran/lfw-server:latest'
            }
        }
    }
}