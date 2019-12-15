FROM openjdk:8-alpine

COPY target/uberjar/musicbook.jar /musicbook/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/musicbook/app.jar"]
