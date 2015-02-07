package youngfriend.utils;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class GUIUtils {
	public static JDialog getDialog(Window owner, String title, Component com) {
		JDialog dialog = new JDialog(owner, title, ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.getContentPane().add(com);
		dialog.pack();
		dialog.setResizable(true);
		dialog.setLocationRelativeTo(owner);
		return dialog;
	}

	public static void showMsg(Window owner, String msg) {
		JOptionPane.showMessageDialog(owner, msg);
	}

	public static boolean showConfirm(Window owner, String msg) {
		return (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(owner, msg, "ÌבÊ¾", JOptionPane.OK_CANCEL_OPTION));
	}
}
