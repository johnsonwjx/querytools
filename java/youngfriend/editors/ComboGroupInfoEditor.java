/*
 * ChooseDatatablele.java
 *
 * Created on 2007Äê8ÔÂ2ÈÕ, ÉÏÎç11:22
 */

package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class ComboGroupInfoEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	public ComboGroupInfoEditor() {
		initComponents();
	}

	private String[] Titles = new String[] { "ÐòºÅ", "×Ö¶Î1", "×Ö¶Î2", "×Ö¶Î3", "×Ö¶Î4" };
	private final int index_sort = 0;
	private JTable table;
	private DefaultTableModel model;

	private void initComponents() {
		this.setPreferredSize(new Dimension(600, 450));
		sp = new javax.swing.JScrollPane();
		setLayout(new BorderLayout(0, 0));
		add(sp);
		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		button = new JButton("\u6DFB\u52A0");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}

		});
		panel.add(button);

		button_1 = new JButton("\u5220\u9664");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delItems();
			}
		});
		panel.add(button_1);
		btnNewButton = new JButton("\u4E0A\u79FB");
		panel.add(btnNewButton);
		button_4 = new JButton("\u4E0B\u79FB");
		panel.add(button_4);
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(50, 0));
		panel.add(separator);

		button_2 = new JButton("\u786E\u5B9A");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}
		});

		panel.add(button_2);

		button_3 = new JButton("\u53D6\u6D88");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel.add(button_3);

		init();
	}

	private void init() {
		inittable();
		CompUtils.tableMove(btnNewButton, table, index_sort, true);
		CompUtils.tableMove(button_4, table, index_sort, false);
	}

	private void inittable() {
		model = new DefaultTableModel(Titles, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == index_sort) {
					return Integer.class;
				}
				return XMLDto.class;
			}
		};
		table = new JTable(model);
		TableColumnModel cm = table.getColumnModel();
		CompUtils.setTableWdiths(table, 0.1);
		ButtonCellEditor cellEditor = new ButtonCellEditor(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(table);
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				XMLDto value = (XMLDto) table.getValueAt(row, column);
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				pnl.edit(defaultpropeditor.getDialog(), null);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					table.setValueAt(value, row, column);
				}
			}
		}, false);

		for (int i = 1; i < cm.getColumnCount(); i++) {
			TableColumn c = cm.getColumn(i);
			c.setCellEditor(cellEditor);
			c.setCellRenderer(cellEditor.getTableCellRenderer());
		}
		sp.setViewportView(table);
	}

	private void delItems() {

		int[] rows = table.getSelectedRows();
		if (rows.length <= 0) {
			return;
		}
		CompUtils.stopTabelCellEditor(table);
		int newSel = rows[0];
		int rmoveCount = 0;
		for (int row : rows) {
			model.removeRow(row - rmoveCount);
			rmoveCount++;
		}
		CompUtils.initSortNum(model, index_sort);
		if (model.getRowCount() > 0) {
			if (newSel != 0) {
				newSel--;
			}
			table.setRowSelectionInterval(newSel, newSel);
		}
	}

	private void addItem() {
		CompUtils.stopTabelCellEditor(table);
		model.addRow(new Object[] { model.getRowCount() + 1, null, null, null });
	}

	private JScrollPane sp;
	private JPanel panel;
	private JButton button;
	private JButton button_1;
	private JSeparator separator;
	private JButton button_2;
	private JButton button_3;
	private JButton btnNewButton;
	private JButton button_4;
	private DefaultPropEditor defaultpropeditor;

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				CompUtils.stopTabelCellEditor(table);
				int count = model.getRowCount();
				if (count <= 0) {
					prop.setValue("");
				} else {
					Element root = DocumentHelper.createElement("root");
					for (int i = 0; i < count; i++) {
						StringBuilder nameSb = new StringBuilder();
						StringBuilder valueSb = new StringBuilder();
						for (int j = 1; j < model.getColumnCount(); j++) {
							XMLDto f = (XMLDto) model.getValueAt(i, j);
							if (f != null) {
								nameSb.append(f.getValue("itemlabel")).append(",");
								valueSb.append(f.getValue("itemname")).append(",");
							}
						}
						if (valueSb.length() == 0 || nameSb.length() == 0) {
							GUIUtils.showMsg(defaultpropeditor.getDialog(), "´æÔÚ¿ÕÁÐ");
							table.setRowSelectionInterval(i, i);
							return false;
						}
						nameSb.deleteCharAt(nameSb.length() - 1);
						valueSb.deleteCharAt(valueSb.length() - 1);

						Element items = root.addElement("items");
						items.addElement("name").setText(nameSb.toString());
						items.addElement("value").setText(valueSb.toString());
					}
					prop.setValue(root.asXML());
				}
				return true;
			}

			@Override
			public void initData() {

				List<Element> allitems = null;
				try {
					Element root = DocumentHelper.parseText(prop.getValue()).getRootElement();
					allitems = root.elements("items");
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
				if (allitems == null || allitems.isEmpty()) {
					return;
				}
				int i = 1;
				for (Element items : allitems) {
					String valueStr = items.elementText("value");
					if (CommonUtils.isStrEmpty(valueStr)) {
						continue;
					}
					String[] arr = valueStr.split(",");
					Object[] row = new Object[model.getColumnCount()];
					row[0] = i;
					int count = 1;
					for (String temp : arr) {
						if (!CommonUtils.isStrEmpty(temp)) {
							XMLDto item = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", temp);
							if (item != null) {
								row[count] = item;
								count++;
							}

						}
					}
					model.addRow(row);
					i++;
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}
}
