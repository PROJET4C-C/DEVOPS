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

        stage('Build Docker Image') {
            steps {
                dir('achat') {
                    bat "docker build -t %APP_NAME% ."
                }
            }
        }

        stage('Stop Old Container') {
            steps {
                bat """
                docker stop %CONTAINER_NAME% || exit 0
                docker rm %CONTAINER_NAME% || exit 0
                """
            }
        }

        stage('Run Docker Container') {
            steps {
                bat """
                docker run -d ^
                -p %APP_PORT%:%APP_PORT% ^
                --name %CONTAINER_NAME% ^
                %APP_NAME%
                """
            }
        }

        stage('Verify Running Containers') {
            steps {
                bat "docker ps"
            }
        }

        stage('Verify Application') {
            steps {
                bat "curl http://localhost:%APP_PORT%"
            }
        }
    }

    post {

        success {
            echo '======================================'
            echo ' PIPELINE SUCCESSFULLY COMPLETED '
            echo ' Build OK'
            echo ' Tests OK'
            echo ' SonarQube OK'
            echo ' Nexus Deployment OK'
            echo ' Docker Deployment OK'
            echo '======================================'
        }

        failure {
            echo '======================================'
            echo ' PIPELINE FAILED '
            echo ' Check Jenkins logs'
            echo '======================================'
        }

        always {
            echo 'Pipeline execution finished.'
        }
    }
}
