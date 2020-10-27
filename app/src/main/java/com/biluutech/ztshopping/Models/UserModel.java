package com.biluutech.ztshopping.Models;

public class UserModel {

    private String name, phone, pid, subcategory;

    private Long totalBill;

    public UserModel() {
    }

    public UserModel(String name, String phone, String pid, String subcategory, Long totalBill) {
        this.name = name;
        this.phone = phone;
        this.pid = pid;
        this.subcategory = subcategory;
        this.totalBill = totalBill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Long getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Long totalBill) {
        this.totalBill = totalBill;
    }
}
