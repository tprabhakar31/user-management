FROM openjdk:17
COPY target/user-management.jar /usr/app/
WORKDIR /usr/app/
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "user-management.jar" ]