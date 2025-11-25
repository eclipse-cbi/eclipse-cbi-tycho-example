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
                sh 'mvn clean verify -Prelease -B'
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
        stage('Maven Central') {
            steps {
                sh 'mvn -P release --batch-mode deploy -DskipTests'                
            }
        }
        stage('Sonar') {
            options {
                timeout(time: 30, unit: 'MINUTES') 
            }
            steps {
                withSonarQubeEnv(installationName: 'SonarCloud.io', credentialsId: 'sonarcloud-token-eclipse-cbi-tycho-example') {
                    sh '''
                        mvn sonar:sonar \
                            -Dmaven.test.failure.ignore=true \
                            -Dsonar.organization=eclipse-cbi \
                            -Dsonar.pullrequest.branch=${CHANGE_BRANCH} \
                            -Dsonar.pullrequest.base=${CHANGE_TARGET} \
                            -Dsonar.pullrequest.key=${CHANGE_ID}\
                            -Dsonar.java.binaries='target/' \
                            -Dsonar.projectKey=eclipse-cbi_eclipse-cbi-tycho-example
                    '''
                }
            }
        }

        stage('Quality Gate') {
            options {
                timeout(time: 30, unit: 'MINUTES') 
            }
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
    }
}