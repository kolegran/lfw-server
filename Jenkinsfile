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

                ./gradlew clean build
                '''
            }
        }
    }
}