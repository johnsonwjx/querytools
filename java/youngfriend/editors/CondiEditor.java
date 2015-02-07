/*
 * ChooseDatatablele.java
 *
 * Created on 2007年8月2日, 上午11:22
 */

package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class CondiEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	public CondiEditor() {
		initComponents();
	}

	private String[] Titles = new String[] { "条件界面字段", "操作符", "条件值" };
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
				model.addRow(new Object[] {});
			}
		});
		panel.add(button);

		button_1 = new JButton("\u5220\u9664");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				if (rows.length <= 0) {
					return;
				}
				CompUtils.stopTabelCellEditor(table);
				Arrays.sort(rows);
				int rmoveCount = 0;
				for (int row : rows) {
					model.removeRow(row - rmoveCount);
					rmoveCount++;
				}
			}
		});
		panel.add(button_1);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(200, 0));
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
		inittable();
	}

	private void inittable() {
		JComboBox cboper = new JComboBox();
		cboper.removeAllItems();
		cboper.addItem("左匹配");
		cboper.addItem("等于");
		cboper.addItem("不等于");

		model = new DefaultTableModel(Titles, 0);
		table = new JTable(model);
		table.setColumnSelectionAllowed(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		ButtonCellEditor cellEditor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(table);
				List<XMLDto> all = CompUtils.getFields();
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				XMLDto value = CommonUtils.getXmlDto(all, "itemname", (String) model.getValueAt(row, column));
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				pnl.edit(defaultpropeditor.getDialog(), null);
				value = pnl.getSelect();
				if (pnl.isChange()) {
					if (pnl.isNull()) {
						table.setValueAt("", row, column);
					} else {
						table.setValueAt(value.getValue("itemname"), row, column);
					}
				}
			}
		}, true);
		table.getColumnModel().getColumn(0).setMaxWidth(1000);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setCellEditor(cellEditor);
		table.getColumnModel().getColumn(0).setCellRenderer(cellEditor.getTableCellRenderer());
		table.getColumnModel().getColumn(1).setMaxWidth(2000);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cboper));
		sp.setViewportView(table);
	}

	private JScrollPane sp;
	private JPanel panel;
	private JButton button;
	private JButton button_1;
	private JSeparator separator;
	private JButton button_2;
	private JButton button_3;
	private DefaultPropEditor defaultpropeditor;

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				StringBuilder sb = new StringBuilder();
				int count = model.getRowCount();
				for (int i = 0; i < count; i++) {
					String field = (String) model.getValueAt(i, 0);
					XMLDto item = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", field);
					if (item != null) {
						field = item.getValue("itemname");
						String itype = item.getValue("itemtype");
						String oper = (String) model.getValueAt(i, 1);
						if ("左匹配".equals(oper)) {
							oper = "llike";
						} else if ("不等于".equals(oper)) {
							oper = "<>";
						} else {
							oper = "=";
						}
						String value = (String) model.getValueAt(i, 2);
						sb.append(field).append(",").append(itype).append(",").append(oper).append(",").append(value).append(";");
					}

				}
				if (sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1);
					prop.setValue(sb.toString());
				}
				return true;
			}

			@Override
			public void initData() {
				String[] condis = prop.getValue().split(";");
				for (int i = 0; i < condis.length; i++) {
					String[] arr = condis[i].split(",");
					String field = arr[0];
					String oper = arr[2];
					String value = arr[3];
					Object[] obj = new Object[3];
					obj[0] = null;
					if (oper.equals("llike"))
						oper = "左匹配";
					if (oper.equals("="))
						oper = "等于";
					if (oper.equals("<>"))
						oper = "不等于";
					model.addRow(new Object[] { field, oper, value });
				}
				table.setRowSelectionInterval(0, 0);

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
