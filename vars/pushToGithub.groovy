stage('Push Changes to GitHub') {
    steps {
        script {
            withCredentials([usernamePassword(
                credentialsId: GIT_CREDS,
                usernameVariable: 'GIT_USER',
                passwordVariable: 'GIT_PASS'
            )]) {
                sh """
                    git reset --hard
                    git clean -fdx
                    git fetch origin
                    git checkout ${BRANCH_NAME} || git checkout -b ${BRANCH_NAME}
                    git config user.name "${GIT_USER}"
                    git config user.email "jenkins@local"
                    git add deployment.yaml
                    git diff --cached --quiet || git commit -m "${COMMIT_MESSAGE}"
                    git push https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/multibranch-task.git ${BRANCH_NAME}
                """
            }
        }
    }
}

