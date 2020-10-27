package com.biluutech.ztshopping.Models;

public class ProductModelClass {

    private String pname, price, description, image, pid, subcategory;

    public ProductModelClass() {
    }

    public ProductModelClass(String pname, String price, String description, String image, String pid, String subcategory) {
        this.pname = pname;
        this.price = price;
        this.description = description;
        this.image = image;
        this.pid = pid;
        this.subcategory = subcategory;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
