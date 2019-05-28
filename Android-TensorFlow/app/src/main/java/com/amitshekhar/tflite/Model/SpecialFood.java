package com.amitshekhar.tflite.Model;

public class SpecialFood {
    private int CountryId;
    private String Image;
    private String Name;

    public SpecialFood() {
    }

    public SpecialFood(int countryId, String image, String name) {
        CountryId = countryId;
        Image = image;
        Name = name;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
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
