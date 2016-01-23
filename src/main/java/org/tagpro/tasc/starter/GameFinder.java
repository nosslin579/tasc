package org.tagpro.tasc.starter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public interface GameFinder {
    URI findGameURI(URI serverUri, String tagProId) throws URISyntaxException, InterruptedException, IOException;
}
