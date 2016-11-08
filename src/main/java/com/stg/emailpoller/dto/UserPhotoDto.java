package com.stg.emailpoller.dto;

import com.stg.emailpoller.model.Photo;
import com.stg.emailpoller.model.User;

/**
 * User and Photo DTO (Data Transfer Object)
 *
 * Created by dqromney on 11/5/16.
 */
public class UserPhotoDto {
    private User user;
    private Photo photo;
//    private String userId; // email address
//    private String userName;
//    private String subject;
//    private String text;
//    private String imageUrl;
//    private Date sentDate;

    public UserPhotoDto() {
    }

    public UserPhotoDto(User user, Photo photo) {
        this.user = user;
        this.photo = photo;
    }

//    public UserPhotoDto(String userId, String userName, String subject, String text, String imageUrl, Date sentDate) {
//        this.userId = userId;
//        this.userName = userName;
//        this.subject = subject;
//        this.text = text;
//        this.imageUrl = imageUrl;
//        this.sentDate = sentDate;
//    }

    // ----------------------------------------------------------------
    // Access methods
    // ----------------------------------------------------------------
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public Date getSentDate() {
//        return sentDate;
//    }
//
//    public void setSentDate(Date sentDate) {
//        this.sentDate = sentDate;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserPhotoDto{");
        sb.append("user=").append(user);
        sb.append(", photo=").append(photo);
        sb.append('}');
        return sb.toString();
    }
}
