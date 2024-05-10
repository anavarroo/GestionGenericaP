pipeline {
    agent any
    tools {
        maven 'Maven_3_9_6'
    }
    stages {
        stage('Build Maven') {
            steps {
                checkout scmGit(branches: [[name: '*/feature-ale-v.2']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/anavarroo/GestionGenericaP']])
                script {
                    bat 'mvn clean test'
                }
                script {
                    currentBuild.result = 'SUCCESS'
                    if (currentBuild.result == 'FAILURE') {
                        error "Build failed due to failing tests"
                    }
                }
                script {
                    bat 'mvn clean install'
                }
            }
        }
        stage('Build docker images') {
            steps {
                dir('api-gateway') {
                    bat 'docker build -t api-gateway .'
                }
                dir('crud-service') {
                    bat 'docker build -t crud-service .'
                }
                dir('discovery-server') {
                    bat 'docker build -t discovery-server .'
                }
                dir('register-service') {
                    bat 'docker build -t register-service .'
                }
            }
        }
        stage('Push docker image to hub') {
            steps {
                script{
                    withCredentials([string(credentialsId: 'DockerHubPassword', variable: 'DockerPassword')]) {
                        bat 'docker login -u aleramiirez -p Arosama4_'
                    }
                def services = ['api-gateway', 'crud-service', 'discovery-server', 'register-service']

                services.each { service ->
                    bat "docker tag $service aleramiirez/generics:$service"
                    bat "docker push aleramiirez/generics:$service"
                }
                }
            }
        }
    }
}
