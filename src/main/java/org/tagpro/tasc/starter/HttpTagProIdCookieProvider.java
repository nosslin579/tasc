package org.tagpro.tasc.starter;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpTagProIdCookieProvider implements TagProIdCookieProvider {
    private static final Logger log = LoggerFactory.getLogger(Starter.class);

    @Override
    public String getTagProIdCookie(Starter starter) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet request = new HttpGet(starter.getServerUri());
            HttpResponse response = httpclient.execute(request);
            final Header setCookieHeaders = response.getFirstHeader("Set-Cookie");

            final List<HeaderElement> headerElements = Arrays.asList(setCookieHeaders.getElements());
            for (HeaderElement headerElement : headerElements) {
                if (headerElement.getName().equals("tagpro")) {
                    return headerElement.getValue();
                }
            }
        } catch (IOException e) {
            log.error("IO error", e);
        }
        throw new RuntimeException("No session could be found");
    }
}
