def call() {
    echo "Pushing changes to GitHub"
    withCredentials([usernamePassword(credentialsId: env.GIT_CREDS, usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
        sh """
            git config user.name "$GIT_USER"
            git config user.email "$GIT_USER@domain.com"
            git add deployment.yaml
            git commit -m "${env.COMMIT_MESSAGE}" || echo "No changes to commit"
            git push https://$GIT_USER:$GIT_PASS@github.com/rowidarafiek/Argocd.git ${env.BRANCH_NAME}
        """
    }
}

