pipeline {
    agent any

    stages {
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

        stage('Package') {
            steps {
                dir('achat') {
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" package -DskipTests'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                dir('achat') {
                    bat '"C:\\apache-maven-3.9.14\\apache-maven-3.9.14\\bin\\mvn.cmd" deploy -DskipTests -s C:\\Users\\jemai\\.m2\\settings.xml'
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
