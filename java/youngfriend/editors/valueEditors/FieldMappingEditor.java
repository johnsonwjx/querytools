/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * condiMapFieldPnl.java
 *
 * Created on 2012-1-30, 15:56:34
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class FieldMappingEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private final String[] header = { "序号", "当前查询类字段", "目标查询类字段" };
	private DefaultTableModel model;
	private List<XMLDto> fields = CompUtils.getFields();
	private List<XMLDto> targets = null;
	private final int index_num = 0;
	private final int index_cur = 1;
	private final int index_target = 2;

	public FieldMappingEditor(List<XMLDto> targets) {
		this.targets = targets;
		initComponents();
	}

	public void initData() {
		String value = props.get("value");
		if (CommonUtils.isStrEmpty(value)) {
			return;
		}
		// 处理历史数据
		String[] allOldParams = value.split(",");
		if (allOldParams.length <= 0) {
			return;
		}
		int index = 1;
		for (String oldParams : allOldParams) {
			String[] oldParam = oldParams.split(":");
			if (oldParam.length != 3) {
				continue;
			}
			model.addRow(new Object[] { index, CommonUtils.getXmlDto(fields, "itemname", oldParam[2]), CommonUtils.getXmlDto(targets, "itemname", oldParam[1]) });
			index++;
		}
		CompUtils.sortTable(table, 0);
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(630, 355));
		jPanel2 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		okButton = new javax.swing.JButton();

		jButton1.setText("+");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("-");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		cancelButton.setText("取消");

		okButton.setText("确定");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				save();
			}

		});
		setLayout(new BorderLayout(0, 0));
		add(jPanel2, BorderLayout.SOUTH);
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel2.add(jButton1);
		jPanel2.add(jButton2);

		button_1 = new JButton("\u6392\u5E8F");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.sortTable(table, 0);
			}
		});
		jPanel2.add(button_1);

		button = new JButton("\u5F53\u524D\u987A\u5E8F\u8BBE\u7F6E\u5E8F\u53F7");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < model.getRowCount(); i++) {
					model.setValueAt(i + 1, i, 0);
				}
			}
		});
		jPanel2.add(button);

		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(50, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		jPanel2.add(separator);
		jPanel2.add(okButton);
		jPanel2.add(cancelButton);
		jScrollPane1 = new javax.swing.JScrollPane();
		add(jScrollPane1, BorderLayout.CENTER);
		initTable();
	}

	private void initTable() {
		model = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;
			private Class<?>[] columnClass = new Class<?>[] { Integer.class, XMLDto.class, XMLDto.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnClass[columnIndex];
			}
		};
		table = new JTable();
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = table.getColumnModel();
		CompUtils.setTableWdiths(table, 0.2);
		TableColumn c1 = cm.getColumn(1);
		TableColumn c2 = cm.getColumn(2);
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				ObjectSelectPnl<XMLDto> pnl = null;
				if (column == 1) {
					pnl = CompUtils.getFieldsPnl();
				} else {
					pnl = new ObjectSelectPnl<XMLDto>(targets);
				}
				XMLDto value = CompUtils.getCellValue(XMLDto.class, table, row, column);
				pnl.setValue(value);
				pnl.edit(dialog, props);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					table.setValueAt(value, row, column);
				}
				CompUtils.stopTabelCellEditor(table);
			}
		}, false);
		c1.setCellEditor(editor);
		c1.setCellRenderer(editor.getTableCellRenderer());
		c2.setCellEditor(editor);
		c2.setCellRenderer(editor.getTableCellRenderer());
		jScrollPane1.setViewportView(table);
	}

	private void save() {
		StringBuilder sb = new StringBuilder();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		if (rowCount > 0) {
			for (int i = 0; i < rowCount; i++) {
				Object num = model.getValueAt(i, index_num);
				Object cur = model.getValueAt(i, index_cur);
				Object target = model.getValueAt(i, index_target);
				if (num == null || cur == null || target == null || !(target instanceof XMLDto) || !(cur instanceof XMLDto)) {
					GUIUtils.showMsg(dialog, "存在空设置");
					table.setRowSelectionInterval(i, i);
					return;
				}
				sb.append(num + ":" + ((XMLDto) target).getValue("itemname") + ":" + ((XMLDto) cur).getValue("itemname") + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			props.put("value", sb.toString());
		} else {
			props.put("value", "");
		}
		submit = true;
		this.dialog.dispose();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		model.addRow(new Object[] { model.getRowCount() + 1, null, null });
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		int index = table.getSelectedRow();
		if (index < 0) {
			return;
		}
		CompUtils.stopTabelCellEditor(table);
		model.removeRow(index);
		if (model.getRowCount() > 0) {
			if (index > 0) {
				index--;
			}
			table.setRowSelectionInterval(index, index);
		}
	}

	private javax.swing.JButton cancelButton;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable table;
	private javax.swing.JButton okButton;
	private boolean submit;
	private Map<String, String> props;
	private JSeparator separator;
	private JButton button_1;
	private JButton button;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "设置字段对应", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

}
