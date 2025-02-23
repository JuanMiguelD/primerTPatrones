pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "juanmigueld/api_names"
        DOCKER_TAG = "${env.BUILD_NUMBER}"  // Tag dinámico
        HELM_RELEASE = "api-names"
        HELM_REPO_URL = "https://github.com/JuanMiguelD/api-names_chart.git"
        HELM_CHART_PATH = "api-names_chart"  // Carpeta donde clonar el repo
        NAMESPACE = "jenkins"
    }

    stages {
        stage('Checkout Código') {
            steps {
                git branch: 'main', url: 'https://github.com/JuanMiguelD/primerTPatrones.git'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                container('docker') {
                    script {
                        sh "docker version"
                        sh "docker build -t $DOCKER_IMAGE:$DOCKER_TAG ."

                        withCredentials([usernamePassword(credentialsId: 'DOCKER_CREDENTIALS', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PAT')]) {
                            sh "echo $DOCKER_PAT | docker login -u $DOCKER_USER --password-stdin"
                            sh "docker push $DOCKER_IMAGE:$DOCKER_TAG"
                        }
                    }
                }
            }
        }

        stage('Clone Helm Chart Repo') {
            steps {
                script {
                    sh "rm -rf $HELM_CHART_PATH"  // Borra si ya existe
                    sh "git clone $HELM_REPO_URL $HELM_CHART_PATH"
                }
            }
        }

        stage('Deploy con Helm') {
            steps {
                script {
                    sh """
                        helm upgrade --install $HELM_RELEASE $HELM_CHART_PATH \
                        --namespace $NAMESPACE \
                        --set image.repository=$DOCKER_IMAGE \
                        --set image.tag=$DOCKER_TAG
                    """
                }
            }
        }
    }
}