pipeline {
    agent {
        kubernetes {
            yaml """
            apiVersion: v1
            kind: Pod
            spec:
              containers:
              - name: maven
                image: eclipse-temurin:21-jdk
                command:
                - sleep
                args:
                - infinity
              - name: docker
                image: docker:latest
                command:
                - sleep
                args:
                - infinity
                volumeMounts:
                - name: docker-sock
                  mountPath: /var/run/docker.sock
              volumes:
              - name: docker-sock
                hostPath:
                  path: /var/run/docker.sock
              - name: docker-config
                secret:
                  secretName: regcred
            """
        }
    }

    stages {
        stage('Checkout Código') {
            steps {
                git branch: 'main', url: 'https://github.com/JuanMiguelD/primerTPatrones.git'
            }
        }

        stage('Verificar Contexto') {
            steps {
                sh 'ls -la'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                container('docker') {
                    script {
                        // Usamos credenciales almacenadas en Jenkins en lugar de buscar en el pod
                        withCredentials([usernamePassword(credentialsId: 'DOCKER_CREDENTIALS', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                            sh '''
                                # Login a Docker Hub con las credenciales de Jenkins
                                echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                                
                                # Construir y etiquetar la imagen
                                docker build -t juanmigueld/api_names:${BUILD_NUMBER} .
                                
                                # Empujar la imagen al registro
                                docker push juanmigueld/api_names:${BUILD_NUMBER}
                            '''
                        }
                    }
                }
            }
        }

    }
}