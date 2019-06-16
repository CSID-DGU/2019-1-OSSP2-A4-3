package com.amitshekhar.tflite.Model;

public class ImageUser {
    String linkImage;
    String nameFood;
    public ImageUser(String linkImage, String nameFood) {
        this.linkImage = linkImage;
        this.nameFood = nameFood;
    }

    public ImageUser() {
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
