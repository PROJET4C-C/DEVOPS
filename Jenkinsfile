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
    }
    stage('Deploy to Nexus') {
            steps {
                dir('achat') {
                    echo 'Déploiement sur Nexus...'
                    withCredentials([usernamePassword(
                        credentialsId: 'nexus-credentials',
                        usernameVariable: 'NEXUS_USER',
                        passwordVariable: 'NEXUS_PASS'
                    )]) {
                        sh 'mvn deploy -DskipTests -Dusername=$NEXUS_USER -Dpassword=$NEXUS_PASS'
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
