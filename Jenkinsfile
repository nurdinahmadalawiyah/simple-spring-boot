pipeline {
    agent any

    stages {
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn clean install'
                    sh 'mvn clean package verify sonar:sonar'
                    echo 'SonarQube Analysis Completed'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                waitForQualityGate abortPipeline: true
                echo 'Quality Gate Completed'
            }
        }
        stage("Send Message to Telegram") {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'telegram-token', varible: 'token'),
                        string(credentialsId: 'telegram-chat-id', variable: 'chatId')
                    ]) {
                        echo "Token: ${token}"
                        echo "Chat ID: ${chatId}"
                        def buildStatus = currentBuild.currentResult ?: 'UNKNOWN'
                        sh "curl -X POST
                        \"https://api.telegram.org/bot${token}/sendMessage\" -d
                        \"chat_id=${chatId}\" -d \"text=Build Project ${JOB_NAME} status is ${buildStatus}\""
                    }
                }
            }
        }
    }
}