def call() {
    echo "Updating deployment for namespace ${env.NAMESPACE}"
    
    def deploymentFile = "${env.NAMESPACE}/deployment.yaml"

    // Update image
    sh """
    sed -i "s|image: .*|image: ${env.IMAGE_NAME}:${env.BUILD_NUMBER}|" ${deploymentFile}
    """

    // Commit and push
    withCredentials([usernamePassword(credentialsId: 'argocd-cred', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
        sh """
        git config user.name "rowidarafiek"
        git config user.email "rowidarafiek@domain.com"

        git fetch origin
        git checkout -B ${env.BRANCH_NAME} origin/${env.BRANCH_NAME} || git checkout -B ${env.BRANCH_NAME}

        git add ${deploymentFile}
        git commit -m "${env.COMMIT_MESSAGE}"

        git remote set-url origin https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/Argocd.git
        git push origin ${env.BRANCH_NAME}

        echo "Deployment file ${deploymentFile} updated and pushed"
        """
    }
}

