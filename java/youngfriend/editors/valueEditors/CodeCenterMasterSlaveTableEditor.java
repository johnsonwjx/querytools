package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
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

public class CodeCenterMasterSlaveTableEditor extends JPanel implements ValueEditor {
	private final static Logger logger = LogManager.getLogger(CodeCenterMasterSlaveTableEditor.class.getName());
	private static final long serialVersionUID = 1L;

	private javax.swing.ButtonGroup buttonGroup2;

	private javax.swing.JButton cancelButton;

	public JComboBox cellTableComboBox = new JComboBox();

	private JDialog dialog;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel10;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JLabel jLabel4;

	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JList jList1;
	private javax.swing.JList jList2;
	private javax.swing.JList jList3;
	private javax.swing.JList jList4;
	private javax.swing.JList jList5;
	private javax.swing.JList jList6;
	private javax.swing.JList jList7;
	private javax.swing.JList jList8;
	private javax.swing.JList jList9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JRadioButton jRadioButton5;
	private javax.swing.JRadioButton jRadioButton6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane10;
	private javax.swing.JScrollPane jScrollPane11;
	private javax.swing.JScrollPane jScrollPane12;
	private javax.swing.JScrollPane jScrollPane13;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane7;
	private javax.swing.JScrollPane jScrollPane8;
	private javax.swing.JScrollPane jScrollPane9;
	private javax.swing.JSpinner jSpinner1;
	private javax.swing.JSpinner jSpinner2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextArea mainCondiTextArea;
	private javax.swing.JTextField titleField;
	private JTextField textField;
	private JTextField textField_1;

	private List<XMLDto> fields = CompUtils.getFields();
	private final int index_codefiedlabel = 0;
	private final int index_codefied = 1;

	private final int index_newlabel = 6;

	private final int index_relationfield = 5;

	private final int index_return = 4;

	private final int index_visible = 3;

	private final int index_width = 2;

	private List<XMLDto> masterFields = new ArrayList<XMLDto>();
	private List<XMLDto> slaveFields = new ArrayList<XMLDto>();
	private XMLDto masterTable;
	private javax.swing.JButton okButton;
	private JPanel panel;
	private Map<String, String> props;
	private javax.swing.JTextField RelationField;
	private javax.swing.JList returnFieldList;
	private XMLDto slaveTable;
	private javax.swing.JTextArea subCondiTextArea;
	private boolean submit = false;
	private final String[] title = new String[] { "代码中心字段标签", "代码中心字段", "字段宽度", "是否显示", "是否返回", "对应的表单项目", "新标签" };
	private DefaultListModel lstModel_master;
	private DefaultTableModel tablemodel;
	private DefaultListModel model_salver;
	private DefaultListModel model_returnFieldList;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JTextField textField_2;
	private JButton button_2;
	private JCheckBox checkBox_3;

	public CodeCenterMasterSlaveTableEditor() {
		initComponents();
		initUI();
	}

