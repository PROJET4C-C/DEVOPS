pipeline {
    agent any

    tools {
        maven 'Maven'   // nom exact dans Manage Jenkins > Tools
        jdk 'JDK21'     // nom exact dans Manage Jenkins > Tools
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
                echo 'Compilation du projet...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Exécution des tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging...'
                sh 'mvn package -DskipTests'
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
