pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "juanmigueld/api_names"
        DOCKER_TAG = "${env.BUILD_NUMBER}"  // Tag dinámico
        HELM_RELEASE = "api-names"
        HELM_REPO_URL = "https://github.com/JuanMiguelD/api-names_chart.git"
        HELM_CHART_PATH = "api-names_chart"  // Carpeta donde clonar el repo
        NAMESPACE = "default"
    }

    stages {
        stage('Checkout Código') {
            steps {
                git branch: 'main', url: 'https://github.com/JuanMiguelD/primerTPatrones.git'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    sh """
                    /kaniko/executor --context=/workspace --dockerfile=/workspace/Dockerfile --destination=juanmigueld/api_names:$DOCKER_TAG
                    """
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
