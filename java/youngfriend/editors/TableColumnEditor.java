package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.coms.TNewColumn;
import youngfriend.coms.TNewGrid;
import youngfriend.editors.valueEditors.BackgroupEditor;
import youngfriend.editors.valueEditors.BackgroupPicEditor;
import youngfriend.editors.valueEditors.CellColorEditor;
import youngfriend.editors.valueEditors.ExpandColEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class TableColumnEditor extends JPanel implements PropEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private JList list_1;
	private JList list;
	private DefaultListModel list1model;
	private DefaultListModel listmodel;
	private JTabbedPane tabbedPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_12;
	private JTextField textField_11;
	private JTextField textField_13;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_4;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_5;
	private JCheckBox checkBox_6;
	private JComboBox comboBox_2;
	private JCheckBox checkBox;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private JButton button_5;
	private JButton button_7;
	private JButton button_8;
	private JButton button_9;
	private JButton button_10;
	private JButton button_11;
	private JButton button_12;
	private JButton button_13;
	private JButton button_14;
	private JSpinner spinner_1;
	private JButton button_2;
	private JButton button_3;
	private JCheckBox checkBox_7;
	private JPanel panel;
	private ButtonGroup bg;
	private JRadioButton rdbtnmax;
	private JRadioButton rdbtnsum;
	private JCheckBox checkBox_8;
	private JComboBox comboBox_4;
	private boolean colChangeUpdateUI = false;
	private JCheckBox checkBox_9;
	private JButton button_17;
	private Map<String, String> propMap = new HashMap<String, String>();

	public TableColumnEditor() {
		this.setPreferredSize(new Dimension(866, 731));
		setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);

		JButton button = new JButton("  \u6DFB\u52A0  ");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCol();
			}

		});
		toolBar.add(button);

		JButton button_1 = new JButton("  \u5220\u9664  ");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delCol();
			}

		});
		toolBar.add(button_1);

		button_2 = new JButton("  \u4E0A\u79FB  ");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upMove();
			}
		});

		toolBar.add(button_2);

		button_3 = new JButton("  \u4E0B\u79FB  ");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downMove();
			}
		});

		toolBar.add(button_3);

		toolBar.addSeparator(new Dimension(20, 0));

		JButton button_15 = new JButton("  \u786E\u5B9A  ");
		button_15.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}

		});
		toolBar.add(button_15);

		JButton button_16 = new JButton("  \u53D6\u6D88  ");
		button_16.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		toolBar.add(button_16);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(150, 0));
		splitPane.setLeftComponent(scrollPane);
		list = new JList();
		scrollPane.setViewportView(list);
		listmodel = new DefaultListModel();
		list.setModel(listmodel);
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		splitPane.setRightComponent(tabbedPane);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("\u5C5E\u6027", null, panel_3, null);
		panel_3.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u6807\u9898");
		lblNewLabel.setBounds(16, 6, 61, 16);
		panel_3.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u5B57\u6BB5\u540D");
		lblNewLabel_1.setBounds(16, 33, 61, 16);
		panel_3.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u5BBD\u5EA6");
		lblNewLabel_2.setBounds(16, 67, 61, 16);
		panel_3.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u5B57\u6BB5\u7C7B\u578B");
		lblNewLabel_3.setBounds(16, 103, 61, 16);
		panel_3.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("\u5C0F\u6570\u4F4D");
		lblNewLabel_4.setBounds(16, 137, 61, 16);
		panel_3.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("\u4E0B\u62C9\u6846\u8BBE\u7F6E");
		lblNewLabel_5.setBounds(16, 171, 72, 16);
		panel_3.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("\u6620\u5C04\u503C\u8BBE\u7F6E");
		lblNewLabel_6.setBounds(16, 199, 72, 16);
		panel_3.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("\u8BBE\u7F6E\u4E3A\u6309\u94AE");
		lblNewLabel_7.setBounds(16, 227, 86, 16);
		panel_3.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("\u5185\u5BB9\u989C\u8272");
		lblNewLabel_8.setBounds(16, 255, 61, 16);
		panel_3.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("\u80CC\u666F\u8272");
		lblNewLabel_9.setBounds(16, 283, 53, 16);
		panel_3.add(lblNewLabel_9);

		JLabel label = new JLabel("\u80CC\u666F\u56FE\u7247");
		label.setBounds(16, 311, 61, 16);
		panel_3.add(label);

		JLabel label_1 = new JLabel("\u5185\u5BB9\u5E03\u5C40");
		label_1.setBounds(16, 339, 61, 16);
		panel_3.add(label_1);

		JLabel label_2 = new JLabel("\u5B57\u4F53");
		label_2.setBounds(16, 367, 61, 16);
		panel_3.add(label_2);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(null, "\u8D85\u94FE\u63A5\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setBounds(16, 430, 458, 77);
		panel_3.add(panel_9);
		panel_9.setLayout(null);

		JLabel lblNewLabel_10 = new JLabel("\u4E8B\u4EF6\u8BBE\u7F6E");
		lblNewLabel_10.setBounds(6, 19, 61, 16);
		panel_9.add(lblNewLabel_10);

		JLabel label_3 = new JLabel("\u56FA\u5B9A\u663E\u793A\u503C");
		label_3.setBounds(6, 47, 73, 16);
		panel_9.add(label_3);

		textField_11 = new JTextField();
		textField_11.setBounds(90, 13, 273, 28);
		panel_9.add(textField_11);
		textField_11.setColumns(10);

		button_14 = new JButton("...");
		button_14.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				linkedEventSet();
			}

		});
		button_14.setBounds(377, 16, 35, 24);
		panel_9.add(button_14);

		textField_13 = new JTextField();
		textField_13.setBounds(91, 43, 221, 28);
		panel_9.add(textField_13);
		textField_13.setColumns(10);

		checkBox = new JCheckBox("\u663E\u793A\u4E3A\u8D85\u94FE\u63A5");
		checkBox.setBounds(342, 43, 110, 23);
		panel_9.add(checkBox);

		textField_1 = new JTextField();
		textField_1.setBounds(101, 0, 319, 28);
		panel_3.add(textField_1);
		textField_1.setColumns(10);

		comboBox = new JComboBox();
		comboBox.setBounds(101, 98, 319, 27);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "字符型", "数字型", "浮点型", "布尔型" }));
		panel_3.add(comboBox);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(101, 165, 319, 28);
		panel_3.add(textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(101, 193, 319, 28);
		panel_3.add(textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(271, 221, 146, 28);
		panel_3.add(textField_7);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(101, 249, 319, 28);
		panel_3.add(textField_8);

		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(101, 277, 319, 28);
		panel_3.add(textField_9);

		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(101, 305, 319, 28);
		panel_3.add(textField_10);

		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(101, 361, 319, 28);
		panel_3.add(textField_12);

		button_5 = new JButton("...");
		button_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectField();
			}
		});

		button_5.setBounds(425, 29, 35, 24);
		panel_3.add(button_5);

		button_7 = new JButton("...");
		button_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comboSet();
			}

		});
		button_7.setBounds(425, 167, 35, 24);
		panel_3.add(button_7);

		button_8 = new JButton("...");
		button_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mappingSet();
			}

		});
		button_8.setBounds(425, 195, 35, 24);
		panel_3.add(button_8);

		button_9 = new JButton("...");
		button_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonSet();
			}

		});
		button_9.setBounds(425, 223, 35, 24);
		panel_3.add(button_9);

		button_10 = new JButton("...");
		button_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selColorSet();
			}

		});
		button_10.setBounds(425, 251, 35, 24);
		panel_3.add(button_10);

		button_11 = new JButton("...");
		button_11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroundColorSet();
			}

		});
		button_11.setBounds(425, 279, 35, 24);
		panel_3.add(button_11);

		button_12 = new JButton("...");
		button_12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroupPicSet();
			}

		});
		button_12.setBounds(425, 307, 35, 24);
		panel_3.add(button_12);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(101, 334, 319, 27);
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "", "居左", "居中", "居右" }));
		panel_3.add(comboBox_1);

		button_13 = new JButton("...");
		button_13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fontSet();
			}

		});
		button_13.setBounds(426, 364, 35, 24);
		panel_3.add(button_13);

		JLabel label_4 = new JLabel("\u5206\u7EC4\u5C5E\u6027");
		label_4.setBounds(16, 395, 61, 16);
		panel_3.add(label_4);

		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(101, 390, 319, 27);
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "无", "记录数", "合计", "最大值", "最小值", "平均值" }));
		panel_3.add(comboBox_2);

		JPanel panel_10 = new JPanel();
		panel_10.setBounds(16, 519, 468, 124);
		panel_3.add(panel_10);
		panel_10.setLayout(null);

		checkBox_1 = new JCheckBox("\u5408\u8BA1");
		checkBox_1.setBounds(6, 12, 58, 23);
		panel_10.add(checkBox_1);

		checkBox_2 = new JCheckBox("\u663E\u793A");
		checkBox_2.setBounds(313, 12, 58, 23);
		panel_10.add(checkBox_2);

		checkBox_3 = new JCheckBox("\u5141\u8BB8\u7F16\u8F91");
		checkBox_3.setBounds(367, 12, 84, 23);
		panel_10.add(checkBox_3);

		checkBox_4 = new JCheckBox("\u663E\u793A\u4E3A\u9009\u62E9\u6846");
		checkBox_4.setBounds(6, 41, 110, 23);
		panel_10.add(checkBox_4);

		checkBox_5 = new JCheckBox("\u663E\u793A\u4E3A\u91D1\u989D(\u4F8B\u5982:1,0000,000.00)");
		checkBox_5.setBounds(128, 41, 227, 23);
		panel_10.add(checkBox_5);

		checkBox_6 = new JCheckBox("\u683C\u5F0F\u5316\u4E3A\u4E07\u4F4D");
		checkBox_6.setBounds(351, 41, 110, 23);
		panel_10.add(checkBox_6);

		checkBox_7 = new JCheckBox("\u663E\u793A\u5343\u5206\u7B26");
		checkBox_7.setBounds(6, 64, 100, 23);
		panel_10.add(checkBox_7);

		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(65, 6, 244, 34);
		panel_10.add(panel);
		panel.setLayout(null);

		rdbtnmax = new JRadioButton("\u6309\u4E3B\u8868(max)");
		rdbtnmax.setEnabled(false);
		rdbtnmax.setBounds(6, 6, 111, 23);
		panel.add(rdbtnmax);

		rdbtnsum = new JRadioButton("\u6309\u660E\u7EC6\u8868(sum)");
		rdbtnsum.setSelected(true);
		rdbtnsum.setEnabled(false);
		rdbtnsum.setBounds(117, 6, 121, 23);
		panel.add(rdbtnsum);

		checkBox_8 = new JCheckBox("\u663E\u793A\u767E\u5206\u6BD4");
		checkBox_8.setBounds(128, 64, 100, 23);
		panel_10.add(checkBox_8);

		checkBox_9 = new JCheckBox("\u662F\u5426\u6269\u5C55\u5217");
		checkBox_9.setBounds(6, 90, 100, 23);
		panel_10.add(checkBox_9);

		button_17 = new JButton("\u6269\u5C55\u5217\u8BBE\u7F6E");
		button_17.setEnabled(false);
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exPandColSeting();
			}

		});
		button_17.setBounds(114, 89, 91, 29);
		panel_10.add(button_17);
		bg = new ButtonGroup();
		for (Component c : panel.getComponents()) {
			bg.add((AbstractButton) c);
		}
		spinner_1 = new JSpinner();
		spinner_1.setBounds(101, 61, 319, 28);
		panel_3.add(spinner_1);

		textField_3 = new JTextField();
		textField_3.setBounds(101, 221, 168, 28);
		panel_3.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(101, 131, 319, 28);
		panel_3.add(textField_4);

		comboBox_4 = new JComboBox();
		comboBox_4.setBounds(101, 29, 319, 27);
		panel_3.add(comboBox_4);

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("\u5B57\u6BB5\u5217\u8868", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_4.add(panel_5, BorderLayout.WEST);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		panel_5.add(Box.createVerticalStrut((this.getPreferredSize().height) / 2 - 80));
		JButton btnNewButton = new JButton("<<");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				field2Col();
			}
		});
		panel_5.add(btnNewButton);
		panel_5.add(Box.createVerticalStrut(5));
		JButton button_6 = new JButton(">>");
		button_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				col2Field();
			}

		});
		panel_5.add(button_6);
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, BorderLayout.CENTER);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		list_1 = new JList();
		list1model = new DefaultListModel();
		list_1.setModel(list1model);
		scrollPane_1.setViewportView(list_1);

		JPanel panel_8 = new JPanel();
		panel_4.add(panel_8, BorderLayout.NORTH);

		textField = new JTextField();
		panel_8.add(textField);
		textField.setColumns(20);

		JButton button_4 = new JButton("\u641C\u7D22");
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchField();
			}

		});

		panel_8.add(button_4);

		JPanel panel_7 = new JPanel();
		panel_7.setMinimumSize(new Dimension(10, 0));
		add(panel_7, BorderLayout.SOUTH);
		init();
	}

	private Map<String, JComponent> cmpMap = new HashMap<String, JComponent>();

	private void setEnable(boolean flag) {
		for (JComponent c : cmpMap.values()) {
			c.setEnabled(flag);
			if (flag == false) {
				setComValue(c, "");
			}
		}
		button_5.setEnabled(flag);
		button_7.setEnabled(flag);
		button_8.setEnabled(flag);
		button_9.setEnabled(flag);
		button_10.setEnabled(flag);
		button_11.setEnabled(flag);
		button_12.setEnabled(flag);
		button_13.setEnabled(flag);
		button_14.setEnabled(flag);
	}

	private void setComValue(JComponent c, String value) {
		if (value == null) {
			value = "";
		}
		if (c instanceof JTextField) {
			((JTextField) c).setText(value);
		} else if (c instanceof JCheckBox) {
			if (c.equals(cmpMap.get("needsum"))) {
				((JCheckBox) c).setSelected("需要合计".equalsIgnoreCase(value));
			} else {
				((JCheckBox) c).setSelected("true".equalsIgnoreCase(value));
			}
		} else if (c instanceof JComboBox) {
			((JComboBox) c).setSelectedItem(value);
		} else if (c instanceof JSpinner) {
			if (CommonUtils.isNumberString(value)) {
				((JSpinner) c).setValue(Integer.parseInt(value));
			} else {
				((JSpinner) c).setValue(0);
			}
		}
	}

	private String getComValue(JComponent c) {
		if (c instanceof JTextField) {
			return ((JTextField) c).getText().trim();
		} else if (c instanceof JCheckBox) {
			if (c.equals(cmpMap.get("needsum"))) {
				return ((JCheckBox) c).isSelected() ? "需要合计" : "不需要合计";
			} else {
				return ((JCheckBox) c).isSelected() ? "true" : "false";
			}

		} else if (c instanceof JComboBox) {
			JComboBox combo = (JComboBox) c;
			Object obj = combo.getSelectedItem();
			if (!(obj instanceof XMLDto)) {
				String str = (String) obj;
				if (CommonUtils.isStrEmpty(str)) {
					return "";
				}
				return str;
			}
			XMLDto dto = (XMLDto) obj;
			return dto.getValue("value");
		} else if (c instanceof JSpinner) {
			JSpinner js = (JSpinner) c;

			try {
				js.commitEdit();
			} catch (ParseException e) {
				e.printStackTrace();
				js.setValue(200);
			}
			int i = (Integer) js.getValue();
			if (i > 0) {
				return i + "";
			} else {
				return "";
			}
		}
		return "";
	}

	private boolean change = false;
	private DefaultComboBoxModel fieldModel;

	private void init() {
		tabbedPane.setPreferredSize(new Dimension(500, 0));
		tabbedPane.setMinimumSize(new Dimension(500, 0));
		fieldModel = new DefaultComboBoxModel();
		fieldModel.addElement(new XMLDto("", ""));
		for (XMLDto dto : CompUtils.getFields()) {
			XMLDto item = new XMLDto(dto.toString(), dto.getValue("itemname"));
			item.setValue("type", dto.getValue("itemtype"));
			fieldModel.addElement(item);
		}
		comboBox_4.setModel(fieldModel);
		comboBox_4.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (colChangeUpdateUI) {
					return;
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Object obj = comboBox_4.getSelectedItem();
					if (obj instanceof XMLDto) {
						XMLDto dto = (XMLDto) obj;
						String name = dto.getValue("key");
						String type = dto.getValue("type");
						if (CommonUtils.isStrEmpty(name)) {
							textField_1.setText("");
						} else {
							textField_1.setText(name.split("->")[1]);
						}
						if ("N".equalsIgnoreCase(type) || "I".equalsIgnoreCase(type)) {
							comboBox.setSelectedIndex(1);
							textField_4.setText(2 + "");
							checkBox_7.setSelected(true);
							comboBox_1.setSelectedIndex(3);
						} else {
							comboBox.setSelectedIndex(0);
							textField_4.setText("");
							checkBox_7.setSelected(false);
							comboBox_1.setSelectedIndex(0);
						}
					}
				}
			}
		});
		cmpMap.put("fieldname", comboBox_4);
		cmpMap.put("canedit", checkBox_3);
		cmpMap.put("visible", checkBox_2);
		cmpMap.put("aligntype", comboBox_1);
		cmpMap.put("grouptype", comboBox_2);
		cmpMap.put("fieldtype", comboBox);
		cmpMap.put("comboboxstring", textField_5);
		cmpMap.put("valuemapping", textField_6);
		cmpMap.put("title", textField_1);
		cmpMap.put("myfont", textField_12);
		cmpMap.put("background", textField_9);
		cmpMap.put("needsum", checkBox_1);
		cmpMap.put("showcheckbox", checkBox_4);
		cmpMap.put("modules", textField_7);
		cmpMap.put("showmyria", checkBox_6);
		cmpMap.put("linkevent", textField_11);
		cmpMap.put("showaslink", checkBox);
		cmpMap.put("showamount", checkBox_5);
		cmpMap.put("showcommas", checkBox_7);
		cmpMap.put("constvalue", textField_13);
		cmpMap.put("width", spinner_1);
		cmpMap.put("backgroundpic", textField_10);
		cmpMap.put("contentcolor", textField_8);
		cmpMap.put("showbuttonandname", textField_3);
		cmpMap.put("datadeci", textField_4);
		cmpMap.put("showpercentage", checkBox_8);
		propMap.put("expandcol", "");
		checkBox_1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean flag = e.getStateChange() == ItemEvent.SELECTED;
				panel.setEnabled(flag);
				rdbtnsum.setEnabled(flag);
				rdbtnmax.setEnabled(flag);
				if (flag) {
					rdbtnsum.setSelected(true);
				} else {
					rdbtnmax.setSelected(true);
				}
			}
		});
		textField_4.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				String text = textField_4.getText().trim();
				if (!CommonUtils.isNumberString(text)) {
					textField_4.setText("");
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		setEnable(false);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (change == false) {
					return;
				}
				if (list.getSelectedIndices().length > 1) {
					return;
				}
				if (e.getValueIsAdjusting()) {
					saveParam2Col();
				} else {
					colChangeUpdateUI = true;
					int index = list.getSelectedIndex();
					if (index < 0) {
						setEnable(false);
						return;
					}
					lastSelect = index;
					ColBean cb = (ColBean) listmodel.getElementAt(lastSelect);
					updateParamUI(cb);
					setEnable(true);
					colChangeUpdateUI = false;
				}

			}
		});

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedPane.getSelectedIndex();
				if (index == 1) {
					initFieldSelect();
				}
			}
		});

		checkBox_9.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				button_17.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				if (e.getStateChange() != ItemEvent.SELECTED) {
					propMap.put("expandcol", "");
				}
			}
		});
	}

	private TNewGrid grid;
	private int lastSelect = -1;
	private JTextField textField_3;
	private JTextField textField_4;

	private void selectField() {
		ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
		XMLDto select = (XMLDto) comboBox_4.getSelectedItem();
		String value = select.getValue("value");
		XMLDto dto = null;
		if (!CommonUtils.isStrEmpty(value)) {
			dto = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", value);
		}
		pnl.setValue(dto);
		pnl.edit(dialog, null);
		if (pnl.isChange()) {
			dto = pnl.getSelect();
			if (dto == null) {
				textField_1.setText("");
				comboBox_4.setSelectedIndex(0);
			} else {
				textField_1.setText(dto.getValue("itemlabel"));
				comboBox_4.setSelectedItem(new XMLDto(dto.toString(), dto.getValue("itemname")));
			}

		}
	}

	private void buttonSet() {
		ModulesEventEditor editor = new ModulesEventEditor();
		PropDto prop = new PropDto();
		prop.setValue(textField_7.getText());
		editor.edit(prop, this.dialog);
		textField_7.setText(prop.getValue());
	}

	private void comboSet() {
		ProValueEditor editor = new ProValueEditor();
		PropDto prop = new PropDto();
		prop.setValue(textField_5.getText());
		editor.edit(prop, this.dialog);
		textField_5.setText(prop.getValue());

	}

	private void mappingSet() {
		ProValueEditor editor = new ProValueEditor();
		PropDto prop = new PropDto();
		prop.setValue(textField_6.getText());
		editor.edit(prop, this.dialog);
		textField_6.setText(prop.getValue());
	}

	private void selColorSet() {
		CellColorEditor editor = new CellColorEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("value", textField_8.getText().trim());
		editor.edit(dialog, props);
		if (editor.isSubmit()) {
			textField_8.setText(props.get("value"));
		}
	}

	private void exPandColSeting() {
		ExpandColEditor pnl = new ExpandColEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("value", propMap.get("expandcol"));
		pnl.edit(dialog, props);
		if (pnl.isSubmit()) {
			propMap.put("expandcol", props.get("value"));
		}

	}

	private void backgroundColorSet() {
		BackgroupEditor editor = new BackgroupEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("value", textField_9.getText().trim());
		editor.edit(dialog, props);
		if (editor.isSubmit()) {
			textField_9.setText(props.get("value"));
		}
	}

	private void backgroupPicSet() {
		BackgroupPicEditor editor = new BackgroupPicEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("value", textField_10.getText().trim());
		editor.edit(dialog, props);
		if (editor.isSubmit()) {
			textField_10.setText(props.get("value"));
		}
	}

	private void fontSet() {
		FontStyleEditor editor = new FontStyleEditor();
		PropDto prop = new PropDto();
		prop.setValue(textField_12.getText().trim());
		editor.edit(prop, this.dialog);
		textField_12.setText(prop.getValue());
	}

	private void linkedEventSet() {
		ModulesEventEditor editor = new ModulesEventEditor();
		PropDto prop = new PropDto();
		prop.setValue(textField_11.getText());
		editor.edit(prop, this.dialog);
		textField_11.setText(prop.getValue());
	}

	private void downMove() {
		change = false;
		int[] indexs = list.getSelectedIndices();
		if (listmodel.getSize() <= 0 || indexs.length < 0) {
			return;
		}
		if (indexs[indexs.length - 1] == listmodel.getSize() - 1) {
			return;
		}
		int[] selectIndexs = new int[indexs.length];
		for (int i = indexs.length - 1; i >= 0; i--) {
			int index = indexs[i];
			selectIndexs[i] = index + 1;
			Object cur = listmodel.remove(index);
			listmodel.insertElementAt(cur, selectIndexs[i]);
		}
		list.setSelectedIndices(selectIndexs);
		lastSelect++;
		change = true;
	}

	private void upMove() {
		change = false;
		int[] indexs = list.getSelectedIndices();
		if (listmodel.getSize() <= 0 || indexs.length < 0) {
			return;
		}
		if (indexs[0] == 0) {
			return;
		}
		int[] selectIndexs = new int[indexs.length];
		for (int i = 0; i < indexs.length; i++) {
			int index = indexs[i];
			selectIndexs[i] = index - 1;
			Object cur = listmodel.remove(index);
			listmodel.insertElementAt(cur, selectIndexs[i]);
		}
		list.setSelectedIndices(selectIndexs);
		lastSelect--;
		change = true;
	}

	private void saveParam2Col() {
		if (lastSelect < 0) {
			return;
		}
		ColBean cb = (ColBean) listmodel.getElementAt(lastSelect);
		for (String key : cmpMap.keySet()) {
			cb.setProp(key, getComValue(cmpMap.get(key)));
		}
		for (String key : propMap.keySet()) {
			cb.setProp(key, propMap.get(key));
		}
		if ("需要合计".equals(cb.getValue("needsum"))) {
			cb.setProp("sumtype", rdbtnmax.isSelected() ? "main" : "detail");
		}
	}

	private void updateParamUI(ColBean cb) {
		for (String key : cmpMap.keySet()) {
			String value = cb.getValue(key);
			JComponent com = cmpMap.get(key);
			if ("fieldname".equalsIgnoreCase(key)) {
				if (!CommonUtils.isStrEmpty(value)) {
					XMLDto dto = CompUtils.getComboValue(fieldModel, value);
					if (dto == null) {
						dto = new XMLDto(cb.getValue("title") + "-->" + value, value);
						fieldModel.addElement(dto);
					}
					comboBox_4.setSelectedItem(dto);
				} else {
					comboBox_4.setSelectedIndex(0);
				}

			} else {
				setComValue(com, value);
			}

		}
		for (String key : propMap.keySet()) {
			if ("expandcol".equals(key) && !CommonUtils.isStrEmpty(cb.getValue(key), true)) {
				checkBox_9.setSelected(true);
			} else {
				checkBox_9.setSelected(false);
			}
			propMap.put(key, cb.getValue(key));
		}

		if ("需要合计".equals(cb.getValue("needsum"))) {
			if ("detail".equals(cb.getValue("sumtype"))) {
				rdbtnsum.setSelected(true);
			} else {
				rdbtnmax.setSelected(true);
			}

		}
	}

	private void addCol() {
		change = false;
		int[] indexs = list.getSelectedIndices();
		int insertCount = listmodel.getSize();
		if (indexs.length == 1) {
			insertCount = indexs[0] + 1;
		}
		ColBean obj = new ColBean();
		obj.setProp("width", "90");
		obj.setProp("visible", "true");
		listmodel.insertElementAt(obj, insertCount);
		change = true;
		saveParam2Col();
		list.setSelectedIndex(insertCount);
	}

	private void delCol() {
		change = false;
		Object[] values = list.getSelectedValues();
		if (values.length <= 0) {
			return;
		}
		int newSel = list.getSelectedIndex();
		list.clearSelection();
		for (Object obj : values) {
			listmodel.removeElement(obj);
		}
		lastSelect = -1;
		change = true;
		if (listmodel.getSize() <= 0) {
			setEnable(false);
			return;
		}
		if (newSel != 0) {
			newSel--;
		}

		list.setSelectedIndex(newSel);
	}

	private void initFieldSelect() {
		list1model.clear();
		List<String> colfields = new ArrayList<String>();
		for (int i = 0; i < listmodel.getSize(); i++) {
			ColBean cb = (ColBean) listmodel.getElementAt(i);
			String fieldname = cb.getValue("fieldname");
			colfields.add(fieldname);
		}
		List<XMLDto> fields = CompUtils.getFields();
		for (XMLDto f : fields) {
			String itemname = f.getValue("itemname");
			if (colfields.contains(itemname)) {
				continue;
			}
			list1model.addElement(f);
		}
	}

	private void field2Col() {
		Object[] values = list_1.getSelectedValues();
		if (values.length < 0) {
			return;
		}
		int index = list.getSelectedIndex();
		if (index < 0) {
			index = listmodel.getSize();
		} else {
			index++;
		}
		for (Object obj : values) {
			XMLDto dto = (XMLDto) obj;
			String type = dto.getValue("itemtype");
			ColBean cb = new ColBean();
			cb.setProp("fieldname", dto.getValue("itemname"));
			cb.setProp("title", dto.getValue("itemlabel"));
			cb.setProp("width", "90");
			if ("N".equals(type) || "I".equals(type)) {
				cb.setProp("fieldtype", "数字型");
				cb.setProp("showcommas", "true");
				cb.setProp("datadeci", "2");
			} else {
				cb.setProp("fieldtype", "字符型");
			}
			cb.setProp("visible", "true");
			listmodel.insertElementAt(cb, index++);
			list1model.removeElement(dto);
		}
	}

	private void col2Field() {
		Object[] values = list.getSelectedValues();
		if (values.length < 0) {
			return;
		}
		for (Object obj : values) {
			ColBean dto = (ColBean) obj;
			listmodel.removeElement(dto);
		}
		initFieldSelect();
	}

	private void save() {
		saveParam2Col();
		LinkedHashMap<String, TNewColumn> columnMap = new LinkedHashMap<String, TNewColumn>();
		for (int i = 0; i < listmodel.getSize(); i++) {
			ColBean cb = (ColBean) listmodel.getElementAt(i);
			TNewColumn col = new TNewColumn(grid);
			Map<String, String> props = cb.getProps();
			for (String key : props.keySet()) {
				col.setPropValue(key, props.get(key));
			}
			String title = col.getPropValue("title");
			if (CommonUtils.isStrEmpty(title)) {
				GUIUtils.showMsg(dialog, "标题不能为空");
				list.setSelectedValue(cb, true);
				return;
			}
			if (columnMap.containsKey(title)) {
				GUIUtils.showMsg(dialog, "存在相同标题");
				list.setSelectedValue(cb, true);
				return;
			}
			columnMap.put(title, col);
		}
		grid.setColumnMap(columnMap);
		grid.upateUIByProps();
		dialog.dispose();
	}

	private void searchField() {
		String text = textField.getText().trim().toLowerCase();
		if (CommonUtils.isStrEmpty(text)) {
			return;
		}
		int searchCout = 0;
		int i = list_1.getSelectedIndex() + 1;
		if (i > list1model.getSize() - 1) {
			i = 0;
		}
		for (; i < list1model.getSize(); i++) {
			searchCout++;
			Object obj = list1model.getElementAt(i);
			if (obj.toString().toLowerCase().indexOf(text) >= 0) {
				list_1.setSelectedValue(obj, true);
				return;
			}
			if (searchCout >= list1model.getSize()) {
				return;
			} else if (i == list1model.getSize() - 1) {
				i = -1;
			}
		}
	}

	@Override
	public void edit(PropDto prop, Window owner) {
		grid = (TNewGrid) prop.getCom();
		LinkedHashMap<String, TNewColumn> columnMap = grid.getColumnMap();
		for (TNewColumn c : columnMap.values()) {
			ColBean cb = new ColBean();
			cb.init(c);
			listmodel.addElement(cb);
		}
		change = true;
		this.dialog = GUIUtils.getDialog(owner, prop.getPropcname() + "设置界面", this);
		dialog.setVisible(true);
	}

	class ColBean {

		public void init(TNewColumn column) {
			Map<String, PropDto> temp = column.listProp();
			if (temp == null || temp.isEmpty()) {
				return;
			}
			for (String key : temp.keySet()) {
				PropDto value = temp.get(key);
				props.put(key, value.getValue());
			}
			logger.debug(props);
		}

		private Map<String, String> props = new LinkedHashMap<String, String>();

		public Map<String, String> getProps() {
			return props;
		}

		public void setProp(String key, String value) {
			props.put(key, value);
		}

		public String getValue(String key) {
			return props.get(key);
		}

		@Override
		public String toString() {
			String title = props.get("title");
			if (CommonUtils.isStrEmpty(title)) {
				return "[]";
			}
			String fieldname = props.get("fieldname");
			return title + "[" + fieldname + "]";
		}
	}
}
