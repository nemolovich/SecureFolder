package fr.nemolovich.apps.securefolder.command.option;

import javax.swing.JOptionPane;

public class GetPasswordFrame {

	public static String getpassword() {
		return JOptionPane.showInputDialog(null,
				"Please type a password", "Enter passord",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
