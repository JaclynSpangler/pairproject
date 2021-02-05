package com.techelevator;

import com.techelevator.VendingMachineParts.Sales;
import com.techelevator.VendingMachineParts.VendingMachine;
import com.techelevator.view.MenuDrivenCLI;

public class Application {


	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};

	private static final String SUBMENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String SUBMENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String SUBMENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] SUBMENU_OPTIONS_PURCHASE = {SUBMENU_OPTION_FEED_MONEY, SUBMENU_OPTION_SELECT_PRODUCT, SUBMENU_OPTION_FINISH_TRANSACTION};


	private VendingMachine vendingMachine = new VendingMachine();
	private final MenuDrivenCLI ui = new MenuDrivenCLI();
	private Sales sales = new Sales();

	public static void main(String[] args) {
		Application application = new Application();
		application.run();
	}

	//writes out the log entry
	public void run() {
		boolean running = true;

		while (running) {
			String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);

			if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayInventory();
			} else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
				runSubmenu();
			} else if (selection.equals(MAIN_MENU_OPTION_EXIT)) {
				running = false;
			} else if (selection.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				///sales report :)
			}
		}


	}

	private void displayInventory(){
		ui.output("The following inventory for this Vending Machine is: ");
		ui.output(vendingMachine.displayItems());
	}

	private void runSubmenu(){
		boolean inSubmenu = true;


		while(inSubmenu) {
			String selection = ui.promptForSelection(SUBMENU_OPTIONS_PURCHASE);
			if (selection.equals(SUBMENU_OPTION_FEED_MONEY)) {
				String input = ui.promptForString("Please deposit WHOLE DOLLAR amount (do not include any decimals): ");
				ui.output(sales.feedMoney(input));
				ui.output(sales.displayBalance());
				//	} else if (selection.equals(SUBMENU_OPTION_SELECT_PRODUCT)){
				//		selectProduct(); //remove 1 from item.numberInSlot && subtract price from change balance
				//&& if $0/0number in slot/costs more than they deposited then error message and send back to submenu
				//return back to purchase menu
			} else if (selection.equals(SUBMENU_OPTION_FINISH_TRANSACTION)) {
				//		finishTransaction();//return change balance && return to main menu
				inSubmenu = false;
			}
		}
	}



}
