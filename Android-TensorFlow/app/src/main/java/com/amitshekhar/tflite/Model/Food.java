package com.amitshekhar.tflite.Model;

public class Food {
    private String CountryID;
    private String Description;
    private String Image;
    private String InstructionsMakeLink;
    private String Name;

    public Food() {
    }
    public Food(String countryID, String description, String image, String instructionsMakeLink, String name, String title) {
        CountryID = countryID;
        Description = description;
        Image = image;
        InstructionsMakeLink = instructionsMakeLink;
        Name = name;
    }

    public String getCountryID() {
        return CountryID;
    }

    public void setCountryID(String countryID) {
        CountryID = countryID;
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

    public String getInstructionsMakeLink() {
        return InstructionsMakeLink;
    }

    public void setInstructionsMakeLink(String instructionsMakeLink) {
        InstructionsMakeLink = instructionsMakeLink;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
