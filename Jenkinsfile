@Library('shared-library') _

pipeline {
    agent { label 'new-agent' }

    environment {
    IMAGE_NAME = "rowidarafiek/app"
    DOCKER_CREDS = 'dockerhub-cred'
    GIT_CREDS = 'github-cred'
    BRANCH_NAME = 'stag'
    NAMESPACE = 'stag'
    COMMIT_MESSAGE = "Update deployment for ${BRANCH_NAME}"
    ARGO_REPO = "https://github.com/rowidarafiek/Argocd.git"
}   

    stages {
        stage('Run Unit Tests') {
            steps { script { unitTests() } }
        }

        stage('Build the Application') {
            steps { script { buildApp() } }
        }

        stage('Build Docker Image') {
            steps { script { buildDockerImage() } }
        }

        stage('Push Docker Image') {
            steps { script { pushDockerImage() } }
        }

        stage('Update Deployment YAML') {
            steps { script { updateDeploymentYaml() } }
        }
          stage('Remove Local Docker Image') {
            steps {
                script { removeDockerImage() }
            }
        }
        stage('Push to GitHub') {
            steps { script { pushToGithub() } }
        }
    }

    post {
        always { echo 'Pipeline completed' }
        success { echo 'Pipeline completed successfully' }
        failure { echo 'Pipeline completed with failure' }
    }
}

