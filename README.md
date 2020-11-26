# Minesweeper API
API implementation of a minesweeper.

The API has been written using JAVA as a main language and MongoDB for storage.

You can clone the project and run it locally or simply play it on [HERE](https://nameless-ocean-97360.herokuapp.com/minesweeper/swagger-ui.html)


## Stack
We have used this stack:

* JAVA 11
* Spring-boot 2.4.0
* MongoDB
* Docker
* nodejs (for the API client)


## Run it locally
You need to have [MAVEN](http://maven.apache.org/download.cgi) installed in your system

Clone it:
> git clone https://github.com/fdelbo/minesweeper-api.git

Build it:
> mvn clean package

> docker build -t minesweeper:v1 .

Run it:
> docker run --env PORT=8080 --env MONGO_USER=${DB_USER} --env MONGO_PASSWORD=${DB_PASSWORD} 
--env MONGO_CLUSTER_URL=${MONGO_CLUSTER} --env DB_NAME=${DB_NAME} -p 8080:8080 minesweeper:v1

Note: replace the placeholder with your mongo-db instance parameters.

## Play it online
You can play the game using the online API through this
[URL](https://nameless-ocean-97360.herokuapp.com/minesweeper)

## API Documentation
This API allows you to:
* Create a user
* Get the games related to a user
* Create a game
* Make an action in a game (FLIP, FLAG, MARK, REMOVE FLAG/MARK) a cell given its coordinates
* Change game status (PLAYING/PAUSE)

You can access the online documentation through this link
[API documentation](https://nameless-ocean-97360.herokuapp.com/minesweeper/swagger-ui.html)

## Integrations
You can find a rest client written in nodejs to easily integrate your application.

The client is in '/client' folder within the project.