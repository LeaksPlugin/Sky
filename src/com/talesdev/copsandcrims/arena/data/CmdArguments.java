package com.talesdev.copsandcrims.arena.data;

/**
 * Cmd Arguments
 *
 * @author MoKunz
 */
public class CmdArguments {
    private Object value;

    public CmdArguments(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        return (T) value;
    }

    public Object get() {
        return value;
    }
}
