// Lien vers Nexus, doit correspondre � l'instance param�tr�e dans Jenkins
def nexusId = 'nexus_localhost'

/* *** Configuration de Nexus pour Maven ***/
// URL de Nexus
def nexusUrl = 'http://localhost:8081'
// Repo Id (provient du settings.xml nexus pour r�cup�rer user/password)
def mavenRepoId = 'nexusLocal'

/* *** Repositories Nexus *** */
def nexusRepoSnapshot = "maven-snapshots"
def nexusRepoRelease = "maven-releases"



/* *** D�tail du projet, r�cup�r� dans le pipeline en lisant le pom.xml *** */
def groupId = ''
def artefactId = ''
def filePath = ''
def packaging = ''
def version = ''

// Variable utilis�e pour savoir si c'est une RELEASE ou une SNAPSHOT
def isSnapshot = true

environment {
    // This can be nexus3 or nexus2
    NEXUS_VERSION = "nexus3"
    // This can be http or https
    NEXUS_PROTOCOL = "http"
    // Where your Nexus is running
    NEXUS_URL = "http://localhost:8082"
    // Repository where we will upload the artifact
    NEXUS_REPOSITORY = "repository-example"
    // Jenkins credential id to authenticate to Nexus OSS
    NEXUS_CREDENTIAL_ID = "nexus-credentials"
}
    
pipeline {
   agent any

   stages {
      stage('Get info from POM') {
          steps {
            script {
                pom = readMavenPom file: 'pom.xml'
                groupId = pom.groupId
                artifactId = pom.artifactId
                packaging = pom.packaging
                version = pom.version
                filepath = "target/${artifactId}-${version}.jar"
                isSnapshot = version.endsWith("-SNAPSHOT")
            }
            echo groupId
            echo artifactId
            echo packaging
            echo version
            echo filepath
            echo "isSnapshot: ${isSnapshot}"
          }
      }
      stage('Build') {
          steps {
			echo "Build..."
            bat 'mvn clean'
            echo "Clean done"
            bat 'mvn package'
			echo "Fin Build..."
          }
      }
      
      stage ('Analyse'){
		steps {
			echo "Analyse..."
			bat 'mvn checkstyle:checkstyle'
			bat 'mvn pmd:pmd'
			bat 'mvn spotbugs:spotbugs'
      	}
  	stage("publish to nexus") {
        steps {
            script {
                pom = readMavenPom file: "pom.xml";
                filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                artifactPath = filesByGlob[0].path;
                artifactExists = fileExists artifactPath;
                if(artifactExists) {
                    echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                    nexusArtifactUploader(
                        nexusVersion: NEXUS_VERSION,
                        protocol: NEXUS_PROTOCOL,
                        nexusUrl: NEXUS_URL,
                        groupId: pom.groupId,
                        version: pom.version,
                        repository: NEXUS_REPOSITORY,
                        credentialsId: NEXUS_CREDENTIAL_ID,
                        artifacts: [
                            // Artifact generated such as .jar, .ear and .war files.
                            [artifactId: pom.artifactId,
                            classifier: '',
                            file: artifactPath,
                            type: pom.packaging],
                            // Lets upload the pom.xml file for additional information for Transitive dependencies
                            [artifactId: pom.artifactId,
                            classifier: '',
                            file: "pom.xml",
                            type: "pom"]
                        ]
                    );
                } else {
                    error "*** File: ${artifactPath}, could not be found";
                }
            }
          }
        }
      }
   }
		  
		   post {
			always {
				junit '**/surefire-reports/*.xml'
				// archiveArtifacts 'target/*.jar'
				recordIssues enabledForFailure: true, tools: [mavenConsole(), java(), javaDoc()]
				recordIssues enabledForFailure: true, tool: checkStyle()
				recordIssues enabledForFailure: true, tool: cpd(pattern: '**/target/cpd.xml')
				recordIssues enabledForFailure: true, tool: pmdParser(pattern: '**/target/pmd.xml')
				recordIssues enabledForFailure: true, tool: spotBugs()
			}
         }
}