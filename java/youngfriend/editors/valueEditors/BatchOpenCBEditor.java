package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import youngfriend.common.util.StringUtils;
import youngfriend.editors.valueEditors.BatchOpenCBEditor.BatchWorkDto.WorkPubDto;
import youngfriend.editors.valueEditors.BatchOpenCBEditor.BatchWorkDto.WorkPubDto.Item;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BatchOpenCBEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private JButton btnNewButton_2;
	private JButton btnNewButton_1;
	private JButton button_3;
	private GsonBuilder builder = new GsonBuilder();

	public BatchOpenCBEditor() {
		this.setPreferredSize(new Dimension(884, 619));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.3);
		panel_1.add(splitPane, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);

		list = new JList();
		scrollPane.setViewportView(list);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		scrollPane.setColumnHeaderView(toolBar);

		JButton button_4 = new JButton(" \u5220\u9664 ");
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delWork();
			}
		});

		JButton btnNewButton_3 = new JButton("\u6DFB\u52A0");
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addWork();
			}
		});
		toolBar.add(btnNewButton_3);

		JButton button_2 = new JButton("\u9009\u62E9\u4E1A\u52A1\u6837\u5F0F");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selWork();
			}
		});
		toolBar.add(button_2);

		JButton button_5 = new JButton("\u81EA\u52A8\u5BF9\u5E94\u76F8\u540C\u5B57\u6BB5");

		toolBar.add(button_5);
		toolBar.add(button_4);
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "\u53C2\u6570\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setRightComponent(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);

		table = new JTable();
		scrollPane_1.setViewportView(table);

		JPanel panel_4 = new JPanel();
		panel_5.add(panel_4, BorderLayout.SOUTH);

		btnNewButton_1 = new JButton("\u6DFB\u52A0");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addParamItem();
			}
		});

		panel_4.add(btnNewButton_1);

		btnNewButton_2 = new JButton("\u5220\u9664");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delParamItem();
			}
		});

		panel_4.add(btnNewButton_2);

		button_3 = new JButton("\u4FDD\u5B58\u8BBE\u7F6E");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveItemParam();
			}

		});
		panel_4.add(button_3);

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(0, 40));
		add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u4E1A\u52A1\u6837\u5F0F\u5BF9\u5E94\u5B57\u6BB5\uFF1A");
		lblNewLabel.setBounds(21, 6, 122, 28);
		panel_2.add(lblNewLabel);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(134, 6, 295, 28);
		panel_2.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\u9009\u62E9");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slelectStyleField();
			}
		});

		btnNewButton.setBounds(447, 7, 65, 29);
		panel_2.add(btnNewButton);
		init();
	}

	private JDialog dialog;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textField;
	private DefaultListModel listModel;
	private JList list;
	private final int index_sortNum = 0;
	private final int index_transfer = 1;
	private final int index_location = 2;
	private final int index_fix = 3;
	private final int index_receiveTable = 4;
	private final int index_receiveField = 5;
	private boolean submit;
	private Map<String, String> props;
	private Collection<XMLDto> classFields;
	private List<XMLDto> works;
	private TreeSelectPnl<XMLDto> pnl;
	private List<XMLDto> tables;
	private WorkPubDto workpub;

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

	private boolean isParamChange() {
		Gson gson = new Gson();
		String temp1 = gson.toJson(workpub);
		String temp2 = gson.toJson(getNewWorkpub());
		return !temp1.equals(temp2);
	}

	private WorkPubDto getNewWorkpub() {
		if (workpub == null) {
			return null;
		}
		WorkPubDto temp = new WorkPubDto(workpub.getWorkpubid());
		temp.setName(workpub.name);
		for (int i = 0; i < model.getRowCount(); i++) {
			String transferField = (String) model.getValueAt(i, index_transfer);
			String fix = (String) model.getValueAt(i, index_fix);
			String receiveTable = (String) model.getValueAt(i, index_receiveTable);
			String receiveField = (String) model.getValueAt(i, index_receiveField);
			int location = "表格外".equals(model.getValueAt(i, index_location)) ? 1 : 0;
			Item item = new Item(transferField, location, receiveTable, receiveField, fix);
			temp.addItem(item);
		}
		return temp;
	}

	private void saveItemParam() {
		if (list.getSelectedIndex() < 0) {
			return;
		}
		WorkPubDto temp = getNewWorkpub();
		workpub.setItems(temp.getItems());
	}

	private void init() {
		builder.setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}

			@Override
			public boolean shouldSkipField(FieldAttributes fieldattributes) {
				System.out.println(fieldattributes.getName());
				if (fieldattributes.getName().equalsIgnoreCase("name")) {
					return true;
				}
				return false;
			}
		});
		try {
			works = InvokerServiceUtils.getWorkPubFormeleAll();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "获取业务失败");
			logger.error(e.getMessage(), e);
		}
		classFields = CompUtils.getFields();

		listModel = new DefaultListModel();
		list.setModel(listModel);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					if (isParamChange()) {
						if (GUIUtils.showConfirm(dialog, "设置改变,保存？")) {
							saveItemParam();
						}
					}
					return;
				}
				switchStyle();
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model = new DefaultTableModel(new String[] { "序号", "传递参数字段", "字段位置", "固定值", "接收参数表", "接收参数字段" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == index_sortNum || column == index_location && !CommonUtils.isStrEmpty((String) model.getValueAt(row, index_location), true)) {
					return false;
				}
				return true;
			}
		};
		model.addTableModelListener(new TableModelListener() {
			boolean flag = false;

			@Override
			public void tableChanged(TableModelEvent e) {
				if (flag) {
					return;
				}
				if (e.getColumn() == index_transfer) {
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
						model.setValueAt("", e.getFirstRow(), index_transfer);
						model.setValueAt("", e.getFirstRow(), index_location);
						flag = false;
					}
				}

			}
		});
		table.setModel(model);
		CompUtils.setTableWdiths(table, 0.2);
		TableColumnModel cm = table.getColumnModel();
		TableColumn c0 = cm.getColumn(index_transfer);
		TableColumn c1 = cm.getColumn(index_receiveTable);
		TableColumn c2 = cm.getColumn(index_receiveField);
		TableColumn c3 = cm.getColumn(index_location);
		c3.setCellEditor(new DefaultCellEditor(new JComboBox(new String[] { "表格内", "表格外" })));
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				ObjectSelectPnl<XMLDto> pnl = null;
				String temp = (String) table.getValueAt(row, column);
				XMLDto value = null;
				switch (column) {
				case index_transfer:
					pnl = CompUtils.getFieldsPnl();
					value = CommonUtils.getXmlDto(classFields, "itemname", temp);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (pnl.isNull()) {
							table.setValueAt("", row, column);
						} else {
							table.setValueAt(value.getValue("itemname"), row, column);
						}
					}
					break;
				case index_receiveTable:
					pnl = new ObjectSelectPnl<XMLDto>(tables);
					value = CommonUtils.getXmlDto(tables, "alias", temp);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						table.setValueAt("", row, index_receiveField);
						if (pnl.isNull()) {
							table.setValueAt("", row, column);
						} else {
							table.setValueAt(value.getValue("alias"), row, column);
						}
					}
					break;
				case index_receiveField:
					String tableValue = (String) table.getValueAt(row, index_receiveTable);
					if (CommonUtils.isStrEmpty(tableValue)) {
						GUIUtils.showMsg(dialog, "请先设置接收参数表");
						break;
					}
					XMLDto recTable = CommonUtils.getXmlDto(tables, "alias", tableValue);
					if (recTable == null) {
						GUIUtils.showMsg(dialog, "当前接收参数表不存在,请重设");
						break;
					}
					List<XMLDto> fields = getFields(recTable);
					if (fields == null || fields.isEmpty()) {
						GUIUtils.showMsg(dialog, "字段为空");
						break;
					}
					pnl = new ObjectSelectPnl<XMLDto>(fields);
					value = CommonUtils.getXmlDto(fields, "itemname", temp);
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isChange()) {
						value = pnl.getSelect();
						if (pnl.isNull()) {
							table.setValueAt("", row, column);
						} else {
							table.setValueAt(value.getValue("itemname"), row, column);
						}
					}
					break;
				default:
					break;
				}
				CompUtils.stopTabelCellEditor(table);
			}
		}, true);
		c0.setCellRenderer(editor.getTableCellRenderer());
		c1.setCellRenderer(editor.getTableCellRenderer());
		c2.setCellRenderer(editor.getTableCellRenderer());
		c0.setCellEditor(editor);
		c1.setCellEditor(editor);
		c2.setCellEditor(editor);

	}

	private void switchStyle() {
		CompUtils.stopTabelCellEditor(table);
		tables = null;
		model.setRowCount(0);
		workpub = (WorkPubDto) list.getSelectedValue();
		try {
			if (workpub == null) {
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				button_3.setEnabled(false);
			} else {
				String moduleid = workpub.getWorkpubid();
				if (CommonUtils.isStrEmpty(moduleid)) {
					btnNewButton_1.setEnabled(false);
					btnNewButton_2.setEnabled(false);
					button_3.setEnabled(false);
				} else {
					btnNewButton_1.setEnabled(true);
					btnNewButton_2.setEnabled(true);
					button_3.setEnabled(true);
					List<Element> eles = InvokerServiceUtils.getWorkPubFormeleTables(moduleid);
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
					List<Item> items = workpub.getItems();
					int i = 1;
					for (Item item : items) {
						String location = item.getLocation() == 1 ? "表格外" : "表格内";
						model.addRow(new String[] { i + "", item.getTransferField(), location, item.getFix(), item.getReceiveTable(), item.getReceiveField() });
						i++;
					}
				}
			}
			CompUtils.stopTabelCellEditor(table);
		} catch (Exception e2) {
			logger.error(e2.getMessage(), e2);
			GUIUtils.showMsg(dialog, "发生未知错误");
		}

	}

	private void slelectStyleField() {
		XMLDto field = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", textField.getText());
		ObjectSelectPnl<XMLDto> editor = CompUtils.getFieldsPnl();
		editor.setValue(field);
		editor.edit(dialog, null);
		if (!editor.isChange()) {
			return;
		}
		if (editor.isNull()) {
			textField.setText("");
		} else {
			textField.setText(editor.getSelect().getValue("itemname"));
		}
	}

	private void addParamItem() {
		CompUtils.stopTabelCellEditor(table);
		int index = table.getSelectedRow();
		if (index < 0) {
			index = table.getRowCount();
		} else {
			index++;
		}
		model.insertRow(index, new Object[] { 0, "", "", "", "", "" });
		CompUtils.initSortNum(model, index_sortNum);
		table.setRowSelectionInterval(index, index);
	}

	private void delParamItem() {
		CompUtils.stopTabelCellEditor(table);
		if (isParamChange()) {
			if (GUIUtils.showConfirm(dialog, "设置改变,保存？")) {
				saveItemParam();
			}
		}
		int index = table.getSelectedRow();
		if (index < 0) {
			return;
		}
		model.removeRow(index);
		if (model.getRowCount() <= 0) {
			return;
		}
		if (index != 0) {
			index--;
		}
		CompUtils.initSortNum(model, index_sortNum);
		table.setRowSelectionInterval(index, index);
	}

	private void addWork() {
		int index = list.getSelectedIndex();
		index++;
		WorkPubDto obj = new WorkPubDto();
		listModel.insertElementAt(obj, index);
		list.setSelectedIndex(index);

	}

	private void delWork() {
		int index = list.getSelectedIndex();
		if (index < 0) {
			return;
		}
		listModel.removeElementAt(index);
		if (listModel.getSize() <= 0) {
			return;
		}
		if (index != 0) {
			index--;
		}
		list.setSelectedIndex(index);
	}

	private void selWork() {
		WorkPubDto workpubdto = (WorkPubDto) list.getSelectedValue();
		if (workpubdto == null) {
			return;
		}
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
		String id = workpubdto.getWorkpubid();
		Map<String, String> prop = new HashMap<String, String>();
		prop.put("key", "id");
		prop.put("value", id);
		pnl.edit(dialog, prop);
		if (!pnl.isChange()) {
			return;
		}
		XMLDto dto = pnl.getSelect();
		workpubdto.init();
		if (dto != null) {
			workpubdto.setName(dto.getValue("name"));
			workpubdto.setWorkpubid(dto.getValue("id"));
		}
		switchStyle();
	}

	private void save() {
		try {
			if (isParamChange()) {
				if (GUIUtils.showConfirm(dialog, "设置改变,保存？")) {
					saveItemParam();
				}
			}
			if (!validateData()) {
				return;
			}
			BatchWorkDto dto = new BatchWorkDto();
			dto.setWordpubidField(textField.getText());
			if (!listModel.isEmpty()) {
				for (int i = 0; i < listModel.size(); i++) {
					dto.addWorkPubDto((WorkPubDto) listModel.getElementAt(i));
				}
			}
			if (dto.workPubDtoEmpty()) {
				props.put("inparam", "");
			} else {
				String json = builder.create().toJson(dto);
				json = json.replaceAll("\"", "'");
				props.put("inparam", json);
			}
			submit = true;
			dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "批量打开通用业务", this);
		dialog.setVisible(true);

	}

	public boolean validateData() {
		if (StringUtils.nullOrBlank(textField.getText())) {
			GUIUtils.showMsg(dialog, "请设置业务样式对应字段");
			return false;
		}
		if (listModel.isEmpty()) {
			GUIUtils.showMsg(dialog, "请添加样式");
			return false;
		}
		for (int i = 0; i < listModel.getSize(); i++) {
			WorkPubDto workpub = (WorkPubDto) listModel.getElementAt(i);
			if (workpub.itemEmpty()) {
				GUIUtils.showMsg(dialog, "有空的参数设置，请检查");
				return false;
			}
			List<Item> items = workpub.getItems();
			for (Item item : items) {
				if (item.hasEmptyProp()) {
					GUIUtils.showMsg(dialog, "有空字段，请检查");
					return false;
				}
			}
		}
		return true;
	}

	private void initData() {
		String inparam = props.get("inparam");
		if (CommonUtils.isStrEmpty(inparam)) {
			return;
		}
		try {
			BatchWorkDto batchWorkDto = builder.create().fromJson(inparam, BatchWorkDto.class);
			if (batchWorkDto == null) {
				return;
			}
			textField.setText(batchWorkDto.wordpubidField);
			if (batchWorkDto.workPubDtoEmpty()) {
				return;
			}
			for (WorkPubDto dto : batchWorkDto.getWorkpubs()) {
				String moduleid = dto.getWorkpubid();
				if (!CommonUtils.isStrEmpty(moduleid)) {
					XMLDto work = CommonUtils.getXmlDto(works, "id", moduleid);
					if (work != null) {
						dto.setName(work.getValue("name"));
					}
				}
				listModel.addElement(dto);
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

	static class BatchWorkDto {
		private String wordpubidField;
		private List<WorkPubDto> workpubs = new LinkedList<WorkPubDto>();

		public WorkPubDto getWorkPubDto(int index) {
			return workpubs.get(index);
		}

		public void addWorkPubDto(WorkPubDto wdto) {
			workpubs.add(wdto);
		}

		public boolean workPubDtoEmpty() {
			return workpubs.isEmpty();
		}

		public List<WorkPubDto> getWorkpubs() {
			return workpubs;
		}

		public void setWorkpubs(List<WorkPubDto> workpubs) {
			this.workpubs = workpubs;
		}

		public BatchWorkDto() {
		}

		public String getWordpubidField() {
			return wordpubidField;
		}

		public void setWordpubidField(String wordpubidField) {
			this.wordpubidField = wordpubidField;
		}

		static class WorkPubDto {
			private String name = "[]";

			@Override
			public String toString() {
				return name;
			}

			public WorkPubDto() {
			}

			public WorkPubDto(String workpubid) {
				this.workpubid = workpubid;
			}

			public void setName(String name) {
				this.name = name;
			}

			public void setItems(List<Item> items) {
				this.items = items;
			}

			private String workpubid;
			private List<Item> items = new LinkedList<Item>();

			public void clearItems() {
				items.clear();
			}

			public void init() {
				this.setName("[]");
				this.setWorkpubid("");
				clearItems();
			}

			public void addItem(Item item) {
				items.add(item);
			}

			public boolean itemEmpty() {
				return items.isEmpty();
			}

			public List<Item> getItems() {
				return items;
			}

			static class Item {
				public Item() {
				}

				public Item(String transferField, int location, String receiveTable, String receiveField, String fix) {
					this.transferField = transferField;
					this.receiveTable = receiveTable;
					this.receiveField = receiveField;
					this.fix = fix;
					this.location = location;
				}

				public boolean hasEmptyProp() {
					if (CommonUtils.isStrEmpty(transferField, true) && CommonUtils.isStrEmpty(fix, true)) {
						return true;
					}
					if (CommonUtils.isStrEmpty(receiveTable, true)) {
						return true;
					}
					if (CommonUtils.isStrEmpty(receiveField, true)) {
						return true;
					}
					return false;
				}

				private String transferField;
				private String fix;
				private int location;

				public int getLocation() {
					return location;
				}

				public void setLocation(int location) {
					this.location = location;
				}

				public String getFix() {
					return fix;
				}

				public void setFix(String fix) {
					this.fix = fix;
				}

				private String receiveTable;
				private String receiveField;

				public String getTransferField() {
					return transferField;
				}

				public void setTransferField(String transferField) {
					this.transferField = transferField;
				}

				public String getReceiveTable() {
					return receiveTable;
				}

				public void setReceiveTable(String receiveTable) {
					this.receiveTable = receiveTable;
				}

				public String getReceiveField() {
					return receiveField;
				}

				public void setReceiveField(String receiveField) {
					this.receiveField = receiveField;
				}

			}

			public String getWorkpubid() {
				return workpubid;
			}

			public void setWorkpubid(String workpubid) {
				this.workpubid = workpubid;
			}
		}
	}
}
