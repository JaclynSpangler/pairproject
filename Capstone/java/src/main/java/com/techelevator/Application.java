package com.techelevator;

import com.techelevator.VendingMachineParts.Sales;
import com.techelevator.VendingMachineParts.VendingMachine;
import com.techelevator.util.VMLog;
import com.techelevator.view.Menu;
import com.techelevator.view.MenuDrivenCLI;

public class Application {


	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = Menu.HIDDEN_OPTION;
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};

	private static final String SUBMENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String SUBMENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String SUBMENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] SUBMENU_OPTIONS_PURCHASE = {SUBMENU_OPTION_FEED_MONEY, SUBMENU_OPTION_SELECT_PRODUCT, SUBMENU_OPTION_FINISH_TRANSACTION};


	private VendingMachine vendingMachine = new VendingMachine();
	private final MenuDrivenCLI ui = new MenuDrivenCLI();

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
				ui.output(vendingMachine.displaySales());
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
				ui.output(vendingMachine.feedMoney(input));
				ui.output(vendingMachine.displayBalance());
					} else if (selection.equals(SUBMENU_OPTION_SELECT_PRODUCT)){
				displayInventory();
				String slotKey = ui.promptForString("Please make your selection (using the slot key): ");
				ui.output(vendingMachine.getTheItem(slotKey));
				ui.output(vendingMachine.displayBalance());

			} else if (selection.equals(SUBMENU_OPTION_FINISH_TRANSACTION)) {
				ui.output(vendingMachine.returnChange());
				inSubmenu = false;
			}
		}
	}



}
