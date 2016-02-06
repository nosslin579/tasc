package org.tagpro.tasc.extras;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.RecordListener;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;

public class Recorder implements GameSubscriber {
    private final Logger recordLogger = LoggerFactory.getLogger(RecordListener.class);

    @Override
    public void keyPressed(Key key, KeyAction keyAction, int count) {
        if (recordLogger.isDebugEnabled()) {
            String sep = RecordListener.SEPARATOR2;
            recordLogger.debug("o" + sep + System.nanoTime() + sep + key.getCommand() + sep + key + sep + keyAction);
        }
    }
}
