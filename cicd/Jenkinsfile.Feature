pipeline {
    agent any

    environment {
        SONAR_LOGIN = credentials('SONAR_LOGIN')
        SONAR_PASSWORD = credentials('SONAR_PASSWORD')
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
                        sh './gradlew jacocoTestReport sonar -Dsonar.login=${SONAR_LOGIN} -Dsonar.password=${SONAR_PASSWORD}'
                    }
                }
            }
        }

    }
}