package com.example.ssbaba;

public class Item {
    int image;
    String text;

    public Item(int image,String text){

        this.image=image;
        this.text=text;

    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }
}
