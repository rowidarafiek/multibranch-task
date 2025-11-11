def call(){
} withCredentials([usernamePassword(credentialsId: 'github', 
                                      usernameVariable: 'GIT_USER', 
                                      passwordVariable: 'GIT_PASS')]) {
        sh '''
            git config user.name "rowidarafiek"
            git config user.email "rowidarafiek@domain.com"
            
            BRANCH=${BRANCH_NAME:-dev}
            git checkout -B $BRANCH
            
            git add ${DEPLOYMENT_FILE}
            
            if git diff --staged --quiet; then
                echo "No changes to commit"
            else
                git commit -m "Automated update from Jenkins build #${BUILD_NUMBER}"
                git push https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/Argocd.git HEAD:$BRANCH
                echo "Successfully pushed changes to GitHub"
            fi
        '''
    }
