package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ButtonClickEditor extends AbstractCellEditor implements TableCellEditor {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6546334664166791132L;

	private JPanel panel;

	private JButton button;

	public ButtonClickEditor(String title, ActionListener listener) {
		button = new JButton(title);
		button.addActionListener(listener);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(this.button, BorderLayout.CENTER);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

}