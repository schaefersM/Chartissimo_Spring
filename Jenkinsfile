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
        }
}
