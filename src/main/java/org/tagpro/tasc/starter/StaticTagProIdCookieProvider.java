package org.tagpro.tasc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticTagProIdCookieProvider implements TagProIdCookieProvider {
    private static final Logger log = LoggerFactory.getLogger(Starter.class);
    private final String tagProId;

    public StaticTagProIdCookieProvider(String tagProId) {
        this.tagProId = tagProId;
    }

    @Override
    public String getTagProIdCookie(Starter starter) {
        return tagProId;
    }
}
