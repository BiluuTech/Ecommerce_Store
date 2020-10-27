package com.biluutech.ztshopping.Models;

public class AdminModel {

    private String pname, image;

    public AdminModel() {
    }

    public AdminModel(String pname, String image) {
        this.pname = pname;
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
