def call(String branch = 'dev') {
    echo "Pushing deployment.yaml to GitHub branch ${branch}..."
    withCredentials([usernamePassword(credentialsId: 'github-cred', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
        sh """
            rm -rf /tmp/multibranch_repo
            git clone https://\$GIT_USER:\$GIT_PASS@github.com/rowidarafiek/multibranch-task.git /tmp/multibranch_repo
            cd /tmp/multibranch_repo
            if git show-ref --verify --quiet refs/heads/${branch}; then
                git checkout ${branch}
            else
                git checkout -b ${branch}
            fi
            cp ${WORKSPACE}/deployment.yaml .
            git config user.name "\$GIT_USER"
            git config user.email "jenkins@ci.com"
            git add deployment.yaml
            git diff --cached --quiet || git commit -m "Update deployment.yaml for ${branch}"
            git push origin ${branch}
        """
    }
}

