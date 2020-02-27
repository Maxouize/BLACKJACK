// Lien vers Nexus, doit correspondre � l'instance param�tr�e dans Jenkins
def nexusId = 'nexus'

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
    NEXUS_URL = "http://localhost:8081"
    // Repository where we will upload the artifact
    NEXUS_REPOSITORY = "repository-example"
    // Jenkins credential id to authenticate to Nexus OSS
    NEXUS_CREDENTIAL_ID = "nexussonar"
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
      }
  	  /*
      Ce stage ne se lance que si isSnapshot est vrai
      Comme on pousse un Snapshot, on utilise le plugin deploy:deploy-file, cela permet de ne pas mettre les param�tres du Repo dans le pom.xml
      */
      stage('Push SNAPSHOT to Nexus') {
          when { expression { isSnapshot } }
          steps {
              sh "mvn deploy:deploy-file -e -DgroupId=${groupId} -Dversion=${version} -Dpackaging=${packaging} -Durl=${nexusUrl}/repository/${nexusRepoSnapshot}/ -Dfile=${filepath} -DartifactId=${artifactId} -DrepositoryId=${mavenRepoId}"

          }
      }
     
     /*
     Ce stage ne se lance que si isSnapshot est faux
     On pousse la release via le plugin Nexus
     */
      stage('Push RELEASE to Nexus') {
          when { expression { !isSnapshot } }
          steps {
            nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: "${nexusRepoRelease}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${filepath}"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${groupId}", packaging: "${packaging}", version: "${version}"]]]
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