package xmpp;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class XmppManager{
	private static final int packetReplyTimeout = 1000;

	private String server;
	private String service;
	private int port;

	private XMPPTCPConnectionConfiguration config;
	private XMPPTCPConnection connection;

	private ChatManager chatManager;
	private ChatMessageListener messageListener;
	private ChatManagerListener managerListener;

	/**
	 * creates a IM Manager with the given server ID
	 *
	 * @param server host address
	 * @param service service name
	 * @param context the IntentService which sends and receives the messages
	 */
	public XmppManager(String server, String service, int port){
		this.server = server;
		this.service = service;
		this.port = port;
	}

	/**
	 * initializes the connection with the server
	 *
	 * @return true if a connection could be established
	 */
	public boolean init(){
		System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));
		SmackConfiguration.setDefaultPacketReplyTimeout(packetReplyTimeout);

		config = XMPPTCPConnectionConfiguration.builder().setServiceName(service).setHost(server).setPort(port)
				.setSecurityMode(SecurityMode.disabled).build();
		connection = new XMPPTCPConnection(config);
		try{
			connection.connect();
		} catch (Exception e){
			System.err.println("Couldn't connect.");
			System.err.println(e.toString());
			return false;
		}

		messageListener = new MyChatMassageListener();
		managerListener = new MyChatManagerListener();

		chatManager = ChatManager.getInstanceFor(connection);
		chatManager.addChatListener(managerListener);

		System.out.println("Initialized XmppManager and connected.");
		return true;
	}

	/**
	 * logs in to the server
	 *
	 * @param username the username to use
	 * @param password the corresponding password
	 * @return true if the login was successful
	 */
	public boolean performLogin(String username, String password){
		if (connection != null && connection.isConnected()){
			try{
				connection.login(username, password);
				System.out.println("Successfully logged in.");
				return true;
			} catch (Exception e){
				System.err.println("Couldn't log in.");
				System.err.println(e.toString());
				return false;
			}
		}
		System.err.println("Couldn't log in: No connection.");
		return false;
	}

	/**
	 * returns the roster for the current connection
	 *
	 * @return the roster and null if the roster cannot be accessed
	 */
	public Roster getRoster(){
		if (connection != null && connection.isConnected()){
			return Roster.getInstanceFor(connection);
		} else{
			System.err.println("Couldn't get the roster: No connection.");
			return null;
		}
	}

	/**
	 * sets the status
	 *
	 * @param available if true the status type will be set to available
	 *        otherwise to unavailable
	 * @param status the status message
	 * @return true if setting the status was successful
	 */
	public boolean setStatus(boolean available, String status){
		if (connection != null && connection.isConnected()){
			Presence.Type type = available ? Presence.Type.available : Presence.Type.unavailable;
			Presence presence = new Presence(type);

			presence.setStatus(status);
			try{
				connection.sendStanza(presence);
			} catch (Exception e){
				System.err.println(e.toString());
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * sends a text message
	 *
	 * @param message the message text to send
	 * @param buddyJID the Buddy to receive the message
	 * @return true if sending was successful
	 */
	public boolean sendMessage(String message, String buddyJID){
		if (connection != null && connection.isConnected()){
			Chat chat = chatManager.createChat(buddyJID, messageListener);
			try{
				chat.sendMessage(message);
				System.out.println("To " + buddyJID + ": " + message);
			} catch (Exception e){
				System.err.println("Couldn't send message.");
				System.err.println(e.toString());
				return false;
			}
			return true;
		}
		System.err.println("Sending failed: No connection.");
		return false;
	}

	public void createEntry(String user, String name)
			throws NotLoggedInException, NoResponseException, XMPPErrorException, NotConnectedException{
		System.out.println("Creating entry for " + user + " with name: " + name);
		Roster roster = Roster.getInstanceFor(connection);
		roster.createEntry(user, name, null);
	}

	/**
	 * destroys the connection (performs a disconnect)
	 */
	public void destroy(){
		if (connection != null && connection.isConnected()){
			connection.disconnect();
		}
	}

	class MyChatMassageListener implements ChatMessageListener{

		@Override
		public void processMessage(Chat chat, Message message){
			String from = message.getFrom();
			String body = message.getBody();
			if (body != null) System.out.println(from + ": " + body);

		}
	}

	class MyChatManagerListener implements ChatManagerListener{

		@Override
		public void chatCreated(Chat chat, boolean createdLocally){
			chat.addMessageListener(messageListener);
		}

	}
}
