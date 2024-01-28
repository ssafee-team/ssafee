pipeline {
    agent any

    tools {
        nodejs 'nodejs-20.11.0'
    }

    stages {
        stage('Pull') {
            steps {
                updateGitlabCommitStatus name: 'build', state: 'pending'
            }
        }

        stage('Credentials') {
            steps {
                withCredentials([file(credentialsId: 'application-prod-yml', variable: 'APPLICATION_PROD_YML')]) {
                    sh 'cp -f $APPLICATION_PROD_YML ./backend/src/main/resources/application-prod.yml'
                }
            }
        }

        stage('Build') {
            steps {
                updateGitlabCommitStatus name: 'build', state: 'running'
                dir('backend') {
                    sh './gradlew clean bootJar -Dspring.profiles.active=prod'
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                dir('backend') {
                    sh 'docker build -t backend .'
                }
                sh 'docker ps -q --filter name=backend | grep -q . && docker stop backend && docker rm backend'
                sh 'docker run -d --name backend backend'
                sh 'docker images -qf dangling=true | xargs -I{} docker rmi {}'
            }
        }

        stage('Finish') {
            steps {
                updateGitlabCommitStatus name: 'build', state: 'success'
            }
        }
    }

    post {
        success {
            mattermostSend color: 'good', message: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
        }
        failure {
            mattermostSend color: 'danger', message: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
        }
    }
}
