/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CodeCenterSingleTable.java
 *
 * Created on Dec 8, 2011, 11:40:40 AM
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ListChooseListPnl;
import youngfriend.gui.Lst2LstSelPnl.Action4Lst;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.ShowPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class CodeCenterSingleTableEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CodeCenterSingleTableEditor.class.getName());
	private JDialog dialog;
	private final List<XMLDto> fields = CompUtils.getFields();
	private XMLDto curCodeTable;
	private DefaultListModel lst1Model = new DefaultListModel();;
	private DefaultTableModel tableModel;
	private final String[] title = new String[] { "代码中心字段标签", "代码中心字段", "字段宽度", "是否显示", "是否返回", "对应的表单项目", "新标签" };
	private final int index_codefiedlabel = 0;
	private final int index_codefied = 1;
	private final int index_width = 2;
	private final int index_visible = 3;
	private final int index_return = 4;
	private final int index_relationfield = 5;
	private final int index_newlabel = 6;
	private List<XMLDto> codeFields = new ArrayList<XMLDto>();
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JButton btnNewButton_1;
	private JButton button;

	/**
	 * Creates new form CodeCenterSingleTable
	 */
	public CodeCenterSingleTableEditor() {
		this.setPreferredSize(new Dimension(1022, 697));
		initComponents();
	}

	private void initUI() {
		clearUI();
		if (curCodeTable == null) {
			return;
		}
		textField.setText(curCodeTable.toString());
		if (codeFields != null && !codeFields.isEmpty()) {
			for (XMLDto field : codeFields) {
				// list1
				lst1Model.addElement(field);
				// table
				tableModel.addRow(new Object[] { field.getValue("fieldlabel"), field.getValue("fieldname"), 0, false, false, "", "" });
			}
		}
	}

	private void clearUI() {
		tableModel.setRowCount(0);
		lst1Model.clear();
		othercondiTextArea.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
	}

	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();
		buttonGroup3 = new javax.swing.ButtonGroup();
		buttonGroup5 = new javax.swing.ButtonGroup();
		jPanel10 = new javax.swing.JPanel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		jPanel12 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane2.setBounds(0, 0, 852, 100);
		othercondiTextArea = new javax.swing.JTextArea();
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(0, 106, 896, 15);
		jPanel13 = new javax.swing.JPanel();
		jPanel13.setBounds(0, 127, 852, 229);
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane3.setBounds(0, 20, 303, 203);
		jList1 = new javax.swing.JList(lst1Model);
		jScrollPane4 = new javax.swing.JScrollPane();
		jScrollPane4.setBounds(309, 20, 318, 203);
		jList2 = new javax.swing.JList();
		jScrollPane5 = new javax.swing.JScrollPane();
		jScrollPane5.setBounds(633, 20, 124, 203);
		jList3 = new javax.swing.JList();
		jScrollPane6 = new javax.swing.JScrollPane();
		jScrollPane6.setBounds(763, 20, 89, 203);
		jList4 = new javax.swing.JList();
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(0, 0, 56, 16);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setBounds(309, 0, 82, 16);
		jPanel11 = new javax.swing.JPanel();

		jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("条件相关设置"));
		jPanel10.setName("jPanel10"); // NOI18N
		jPanel10.setLayout(new java.awt.BorderLayout());
		initTable();

		JPanel jpnl = new JPanel(new BorderLayout());
		jpnl.add(jScrollPane1, BorderLayout.CENTER);
		jTabbedPane1.addTab("字段设置窗口", jpnl);

		panel_1 = new JPanel();
		jpnl.add(panel_1, BorderLayout.SOUTH);

		label_2 = new JLabel("\u6392\u5E8F\u5B57\u6BB5\uFF1A");
		panel_1.add(label_2);

		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(40);

		button_1 = new JButton("\u8BBE\u7F6E\u6392\u5E8F\u5B57\u6BB5");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSortFileds();
			}
		});
		panel_1.add(button_1);

		jPanel12.setName("jPanel12"); // NOI18N

		jScrollPane2.setName("jScrollPane2"); // NOI18N

		othercondiTextArea.setColumns(20);
		othercondiTextArea.setLineWrap(true);
		othercondiTextArea.setRows(6);
		othercondiTextArea.setName("othercondiTextArea"); // NOI18N
		jScrollPane2.setViewportView(othercondiTextArea);

		jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		jLabel5.setText("\u5FC5\u987B\u7528[]\u62EC\u8D77\u6765,\u6761\u4EF6\u5B57\u6BB5\u540D\u5FC5\u987B\u7528{}\u62EC\u8D77\u6765,\u4E3E\u4F8B:1=1 [and project_code='{project_code}'] \u5982\u679C\u6709\u591A\u4E2A\u63A7\u4EF6\u5B57\u6BB5\u540D\u76F8\u540C\u8BF7\u7528\u63A7\u4EF6id {project_code} \u66FF\u6362\u6210{\u63A7\u4EF6id} \uFF1B");
		jLabel5.setName("jLabel5"); // NOI18N

		jPanel13.setName("jPanel13"); // NOI18N

		jScrollPane3.setName("jScrollPane3"); // NOI18N

		jList1.setName("jList1"); // NOI18N
		jList1.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList1MouseClicked(evt);
			}
		});
		jScrollPane3.setViewportView(jList1);

		jScrollPane4.setName("jScrollPane4"); // NOI18N
		DefaultListModel listModel2 = new DefaultListModel();
		for (XMLDto field : fields) {
			listModel2.addElement(field);
		}
		jList2.setModel(listModel2);
		jList2.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList2MouseClicked(evt);
			}
		});
		jScrollPane4.setViewportView(jList2);

		jScrollPane5.setName("jScrollPane5"); // NOI18N

		jList3.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "=", ">", ">=", "<", "<=", "!=", "like" };

			@Override
			public int getSize() {
				return strings.length;
			}

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jList3.setName("jList3"); // NOI18N
		jList3.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList3MouseClicked(evt);
			}
		});
		jScrollPane5.setViewportView(jList3);

		jScrollPane6.setName("jScrollPane6"); // NOI18N

		jList4.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "and", "or" };

			@Override
			public int getSize() {
				return strings.length;
			}

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jList4.setName("jList4"); // NOI18N
		jList4.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList4MouseClicked(evt);
			}
		});
		jScrollPane6.setViewportView(jList4);

		jLabel6.setText("源表字段:");
		jLabel6.setName("jLabel6"); // NOI18N

		jLabel7.setText("条件值表字段:");
		jLabel7.setName("jLabel7");
		jPanel13.setLayout(null);
		jPanel13.add(jScrollPane3);
		jPanel13.add(jLabel6);
		jPanel13.add(jLabel7);
		jPanel13.add(jScrollPane4);
		jPanel13.add(jScrollPane5);
		jPanel13.add(jScrollPane6);

		jTabbedPane1.addTab("数据过滤条件设置", jPanel12);
		jPanel12.setLayout(null);
		jPanel12.add(jLabel5);
		jPanel12.add(jScrollPane2);
		jPanel12.add(jPanel13);

		jPanel10.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

		jPanel11.setName("jPanel11");
		jPanel11.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		setLayout(new BorderLayout(0, 0));
		add(jPanel10, BorderLayout.CENTER);
		add(jPanel11, BorderLayout.SOUTH);
		okButton = new javax.swing.JButton();
		jPanel11.add(okButton);

		okButton.setText("确定");
		okButton.setName("okButton"); // NOI18N
		cancelButton = new javax.swing.JButton();
		jPanel11.add(cancelButton);

		cancelButton.setText("取消");
		cancelButton.setName("cancelButton"); // NOI18N

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 200));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setBounds(0, 0, 524, 183);
		panel.add(jPanel1);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(12, 22, 69, 16);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(12, 57, 56, 16);
		jTextField1 = new javax.swing.JTextField();
		jTextField1.setBounds(87, 51, 425, 28);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(12, 91, 56, 16);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(12, 125, 56, 16);
		jSpinner1 = new javax.swing.JSpinner();
		jSpinner1.setBounds(87, 85, 77, 28);
		jSpinner2 = new javax.swing.JSpinner();
		jSpinner2.setBounds(87, 119, 77, 28);
		jcbbycode = new javax.swing.JCheckBox();
		jcbbycode.setBounds(193, 104, 175, 23);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("代码中心数据表"));
		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("数据表名称:");
		jLabel1.setName("jLabel1");

		jLabel2.setText("窗口标题:");
		jLabel2.setName("jLabel2"); // NOI18N

		jTextField1.setName("jTextField1"); // NOI18N

		jLabel3.setText("默认高度:");
		jLabel3.setName("jLabel3"); // NOI18N

		jLabel4.setText("默认宽度:");
		jLabel4.setName("jLabel4"); // NOI18N

		jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(800), Integer.valueOf(0), null, Integer.valueOf(10)));
		jSpinner1.setAutoscrolls(true);
		jSpinner1.setName("jSpinner1"); // NOI18N
		jSpinner1.setRequestFocusEnabled(false);
		jSpinner1.setValue(800);

		jSpinner2.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(600), Integer.valueOf(0), null, Integer.valueOf(10)));
		jSpinner2.setName("jSpinner2"); // NOI18N
		jSpinner2.setValue(600);

		jcbbycode.setText("按代码中心编码格式建树");
		jcbbycode.setName("jcbbycode");
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel4);
		jPanel1.add(jLabel3);
		jPanel1.add(jTextField1);
		jPanel1.add(jSpinner1);
		jPanel1.add(jSpinner2);
		jPanel1.add(jcbbycode);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(89, 16, 357, 28);
		jPanel1.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getCodeTablePnl(curCodeTable);
				pnl.edit(dialog, null);
				if (!pnl.isChange()) {
					return;
				}
				curCodeTable = pnl.getSelect();
				if (curCodeTable == null) {
					codeFields.clear();
				} else {
					try {
						codeFields = InvokerServiceUtils.getCodeFields(curCodeTable.getValue("id"));
					} catch (Exception ex) {
						GUIUtils.showMsg(dialog, "获取代码中心字段失败");
						logger.error(ex.getMessage(), ex);
					}
				}
				initUI();

			}
		});
		btnNewButton.setBounds(445, 17, 56, 29);
		jPanel1.add(btnNewButton);
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(524, 0, 492, 183);
		panel.add(jPanel2);
		jPanel3 = new javax.swing.JPanel();
		jPanel3.setBounds(6, 18, 96, 76);
		jRadioButton1 = new javax.swing.JRadioButton();
		jRadioButton1.setBounds(6, 18, 84, 23);
		jRadioButton2 = new javax.swing.JRadioButton();
		jRadioButton2.setBounds(6, 47, 84, 23);
		jPanel4 = new javax.swing.JPanel();
		jPanel4.setBounds(108, 18, 109, 76);
		jRadioButton3 = new javax.swing.JRadioButton();
		jRadioButton3.setBounds(6, 18, 84, 23);
		jRadioButton4 = new javax.swing.JRadioButton();
		jRadioButton4.setBounds(6, 47, 97, 23);
		jPanel5 = new javax.swing.JPanel();
		jPanel5.setBounds(223, 18, 126, 76);
		jRadioButton5 = new javax.swing.JRadioButton();
		jRadioButton5.setBounds(6, 18, 58, 23);
		jRadioButton6 = new javax.swing.JRadioButton();
		jRadioButton6.setBounds(6, 47, 58, 23);
		jPanel7 = new javax.swing.JPanel();
		jPanel7.setBounds(361, 18, 102, 76);
		jRadioButton9 = new javax.swing.JRadioButton();
		jRadioButton9.setBounds(6, 18, 84, 23);
		jRadioButton10 = new javax.swing.JRadioButton();
		jRadioButton10.setBounds(6, 47, 84, 23);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("参数设置"));
		jPanel2.setName("jPanel2"); // NOI18N

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("显示方式"));
		jPanel3.setName("jPanel3"); // NOI18N

		buttonGroup1.add(jRadioButton1);
		jRadioButton1.setSelected(true);
		jRadioButton1.setText("列表显示");
		jRadioButton1.setName("jRadioButton1"); // NOI18N

		buttonGroup1.add(jRadioButton2);
		jRadioButton2.setText("树状显示");
		jRadioButton2.setName("jRadioButton2"); // NOI18N
		jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jRadioButton2ItemStateChanged(evt);
			}
		});

		jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("建树方式"));
		jPanel4.setName("jPanel4"); // NOI18N

		buttonGroup2.add(jRadioButton3);
		jRadioButton3.setText("分批取数");
		jRadioButton3.setEnabled(false);
		jRadioButton3.setName("jRadioButton3"); // NOI18N

		buttonGroup2.add(jRadioButton4);
		jRadioButton4.setSelected(true);
		jRadioButton4.setText("一次性取数");
		jRadioButton4.setEnabled(false);
		jRadioButton4.setName("jRadioButton4");

		jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("选择方式"));
		jPanel5.setName("jPanel5"); // NOI18N

		buttonGroup3.add(jRadioButton5);
		jRadioButton5.setSelected(true);
		jRadioButton5.setText("单选");
		jRadioButton5.setName("jRadioButton5"); // NOI18N

		buttonGroup3.add(jRadioButton6);
		jRadioButton6.setText("多选");
		jRadioButton6.setName("jRadioButton6");

		jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("返回范围"));
		jPanel7.setName("jPanel7"); // NOI18N

		buttonGroup5.add(jRadioButton9);
		jRadioButton9.setText("返回底层");
		jRadioButton9.setEnabled(false);
		jRadioButton9.setName("jRadioButton9"); // NOI18N

		buttonGroup5.add(jRadioButton10);
		jRadioButton10.setSelected(true);
		jRadioButton10.setText("返回所有");
		jRadioButton10.setEnabled(false);
		jRadioButton10.setName("jRadioButton10");
		jPanel2.setLayout(null);
		jPanel2.add(jPanel7);
		jPanel7.setLayout(null);
		jPanel7.add(jRadioButton9);
		jPanel7.add(jRadioButton10);
		jPanel2.add(jPanel3);
		jPanel3.setLayout(null);
		jPanel3.add(jRadioButton1);
		jPanel3.add(jRadioButton2);
		jPanel2.add(jPanel4);
		jPanel4.setLayout(null);
		jPanel4.add(jRadioButton3);
		jPanel4.add(jRadioButton4);
		jPanel2.add(jPanel5);
		jPanel5.setLayout(null);
		jPanel5.add(jRadioButton5);
		jPanel5.add(jRadioButton6);

		checkBox = new JCheckBox("\u663E\u793A\u4EE3\u7801");
		checkBox.setEnabled(false);
		checkBox.setBounds(6, 153, 96, 23);
		jPanel2.add(checkBox);

		checkBox_1 = new JCheckBox("\u5C55\u5F00\u5168\u90E8");
		checkBox_1.setEnabled(false);
		checkBox_1.setBounds(6, 131, 84, 23);
		jPanel2.add(checkBox_1);

		checkBox_2 = new JCheckBox("\u662F\u5426\u5206\u9875");
		checkBox_2.setSelected(true);
		checkBox_2.setBounds(6, 106, 84, 23);
		jPanel2.add(checkBox_2);

		checkBox_3 = new JCheckBox("\u662F\u5426\u53D6\u4E0A\u7EA7");
		checkBox_3.setVisible(false);
		checkBox_3.setBounds(94, 106, 105, 23);
		jPanel2.add(checkBox_3);

		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setBounds(264, 104, 182, 28);
		jPanel2.add(textField_1);
		textField_1.setColumns(10);

		JLabel label = new JLabel("\u5EFA\u6811\u5B57\u6BB5");
		label.setBounds(211, 110, 63, 16);
		jPanel2.add(label);

		JLabel label_1 = new JLabel("\u6811\u663E\u793A\u5B57\u6BB5");
		label_1.setBounds(198, 153, 73, 16);
		jPanel2.add(label_1);

		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setColumns(10);
		textField_2.setBounds(264, 147, 182, 28);
		jPanel2.add(textField_2);

		btnNewButton_1 = new JButton("...");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBuilTreeField();
			}

		});
		btnNewButton_1.setBounds(446, 107, 22, 23);
		jPanel2.add(btnNewButton_1);

		button = new JButton("...");
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTreeNameFiled();
			}

		});
		button.setBounds(446, 150, 22, 23);
		jPanel2.add(button);
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				save();
			}
		});
	}// </editor-fold>//GEN-END:initComponents

	private void initTable() {
		jTable1 = new javax.swing.JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				Boolean choose = true;
				if (column == index_codefied || index_relationfield == column && !choose.equals(jTable1.getValueAt(row, index_return)) || column == index_visible && jRadioButton2.isSelected()) {
					return false;
				}
				if ((index_codefiedlabel == column || index_width == column || index_newlabel == column) && !choose.equals(jTable1.getValueAt(row, index_visible))) {
					return false;
				}

				return super.isCellEditable(row, column);
			}
		};
		tableModel = new DefaultTableModel(title, 0) {
			private static final long serialVersionUID = 1L;
			Class<?>[] types = new Class[] { String.class, String.class, Integer.class, Boolean.class, Boolean.class, String.class, java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};

		jTable1.setModel(tableModel);
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(jTable1);
				int row = jTable1.getSelectedRow();
				int column = jTable1.getSelectedColumn();
				String itemname = (String) tableModel.getValueAt(row, column);
				XMLDto value = CommonUtils.getXmlDto(fields, "itemname", itemname);
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				pnl.edit(dialog, null);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					if (value != null) {
						tableModel.setValueAt(value.getValue("itemname"), row, column);
					} else {
						tableModel.setValueAt("", row, column);
					}
				}
			}
		}, true);
		TableColumnModel cm = jTable1.getColumnModel();
		cm.getColumn(index_relationfield).setCellRenderer(editor.getTableCellRenderer());
		cm.getColumn(index_relationfield).setCellEditor(editor);

		CompUtils.setTableWdiths(jTable1, null, null, 0.11, 0.12, 0.12, null, 0.2);
		jTable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = jTable1.rowAtPoint(e.getPoint());
				int col = jTable1.columnAtPoint(e.getPoint());
				Boolean choose = true;
				if (col == index_visible) {
					if (choose.equals(jTable1.getValueAt(row, index_visible))) {
						jTable1.setValueAt(80, row, index_width);
					} else {
						jTable1.setValueAt(0, row, index_width);

					}
				}
				if (col == index_return) {
					if (!choose.equals(jTable1.getValueAt(row, index_return))) {
						jTable1.setValueAt("", row, index_relationfield);

					}
				}
				for (int i = 0; i < jTable1.getRowCount(); i++) {
					if (choose.equals(jTable1.getValueAt(i, index_return)) && CommonUtils.isStrEmpty((String) jTable1.getValueAt(i, index_relationfield)) && row != i) {
						jTable1.setRowSelectionInterval(i, i);
						GUIUtils.showMsg(dialog, "请选择[对应的表单项目]");
						Class<?> clazz = tableModel.getColumnClass(col);
						if (clazz.equals(Boolean.class)) {
							tableModel.setValueAt(!(Boolean) tableModel.getValueAt(row, col), row, col);
						}
						return;
					}
				}

			}
		});

		jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(jTable1);
	}

	private void setSortFileds() {
		List<XMLDto> all = new ArrayList<XMLDto>();
		List<String> toStringPros = Arrays.asList("itemlabel", "itemname", "direc");
		for (XMLDto f : codeFields) {
			XMLDto dto = new XMLDto(toStringPros);
			dto.setValue("itemlabel", f.getValue("fieldlabel"));
			dto.setValue("itemname", f.getValue("fieldname"));
			dto.setValue("direc", "");
			all.add(dto);
		}
		Collection<XMLDto> values = new ArrayList<XMLDto>();
		String value = textField_2.getText();
		if (!CommonUtils.isStrEmpty(value)) {
			String[] temp = value.split(",");
			if (temp.length > 0) {
				String ingore = "";
				for (String t : temp) {
					boolean desc = false;
					int index = t.toLowerCase().indexOf("desc");
					String itemnameStr = "";
					if (index > 0) {
						itemnameStr = t.substring(0, index);
						desc = true;
					} else {
						index = t.toLowerCase().indexOf("asc");
						if (index > 0) {
							itemnameStr = t.substring(0, index);
						} else {
							itemnameStr = t;
						}
					}
					XMLDto obj = CommonUtils.getXmlDto(all, "itemname", itemnameStr.trim());
					if (obj == null) {
						if (CommonUtils.isStrEmpty(ingore)) {
							ingore += itemnameStr;
						} else {
							ingore += "," + itemnameStr;
						}
						continue;
					}
					if (desc) {
						obj.setValue("direc", " DESC");
					}
					if (obj != null) {
						values.add(obj);
					}
				}
				if (!CommonUtils.isStrEmpty(ingore)) {
					new ShowPnl(dialog, 400, 50, ingore + "无效字段");
				}
			}
		}
		ListChooseListPnl<XMLDto> pnl = new ListChooseListPnl<XMLDto>(dialog, "排序字段", all, new Action4Lst() {
			@Override
			public void do4Lst(JList list) {
				Object[] selects = list.getSelectedValues();
				if (selects.length <= 0) {
					return;
				}
				for (Object obj : selects) {
					XMLDto dto = (XMLDto) obj;
					if ("DESC".equalsIgnoreCase(dto.getValue("direc"))) {
						dto.setValue("direc", "");
					} else {
						dto.setValue("direc", " DESC");
					}
					list.updateUI();
				}
			}

			@Override
			public String getTitle() {
				return "改变正倒序";
			}
		});
		pnl.setValues(values);
		pnl.edit(dialog, null);
		if (!pnl.isSubmit()) {
			return;
		}
		values = pnl.getValues();
		if (values == null || values.isEmpty()) {
			textField.setText("");
		} else {
			StringBuilder sb = new StringBuilder();
			for (XMLDto dto : values) {
				sb.append(dto.getValue("itemname")).append(dto.getValue("direc")).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			textField_3.setText(sb.toString());
		}

	}

	private void jRadioButton2ItemStateChanged(java.awt.event.ItemEvent evt) {
		boolean flag = evt.getStateChange() == ItemEvent.SELECTED;
		jRadioButton3.setEnabled(flag);
		jRadioButton4.setEnabled(flag);
		jRadioButton9.setEnabled(flag);
		jRadioButton10.setEnabled(flag);
		checkBox_1.setEnabled(flag);
		checkBox.setEnabled(flag);
		checkBox_2.setEnabled(!flag);
		checkBox_3.setVisible(flag);
		checkBox_3.setSelected(flag);
		textField_1.setEnabled(flag);
		textField_2.setEnabled(flag);
		if (!flag) {
			textField_1.setText("");
			textField_2.setText("");
		}
		btnNewButton_1.setEnabled(flag);
		button.setEnabled(flag);
		textField_3.setEnabled(!flag);
		if (flag) {
			textField_3.setText("");
		}
		button_1.setEnabled(!flag);
	}

	private void jList1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jList1MouseClicked
		if (evt.getClickCount() == 2) {
			int index = jList1.locationToIndex(evt.getPoint());
			if (index >= 0) {
				XMLDto item = (XMLDto) lst1Model.getElementAt(index);
				CompUtils.textareaInsertText(othercondiTextArea, " " + item.getValue("fieldname"));
			}

		}
	}

	private void jList2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jList2MouseClicked
		if (evt.getClickCount() == 2) {
			int index = jList2.locationToIndex(evt.getPoint());
			if (index >= 0) {
				XMLDto item = (XMLDto) jList2.getModel().getElementAt(index);
				CompUtils.textareaInsertText(othercondiTextArea, " " + item.getValue("itemname"));
			}

		}
	}

	private void jList3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jList3MouseClicked
		if (evt.getClickCount() == 2) {
			int index = jList3.locationToIndex(evt.getPoint());
			if (index >= 0) {
				Object item = jList3.getModel().getElementAt(index);
				String items = item.toString();
				CompUtils.textareaInsertText(othercondiTextArea, " " + items);
			}
		}
	}// GEN-LAST:event_jList3MouseClicked

	private void jList4MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jList4MouseClicked
		if (evt.getClickCount() == 2) {
			int index = jList4.locationToIndex(evt.getPoint());
			if (index >= 0) {
				Object item = jList4.getModel().getElementAt(index);
				CompUtils.textareaInsertText(othercondiTextArea, " " + item.toString() + " ");
			}
		}
	}

	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.ButtonGroup buttonGroup3;
	private javax.swing.ButtonGroup buttonGroup5;
	private javax.swing.JButton cancelButton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JList jList1;
	private javax.swing.JList jList2;
	private javax.swing.JList jList3;
	private javax.swing.JList jList4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton10;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JRadioButton jRadioButton3;
	private javax.swing.JRadioButton jRadioButton4;
	private javax.swing.JRadioButton jRadioButton5;
	private javax.swing.JRadioButton jRadioButton6;
	private javax.swing.JRadioButton jRadioButton9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JSpinner jSpinner1;
	private javax.swing.JSpinner jSpinner2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JCheckBox jcbbycode;
	private javax.swing.JButton okButton;
	private javax.swing.JTextArea othercondiTextArea;
	private JTextField textField;
	private boolean submit = false;
	private Map<String, String> props;
	private JCheckBox checkBox;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPanel panel_1;
	private JLabel label_2;
	private JTextField textField_3;
	private JButton button_1;

	private String selectCodeField(String valueStr) {
		if (codeFields == null || codeFields.isEmpty()) {
			GUIUtils.showMsg(dialog, "字段为空");
			return null;
		}
		ObjectSelectPnl<XMLDto> editor = new ObjectSelectPnl<XMLDto>(codeFields);
		XMLDto value;
		if (!CommonUtils.isStrEmpty(valueStr, true)) {
			value = CommonUtils.getXmlDto(codeFields, "fieldname", valueStr);
			editor.setValue(value);
		}
		editor.edit(dialog, null);
		if (editor.isChange()) {
			value = editor.getSelect();
			if (editor.isNull()) {
				return "";
			} else {
				return value.getValue("fieldname");
			}
		}
		return null;
	}

	private void setBuilTreeField() {
		String value = selectCodeField(textField_1.getText());
		if (value == null) {
			return;
		}
		textField_1.setText(value);
	}

	private void setTreeNameFiled() {
		String value = selectCodeField(textField_2.getText());
		if (value == null) {
			return;
		}
		textField_2.setText(value);

	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "代码中心单表设置", this);
		dialog.setVisible(true);
	}

	private void save() {
		try {
			CompUtils.stopTabelCellEditor(jTable1);
			StringBuffer outparam = new StringBuffer();
			String alias = "";
			if (curCodeTable != null) {
				alias = curCodeTable.getValue("alias");
			}
			String isMultiPage = checkBox_2.isSelected() ? "0" : "1";
			String selectType = jRadioButton5.isSelected() ? "0" : "1";// 单多选
			String getDataTime = jRadioButton3.isSelected() ? "0" : "1";// 建树方式
			String isBottom = jRadioButton9.isSelected() ? "0" : "1";// 返回范围
			String showType = jRadioButton1.isSelected() ? "0" : "1";// 列表或者树状显示
			String expendAll = checkBox_1.isSelected() ? "0" : "1";
			String showCode = checkBox.isSelected() ? "0" : "1";
			String bycodeparam = jcbbycode.isSelected() ? "1" : "0";// 是否按代码中心编码方式建树
			String hasparent = "F";
			if (jRadioButton2.isSelected()) {
				hasparent = checkBox_3.isSelected() ? "T" : "F";
			}
			String width, height = "";
			height = String.valueOf(jSpinner1.getValue());
			width = String.valueOf(jSpinner2.getValue());// 表宽和高
			String winTitle = jTextField1.getText(); // 窗口title

			StringBuffer field = new StringBuffer();
			Boolean choose = true;
			for (int i = 0; i < jTable1.getRowCount(); i++) {
				if (!choose.equals(jTable1.getValueAt(i, index_visible)) && !choose.equals(jTable1.getValueAt(i, index_return))) {
					continue;
				}
				String fieldname = (String) jTable1.getValueAt(i, index_codefied);
				String fieldlabel = (String) jTable1.getValueAt(i, index_codefiedlabel);
				if (CommonUtils.isStrEmpty(fieldname)) {
					continue;
				}
				if (choose.equals(jTable1.getValueAt(i, index_visible))) {
					field.append(fieldlabel).append("\\b").append(fieldname).append("\\b").append(jTable1.getValueAt(i, index_width)).append("\\byes");
				} else {
					field.append(fieldlabel).append("\\b").append(fieldname).append("\\b\\bno");
				}
				if (choose.equals(jTable1.getValueAt(i, index_return))) {
					XMLDto relationField = CommonUtils.getXmlDto(fields, "itemname", (String) jTable1.getValueAt(i, index_relationfield));
					if (relationField == null) {
						GUIUtils.showMsg(dialog, "请选择[对应的表单项目]");
						jTable1.setRowSelectionInterval(i, i);
						return;

					}
					field.append("\\byes\\b").append(relationField.getValue("itemname")).append("\\c").append(relationField.getValue("itemlabel"));
					outparam.append(relationField.getValue("itemname")).append("=").append(fieldname).append(":").append(fieldlabel).append(",");
				} else {
					field.append("\\bno\\b");
				}
				field.append("\\b");
				field.append(CommonUtils.coverNull((String) jTable1.getValueAt(i, index_newlabel))).append("\\b");
				field.append(",");
			}
			if (field.indexOf(",") > 0) {
				int del = field.lastIndexOf(",");
				field.delete(del, del + 1);
			}
			if (outparam.indexOf(",") > 0) {
				int del = outparam.lastIndexOf(",");
				outparam.delete(del, del + 1);
			}

			String othercondi = othercondiTextArea.getText().replaceAll("\\n", " ");
			StringBuilder inParam = new StringBuilder();
			inParam.append("alias=").append(alias).append(";");
			inParam.append("isMultiPage=").append(isMultiPage).append(";");
			inParam.append("selectType=").append(selectType).append(";");
			inParam.append("getDataTime=").append(getDataTime).append(";");
			inParam.append("isBottom=").append(isBottom).append(";");
			inParam.append("showType=").append(showType).append(";");
			inParam.append("expendAll=").append(expendAll).append(";");
			inParam.append("showCode=").append(showCode).append(";");
			inParam.append("bycodeparam=").append(bycodeparam).append(";");
			inParam.append("hasparent=").append(hasparent).append(";");
			inParam.append("windowparam=formtitle:").append(winTitle);
			inParam.append("||width:").append(width);
			inParam.append("||height:").append(height);
			inParam.append("||field:").append(field).append(";");
			inParam.append("othcondi=").append(CommonUtils.base64Encode(othercondi.getBytes())).append(";");
			inParam.append("buildTreeField=").append(textField_1.getText()).append(";");
			inParam.append("orderfields=").append(textField_3.getText().trim()).append(";");
			inParam.append("treeNameField=").append(textField_2.getText());

			this.props.put("inparam", inParam.toString());
			this.props.put("outparam", outparam.toString());
			submit = true;
			dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}

	}

	private void initData() {
		try {
			String inparam = props.get("inparam");
			if (CommonUtils.isStrEmpty(inparam)) {
				return;
			}
			HashMap<String, String> paramMap = CommonUtils.paramStrToMap(inparam);
			if (paramMap.isEmpty()) {
				return;
			}
			String alias = paramMap.get("alias");
			if (CommonUtils.isStrEmpty(alias)) {
				return;
			}
			if (alias != null) {
				if (alias.indexOf(",") > 0) {
					return;
				}
			} // 取得的表名为主从表的表名,取“”
			curCodeTable = CommonUtils.getXmlDto(CompUtils.getCodeTables(), "alias", alias);
			if (curCodeTable == null) {
				GUIUtils.showMsg(dialog, alias + "代码中心表不存在");
				codeFields.clear();
				return;
			}
			try {
				codeFields = InvokerServiceUtils.getCodeFields(curCodeTable.getValue("id"));
			} catch (Exception ex) {
				GUIUtils.showMsg(dialog, "获取代码中心字段失败");
				logger.error(ex.getMessage(), ex);
			}

			initUI();
			String othcondi = paramMap.get("othcondi");
			if (!CommonUtils.isStrEmpty(othcondi)) {
				othercondiTextArea.setText(new String(CommonUtils.base64Dcode(othcondi)));
			}
			String windowparam = paramMap.get("windowparam");
			if (!CommonUtils.isStrEmpty(windowparam)) {
				String[] params = windowparam.split("[||]");
				for (String param : params) {// 分割参数
					if (!"".equals(param)) {
						String[] keyValue = param.split(":", 2);
						if (keyValue.length < 2) {
							paramMap.put(keyValue[0], "");
						} else {
							paramMap.put(keyValue[0], keyValue[1]);
						}
					}
				}
				jTextField1.setText(paramMap.get("formtitle"));// 标题重置
				jSpinner1.setValue(Integer.parseInt(paramMap.get("height")));// 高度重置
				jSpinner2.setValue(Integer.parseInt(paramMap.get("width")));// 宽度重置
			}
			if (codeFields != null && !codeFields.isEmpty()) {
				String field = paramMap.get("field");// 利用参数对表格区域的初始化
				if (!CommonUtils.isStrEmpty(field)) {
					String[] fields = field.split(",");
					for (String field1 : fields) {
						if (CommonUtils.isStrEmpty(field1)) {
							continue;
						}
						String[] field2 = field1.split("\\\\b");
						String fieldname = field2[1];
						XMLDto fieldItem = CommonUtils.getXmlDto(codeFields, "fieldname", fieldname);
						int index = codeFields.indexOf(fieldItem);
						if (index < 0) {
							continue;
						}
						if ("yes".equals(field2[3])) {
							jTable1.setValueAt(true, index, index_visible);
							jTable1.setValueAt(field2[0], index, index_codefiedlabel);
							String temp = field2[2];
							int width = 80;
							if (CommonUtils.isNumberString(temp)) {
								width = Integer.parseInt(temp);
							}
							jTable1.setValueAt(width, index, index_width);
							if (field2.length == 7) {
								jTable1.setValueAt(field2[6], index, index_newlabel);
							}
						}
						if ("yes".equals(field2[4])) {
							jTable1.setValueAt(true, index, index_return);
							if (!CommonUtils.isStrEmpty(field2[5])) {
								String[] field3 = field2[5].split("\\\\c");
								jTable1.setValueAt(field3[0], index, index_relationfield);
							}
						}
					}
				}

			}

			// radio

			String isMultiPage = paramMap.get("isMultiPage");
			String selectType = paramMap.get("selectType");
			String showCode = paramMap.get("showCode");
			String isBottom = paramMap.get("isBottom");
			String getDataTime = paramMap.get("getDataTime");
			String expendAll = paramMap.get("expendAll");
			String showType = paramMap.get("showType");
			String bycodeparam = paramMap.get("bycodeparam");
			String hasparent = paramMap.get("hasparent");
			textField_1.setText(paramMap.get("buildTreeField"));
			textField_2.setText(paramMap.get("treeNameField"));
			if (!"0".equals(isMultiPage)) {
				checkBox_2.setSelected(false);
			}
			if ("1".equals(selectType)) {
				jRadioButton6.setSelected(true);
			} else {
				jRadioButton5.setSelected(true);
			}
			if ("0".equals(showCode)) {
				checkBox.setSelected(true);
			}
			if ("1".equals(isBottom)) {
				jRadioButton10.setSelected(true);
			} else {
				jRadioButton9.setSelected(true);
			}
			if ("1".equals(getDataTime)) {
				jRadioButton4.setSelected(true);
			} else {
				jRadioButton3.setSelected(true);
			}
			if ("0".equals(expendAll)) {
				checkBox_1.setSelected(true);
			}
			if ("1".equals(showType)) {
				jRadioButton2.setSelected(true);
			} else {
				jRadioButton1.setSelected(true);
				String orderfields = paramMap.get("orderfields");
				if (CommonUtils.isStrEmpty(orderfields, true)) {
					String orderbyflag = paramMap.get("orderbyflag");
					if ("DESC".equalsIgnoreCase(orderbyflag)) {
						String[] arr = orderfields.split(",");
						if (arr.length > 0) {
							orderfields = "";
							for (String str : arr) {
								orderfields += (str + " DESC,");
							}
							orderfields = orderfields.substring(0, orderfields.length() - 1);
						}
					}
				}

				if (!CommonUtils.isStrEmpty(orderfields)) {
					textField_3.setText(orderfields);
				}
			}
			if ("1".equals(bycodeparam)) {
				jcbbycode.setSelected(true);
			} else {
				jcbbycode.setSelected(false);
			}

			if ("T".equalsIgnoreCase(hasparent)) {
				checkBox_3.setSelected(true);
			} else {
				checkBox_3.setSelected(false);
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化失败");
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
