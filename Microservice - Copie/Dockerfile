# Conteneur basé sur l'image officielle Docker openjdk-8-jdk, cf. https://github.com/docker-library/official-images/blob/master/library/java
FROM java:openjdk-8-jdk

#Ajout d'un user
RUN useradd -d /home/myUser -m -s /bin/bash myUser
USER myUser

# Ajout du jar dans le répertoire /etc/myUser/ du conteneur
ADD ${project.artifactId}-${project.version}.jar /etc/myUser/

# Exposition du port 8080 sur lequel l'application répond
EXPOSE 8080

WORKDIR /etc/myUser/

# Lancement de l'application java avec le paramètre -Djava.security.egd pour
# un démarrage plus rapide du tomcat embarqué cf. https://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/etc/gif/${project.artifactId}-${project.version}.jar"]