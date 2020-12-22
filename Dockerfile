FROM openjdk:8-jdk
VOLUME /tmp
ENV TZ Asia/Shanghai
ENV spring.profiles.active product
COPY target/grpc-dubbo-proxy-1.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
