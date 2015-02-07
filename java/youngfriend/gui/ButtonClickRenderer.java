package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonClickRenderer implements TableCellRenderer {
	private JPanel panel;
	private JButton button;

	public ButtonClickRenderer(String title) {
		button = new JButton(title);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(button, BorderLayout.CENTER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		return panel;
	}

}