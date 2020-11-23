FROM amazoncorretto:11.0.6
COPY target/minesweeper*.jar /build/app.jar
WORKDIR /build
EXPOSE ${PORT}

CMD [ "java" , "-jar" , "app.jar" ]