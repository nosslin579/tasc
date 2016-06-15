package org.tagpro.tasc;

import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyAction;

public interface KeyState {

    boolean isPushed(Key key);

    KeyAction getStateFor(Key key);

    float getHorizontalAcceleration(float surface);

    float getVerticalAcceleration(float surface);
}
