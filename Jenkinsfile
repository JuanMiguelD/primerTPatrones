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
                args:
                - --dockerfile=Dockerfile  # Ajusta la ruta si es necesario
                - --context=dir:///workspace
                - --destination=juanmigueld/api_names:${BUILD_NUMBER}
                - --cache=true
                - --verbosity=debug
                - --skip-tls-verify
                volumeMounts:
                - name: docker-config
                  mountPath: /kaniko/.docker/
              restartPolicy: Never
              volumes:
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
                sh 'ls -la /workspace'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                container('kaniko') {
                    script {
                        sh ''' 
                            /kaniko/executor --dockerfile=Dockerfile \
                            --context=dir:///workspace \
                            --destination=juanmigueld/api_names:${BUILD_NUMBER} \
                            --cache=true \
                            --verbosity=debug \
                            --skip-tls-verify
                        '''
                    }
                }
            }
        }
    }
}