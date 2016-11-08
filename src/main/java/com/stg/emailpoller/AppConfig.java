package com.stg.emailpoller;

/**
 * Application Configuration.
 *
 * Created by dqromney on 11/5/16.
 */
public enum AppConfig {

    GMAIL_IMAP_SERVICE_URL("imap.gmail.com"),
    TEMP_IMAGE_URL("http://images.guff.com/gallery/image/dubai-176127");

    private String value;

    AppConfig(String pValue) {
        this.value = pValue;
    }

    public String getValue() {
        return value;
    }
}
