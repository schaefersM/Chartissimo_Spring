pipeline {
    agent any
    environment {
        def BUILDVERSION = sh(script: "echo `date +%d%m%H%M`", returnStdout: true).trim()
    }
        stages {
            stage('package') {
                steps {
                    sh './mvnw package'
                }
            }
            stage('build') {
                steps {
                    sh 'docker build --no-cache -t java-user:${BUILDVERSION} .'
                }
            }
            stage('tag') {
                steps {
                    sh 'docker tag java-user:${BUILDVERSION} localhost:32000/java-user:${BUILDVERSION}'
                }
            }
            stage('push') {
                steps {
                    sh 'docker push localhost:32000/java-user:${BUILDVERSION}'
                }
            }
        }
}
