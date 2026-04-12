pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir('achat') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('achat') {
                    bat 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                dir('achat') {
                    bat 'mvn package -DskipTests'
                }
            }
        }
    }
}
