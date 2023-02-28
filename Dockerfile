FROM openjdk:17
EXPOSE 8080
ADD target/reactive-programming.jar reactive-programming.jar
ENTRYPOINT ["java","-jar","/reactive-programming.jar"]