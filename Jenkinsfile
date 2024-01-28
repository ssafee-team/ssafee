pipeline {
    agent any

    environment {
        GIT_URL = 'https://lab.ssafy.com/s10-webmobile1-sub2/S10P12C209.git'
    }

    tools {
        nodejs 'nodejs-20.11.0'
    }

    stages {
        stage('Pull') {
            steps {
                updateGitlabCommitStatus name: 'build', state: 'pending'
                git "${GIT_URL}"
            }
        }

        stage('Credentials') {
            steps {
                withCredentials([file(credentialsId: 'application-prod-yml', variable: 'APPLICATION_PROD_YML')]) {
                    sh 'cp $APPLICATION_PROD_YML ./backend/src/main/resources/application-prod.yml'
                }
            }
        }

        stage('Gradle Build') {
            steps {
                updateGitlabCommitStatus name: 'build', state: 'running'
                sh './backend/gradlew clean bootJar -Dspring.profiles.active=prod'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t backend ./backend/'
            }
        }

        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
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
}
