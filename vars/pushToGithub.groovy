def call(String branchName = 'dev') {
    echo "Pushing deployment.yaml to GitHub branch ${branchName}..."

    withCredentials([usernamePassword(
        credentialsId: 'github-cred', 
        usernameVariable: 'GIT_USER', 
        passwordVariable: 'GIT_PASS'
    )]) {

        // Remove old temp repo if exists
        sh '''
            rm -rf /tmp/multibranch_repo
        '''

        // Clone your repo into temp folder
        sh """
            git clone https://\$GIT_USER:\$GIT_PASS@github.com/rowidarafiek/multibranch-task.git /tmp/multibranch_repo
            cd /tmp/multibranch_repo

            # Checkout branch or create if missing
            if git show-ref --verify --quiet refs/heads/${branchName}; then
                git checkout ${branchName}
            else
                git checkout -b ${branchName}
            fi

            # Clean any previous changes/build files
            git reset --hard
            git clean -fdx

            # Copy updated deployment.yaml
            cp ${WORKSPACE}/deployment.yaml .

            # Configure Git
            git config user.name "\$GIT_USER"
            git config user.email "jenkins@ci.com"

            # Add, commit, and push changes
            git add deployment.yaml
            git diff --cached --quiet || git commit -m "Update deployment.yaml for ${branchName}"
            git push origin ${branchName}
        """
    }

    echo "✅ deployment.yaml pushed successfully to ${branchName} branch."
}

