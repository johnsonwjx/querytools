/*
 * ChooseDataTable.java
 *
 * Created on 2007年8月2日, 上午11:22
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

/**
 * 
 * @author Administrator
 */
public class ExpressionEditor extends javax.swing.JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;
	private JButton btnNewButton;

	private JButton button;

	private JButton button_1;
	private JButton button_10;
	private JButton button_11;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;

	private JButton button_5;

	private JButton button_6;

	private JButton button_7;

	private JButton button_8;

	private JButton button_9;

	private javax.swing.JList lstField;

	private DefaultTableModel model;

	private JPanel panel;

	private JPanel panel_1;

	private JPanel panel_10;
	private JPanel panel_11;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JPanel panel_9;
	private JScrollPane scrollPane;
	private javax.swing.JScrollPane sp;
	private JSplitPane splitPane;
	private JTable tab;
	private javax.swing.JTextArea taCnexps;
	private javax.swing.JTextArea taExps;
	private String[] Titles = new String[] { "赋值字段", "字段类型", "表达式", "中文表达式" };
	private static final int index_field = 0;
	private static final int index_type = 1;
	private static final int index_exp = 2;
	private static final int index_expcn = 3;
	private javax.swing.JScrollPane ys;
	private javax.swing.JScrollPane yw;
	private DefaultPropEditor defaultpropeditor;
	private JButton button_12;
	private JButton button_13;

	public ExpressionEditor() {
		initComponents();
		initLstFields();
		initTab();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(1164, 569));
		setLayout(new BorderLayout(0, 0));

		splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		add(splitPane, BorderLayout.CENTER);

		panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		sp = new javax.swing.JScrollPane();
		panel.add(sp);

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);

		button_6 = new JButton("\u6DFB\u52A0");
		button_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tab.getSelectedRow();
				model.insertRow(row + 1, new Object[] { null, "", "" });
			}
		});
		panel_1.add(button_6);

		button_7 = new JButton("\u5220\u9664");
		button_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tab.getSelectedRow();
				if (index == -1) {
					return;
				}
				CompUtils.stopTabelCellEditor(tab);
				model.removeRow(index);
				if (model.getRowCount() > 0) {
					if (index > 0) {
						index--;
					}
					tab.setRowSelectionInterval(index, index);
				}
			}
		});
		panel_1.add(button_7);

		button_12 = new JButton("\u4E0A\u79FB");
		panel_1.add(button_12);

		button_13 = new JButton("\u4E0B\u79FB");
		panel_1.add(button_13);
		panel_9 = new JPanel();
		panel_9.setPreferredSize(new Dimension(500, 0));
		splitPane.setRightComponent(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane_1 = new JSplitPane();
		panel_9.add(splitPane_1, BorderLayout.CENTER);
		splitPane_1.setResizeWeight(1.0);

		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u8868\u8FBE\u5F0F(\u5982\u679C\u8868\u8FBE\u5F0F\u6709\u5E38\u91CF\uFF0C\u8BF7\u7528' '\uFF0C\u4E0D\u8981\u7528\" \")", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		splitPane_1.setLeftComponent(panel_5);
		panel_5.setLayout(new GridLayout(2, 1, 0, 0));

		panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		yw = new javax.swing.JScrollPane();
		panel_6.add(yw, BorderLayout.CENTER);
		taExps = new javax.swing.JTextArea();
		yw.setViewportView(taExps);

		taExps.setColumns(20);
		taExps.setLineWrap(true);
		taExps.setRows(5);
		taExps.addInputMethodListener(new java.awt.event.InputMethodListener() {
			@Override
			public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
			}

			@Override
			public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
				initTxtAreaChinese();
			}
		});
		taExps.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				initTxtAreaChinese();
			}
		});

		panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		ys = new javax.swing.JScrollPane();
		panel_7.add(ys, BorderLayout.CENTER);
		taCnexps = new javax.swing.JTextArea();
		ys.setViewportView(taCnexps);

		taCnexps.setColumns(20);
		taCnexps.setLineWrap(true);
		taCnexps.setRows(5);

		panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(300, 0));
		panel_3.setBorder(new TitledBorder(null, "\u8868\u5B57\u6BB5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane_1.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel_3.add(panel_2, BorderLayout.CENTER);
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		panel_4 = new JPanel();
		scrollPane.setViewportView(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		lstField = new javax.swing.JList();
		panel_4.add(lstField, BorderLayout.CENTER);

		lstField.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lstFieldMouseClicked(evt);
			}
		});

		panel_8 = new JPanel();
		panel_3.add(panel_8, BorderLayout.SOUTH);

		btnNewButton = new JButton("+");
		panel_8.add(btnNewButton);

		button = new JButton("-");
		panel_8.add(button);

		button_1 = new JButton("*");
		panel_8.add(button_1);

		button_2 = new JButton("/");
		panel_8.add(button_2);

		button_3 = new JButton("=");
		panel_8.add(button_3);

		button_4 = new JButton("(");
		panel_8.add(button_4);

		button_5 = new JButton(")");
		panel_8.add(button_5);

		panel_10 = new JPanel();
		panel_9.add(panel_10, BorderLayout.SOUTH);

		button_8 = new JButton("\u4FDD\u5B58\u8BBE\u7F6E");
		button_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tab.getSelectedRow();
				if (index < 0) {
					GUIUtils.showMsg(defaultpropeditor.getDialog(), "请选择");
					return;
				}
				String cnexps = taCnexps.getText().replaceAll("\n", " ");
				String formula = taExps.getText().replaceAll("\n", " ");
				tab.setValueAt(formula, index, index_exp);
				tab.setValueAt(cnexps, index, index_expcn);
			}
		});
		panel_10.add(button_8);

		button_9 = new JButton("\u6E05\u7A7A\u8BBE\u7F6E");
		button_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!GUIUtils.showConfirm(defaultpropeditor.getDialog(), "确认清空吗")) {
					return;
				}
				taExps.setText("");
				taCnexps.setText("");
				int index = tab.getSelectedRow();
				if (index < 0) {
					return;
				}
				model.setValueAt(null, index, index_field);
				model.setValueAt("", index, index_type);
				model.setValueAt("", index, index_exp);
				model.setValueAt("", index, index_expcn);
			}
		});
		panel_10.add(button_9);

		panel_11 = new JPanel();
		panel_11.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		FlowLayout flowLayout = (FlowLayout) panel_11.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_11, BorderLayout.SOUTH);

		button_10 = new JButton("\u786E\u5B9A");
		button_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();

			}
		});
		panel_11.add(button_10);

		button_11 = new JButton("\u53D6\u6D88");
		button_11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel_11.add(button_11);
		for (Component c : panel_8.getComponents()) {
			((JButton) c).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evn) {
					String op = ((JButton) evn.getSource()).getText();
					taExps.replaceSelection(" " + op + " ");
					initTxtAreaChinese();
				}
			});
		}
	}

	private void initLstFields() {
		DefaultListModel fieldModel = new DefaultListModel();
		for (XMLDto item : CompUtils.getFields()) {
			fieldModel.addElement(item);
		}
		lstField.setModel(fieldModel);

	}

	private void initTab() {
		model = new DefaultTableModel(Titles, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == index_exp || column == index_expcn) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		};
		tab = new JTable(model);
		tab.setRowSelectionAllowed(true);
		tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab.setColumnSelectionAllowed(false);
		ButtonCellEditor cellEditor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				CompUtils.stopTabelCellEditor(tab);
				XMLDto value = CompUtils.getCellValue(XMLDto.class, tab, tab.getSelectedRow(), tab.getSelectedColumn());
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				pnl.edit(defaultpropeditor.getDialog(), null);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					String type = "";
					if (value != null) {
						if ("C".equalsIgnoreCase(value.getValue("itemtype"))) {
							type = "varchar(32)";
						} else if ("D".equalsIgnoreCase(value.getValue("itemtype"))) {
							type = "datetype";
						}
					}
					tab.setValueAt(value, tab.getSelectedRow(), tab.getSelectedColumn());
					tab.setValueAt(type, tab.getSelectedRow(), index_type);
				}
			}
		}, false);
		tab.getColumnModel().getColumn(index_field).setCellEditor(cellEditor);
		tab.getColumnModel().getColumn(index_field).setCellRenderer(cellEditor.getTableCellRenderer());
		tab.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// 显示参数
				if (e.getValueIsAdjusting()) {
					return;
				}
				int selIndex = tab.getSelectedRow();
				if (selIndex < 0) {
					return;
				}
				String exps = (String) tab.getValueAt(selIndex, index_exp);
				String cnexps = (String) tab.getValueAt(selIndex, index_expcn);
				taExps.setText(exps);
				taCnexps.setText(cnexps);
			}
		});
		sp.setViewportView(tab);
		CompUtils.tableMove(button_12, tab, -1, true);
		CompUtils.tableMove(button_13, tab, -1, false);
	}

	private void initTxtAreaChinese() {
		String formula = taExps.getText();
		if (formula != null && !formula.trim().equals("")) {
			formula = " " + formula + " ";
			for (XMLDto item : CompUtils.getFields()) {
				String key = item.getValue("itemname");
				String name = item.getValue("itemlabel");
				formula = formula.replaceAll(key, name);
				formula = formula.replaceAll(key, name);
			}
			formula = formula.substring(1, formula.length() - 1);
			taCnexps.setText(formula);
		} else {
			taCnexps.setText("");
		}

	}

	private void lstFieldMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lstFieldMouseClicked
		XMLDto item = (XMLDto) lstField.getSelectedValue();
		taExps.replaceSelection(item.getValue("itemname"));
		initTxtAreaChinese();
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				String xml = "";
				CompUtils.stopTabelCellEditor(tab);
				int count = model.getRowCount();
				if (count <= 0) {
					prop.setValue("");
				} else {
					Document doc = DocumentHelper.createDocument();
					Element root = DocumentHelper.createElement("root");
					doc.setRootElement(root);
					for (int i = 0; i < count; i++) {
						Element itemEle = root.addElement("item");
						XMLDto item = CompUtils.getCellValue(XMLDto.class, tab, i, 0);
						String field = "";
						if (item != null) {
							field = item.getValue("itemname");
						}

						Element fieldEle = itemEle.addElement("field");
						fieldEle.setText(field);
						Element expEle = itemEle.addElement("expression");
						expEle.setText((String) model.getValueAt(i, index_exp));
						Element cnexpsEle = itemEle.addElement("cnexpression");
						cnexpsEle.setText((String) model.getValueAt(i, index_expcn));
						Element field_typeEle = itemEle.addElement("fieldtype");
						field_typeEle.setText(CommonUtils.coverNull((String) model.getValueAt(i, index_type)));
					}
					xml = doc.asXML().replace("\n", "");
					prop.setValue(xml);
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
				Element root = doc.getRootElement();
				List<Element> list = root.elements("item");
				for (Element itemEle : list) {
					String exps = itemEle.elementText("expression");
					String cnexps = itemEle.elementText("cnexpression");
					String field = itemEle.elementText("field");
					String type = itemEle.elementText("fieldtype");
					model.addRow(new Object[] { CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", field), type, exps, cnexps });
				}
				tab.setRowSelectionInterval(0, 0);

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
