package org.tagpro.tasc;

import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyState;

public interface PressedKeys {

    boolean isPushed(Key key);

    KeyState getStateFor(Key key);

    float getHorizontalAcceleration(float surface);

    float getVerticalAcceleration(float surface);
}
