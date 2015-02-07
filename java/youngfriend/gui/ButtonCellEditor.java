package youngfriend.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import youngfriend.utils.CommonUtils;

public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 1L;
	private ButtonEditorCom editorCom = null;
	private ButtonEditorCom renderCom = new ButtonEditorCom(false, null);
	private JTable table;
	private TableCellRenderer render;
	private boolean editing = false;

	public ButtonCellEditor(ActionListener action, final boolean editable) {
		super();
		editorCom = new ButtonEditorCom(editable, action);
		editorCom.txt.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				editing = true;

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				editing = true;

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				editing = true;

			}
		});
		render = new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if (editable) {
					renderCom.setValue((String) value);
				} else {
					if (value == null) {
						renderCom.setValue("");
					} else {
						renderCom.setValue(value.toString());
					}
				}
				if (value != null && value instanceof String) {
					String[] colors = ((String) value).split(",");
					if (colors.length == 3 && CommonUtils.isNumberString(colors[0]) && CommonUtils.isNumberString(colors[1]) && CommonUtils.isNumberString(colors[2])) {
						renderCom.setBgColor(new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2])));
					}
				}
				return renderCom;
			}
		};

	}

	public TableCellRenderer getTableCellRenderer() {
		return render;
	}

	@Override
	public Object getCellEditorValue() {
		if (editing) {
			table.setValueAt(editorCom.getValue(), table.getSelectedRow(), table.getSelectedColumn());
			editing = false;
			editorCom.setValue("");
		}
		return table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.table = table;
		if (value == null) {
			editorCom.setValue("");
		} else {
			editorCom.setValue(value.toString());
		}
		editing = false;
		return editorCom;
	}

}