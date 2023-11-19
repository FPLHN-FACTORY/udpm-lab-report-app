# Sử dụng base image Nginx
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar

# Copy tệp JAR vào container
COPY ./build/libs/server-0.0.1-SNAPSHOT.jar app.jar

# Khai báo lệnh chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "/app.jar"]
