FROM openjdk:14-jdk-buster

RUN mkdir /src 
COPY mvnw /src 
COPY pom.xml /src
COPY .mvn /src/.mvn

RUN cd /src &&\
    chmod a+x mvnw &&\
    ./mvnw clean
    
COPY src /src/src
RUN cd /src &&\
    ./mvnw install -DskipTests
    
ENTRYPOINT ["java" \
    ,"-Dfile.encoding=UTF-8"\
    ,"-Dspring.profiles.active=docker"\
    ,"-jar","/src/target/hdp-1.0.jar"]