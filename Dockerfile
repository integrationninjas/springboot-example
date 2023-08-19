FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/springboot-example.jar springboot-examle.jar
EXPOSE 8080
CMD ["java","-jar","springboot-example.jar"]