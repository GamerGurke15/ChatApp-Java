package gui.selection;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jivesoftware.smack.roster.RosterEntry;

import gui.ChatAppPanel;
import gui.MainFrame;
import xmpp.XmppManager;

public class ChatSelectionGUI extends ChatAppPanel{

	private MainFrame context;
	private SpringLayout l;
	private XmppManager xmppManager;

	private JList<RosterEntry> ChatList;
	private JScrollPane sp;

	public ChatSelectionGUI(Dimension size, MainFrame context){
		super();
		setLayout(l = new SpringLayout());
		this.context = context;
		xmppManager = context.getXmppManager();
	}

	public void activate(){
		super.activate();
		RosterEntry[] chats = new RosterEntry[0];
		for (RosterEntry e : xmppManager.getRoster().getEntries())
			System.out.println(e.toString());
		ChatList = new JList<>(xmppManager.getRoster().getEntries().toArray(chats));
		ChatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ChatList.setLayoutOrientation(JList.VERTICAL);
		ChatList.setVisibleRowCount(-1);
		ChatList.addListSelectionListener(new ListSelectionListener(){
			
			@Override
			public void valueChanged(ListSelectionEvent e){
				RosterEntry entry = ChatList.getSelectedValue();
				context.setOpenChat(entry.getUser());
				context.setStatus(MainFrame.STATUS_CHAT);
			}
		});
		sp = new JScrollPane(ChatList);
		sp.setPreferredSize(getSize());
		
		l.putConstraint(SpringLayout.NORTH, sp, 0, SpringLayout.NORTH, this);
		l.putConstraint(SpringLayout.WEST, sp, 0, SpringLayout.WEST, this);
		add(sp);
	}

}
