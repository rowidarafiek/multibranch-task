def call() {
    withCredentials([usernamePassword(credentialsId: 'argocd-cred', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
        // Deployment file path based on namespace
        def deploymentFile = "${env.NAMESPACE}/deployment.yaml"

        sh """
            git config user.name "rowidarafiek"
            git config user.email "rowidarafiek@domain.com"

            # Ensure branch exists locally
            git fetch origin
            git checkout -B ${env.BRANCH_NAME} origin/${env.BRANCH_NAME} || git checkout -B ${env.BRANCH_NAME}

            # Stage deployment file
            git add ${deploymentFile}

            # Commit changes
            git commit -m "${env.COMMIT_MESSAGE}"

            # Push to ArgoCD repo with credentials
            git remote set-url origin https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/Argocd.git
            git push origin ${env.BRANCH_NAME}

            echo "Deployment file ${deploymentFile} pushed to ArgoCD repository"
        """
    }
}


