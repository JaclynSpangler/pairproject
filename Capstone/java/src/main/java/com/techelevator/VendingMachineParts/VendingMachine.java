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

    private Sales transactions = new Sales();

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
        String result ="nothing happened";
       // Item selectedItem = new Item();

        for(int i=0; i< listOfItems.size();i++){

            Item itemInLoop = listOfItems.get(i);
            String slotKeyOfItemInLoop = itemInLoop.getSlot();

            if(slotKey.equals(slotKeyOfItemInLoop)) {

                if(transactions.getCustomerBalance().equals(BigDecimal.ZERO) || transactions.getCustomerBalance().compareTo(itemInLoop.getPrice()) < 0) {
                    result = "Sorry, You do not have enough funds, please deposit more funds.";
                } else if( itemInLoop.getNumberOfItemsInSlot()==0 ){
                    result = "Item is SOLD OUT";
                } else {//maybe turn this into a method
                    itemInLoop.setNumberOfItemsInSlot(itemInLoop.getNumberOfItemsInSlot()-1);
                    transactions.setCustomerBalance( transactions.getCustomerBalance().subtract(itemInLoop.getPrice()) );
                    transactions.setVendingMachineBalance(transactions.getVendingMachineBalance().add(itemInLoop.getPrice()));
                    result = displayPhrase(itemInLoop.getType());
                }
                break;
                }
            result = "could not find item";
        }
       return result;
    }



    public String displayPhrase(String type){

        String phrase = "";
        if(type.equals("Chip")){
            phrase = "Crunch Crunch, Yum!";

        } else if(type.equals("Candy")){
            phrase = "Munch Munch, Yum!";

        } else if(type.equals("Drink")){
            phrase = "Glug Glug, Yum!";

        } else if (type.equals("Gum")){
            phrase = "Chew Chew, Yum!";

        }

        return phrase;
    }


    public String feedMoney(String amount){
        return transactions.feedMoney(amount);
    }
    public String displayBalance(){
        return transactions.displayBalance();
    }

    public String returnChange(){
        return transactions.returnChange();
    }



}
