package com.example.hp.travelitinerary;


public class Item {
    private int id;
    private int day;
    private String itemName;
    private String itemDescription;
    private byte[] image;

    public Item(int id, int day, String itemName, String itemDescription, byte[] image) {
        this.id = id;

        this.day = day;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getItemName() {
        return itemName;

    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
