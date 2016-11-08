package com.stg.emailpoller.model;

/**
 * User model.
 *
 * Created by dqromney on 11/7/16.
 */
public class User {
    private Long id;
    private String email;
    private String name;

    public User() {
        // Empty
    }

    public User(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    // ----------------------------------------------------------------
    // Accessor methods
    // ----------------------------------------------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
