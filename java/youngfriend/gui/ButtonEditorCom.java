package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ButtonEditorCom extends JPanel {
	private static final long serialVersionUID = 1L;
	JTextField txt = null;
	private JButton btn;

	public ButtonEditorCom(boolean editable, ActionListener action) {
		this.setLayout(new BorderLayout(-1, -1));
		txt = new JTextField();
		txt.setEditable(editable);
		txt.setSize(100, 100);
		txt.setPreferredSize(new Dimension(100, 100));
		btn = new JButton("...");
		btn.addActionListener(action);
		btn.setPreferredSize(new Dimension(25, 0));
		this.add(txt, BorderLayout.CENTER);
		this.add(btn, BorderLayout.EAST);
	}

	public void setColor(Color fg) {
		txt.setForeground(fg);
	}

	public void setBgColor(Color c) {
		txt.setBackground(c);
	}

	public void addAction(ActionListener action) {
		btn.addActionListener(action);
	}

	public String getValue() {
		return txt.getText();
	}

	public void setValue(String value) {
		txt.setText(value);
	}
}
