package gui.login;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jivesoftware.smack.roster.Roster;

import gui.ChatAppPanel;
import gui.MainFrame;
import xmpp.XmppManager;

public class LoginGUI extends ChatAppPanel{

	private MainFrame context;
	private XmppManager xmppManager;

	private JTextField username;
	private JLabel usrLbl;
	private JPasswordField password;
	private JLabel pwdLbl;
	private JButton submit;

	public LoginGUI(Dimension size, MainFrame context){
		super();
		this.context = context;
		this.xmppManager = context.getXmppManager();
	}
	
	public void activate(){
		super.activate();
		SpringLayout l;
		setLayout(l = new SpringLayout());

		username = new JTextField();
		usrLbl = new JLabel("Username");
		password = new JPasswordField();
		pwdLbl = new JLabel("Password");
		submit = new JButton("Log in");

		username.setPreferredSize(new Dimension(100, 26));
		password.setPreferredSize(new Dimension(100, 26));

		l.putConstraint(SpringLayout.HORIZONTAL_CENTER, usrLbl, -50, SpringLayout.HORIZONTAL_CENTER, this);
		l.putConstraint(SpringLayout.VERTICAL_CENTER, usrLbl, -50, SpringLayout.VERTICAL_CENTER, this);

		l.putConstraint(SpringLayout.HORIZONTAL_CENTER, username, 50, SpringLayout.HORIZONTAL_CENTER, this);
		l.putConstraint(SpringLayout.VERTICAL_CENTER, username, -50, SpringLayout.VERTICAL_CENTER, this);

		l.putConstraint(SpringLayout.HORIZONTAL_CENTER, pwdLbl, -50, SpringLayout.HORIZONTAL_CENTER, this);
		l.putConstraint(SpringLayout.VERTICAL_CENTER, pwdLbl, 0, SpringLayout.VERTICAL_CENTER, this);

		l.putConstraint(SpringLayout.HORIZONTAL_CENTER, password, 50, SpringLayout.HORIZONTAL_CENTER, this);
		l.putConstraint(SpringLayout.VERTICAL_CENTER, password, 0, SpringLayout.VERTICAL_CENTER, this);

		l.putConstraint(SpringLayout.HORIZONTAL_CENTER, submit, 0, SpringLayout.HORIZONTAL_CENTER, this);
		l.putConstraint(SpringLayout.VERTICAL_CENTER, submit, 50, SpringLayout.VERTICAL_CENTER, this);

		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (!username.getText().isEmpty() && !password.getPassword().equals("")){
					System.out.println("Performing log in as user: " + username.getText());
					if (LoginGUI.this.xmppManager.performLogin(username.getText(),
							new String(password.getPassword()))){
						//loading the roster
						Roster roster = xmppManager.getRoster();
						if (!roster.isLoaded())
							try{
							roster.reloadAndWait();
							} catch (Exception ex){
								ex.printStackTrace();
							}
						LoginGUI.this.context.setStatus(MainFrame.STATUS_SELECT);
					}
				}
			}
		});

		add(usrLbl);
		add(username);
		add(pwdLbl);
		add(password);
		add(submit);
	}
}
