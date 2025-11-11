def call() {
          echo "Building Java app..."
    sh 'mvn clean package -DskipTests'

    echo "Verifying JAR..."
    sh '''
        if [ ! -f target/demo-0.0.1-SNAPSHOT.jar ]; then
            echo "JAR not found after build."
            exit 1
        fi
        ls -lh target/
    '''
}

