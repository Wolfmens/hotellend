FROM openjdk:21-oracle

WORKDIR /app

COPY build/libs/hotelland-0.0.1-SNAPSHOT.jar hotelland.jar

CMD ["java","-jar","hotelland.jar"]