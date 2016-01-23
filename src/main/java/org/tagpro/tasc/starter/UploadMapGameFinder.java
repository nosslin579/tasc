package org.tagpro.tasc.starter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UploadMapGameFinder implements GameFinder {
    private final String mapName;

    public UploadMapGameFinder(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public URI findGameURI(URI serverUri, String tagProId) throws URISyntaxException, InterruptedException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(serverUri.toString()+"/testmap");

        String imageFileName = "src/main/resources/" + mapName + ".png";
        File imageFile = new File(imageFileName);

        String jsonFileName = "src/main/resources/" + mapName + ".json";
        File jsonFile = new File(jsonFileName);


        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("layout", imageFile, ContentType.DEFAULT_BINARY, imageFileName);
        builder.addBinaryBody("logic", jsonFile, ContentType.APPLICATION_OCTET_STREAM, jsonFileName);

        HttpEntity entity = builder.build();
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        String s = response.getFirstHeader("Location").getValue();
        return new URI(s);
    }
}
