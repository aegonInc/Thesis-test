package com.example.thesis.Model;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String _username, String _email) {
        username = _username;
        email = _email;
    }


    public String getUser() {
        return username;
    }
    public void setUser(String _user) {
        username = _user;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String _email) {
        email = _email;
    }

}
// [END blog_user_class]
