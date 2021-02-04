package com.techelevator.VendingMachineParts;

import java.math.BigDecimal;

public class Item {

    private static final int DEFAULT_NUMBER = 5;
    private String slot;
    private String name;
    private BigDecimal price;
    private String type;
    private int numberOfItemsInSlot = DEFAULT_NUMBER;

    //Slot getter and setter
    public String getSlot() {
        return slot;
    }
    public void setSlot(String slot) {
        this.slot = slot;
    }

    //name getter and setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //price getter and setter
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //type setter and getter
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //Number of Items in Slot setter and getter
    public int getNumberOfItemsInSlot() {
        return numberOfItemsInSlot;
    }
    public void setNumberOfItemsInSlot(int numberOfItemsInSlot) {
        this.numberOfItemsInSlot = numberOfItemsInSlot;
    }


}
