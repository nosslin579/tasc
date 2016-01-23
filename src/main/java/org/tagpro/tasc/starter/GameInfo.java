package org.tagpro.tasc.starter;

import io.socket.client.Socket;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.GamePublisher;

import java.net.URI;

public class GameInfo {
    private final Socket socket;
    private final URI gameURI;
    private final GamePublisher publisher;
    private final Command command;

    public Command getCommand() {
        return command;
    }

    public GameInfo(Socket socket, URI gameURI, GamePublisher publisher, Command command) {
        this.socket = socket;
        this.gameURI = gameURI;
        this.publisher = publisher;
        this.command = command;

    }

    public void disconnect() {
        socket.disconnect();
    }

    public URI getGameURI() {
        return gameURI;
    }

    public GamePublisher getPublisher() {
        return publisher;
    }
}
