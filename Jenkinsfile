/**
 * Copyright © 2019-2025, jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *    Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Builds and deploys the project.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0.0
 * @version 1.4.0
 */

def groupId
def artifactId
def version
def isSnapshot

pipeline {

    options {
        buildDiscarder(logRotator(numToKeepStr: "3"))
        disableConcurrentBuilds()
        timeout(time: 60, unit: "MINUTES")
    }

    agent {
        label "maven"
    }

    stages {

        /**
        * The stage will checkout the current branch.
        */
        stage("Checkout Build") {
            steps {
                container("maven") {
                    checkout scm
                }
            }
        } // stage

        /**
        * Setups the pipeline.
        */
        stage("Setup") {
            steps {
                container("maven") {
                    script {
                        groupId = sh script: 'mvn help:evaluate -Dexpression=project.groupId -q -DforceStdout', returnStdout: true
                        artifactId = sh script: 'mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout', returnStdout: true
                        version = sh script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
                        isSnapshot = (version =~ /(?i).*-snapshot$/).matches()
                        echo "${groupId}/${artifactId}:${version} snapshot: ${isSnapshot}"
                    }
                }
            }
        } // stage

        /**
        * The stage will compile, test and deploy on all branches.
        */
        stage("Compile, Test and Deploy") {
            steps {
                container("maven") {
                    script {
                        if (isSnapshot) {
                            sh "/setup-gpg.sh; mvn -s /m2/settings.xml -B clean install site:site deploy site:deploy"
                        } else {
                            sh "/setup-gpg.sh; mvn -s /m2/settings.xml -B clean install site:site site:deploy"
                        }
                    }
                }
            }
        } // stage

        /**
        * The stage will deploy the artifacts and the generated site to the public repository from the main branch.
        */
        stage("Publish to Private") {
            when {
                allOf {
                    expression { !isSnapshot }
                    branch "main"
                }
            }
            steps {
                container("maven") {
                    sh "/setup-gpg.sh; mvn -s /m2/settings.xml -B deploy"
                }
            }
        } // stage

        /**
        * The stage will deploy the artifacts and the generated site to the public repository from the main branch.
        */
        stage("Publish to Public") {
            when {
                allOf {
                    expression { !isSnapshot }
                    branch "main"
                }
            }
            steps {
                container("maven") {
                    sh "/setup-gpg.sh; mvn -s /m2/settings.xml -Posssonatype -B deploy"
                }
            }
        } // stage

    } // stages

    post {
        success {
            container("maven") {
                script {
                    manager.createSummary("document.png").appendText("<a href=\"${env.JAVADOC_URL}/${groupId}/${artifactId}/${version}/index.html\">View Maven Site</a>", false)
                }
            }
        }
    } // post

}
