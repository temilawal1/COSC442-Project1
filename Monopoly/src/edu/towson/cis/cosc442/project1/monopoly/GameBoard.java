package edu.towson.cis.cosc442.project1.monopoly;

import java.util.ArrayList;
import java.util.Hashtable;

public class GameBoard {

	private ArrayList<Cell> cells = new ArrayList<Cell>();
    private ArrayList<Card> chanceCards = new ArrayList<Card>();
	//the key of colorGroups is the name of the color group.
	private Hashtable<String, Integer> colorGroups = new Hashtable<String, Integer>();
	private ArrayList<Card> communityChestCards = new ArrayList<Card>();
	/**
	 * Initializes a new game board and adds the starting 'Go' cell.
	 */
	/**
	 * Initializes a new game board and adds the starting 'Go' cell.
	 */
	public GameBoard() {
		Cell go = new GoCell();
		addCell(go);
	}

    /**
     * Adds a given card to either the community chest or chance card collection based on its type.
     * @param card The card to be added to the game board
     */
    /**
     * Adds a given card to either the community chest or chance card collection based on its type.
     * @param card The card to be added to the game board
     */
    public void addCard(Card card) {
        if(card.getCardType() == Card.TYPE_CC) {
            communityChestCards.add(card);
        } else {
            chanceCards.add(card);
        }
    }
	
	/**
	 * Adds a general cell to the game board's list of cells.
	 * @param cell The cell to be added
	 */
	/**
	 * Adds a general cell to the game board's list of cells.
	 * @param cell The cell to be added
	 */
	public void addCell(Cell cell) {
		cells.add(cell);
	}
	
	/**
	 * Adds a property cell to the game board and updates the count of properties in its color group.
	 * @param cell The property cell to be added
	 */
	/**
	 * Adds a property cell to the game board and updates the count of properties in its color group.
	 * @param cell The property cell to be added
	 */
	public void addCell(PropertyCell cell) {
		int propertyNumber = getPropertyNumberForColor(cell.getColorGroup());
		String colorGroup = cell.getColorGroup();
		colorGroups.put(colorGroup, new Integer(propertyNumber + 1));
        cells.add(cell);
	}

    /**
     * Draws the top community chest card, removes it from the deck, then adds it back to the bottom.
     * @return The drawn community chest card
     */
    /**
     * Draws the top community chest card, removes it from the deck, then adds it back to the bottom.
     * @return The drawn community chest card
     */
    public Card drawCCCard() {
        Card card = (Card)communityChestCards.get(0);
        communityChestCards.remove(0);
        addCard(card);
        return card;
    }

    /**
     * Draws the top chance card, removes it from the deck, then adds it back to the bottom.
     * @return The drawn chance card
     */
    /**
     * Draws the top chance card, removes it from the deck, then adds it back to the bottom.
     * @return The drawn chance card
     */
    public Card drawChanceCard() {
        Card card = (Card)chanceCards.get(0);
        chanceCards.remove(0);
        addCard(card);
        return card;
    }

	/**
	 * Retrieves the cell at the specified index on the game board.
	 * @param newIndex The index of the cell to retrieve
	 * @return The cell located at the given index
	 */
	/**
	 * Retrieves the cell at the specified index on the game board.
	 * @param newIndex The index of the cell to retrieve
	 * @return The cell located at the given index
	 */
	public Cell getCell(int newIndex) {
		return (Cell)cells.get(newIndex);
	}
	
	/**
	 * Returns the total number of cells on the game board.
	 * @return The number of cells on the game board
	 */
	/**
	 * Returns the total number of cells on the game board.
	 * @return The number of cells on the game board
	 */
	public int getCellNumber() {
		return cells.size();
	}
	
	/**
	 * Gets all property cells in a specified color group (monopoly) on the board.
	 * @param color The color group name to filter properties
	 * @return An array of property cells belonging to the specified color group
	 */
	/**
	 * Gets all property cells in a specified color group (monopoly) on the board.
	 * @param color The color group name to filter properties
	 * @return An array of property cells belonging to the specified color group
	 */
	public PropertyCell[] getPropertiesInMonopoly(String color) {
		PropertyCell[] monopolyCells = 
			new PropertyCell[getPropertyNumberForColor(color)];
		int counter = 0;
		for (int i = 0; i < getCellNumber(); i++) {
			Cell c = getCell(i);
			if(c instanceof PropertyCell) {
				PropertyCell pc = (PropertyCell)c;
				if(pc.getColorGroup().equals(color)) {
					monopolyCells[counter] = pc;
					counter++;
				}
			}
		}
		return monopolyCells;
	}
	
	/**
	 * Returns the number of properties that exist for a specified color group.
	 * @param name The color group name to query
	 * @return The count of properties in the indicated color group
	 */
	/**
	 * Returns the number of properties that exist for a specified color group.
	 * @param name The color group name to query
	 * @return The count of properties in the indicated color group
	 */
	public int getPropertyNumberForColor(String name) {
		Integer number = (Integer)colorGroups.get(name);
		if(number != null) {
			return number.intValue();
		}
		return 0;
	}

	/**
	 * Finds a cell by its name on the game board.
	 * @param string The name of the cell to find
	 * @return The cell with the specified name or null if not found
	 */
	/**
	 * Finds a cell by its name on the game board.
	 * @param string The name of the cell to find
	 * @return The cell with the specified name or null if not found
	 */
	public Cell queryCell(String string) {
		for(int i = 0; i < cells.size(); i++){
			Cell temp = (Cell)cells.get(i); 
			if(temp.getName().equals(string)) {
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * Finds the index of a cell by its name on the game board.
	 * @param string The name of the cell to find
	 * @return The index of the cell with the specified name or -1 if not found
	 */
	/**
	 * Finds the index of a cell by its name on the game board.
	 * @param string The name of the cell to find
	 * @return The index of the cell with the specified name or -1 if not found
	 */
	public int queryCellIndex(String string){
		for(int i = 0; i < cells.size(); i++){
			Cell temp = (Cell)cells.get(i); 
			if(temp.getName().equals(string)) {
				return i;
			}
		}
		return -1;
	}

    /**
     * Removes all community chest cards from the game board.
     */
    /**
     * Removes all community chest cards from the game board.
     */
    public void removeCards() {
        communityChestCards.clear();
    }
}
