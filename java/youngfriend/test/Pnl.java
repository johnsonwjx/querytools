package youngfriend.test;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Pnl extends JPanel {
	public static void main(String[] args) {
		System.out.println("Abc".matches("(?i).*a.*"));
	}

	private JTextField textField;
	private JPopupMenu menu = new JPopupMenu();
	private JDialog dlg;

	public Pnl(Frame owner) {
		setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField.setText("");
		add(textField, BorderLayout.NORTH);
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
		dlg = new JDialog(owner, "xx", true);
		dlg.add(this);
		dlg.setSize(this.getSize());
		dlg.setVisible(true);
	}

	private void search() {
		if (textField.getText().length() == 0) {
			menu.setVisible(false);
			return;
		}
		for (int i = 0; i < 10; i++) {
			menu.add(new JMenuItem(i + ""));
		}
		this.add(menu);
		menu.setLocation(20, 20);
		menu.setVisible(true);
	}
}
