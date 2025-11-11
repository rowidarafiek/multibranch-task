def call() {
  echo "Building Java application..."
    sh 'mvn clean package -DskipTests'

    echo "Verifying build output..."
    sh 'ls -lh target || echo "Target folder not found"'

    echo "Building Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
    sh """
        if [ ! -f target/demo-0.0.1-SNAPSHOT.jar ]; then
            echo "Error: JAR file not found in target/"
            exit 1
        fi


 }



