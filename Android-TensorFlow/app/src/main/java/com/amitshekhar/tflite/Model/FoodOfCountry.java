package com.amitshekhar.tflite.Model;

public class FoodOfCountry {
    private int Id;
    private String Image;
    private String Name;

    public FoodOfCountry() { }

    public FoodOfCountry(String name, String image) {
        Image = image;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
