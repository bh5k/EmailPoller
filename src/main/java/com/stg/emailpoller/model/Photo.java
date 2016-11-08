package com.stg.emailpoller.model;

import java.util.Date;

/**
 * Photo model.
 *
 * Created by dqromney on 11/7/16.
 */
public class Photo {
    private String subject;
    private String text;
    private String imageUrl;
    private Date sentDate;

    public Photo(String subject, String text, String imageUrl, Date sentDate) {
        this.subject = subject;
        this.text = text;
        this.imageUrl = imageUrl;
        this.sentDate = sentDate;
    }

    // ----------------------------------------------------------------
    // Accessor methods
    // ----------------------------------------------------------------
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Photo{");
        sb.append("subject='").append(subject).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", sentDate=").append(sentDate);
        sb.append('}');
        return sb.toString();
    }
}
