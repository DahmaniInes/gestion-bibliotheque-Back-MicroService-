# Étape 1 : Utilisation de l'image de base OpenJDK 17
FROM openjdk:17

# Étape 2 : Définir le répertoire de travail
WORKDIR /app
# Étape 3 : Copier le fichier JAR du Gateway
COPY target/*.jar gateway-service.jar

# Étape 4 : Exposer le port Eureka
EXPOSE 8093

# Étape 5 : Lancer le Gateway
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]




