pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk21'
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Récupération du code depuis GitHub...'
                checkout scm
            }
        }
        stage('Build') {
            steps {
                dir('achat') {
                    echo 'Compilation du projet...'
                    sh 'mvn clean compile'
                }
            }
        }
        stage('Test') {
            steps {
                dir('achat') {
                    echo 'Exécution des tests...'
                    sh 'mvn test'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                dir('achat') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
        stage('Package') {
            steps {
                dir('achat') {
                    echo 'Packaging...'
                    sh 'mvn package -DskipTests'
                }
            }
        }
    
       stage('Deploy to Nexus') {
            steps {
                dir('achat') {
                    echo 'Déploiement sur Nexus...'
                    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh 'mvn deploy -DskipTests'
                    }
                }
            }
        }
       stage('Docker Build') {
            steps {
                dir('achat') {
                     echo 'Construction de l image Docker...'
                     sh 'docker build -t achat-app:2.0 .'
        }
    }
}

     stage('Docker Run') {
    steps {
        dir('achat') {
            echo 'Lancement avec Docker Compose...'
            sh 'docker-compose down --remove-orphans || true'
            sh 'docker rm -f achat-container mysql-container || true'
            sh 'docker-compose up -d'
        }
    }
}
    }
    post {
        success {
            echo 'Build réussi !'
        }
        failure {
            echo 'Build échoué !'
        }
    }
}
