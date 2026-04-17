pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'JDK21'
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
        stage('Package') {
            steps {
                dir('achat') {
                    echo 'Packaging...'
                    sh 'mvn package -DskipTests'
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
