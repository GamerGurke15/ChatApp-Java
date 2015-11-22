package gui.chat;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.MainFrame;

public class ChatGUI extends JPanel{
	
	private MainFrame context;
	
	public ChatGUI(Dimension size, MainFrame context){
		super();
		this.context = context;
		
		SpringLayout l;
		setLayout(l = new SpringLayout());
	}

}
