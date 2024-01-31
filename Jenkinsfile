pipeline {
    agent any

    stages {
        stage('Build Backend') {
            steps {
                dir('backend') {
                    withGradle {
                        sh './gradlew bootJar'
                    }
                }
            }
        }

        stage('Build Frontend') {
            environment {
                NO_COLOR = 'true'
            }
            steps {
                dir('frontend') {
                    nodejs(nodeJSInstallationName: 'node-20.11.0') {
                        sh 'npm install'
                        sh 'npm run build'
                    }
                }
            }
        }

        stage('Test Backend') {
            steps {
                dir('backend') {
                    withGradle {
                        sh './gradlew test'
                    }
                }
            }
        }

        stage('Deploy Backend') {
            when {
                branch 'master'
            }
            environment {
                PROD_DB_URL = credentials('PROD_DB_URL')
                PROD_DB_USERNAME = credentials('PROD_DB_USERNAME')
                PROD_DB_PASSWORD = credentials('PROD_DB_PASSWORD')
            }
            steps {
                sh 'docker compose -p ssafee build backend'
                sh 'docker compose -p ssafee up -d backend'
            }
        }

        stage('Deploy Frontend') {
            when {
                branch 'master'
            }
            steps {
                sh 'docker compose -p ssafee build frontend'
                sh 'docker compose -p ssafee up -d frontend'
            }
        }

        stage('Cleanup Dangling') {
            steps {
                sh 'docker images prune'
            }
        }
    }

    post {
        success {
            mattermostSend color: 'good', message: "✅ SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
        }
        failure {
            mattermostSend color: 'danger', message: "🟥 FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
        }
    }
}
