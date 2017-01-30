package com.kokaba.rxfirebase.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;


public class FirebaseEvent<DataModel> {


    private @EventType int type;
    private DataModel value;

    public FirebaseEvent(@EventType int type, DataModel value) {
        this.type = type;
        this.value = value;
    }

    public @EventType int getType() {
        return type;
    }

    public DataModel getValue() {
        return value;
    }

    @Retention(SOURCE)
    @IntDef({ADDED, REMOVED, CHANGED})
    public @interface EventType {}
    public static final int ADDED = 0;
    public static final int REMOVED = 1;
    public static final int CHANGED = 2;


}
