package org.tagpro.tasc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class StaticTagProIdCookieCreator implements TagProIdCookieCreator {
    private static final Logger log = LoggerFactory.getLogger(Starter.class);
    private final String tagProId;

    public StaticTagProIdCookieCreator(String tagProId) {
        this.tagProId = tagProId;
    }

    @Override
    public String getTagProIdCookie(URI server) {
        return tagProId;
    }
}
