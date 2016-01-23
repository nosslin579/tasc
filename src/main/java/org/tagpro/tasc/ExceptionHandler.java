package org.tagpro.tasc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ExceptionHandler implements GameSubscriber, Thread.UncaughtExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onException(Exception e, Object[] args) {
        log.error("Exception with args:" + Arrays.toString(args), e);
        System.exit(1);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in " + t.getName(), e);
        t.interrupt();
//        System.exit(1);
    }
}
