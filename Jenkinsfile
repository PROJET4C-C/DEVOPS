pipeline {
    agent any

    stages {

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
                    bat "mvn package -DskipTests"
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
    }

    post {
        success {
            echo '=== Pipeline SUCCESS ==='
        }
        failure {
            echo '=== Pipeline FAILED ==='
        }
    }
}
