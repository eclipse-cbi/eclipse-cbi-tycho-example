def secrets = [
  [path: 'cbi/technology.cbi/develocity.eclipse.org', secretValues: [
    [envVar: 'DEVELOCITY_ACCESS_KEY', vaultKey: 'api-token']
    ]
  ]
]

pipeline {
    agent any
    options {
        disableConcurrentBuilds()
        timeout(time: 10, unit: 'MINUTES')
    }
    tools {
        maven 'apache-maven-3.9.6'
        jdk 'openjdk-jdk17-latest'
    }
    environment {
        LOGIN = 'genie.cbi@projects-storage.eclipse.org'
        SITE_DIR = '/home/data/httpd/download.eclipse.org/cbi/cbi-tycho-example/updatesite'
    }
    stages {
        stage('Build') {
            steps {
                withVault([vaultSecrets: secrets]) {
                    sh 'mvn clean verify -Prelease -B'
                }
            }
        }
        stage('Deploy') {
            steps {
                sshagent(['projects-storage.eclipse.org-bot-ssh']) {
                    sh """
                       echo "Deploying..."
                       #check if dir already exists
                       if ssh ${LOGIN} test -d ${SITE_DIR}; then
                         ssh ${LOGIN} rm -rf ${SITE_DIR}
                       fi
                       ssh ${login} mkdir -p ${SITE_DIR}
                       scp -r org.eclipse.cbi.tycho.example.updatesite/target/repository ${LOGIN}:${SITE_DIR}
                       """
                }
            }
        }
    }
}