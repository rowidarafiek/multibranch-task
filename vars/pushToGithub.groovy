def call(String branch = 'dev') {
    echo "Pushing deployment.yaml to GitHub branch ${branch}..."

    withCredentials([usernamePassword(credentialsId: 'github-pat', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {

        // Remove old clone and clone fresh repo
        sh """
            rm -rf /tmp/multibranch_repo
            git clone https://\$GIT_USER:\$GIT_PASS@github.com/rowidarafiek/multibranch-task.git /tmp/multibranch_repo
        """

        dir("/tmp/multibranch_repo") {
            // Clean any previous changes
            sh """
                git reset --hard
                git clean -fdx
            """

            // Checkout branch or create if it doesn't exist
            sh """
                if git show-ref --verify --quiet refs/heads/${branch}; then
                    git checkout ${branch}
                else
                    git checkout -b ${branch}
                fi
            """

            // Copy updated deployment.yaml
            sh """
                cp ${WORKSPACE}/deployment.yaml .
            """

            // Configure Git user
            sh """
                git config user.name "\$GIT_USER"
                git config user.email "jenkins@ci.com"
            """

            // Commit if there are changes
            sh """
                git add deployment.yaml
                git diff --cached --quiet || git commit -m "Update deployment.yaml for ${branch}"
            """

            // Push to remote
            sh """
                git push https://\$GIT_USER:\$GIT_PASS@github.com/rowidarafiek/multibranch-task.git ${branch}
            """
        }
    }
}

