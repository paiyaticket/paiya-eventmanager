pipeline {
    agent any

    environment {
        DOCKER_HUB_PWD = credentials('DOCKER_HUB_PWD')
    }

    stages{
        stage('build'){
            steps{
                git branch: 'pipeline-de-dev', url: 'https://github.com/koffiange62/paiya-eventmanager.git'
                sh './gradlew build -x test'
            }
        }

        stage('unit test'){
            steps{
                sh './gradlew test'
            }
        }

        stage('Statics Analysis'){
            options {
                timeout(time: 1, unit: "MINUTES")
            }
            steps{
                script{
                    withSonarQubeEnv() {
                        sh './gradlew jacocoTestReport sonar'
                    }
                }
            }
        }

    }
}