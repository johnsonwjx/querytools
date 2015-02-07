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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
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
import youngfriend.editors.valueEditors.RebulidTreeCondiEditor;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class ComboTreeInfoEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	public ComboTreeInfoEditor() {
		initComponents();
	}

	private String[] Titles = new String[] { "序号", "显示值", "数据来源", "建树参考信息", "建树动态条件", "展开方式" };
	private final int index_sortnumber = 0;
	private final int index_showvalue = 1;
	private final int index_location = 2;
	private final int index_info = 3;
	private final int index_condi = 4;
	private final int index_expand = 5;
	private JTable table;
	private DefaultTableModel model;
	private final XMLDto[] locationArr = new XMLDto[] { new XMLDto("代码中心", "codecenter"), new XMLDto("其他数据源", "otherdata") };
	private final XMLDto[] expandArr = new XMLDto[] { new XMLDto("", ""), new XMLDto("展开全部", "true"), new XMLDto("不展开全部", "false") };

	private void initComponents() {
		this.setPreferredSize(new Dimension(865, 450));
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
		CompUtils.tableMove(btnNewButton, table, index_sortnumber, true);
		CompUtils.tableMove(button_4, table, index_sortnumber, false);
	}

	private void inittable() {
		model = new DefaultTableModel(Titles, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == index_sortnumber) {
					return Integer.class;
				}
				if (columnIndex == index_location || columnIndex == index_expand) {
					return XMLDto.class;
				}
				return super.getColumnClass(columnIndex);
			}
		};
		table = new JTable(model);
		TableColumnModel cm = table.getColumnModel();
		TableColumn location = cm.getColumn(index_location);
		location.setCellEditor(new DefaultCellEditor(new JComboBox(locationArr)));
		TableColumn expand = cm.getColumn(index_expand);
		expand.setCellEditor(new DefaultCellEditor(new JComboBox(expandArr)));

		CompUtils.setTableWdiths(table, 0.08, 0.4, 0.2, null, 0.5, 0.2);
		ButtonCellEditor cellEditor = new ButtonCellEditor(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(table);
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				String value = (String) table.getValueAt(row, column);
				if (column == index_condi) {
					// 动态条件
					RebulidTreeCondiEditor editor = new RebulidTreeCondiEditor();
					Map<String, String> props = new HashMap<String, String>();
					props.put("value", value);
					editor.edit(defaultpropeditor.getDialog(), props);
					if (editor.isSubmit()) {
						value = props.get("value");
					}
				} else if (column == index_info) {
					XMLDto dto = (XMLDto) table.getValueAt(row, index_location);
					if (dto == null) {
						GUIUtils.showMsg(defaultpropeditor.getDialog(), "请先设置数据来源");
						return;
					}
					PropEditor editor = null;
					PropDto temp = new PropDto();
					temp.setValue(value);
					if ("codecenter".equals(dto.getValue("value"))) {
						editor = new CodeCenterSourceEditor();
						editor.edit(temp, defaultpropeditor.getDialog());
					} else if ("otherdata".equals(dto.getValue("value"))) {
						editor = new OtherSoruceEditor();
						editor.edit(temp, defaultpropeditor.getDialog());

					}
					value = temp.getValue();
				}
				table.setValueAt(value, row, column);
			}
		}, true);

		TableColumn info = cm.getColumn(index_info);
		TableColumn condi = cm.getColumn(index_condi);
		info.setCellEditor(cellEditor);
		condi.setCellEditor(cellEditor);
		info.setCellRenderer(cellEditor.getTableCellRenderer());
		condi.setCellRenderer(cellEditor.getTableCellRenderer());
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
		CompUtils.initSortNum(model, index_sortnumber);
		if (model.getRowCount() > 0) {
			if (newSel != 0) {
				newSel--;
			}
			table.setRowSelectionInterval(newSel, newSel);
		}
	}

	private void addItem() {
		CompUtils.stopTabelCellEditor(table);
		model.addRow(new Object[] { model.getRowCount() + 1, "", locationArr[0], "", "", new XMLDto("", "") });
	}

	private javax.swing.JScrollPane sp;
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
						String showvalue = (String) model.getValueAt(i, index_showvalue);
						XMLDto datasource = (XMLDto) model.getValueAt(i, index_location);
						String treeinfo = (String) model.getValueAt(i, index_info);
						String othercondi = (String) model.getValueAt(i, index_condi);
						XMLDto expand = (XMLDto) model.getValueAt(i, index_expand);
						if (datasource == null || CommonUtils.isStrEmpty(showvalue) || CommonUtils.isStrEmpty(showvalue.trim()) || CommonUtils.isStrEmpty(treeinfo) || CommonUtils.isStrEmpty(treeinfo.trim())) {
							GUIUtils.showMsg(defaultpropeditor.getDialog(), "数据来源，显示值,建树参数不能为空");
							table.setRowSelectionInterval(i, i);
							return false;
						}
						Element items = root.addElement("items");
						items.addElement("sortnumber").setText(i + 1 + "");
						items.addElement("datasource").setText(datasource.getValue("value"));
						items.addElement("showvalue").setText(CommonUtils.base64Encode(showvalue.getBytes()));
						items.addElement("treeinfo").setText(CommonUtils.base64Encode(treeinfo.getBytes()));
						items.addElement("expand").setText(expand == null ? "" : expand.getValue("value"));
						Element othercondiEle = items.addElement("othercondi");
						if (!CommonUtils.isStrEmpty(othercondi) && !CommonUtils.isStrEmpty(othercondi.trim())) {
							othercondiEle.setText(CommonUtils.base64Encode(othercondi.getBytes()));
						}
					}
					prop.setValue(root.asXML());
				}
				return true;
			}

			@Override
			public void initData() {
				Element root;
				try {
					root = DocumentHelper.parseText(prop.getValue()).getRootElement();
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
				List<Element> allitems = root.elements("items");
				if (allitems == null || allitems.isEmpty()) {
					return;
				}
				int i = 1;
				String temp = null;
				List<XMLDto> datasortLst = Arrays.asList(locationArr);
				List<XMLDto> expnadLst = Arrays.asList(expandArr);
				for (Element items : allitems) {
					String showvalue = "";
					XMLDto datasource = null;
					String treeinfo = "";
					String othercondi = "";
					XMLDto expand = expandArr[0];
					temp = items.elementText("showvalue");
					if (!CommonUtils.isStrEmpty(temp)) {
						showvalue = new String(CommonUtils.base64Dcode(temp));
					}
					temp = items.elementText("datasource");
					if (!CommonUtils.isStrEmpty(temp)) {
						datasource = CommonUtils.getXmlDto(datasortLst, "value", temp);
					}
					temp = items.elementText("treeinfo");
					if (!CommonUtils.isStrEmpty(temp)) {
						treeinfo = new String(CommonUtils.base64Dcode(temp));
					}
					temp = items.elementText("othercondi");
					if (!CommonUtils.isStrEmpty(temp)) {
						othercondi = new String(CommonUtils.base64Dcode(temp));
					}
					temp = items.elementText("expand");
					if (!CommonUtils.isStrEmpty(temp)) {
						expand = CommonUtils.getXmlDto(expnadLst, "value", temp);
					}
					model.addRow(new Object[] { i, showvalue, datasource, treeinfo, othercondi, expand });
					i++;
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
