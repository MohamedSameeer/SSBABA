package com.example.ssbaba;

import java.util.List;

public class CategoryItem {

    String imageUrl;
    String text;
    List<AnyItemInFirebase> list;


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public List<AnyItemInFirebase> getList() {
        return list;
    }
}
