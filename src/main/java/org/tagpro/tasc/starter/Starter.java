package org.tagpro.tasc.starter;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.*;
import org.tagpro.tasc.listeners.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Starter {
    private static final Logger log = LoggerFactory.getLogger(Starter.class);
    private final String name;


    private URI serverUri = createURI("http://maptest.newcompte.fr");
    private GameFinder gameFinder = new UploadMapGameFinder("test");
    private List<GameSubscriber> subscribers = new ArrayList<>();
    private TagProIdCookieProvider tagProIdCookieProvider = new PreferencesTagProIdCookieProvider();
    private ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory("PublisherInternal"));
    private Command command = new Command();
    private boolean recordEvents = false;

    public Starter(String name) {
        this.name = name;
    }

    private URI createURI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Create uri failed", e);
        }
    }

    public void setRecordEvents(boolean recordEvents) {
        this.recordEvents = recordEvents;
    }

    public void setTagProIdCookieProvider(TagProIdCookieProvider tagProIdCookieProvider) {
        this.tagProIdCookieProvider = tagProIdCookieProvider;
    }

    public void setServerUri(URI serverUri) {
        this.serverUri = serverUri;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public URI getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = createURI(serverUri);
    }

    public GameFinder getGameFinder() {
        return gameFinder;
    }

    public void setGameFinder(GameFinder gameFinder) {
        this.gameFinder = gameFinder;
    }

    public void addListener(GameSubscriber gameSubscriber) {
        if (subscribers.contains(gameSubscriber)) {
            throw new IllegalArgumentException("Can not add subscriber again. " + gameSubscriber);
        }
        subscribers.add(gameSubscriber);
    }

    public GameInfo start() throws IOException, URISyntaxException, InterruptedException {
        String tagProId = tagProIdCookieProvider.getTagProIdCookie(this);
        log.info("Got tagpro id:" + tagProId);
        URI gameURI = gameFinder.findGameURI(serverUri, tagProId);
        log.info("Found a game:" + gameURI);
        Socket socket = createGameSocket(gameURI, tagProId);
        command.setSocket(socket);


        GamePublisher publisher = new GamePublisher(executor);
        subscribers.add(new ExceptionHandler());
        subscribers.add(new PingService(socket));

        socket.on("connect", new ConnectListener(publisher));
        socket.on("disconnect", new ConnectListener(publisher));
        socket.on("chat", new ChatListener(publisher));
        socket.on("map", new MapListener(publisher));
        socket.on("mapupdate", new MapUpdateListener(publisher));
        socket.on("spawn", new SpawnListener(publisher));
        socket.on("p", new PlayerListener(publisher));
        socket.on("banned", new BannedListener(publisher));
        socket.on("end", new EndListener(publisher));
        socket.on("full", new FullListener(publisher));
        socket.on("id", new IdListener(publisher));
        socket.on("score", new ScoreListener(publisher));
        socket.on("time", new TimeListener(publisher));


        if (log.isDebugEnabled()) {
            String[] logListenerEvents = new String[]{"duplicate", "groupId", "joinWorld", "mapRating", "object", "playerLeft", "preferredServer", "private", "selfDestructSoon", "settings", "sound", "stopSound", "spectator", "spectators", "splat", "teamNames"};
            for (String logListenerEvent : logListenerEvents) {
                socket.on(logListenerEvent, new LogEmitterListener(logListenerEvent));
                socket.on(logListenerEvent, new RecordListener(logListenerEvent));
            }
        }
        if (recordEvents) {
            List<String> recordEvents = Arrays.asList("connect", "chat", "map", "mapupdate", "spawn", "p", "banned", "end", "full", "id", "score", "time");
            for (String recordEvent : recordEvents) {
                socket.on(recordEvent, new RecordListener(recordEvent));
            }
        }

        publisher.setSubscribers(subscribers);
        publisher.preConnect();
        socket.connect();
        log.info("Starting bot:" + name);
        return new GameInfo(socket, gameURI, publisher, command);
    }

    private static void addStandardEvents(Socket socket) {
        String[] logListenerEvents = new String[]{Socket.EVENT_CONNECT, Socket.EVENT_MESSAGE, Socket.EVENT_CONNECT_ERROR, Socket.EVENT_CONNECT_TIMEOUT, Socket.EVENT_DISCONNECT, Socket.EVENT_RECONNECT, Socket.EVENT_ERROR, Socket.EVENT_RECONNECT_ATTEMPT, Socket.EVENT_RECONNECT_ERROR, Socket.EVENT_RECONNECT_FAILED};
        for (String logListenerEvent : logListenerEvents) {
            socket.on(logListenerEvent, new LogEmitterListener(logListenerEvent));
        }
    }

    public Socket createGameSocket(URI gameUri, String tagProId) throws URISyntaxException {
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = false;
        //without r parameter ball is placed outside map, probably be a caching problem
        final Socket socket = IO.socket(gameUri + "?r=" + System.nanoTime(), opts);
        socket.io().on(Manager.EVENT_TRANSPORT, new SetCookieEmitterListener(tagProId));
        addStandardEvents(socket);
        return socket;
    }

    public Command getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }
}
