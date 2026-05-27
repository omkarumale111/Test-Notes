pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/omkarumale111/Test-Notes.git'
            }
        }

        stage('Run All Tests') {
            steps {
                bat 'mvn clean test'
            }
        }
    }

    post {

        always {

            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: 'allure-results']]
            ])
        }

        success {
            echo 'All tests executed successfully!'
        }

        failure {
            echo 'Some tests failed!'
        }
    }
}