def call(String imageName, String tag) {
    echo "Building Docker image ${imageName}:${tag}"
    sh "sudo docker build -t ${imageName}:${tag} ."
}

