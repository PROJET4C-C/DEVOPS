pipeline {

    agent any

    tools {
        dependencyCheck 'DependencyCheck'
    }

    environment {
        APP_NAME = "achat-app"
        CONTAINER_NAME = "achat-container"
        APP_PORT = "8090"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/PROJET4C-C/DEVOPS.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Build') {
            steps {
                dir('achat') {
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('achat') {
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" test'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('achat') {
                    withSonarQubeEnv('SonarQube') {
                        bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" sonar:sonar -Dsonar.projectKey=achat'
                    }
                }
            }
        }

        stage('OWASP Dependency Check') {
            steps {
                dir('achat') {
                    // ✅ Seuil d'échec si vulnérabilité HIGH ou CRITICAL
                    dependencyCheck additionalArguments: '--scan . --failOnCVSS 7 --format XML --format HTML',
                        odcInstallation: 'DependencyCheck'

                    dependencyCheckPublisher pattern: '**/dependency-check-report.xml',
                        failedTotalCritical: 1,
                        unstableTotalHigh: 5
                }
            }
        }

        stage('Package') {
            steps {
                dir('achat') {
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" package -DskipTests'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'nexus-creds',
                    usernameVariable: 'NEXUS_USER',
                    passwordVariable: 'NEXUS_PASS'
                )]) {
                    dir('achat') {
                        bat """
                        set NEXUS_USERNAME=%NEXUS_USER%
                        set NEXUS_PASSWORD=%NEXUS_PASS%
                        C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd deploy -DskipTests
                        """
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                bat "docker stop %CONTAINER_NAME% || exit 0"
                bat "docker rm %CONTAINER_NAME% || exit 0"
                bat "docker-compose down"
                bat "docker-compose up -d --build"
            }
        }

        stage('Verify Pipeline') {
            steps {
                bat "docker ps"
                sleep 15
                // ✅ Vérifie que l'app répond correctement
                bat "curl -f http://localhost:%APP_PORT%/actuator/health || exit 1"
            }
        }
    }

    post {
        success {
            echo '======================================'
            echo ' PIPELINE DEVSECOPS SUCCESS '
            echo ' Jenkins + SonarQube + Nexus + OWASP '
            echo ' Docker Deployment SUCCESS '
            echo '======================================'
        }
        failure {
            echo '======================================'
            echo ' PIPELINE DEVSECOPS FAILED '
            echo ' Check Jenkins logs for details '
            echo '======================================'
        }
        always {
            echo 'Pipeline execution finished.'
        }
    }
}