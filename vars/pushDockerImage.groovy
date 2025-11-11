def call() {
    withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
    echo 'Pushing Docker image to registry...'
    sh '''
    docker login -u $DOCKER_USER -p $DOCKER_PASSWORD
    docker push $IMAGE_NAME:$BUILD_NUMBER
    '''
}
}

