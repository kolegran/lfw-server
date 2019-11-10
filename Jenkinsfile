pipeline {
    agent {
        docker {
            image 'gradle:latest'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh '''
                chmod +x gradlew
                ./gradlew clean build'
                '''
            }
        }
    }
}