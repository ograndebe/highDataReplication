FROM openjdk:14-jdk-buster

COPY src /src/src
COPY mvnw /src 
COPY pom.xml /src
COPY .mvn /src/.mvn

RUN cd /src &&\
    chmod a+x mvnw &&\
    ./mvnw clean
    
RUN cd /src &&\
    ./mvnw install -DskipTests
    
ENTRYPOINT ["java" \
    ,"-Dfile.encoding=UTF-8"\
    ,"-Dspring.profiles.active=docker"\
    ,"-jar","/src/target/hdp-1.0.jar"]