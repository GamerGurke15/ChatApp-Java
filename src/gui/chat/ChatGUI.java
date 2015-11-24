package gui.chat;

import java.awt.Dimension;

import javax.swing.SpringLayout;

import gui.ChatAppPanel;
import gui.MainFrame;

public class ChatGUI extends ChatAppPanel{
	
	private MainFrame context;
	private SpringLayout l;
	
	public ChatGUI(Dimension size, MainFrame context){
		super();
		this.context = context;
		
		setLayout(l = new SpringLayout());
	}
	
	public void activate(){
		super.activate();
	}
	
	private void loadChatFromFile(){
		
	}
	
	private void saveChatToFile(){
		
	}

}
