pipeline {
    agent any

    stages {
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

        stage('Build Backend') {
            steps {
                dir('backend') {
                    withGradle {
                        sh './gradlew bootJar'
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

        stage('Deploy Develop') {
            when {
                branch 'develop'
            }
            steps {
                sh 'docker compose -p ssafee build frontend-dev backend-dev'
                    sh 'docker compose -p ssafee up -d frontend-dev'
                withCredentials([file(credentialsId: 'dotenv', variable: 'dotenv')]) {
                    writeFile file: '.env', text: readFile(dotenv)
                    sh 'docker compose -p ssafee up -d backend-dev'
                }
            }
        }

        stage('Deploy Master') {
            when {
                branch 'master'
            }
            steps {
                sh 'docker compose -p ssafee build frontend backend'
                sh 'docker compose -p ssafee up -d frontend'
                withCredentials([file(credentialsId: 'dotenv', variable: 'dotenv')]) {
                    writeFile file: '.env', text: readFile(dotenv)
                    sh 'docker compose -p ssafee up -d backend'
                }
            }
        }

        stage('Cleanup Dangling') {
            steps {
                sh 'docker image prune'
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
