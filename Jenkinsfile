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
            steps {
                sh 'docker compose -p ssafee build backend'
                withCredentials([file(credentialsId: 'dotenv', variable: 'dotenv')]) {
                    writeFile file: '.env', text: readFile(dotenv)
                    sh 'docker compose -p ssafee up -d backend'
                }
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
