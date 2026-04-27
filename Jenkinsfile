pipeline {
    agent any

    environment {
        APP_NAME = "achat-app"
        CONTAINER_NAME = "achat-container"
        APP_PORT = "8090"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/PROJET4C-C/DEVOPS.git'
            }
        }

        stage('Build') {
            steps {
                dir('achat') {
                    bat "mvn clean compile"
                }
            }
        }

        stage('Test') {
            steps {
                dir('achat') {
                    bat "mvn test"
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('achat') {
                    withSonarQubeEnv('SonarQube') {
                        bat "mvn sonar:sonar -Dsonar.projectKey=achat"
                    }
                }
            }
        }

        stage('Package') {
            steps {
                dir('achat') {
                    bat "mvn clean package -DskipTests"
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                dir('achat') {
                    bat "mvn deploy -DskipTests"
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                // Force cleanup of old containers to avoid naming conflicts
                bat "docker stop %CONTAINER_NAME% || exit 0"
                bat "docker rm %CONTAINER_NAME% || exit 0"
                bat "docker-compose down"
                bat "docker-compose up -d --build"
            }
        }

        stage('Verify Pipeline') {
            steps {
                bat "docker ps"
                // Give it a few seconds to start before curl
                bat "timeout /t 15"
                bat "curl http://localhost:%APP_PORT%"
            }
        }
    }

    post {
        success {
            echo '======================================'
            echo ' PIPELINE SUCCESSFULLY COMPLETED '
            echo ' Build & Test OK'
            echo ' SonarQube & Nexus OK'
            echo ' Docker Compose Deployment OK'
            echo '======================================'
        }
        failure {
            echo '======================================'
            echo ' PIPELINE FAILED '
            echo ' Check Jenkins logs for details'
            echo '======================================'
        }
        always {
            echo 'Pipeline execution finished.'
        }
    }
}
