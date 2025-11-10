@Library('shared-library') _

pipeline {
    agent { label 'linux-docker' }

    environment {
        IMAGE_NAME = "rowidarafiek/app"
        DOCKER_CREDS = 'dockerhub-cred'
    }

    stages {
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
                buildDockerImage()
            }
        }

        stage('Push Docker Image to Registry') {
            steps {
                pushDockerImage()
            }
        }

        stage('Update Deployment deployment.yaml') {
            steps {
                updateDeploymentYaml()
            }
        }

        stage('Push to GitHub') {
            steps {
                pushToGithub()
            }
        }
    }

    post {
        always {
            echo 'pipeline completed'
        }
        success {
            echo 'pipeline completed successfully'
        }
        failure {
            echo 'pipeline completed with failure'
        }
    }
}

