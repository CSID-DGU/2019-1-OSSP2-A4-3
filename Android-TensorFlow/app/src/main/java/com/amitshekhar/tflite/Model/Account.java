package com.amitshekhar.tflite.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    String name;
    String email;
    String photo;
    ArrayList<Food> listSavePhoto;

    public Account() {
    }

    public Account(String name, String email, String photo, ArrayList<Food> listSavePhoto) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.listSavePhoto = listSavePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<Food> getListSavePhoto() {
        return listSavePhoto;
    }

    public void setListSavePhoto(ArrayList<Food> listSavePhoto) {
        this.listSavePhoto = listSavePhoto;
    }
}
