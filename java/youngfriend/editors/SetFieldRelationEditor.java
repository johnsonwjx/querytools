package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.ShowPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class SetFieldRelationEditor extends JPanel implements PropEditor {
	private static final String[] columnNames = new String[] { "源表字段", "选择", "临时表字段", "主键", "更新父节点关联字段", "底层" };
	private static final int INDEX_SRC = 0;
	private static final int INDEX_SELECT = 1;
	private static final int INDEX_TEMP = 2;
	private static final int INDEX_PRIMARY = 3;
	private static final int INDEX_FATHER = 4;
	private static final int INDEX_BOTTOM = 5;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private Logger logger = null;
	private JList list;
	private JButton button_6;
	private DefaultTableModel tableModel;
	private DefaultListModel listModel;
	private JTextArea textArea;
	private List<XMLDto> tablelst = new ArrayList<XMLDto>();
	private XMLDto curTable = null;
	private JButton btnxml;
	private boolean flag = false;
	private String serviceName = "";

	public SetFieldRelationEditor() {
		this.setPreferredSize(new Dimension(880, 662));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);

		radioButton = new JRadioButton("\u4EE3\u7801\u4E2D\u5FC3");

		radioButton.setSelected(true);
		panel_4.add(radioButton);
		button_6 = new JButton("\u83B7\u53D6");
		button_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getTableLstByService(textField_1.getText().trim());
				GUIUtils.showMsg(dialog, "获取成功");
			}
		});

		radioButton_1 = new JRadioButton("\u901A\u8FC7\u670D\u52A1");

		panel_4.add(radioButton_1);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panel_4.add(textField_1);
		textField_1.setColumns(17);

		button_6.setEnabled(false);
		panel_4.add(button_6);

		ButtonGroup bg = new ButtonGroup();
		Component[] coms = panel_4.getComponents();
		for (Component c : coms) {
			if (c instanceof AbstractButton) {
				bg.add((AbstractButton) c);
			}

		}

		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);

		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == INDEX_SRC) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		};
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
			private Class<?>[] columnClasses = new Class<?>[] { String.class, Boolean.class, String.class, Boolean.class, Boolean.class, Boolean.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnClasses[columnIndex];
			}
		};
		table.setModel(tableModel);
		scrollPane_1.setViewportView(table);

		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);

		JLabel label = new JLabel("\u8868\u540D");
		panel_6.add(label);

		textField = new JTextField();
		textField.setEditable(false);
		panel_6.add(textField);
		textField.setColumns(28);

		JButton button_3 = new JButton("...");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectTable();
			}
		});
		panel_6.add(button_3);

		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.SOUTH);
		panel_7.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setPreferredSize(new Dimension(0, 100));
		scrollPane_2.setViewportBorder(new TitledBorder(null, "\u81EA\u5B9A\u4E49\u53D6\u6570\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.add(scrollPane_2, BorderLayout.NORTH);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane_2.setViewportView(textArea);

		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8, BorderLayout.SOUTH);

		JButton button_4 = new JButton("\u4FDD\u5B58\u5230\u5DE6\u8FB9\u5217\u8868");
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save2List();
			}
		});
		panel_8.add(button_4);
		btnxml = new JButton("\u9884\u89C8\u8BBE\u7F6Exml");
		panel_8.add(btnxml);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}
		});
		panel_1.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel_1.add(button_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_2.setPreferredSize(new Dimension(200, 0));
		add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);

		list = new JList();
		listModel = new DefaultListModel();
		list.setModel(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.SOUTH);

		JButton button_2 = new JButton("\u5220\u9664");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index == -1) {
					return;
				}
				listModel.removeElementAt(index);
				if (!listModel.isEmpty()) {
					if (index != 0) {
						index--;
					}
				}
				list.setSelectedIndex(index);
			}
		});
		panel_3.add(button_2);

		init();
	}

	private void init() {
		table.getColumnModel().getColumn(INDEX_SRC).setPreferredWidth(250);
		table.getColumnModel().getColumn(INDEX_TEMP).setPreferredWidth(150);
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(table);
				XMLDto value = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				pnl.edit(dialog, null);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					if (pnl.isNull()) {
						table.setValueAt("", table.getSelectedRow(), table.getSelectedColumn());
					} else {
						table.setValueAt(value.getValue("itemname"), table.getSelectedRow(), table.getSelectedColumn());
					}
				}
			}
		}, true);
		table.getColumnModel().getColumn(INDEX_TEMP).setCellEditor(editor);
		table.getColumnModel().getColumn(INDEX_TEMP).setCellRenderer(editor.getTableCellRenderer());

		btnxml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String xml = getParamsXML();
				if (xml == null) {
					return;
				}
				new ShowPnl(dialog, 300, 300, xml);
			}

		});
		radioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (flag == true) {
					return;
				}
				tablelst = null;
				curTable = null;
				textField_1.setEditable(e.getStateChange() == ItemEvent.DESELECTED);
				button_6.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
				serviceName = "";
				textField_1.setText(serviceName);
				updateTable();
			}
		});

		// list size 小于2 时 选变化不了
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (listModel.getSize() > 1) {
					return;
				}
				XMLDto dto = (XMLDto) list.getSelectedValue();
				updateUI(dto);
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listModel.getSize() <= 1) {
					return;
				}
				if (flag == true) {
					return;
				}
				if (e.getValueIsAdjusting()) {
					return;
				}
				XMLDto dto = (XMLDto) list.getSelectedValue();
				updateUI(dto);
			}
		});
	}

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textField;
	private JDialog dialog;
	private JTextField textField_1;
	private DefaultPropEditor defaultpropeditor;

	private void selectTable() {
		ObjectSelectPnl<XMLDto> pnl = null;
		if (radioButton.isSelected()) {
			if (CompUtils.getCodeTables().isEmpty()) {
				GUIUtils.showMsg(dialog, "表格信息为空");
				return;
			}
			pnl = CompUtils.getCodeTablePnl(null);
		} else {
			if (tablelst == null || tablelst.isEmpty()) {
				GUIUtils.showMsg(dialog, "表格信息为空,请获取");
				return;
			}
			pnl = new ObjectSelectPnl<XMLDto>(tablelst);
		}
		pnl.setValue(curTable);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		curTable = pnl.getSelect();
		int index = getListIndexByCurTable();
		if (index != -1 && index != list.getSelectedIndex()) {
			// list 有对应的表格
			list.setSelectedIndex(index);
			return;
		}
		updateTable();
	}

	private int getListIndexByCurTable() {
		if (curTable == null) {
			return -1;
		}
		for (int i = 0; i < listModel.getSize(); i++) {
			XMLDto dto = (XMLDto) listModel.getElementAt(i);
			if (dto.getObject(XMLDto.class, "table").equals(curTable)) {
				return i;
			}
		}
		return -1;
	}

	private void updateTable() {
		tableModel.setRowCount(0);
		textField.setText("");
		if (curTable == null) {
			return;
		}
		textField.setText(curTable.toString());
		if (radioButton.isSelected()) {
			// 代码中心
			try {
				List<XMLDto> fields = InvokerServiceUtils.getCodeFields(curTable.getValue("id"));
				if (fields == null || fields.isEmpty()) {
					return;
				}
				for (XMLDto field : fields) {
					tableModel.addRow(new Object[] { field, false, "", false, false, false });
				}

			} catch (Exception e) {
				GUIUtils.showMsg(dialog, "获取失败");
				logger.error(e.getMessage(), e);
			}

		} else {
			List<XMLDto> fields = CommonUtils.getFieldsByServiceTable(curTable);
			if (fields == null || fields.isEmpty()) {
				return;
			}
			for (XMLDto field : fields) {
				tableModel.addRow(new Object[] { field, false, "", false, false, false });
			}
		}
	}

	private void getTableLstByService(String service) {
		if (CommonUtils.isStrEmpty(service)) {
			GUIUtils.showMsg(dialog, "请输入服务名(像 store2)");
			return;
		}
		if (service.equals(serviceName)) {
			return;
		}
		serviceName = service;
		curTable = null;
		updateTable();
		tablelst = CommonUtils.getTableByService(serviceName);
	}

	private void save2List() {
		String xml = getParamsXML();
		if (xml == null) {
			return;
		}
		int index = getListIndexByCurTable();
		XMLDto dto = null;
		if (index == -1) {
			flag = true;
			dto = new XMLDto("name");
			listModel.addElement(dto);
		} else {
			dto = (XMLDto) listModel.getElementAt(index);
		}
		dto.setValue("name", curTable.getValue(radioButton.isSelected() ? "name" : "cname"));
		dto.setValue("xml", xml);
		dto.setValue("table", curTable);
		list.updateUI();
		if (flag == true) {
			list.setSelectedValue(dto, true);
			flag = false;
		}
		GUIUtils.showMsg(dialog, "保存成功");
	}

	private String getParamsXML() {
		CompUtils.stopTabelCellEditor(table);
		if (!validateData()) {
			return null;
		}
		Element params = DocumentHelper.createElement("params");
		params.addElement("servicename").setText(textField_1.getText());
		String tablename = "";
		String tablecnname = "";
		if (radioButton.isSelected()) {
			// 代码中心
			tablename = curTable.getValue("alias");
			tablecnname = curTable.getValue("name");
		} else {
			tablename = curTable.getValue("name");
			tablecnname = curTable.getValue("cname");
		}
		params.addElement("tablename").setText(tablename);
		params.addElement("tablecnname").setText(tablecnname);

		String keyfieldname = "", otherfieldname = "", parentcode = "", leaf_field = "";
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			boolean select = (Boolean) tableModel.getValueAt(i, INDEX_SELECT);
			if (!select) {
				continue;
			}
			XMLDto src = (XMLDto) tableModel.getValueAt(i, INDEX_SRC);
			String srcname = "";
			if (radioButton.isSelected()) {
				// 代码中心
				srcname = src.getValue("fieldname");
			} else {
				srcname = src.getValue("name");
			}

			String temp = (String) tableModel.getValueAt(i, INDEX_TEMP);
			boolean primaryValue = (Boolean) tableModel.getValueAt(i, INDEX_PRIMARY);
			if (primaryValue) {
				keyfieldname = srcname + ":" + temp;
			} else {
				otherfieldname += srcname + ":" + temp + ",";
			}
			boolean fatherValue = (Boolean) tableModel.getValueAt(i, INDEX_FATHER);
			if (fatherValue) {
				parentcode = srcname + ":" + temp;
			}
			boolean bottomValue = (Boolean) tableModel.getValueAt(i, INDEX_BOTTOM);
			if (bottomValue) {
				leaf_field = srcname;
			}
		}
		if (!CommonUtils.isStrEmpty(otherfieldname)) {
			otherfieldname = otherfieldname.substring(0, otherfieldname.length() - 1);
		}
		params.addElement("keyfieldname").setText(keyfieldname);
		params.addElement("otherfieldname").setText(otherfieldname);
		params.addElement("parentcode").setText(parentcode);
		params.addElement("percentage");
		String condi = "";
		String temp = textArea.getText();
		if (!CommonUtils.isStrEmpty(temp)) {
			condi = CommonUtils.base64Encode(temp.getBytes());
		}
		params.addElement("condi").setText(condi);
		params.addElement("leaf_field").setText(leaf_field);
		return params.asXML();
	}

	private void updateUI(XMLDto dto) { // 清空界面
		if (dto == null) {
			return;
		}
		String xml = dto.getValue("xml");
		if (CommonUtils.isStrEmpty(xml)) {
			return;
		}
		try {
			String keyfieldname = null, otherfieldname = null, parentcode = null, leaf_field = null, condi = null;
			Document doc = DocumentHelper.parseText(xml);
			String serviceTemp = doc.selectSingleNode("//servicename").getText();
			flag = true;
			radioButton.setSelected(CommonUtils.isStrEmpty(serviceTemp));
			radioButton_1.setSelected(!CommonUtils.isStrEmpty(serviceTemp));
			if (radioButton_1.isSelected()) {
				getTableLstByService(serviceTemp);
			}
			serviceName = serviceTemp;
			textField_1.setText(serviceName);
			flag = false;
			curTable = dto.getObject(XMLDto.class, "table");
			updateTable();
			if (curTable == null) {
				return;
			}
			Element temp = null;
			temp = (Element) doc.selectSingleNode("//keyfieldname");
			if (temp != null) {
				keyfieldname = temp.getText();
			}

			temp = (Element) doc.selectSingleNode("//otherfieldname");
			if (temp != null) {
				otherfieldname = temp.getText();
			}

			temp = (Element) doc.selectSingleNode("//parentcode");
			if (temp != null) {
				parentcode = temp.getText();
			}

			temp = (Element) doc.selectSingleNode("//leaf_field");
			if (temp != null) {
				leaf_field = temp.getText();
			}
			temp = (Element) doc.selectSingleNode("//condi");
			if (temp != null) {
				condi = temp.getText();
			}
			updateTableParam(keyfieldname, otherfieldname, parentcode, leaf_field, condi);
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化界面失败");
			logger.error(e.getMessage(), e);
		}
	}

	private void updateTableParam(String keyfieldname, String otherfieldname, String parentcode, String leaf_field, String condi) {
		String fieldProp = "fieldname";
		if (radioButton_1.isSelected()) {
			fieldProp = "name";
		}
		Map<String, String> otherfieldMap = new HashMap<String, String>();
		if (!CommonUtils.isStrEmpty(otherfieldname)) {
			String[] items = otherfieldname.split(",");
			if (items.length > 0) {
				for (String item : items) {
					String[] arr = item.split(":");
					if (arr.length == 2) {
						otherfieldMap.put(arr[0], arr[1]);
					}
				}
			}
		}
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			XMLDto src = (XMLDto) tableModel.getValueAt(i, INDEX_SRC);
			// 主键
			if (!CommonUtils.isStrEmpty(keyfieldname)) {
				String[] srctemp = keyfieldname.split(":");
				if (srctemp.length == 2) {
					String fiendName = srctemp[0];
					if (src.getValue(fieldProp).equalsIgnoreCase(fiendName)) {
						tableModel.setValueAt(true, i, INDEX_SELECT);
						tableModel.setValueAt(true, i, INDEX_PRIMARY);
						String tempName = srctemp[1];
						tableModel.setValueAt(tempName, i, INDEX_TEMP);
					}

				}
			}
			// 其它字段
			if (otherfieldMap.containsKey(src.getValue(fieldProp))) {
				tableModel.setValueAt(true, i, INDEX_SELECT);
				tableModel.setValueAt(otherfieldMap.get(src.getValue(fieldProp)), i, INDEX_TEMP);
			}

			if (!(Boolean) tableModel.getValueAt(i, INDEX_SELECT)) {
				continue;
			}
			// 已选的

			// 更新父字段
			if (!CommonUtils.isStrEmpty(parentcode)) {
				String[] srctemp = parentcode.split(":");
				if (srctemp.length > 0) {
					String fiendName = srctemp[0];
					if (src.getValue(fieldProp).equalsIgnoreCase(fiendName)) {
						tableModel.setValueAt(true, i, INDEX_FATHER);
					}

				}
			}

			// 底层字段
			if (!CommonUtils.isStrEmpty(leaf_field)) {
				if (src.getValue(fieldProp).equalsIgnoreCase(leaf_field)) {
					tableModel.setValueAt(true, i, INDEX_BOTTOM);
				}

			}

			// 条件
			if (!CommonUtils.isStrEmpty(condi)) {
				textArea.setText(new String(CommonUtils.base64Dcode(condi)));
			} else {
				textArea.setText("");
			}
		}

	}

	private boolean validateData() {
		if (curTable == null) {
			return false;
		}
		int primaryCount = 0;
		int fatherCount = 0;
		int bottomCount = 0;
		boolean selected = false;
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			boolean select = (Boolean) tableModel.getValueAt(i, INDEX_SELECT);
			if (select) {
				String temp = (String) tableModel.getValueAt(i, INDEX_TEMP);
				if (CommonUtils.isStrEmpty(temp)) {
					GUIUtils.showMsg(dialog, "存在对应临时表字段没设置");
					return false;
				}
				selected = true;
			} else {
				continue;
			}
			boolean primaryValue = (Boolean) tableModel.getValueAt(i, INDEX_PRIMARY);
			if (primaryValue) {
				primaryCount++;
			}
			boolean fatherValue = (Boolean) tableModel.getValueAt(i, INDEX_FATHER);
			if (fatherValue) {
				fatherCount++;
			}
			boolean bottomValue = (Boolean) tableModel.getValueAt(i, INDEX_BOTTOM);
			if (bottomValue) {
				bottomCount++;
			}
		}
		if (!selected) {
			GUIUtils.showMsg(dialog, "请设置字段");
			return false;
		}
		if (primaryCount == 0) {
			GUIUtils.showMsg(dialog, "请设置主键");
			return false;
		}
		if (primaryCount > 1) {
			GUIUtils.showMsg(dialog, "只能设置一个主键字段");
			return false;
		}
		if (fatherCount > 1) {
			GUIUtils.showMsg(dialog, "只能设置一个更新父亲字段");
			return false;
		}
		if (bottomCount > 1) {
			GUIUtils.showMsg(dialog, "只能设置一个底层字段");
			return false;
		}
		return true;
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {
			@Override
			public boolean save() {
				if (listModel.isEmpty()) {
					prop.setValue("");
				} else {
					Document doc = DocumentHelper.createDocument();
					Element root = DocumentHelper.createElement("root");
					doc.setRootElement(root);
					for (int i = 0; i < listModel.getSize(); i++) {
						XMLDto dto = (XMLDto) listModel.getElementAt(i);
						String xml = dto.getValue("xml");
						if (!CommonUtils.isStrEmpty(xml)) {
							Document ele;
							try {
								ele = DocumentHelper.parseText(xml);
							} catch (DocumentException e) {
								throw new RuntimeException(e);
							}
							root.add(ele.getRootElement());
						}
					}
					prop.setValue(doc.asXML());
				}
				return true;
			}

			@Override
			public void initData() {

				Document doc;
				try {
					doc = DocumentHelper.parseText(prop.getValue());
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
				List<Element> eles = doc.getRootElement().elements();
				if (!eles.isEmpty()) {
					String tablecnname = "";
					XMLDto table = null;
					for (Element ele : eles) {
						String tablename = ele.elementText("tablename");
						String servicename = ele.elementText("servicename");
						if (CommonUtils.isStrEmpty(servicename)) {
							table = CommonUtils.getXmlDto(CompUtils.getCodeTables(), "alias", tablename);
							if (table == null) {
								continue;
							}
							tablecnname = table.getValue("name");
						} else {
							getTableLstByService(servicename);
							table = CommonUtils.getXmlDto(tablelst, "name", tablename);
							if (table == null) {
								continue;
							}
							tablecnname = table.getValue("cname");
						}
						XMLDto dto = new XMLDto("name");
						dto.setValue("name", tablecnname);
						dto.setValue("xml", ele.asXML());
						dto.setValue("table", table);
						listModel.addElement(dto);
					}
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		dialog = defaultpropeditor.getDialog();
		logger = defaultpropeditor.getLogger();
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}
}
