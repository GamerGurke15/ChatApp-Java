package gui;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SpringLayout;

import gui.chat.ChatGUI;
import gui.login.LoginGUI;
import gui.selection.ChatSelectionGUI;
import xmpp.XmppManager;

public class MainFrame extends JFrame{

	public static final int STATUS_LOGIN = 0;
	public static final int STATUS_SELECT = 1;
	public static final int STATUS_CHAT = 2;

	private int status = STATUS_LOGIN;

	private XmppManager xmppManager;

	private String openChat = null;

	private ArrayList<ChatAppPanel> gui;
	private SpringLayout l;
	private Container p;
	private int height, width;

	public MainFrame(int width, int height){
		super("");
		this.width = width;
		this.height = height;
		setBounds(0, 0, width, height);

		xmppManager = new XmppManager("raspi-server.ddns.net", "raspi-server.mooo.com", 5222);
		xmppManager.init();

		l = new SpringLayout();
		p = this.getContentPane();
		p.setLayout(l);
		initGUI();
		setBackground(Color.BLUE);
		setStatus(status);
	}

	private void initGUI(){
		gui = new ArrayList<>();
		gui.add(new LoginGUI(this.getSize(), this));
		gui.add(new ChatSelectionGUI(this.getSize(), this));
		gui.add(new ChatGUI(this.getSize(), this));
		for (JComponent comp : gui){
			comp.setBounds(0, 0, width, height);
			l.putConstraint(SpringLayout.NORTH, comp, 0, SpringLayout.NORTH, p);
			l.putConstraint(SpringLayout.EAST, comp, 0, SpringLayout.EAST, p);
			l.putConstraint(SpringLayout.SOUTH, comp, 0, SpringLayout.SOUTH, p);
			l.putConstraint(SpringLayout.WEST, comp, 0, SpringLayout.WEST, p);
			comp.setVisible(false);
			add(comp);
		}
	}

	public boolean setStatus(int status){
		if (status < gui.size()){
			this.status = status;
			for (int i = 0; i < gui.size(); i++)
				if (i == status)
					gui.get(i).activate();
				else if (gui.get(i).isActive()) gui.get(i).deactivate();
			setTitle(gui.get(status).getClass().toString());
			revalidate();
			return true;
		}
		return false;
	}

	public XmppManager getXmppManager(){
		return xmppManager;
	}

	public void setOpenChat(String JID){
		openChat = JID;
	}

	public String getOpenChat(){
		return openChat;
	}
}
