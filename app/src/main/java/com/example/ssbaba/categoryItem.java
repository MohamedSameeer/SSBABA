package com.example.ssbaba;

public class categoryItem {
    String color;
    String description ;
    String image;
    String model;
    String name;
    String price;
    String year ;

    public String getImage() {
        return image;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
