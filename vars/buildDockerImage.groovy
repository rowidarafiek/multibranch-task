def call() {    echo "Building Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
    sh """
        docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
        docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
    """
}
