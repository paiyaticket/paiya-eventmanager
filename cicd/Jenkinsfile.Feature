pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN')
    }

    stages{
        stage('Build'){
            steps{
                git branch: 'integration-test', url: 'https://github.com/koffiange62/paiya-eventmanager.git'
                sh './gradlew build -x test'
            }
        }

        stage('Unit test'){
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
                        sh './gradlew jacocoTestReport sonar -Psonarstage=feature -Dsonar.token=${SONAR_TOKEN}'
                    }
                }
            }
        }

    }
}