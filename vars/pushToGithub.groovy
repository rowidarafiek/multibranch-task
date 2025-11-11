def call() {

    withCredentials([usernamePassword(credentialsId: 'github', 
                                      usernameVariable: 'GIT_USER', 
                                      passwordVariable: 'GIT_PASS')]) {
        sh '''
            # Configure Git
            git config user.name "rowidarafiek"
            git config user.email "rowidarafiek@domain.com"
            
            # Get branch name
            BRANCH=${BRANCH_NAME:-dev}
            echo "Current branch: $BRANCH"
            
            # Ensure we're on a branch (fix detached HEAD)
            git checkout -B $BRANCH
            
            # Stage changes
            git add ${DEPLOYMENT_FILE}
            
            # Check if there are changes to commit
            if git diff --staged --quiet; then
                echo "No changes to commit"
            else
                # Commit changes
                git commit -m "Automated update from Jenkins build #${BUILD_NUMBER}"
                
                # Push to remote
                git push https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/Argocd.git HEAD:$BRANCH
                
                echo "Successfully pushed changes to GitHub"
            fi
        '''
    }
}
