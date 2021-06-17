pipeline {
    agent any
    environment {
        def BUILDVERSION = sh(script: "echo `date +%d%m%H%M`", returnStdout: true).trim()
    }
        stages {
            stage('build') {
                steps {
                    sh './mvnw package'
                    sh 'docker build --no-cache -t java-user:BUILDVERSION .'
                }
            }
        }
}
