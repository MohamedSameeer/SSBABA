package com.example.ssbaba;

public class ProfileOptionsModel {
    private String option;
    private int icon;
    public ProfileOptionsModel(String option,int icon){
        this.option=option;
        this.icon=icon;

    }
    public int getIcon() {
        return icon;
    }

    public String getOption() {
        return option;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
