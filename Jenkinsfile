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
            agent {
                docker {
                    build '-t kolegran/lfw-server:latest .'
                    push 'kolegran/lfw-server:latest'
                }
            }
        }
    }
}