FROM eclipse-temurin:17-jre-alpine
RUN mkdir /opt/app
COPY build/libs/*.jar /opt/app/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
CMD ["--spring.profiles.active=prod"]
