# Tasc

Tagpro Asynchronous Socket Client for Java

##What is Tasc?

Tasc is a connector for the super awesome game [Tagpro](http://tagpro.koalabeast.com/)


##What can Tasc do?

* Retreive a Tagpro ID neccessary for connecting to a game.
* Find a game using the joiner socket
* Start a game by uploading a map
* Connect to the game socket, send commands and retreive data
* Run the Box2D physics engine with Tagpro settings

##What can Tasc be used for?

* A bot
* An Android app
* A standalone java client

##The todo list

* Add support for map tiles in TagproWorld
* Complete the Box2DClientSidePredictor
* Add a GUI
* Add a mock server for testing


##How do I run Tasc?

1. Clone repo
2. Run *mvn clean install*
3. Run *java -jar tasc-0.1-SNAPSHOT.jar*

This will run upload map test, connect, when game start right key pressed and finally exit when ball hits spike.

##How do I use Tasc?

Implement a class with `GameSubscriber`, start it 
```java
Starter s = new Starter();
s.addListener(new GameSubscriberImpl());
s.start();
```
