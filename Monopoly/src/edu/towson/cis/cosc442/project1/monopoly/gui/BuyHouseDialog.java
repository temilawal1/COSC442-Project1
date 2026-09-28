
package edu.towson.cis.cosc442.project1.monopoly.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import edu.towson.cis.cosc442.project1.monopoly.Player;


public class BuyHouseDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JComboBox<?> cboMonopoly;
	private JComboBox<?> cboNumber;

	private Player player;


	/**
	 * Constructs a dialog that allows the given player to buy houses for their monopolies.
	 * @param player the player who will be buying houses
	 */
	public BuyHouseDialog(Player player) {
		this.player = player;
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(3, 2));
		c.add(new JLabel("Select monopoly"));
		c.add(buildMonopolyComboBox());
		c.add(new JLabel("Number of houses"));
		c.add(buildNumberComboBox());
		c.add(buildOKButton());
		c.add(buildCancelButton());
		c.doLayout();
		this.pack();
	}

	/**
	 * Creates and returns a button that cancels the house buying operation when clicked.
	 * @return the Cancel button component
	 */
	private JButton buildCancelButton() {
		JButton btn = new JButton("Cancel");
		btn.addActionListener(new ActionListener(){

			/**
			 * Processes action events from UI components such as buttons.
			 * @param e the action event triggered by a component
			 */
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
			}
		});
		return btn;
	}


	/**
	 * Creates and returns a combo box populated with the player's monopolies for selection.
	 * @return the combo box listing the player's monopolies
	 */
	private JComboBox<?> buildMonopolyComboBox() {
		cboMonopoly = new JComboBox<Object>(player.getMonopolies());
		return cboMonopoly;
	}
	

	/**
	 * Creates and returns a combo box for selecting the number of houses to purchase.
	 * @return the combo box with house number options
	 */
	private JComboBox<?> buildNumberComboBox() {
		cboNumber = new JComboBox<Object>(new Integer[]{
				(1), (2), (3), (4), (5)});
		return cboNumber;
	}


	/**
	 * Creates and returns a button that confirms the house purchase when clicked.
	 * @return the OK button component
	 */
	private JButton buildOKButton() {
		JButton btn = new JButton("OK");
		btn.addActionListener(new ActionListener(){

			/**
			 * Processes action events from UI components such as buttons.
			 * @param e the action event triggered by a component
			 */
			public void actionPerformed(ActionEvent e) {
				okClicked();
			}
		});
		return btn;
	}
	

	/**
	 * Handles the cancel action by closing the dialog without making changes.
	 */
	private void cancelClicked() {
		this.dispose();
	}

	/**
	 * Processes the OK action by purchasing houses for the selected monopoly and closing the dialog.
	 */
	private void okClicked() {
		String monopoly = (String)cboMonopoly.getSelectedItem();
		int number = cboNumber.getSelectedIndex() + 1;
		player.purchaseHouse(monopoly, number);
		this.dispose();
	}
}
