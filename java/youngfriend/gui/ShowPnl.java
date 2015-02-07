package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import youngfriend.utils.GUIUtils;

public class ShowPnl extends JPanel {
	private static final long serialVersionUID = 1L;

	public ShowPnl(Window win, int width, int height, String data) {
		this.setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout(0, 0));
		JDialog dialog = GUIUtils.getDialog(win, "œ‘ æ", this);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		JTextArea textArea = new JTextArea(data);
		textArea.setLineWrap(true);
		scrollPane.getViewport().setView(textArea);
		dialog.setVisible(true);
		dialog.dispose();
	}
}
