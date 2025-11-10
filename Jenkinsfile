@Library('shared-library') _

pipeline {
    agent { label 'linux-docker' }

    environment {
        IMAGE_NAME = "rowidarafiek/app"
        IMAGE_TAG = "23"
        DOCKER_CREDS = 'dockerhub-cred'
        GIT_CREDS = 'github-cred'
        BRANCH_NAME = 'dev'
        COMMIT_MESSAGE = "Automated update from Jenkins ${IMAGE_TAG}"
    }

    stages {
        stage('Clean Workspace') {
    steps {
        sh 'git reset --hard; git clean -fdx'
    }
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

        stage('Push Changes to GitHub') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: GIT_CREDS,
                        usernameVariable: 'GIT_USER',
                        passwordVariable: 'GIT_PASS'
                    )]) {
                        sh """
                            git checkout ${BRANCH_NAME} || git checkout -b ${BRANCH_NAME}
                            git config user.name "${GIT_USER}"
                            git config user.email "jenkins@local"
                            git add .
                            git commit -m "${COMMIT_MESSAGE}" || echo "No changes to commit"
                            git push https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/multibranch-task.git ${BRANCH_NAME}
                        """
                    }
                }
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

