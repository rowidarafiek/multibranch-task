def call() {
    echo 'Removing Docker image...'
    sh 'docker rmi $IMAGE_NAME:$BUILD_NUMBER'
}
