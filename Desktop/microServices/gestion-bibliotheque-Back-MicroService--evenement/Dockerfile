# Utiliser une image de base avec Java 17
FROM openjdk:17

# Exposer le port sur lequel l'application Spring Boot écoute
EXPOSE 8085

# Copier le fichier JAR généré dans l'image Docker
ADD target/Event-0.0.1-SNAPSHOT.jar app.jar

# Définir le point d'entrée pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]