package edu.towson.cis.cosc442.project1.monopoly;

public class PropertyCell extends Cell {
	private String colorGroup;
	private int housePrice;
	private int numHouses;
	private int rent;
	private int sellPrice;

	/**
	 * Returns the color group associated with this property cell.
	 * @return The color group string of the property.
	 */
	/**
	 * Returns the color group associated with this property cell.
	 * @return The color group string of the property.
	 */
	public String getColorGroup() {
		return colorGroup;
	}

	/**
	 * Returns the price of a house on this property.
	 * @return The house price as an integer.
	 */
	/**
	 * Returns the price of a house on this property.
	 * @return The house price as an integer.
	 */
	public int getHousePrice() {
		return housePrice;
	}

	/**
	 * Returns the number of houses currently built on this property.
	 * @return The current number of houses on the property.
	 */
	/**
	 * Returns the number of houses currently built on this property.
	 * @return The current number of houses on the property.
	 */
	public int getNumHouses() {
		return numHouses;
	}
    
    /**
     * Returns the selling price of this property.
     * @return The sale price of the property.
     */
    /**
     * Returns the selling price of this property.
     * @return The sale price of the property.
     */
    public int getPrice() {
		return sellPrice;
	}

	/**
	 * Calculates and returns the rent due for this property based on ownership and houses.
	 * @return The rent amount to be charged to a player landing on the property.
	 */
	/**
	 * Calculates and returns the rent due for this property based on ownership and houses.
	 * @return The rent amount to be charged to a player landing on the property.
	 */
	public int getRent() {
		int rentToCharge = rent;
		String [] monopolies = theOwner.getMonopolies();
		rentToCharge = calculateMonopoliesRent(rentToCharge, monopolies);
		if(numHouses > 0) {
			rentToCharge = rent * (numHouses + 1);
		}
		return rentToCharge;
	}

	/**
	 * Calculates the rent doubling effect if the property is part of a monopoly.
	 * @param rentToCharge The current rent amount to potentially modify.
	 * @param monopolies Array of color groups where the owner has monopolies.
	 * @return The adjusted rent after considering monopolies.
	 */
	/**
	 * Calculates the rent doubling effect if the property is part of a monopoly.
	 * @param rentToCharge The current rent amount to potentially modify.
	 * @param monopolies Array of color groups where the owner has monopolies.
	 * @return The adjusted rent after considering monopolies.
	 */
	private int calculateMonopoliesRent(int rentToCharge, String[] monopolies) {
		for(int i = 0; i < monopolies.length; i++) {
			if(monopolies[i].equals(colorGroup)) {
				rentToCharge = rent * 2;
			}
		}
		return rentToCharge;
	}

	/**
	 * Executes the action when a player lands on this property, charging rent if applicable.
	 */
	/**
	 * Executes the action when a player lands on this property, charging rent if applicable.
	 */
	public void playAction() {
		Player currentPlayer = null;
		if(!isAvailable()) {
			currentPlayer = GameMaster.instance().getCurrentPlayer();
			if(theOwner != currentPlayer) {
				currentPlayer.payRentTo(theOwner, getRent());
			}
		}
	}

	/**
	 * Sets the color group of this property.
	 * @param colorGroup A string representing the property's color group.
	 */
	/**
	 * Sets the color group of this property.
	 * @param colorGroup A string representing the property's color group.
	 */
	public void setColorGroup(String colorGroup) {
		this.colorGroup = colorGroup;
	}

	/**
	 * Sets the cost to build a house on this property.
	 * @param housePrice The price per house to be set.
	 */
	/**
	 * Sets the cost to build a house on this property.
	 * @param housePrice The price per house to be set.
	 */
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * Sets the number of houses currently on this property.
	 * @param numHouses The number of houses to assign to the property.
	 */
	/**
	 * Sets the number of houses currently on this property.
	 * @param numHouses The number of houses to assign to the property.
	 */
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}

	/**
	 * Sets the selling price of this property.
	 * @param sellPrice The sale price to assign to the property.
	 */
	/**
	 * Sets the selling price of this property.
	 * @param sellPrice The sale price to assign to the property.
	 */
	public void setPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * Sets the base rent amount for this property.
	 * @param rent The rent value to be set.
	 */
	/**
	 * Sets the base rent amount for this property.
	 * @param rent The rent value to be set.
	 */
	public void setRent(int rent) {
		this.rent = rent;
	}
}
