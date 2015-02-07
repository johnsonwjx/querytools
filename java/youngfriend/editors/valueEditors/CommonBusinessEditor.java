/*
 * OpenCommonBusiness.java
 * Created on Dec 12, 2011, 8:27:29 PM
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Element;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;

import javax.swing.border.TitledBorder;

import java.awt.Font;

public class CommonBusinessEditor extends JPanel implements ValueEditor {

	private static final long serialVersionUID = 3192479523056269873L;
	private JDialog dialog;
	public JComboBox cellTableComboBox = new JComboBox();
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private JCheckBox checkBox;
	private JButton btnNewButton_1;

	public CommonBusinessEditor() {
		initComponents();
		init();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(700, 629));
		buttonGroup1 = new javax.swing.ButtonGroup();
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setPreferredSize(new Dimension(0, 120));
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 6, 104, 16);
		workpubnameTextField = new javax.swing.JTextField();
		workpubnameTextField.setEditable(false);
		workpubnameTextField.setBounds(128, 0, 421, 28);
		selectWorkpubButton = new javax.swing.JButton();
		selectWorkpubButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectBusiness();
			}
		});
		selectWorkpubButton.setBounds(555, 1, 122, 29);
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(0, 27, 205, 47);
		modalRadioButton = new javax.swing.JRadioButton();
		modalRadioButton.setBounds(6, 18, 84, 23);
		nonModalRadioButton = new javax.swing.JRadioButton();
		nonModalRadioButton.setBounds(96, 18, 97, 23);
		isRefreshCheckBox = new javax.swing.JCheckBox();
		isRefreshCheckBox.setBounds(217, 40, 205, 23);
		jPanel3 = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();

		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("对应通用业务功能");
		jLabel1.setName("jLabel1");

		workpubnameTextField.setName("workpubnameTextField"); // NOI18N

		selectWorkpubButton.setText("选择通用业务");
		selectWorkpubButton.setName("selectWorkpubButton");

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("打开模式"));
		jPanel2.setName("jPanel2"); // NOI18N

		buttonGroup1.add(modalRadioButton);
		modalRadioButton.setText("模式窗口");
		modalRadioButton.setActionCommand("1");
		modalRadioButton.setName("modalRadioButton"); // NOI18N

		buttonGroup1.add(nonModalRadioButton);
		nonModalRadioButton.setSelected(true);
		nonModalRadioButton.setText("非模式窗口");
		nonModalRadioButton.setActionCommand("0");
		nonModalRadioButton.setName("nonModalRadioButton"); // NOI18N
		nonModalRadioButton.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				nonModalRadioButtonItemStateChanged(evt);
			}
		});

		isRefreshCheckBox.setText("模式窗口时,关闭窗口重新刷新");
		isRefreshCheckBox.setEnabled(false);
		isRefreshCheckBox.setName("isRefreshCheckBox");
		jPanel3.setName("jPanel3"); // NOI18N
		setLayout(new BorderLayout(0, 0));

		jPanel5.setName("jPanel5");
		add(jPanel5, BorderLayout.SOUTH);
		jPanel5.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		okButton = new javax.swing.JButton();
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		jPanel5.add(okButton);

		okButton.setText("确定");
		okButton.setName("okButton"); // NOI18N
		cancelButton = new javax.swing.JButton();
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		jPanel5.add(cancelButton);

		cancelButton.setText("取消");
		cancelButton.setName("cancelButton"); // NOI18N
		add(jPanel1, BorderLayout.NORTH);
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(workpubnameTextField);
		jPanel1.add(selectWorkpubButton);
		jPanel1.add(jPanel2);
		jPanel2.setLayout(null);
		jPanel2.add(modalRadioButton);
		jPanel2.add(nonModalRadioButton);
		jPanel1.add(isRefreshCheckBox);

		JButton button = new JButton("\u81EA\u52A8\u5BF9\u5E94\u76F8\u540C\u5B57\u6BB5");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autoCorspond();
			}
		});
		button.setBounds(436, 40, 148, 29);
		jPanel1.add(button);

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(6, 75, 688, 39);
		jPanel1.add(panel);
		panel.setLayout(null);

		checkBox = new JCheckBox("\u4F7F\u7528\u4E1A\u52A1\u6837\u5F0F\u5BF9\u5E94\u5B57\u6BB5\u7684\u503C\u4F5C\u4E3A\u4E1A\u52A1\u6837\u5F0F");
		checkBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				btnNewButton_1.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				if (!btnNewButton_1.isEnabled()) {
					textField.setText("");
					styleRelationField = null;
				}
			}
		});
		checkBox.setBounds(6, 6, 266, 23);
		panel.add(checkBox);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(392, 4, 240, 28);
		panel.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("\u4E1A\u52A1\u6837\u5F0F\u5BF9\u5E94\u5B57\u6BB5:");
		label.setBounds(284, 10, 115, 16);
		panel.add(label);

		btnNewButton_1 = new JButton("...");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectSelectPnl<XMLDto> fieldPnl = CompUtils.getFieldsPnl();
				fieldPnl.setValue(styleRelationField);
				fieldPnl.edit(dialog, null);
				if (fieldPnl.isChange()) {
					styleRelationField = fieldPnl.getSelect();
					if (styleRelationField != null) {
						textField.setText(styleRelationField.toString());
					} else {
						textField.setText("");
					}
				}

			}
		});
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(632, 5, 50, 29);
		panel.add(btnNewButton_1);
		add(jPanel3);
		jPanel3.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u5165\u53E3\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel3.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		jScrollPane1 = new javax.swing.JScrollPane();
		panel_1.add(jScrollPane1);
		table = new javax.swing.JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == index_num) {
					return false;
				}
				return true;
			}
		};
		JPanel btn_pnl_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) btn_pnl_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_1.add(btn_pnl_1, BorderLayout.SOUTH);
		addRowButton = new javax.swing.JButton();
		btn_pnl_1.add(addRowButton);
		addRowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRow(table, index_num);
			}
		});

		addRowButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		addRowButton.setText("+");
		addRowButton.setName("addRowButton"); // NOI18N
		delRowButton = new javax.swing.JButton();
		btn_pnl_1.add(delRowButton);
		delRowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delRow(table, index_num);
			}
		});

		delRowButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		delRowButton.setText("-");
		delRowButton.setName("delRowButton"); // NOI18N

		btnNewButton = new JButton("\u6E05\u7A7A");
		btn_pnl_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (GUIUtils.showConfirm(dialog, "确定清空")) {
					clearTble(table);
				}
			}
		});
		jScrollPane1.setName("jScrollPane1"); // NOI18N
		jScrollPane1.setViewportView(table);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "\u51FA\u53E3\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel3.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		JPanel btn_pnl_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) btn_pnl_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_2.add(btn_pnl_2, BorderLayout.SOUTH);

		button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRow(table_1, index_num_out);
			}
		});
		button_1.setText("+");
		button_1.setName("addRowButton");
		button_1.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btn_pnl_2.add(button_1);

		button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delRow(table_1, index_num_out);
			}
		});
		button_2.setText("-");
		button_2.setName("delRowButton");
		button_2.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btn_pnl_2.add(button_2);

		button_3 = new JButton("\u6E05\u7A7A");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GUIUtils.showConfirm(dialog, "确定清空")) {
					clearTble(table_1);
				}
			}
		});
		btn_pnl_2.add(button_3);
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		scrollPane.setPreferredSize(new Dimension(0, 147));
	}

	private void nonModalRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {
		if (((JRadioButton) evt.getSource()).isSelected()) {
			isRefreshCheckBox.setEnabled(false);
		} else {
			isRefreshCheckBox.setEnabled(true);
		}
	}

	private javax.swing.JButton addRowButton;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JButton cancelButton;
	private javax.swing.JButton delRowButton;
	private javax.swing.JTable table;
	private javax.swing.JCheckBox isRefreshCheckBox;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JRadioButton modalRadioButton;
	private javax.swing.JRadioButton nonModalRadioButton;
	private javax.swing.JButton okButton;
	private javax.swing.JButton selectWorkpubButton;
	private javax.swing.JTextField workpubnameTextField;
	private JButton btnNewButton;
	private boolean submit;
	private Map<String, String> props;
	private DefaultTableModel model;
	private XMLDto work;
	private List<XMLDto> tables;
	private List<XMLDto> classfields = CompUtils.getFields();
	private List<XMLDto> works = null;;
	private TreeSelectPnl<XMLDto> pnl = null;
	private JPanel panel;
	private JTextField textField;
	private JTable table_1;
	private String[] title_out = new String[] { "序号", "查询字段", "通用业务表", "通用业务字段" };
	private final int index_num_out = 0;
	private final int index_field_out = 1;
	private final int index_bustable_out = 2;
	private final int index_busfield_out = 3;

	private XMLDto styleRelationField = null;
	private final String[] title = { "序号", "传递的参数字段", "字段位置", "固定值", "接收的参数表", "接收的参数字段" };
	private final int index_transmitField = 1;
	private final int index_location = 2;
	private final int index_fix = 3;
	private final int index_receiveTable = 4;
	private final int index_receiveField = 5;
	private final int index_num = 0;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private DefaultTableModel model_out;

	private void selectBusiness() {
		try {
			if (pnl == null) {
				JTree tree = new JTree();
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("通用业务目录");
				tree.setModel(new DefaultTreeModel(root));
				CompUtils.buildTree(works, root, "code", null);
				pnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {

					@Override
					public String validate(XMLDto obj) {
						return "#".equals(obj.getValue("type")) ? "请选择底层" : null;
					}
				});
			}
			String id = "";
			if (work != null) {
				id = work.getValue("id");
			}
			Map<String, String> prop = new HashMap<String, String>();
			prop.put("key", "id");
			prop.put("value", id);
			pnl.edit(dialog, prop);
			if (!pnl.isChange()) {
				return;
			}
			work = pnl.getSelect();
			initByWork();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "获取业务树失败");
			logger.error(e.getMessage(), e);
		}
	}

	private void initByWork() throws Exception {
		tables = null;
		if (work == null) {
			workpubnameTextField.setText("");
			return;
		}
		workpubnameTextField.setText(work.toString());
		List<Element> eles = InvokerServiceUtils.getWorkPubFormeleTables(work.getValue("id"));
		if (eles == null || eles.isEmpty()) {
			return;
		}
		tables = new ArrayList<XMLDto>();
		for (Element tEle : eles) {
			XMLDto dto = new XMLDto(Arrays.asList("name", "alias"));
			dto.setValue("name", tEle.elementText("name"));
			dto.setValue("alias", tEle.elementText("alias"));
			dto.setValue("fields", tEle.element("classitems"));
			tables.add(dto);
		}
	}

	private void autoCorspond() {
		if (tables == null || tables.isEmpty()) {
			GUIUtils.showMsg(dialog, "请先选择通用业务");
			return;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(tables);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		XMLDto value = pnl.getSelect();
		List<XMLDto> fields = getFields(value);
		if (fields == null || fields.isEmpty()) {
			return;
		}
		CompUtils.stopTabelCellEditor(table);
		int insertIndex = model.getRowCount() - 1;
		for (XMLDto classField : classfields) {
			String name = classField.getValue("itemname");
			for (XMLDto f : fields) {
				if (f.getValue("itemname").equalsIgnoreCase(name)) {
					model.addRow(new Object[] { table.getRowCount() + 1, name, "表格内", "", value.getValue("alias"), name });
					break;
				}
			}
		}
		if (model.getRowCount() > 0) {
			insertIndex++;
		}
		table.setRowSelectionInterval(insertIndex, insertIndex);
	}

	private void init() {
		model = new DefaultTableModel(title, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == index_location && !CommonUtils.isStrEmpty((String) model.getValueAt(row, index_fix), true)) {
					return false;
				}
				return true;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				Class<?>[] clazzes = { Integer.class, String.class, String.class, String.class };
				return clazzes[columnIndex];
			}
		};
		model.addTableModelListener(new TableModelListener() {
			boolean flag = false;

			@Override
			public void tableChanged(TableModelEvent e) {
				if (flag) {
					return;
				}
				if (e.getColumn() == index_transmitField) {
					String value = (String) model.getValueAt(e.getFirstRow(), e.getColumn());
					if (!CommonUtils.isStrEmpty(value, true)) {
						flag = true;
						model.setValueAt("", e.getFirstRow(), index_fix);
						flag = false;
					}
				} else if (e.getColumn() == index_fix) {
					String value = (String) model.getValueAt(e.getFirstRow(), e.getColumn());
					if (!CommonUtils.isStrEmpty(value, true)) {
						flag = true;
						model.setValueAt("", e.getFirstRow(), index_location);
						model.setValueAt("", e.getFirstRow(), index_transmitField);
						flag = false;
					}
				}

			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(model);
		CompUtils.setTableWdiths(table, 0.1);

		model_out = new DefaultTableModel(title_out, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == index_num_out) {
					return false;
				}
				return true;
			}
		};
		table_1.setModel(model_out);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		CompUtils.setTableWdiths(table_1, 0.1);
		TableColumnModel cm = table.getColumnModel();
		TableColumnModel cm_out = table_1.getColumnModel();
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(table);
				int sc = table.getSelectedColumn();
				int row = table.getSelectedRow();
				String temp = (String) table.getValueAt(row, sc);
				XMLDto value = null;
				ObjectSelectPnl<XMLDto> pnl = null;
				switch (sc) {
				case index_transmitField:
					if (!CommonUtils.isStrEmpty(temp)) {
						value = CommonUtils.getXmlDto(classfields, "itemname", temp);
					}
					pnl = new ObjectSelectPnl<XMLDto>(classfields);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (!pnl.isNull()) {
							table.setValueAt(value.getValue("itemname"), row, sc);
						} else {
							table.setValueAt("", row, sc);
						}
					}
					break;
				case index_receiveTable:
					if (tables == null || tables.isEmpty()) {
						GUIUtils.showMsg(dialog, "请先选择通用业务");
						break;
					}
					if (!CommonUtils.isStrEmpty(temp)) {
						value = CommonUtils.getXmlDto(tables, "alias", temp);
					}
					pnl = new ObjectSelectPnl<XMLDto>(tables);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (value != null) {
							table.setValueAt(value.getValue("alias"), row, sc);
							table.setValueAt("", row, index_receiveField);
						} else {
							table.setValueAt("", row, sc);
							table.setValueAt("", row, index_receiveField);
						}
					}
					break;
				case index_receiveField:

					String tableStr = (String) table.getValueAt(row, index_receiveTable);
					if (CommonUtils.isStrEmpty(tableStr)) {
						GUIUtils.showMsg(dialog, "请先选择接收表");
						break;
					}
					XMLDto receiveTable = CommonUtils.getXmlDto(tables, "alias", tableStr);
					if (receiveTable == null) {
						GUIUtils.showMsg(dialog, "无效表格");
						break;
					}
					List<XMLDto> fields = getFields(receiveTable);
					if (!CommonUtils.isStrEmpty(temp)) {
						value = CommonUtils.getXmlDto(fields, "itemname", temp);
					}
					pnl = new ObjectSelectPnl<XMLDto>(fields);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (!pnl.isNull()) {
							table.setValueAt(value.getValue("itemname"), row, sc);
						} else {
							table.setValueAt("", row, sc);
						}
					}
					break;
				}
			}
		}, true);
		ButtonCellEditor editor_out = new ButtonCellEditor(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(table_1);
				int sc = table_1.getSelectedColumn();
				int row = table_1.getSelectedRow();
				String temp = (String) table_1.getValueAt(row, sc);
				XMLDto value = null;
				ObjectSelectPnl<XMLDto> pnl = null;
				switch (sc) {
				case index_field_out:
					if (!CommonUtils.isStrEmpty(temp)) {
						value = CommonUtils.getXmlDto(classfields, "itemname", temp);
					}
					pnl = new ObjectSelectPnl<XMLDto>(classfields);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (!pnl.isNull()) {
							table_1.setValueAt(value.getValue("itemname"), row, sc);
						} else {
							table_1.setValueAt("", row, sc);
						}
					}
					break;
				case index_bustable_out:
					if (tables == null || tables.isEmpty()) {
						GUIUtils.showMsg(dialog, "请先选择通用业务");
						break;
					}
					if (!CommonUtils.isStrEmpty(temp)) {
						value = CommonUtils.getXmlDto(tables, "alias", temp);
					}
					pnl = new ObjectSelectPnl<XMLDto>(tables);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (value != null) {
							table_1.setValueAt(value.getValue("alias"), row, sc);
							table_1.setValueAt("", row, index_busfield_out);
						} else {
							table_1.setValueAt("", row, sc);
							table_1.setValueAt("", row, index_busfield_out);
						}
					}
					break;
				case index_busfield_out:

					String tableStr = (String) table_1.getValueAt(row, index_bustable_out);
					if (CommonUtils.isStrEmpty(tableStr)) {
						GUIUtils.showMsg(dialog, "请先选择接收表");
						break;
					}
					XMLDto receiveTable = CommonUtils.getXmlDto(tables, "alias", tableStr);
					if (receiveTable == null) {
						GUIUtils.showMsg(dialog, "无效表格");
						break;
					}
					List<XMLDto> fields = getFields(receiveTable);
					if (!CommonUtils.isStrEmpty(temp)) {
						value = CommonUtils.getXmlDto(fields, "itemname", temp);
					}
					pnl = new ObjectSelectPnl<XMLDto>(fields);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (!pnl.isNull()) {
							table_1.setValueAt(value.getValue("itemname"), row, sc);
						} else {
							table_1.setValueAt("", row, sc);
						}
					}
					break;
				}
			}
		}, true);
		TableColumn field_out = cm_out.getColumn(index_field_out);
		TableColumn bustable_out = cm_out.getColumn(index_bustable_out);
		TableColumn busfield_out = cm_out.getColumn(index_busfield_out);

		field_out.setCellEditor(editor_out);
		bustable_out.setCellEditor(editor_out);
		busfield_out.setCellEditor(editor_out);
		field_out.setCellRenderer(editor_out.getTableCellRenderer());
		bustable_out.setCellRenderer(editor_out.getTableCellRenderer());
		busfield_out.setCellRenderer(editor_out.getTableCellRenderer());

		TableColumn c1 = cm.getColumn(index_transmitField);
		TableColumn c2 = cm.getColumn(index_receiveTable);
		TableColumn c3 = cm.getColumn(index_receiveField);
		TableColumn c4 = cm.getColumn(index_location);
		c4.setCellEditor(new DefaultCellEditor(new JComboBox(new String[] { "表格内", "表格外" })));
		c1.setCellEditor(editor);
		c2.setCellEditor(editor);
		c3.setCellEditor(editor);
		c1.setCellRenderer(editor.getTableCellRenderer());
		c2.setCellRenderer(editor.getTableCellRenderer());
		c3.setCellRenderer(editor.getTableCellRenderer());
	}

	private List<XMLDto> getFields(XMLDto table) {
		if (table == null) {
			return null;
		}
		try {
			Element fieldEle = table.getObject(Element.class, "fields");
			if (fieldEle == null || !fieldEle.hasContent()) {
				return null;
			}
			List<Element> fieldsEle = fieldEle.elements();
			if (fieldsEle == null || fieldsEle.isEmpty()) {
				return null;
			}
			return CommonUtils.coverEles(fieldsEle, new String[] { "itemname", "itemlabel" }, null, new String[] { "itemlabel", "itemname" }, null, null, null);

		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "选择表格失败");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	private void addRow(JTable tb, int index_num) {
		CompUtils.stopTabelCellEditor(tb);
		DefaultTableModel md = (DefaultTableModel) tb.getModel();
		int index = tb.getSelectedRow();
		if (index < 0) {
			index = md.getRowCount();
		} else {
			index++;
		}
		md.insertRow(index, new Object[] { index, "", "", "", "" });
		setNum(md, index_num);
		tb.setRowSelectionInterval(index, index);

	}

	private void clearTble(JTable tb) {
		CompUtils.stopTabelCellEditor(tb);
		DefaultTableModel md = (DefaultTableModel) tb.getModel();
		md.setRowCount(0);
	}

	private void delRow(JTable tb, int index_num) {
		CompUtils.stopTabelCellEditor(tb);
		DefaultTableModel md = (DefaultTableModel) tb.getModel();
		int index = tb.getSelectedRow();
		if (index < 0) {
			return;
		}
		md.removeRow(index);
		setNum(md, index_num);
		if (md.getRowCount() <= 0) {
			return;
		}
		if (index != 0) {
			index--;
		}
		tb.setRowSelectionInterval(index, index);

	}

	private void setNum(DefaultTableModel md, int index) {
		if (md.getRowCount() < 0) {
			return;
		}
		for (int i = 0; i < md.getRowCount(); i++) {
			md.setValueAt(i + 1, i, index);
		}
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "打开通用业务设置", this);
		dialog.setVisible(true);
	}

	private void save() {
		CompUtils.stopTabelCellEditor(table);
		CompUtils.stopTabelCellEditor(table_1);
		if (work == null) {
			GUIUtils.showMsg(dialog, "请选择通用业务");
			return;
		}
		try {
			StringBuffer paramBuf = new StringBuffer();
			paramBuf.append("workpubid=").append(work.getValue("id")).append(";");
			paramBuf.append("workpubcode=").append(work.getValue("code")).append(";");
			paramBuf.append("workpubname=").append(work.getValue("name")).append(";");
			paramBuf.append("openMode=").append(buttonGroup1.getSelection().getActionCommand()).append(";");
			paramBuf.append("fieldRela=").append(styleRelationField == null ? "" : styleRelationField.getValue("itemname")).append(";");
			paramBuf.append("gridparams=");
			int rowCount = model.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				String transmitFieldName = (String) model.getValueAt(i, index_transmitField);
				String location = (String) model.getValueAt(i, index_location);
				String receivetTable = (String) model.getValueAt(i, index_receiveTable);
				String receviveField = (String) model.getValueAt(i, index_receiveField);
				String fix = (String) model.getValueAt(i, index_fix);
				if ((CommonUtils.isStrEmpty(transmitFieldName, true) && CommonUtils.isStrEmpty(fix, true)) || CommonUtils.isStrEmpty(receivetTable, true) || CommonUtils.isStrEmpty(receviveField, true)) {
					table.getSelectionModel().setSelectionInterval(i, i);
					GUIUtils.showMsg(dialog, "存在空数据,请检查");
					return;
				}
				String data = null;
				if (!CommonUtils.isStrEmpty(transmitFieldName, true)) {
					data = transmitFieldName;
					if ("表格外".equals(location)) {
						data = "comvalue_" + data;
					}
				} else {
					data = "fix_" + fix;
				}
				paramBuf.append(data).append("-");
				paramBuf.append(receivetTable).append(".").append(receviveField);
				if (i != rowCount - 1) {
					paramBuf.append(",");
				}
			}
			props.put("inparam", paramBuf.toString());
			paramBuf.setLength(0);
			rowCount = model_out.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				String field = (String) model_out.getValueAt(i, index_field_out);
				String bustable_out = (String) model_out.getValueAt(i, index_bustable_out);
				String busfield_out = (String) model_out.getValueAt(i, index_busfield_out);
				if (CommonUtils.isStrEmpty(field, true) || CommonUtils.isStrEmpty(bustable_out, true) || CommonUtils.isStrEmpty(busfield_out, true)) {
					GUIUtils.showMsg(dialog, "存在空数据,请检查");
					table_1.getSelectionModel().setSelectionInterval(i, i);
					return;
				}
				paramBuf.append("tablename.").append(field).append("-").append(bustable_out).append(".").append(busfield_out).append(",");
			}
			if (paramBuf.length() > 0) {
				paramBuf.deleteCharAt(paramBuf.length() - 1);
				props.put("outparam", paramBuf.toString());
			} else {
				props.put("outparam", "");
			}

			submit = true;
			this.dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}
	}

	private void initData() {
		try {
			works = InvokerServiceUtils.getWorkPubFormeleAll();
			String inparam = props.get("inparam");
			if (CommonUtils.isStrEmpty(inparam)) {
				return;
			}
			HashMap<String, String> paramMap = CommonUtils.paramStrToMap(inparam);
			if (paramMap == null || paramMap.isEmpty()) {
				return;
			}

			Enumeration<AbstractButton> buttons = buttonGroup1.getElements();
			while (buttons.hasMoreElements()) {
				JRadioButton button = (JRadioButton) buttons.nextElement();
				if (button.getActionCommand().equals(paramMap.get("openMode"))) {
					button.setSelected(true);
				}
			}

			String fieldRela = paramMap.get("fieldRela");
			if (!CommonUtils.isStrEmpty(fieldRela, true)) {
				styleRelationField = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", fieldRela);
				if (styleRelationField != null) {
					checkBox.setSelected(true);
					textField.setText(styleRelationField.toString());
				}
			}

			String workpubid = paramMap.get("workpubid");
			if (CommonUtils.isStrEmpty(workpubid)) {
				return;
			}
			work = CommonUtils.getXmlDto(works, "id", workpubid);
			if (work == null) {
				return;
			}
			initByWork();
			String gridparamsStr = paramMap.get("gridparams");
			if (gridparamsStr != null) {
				String[] rowParams = gridparamsStr.split(",");
				for (String rowParam : rowParams) {
					String[] fields = rowParam.split("-");
					if (fields.length > 1) {
						String transmitField = fields[0];
						int fieldIndex = transmitField.indexOf(".");
						String transmitFieldName = null;
						String fix = "", location = "表格内";
						if (fieldIndex != -1) {
							transmitFieldName = transmitField.substring(fieldIndex + 1);// corpid
						} else {
							transmitFieldName = transmitField;
						}
						if (transmitFieldName.startsWith("fix_")) {
							fix = transmitFieldName.substring(4);
							transmitFieldName = "";
							location = "";
						} else if (transmitFieldName.startsWith("comvalue_")) {
							fix = "";
							transmitFieldName = transmitFieldName.substring(9);
							location = "表格外";
						}

						String receiveField = fields[1];
						String receiveTableName = receiveField.substring(0, receiveField.indexOf("."));
						String receiveFieldName = receiveField.substring(receiveField.indexOf(".") + 1);
						model.addRow(new Object[] { model.getRowCount() + 1, transmitFieldName, location, fix, receiveTableName, receiveFieldName });
					}
				}
			}
			String outparam = props.get("outparam");
			if (CommonUtils.isStrEmpty(outparam, true) || work == null) {
				return;
			}
			for (String item : outparam.split(",")) {
				String[] itemArr = CommonUtils.getKeyVal(item, "-");
				if (itemArr == null || itemArr.length != 2) {
					continue;
				}
				String field = "", bustable = "", busfield = "";
				String[] temp = CommonUtils.getKeyVal(itemArr[0], ".");
				if (temp != null && temp.length == 2) {
					field = temp[1];
				}
				temp = CommonUtils.getKeyVal(itemArr[1], ".");
				if (temp != null && temp.length == 2) {
					bustable = temp[0];
					busfield = temp[1];
				}
				model_out.addRow(new Object[] { model_out.getRowCount() + 1, field, bustable, busfield });
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
