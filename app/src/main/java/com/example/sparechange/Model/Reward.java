package com.example.sparechange.Model;

public class Reward {

    private int id, price;
    private String title, company;
    private int image;

    public Reward(int id, String title, String company, int price, int image) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
