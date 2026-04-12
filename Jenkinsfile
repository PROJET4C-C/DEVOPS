pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir('achat') {
                    echo '=== Compilation du projet ==='
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('achat') {
                    echo '=== Execution des tests unitaires ==='
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" test'
                }
            }
        }

        stage('Package') {
            steps {
                dir('achat') {
                    echo '=== Generation de l artefact ==='
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" package -DskipTests'
                }
            }
        }
    }

    post {
        success {
            echo '=== Pipeline execute avec succes ==='
        }
        failure {
            echo '=== Echec du pipeline ==='
        }
    }
}
