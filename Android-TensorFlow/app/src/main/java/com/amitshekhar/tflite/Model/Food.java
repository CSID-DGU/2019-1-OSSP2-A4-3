package com.amitshekhar.tflite.Model;

import java.io.Serializable;

public class Food  implements Serializable {
    private String Country;
    private String Description;
    private String Image;
    private String YoutubeLink;
    private String Name;
    public Food() {
    }
    public Food(String country, String description, String image, String youtubelink, String name, String title) {
        Country = country;
        Description = description;
        Image = image;
        YoutubeLink = youtubelink;
        Name = name;
    }
    public String getCountry() {
        return Country;
    }
    public void setCountry(String country) {
        Country = country;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getYoutubeLink() {
        return YoutubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        YoutubeLink = youtubeLink;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
