FROM openjdk:17
EXPOSE 8081
ADD target/biblio-gestionCatalogues-0.0.1-SNAPSHOT.jar biblio-gestionCatalogues.jar
ENTRYPOINT ["java", "-jar", "biblio-gestionCatalogues.jar"]