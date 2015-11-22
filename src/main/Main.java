package main;

import javax.swing.SwingUtilities;

import gui.MainFrame;

public class Main{

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			
			private int width = 600;
			private int height = 600;
			
			@Override
			public void run(){
				MainFrame mainFrame = new MainFrame(width, height);
				mainFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);
			}
		});
	}

}
