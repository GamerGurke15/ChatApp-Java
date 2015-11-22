package gui.selection;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import gui.MainFrame;
import xmpp.XmppManager;

public class ChatSelectionGUI extends JPanel{

	private MainFrame context;
	private XmppManager xmppManager;
	
	private JList<String> ChatList;
	private JScrollPane sp;
	
	public ChatSelectionGUI(Dimension size, MainFrame context){
		super();
		this.context = context;
		xmppManager = context.getXmppManager();
		
		String[] chats = null;
		
		ChatList = new JList<>(chats);
		ChatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ChatList.setLayoutOrientation(JList.VERTICAL);
		ChatList.setVisibleRowCount(-1);
		sp = new JScrollPane(ChatList);
	}

}
