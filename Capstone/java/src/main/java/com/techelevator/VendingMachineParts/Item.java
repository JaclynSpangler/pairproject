package com.techelevator.VendingMachineParts;

import java.math.BigDecimal;

public class Item {

    private static final int DEFAULT_NUMBER = 5;
    private String slot;
    private String name;
    private BigDecimal price;
    private String type;
    private int numberOfItemsInSlot = DEFAULT_NUMBER;

    public BigDecimal getItemSales(){
       int numberOfItemsSold = (DEFAULT_NUMBER - getNumberOfItemsInSlot());
        BigDecimal itemSales = BigDecimal.valueOf(numberOfItemsSold).multiply(getPrice());
        return itemSales;
    }

    public String getSlot() {
        return slot;
    }
    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfItemsInSlot() {
        return numberOfItemsInSlot;
    }
    public void setNumberOfItemsInSlot(int numberOfItemsInSlot) {
        this.numberOfItemsInSlot = numberOfItemsInSlot;
    }

}
