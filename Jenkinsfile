pipeline {
    agent {
        docker {
            image 'openjdk:11'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh '''
                    ./gradlew clean build
                '''
            }
        }
        stage('Publish') {
            steps {
                sh '''
                    docker build -t kolegran/lfw-server:latest .
                    docker push kolegran/lfw-server:latest
                '''
            }
        }
    }
}