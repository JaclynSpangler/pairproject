package com.techelevator;

import com.techelevator.view.MenuDrivenCLI;

public class Application {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};

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
				//get data from inventory.txt and convert it to string
				// display vending machine items
			} else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
			} else if (selection.equals(MAIN_MENU_OPTION_EXIT)) {
				running = false;
			} else if (selection.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				///sales report :)
			}
		}


	}

	private void displayInventory(){
		ui.output("The following inventory for this Vending Machine is: ");
		ui.output()

	}
}
