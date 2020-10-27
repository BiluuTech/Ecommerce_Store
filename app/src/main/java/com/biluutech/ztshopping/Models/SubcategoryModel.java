package com.biluutech.ztshopping.Models;

public class SubcategoryModel {

    private String scname, scimage, catname;

    public SubcategoryModel() {
    }

    public SubcategoryModel(String scname, String scimage, String catname) {
        this.scname = scname;
        this.scimage = scimage;
        this.catname = catname;
    }

    public String getScname() {
        return scname;
    }

    public void setScname(String scname) {
        this.scname = scname;
    }

    public String getScimage() {
        return scimage;
    }

    public void setScimage(String scimage) {
        this.scimage = scimage;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
}
