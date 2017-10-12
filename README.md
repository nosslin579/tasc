# Tasc

Tagpro Asynchronous Socket Client for Java

## What is Tasc?

Tasc is a connector for the super awesome game [TagPro](http://tagpro.koalabeast.com/) made in Java


## What can Tasc do?

* Retreive a TagPro ID neccessary for connecting to a game.
* Find a game using the joiner socket
* Start a game by uploading a map
* Connect to the game socket, send commands and retreive data
* Run the Box2D physics engine with TagPro settings

## What can Tasc be used for?

* A bot
* An Android app
* A standalone java client

## The todo list

* Add support for map tiles in TagproWorld
* Complete the Box2DClientSidePredictor
* Add a GUI
* Add a mock server for testing
* Add group socket game finder
* Upgrade to Tagpro Next
* Add documentation


## How do I run Tasc?

1. Clone repo
2. Run *mvn clean install*
3. Run *java -jar tasc-0.1-SNAPSHOT.jar*

This will run upload map test, connect, when game start right key pressed and finally exit when ball hits spike.

## How do I use Tasc?

Create a class that implements `GameSubscriber`:

```java
public class GoLeftBot implements GameSubscriber {
    private final Command command;

    public GoLeftBot(Command command) {
        this.command = command;
    }

    @Override
    public void time(int time, GameState state) {
        if (state == GameState.ACTIVE) {
          command.key(Key.LEFT, KeyAction.KEYDOWN);
        }
    }
}
```

Then start it:
```java
Starter s = new Starter("TheName");
s.addListener(new GoLeftBot(s.getCommand()));
s.addListener(new CommandFix(s.getCommand()));
s.start();
```

## How to enable JUtil logging?

```java
Logger rootLogger = Logger.getLogger("");
for (Handler handler : rootLogger.getHandlers()) {
    handler.setLevel(Level.FINE);
}
rootLogger.setLevel(Level.ALL);
```




