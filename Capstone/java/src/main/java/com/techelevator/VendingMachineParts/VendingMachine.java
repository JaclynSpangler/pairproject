package com.techelevator.VendingMachineParts;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    //all functionality
    //constructor open inventory read lines in to create inventory item,
    // class that has different items that can put them into collections

    private List<Item> listOfItems = new ArrayList<>();


    public VendingMachine(){
        getItemsFromFile();
    }


    public void getItemsFromFile() {

        String fileName = "inventory.txt";
        Path inventoryFile = Path.of(fileName);

        try(Scanner readFile = new Scanner(inventoryFile)){
            while(readFile.hasNextLine()){

                Item itemPerLine = new Item();
                String inventoryLine = readFile.nextLine();
                String[] inventoryArray = inventoryLine.split("\\|");

                itemPerLine.setSlot(inventoryArray[0]);
                itemPerLine.setName(inventoryArray[1]);
                itemPerLine.setPrice(new BigDecimal(inventoryArray[2]));
                itemPerLine.setType(inventoryArray[3]);

                listOfItems.add(itemPerLine);

                //  this was a test to print
              //   System.out.println(item.getSlot() + " " + item.getName()
              //           + " $" + item.getPrice() + " " + item.getType());

            }

        } catch (IOException e) {
            System.out.println("Invalid source file");
        }


    }

    public String displayItems(){
        String display = "";
        for(Item item : listOfItems) {
            if (item.getNumberOfItemsInSlot() == 0) {//check once method for subtracting numberOfItems is made :)
                display += item.getName() + "  is SOLD OUT\n";
            } else {
                display += "Slot Key(" + item.getSlot() + ") " + item.getName()+ " $" + item.getPrice() + "\n";
            }
        }

        return display;
    }

    public String getTheItem(String slotKey){
        String result ="";
       // Item selectedItem = new Item();

        for(int i=0; i< listOfItems.size();i++){
            Item item = listOfItems.get(i);
            String slotKeyOfItem = item.getSlot();
            if(slotKey.equals(slotKeyOfItem)) {
                result = "HEYYYYYYYYYY";
                break;
            }
        }

        return result;
    }





}
