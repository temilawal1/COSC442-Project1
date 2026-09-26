package edu.towson.cis.cosc442.project1.monopoly;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Player {
	//the key of colorGroups is the name of the color group.
	private Hashtable<String, Integer> colorGroups = new Hashtable<String, Integer>();
	private boolean inJail;
	private int money;
	private String name;

	private Cell position;
	private ArrayList<PropertyCell> properties = new ArrayList<PropertyCell>();
	private ArrayList<Cell> railroads = new ArrayList<Cell>();
	private ArrayList<Cell> utilities = new ArrayList<Cell>();
	
	/**
	 * Initializes a new Player object positioned at 'Go' with default values.
	 */
	/**
	 * Initializes a new Player object positioned at 'Go' with default values.
	 */
	public Player() {
		GameBoard gb = GameMaster.instance().getGameBoard();
		inJail = false;
		if(gb != null) {
			position = gb.queryCell("Go");
		}
	}

    /**
     * Assigns ownership of a property to the player and deducts the purchase amount from the player's money.
     * @param property The property Cell to be bought.
     * @param amount The price paid for the property.
     */
    /**
     * Assigns ownership of a property to the player and deducts the purchase amount from the player's money.
     * @param property The property Cell to be bought.
     * @param amount The price paid for the property.
     */
    public void buyProperty(Cell property, int amount) {
        property.setTheOwner(this);
        if(property instanceof PropertyCell) {
            PropertyCell cell = (PropertyCell)property;
            properties.add(cell);
            colorGroups.put(
                    cell.getColorGroup(), 
                    new Integer(getPropertyNumberForColor(cell.getColorGroup())+1));
        }
        if(property instanceof RailRoadCell) {
            railroads.add(property);
            colorGroups.put(
                    RailRoadCell.COLOR_GROUP, 
                    new Integer(getPropertyNumberForColor(RailRoadCell.COLOR_GROUP)+1));
        }
        if(property instanceof UtilityCell) {
            utilities.add(property);
            colorGroups.put(
                    UtilityCell.COLOR_GROUP, 
                    new Integer(getPropertyNumberForColor(UtilityCell.COLOR_GROUP)+1));
        }
        setMoney(getMoney() - amount);
    }
	
	/**
	 * Determines if the player currently holds any monopolies and thus can buy houses.
	 * @return true if the player has at least one monopoly, false otherwise.
	 */
	/**
	 * Determines if the player currently holds any monopolies and thus can buy houses.
	 * @return true if the player has at least one monopoly, false otherwise.
	 */
	public boolean canBuyHouse() {
		return (getMonopolies().length != 0);
	}

	/**
	 * Checks if the player owns a property with the specified name.
	 * @param property The property name to check ownership for.
	 * @return true if the player owns the property, false otherwise.
	 */
	/**
	 * Checks if the player owns a property with the specified name.
	 * @param property The property name to check ownership for.
	 * @return true if the player owns the property, false otherwise.
	 */
	public boolean checkProperty(String property) {
		for(int i=0;i<properties.size();i++) {
			Cell cell = (Cell)properties.get(i);
			if(cell.getName().equals(property)) {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Transfers all owned properties from this player to another player or releases them if null is passed.
	 * @param player The Player to receive the properties or null to release ownership.
	 */
	/**
	 * Transfers all owned properties from this player to another player or releases them if null is passed.
	 * @param player The Player to receive the properties or null to release ownership.
	 */
	public void exchangeProperty(Player player) {
		for(int i = 0; i < getPropertyNumber(); i++ ) {
			PropertyCell cell = getProperty(i);
			cell.setTheOwner(player);
			if(player == null) {
				cell.setAvailable(true);
				cell.setNumHouses(0);
			}
			else {
				player.properties.add(cell);
				colorGroups.put(
						cell.getColorGroup(), 
						new Integer(getPropertyNumberForColor(cell.getColorGroup())+1));
			}
		}
		properties.clear();
	}
    
    /**
     * Returns an array of all properties, utilities, and railroads owned by the player.
     * @return An array of Cells representing all owned properties.
     */
    /**
     * Returns an array of all properties, utilities, and railroads owned by the player.
     * @return An array of Cells representing all owned properties.
     */
    public Cell[] getAllProperties() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        list.addAll(properties);
        list.addAll(utilities);
        list.addAll(railroads);
        return (Cell[])list.toArray(new Cell[list.size()]);
    }

	/**
	 * Returns the current amount of money the player has.
	 * @return The player's current money balance.
	 */
	/**
	 * Returns the current amount of money the player has.
	 * @return The player's current money balance.
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Retrieves an array of color group names where the player has a monopoly.
	 * @return An array of Strings naming each monopoly the player owns.
	 */
	/**
	 * Retrieves an array of color group names where the player has a monopoly.
	 * @return An array of Strings naming each monopoly the player owns.
	 */
	public String[] getMonopolies() {
		ArrayList<String> monopolies = new ArrayList<String>();
		Enumeration<String> colors = colorGroups.keys();
		while(colors.hasMoreElements()) {
			String color = (String)colors.nextElement();
            if(!(color.equals(RailRoadCell.COLOR_GROUP)) && !(color.equals(UtilityCell.COLOR_GROUP))) {
    			Integer num = (Integer)colorGroups.get(color);
    			GameBoard gameBoard = GameMaster.instance().getGameBoard();
    			if(num.intValue() == gameBoard.getPropertyNumberForColor(color)) {
    				monopolies.add(color);
    			}
            }
		}
		return (String[])monopolies.toArray(new String[monopolies.size()]);
	}

	/**
	 * Returns the player's name.
	 * @return The name of the player.
	 */
	/**
	 * Returns the player's name.
	 * @return The name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Processes the player's payment of bail and releases them from jail, handling bankruptcy if necessary.
	 */
	/**
	 * Processes the player's payment of bail and releases them from jail, handling bankruptcy if necessary.
	 */
	public void getOutOfJail() {
		money -= JailCell.BAIL;
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(null);
		}
		inJail = false;
		GameMaster.instance().updateGUI();
	}

	/**
	 * Returns the current board cell where the player is positioned.
	 * @return The Cell representing the player's current position.
	 */
	/**
	 * Returns the current board cell where the player is positioned.
	 * @return The Cell representing the player's current position.
	 */
	public Cell getPosition() {
		return this.position;
	}
	
	/**
	 * Retrieves the property owned by the player at the specified index.
	 * @param index The index of the property to retrieve.
	 * @return The PropertyCell at the given index.
	 */
	/**
	 * Retrieves the property owned by the player at the specified index.
	 * @param index The index of the property to retrieve.
	 * @return The PropertyCell at the given index.
	 */
	public PropertyCell getProperty(int index) {
		return (PropertyCell)properties.get(index);
	}
	
	/**
	 * Returns the number of properties owned by the player.
	 * @return The count of properties owned.
	 */
	/**
	 * Returns the number of properties owned by the player.
	 * @return The count of properties owned.
	 */
	public int getPropertyNumber() {
		return properties.size();
	}

	/**
	 * Counts the number of properties the player owns of a specified color group.
	 * @param name The name of the color group.
	 * @return The number of properties owned within that color group.
	 */
	/**
	 * Counts the number of properties the player owns of a specified color group.
	 * @param name The name of the color group.
	 * @return The number of properties owned within that color group.
	 */
	private int getPropertyNumberForColor(String name) {
		Integer number = (Integer)colorGroups.get(name);
		if(number != null) {
			return number.intValue();
		}
		return 0;
	}

	/**
	 * Checks if the player has zero or negative money indicating bankruptcy.
	 * @return true if the player is bankrupt, false otherwise.
	 */
	/**
	 * Checks if the player has zero or negative money indicating bankruptcy.
	 * @return true if the player is bankrupt, false otherwise.
	 */
	public boolean isBankrupt() {
		return money <= 0;
	}

	/**
	 * Indicates whether the player is currently in jail.
	 * @return true if the player is in jail, false otherwise.
	 */
	/**
	 * Indicates whether the player is currently in jail.
	 * @return true if the player is in jail, false otherwise.
	 */
	public boolean isInJail() {
		return inJail;
	}

	/**
	 * Returns the number of railroad properties the player owns.
	 * @return The count of railroad properties owned.
	 */
	/**
	 * Returns the number of railroad properties the player owns.
	 * @return The count of railroad properties owned.
	 */
	public int numberOfRR() {
		return getPropertyNumberForColor(RailRoadCell.COLOR_GROUP);
	}

	/**
	 * Returns the number of utility properties the player owns.
	 * @return The count of utility properties owned.
	 */
	/**
	 * Returns the number of utility properties the player owns.
	 * @return The count of utility properties owned.
	 */
	public int numberOfUtil() {
		return getPropertyNumberForColor(UtilityCell.COLOR_GROUP);
	}
	
	/**
	 * Pays rent to another player and handles bankruptcy and property exchange if payment exceeds funds.
	 * @param owner The player receiving the rent payment.
	 * @param rentValue The amount of rent to be paid.
	 */
	/**
	 * Pays rent to another player and handles bankruptcy and property exchange if payment exceeds funds.
	 * @param owner The player receiving the rent payment.
	 * @param rentValue The amount of rent to be paid.
	 */
	public void payRentTo(Player owner, int rentValue) {
		if(money < rentValue) {
			owner.money += money;
			money -= rentValue;
		}
		else {
			money -= rentValue;
			owner.money +=rentValue;
		}
		if(isBankrupt()) {
			money = 0;
			exchangeProperty(owner);
		}
	}
	
	/**
	 * Purchases the property at the player's current position if it is available.
	 */
	/**
	 * Purchases the property at the player's current position if it is available.
	 */
	public void purchase() {
		if(getPosition().isAvailable()) {
			Cell c = getPosition();
			c.setAvailable(false);
			if(c instanceof PropertyCell) {
				PropertyCell cell = (PropertyCell)c;
				purchaseProperty(cell);
			}
			if(c instanceof RailRoadCell) {
				RailRoadCell cell = (RailRoadCell)c;
				purchaseRailRoad(cell);
			}
			if(c instanceof UtilityCell) {
				UtilityCell cell = (UtilityCell)c;
				purchaseUtility(cell);
			}
		}
	}
	
	/**
	 * Buys a specified number of houses for all properties in a selected monopoly if the player can afford it.
	 * @param selectedMonopoly The name of the monopoly color group.
	 * @param houses The number of houses to purchase per property.
	 */
	/**
	 * Buys a specified number of houses for all properties in a selected monopoly if the player can afford it.
	 * @param selectedMonopoly The name of the monopoly color group.
	 * @param houses The number of houses to purchase per property.
	 */
	public void purchaseHouse(String selectedMonopoly, int houses) {
		GameBoard gb = GameMaster.instance().getGameBoard();
		PropertyCell[] cells = gb.getPropertiesInMonopoly(selectedMonopoly);
		if((money >= (cells.length * (cells[0].getHousePrice() * houses)))) {
			for(int i = 0; i < cells.length; i++) {
				int newNumber = cells[i].getNumHouses() + houses;
				if (newNumber <= 5) {
					cells[i].setNumHouses(newNumber);
					this.setMoney(money - (cells[i].getHousePrice() * houses));
					GameMaster.instance().updateGUI();
				}
			}
		}
	}
	
	/**
	 * Purchases a standard property cell by invoking buyProperty with its price.
	 * @param cell The PropertyCell to purchase.
	 */
	/**
	 * Purchases a standard property cell by invoking buyProperty with its price.
	 * @param cell The PropertyCell to purchase.
	 */
	private void purchaseProperty(PropertyCell cell) {
        buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases a railroad cell by invoking buyProperty with its price.
	 * @param cell The RailRoadCell to purchase.
	 */
	/**
	 * Purchases a railroad cell by invoking buyProperty with its price.
	 * @param cell The RailRoadCell to purchase.
	 */
	private void purchaseRailRoad(RailRoadCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

	/**
	 * Purchases a utility cell by invoking buyProperty with its price.
	 * @param cell The UtilityCell to purchase.
	 */
	/**
	 * Purchases a utility cell by invoking buyProperty with its price.
	 * @param cell The UtilityCell to purchase.
	 */
	private void purchaseUtility(UtilityCell cell) {
	    buyProperty(cell, cell.getPrice());
	}

    /**
     * Sells a property and removes it from the player's ownership, adding the sale amount to player's money.
     * @param property The property Cell to be sold.
     * @param amount The price received from selling the property.
     */
    /**
     * Sells a property and removes it from the player's ownership, adding the sale amount to player's money.
     * @param property The property Cell to be sold.
     * @param amount The price received from selling the property.
     */
    public void sellProperty(Cell property, int amount) {
        property.setTheOwner(null);
        if(property instanceof PropertyCell) {
            properties.remove(property);
        }
        if(property instanceof RailRoadCell) {
            railroads.remove(property);
        }
        if(property instanceof UtilityCell) {
            utilities.remove(property);
        }
        setMoney(getMoney() + amount);
    }

	/**
	 * Sets the player's jail status to the specified value.
	 * @param inJail true if player is in jail, false otherwise.
	 */
	/**
	 * Sets the player's jail status to the specified value.
	 * @param inJail true if player is in jail, false otherwise.
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	/**
	 * Sets the player's money to the specified amount.
	 * @param money The new amount of money for the player.
	 */
	/**
	 * Sets the player's money to the specified amount.
	 * @param money The new amount of money for the player.
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Sets the player's name to the specified string.
	 * @param name The player's new name.
	 */
	/**
	 * Sets the player's name to the specified string.
	 * @param name The player's new name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Updates the player's position on the board to the specified cell.
	 * @param newPosition The Cell to set as the player's current position.
	 */
	/**
	 * Updates the player's position on the board to the specified cell.
	 * @param newPosition The Cell to set as the player's current position.
	 */
	public void setPosition(Cell newPosition) {
		this.position = newPosition;
	}

    /**
     * Returns the player's name as a string representation.
     * @return The player's name.
     */
    /**
     * Returns the player's name as a string representation.
     * @return The player's name.
     */
    public String toString() {
        return name;
    }
    
    /**
     * Clears all property, railroad, and utility ownership for the player.
     */
    /**
     * Clears all property, railroad, and utility ownership for the player.
     */
    public void resetProperty() {
    	properties = new ArrayList<PropertyCell>();
    	railroads = new ArrayList<Cell>();
    	utilities = new ArrayList<Cell>();
	}
}
