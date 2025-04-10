# Étape 1 : Utilisation de l'image de base OpenJDK 17
FROM openjdk:17

# Étape 2 : Définir le répertoire de travail
WORKDIR /app

# Étape 3 : Copier le fichier JAR du serveur Eureka
COPY target/EurekaServer-0.0.1-SNAPSHOT.jar eureka-server.jar

# Étape 4 : Exposer le port Eureka
EXPOSE 8761

# Étape 5 : Lancer Eureka Server
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]