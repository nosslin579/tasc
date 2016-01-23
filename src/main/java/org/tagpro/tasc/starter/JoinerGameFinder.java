package org.tagpro.tasc.starter;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import org.tagpro.tasc.listeners.SetCookieEmitterListener;

import java.net.URI;
import java.net.URISyntaxException;

public class JoinerGameFinder implements GameFinder {
    @Override
    public URI findGameURI(URI serverUri, String tagProId) throws URISyntaxException, InterruptedException {
            Socket joinerSocket = createJoinerSocket(serverUri, tagProId);
            final Joiner ret = new Joiner(joinerSocket);
            ret.start();
            String ret2 = ret.getFoundWorldUrl();
            ret.stop();
            return new URI(ret2);
    }

    public static Socket createJoinerSocket(final URI baseUri, String tagProId) throws URISyntaxException {
        URI joinUri = new URI(baseUri.toString() + ":81/games/find");
        final Socket socket = IO.socket(joinUri);
        socket.io().on(Manager.EVENT_TRANSPORT, new SetCookieEmitterListener(tagProId));
        return socket;
    }
}
