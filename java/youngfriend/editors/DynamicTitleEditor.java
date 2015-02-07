/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.editors.valueEditors.ComIdExpEditor;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class DynamicTitleEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;
	private String[] title = new String[] { "表达式", "表达式条件", "备注" };

	public DynamicTitleEditor() {
		initComponents();
		model = (DefaultTableModel) jTable1.getModel();
		TableColumnModel colMol = jTable1.getColumnModel();
		setLayout(new BorderLayout(0, 0));
		add(jScrollPane1, BorderLayout.CENTER);
		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jButton3 = new javax.swing.JButton();
		panel.add(jButton3);

		jButton3.setText("\u589E\u52A0");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addItem();
			}
		});
		jButton4 = new javax.swing.JButton();
		panel.add(jButton4);

		jButton4.setText("\u5220\u9664");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				delItem();
			}
		});

		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(100, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator);
		jButton1 = new javax.swing.JButton();
		panel.add(jButton1);

		jButton1.setText("确定");
		jButton2 = new javax.swing.JButton();
		panel.add(jButton2);

		jButton2.setText("取消");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultpropeditor.disposeDialog();
			}
		});
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultpropeditor.save();
			}
		});
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				CompUtils.stopTabelCellEditor(jTable1);
				String value = (String) jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getSelectedColumn());
				Map<String, String> props = new HashMap<String, String>();
				props.put("value", value);
				props.put("memo", (String) jTable1.getValueAt(jTable1.getSelectedRow(), 2));
				ComIdExpEditor idEditor = new ComIdExpEditor();
				idEditor.edit(defaultpropeditor.getDialog(), props);
				if (idEditor.isSubmit()) {
					jTable1.setValueAt(props.get("value"), jTable1.getSelectedRow(), jTable1.getSelectedColumn());
					jTable1.setValueAt(props.get("memo"), jTable1.getSelectedRow(), 2);
				}
				CompUtils.stopTabelCellEditor(jTable1);
			}
		}, true);
		colMol.getColumn(0).setCellEditor(editor);
		colMol.getColumn(1).setCellEditor(editor);
		colMol.getColumn(0).setCellRenderer(editor.getTableCellRenderer());
		colMol.getColumn(1).setCellRenderer(editor.getTableCellRenderer());

	}

	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jTable1.setModel(new javax.swing.table.DefaultTableModel(title, 0) {
			private static final long serialVersionUID = 1L;
			Class<?>[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane1.setViewportView(jTable1);
	}

	private void addItem() {
		model.addRow(new Object[] {});
	}

	private void delItem() {
		int index = jTable1.getSelectedRow();
		if (index == -1) {
			GUIUtils.showMsg(defaultpropeditor.getDialog(), "请选择");
			return;
		}
		if (jTable1.getCellEditor() != null) {
			jTable1.getCellEditor().stopCellEditing();
		}
		model.removeRow(index);
		if (model.getRowCount() > 0) {
			if (index > 0) {
				index--;
			}
			jTable1.setRowSelectionInterval(index, index);
		}

	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTable1;
	private DefaultTableModel model;
	private JPanel panel;
	private JSeparator separator;
	private DefaultPropEditor defaultpropeditor;

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				CompUtils.stopTabelCellEditor(jTable1);
				String result = "";
				int addCount = 0;
				if (model.getRowCount() > 0) {
					Document doc = DocumentHelper.createDocument();
					Element root = DocumentHelper.createElement("root");
					doc.setRootElement(root);
					String expression = null;
					String expressioncondi = null;
					String memo = null;
					for (int i = 0; i < model.getRowCount(); i++) {
						Element item = root.addElement("item");
						expression = CommonUtils.coverNull((String) model.getValueAt(i, 0));
						expressioncondi = CommonUtils.coverNull((String) model.getValueAt(i, 1));
						memo = CommonUtils.coverNull((String) model.getValueAt(i, 2));
						if (CommonUtils.isStrEmpty(expression) || CommonUtils.isStrEmpty(expressioncondi)) {
							continue;
						}
						item.addElement("expression").setText(expression);
						item.addElement("expressioncondi").setText(expressioncondi);
						item.addElement("memo").setText(memo);
						addCount++;
					}
					if (root.hasContent()) {
						result = root.asXML();
					}
				}
				if (addCount > 0) {
					prop.setValue(result);
				} else {
					prop.setValue("");
				}
				return true;
			}

			@Override
			public void initData() {
				String xml = prop.getValue();
				Document doc;
				try {
					doc = DocumentHelper.parseText(xml);
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
				List<Element> items = doc.selectNodes("/root/item");
				if (items != null && items.size() > 0) {
					String expression = null;
					String expressioncondi = null;
					String memo = null;
					for (int i = 0; i < items.size(); i++) {
						Element item = items.get(i);
						expression = item.elementText("expression");
						expressioncondi = item.elementText("expressioncondi");
						memo = item.elementText("memo");
						if (i < jTable1.getRowCount()) {
							model.setValueAt(expression, i, 0);
							model.setValueAt(expressioncondi, i, 1);
							model.setValueAt(memo, i, 2);
						} else {
							model.addRow(new String[] { expression, expressioncondi, memo });
						}
					}
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}
}
