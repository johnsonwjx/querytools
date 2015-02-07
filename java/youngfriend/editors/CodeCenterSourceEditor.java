package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.Lst2LstSelPnl;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class CodeCenterSourceEditor extends JPanel implements PropEditor {
	private Lst2LstSelPnl<XMLDto> selPnl;
	private JPanel panel_3;
	private List<XMLDto> fields;
	private XMLDto table;
	private XMLDto field;
	private XMLDto showfield;
	private ObjectSelectPnl<XMLDto> tableSel;
	private ObjectSelectPnl<XMLDto> fieldSel;
	private JTextArea textArea;
	private JCheckBox checkBox;
	private ButtonGroup bg;
	private JDialog dialog = null;

	public CodeCenterSourceEditor() {
		this.setPreferredSize(new Dimension(612, 538));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u5EFA\u6811\u5B57\u6BB5\u9ED8\u8BA4\u4E3Acode", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(0, 40));
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);

		JLabel label = new JLabel("\u4EE3\u7801\u4E2D\u5FC3\u8868");
		label.setBounds(16, 13, 65, 16);
		panel_2.add(label);

		textField = new JTextField();
		textField.setBounds(101, 7, 282, 28);
		textField.setEditable(false);
		panel_2.add(textField);
		textField.setColumns(20);

		JButton button_2 = new JButton("...");
		button_2.setBounds(395, 7, 75, 29);
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selCodeTable();
			}
		});
		panel_2.add(button_2);
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"\u53D6\u6570\u81EA\u5B9A\u4E49\u6761\u4EF6 (\u7528[]\u62EC\u8D77\u6765,\u5B57\u6BB5\u540D\u7528{}\u62EC\u8D77\u6765, \u5982\u679C\u6709\u591A\u4E2A\u63A7\u4EF6\u5B57\u6BB5\u540D\u76F8\u540C\u8BF7\u7528\u63A7\u4EF6Id)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setPreferredSize(new Dimension(0, 120));
		panel.add(scrollPane, BorderLayout.SOUTH);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		JButton button = new JButton("\u786E\u8BA4");
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
		init();
	}

	private void init() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 40));
		panel_3.add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel label_1 = new JLabel("\u5EFA\u6811\u5B57\u6BB5");
		label_1.setBounds(6, 12, 63, 16);
		panel.add(label_1);

		textField_1 = new JTextField();
		textField_1.setBounds(101, 6, 282, 28);
		panel.add(textField_1);
		textField_1.setEditable(false);
		textField_1.setColumns(10);

		JButton button_3 = new JButton("...");
		button_3.setBounds(395, 7, 75, 29);
		panel.add(button_3);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(0, 90));
		panel_3.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(null);

		JLabel label = new JLabel("\u6811\u663E\u793A\u5B57\u6BB5");
		label.setBounds(6, 58, 75, 16);
		panel_1.add(label);

		textField_2 = new JTextField();
		textField_2.setBounds(107, 52, 282, 28);
		panel_1.add(textField_2);
		textField_2.setEditable(false);
		textField_2.setColumns(10);

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setShowField();
			}
		});
		button.setBounds(395, 52, 75, 29);
		panel_1.add(button);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(17, 6, 281, 37);
		panel_1.add(panel_4);
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel label_2 = new JLabel("\u64CD\u4F5C\u7B26\uFF1A");
		panel_4.add(label_2);

		JRadioButton radioButton = new JRadioButton("\u5DE6\u5339\u914D");
		radioButton.setSelected(true);
		panel_4.add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("\u5168\u5339\u914D");
		panel_4.add(radioButton_1);

		JRadioButton radioButton_2 = new JRadioButton("\u7B49\u4E8E");
		panel_4.add(radioButton_2);
		bg = new ButtonGroup();
		for (Component c : panel_4.getComponents()) {
			if (c instanceof AbstractButton) {
				bg.add((AbstractButton) c);
			}

		}
		checkBox = new JCheckBox("\u81EA\u52A8\u8865\u4E0A\u7EA7");
		checkBox.setBounds(315, 20, 97, 23);
		panel_1.add(checkBox);
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selCodeField();
			}

		});
	}

	private void selCodeTable() {
		if (CompUtils.getCodeTables().isEmpty()) {
			GUIUtils.showMsg(dialog, "表格为空");
			return;
		}
		if (tableSel == null) {
			tableSel = CompUtils.getCodeTablePnl(table);
		} else {
			tableSel.setValue(table);
		}
		tableSel.edit(dialog, null);
		if (tableSel.isChange()) {
			table = tableSel.getSelect();
			changeTable();
		}
	}

	private void changeTable() {
		textField_1.setText("");
		field = null;
		textField_2.setText("");
		showfield = null;
		if (table == null) {
			fields = null;
			textField.setText("");
			return;
		}
		textField.setText(table.toString());
		try {
			fields = InvokerServiceUtils.getCodeFields(table.getValue("id"));
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "获取代码中心字段失败");
			defaultpropeditor.getLogger().error(e.getMessage(), e);
		}
	}

	private void selCodeField() {
		if (table == null) {
			GUIUtils.showMsg(dialog, "请选择表格");
			return;
		}
		if (fields == null || fields.isEmpty()) {
			GUIUtils.showMsg(dialog, "字段为空");
			return;
		}
		if (fieldSel == null) {
			fieldSel = new ObjectSelectPnl<XMLDto>(fields);
		}
		fieldSel.setValue(field);
		fieldSel.edit(dialog, null);
		if (fieldSel.isChange()) {
			field = fieldSel.getSelect();
			if (field == null) {
				textField_1.setText("");
			} else {
				textField_1.setText(field.toString());
			}
		}
	}

	private void setShowField() {
		if (table == null) {
			GUIUtils.showMsg(dialog, "请选择表格");
			return;
		}
		if (fields == null || fields.isEmpty()) {
			GUIUtils.showMsg(dialog, "字段为空");
			return;
		}
		if (fieldSel == null) {
			fieldSel = new ObjectSelectPnl<XMLDto>(fields);
		}
		fieldSel.setValue(showfield);
		fieldSel.edit(dialog, null);
		if (fieldSel.isChange()) {
			showfield = fieldSel.getSelect();
			if (showfield == null) {
				textField_2.setText("");
			} else {
				textField_2.setText(showfield.toString());
			}
		}
	}

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private DefaultPropEditor defaultpropeditor;

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {
			@Override
			public boolean save() {
				if (table == null) {
					GUIUtils.showMsg(defaultpropeditor.getDialog(), "表名为空");
					return false;
				}
				if (field == null) {
					GUIUtils.showMsg(defaultpropeditor.getDialog(), "树字段为空");
					return false;
				}
				Collection<XMLDto> values = selPnl.getValues();
				if (values == null || values.isEmpty()) {
					GUIUtils.showMsg(defaultpropeditor.getDialog(), "过滤字段为空");
					return false;
				}
				StringBuilder temp = new StringBuilder();
				for (XMLDto dto : values) {
					temp.append(dto.getValue("itemname")).append("+");
				}
				temp.deleteCharAt(temp.length() - 1);
				StringBuilder str = new StringBuilder(table.getValue("alias"));
				String condi = "";
				if (!CommonUtils.isStrEmpty(textArea.getText())) {
					condi = CommonUtils.base64Encode(textArea.getText().getBytes());
				}
				str.append(",").append(field.getValue("fieldname")).append(",").append(showfield == null ? "" : showfield.getValue("fieldname")).append(",").append(temp).append(",").append(condi).append(",");
				AbstractButton btn = CompUtils.getGroupSelect(bg);
				str.append(btn.getText()).append(",").append(checkBox.isSelected());
				prop.setValue(str.toString());
				return true;
			}

			@Override
			public void initData() {
				String[] arrs = prop.getValue().split(",");
				if (arrs.length < 5) {
					return;
				}
				String temp = arrs[0];
				if (CommonUtils.isStrEmpty(temp)) {
					return;
				}
				table = CommonUtils.getXmlDto(CompUtils.getCodeTables(), "alias", temp);
				if (table == null) {
					return;
				}
				changeTable();

				temp = arrs[1];
				if (!CommonUtils.isStrEmpty(temp)) {
					field = CommonUtils.getXmlDto(fields, "fieldname", temp);
					if (field != null) {
						textField_1.setText(field.toString());
					} else {
						textField_1.setText(temp);
						GUIUtils.showMsg(dialog, "不存在字段" + temp);
					}
				}
				temp = arrs[2];
				if (!CommonUtils.isStrEmpty(temp)) {
					showfield = CommonUtils.getXmlDto(fields, "fieldname", temp);
					if (showfield != null) {
						textField_2.setText(showfield.toString());
					} else {
						textField_2.setText(temp);
						GUIUtils.showMsg(dialog, "不存在字段" + temp);
					}
				}
				temp = arrs[3];
				if (!CommonUtils.isStrEmpty(temp)) {
					String[] temps = temp.split("\\+");
					if (temps.length > 0) {
						Collection<XMLDto> values = new ArrayList<XMLDto>();
						for (String str : temps) {
							XMLDto dto = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", str);
							if (dto != null) {
								values.add(dto);
							}
						}
						if (!values.isEmpty()) {
							selPnl.setValue(values);
						}

					}
				}

				temp = arrs[4];
				if (!CommonUtils.isStrEmpty(temp)) {
					textArea.setText(new String(CommonUtils.base64Dcode(temp)));
				}
				if (arrs.length < 6) {
					return;
				}
				temp = arrs[5];
				if (!CommonUtils.isStrEmpty(temp)) {
					Enumeration<AbstractButton> e = bg.getElements();
					for (; e.hasMoreElements();) {
						AbstractButton btn = e.nextElement();
						if (btn.getText().equals(temp)) {
							btn.setSelected(true);
							break;
						}
					}
				}
				if (arrs.length < 7) {
					return;
				}
				temp = arrs[6];
				if (!CommonUtils.isStrEmpty(temp)) {
					checkBox.setSelected("true".equalsIgnoreCase(temp));
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		dialog = defaultpropeditor.getDialog();
		selPnl = new Lst2LstSelPnl<XMLDto>(dialog, CompUtils.getFields(), CodeCenterSourceEditor.this.getPreferredSize().width, 0, "", "");
		selPnl.setBorder(new TitledBorder(null, "\u5EFA\u6811\u5173\u8054\u5B57\u6BB5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		selPnl.setTopPnl(false);
		panel_3.add(selPnl, BorderLayout.CENTER);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}
}
