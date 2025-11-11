def call() {
    echo 'Updating deployment deployment.yaml'
    sh '''
    sed -i "s|image: .*|image: $IMAGE_NAME:$BUILD_NUMBER|" $DOCKER_REGISTRY_FILE
    '''

}
