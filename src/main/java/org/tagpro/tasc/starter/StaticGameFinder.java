package org.tagpro.tasc.starter;

import java.net.URI;
import java.net.URISyntaxException;

public class StaticGameFinder implements GameFinder {
    private final String port;

    public StaticGameFinder(String port) {
        this.port = port;
    }

    @Override
    public URI findGameURI(URI serverUri, String tagProId) throws URISyntaxException, InterruptedException {
        return new URI(serverUri.toString() + ":" + port);
    }
}
