package com.lymytz.android.component;

import androidx.core.app.NotificationCompat;

public class MyBuilder {
    private int id;
    private NotificationCompat.Builder builder;

    public MyBuilder(int id, NotificationCompat.Builder builder) {
        this.id = id;
        this.builder = builder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NotificationCompat.Builder getBuilder() {
        return builder;
    }

    public void setBuilder(NotificationCompat.Builder builder) {
        this.builder = builder;
    }
}
