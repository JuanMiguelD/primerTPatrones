pipeline {
    agent {
        kubernetes {
            yaml """
            apiVersion: v1
            kind: Pod
            spec:
              containers:
              - name: maven
                image: maven:3.8.1-openjdk-21
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

        stage('Verificar Contexto con tecto') {
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