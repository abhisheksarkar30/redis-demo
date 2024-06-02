FROM bellsoft/liberica-openjdk-alpine
LABEL authors="abhisheksarkar30"
# Below is documentation of listening port which should be published to run a container of this image
EXPOSE 8080
ENV APP_NAME=redis-demo
ADD target/${APP_NAME}*.jar ${APP_NAME}.jar
# Won't be able to override below ENTRYPOINT (in shell mode) while creating container, will be treated as start command
# no other java Program args could be appended with this, only solution docker --env variables
ENTRYPOINT java -jar ${APP_NAME}.jar