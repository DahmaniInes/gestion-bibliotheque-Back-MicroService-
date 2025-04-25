FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/biblio-gestionCatalogues-0.0.1-SNAPSHOT.jar biblio-gestionCatalogues.jar
EXPOSE 8081
CMD ["java", "-jar", "biblio-gestionCatalogues.jar"]