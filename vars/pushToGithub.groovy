def call(String branch, String commitMessage, String creds) {
    echo "Pushing changes to GitHub on branch ${branch}"

    withCredentials([usernamePassword(credentialsId: creds, usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
        sh """
            # Clean workspace to prevent checkout errors
            git reset --hard
            git clean -fd

            # Switch to branch or create it
            git fetch origin
            git checkout ${branch} || git checkout -b ${branch}

            # Configure Git
            git config user.name "${GIT_USER}"
            git config user.email "jenkins@local"

            # Add and commit only deployment.yaml
            git add deployment.yaml
            git commit -m "${commitMessage}" || echo "No changes to commit"

            # Push changes
            git push https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/jenkins.git ${branch}
        """
    }
}

