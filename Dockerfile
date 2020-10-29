# build stage
FROM openjdk:8 AS build-stage
ENV APP_HOME=/usr/dockerbuild/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build

# production stage
FROM openjdk:8-jre-alpine AS production-stage
ENV APP_HOME=/usr/app/
ENV APPLICATION_USER=www
RUN adduser -D -g '' $APPLICATION_USER
RUN mkdir $APP_HOME
RUN chown -R $APPLICATION_USER $APP_HOME
USER $APPLICATION_USER
WORKDIR $APP_HOME
COPY --from=build-stage /usr/dockerbuild/app/build/libs/app.jar .
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "app.jar"]
EXPOSE 8080
