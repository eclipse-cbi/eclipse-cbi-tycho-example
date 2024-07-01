pipeline {
    agent any
    options {
        disableConcurrentBuilds()
        timeout(time: 10, unit: 'MINUTES')
    }
    tools {
        maven 'apache-maven-3.9.6'
        jdk 'temurin-jdk17-latest'
    }
    environment {
        LOGIN = 'genie.cbi@projects-storage.eclipse.org'
        SITE_DIR = '/home/data/httpd/download.eclipse.org/cbi/cbi-tycho-example/updatesite'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean verify -Prelease -B'
            }
        }
        // stage('Deploy') {
        //     steps {
        //         sshagent(['projects-storage.eclipse.org-bot-ssh']) {
        //             sh """
        //                echo "Deploying..."
        //                #check if dir already exists
        //                if ssh ${LOGIN} test -d ${SITE_DIR}; then
        //                  ssh ${LOGIN} rm -rf ${SITE_DIR}
        //                fi
        //                ssh ${login} mkdir -p ${SITE_DIR}
        //                scp -r org.eclipse.cbi.tycho.example.updatesite/target/repository ${LOGIN}:${SITE_DIR}
        //                """
        //         }
        //     }
        // }
        stage('ossrh') {
            steps {
                sh 'mvn -X deploy -Possrh -B'
            }
        }
    }
}