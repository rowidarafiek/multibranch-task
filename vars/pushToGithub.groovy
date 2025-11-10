def call(String branch, String commitMessage, String creds) {
    echo "Pushing changes to GitHub on branch ${branch}"
    
    withCredentials([usernamePassword(credentialsId: creds, usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
        sh """
            git reset --hard
            git clean -fd
            git checkout ${branch} || git checkout -b ${branch}
            git config user.name "${GIT_USER}"
            git config user.email "jenkins@local"
            git add deployment.yaml
            git commit -m "${commitMessage}" || echo "No changes to commit"
            git push https://${GIT_USER}:${GIT_PASS}@github.com/rowidarafiek/jenkins.git ${branch}
        """
    }
}

