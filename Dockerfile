FROM openjdk:22
COPY . /usr/var/www/MYPROJECT
WORKDIR /usr/var/www/MYPROJECT
EXPOSE 8080
CMD ./mvnw spring-boot:run