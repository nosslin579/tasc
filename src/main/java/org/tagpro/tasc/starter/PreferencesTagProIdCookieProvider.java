package org.tagpro.tasc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.prefs.Preferences;

public class PreferencesTagProIdCookieProvider implements TagProIdCookieProvider {
    private static final Logger log = LoggerFactory.getLogger(Starter.class);
    private static final String NODE_NAME = "/org/tagpro/tasc";

    private Properties ids = new Properties();
    private HttpTagProIdCookieProvider httpTagProIdCookieProvider = new HttpTagProIdCookieProvider();

    @Override
    public String getTagProIdCookie(Starter starter) {
        Preferences prefs = Preferences.userRoot().node(NODE_NAME);
        String name = starter.getName();
        String ret = prefs.get(name, "");
        if (ret.isEmpty()) {
            log.warn("No TagPro Id found for:" + name);
            String newId = httpTagProIdCookieProvider.getTagProIdCookie(starter);
            prefs.put(name, newId);
            return newId;
        }

        return ret;
    }
}
