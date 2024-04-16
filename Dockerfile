FROM openjdk:21

WORKDIR /app

COPY ./src/main/resources/db/migration /app/db/migration

COPY ./target/api-proc-arquivo-0.0.1-SNAPSHOT.jar /app/api-proc-arquivo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-proc-arquivo.jar"]