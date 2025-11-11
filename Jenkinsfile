@Library('shared-library') _

pipeline {
    agent { label 'new-agent' }

    environment {
        IMAGE_NAME = "rowidarafiek/app"
        IMAGE_TAG = "23"
        DOCKER_CREDS = 'dockerhub-cred'
        GIT_CREDS = 'github-cred'
        BRANCH_NAME = 'dev'
        COMMIT_MESSAGE = "Automated update from Jenkins ${IMAGE_TAG}"
    }

    stages {
           stage('Test Docker') {
            steps {
                sh 'whoami'
                sh 'groups'
                sh 'docker ps'
            }
        stage('Run Unit Tests') {
            steps {
                unitTests()
            }
        }

        stage('Build the Application') {
            steps {
                buildApp()
            }
        }

        stage('Build Docker Image') {
            steps {
                buildDockerImage(IMAGE_NAME, IMAGE_TAG)
            }
        }

        stage('Push Docker Image to Registry') {
            steps {
                pushDockerImage(IMAGE_NAME, IMAGE_TAG, DOCKER_CREDS)
            }
        }

        stage('Update Deployment YAML') {
            steps {
                updateDeploymentYaml(IMAGE_NAME, IMAGE_TAG)
            }
        }

        stage('Push Deployment to GitHub') {
            steps {
                pushToGithub(BRANCH_NAME)
            }
        }

        stage('Remove Local Docker Image') {
            steps {
                removeDockerImage(IMAGE_NAME, IMAGE_TAG)
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed'
        }
        success {
            echo 'Pipeline completed successfully'
        }
        failure {
            echo 'Pipeline completed with failure'
        }
    }
}

