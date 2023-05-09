FROM eclipse-temurin:17-jdk-alpine
ADD target/demo-*.jar demo.jar
ENTRYPOINT ["java", "-jar", "/demo.jar"]