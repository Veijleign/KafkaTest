FROM amazoncorretto:17
LABEL authors="IgnisDivine"

RUN mkdir /app

COPY build/libs/kafkaTest-0.0.1-SNAPSHOT.jar /app/kafkaTest.jar

ENV TZ = "Europe/Moscow"
ENTRYPOINT ["java", "-jar", "/app/kafkaTest.jar"]