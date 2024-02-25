pipeline {
    agent any

    stages {
        stage('Build Frontend') {
            environment {
                NO_COLOR = 'true'
            }
            steps {
                script {
                    if (env.BRANCH_NAME == 'develop') {
                        dotenvCredentialsId = 'dotenv-frontend-test'
                    } else if (env.BRANCH_NAME == 'master') {
                        dotenvCredentialsId = 'dotenv-frontend'
                    } else {
                        dotenvCredentialsId = ''
                    }

                    if (dotenvCredentialsId) {
                        dir('frontend') {
                            nodejs(nodeJSInstallationName: 'node-20.11.0') {
                                sh 'corepack enable pnpm'
                                sh 'pnpm install'
                                withCredentials([file(credentialsId: dotenvCredentialsId, variable: 'dotenv')]) {
                                    sh 'pnpm run generate --dotenv $dotenv'
                                }
                            }
                        }
                    } else {
                        dir('frontend') {
                            nodejs(nodeJSInstallationName: 'node-20.11.0') {
                                sh 'corepack enable pnpm'
                                sh 'pnpm install'
                                sh 'pnpm run generate'
                            }
                        }
                    }
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    withGradle {
                        sh './gradlew test bootJar'
                    }
                }
            }
        }

        stage('Deploy Develop') {
            when {
                branch 'develop'
            }
            steps {
                withCredentials([file(credentialsId: 'dotenv', variable: 'dotenv')]) {
                    sh 'docker compose -p ssafee --env-file $dotenv up -d --build frontend-test backend-test'
                    sh 'docker compose -p ssafee exec nginx nginx -s reload'
                }
            }
        }

        stage('Deploy Master') {
            when {
                branch 'master'
            }
            steps {
                withCredentials([file(credentialsId: 'dotenv', variable: 'dotenv')]) {
                    sh 'docker compose -p ssafee --env-file $dotenv up -d --build frontend backend'
                    sh 'docker compose -p ssafee exec nginx nginx -s reload'
                }
            }
        }

        stage('Cleanup Dangling') {
            steps {
                sh 'docker image prune -f'
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
