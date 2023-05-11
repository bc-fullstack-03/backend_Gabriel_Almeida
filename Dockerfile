FROM eclipse-temurin:17-jdk-alpine
ADD target/socialMedia-*.jar socalMedia.jar
ENTRYPOINT ["java", "-jar", "/socialMedia.jar"]