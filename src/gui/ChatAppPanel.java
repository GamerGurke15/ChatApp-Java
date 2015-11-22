package gui;

import javax.swing.JPanel;

public class ChatAppPanel extends JPanel{

	private boolean active;

	/**
	 * this method is called to indicate that this Panel is now the visible one
	 */
	public void activate(){
		setVisible(true);
		active = true;
		System.out.println("---------activated " + this.getClass());
	}

	/**
	 * this method is called to indicate that this Panel is no longer visible.
	 */
	public void deactivate(){
		setVisible(false);
		active = false;
		System.out.println("---------deactivated " + this.getClass());
	}
	
	/**
	 * returns whether this Panel is active or not
	 * @return true if this Panel is active
	 */
	public boolean isActive(){
		return active;
	}
}
