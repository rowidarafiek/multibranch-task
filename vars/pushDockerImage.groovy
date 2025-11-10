// vars/pushDocker.groovy
def call(String imageName, String imageTag, String dockerCreds) {
    echo "Pushing Docker image ${imageName}:${imageTag}"
    withCredentials([usernamePassword(credentialsId: dockerCreds, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh '''
            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
            docker push ${imageName}:${imageTag}
        '''
    }
}

