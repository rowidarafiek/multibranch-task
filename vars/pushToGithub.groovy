def call(String branch, String commitMessage) {
    withCredentials([usernamePassword(credentialsId: 'github-cred', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
        
        // Step 1: Remove build artifacts and untracked files
        sh '''
            rm -rf target/
            git reset --hard
            git clean -fdx
        '''

        // Step 2: Checkout branch safely
        sh """
            git fetch origin ${branch}
            if git show-ref --verify --quiet refs/heads/${branch}; then
                git checkout ${branch}
            else
                git checkout -b ${branch}
            fi
            git config user.name "${GIT_USER}"
            git config user.email "jenkins@local"
        """

        // Step 3: Commit changes if any
        sh """
            git add .
            git diff --cached --quiet || git commit -m "${commitMessage}"
        """

        // Step 4: Push
        sh '''
            git push https://$GIT_USER:$GIT_PASS@github.com/rowidarafiek/multibranch-task.git ${branch}
        '''
    }
}

