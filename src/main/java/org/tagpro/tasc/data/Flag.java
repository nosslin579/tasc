package org.tagpro.tasc.data;

import java.util.Objects;

public enum Flag {
    NONE(null), RED(1), BLUE(2), YELLOW(3);

    private final Object id;

    Flag(Object id) {
        this.id = id;
    }

    public static Flag resolve(Object id) {
        for (Flag flag : values()) {
            if (Objects.equals(id, flag.id)) {
                return flag;
            }
        }
        throw new IllegalArgumentException("No such flag:" + id + " class:" + (id == null ? "" : id.getClass()));
    }
}
