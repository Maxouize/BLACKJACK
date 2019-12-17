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
            sh 'mvn clean'
            echo "Clean done"
            sh 'mvn package'
			echo "Fin Build..."
          }
      }
      
      /*stage ('Analyse'){
		steps {
			echo "Analyse..."
			sh 'mvn checkstyle:checkstyle'
			sh 'mvn pmd:pmd'
			sh 'mvn spotbugs:spotbugs'
      	}
      }*/
   }
}