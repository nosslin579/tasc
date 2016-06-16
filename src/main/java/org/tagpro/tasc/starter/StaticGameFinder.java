package org.tagpro.tasc.starter;

import java.net.URI;
import java.net.URISyntaxException;

public class StaticGameFinder implements GameFinder {

    private final URI uri;

    public StaticGameFinder(URI uri) {
        this.uri = uri;
    }

    @Override
    public URI findGameURI(URI serverUri, String tagProId) throws URISyntaxException, InterruptedException {
        return uri;
    }
}
