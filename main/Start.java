package main;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import model.LoginOnFrame;

public class Start {
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new LoginOnFrame();
	}
}
