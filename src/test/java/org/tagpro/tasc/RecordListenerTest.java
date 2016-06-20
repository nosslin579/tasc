package org.tagpro.tasc;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class RecordListenerTest {

    @Test
    public void test() throws IOException {
        new RecordListener("connect").call("");
        String mapArg = "[1,åäö,1,1,1]";
        new RecordListener("map").call(mapArg);
        List<String> serverEvents = Files.readAllLines(new File("recorded-session.log").toPath());
        Assert.assertEquals(serverEvents.size(), 2);
        Assert.assertEquals(serverEvents.get(1).split(RecordListener.SEPARATOR)[3], mapArg);
    }
}