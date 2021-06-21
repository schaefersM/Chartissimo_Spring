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
                    sh 'docker build --no-cache -t java-user:$(date +%d%m%H%M) .'
                }
            }
            stage('tag') {
                steps {
                    sh 'docker tag java-user:$(date +%d%m%H%M) localhost:32000/java-user:$(date +%d%m%H%M) '
                }
            }
            stage('tag-test') {
                steps {
                    sh 'docker tag java-user:$(date +%d%m%H%M) java-user::BUILDVERSION'
                }
            }
            stage('push') {
                steps {
                    sh 'docker push localhost:32000/java-user:$(date +%d%m%H%M)'
                }
            }
        }
}
