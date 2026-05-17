pipeline {

    agent any



    options {
        // ✅ Timeout global du pipeline
        timeout(time: 30, unit: 'MINUTES')
        // ✅ Garder uniquement les 5 derniers builds
        buildDiscarder(logRotator(numToKeepStr: '5'))
        // ✅ Pas de builds parallèles sur la même branche
        disableConcurrentBuilds()
    }

    environment {
        APP_NAME = "achat-app"
        CONTAINER_NAME = "achat-container"
        APP_PORT = "8090"
        // ✅ Email générique pour les notifications - remplacez-le par votre adresse email
        NOTIFICATION_EMAIL = "admin@example.com"
    }

    stages {

        stage('Checkout') {
            steps {
                // ✅ Utilise la configuration SCM générique du projet Jenkins (télécharge automatiquement le dépôt et la branche du déclencheur)
                checkout scm
            }
        }

        stage('Build') {
            options { timeout(time: 5, unit: 'MINUTES') }
            steps {
                dir('achat') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            options { timeout(time: 10, unit: 'MINUTES') }
            steps {
                dir('achat') {
                    bat 'mvn test'
                }
            }
        }

        stage('SonarQube Analysis') {
            options { timeout(time: 5, unit: 'MINUTES') }
            steps {
                dir('achat') {
                    withSonarQubeEnv('SonarQube') {
                        bat 'mvn sonar:sonar -Dsonar.projectKey=achat'
                    }
                }
            }
        }

        stage('OWASP Dependency Check') {
            options { timeout(time: 15, unit: 'MINUTES') }
            steps {
                dir('achat') {
                    // Utilise le plugin Maven directement pour faire l'analyse
                    bat 'mvn dependency-check:check'
                }
            }
        }

        stage('Package') {
            options { timeout(time: 5, unit: 'MINUTES') }
            steps {
                dir('achat') {
                    bat 'mvn package -DskipTests'
                }
            }
        }

        stage('Deploy to Nexus') {
            options { timeout(time: 5, unit: 'MINUTES') }
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
                        mvn deploy -DskipTests
                        """
                    }
                }
            }
        }

        stage('Docker Trivy Scan') {
            options { timeout(time: 10, unit: 'MINUTES') }
            steps {
                // Utilise le conteneur Trivy pour scanner l'image de base utilisée dans le Dockerfile
                // ou l'image construite. Ici on scanne l'image construite par Docker Compose.
                bat "docker build -t achat-app:latest ./achat"
                bat "docker run --rm -v //var/run/docker.sock:/var/run/docker.sock aquasec/trivy image --severity HIGH,CRITICAL --no-progress achat-app:latest"
            }
        }

        stage('Deploy with Docker Compose') {
            options { timeout(time: 5, unit: 'MINUTES') }
            steps {
                bat "docker stop %CONTAINER_NAME% || exit 0"
                bat "docker rm %CONTAINER_NAME% || exit 0"
                // ✅ Nettoyage complet
                bat "docker-compose down --remove-orphans"
                bat "docker-compose up -d --build"
            }
        }

        stage('Verify Pipeline') {
            options { timeout(time: 2, unit: 'MINUTES') }
            steps {
                bat "docker ps"
                sleep 15
                // ✅ Vérification santé de l'application
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
            // ✅ Notification email générique en cas d'échec
            mail to: env.NOTIFICATION_EMAIL,
                 subject: "Pipeline FAILED : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Le pipeline a echoue. Verifiez les logs : ${env.BUILD_URL}"
        }
        always {
            echo 'Pipeline execution finished.'
        }
    }
}