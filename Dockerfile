FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY build/libs/*.jar hackernews.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /hackernews.jar $HACKERNEWS