	private void initUI() {
		jList7.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList7.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "=", ">", ">=", "<", "<=", "!=", "like" };

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}

			@Override
			public int getSize() {
				return strings.length;
			}
		});

		jList7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				String item = (String) jList7.getSelectedValue();
				if (item == null) {
					return;
				}
				CompUtils.textareaInsertText(subCondiTextArea, " " + item);
			}
		});

		jList8.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "and", "or" };

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}

			@Override
			public int getSize() {
				return strings.length;
			}
		});
		jList8.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				String item = (String) jList8.getSelectedValue();
				if (item == null) {
					return;
				}
				CompUtils.textareaInsertText(subCondiTextArea, " " + item + " ");
			}
		});

		jList4.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "and", "or" };

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}

			@Override
			public int getSize() {
				return strings.length;
			}
		});
		jList4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				String item = (String) jList4.getSelectedValue();
				if (item == null) {
					return;
				}
				CompUtils.textareaInsertText(mainCondiTextArea, " " + item + " ");
			}
		});

		jList3.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "=", ">", ">=", "<", "<=", "!=", "like" };

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}

			@Override
			public int getSize() {
				return strings.length;
			}
		});
		jList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				String item = (String) jList3.getSelectedValue();
				if (item == null) {
					return;
				}
				CompUtils.textareaInsertText(mainCondiTextArea, " " + item + " ");
			}
		});

		jList2.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getElementAt(int i) {
				return fields.get(i);
			}

			@Override
			public int getSize() {
				return fields.size();
			}
		});
		jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				XMLDto item = (XMLDto) jList2.getSelectedValue();
				if (item == null) {
					return;
				}
				CompUtils.textareaInsertText(mainCondiTextArea, " " + item.getValue("itemname"));
			}
		});

		jList6.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getElementAt(int i) {
				return fields.get(i);
			}

			@Override
			public int getSize() {
				return fields.size();
			}
		});
		jList6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				XMLDto item = (XMLDto) jList6.getSelectedValue();
				if (item == null) {
					return;
				}
				CompUtils.textareaInsertText(subCondiTextArea, " " + item.getValue("itemname"));
			}
		});

		initTable();
		// master List
		lstModel_master = new DefaultListModel();
		jList1.setModel(lstModel_master);
		jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() != 2) {
					return;
				}
				XMLDto dto = (XMLDto) jList1.getSelectedValue();
				if (dto == null) {
					return;
				}
				CompUtils.textareaInsertText(mainCondiTextArea, " " + dto.getValue("fieldname"));
			}
		});

		jList9.setModel(lstModel_master);
		jList9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				XMLDto dto = (XMLDto) jList9.getSelectedValue();
				if (dto == null) {
					return;
				}
				model_returnFieldList.addElement(dto.getValue("fieldname"));
			}
		});

		returnFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnFieldList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				int index = returnFieldList.getSelectedIndex();
				if (index < 0) {
					return;
				}
				model_returnFieldList.removeElementAt(index);
			}
		});
		// slaver List
		model_salver = new DefaultListModel();
		jList5.setModel(model_salver);
		jList5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				XMLDto dto = (XMLDto) jList5.getSelectedValue();
				if (dto == null) {
					return;
				}
				CompUtils.textareaInsertText(subCondiTextArea, " " + dto.getValue("fieldname"));
			}
		});
		model_returnFieldList = new DefaultListModel();
		returnFieldList.setModel(model_returnFieldList);

	}

	private void initTable() {
		jTable1 = new javax.swing.JTable();
		tablemodel = new DefaultTableModel(title, 0) {
			private static final long serialVersionUID = 1L;
			Class<?>[] types = new Class[] { String.class, String.class, Integer.class, Boolean.class, Boolean.class, String.class, java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				Boolean choose = true;
				if (index_codefied == column || index_relationfield == column && !choose.equals(jTable1.getValueAt(row, index_return))) {
					return false;
				}
				if ((index_width == column || index_newlabel == column || index_codefiedlabel == column) && !choose.equals(jTable1.getValueAt(row, index_visible))) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		};

		jTable1.setModel(tablemodel);
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(jTable1);
				int row = jTable1.getSelectedRow();
				int column = jTable1.getSelectedColumn();
				String itemname = (String) tablemodel.getValueAt(row, column);
				XMLDto value = CommonUtils.getXmlDto(fields, "itemname", itemname);
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				pnl.edit(dialog, null);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					if (value != null) {
						tablemodel.setValueAt(value.getValue("itemname"), row, column);
					} else {
						tablemodel.setValueAt("", row, column);
					}
				}
			}
		}, true);
		TableColumnModel cm = jTable1.getColumnModel();
		cm.getColumn(index_relationfield).setCellRenderer(editor.getTableCellRenderer());
		cm.getColumn(index_relationfield).setCellEditor(editor);
		CompUtils.setTableWdiths(jTable1, null, null, 0.12, 0.12, 0.12, null, 0.4);
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
						Class<?> clazz = tablemodel.getColumnClass(col);
						if (clazz.equals(Boolean.class)) {
							tablemodel.setValueAt(!(Boolean) tablemodel.getValueAt(row, col), row, col);
						}
						return;
					}
				}

			}
		});

		jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(jTable1);

	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		this.dialog.dispose();
	}

	private void initMaster() {
		lstModel_master.clear();
		model_returnFieldList.clear();
		mainCondiTextArea.setText("");
		if (masterFields == null || masterFields.isEmpty()) {
			return;
		}
		textField.setText(masterTable.toString());
		for (XMLDto dto : masterFields) {
			lstModel_master.addElement(dto);
		}

	};

	private void initSlave() {
		tablemodel.setRowCount(0);
		model_salver.clear();
		subCondiTextArea.setText("");
		textField_2.setText("");
		button_2.setEnabled(false);
		if (slaveFields == null || slaveFields.isEmpty()) {
			return;
		}
		button_2.setEnabled(true);
		textField_1.setText(slaveTable.toString());
		for (XMLDto dto : slaveFields) {
			model_salver.addElement(dto);
			tablemodel.addRow(new Object[] { dto.getValue("fieldlabel"), dto.getValue("fieldname"), 0, false, false, "", "" });
		}
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		this.props = props;
		this.submit = false;
		initData();
		dialog = GUIUtils.getDialog(owner, "代码中心主从表设置", this);
		dialog.setVisible(true);

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
			String[] aliass = alias.split(",");
			if (aliass.length > 1) {// 有两个表别名
				masterTable = CommonUtils.getXmlDto(CompUtils.getCodeTables(), "alias", aliass[0]);
				slaveTable = CommonUtils.getXmlDto(CompUtils.getCodeTables(), "alias", aliass[1]);
				if (masterTable == null && slaveTable == null) {
					if (masterTable == null) {
						GUIUtils.showMsg(dialog, aliass[0] + "表找不到");
					} else {
						GUIUtils.showMsg(dialog, aliass[1] + "表找不到");
					}
					return;
				}
				textField.setText(masterTable.toString());
				masterFields = InvokerServiceUtils.getCodeFields(masterTable.getValue("id"));
				initMaster();

				slaveFields = InvokerServiceUtils.getCodeFields(slaveTable.getValue("id"));
				textField_1.setText(aliass[1]);
				initSlave();
			} else {
				GUIUtils.showMsg(dialog, "初始化失败");
				return;
			}

			String RelationFieldName = paramMap.get("RelationFieldName");
			if (RelationFieldName != null) {
				RelationField.setText(RelationFieldName);
			}

			String returnFields = paramMap.get("returnFields");
			if (returnFields != null && !"".equals(returnFields)) {
				String[] fields = returnFields.split(",");
				for (String field : fields) {
					model_returnFieldList.addElement(field);
				}
			}

			String isMultiPage = paramMap.get("isMultiPage");
			String selectType = paramMap.get("selectType");
			String showCode = paramMap.get("showCode");
			String expendAll = paramMap.get("expendAll");
			if (!"0".equals(isMultiPage)) {
				checkBox.setSelected(false);
			}
			if ("1".equals(selectType)) {
				jRadioButton6.setSelected(true);
			} else {
				jRadioButton5.setSelected(true);
			}
			if ("0".equals(showCode)) {
				checkBox_2.setSelected(true);
			}
			if ("0".equals(expendAll)) {
				checkBox_1.setSelected(true);
			}
			checkBox_3.setSelected(!"F".equals(paramMap.get("hasparent")));
			String mainCondi = paramMap.get("mainCondi");
			if (!CommonUtils.isStrEmpty(mainCondi)) {
				mainCondiTextArea.setText(new String(CommonUtils.base64Dcode(mainCondi)));
			}
			String subCondi = paramMap.get("subCondi");
			if (!CommonUtils.isStrEmpty(subCondi)) {
				subCondiTextArea.setText(new String(CommonUtils.base64Dcode(subCondi)));
			}

			String windowparam = paramMap.get("windowparam");
			if (windowparam != null) {
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
				titleField.setText(paramMap.get("formtitle"));// 标题重置
				jSpinner1.setValue(Integer.parseInt(paramMap.get("height")));// 高度重置
				jSpinner2.setValue(Integer.parseInt(paramMap.get("width")));// 宽度重置
			}
			if (slaveFields != null && !slaveFields.isEmpty()) {
				String field = paramMap.get("field");// 利用参数对表格区域的初始化
				if (!CommonUtils.isStrEmpty(field)) {
					String[] fields = field.split(",");
					for (String field1 : fields) {
						if (CommonUtils.isStrEmpty(field1)) {
							continue;
						}
						String[] field2 = field1.split("\\\\b");
						String fieldname = field2[1];
						XMLDto fieldItem = CommonUtils.getXmlDto(slaveFields, "fieldname", fieldname);
						int index = slaveFields.indexOf(fieldItem);
						if (index < 0) {
							continue;
						}
						if ("yes".equals(field2[3])) {
							jTable1.setValueAt(true, index, index_visible);
							String temp = field2[2];
							int width = 80;
							if (CommonUtils.isNumberString(temp)) {
								width = Integer.parseInt(temp);
							}
							jTable1.setValueAt(width, index, index_width);
							jTable1.setValueAt(field2[0], index, index_codefiedlabel);
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
				textField_2.setText(orderfields);
			}

		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化失败");
			return;
		}
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(990, 690));
		jPanel10 = new javax.swing.JPanel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane12 = new javax.swing.JScrollPane();
		jList9 = new javax.swing.JList();
		jScrollPane13 = new javax.swing.JScrollPane();
		returnFieldList = new javax.swing.JList();
		jPanel12 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		mainCondiTextArea = new javax.swing.JTextArea();
		jPanel13 = new javax.swing.JPanel();
		jPanel13.setPreferredSize(new Dimension(0, 280));
		jPanel13.setBorder(new TitledBorder(null,
				"\u8BF4\u660E:\u4F7F\u7528\u4E86\u52A8\u6001\u6761\u4EF6\u7684\u7EC4\u5FC5\u987B\u7528[]\u62EC\u8D77\u6765,\u6761\u4EF6\u5B57\u6BB5\u540D\u5FC5\u987B\u7528{}\u62EC\u8D77\u6765,\u4E3E\u4F8B\u5982\u4E0B:1=1 [and project_code='{project_code}'] and project_type=1", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane3.setBounds(6, 20, 303, 254);
		jScrollPane3.setViewportBorder(new TitledBorder(null, "\u67E5\u8BE2\u7C7B\u8868\u5B57\u6BB5:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jList1 = new javax.swing.JList();
		jScrollPane4 = new javax.swing.JScrollPane();
		jScrollPane4.setBounds(309, 20, 365, 254);
		jScrollPane4.setViewportBorder(new TitledBorder(null, "\u67E5\u8BE2\u7C7B\u8868\u5B57\u6BB5:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jList2 = new javax.swing.JList();
		jScrollPane5 = new javax.swing.JScrollPane();
		jScrollPane5.setBounds(686, 20, 124, 254);
		jList3 = new javax.swing.JList();
		jScrollPane6 = new javax.swing.JScrollPane();
		jScrollPane6.setBounds(820, 20, 83, 254);
		jList4 = new javax.swing.JList();
		jPanel15 = new javax.swing.JPanel();
		jScrollPane7 = new javax.swing.JScrollPane();
		subCondiTextArea = new javax.swing.JTextArea();
		jPanel16 = new javax.swing.JPanel();
		jPanel16.setPreferredSize(new Dimension(0, 280));
		jPanel16.setBorder(new TitledBorder(null,
				"\u8BF4\u660E:\u4F7F\u7528\u4E86\u52A8\u6001\u6761\u4EF6\u7684\u7EC4\u5FC5\u987B\u7528[]\u62EC\u8D77\u6765,\u6761\u4EF6\u5B57\u6BB5\u540D\u5FC5\u987B\u7528{}\u62EC\u8D77\u6765,\u4E3E\u4F8B\u5982\u4E0B:1=1 [and project_code='{project_code}'] and project_type=1", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		jScrollPane8 = new javax.swing.JScrollPane();
		jScrollPane8.setBounds(6, 20, 303, 254);
		jScrollPane8.setViewportBorder(new TitledBorder(null, "\u4E1A\u52A1\u8868\u5B57\u6BB5:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jList5 = new javax.swing.JList();
		jScrollPane9 = new javax.swing.JScrollPane();
		jScrollPane9.setBounds(309, 20, 365, 254);
		jScrollPane9.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u67E5\u8BE2\u7C7B\u8868\u5B57\u6BB5:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jList6 = new javax.swing.JList();
		jScrollPane10 = new javax.swing.JScrollPane();
		jScrollPane10.setBounds(686, 20, 124, 254);
		jList7 = new javax.swing.JList();
		jScrollPane11 = new javax.swing.JScrollPane();
		jScrollPane11.setBounds(820, 20, 83, 254);
		jList8 = new javax.swing.JList();
		jPanel11 = new javax.swing.JPanel();

		jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("条件相关设置"));
		jPanel10.setName("jPanel10"); // NOI18N
		jPanel10.setLayout(new java.awt.BorderLayout());

		jTabbedPane1.setName("jTabbedPane1"); // NOI18N

		jScrollPane1.setName("jScrollPane1"); // NOI18N
		JPanel panl = new JPanel(new BorderLayout());
		panl.add(jScrollPane1, BorderLayout.CENTER);
		jTabbedPane1.addTab("从表字段设置窗口", panl);

		JPanel panel_1 = new JPanel();
		panl.add(panel_1, BorderLayout.SOUTH);

		JLabel label = new JLabel("\u6392\u5E8F\u5B57\u6BB5\uFF1A");
		panel_1.add(label);

		textField_2 = new JTextField();
		panel_1.add(textField_2);
		textField_2.setColumns(40);

		button_2 = new JButton("\u8BBE\u7F6E\u6392\u5E8F\u5B57\u6BB5");
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSortFileds();
			}

		});
		panel_1.add(button_2);

		jPanel3.setName("jPanel3"); // NOI18N

		jScrollPane12.setBorder(javax.swing.BorderFactory.createTitledBorder("主表所有的字段"));
		jScrollPane12.setName("jScrollPane12"); // NOI18N

		jList9.setName("jList9"); // NOI18N

		jScrollPane12.setViewportView(jList9);

		jScrollPane13.setBorder(javax.swing.BorderFactory.createTitledBorder("建树所需要的字段"));
		jScrollPane13.setName("jScrollPane13"); // NOI18N

		returnFieldList.setName("returnFieldList"); // NOI18N

		jScrollPane13.setViewportView(returnFieldList);

		jTabbedPane1.addTab("主表字段设置窗口", jPanel3);
		jPanel3.setLayout(new GridLayout(0, 2, 0, 0));
		jPanel3.add(jScrollPane12);
		jPanel3.add(jScrollPane13);

		jScrollPane2.setName("jScrollPane2"); // NOI18N

		mainCondiTextArea.setColumns(20);
		mainCondiTextArea.setLineWrap(true);
		mainCondiTextArea.setRows(6);
		mainCondiTextArea.setName("mainCondiTextArea"); // NOI18N
		jScrollPane2.setViewportView(mainCondiTextArea);

		jScrollPane3.setViewportView(jList1);

		jScrollPane4.setViewportView(jList2);

		jScrollPane5.setViewportView(jList3);

		jScrollPane6.setViewportView(jList4);
		jPanel13.setLayout(null);
		jPanel13.add(jScrollPane3);
		jPanel13.add(jScrollPane4);
		jPanel13.add(jScrollPane5);
		jPanel13.add(jScrollPane6);

		jTabbedPane1.addTab("主表过滤条件设置", jPanel12);
		jPanel12.setLayout(new BorderLayout(0, 0));
		jPanel12.add(jScrollPane2);
		jPanel12.add(jPanel13, BorderLayout.SOUTH);

		jPanel15.setName("jPanel15"); // NOI18N

		jScrollPane7.setName("jScrollPane7"); // NOI18N

		subCondiTextArea.setColumns(20);
		subCondiTextArea.setLineWrap(true);
		subCondiTextArea.setRows(6);
		subCondiTextArea.setName("subCondiTextArea"); // NOI18N
		jScrollPane7.setViewportView(subCondiTextArea);

		jScrollPane8.setViewportView(jList5);

		jScrollPane9.setViewportView(jList6);

		jScrollPane10.setViewportView(jList7);

		jScrollPane11.setViewportView(jList8);
		jPanel16.setLayout(null);
		jPanel16.add(jScrollPane8);
		jPanel16.add(jScrollPane9);
		jPanel16.add(jScrollPane10);
		jPanel16.add(jScrollPane11);

		jTabbedPane1.addTab("从表过滤条件设置", jPanel15);
		jPanel15.setLayout(new BorderLayout(0, 0));
		jPanel15.add(jScrollPane7);
		jPanel15.add(jPanel16, BorderLayout.SOUTH);

		jPanel10.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

		jPanel11.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		okButton = new javax.swing.JButton();
		jPanel11.add(okButton);

		okButton.setText("确定");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				save();
			}
		});
		cancelButton = new javax.swing.JButton();
		jPanel11.add(cancelButton);

		cancelButton.setText("取消");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
		setLayout(new BorderLayout(0, 0));
		add(jPanel11, BorderLayout.SOUTH);
		add(jPanel10);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 150));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setBounds(0, 0, 438, 157);
		panel.add(jPanel1);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(45, 22, 30, 16);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(45, 55, 30, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(6, 90, 69, 16);
		RelationField = new javax.swing.JTextField();
		RelationField.setBounds(81, 84, 351, 28);
		jLabel10 = new javax.swing.JLabel();
		jLabel10.setBounds(6, 118, 380, 15);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("代码中心数据表"));

		jLabel1.setText("主表:");

		jLabel2.setText("从表:");

		jLabel3.setText("关联字段名:");

		jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		jLabel10.setText("说明:关联字段名是从表的字段;点树节点以后用此字段来过滤表格的数据");
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel3);
		jPanel1.add(RelationField);
		jPanel1.add(jLabel10);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(81, 16, 284, 28);
		jPanel1.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getCodeTablePnl(masterTable);
				pnl.edit(dialog, null);
				if (!pnl.isChange()) {
					return;
				}
				masterTable = pnl.getSelect();
				try {
					masterFields = InvokerServiceUtils.getCodeFields(masterTable.getValue("id"));
				} catch (Exception ex) {
					GUIUtils.showMsg(dialog, "获取代码中心字段失败");
					logger.error(ex.getMessage(), ex);
				}
				initMaster();
			}
		});
		button.setBounds(365, 17, 60, 29);
		jPanel1.add(button);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(81, 49, 284, 28);
		jPanel1.add(textField_1);

		JButton button_1 = new JButton("...");
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getCodeTablePnl(slaveTable);
				pnl.edit(dialog, null);
				if (!pnl.isChange()) {
					return;
				}
				slaveTable = pnl.getSelect();
				if (slaveTable == null) {
					slaveFields.clear();
				} else {
					try {
						slaveFields = InvokerServiceUtils.getCodeFields(slaveTable.getValue("id"));
					} catch (Exception ex) {
						GUIUtils.showMsg(dialog, "获取代码中心字段失败");
						logger.error(ex.getMessage(), ex);
					}
				}
				initSlave();
			}

		});
		button_1.setBounds(365, 48, 60, 29);
		jPanel1.add(button_1);
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(438, 0, 546, 157);
		panel.add(jPanel2);
		jPanel5 = new javax.swing.JPanel();
		jPanel5.setBounds(12, 18, 134, 47);
		jRadioButton5 = new javax.swing.JRadioButton();
		jRadioButton5.setBounds(6, 18, 58, 23);
		jRadioButton6 = new javax.swing.JRadioButton();
		jRadioButton6.setBounds(70, 18, 58, 23);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(12, 89, 30, 16);
		jLabel8 = new javax.swing.JLabel();
		jLabel8.setBounds(12, 123, 56, 16);
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setBounds(213, 123, 56, 16);
		titleField = new javax.swing.JTextField();
		titleField.setBounds(54, 83, 372, 28);
		jSpinner1 = new javax.swing.JSpinner();
		jSpinner1.setBounds(86, 117, 74, 28);
		jSpinner2 = new javax.swing.JSpinner();
		jSpinner2.setBounds(287, 117, 74, 28);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("参数设置"));
		jPanel2.setName("jPanel2"); // NOI18N

		jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("选择方式"));
		jPanel5.setName("jPanel5"); // NOI18N
		buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(jRadioButton5);
		jRadioButton5.setSelected(true);
		jRadioButton5.setText("单选");
		jRadioButton5.setName("jRadioButton5"); // NOI18N

		buttonGroup2.add(jRadioButton6);
		jRadioButton6.setText("多选");
		jRadioButton6.setName("jRadioButton6");

		jLabel4.setText("标题:");
		jLabel4.setName("jLabel4"); // NOI18N

		jLabel8.setText("默认高度:");
		jLabel8.setName("jLabel8"); // NOI18N

		jLabel9.setText("默认宽度:");
		jLabel9.setName("jLabel9"); // NOI18N

		titleField.setName("titleField"); // NOI18N

		jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(800), Integer.valueOf(0), null, Integer.valueOf(10)));
		jSpinner1.setName("jSpinner1"); // NOI18N
		jSpinner1.setValue(800);

		jSpinner2.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(600), Integer.valueOf(0), null, Integer.valueOf(10)));
		jSpinner2.setName("jSpinner2"); // NOI18N
		jSpinner2.setValue(600);
		jPanel2.setLayout(null);
		jPanel2.add(jPanel5);
		jPanel5.setLayout(null);
		jPanel5.add(jRadioButton5);
		jPanel5.add(jRadioButton6);
		jPanel2.add(jLabel4);
		jPanel2.add(titleField);
		jPanel2.add(jLabel8);
		jPanel2.add(jSpinner1);
		jPanel2.add(jLabel9);
		jPanel2.add(jSpinner2);

		checkBox = new JCheckBox("\u662F\u5426\u5206\u9875");
		checkBox.setSelected(true);
		checkBox.setBounds(158, 34, 84, 23);
		jPanel2.add(checkBox);

		checkBox_1 = new JCheckBox("\u5C55\u5F00\u5168\u90E8");
		checkBox_1.setBounds(245, 34, 84, 23);
		jPanel2.add(checkBox_1);

		checkBox_2 = new JCheckBox("\u663E\u793A\u4EE3\u7801");
		checkBox_2.setBounds(330, 34, 84, 23);
		jPanel2.add(checkBox_2);

		checkBox_3 = new JCheckBox("\u662F\u5426\u8865\u4E0A\u7EA7");
		checkBox_3.setSelected(true);
		checkBox_3.setBounds(423, 34, 97, 23);
		jPanel2.add(checkBox_3);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

	private void setSortFileds() {
		List<XMLDto> all = new ArrayList<XMLDto>();
		List<String> toStringPros = Arrays.asList("itemlabel", "itemname", "direc");
		for (XMLDto f : slaveFields) {
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
			textField_2.setText(sb.toString());
		}

	}

	private void save() {
		try {
			CompUtils.stopTabelCellEditor(jTable1);
			StringBuffer outparam = new StringBuffer();
			String alias = "";
			if (masterTable != null && slaveTable != null) {
				alias = masterTable.getValue("alias") + "," + slaveTable.getValue("alias");
			} else {
				GUIUtils.showMsg(dialog, "代码中心表名不能为空");
				return;
			}

			String isMultiPage = checkBox.isSelected() ? "0" : "1";// 是否分页
			String selectType = jRadioButton5.isSelected() ? "0" : "1";// 单多选
			String expendAll = checkBox_1.isSelected() ? "0" : "1";// 是否展开全部
			String showCode = checkBox_2.isSelected() ? "0" : "1";// 是否显示代码
			String hasparent = checkBox_3.isSelected() ? "T" : "F";
			StringBuffer returnField = new StringBuffer("");
			for (int i = 0; i < returnFieldList.getModel().getSize(); i++) {
				if (i < returnFieldList.getModel().getSize() - 1) {
					returnField.append(returnFieldList.getModel().getElementAt(i)).append(",");
				} else {
					if (i == returnFieldList.getModel().getSize() - 1) {
						returnField.append(returnFieldList.getModel().getElementAt(i));
					}
				}
			}

			String width, height = "";
			height = String.valueOf(jSpinner1.getValue());
			width = String.valueOf(jSpinner2.getValue());// 表宽和高

			String formtitle = titleField.getText();

			StringBuffer windowparam = new StringBuffer("");
			windowparam.append("formtitle:").append(formtitle).append("||").append("width:").append(width).append("||").append("height:").append(height).append("||").append("field:");
			if (slaveFields != null && !slaveFields.isEmpty()) {
				Boolean choose = true;
				for (int i = 0; i < jTable1.getRowCount(); i++) {
					if (!choose.equals(jTable1.getValueAt(i, index_visible)) && !choose.equals(jTable1.getValueAt(i, index_return))) {
						continue;
					}
					String fieldlable = (String) jTable1.getValueAt(i, index_codefiedlabel);
					String fieldname = (String) jTable1.getValueAt(i, index_codefied);
					if (choose.equals(jTable1.getValueAt(i, index_visible))) {
						windowparam.append(fieldlable).append("\\b").append(fieldname).append("\\b").append(jTable1.getValueAt(i, index_width)).append("\\byes");
					} else {
						windowparam.append(fieldlable).append("\\b").append(fieldname).append("\\b").append(jTable1.getValueAt(i, index_width)).append("\\bno");
					}

					if (choose.equals(jTable1.getValueAt(i, index_return))) {
						String temp = (String) jTable1.getValueAt(i, index_relationfield);
						if (!CommonUtils.isStrEmpty(temp)) {
							XMLDto relationField = CommonUtils.getXmlDto(fields, "itemname", temp);
							if (relationField == null) {
								jTable1.setRowSelectionInterval(i, i);
								GUIUtils.showMsg(dialog, "请选择[对应的表单项目]");
								return;
							}
							windowparam.append("\\byes\\b").append(relationField.getValue("itemname")).append("\\c").append(relationField.getValue("itemlabel"));
							outparam.append(relationField.getValue("itemname")).append("=").append(fieldname).append(":").append(fieldlable).append(",");
						} else {
							GUIUtils.showMsg(dialog, "请选择[对应的表单项目]");
							jTable1.setRowSelectionInterval(i, i);
							return;
						}
					} else {
						windowparam.append("\\bno\\b");
					}
					windowparam.append(",");
				}
			}

			if (windowparam.indexOf(",") > 0) {
				int del = windowparam.lastIndexOf(",");
				windowparam.delete(del, del + 1);
			}
			if (outparam.indexOf(",") > 0) {
				int del = outparam.lastIndexOf(",");
				outparam.delete(del, del + 1);
			}

			String mainCondi = mainCondiTextArea.getText();
			String subCondi = subCondiTextArea.getText();

			StringBuffer inParam = new StringBuffer();
			inParam.append("alias=").append(alias).append(";");
			inParam.append("isMultiPage=").append(isMultiPage).append(";");
			inParam.append("selectType=").append(selectType).append(";");
			inParam.append("expendAll=").append(expendAll).append(";");
			inParam.append("showCode=").append(showCode).append(";");
			inParam.append("RelationFieldName=").append(RelationField.getText()).append(";");
			inParam.append("returnFields=").append(returnField).append(";");
			inParam.append("windowparam=").append(windowparam).append(";");
			inParam.append("mainCondi=").append(CommonUtils.base64Encode(mainCondi.getBytes())).append(";");
			inParam.append("subCondi=").append(CommonUtils.base64Encode(subCondi.getBytes())).append(";");
			String orderbyfileds = textField_2.getText();
			inParam.append("orderfields=").append(orderbyfileds).append(";");
			inParam.append("hasparent=").append(hasparent);
			this.props.put("inparam", inParam.toString());
			this.props.put("outparam", outparam.toString());
			submit = true;
			this.dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}

	}
}
