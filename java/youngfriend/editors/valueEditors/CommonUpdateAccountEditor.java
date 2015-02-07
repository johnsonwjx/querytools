/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CommonUpdateAccount.java
 *
 * Created on Dec 13, 2011, 9:15:42 AM
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

import javax.swing.JCheckBox;
import java.awt.Color;

public class CommonUpdateAccountEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton;
	private String service;

	public CommonUpdateAccountEditor() {
		initComponents();
		init();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(997, 679));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		button = new JButton("\u6E05\u7A7A\u8BBE\u7F6E");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearUI();
			}
		});

		checkBox = new JCheckBox("\u540C\u65F6\u66F4\u65B0\u6761\u5F62\u7801\u6570\u636E");
		checkBox.setForeground(Color.RED);
		panel.add(checkBox);
		panel.add(button);
		jButton1 = new javax.swing.JButton();
		panel.add(jButton1);
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addParamtoList();
			}
		});

		jButton1.setText("保存当前设置到右边列表");
		jButton1.setName("jButton1");

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(400, 0));
		panel.add(separator);
		jButton3 = new javax.swing.JButton();
		jButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel.add(jButton3);

		jButton3.setText("确定");
		jButton3.setName("jButton3");
		jButton2 = new javax.swing.JButton();
		panel.add(jButton2);
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		jButton2.setText("取消");
		jButton2.setName("jButton2");

		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setPreferredSize(new Dimension(0, 80));
		panel_1.add(jPanel2, BorderLayout.NORTH);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(210, 11, 56, 16);
		jTextField1 = new javax.swing.JTextField();
		jTextField1.setBounds(267, 5, 206, 28);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(16, 46, 92, 16);
		jTextField2 = new javax.swing.JTextField();
		jTextField2.setEditable(false);
		jTextField2.setBounds(109, 40, 364, 28);

		jPanel2.setName("jPanel2"); // NOI18N

		jLabel1.setText("服务名称:");
		jLabel1.setName("jLabel1"); // NOI18N

		jTextField1.setName("jTextField1"); // NOI18N

		jLabel2.setText("\u9700\u8981\u66F4\u65B0\u7684\u8868:");
		jLabel2.setName("jLabel2"); // NOI18N

		jTextField2.setName("jTextField2");
		jPanel2.setLayout(null);
		jPanel2.add(jLabel1);
		jPanel2.add(jTextField1);
		jPanel2.add(jLabel2);
		jPanel2.add(jTextField2);
		ButtonGroup bg = new ButtonGroup();

		button_3 = new JButton("...");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectTable();
				} catch (Exception e1) {
					GUIUtils.showMsg(dialog, "选择表格出错");
					logger.error(e1.getMessage(), e1);
				}
			}
		});
		button_3.setBounds(486, 41, 68, 29);
		jPanel2.add(button_3);

		btnNewButton_1 = new JButton("...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTablesByService();
			}
		});
		btnNewButton_1.setBounds(476, 5, 60, 29);
		jPanel2.add(btnNewButton_1);

		panel_8 = new JPanel();
		panel_8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_8.setBounds(16, 2, 182, 34);
		jPanel2.add(panel_8);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));

		radioButton = new JRadioButton("\u670D\u52A1");
		panel_8.add(radioButton);
		radioButton.setSelected(true);
		bg.add(radioButton);
		radioButton_1 = new JRadioButton("\u4EE3\u7801\u4E2D\u5FC3");
		panel_8.add(radioButton_1);
		bg.add(radioButton_1);

		panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		jPanel4 = new javax.swing.JPanel();

		jPanel5 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable2 = new javax.swing.JTable();
		jPanel6 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("条件信息"));
		jPanel4.setName("jPanel4"); // NOI18N

		jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("动态条件"));
		jPanel5.setName("jPanel5"); // NOI18N

		jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane2.setName("jScrollPane2"); // NOI18N
		jTable2.setName("jTable2");
		jScrollPane2.setViewportView(jTable2);

		jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("固定条件(SQL语句的where部分)"));
		jPanel6.setName("jPanel6"); // NOI18N

		jScrollPane3.setName("jScrollPane3"); // NOI18N

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jTextArea1.setName("jTextArea1"); // NOI18N
		jScrollPane3.setViewportView(jTextArea1);
		jPanel4.setLayout(new BorderLayout(0, 0));
		jPanel5.setLayout(new BorderLayout(0, 0));
		jPanel5.add(jScrollPane2);
		jPanel4.add(jPanel5);

		panel_3 = new JPanel();
		jPanel5.add(panel_3, BorderLayout.SOUTH);
		jButton15 = new javax.swing.JButton();
		panel_3.add(jButton15);
		jButton15.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRowTable2();
			}
		});

		jButton15.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jButton15.setText("+");
		jButton15.setName("jButton15"); // NOI18N
		jButton14 = new javax.swing.JButton();
		panel_3.add(jButton14);

		jButton14.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jButton14.setText("-");
		jButton14.setName("jButton14"); // NOI18N
		jCheckBox2 = new javax.swing.JCheckBox();
		panel_3.add(jCheckBox2);

		jCheckBox2.setText("\u66F4\u65B0\u68C0\u7D22\u5230\u7684\u6240\u6709\u8BB0\u5F55");
		jCheckBox2.setName("jCheckBox2");
		jPanel4.add(jPanel6, BorderLayout.EAST);
		jPanel6.setLayout(new BorderLayout(0, 0));
		jPanel6.add(jScrollPane3);
		jPanel3 = new javax.swing.JPanel();
		panel_2.add(jPanel3);
		panel_2.add(jPanel4);
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("更新字段信息"));
		jPanel3.setName("jPanel3"); // NOI18N

		jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane1.setName("jScrollPane1"); // NOI18N
		jTable1.setName("jTable1");
		jScrollPane1.setViewportView(jTable1);
		jPanel3.setLayout(new BorderLayout(0, 0));
		jPanel3.add(jScrollPane1);

		panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		jPanel3.add(panel_4, BorderLayout.SOUTH);
		jButton8 = new javax.swing.JButton();
		panel_4.add(jButton8);
		jButton8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRowTable1();
			}
		});

		jButton8.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jButton8.setText("+");
		jButton8.setName("jButton8");
		jButton9 = new javax.swing.JButton();
		panel_4.add(jButton9);

		jButton9.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jButton9.setText("-");
		jButton9.setName("jButton9");
		jCheckBox1 = new javax.swing.JCheckBox();
		panel_4.add(jCheckBox1);

		jCheckBox1.setText("\u66F4\u65B0\u503C\u4E3A\u8868\u683C\u4FEE\u6539\u540E\u7684\u5185\u5BB9");
		jCheckBox1.setName("jCheckBox1");

		panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(0, 50));
		jPanel3.add(panel_5, BorderLayout.NORTH);
		panel_5.setLayout(null);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(0, 27, 138, 15);
		panel_5.add(jLabel5);

		jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		jLabel5.setText("例子:字符型 '3'  数字型 3");
		jLabel5.setName("jLabel5"); // NOI18N
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(0, 6, 472, 15);
		panel_5.add(jLabel4);

		jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		jLabel4.setText("注意:更新值一列,如果字段为字符型或者为日期型,请加单引号;如果是数值型无需加单引号");
		jLabel4.setName("jLabel4");

		panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u4EE5\u6700\u540E\u4E00\u884C\u8BBE\u7F6E\u4E3A\u51C6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setPreferredSize(new Dimension(250, 0));
		add(panel_6, BorderLayout.EAST);
		panel_6.setLayout(new BorderLayout(0, 0));
		jScrollPane4 = new javax.swing.JScrollPane();
		panel_6.add(jScrollPane4, BorderLayout.CENTER);
		jList1 = new javax.swing.JList();

		jScrollPane4.setName("jScrollPane4"); // NOI18N

		jList1.setName("jList1"); // NOI18N
		jScrollPane4.setViewportView(jList1);

		panel_7 = new JPanel();
		panel_6.add(panel_7, BorderLayout.SOUTH);

		button_1 = new JButton("\u4E0A\u79FB");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upMoveList();
			}

		});
		panel_7.add(button_1);

		btnNewButton = new JButton("\u4E0B\u79FB");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downMoveList();
			}
		});
		panel_7.add(btnNewButton);

		button_2 = new JButton("\u5220\u9664");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delList();
			}
		});
		panel_7.add(button_2);
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton14;
	private javax.swing.JButton jButton15;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton8;
	private javax.swing.JButton jButton9;
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JCheckBox jCheckBox2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private JList jList1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JTable jTable1;
	private javax.swing.JTable jTable2;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private boolean submit;
	private Map<String, String> props;
	private final String[] title1 = { "序号", "更新字段名称", "更新值" };
	private final String[] title2 = { "序号", "查询类字段名称", "条件字段名称" };
	private final int INDEX_UPDATEFIELD = 1;
	private final int INDEX_UPDATEVALUE = 2;
	private final int INDEX_CLASSFIELD = 1;
	private final int INDEX_QUERYFIELD = 2;
	private DefaultTableModel model1;
	private DefaultTableModel model2;
	private DefaultListModel listModel;
	private JPanel panel_2;
	private JSeparator separator;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_3;
	private JButton button;
	private JPanel panel_6;
	private JPanel panel_7;
	private JButton button_1;
	private JButton btnNewButton;
	private JButton button_2;
	private boolean flag = false;
	private JButton button_3;
	private XMLDto table;
	private List<XMLDto> tableAll = null;
	private JButton btnNewButton_1;
	private List<XMLDto> allFields = null;
	private JPanel panel_8;
	private JCheckBox checkBox;

	private void getTablesByService() {
		String temp = jTextField1.getText().trim();
		if (CommonUtils.isStrEmpty(temp, true)) {
			GUIUtils.showMsg(dialog, "请输入服务名");
			return;
		}
		if (temp.equals(service)) {
			return;
		}
		service = temp;
		tableAll = CommonUtils.getTableByService(service);
	}

	private void chooseCodeCenter(boolean flag) {
		clear();
		if (flag) {
			tableAll = CompUtils.getCodeTables();
		} else {
			tableAll = null;
			service = null;

		}

		jLabel1.setEnabled(!flag);
		jTextField1.setEnabled(!flag);
		btnNewButton_1.setEnabled(!flag);
	}

	private void clear() {
		tableAll = null;
		service = null;
		table = null;
		allFields = null;
		clearUI();
	}

	private void selectTable() throws Exception {
		if (tableAll == null || tableAll.isEmpty()) {
			GUIUtils.showMsg(dialog, "表格为空");
			return;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(tableAll);
		pnl.setValue(table);
		pnl.edit(dialog, null);
		if (pnl.isChange()) {
			table = pnl.getSelect();
			if (table != null) {
				jTextField2.setText(table.toString());
				if (radioButton.isSelected()) {
					allFields = CommonUtils.getFieldsByServiceTable(table);
				} else {
					allFields = InvokerServiceUtils.getCodeFields(table.getValue("id"));
				}

			} else {
				jTextField2.setText("");
				allFields = null;
			}
		}
	}

	private void addParamtoList() {
		try {
			CompUtils.stopTabelCellEditor(jTable1);
			CompUtils.stopTabelCellEditor(jTable2);
			if (table == null) {
				GUIUtils.showMsg(dialog, "更新表名不能为空");
				return;
			}
			int rcount1 = model1.getRowCount();
			if (rcount1 <= 0) {
				GUIUtils.showMsg(dialog, "请添加更新信息");
				return;
			}
			String autoCondiInfo = "autoCondiInfo=";
			String constCondiInfo = "constCondiInfo=";
			String autoValue = "autoValue=";
			String serviceName = "serviceName=";
			String tableName = "tableName=";
			String updateInfo = "updateInfo=";
			String updateAll = "updateAll=";
			String temp = jTextField1.getText().trim();
			boolean isCodeTable = radioButton_1.isSelected();
			if (isCodeTable) {
				serviceName += CommonUtils.base64Encode("codecenter".getBytes());
			} else {
				if (CommonUtils.isStrEmpty(temp)) {
					GUIUtils.showMsg(dialog, "服务名不能为空");
					return;
				}
				serviceName += CommonUtils.base64Encode(temp.getBytes());

			}

			if (jCheckBox1.isSelected()) {
				autoValue += "1";
			}
			if (jCheckBox2.isSelected()) {
				updateAll += "1";
			}
			temp = jTextArea1.getText();
			constCondiInfo += CommonUtils.isStrEmpty(temp) ? "" : CommonUtils.base64Encode(temp.getBytes());
			StringBuilder sb1 = new StringBuilder();
			StringBuilder resStr = new StringBuilder();
			String tableKey = "", fieldKey = "", fieldlabelKey = "";
			if (isCodeTable) {
				tableKey = "tablename";
				fieldKey = "fieldname";
				fieldlabelKey = "fieldlabel";
			} else {
				tableKey = "name";
				fieldKey = "name";
				fieldlabelKey = "cname";
			}

			tableName += CommonUtils.base64Encode(table.getValue(tableKey).getBytes());
			for (int i = 0; i < rcount1; i++) {
				XMLDto updateField = (XMLDto) jTable1.getValueAt(i, INDEX_UPDATEFIELD);
				if (updateField == null) {
					GUIUtils.showMsg(dialog, "待更新字段不能为空");
					jTable1.setRowSelectionInterval(i, i);
					return;
				}
				String fieldName = updateField.getValue(fieldKey);
				String fieldLabel = updateField.getValue(fieldlabelKey);
				String value = CommonUtils.coverNull((String) jTable1.getValueAt(i, INDEX_UPDATEVALUE));
				sb1.append(fieldLabel + "[" + fieldName + "]=" + value);
				if (i != rcount1 - 1) {
					sb1.append(";");
				}

			}
			StringBuilder sb2 = new StringBuilder();
			int rcount2 = model2.getRowCount();
			if (rcount2 > 0) {
				for (int i = 0; i < rcount2; i++) {
					Object tempObj = jTable2.getValueAt(i, INDEX_CLASSFIELD);
					if (tempObj == null) {
						GUIUtils.showMsg(dialog, "查询类字段不能为空");
						jTable2.setRowSelectionInterval(i, i);
						return;
					}
					String itemname = "", itemlabel = "";
					if (tempObj instanceof String) {
						itemname = (String) tempObj;
					} else {
						XMLDto classField = (XMLDto) tempObj;
						itemname = classField.getValue("itemname");
						itemlabel = classField.getValue("itemlabel");
					}
					String queryField = CommonUtils.coverNull((String) jTable2.getValueAt(i, INDEX_QUERYFIELD));
					sb2.append(itemlabel).append("[").append(itemname).append("]=").append(queryField);
					if (i != rcount1 - 1) {
						sb2.append(";");
					}

				}
			}
			updateInfo += !"".equals(sb1.toString()) ? CommonUtils.base64Encode(sb1.toString().getBytes()) : "";
			autoCondiInfo += !"".equals(sb2.toString()) ? CommonUtils.base64Encode(sb2.toString().getBytes()) : "";
			resStr.append(serviceName).append(";").append(tableName).append(";").append(updateInfo).append(";").append(autoCondiInfo).append(";").append(constCondiInfo).append(";").append(autoValue).append(";").append(updateAll);
			if (checkBox.isSelected()) {
				resStr.append(";needUpdateTxm=1");
			}
			listModel.addElement(CommonUtils.base64Encode(resStr.toString().getBytes()));
			flag = true;
			jList1.setSelectedIndex(listModel.size() - 1);
			flag = false;
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "保存失败");
			logger.error(e.getMessage(), e);
		}

	}

	private void addRowTable1() {
		int index = jTable1.getSelectedRow();
		if (index < 0) {
			index = model1.getRowCount();
		}
		model1.insertRow(index, new Object[] { 0, null, "" });
		CompUtils.initSortNum(model1, 0);
		jTable1.setRowSelectionInterval(index, index);
	}

	private void addRowTable2() {
		int index = jTable2.getSelectedRow();
		if (index < 0) {
			index = model2.getRowCount();
		}
		model2.insertRow(index, new Object[] { 0, null, "" });
		CompUtils.initSortNum(model2, 0);
		jTable2.setRowSelectionInterval(index, index);
	}

	private void init() {
		radioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				chooseCodeCenter(e.getStateChange() == ItemEvent.DESELECTED);
			}
		});

		model1 = new DefaultTableModel(title1, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return false;
				}
				return true;
			}

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) {
					return Integer.class;
				}
				return super.getColumnClass(columnIndex);
			}
		};
		model2 = new DefaultTableModel(title2, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return false;
				}
				return true;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) {
					return Integer.class;
				}
				return super.getColumnClass(columnIndex);
			}
		};
		jTable1.setModel(model1);
		jTable2.setModel(model2);

		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jTable1.isEditing()) {
					if (allFields == null) {
						GUIUtils.showMsg(dialog, "字段为空");
						CompUtils.stopTabelCellEditor(jTable1);
						return;
					}
					int row = jTable1.getSelectedRow();
					int col = jTable1.getSelectedColumn();
					ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(allFields);
					XMLDto value = (XMLDto) jTable1.getValueAt(row, col);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (!pnl.isChange()) {
						CompUtils.stopTabelCellEditor(jTable1);
						return;
					}
					value = pnl.getSelect();
					jTable1.setValueAt(value, row, col);
					CompUtils.stopTabelCellEditor(jTable1);
				} else if (jTable2.isEditing()) {
					int row = jTable2.getSelectedRow();
					int col = jTable2.getSelectedColumn();
					ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
					XMLDto value = (XMLDto) jTable2.getValueAt(row, col);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (!pnl.isChange()) {
						CompUtils.stopTabelCellEditor(jTable1);
						return;
					}
					value = pnl.getSelect();
					jTable2.setValueAt(value, row, col);
					CompUtils.stopTabelCellEditor(jTable2);
				}

			}
		}, false);

		TableColumnModel cm1 = jTable1.getColumnModel();
		TableColumnModel cm2 = jTable2.getColumnModel();
		TableColumn updateCol = cm1.getColumn(INDEX_UPDATEFIELD);
		TableColumn classCol = cm2.getColumn(INDEX_CLASSFIELD);
		updateCol.setCellEditor(editor);
		updateCol.setCellRenderer(editor.getTableCellRenderer());
		classCol.setCellEditor(editor);
		classCol.setCellRenderer(editor.getTableCellRenderer());

		CompUtils.setTableWdiths(jTable1, 0.2);
		CompUtils.setTableWdiths(jTable2, 0.2);

		listModel = new DefaultListModel();
		jList1.setModel(listModel);
		jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList1.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (flag || e.getValueIsAdjusting()) {
					return;
				}
				clear();
				String param = (String) jList1.getSelectedValue();
				if (CommonUtils.isStrEmpty(param)) {
					return;
				}
				updateU(param);
			}
		});

		CompUtils.tableDelRow(jButton9, jTable1, 0);
		CompUtils.tableDelRow(jButton14, jTable2, 0);
	}

	private void delList() {
		int index = jList1.getSelectedIndex();
		if (index <= 0) {
			return;
		}
		flag = true;
		listModel.removeElementAt(index);
		flag = false;
	}

	private void upMoveList() {
		int index = jList1.getSelectedIndex();
		if (index <= 0) {
			return;
		}
		flag = true;
		Object value = jList1.getSelectedValue();
		jList1.clearSelection();
		listModel.removeElementAt(index);
		listModel.insertElementAt(value, --index);
		jList1.setSelectedIndex(index);
		flag = false;

	}

	private void downMoveList() {
		int index = jList1.getSelectedIndex();
		if (index < 0 || index >= listModel.size() - 1) {
			return;
		}
		flag = true;
		Object value = jList1.getSelectedValue();
		jList1.clearSelection();
		listModel.removeElementAt(index);
		listModel.insertElementAt(value, ++index);
		jList1.setSelectedIndex(index);
		flag = false;
	}

	private void clearUI() {
		jTextField1.setText("");
		jTextField2.setText("");
		jTextArea1.setText("");
		model1.setRowCount(0);
		model2.setRowCount(0);
	}

	private void updateU(String inParam) {
		try {
			String initStr = new String(CommonUtils.base64Dcode(inParam));
			String[] fields = initStr.split(";");
			boolean isCodeCenter = false;
			String tableKey = "", fieldKey = "";
			String temp;
			for (String field : fields) {
				int index = field.indexOf("=");
				if (index == -1) {
					continue;
				}
				String key = field.substring(0, index);
				String value = field.substring(index + 1);
				if (!CommonUtils.isStrEmpty(value, true) && value.length() > 1) {
					value = new String(CommonUtils.base64Dcode(value));
				}
				if ("serviceName".equalsIgnoreCase(key)) {
					isCodeCenter = "codecenter".equals(value);
					if (isCodeCenter) {
						service = "";
						radioButton_1.setSelected(true);
						tableAll = CompUtils.getCodeTables();
						tableKey = "tablename";
						fieldKey = "fieldname";
					} else {
						service = value;
						radioButton.setSelected(true);
						tableAll = CommonUtils.getTableByService(service);
						tableKey = "name";
						fieldKey = "name";
						jTextField1.setText(service);
					}
				} else if ("tableName".equalsIgnoreCase(key)) {
					if (CommonUtils.isStrEmpty(value, true)) {
						return;
					}
					table = CommonUtils.getXmlDto(tableAll, tableKey, value);
					if (table == null) {
						table = CommonUtils.getXmlDto(tableAll, "alias", value);
						if (table == null) {
							GUIUtils.showMsg(dialog, "表格没找到");
							return;
						}

					}
					jTextField2.setText(table.toString());
					if (isCodeCenter) {
						allFields = InvokerServiceUtils.getCodeFields(table.getValue("id"));
					} else {
						allFields = CommonUtils.getFieldsByServiceTable(table);
					}
				} else if ("autoCondiInfo".equalsIgnoreCase(key)) {
					if (!CommonUtils.isStrEmpty(value)) {
						String[] autofields = value.split(";");
						if (autofields.length > 0) {
							for (int i = 0; i < autofields.length; i++) {
								int index1 = autofields[i].indexOf("[");
								int index2 = autofields[i].indexOf("]");
								temp = autofields[i].substring(index1 + 1, index2);
								if (CommonUtils.isStrEmpty(temp, true)) {
									continue;
								}
								Object dto = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", temp);
								if (dto == null) {
									dto = temp;
								}
								model2.addRow(new Object[] { i + 1, dto, autofields[i].substring(index2 + 2) });
							}
						}
					}
				} else if ("constCondiInfo".equalsIgnoreCase(key)) {
					jTextArea1.setText(value);
				} else if ("updateAll".equalsIgnoreCase(key)) {
					jCheckBox2.setSelected("1".equals(value));
				} else if ("autoValue".equalsIgnoreCase(key)) {
					jCheckBox1.setSelected("1".equals(value));
				} else if ("updateInfo".equalsIgnoreCase(key)) {
					if (!CommonUtils.isStrEmpty(value)) {
						String[] upfields = value.split(";");
						if (upfields.length > 0) {
							for (int i = 0; i < upfields.length; i++) {
								int index1 = upfields[i].indexOf("[");
								int index2 = upfields[i].indexOf("]");
								temp = upfields[i].substring(index1 + 1, index2);
								if (CommonUtils.isStrEmpty(temp, true)) {
									continue;
								}
								XMLDto dto = CommonUtils.getXmlDto(allFields, fieldKey, temp);
								if (dto == null) {
									continue;
								}
								model1.addRow(new Object[] { i + 1, dto, upfields[i].substring(index2 + 2) });
							}
						}
					}
				} else if ("needUpdateTxm".equals(key)) {
					checkBox.setSelected("1".equals(value));
				}

			}

		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "更新界面失败");
			logger.error(e.getMessage(), e);
		}
	}

	private void save() {
		if (listModel.size() > 0) {
			Element root = DocumentHelper.createElement("root");
			for (int i = 0; i < listModel.size(); i++) {
				root.addElement("data").setText((String) listModel.getElementAt(i));
			}
			props.put("inparam", root.asXML());
		} else {
			props.put("inparam", "");
		}
		submit = true;
		dialog.dispose();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "通用更新数据组件设置", this);
		dialog.setVisible(true);
	}

	private void initData() {
		try {
			String inparam = props.get("inparam");
			if (CommonUtils.isStrEmpty(inparam)) {
				return;
			}

			if (inparam.startsWith("&lt;")) {
				inparam = inparam.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
			}
			if (!inparam.startsWith("<root>")) {
				listModel.addElement(CommonUtils.base64Encode(inparam.getBytes()));
			} else {
				Document doc = DocumentHelper.parseText(inparam);
				List<Element> lst = doc.getRootElement().elements("data");
				if (lst == null || lst.size() <= 0) {
					return;
				}
				for (Element e : lst) {
					listModel.addElement(e.getText());
				}
			}
			if (!listModel.isEmpty()) {
				jList1.setSelectedIndex(listModel.size() - 1);
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.INIT_ERROR);
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
