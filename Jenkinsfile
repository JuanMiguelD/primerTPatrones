pipeline {
    agent {
        kubernetes {
            yaml """
            apiVersion: v1
            kind: Pod
            spec:
              containers:
              - name: kaniko
                image: gcr.io/kaniko-project/executor:latest
                command:
                - /kaniko/executor
                args:
                - --dockerfile=Dockerfile
                - --context=dir:///workspace/api-names_Pipeline
                - --destination=juanmigueld/api_names:\${BUILD_NUMBER}
                - --cache=true
                - --verbosity=debug
                - --skip-tls-verify
                volumeMounts:
                - name: docker-config
                  mountPath: /kaniko/.docker/
              restartPolicy: Never  # Evita reiniciar pods fallidos
              volumes:
              - name: docker-config
                secret:
                  secretName: regcred
            """
        }
    }

    environment {
        DOCKER_IMAGE = "juanmigueld/api_names"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        HELM_RELEASE = "api-names"
        HELM_REPO_URL = "https://github.com/JuanMiguelD/api-names_chart.git"
        HELM_CHART_PATH = "api-names_chart"
        NAMESPACE = "jenkins"
    }

    stages {
        stage('Verificar Secreto Docker') {
            steps {
                script {
                    sh "kubectl get secret regcred -n jenkins || echo '❌ ERROR: El secreto regcred no existe'"
                }
            }
        }

        stage('Checkout Código') {
            steps {
                git branch: 'main', url: 'https://github.com/JuanMiguelD/primerTPatrones.git'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                container('kaniko') {
                    script {
                        echo "Ejecutando Kaniko con logs detallados..."
                        sh ''' 
                            /kaniko/executor --dockerfile=Dockerfile \
                            --context=dir:///workspace/api-names_Pipeline \
                            --destination=$DOCKER_IMAGE:$DOCKER_TAG \
                            --cache=true \
                            --verbosity=debug \
                            --skip-tls-verify
                        '''
                    }
                }
            }
        }

        stage('Clone Helm Chart Repo') {
            steps {
                script {
                    sh "rm -rf $HELM_CHART_PATH"
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
