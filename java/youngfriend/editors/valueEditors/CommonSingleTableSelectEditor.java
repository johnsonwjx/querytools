package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.tree.BackedList;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.common.util.StringUtils;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class CommonSingleTableSelectEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private Map<String, Object> cmpMap = new HashMap<String, Object>();
	private String[] title = { "字段标签", "字段名称", "字段宽度", "是否显示", "是否返回", "对应的表单项目", "新标签" };
	private final int index_label = 0;
	private final int index_name = 1;
	private final int index_width = 2;
	private final int index_visible = 3;
	private final int index_return = 4;
	private final int index_field = 5;
	private final int index_newlabel = 6;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private DefaultTableModel tableModel;
	private JTable tableFields;
	private DefaultListModel tableFieldModel;
	private SetOtherDataEditor setotherdata = new SetOtherDataEditor();

	public CommonSingleTableSelectEditor() {
		initComponents();
		init();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(1123, 665));
		showType = new javax.swing.ButtonGroup();
		getDataTime = new javax.swing.ButtonGroup();
		selectType = new javax.swing.ButtonGroup();
		isBottom = new javax.swing.ButtonGroup();
		needCorp = new javax.swing.ButtonGroup();
		condiPanel = new javax.swing.ButtonGroup();
		jPanel0 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setBounds(0, 0, 525, 267);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 24, 69, 16);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(6, 58, 69, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(6, 92, 56, 16);
		serviceName = new javax.swing.JTextField();
		serviceName.setBounds(87, 18, 283, 28);
		jButton1 = new javax.swing.JButton();
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sName = serviceName.getText().trim();
				if (CommonUtils.isStrEmpty(sName)) {
					GUIUtils.showMsg(dialog, "请填写服务名，如 store2");
					return;
				}
				if (sName.equals(oldServiceName)) {
					return;
				}
				oldServiceName = sName;
				tables = CommonUtils.getTableByService(sName);
				String param = setotherdata.getParamTxt(serviceName.getText().trim() + ".simplequery");
				DataParam.setText(param);
			}

		});
		jButton1.setBounds(376, 19, 122, 29);
		setParamsButton = new javax.swing.JButton();
		setParamsButton.setBounds(376, 87, 122, 29);
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setBounds(6, 122, 513, 139);
		DataParam = new javax.swing.JTextArea();
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(537, 0, 578, 267);
		jPanel3 = new javax.swing.JPanel();
		jPanel3.setBounds(12, 18, 105, 80);
		jRadioButton1 = new javax.swing.JRadioButton();
		jRadioButton1.setBounds(6, 18, 84, 23);
		jRadioButton2 = new javax.swing.JRadioButton();
		jRadioButton2.setBounds(6, 47, 84, 23);
		jPanel4 = new javax.swing.JPanel();
		jPanel4.setBounds(123, 18, 105, 80);
		jRadioButton3 = new javax.swing.JRadioButton();
		jRadioButton3.setBounds(6, 18, 84, 23);
		jRadioButton4 = new javax.swing.JRadioButton();
		jRadioButton4.setBounds(6, 47, 97, 23);
		jPanel5 = new javax.swing.JPanel();
		jPanel5.setLocation(240, 18);
		jRadioButton5 = new javax.swing.JRadioButton();
		jRadioButton5.setBounds(6, 18, 58, 23);
		jRadioButton6 = new javax.swing.JRadioButton();
		jRadioButton6.setBounds(6, 47, 58, 23);
		jPanel6 = new javax.swing.JPanel();
		jPanel6.setBounds(12, 113, 105, 80);
		jRadioButton7 = new javax.swing.JRadioButton();
		jRadioButton7.setBounds(6, 18, 84, 23);
		jRadioButton8 = new javax.swing.JRadioButton();
		jRadioButton8.setBounds(6, 47, 84, 23);
		jPanel10 = new javax.swing.JPanel();
		jPanel10.setBounds(468, 18, 98, 80);
		jRadioButton15 = new javax.swing.JRadioButton();
		jRadioButton15.setSelected(true);
		jRadioButton15.setBounds(6, 24, 58, 23);
		jRadioButton16 = new javax.swing.JRadioButton();
		jRadioButton16.setBounds(6, 53, 71, 23);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(12, 205, 56, 16);
		buildTreeField = new javax.swing.JTextField();
		buildTreeField.setBounds(86, 199, 282, 28);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(12, 239, 56, 16);
		formtitle = new javax.swing.JTextField();
		formtitle.setBounds(86, 233, 282, 28);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(440, 205, 56, 16);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setBounds(440, 239, 56, 16);
		height = new javax.swing.JSpinner();
		height.setBounds(502, 199, 70, 28);
		width = new javax.swing.JSpinner();
		width.setBounds(502, 233, 70, 28);
		jPanel13 = new javax.swing.JPanel();
		jPanel13.setBounds(351, 18, 105, 80);
		jRadioButton17 = new javax.swing.JRadioButton();
		jRadioButton17.setBounds(6, 18, 58, 23);
		jRadioButton18 = new javax.swing.JRadioButton();
		jRadioButton18.setBounds(6, 47, 58, 23);
		jPanel11 = new javax.swing.JPanel();
		jPanel11.setBounds(0, 282, 1115, 342);
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jTabbedPane1.setBounds(6, 18, 1103, 318);
		jScrollPane2 = new javax.swing.JScrollPane();
		jPanel16 = new javax.swing.JPanel();
		jScrollPane15 = new javax.swing.JScrollPane();
		jScrollPane15.setBounds(0, 0, 1082, 84);
		jTextArea4 = new javax.swing.JTextArea();
		jLabel14 = new javax.swing.JLabel();
		jLabel14.setBounds(0, 90, 924, 15);
		jPanel22 = new javax.swing.JPanel();
		jPanel22.setBounds(0, 111, 1082, 161);
		jScrollPane36 = new javax.swing.JScrollPane();
		jScrollPane36.setBounds(0, 20, 303, 135);
		jList29 = new javax.swing.JList();
		jScrollPane37 = new javax.swing.JScrollPane();
		jScrollPane37.setBounds(309, 20, 429, 135);
		jList30 = new javax.swing.JList();
		jScrollPane38 = new javax.swing.JScrollPane();
		jScrollPane38.setBounds(744, 20, 124, 135);
		jList31 = new javax.swing.JList();
		jScrollPane39 = new javax.swing.JScrollPane();
		jScrollPane39.setBounds(874, 20, 208, 135);
		jList32 = new javax.swing.JList();
		jLabel25 = new javax.swing.JLabel();
		jLabel25.setBounds(0, 0, 78, 16);
		jLabel26 = new javax.swing.JLabel();
		jLabel26.setBounds(309, 0, 69, 16);

		jPanel0.setName("jPanel0");

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("表信息设置"));
		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("通用服务名:");
		jLabel1.setName("jLabel1"); // NOI18N

		jLabel2.setText("选择业务表:");
		jLabel2.setName("jLabel2"); // NOI18N

		jLabel3.setText("取数参数:");
		jLabel3.setName("jLabel3"); // NOI18N

		serviceName.setName("serviceName"); // NOI18N

		jButton1.setText("获取表信息");
		jButton1.setName("jButton1");

		setParamsButton.setText("设置取数参数");
		setParamsButton.setName("setParamsButton"); // NOI18N
		setParamsButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setCustomCondiValue();
			}

		});

		jScrollPane1.setName("jScrollPane1"); // NOI18N

		DataParam.setColumns(20);
		DataParam.setLineWrap(true);
		DataParam.setRows(5);
		DataParam.setName("DataParam"); // NOI18N
		jScrollPane1.setViewportView(DataParam);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("参数设置"));
		jPanel2.setName("jPanel2"); // NOI18N

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("显示方式"));
		jPanel3.setName("jPanel3"); // NOI18N
		jPanel3.setPreferredSize(new java.awt.Dimension(105, 80));

		showType.add(jRadioButton1);
		jRadioButton1.setSelected(true);
		jRadioButton1.setText("列表显示");
		jRadioButton1.setName("jRadioButton1"); // NOI18N

		showType.add(jRadioButton2);
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
		jPanel4.setPreferredSize(new java.awt.Dimension(105, 80));

		getDataTime.add(jRadioButton3);
		jRadioButton3.setSelected(true);
		jRadioButton3.setText("分批取数");
		jRadioButton3.setEnabled(false);

		getDataTime.add(jRadioButton4);
		jRadioButton4.setText("一次性取数");
		jRadioButton4.setEnabled(false);
		jRadioButton4.setName("jRadioButton4");

		jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("选择方式"));
		jPanel5.setMaximumSize(new java.awt.Dimension(105, 80));
		jPanel5.setName("jPanel5"); // NOI18N
		jPanel5.setPreferredSize(new java.awt.Dimension(105, 80));
		jPanel5.setSize(new Dimension(105, 80));

		selectType.add(jRadioButton5);
		jRadioButton5.setSelected(true);
		jRadioButton5.setText("单选");

		selectType.add(jRadioButton6);
		jRadioButton6.setText("多选");
		jRadioButton6.setName("jRadioButton6");

		jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("返回范围"));
		jPanel6.setName("jPanel6"); // NOI18N
		jPanel6.setPreferredSize(new java.awt.Dimension(105, 80));

		isBottom.add(jRadioButton7);
		jRadioButton7.setSelected(true);
		jRadioButton7.setText("返回底层");
		jRadioButton7.setEnabled(false);
		jRadioButton7.setName("jRadioButton7"); // NOI18N

		isBottom.add(jRadioButton8);
		jRadioButton8.setText("返回所有");
		jRadioButton8.setEnabled(false);
		jRadioButton8.setName("jRadioButton8");

		jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("集团权限控制"));
		jPanel10.setName("jPanel10"); // NOI18N

		needCorp.add(jRadioButton15);
		jRadioButton15.setText("需要");
		jRadioButton15.setName("jRadioButton15"); // NOI18N

		needCorp.add(jRadioButton16);
		jRadioButton16.setText("不需要");
		jRadioButton16.setName("jRadioButton16");

		jLabel4.setText("建树字段:");
		jLabel4.setName("jLabel4"); // NOI18N

		buildTreeField.setText("CODE");
		buildTreeField.setEnabled(false);
		buildTreeField.setName("buildTreeField"); // NOI18N

		jLabel5.setText("窗口标题:");
		jLabel5.setName("jLabel5"); // NOI18N

		formtitle.setName("formtitle"); // NOI18N

		jLabel6.setText("默认高度:");
		jLabel6.setName("jLabel6"); // NOI18N

		jLabel7.setText("默认宽度:");
		jLabel7.setName("jLabel7"); // NOI18N

		height.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(800), Integer.valueOf(0), null, Integer.valueOf(10)));
		height.setName("height"); // NOI18N

		width.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(600), Integer.valueOf(0), null, Integer.valueOf(10)));
		width.setName("width"); // NOI18N

		jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("动态条件来源"));
		jPanel13.setName("jPanel13");
		jPanel13.setPreferredSize(new java.awt.Dimension(105, 80));

		condiPanel.add(jRadioButton17);
		jRadioButton17.setSelected(true);
		jRadioButton17.setText("单选");
		jRadioButton17.setName("jRadioButton17");

		condiPanel.add(jRadioButton18);
		jRadioButton18.setText("多选");
		jRadioButton18.setName("jRadioButton18");

		jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("条件相关设置"));
		jPanel11.setName("jPanel11"); // NOI18N

		jTabbedPane1.setName("jTabbedPane1"); // NOI18N

		jScrollPane2.setName("jScrollPane2");

		jTabbedPane1.addTab("字段设置窗口", jScrollPane2);

		jPanel16.setName("jPanel16"); // NOI18N

		jScrollPane15.setName("jScrollPane15"); // NOI18N

		jTextArea4.setColumns(20);
		jTextArea4.setRows(5);
		jTextArea4.setName("jTextArea4"); // NOI18N
		jScrollPane15.setViewportView(jTextArea4);

		jLabel14.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		jLabel14.setText("\u8BF4\u660E:\u7528[]\u62EC\u8D77\u6765,\u5B57\u6BB5\u540D\u7528{}\u62EC\u8D77\u6765,\u4E3E\u4F8B:1=1 [and project_code='{project_code}'] \u5982\u679C\u6709\u591A\u4E2A\u63A7\u4EF6\u5B57\u6BB5\u540D\u76F8\u540C\u8BF7\u7528\u63A7\u4EF6Id,\u5982: {project_code} \u66FF\u6362\u6210{\u63A7\u4EF6Id} ");
		jLabel14.setName("jLabel14"); // NOI18N

		jPanel22.setName("jPanel22"); // NOI18N

		jScrollPane36.setName("jScrollPane36"); // NOI18N

		jList29.setName("jList29"); // NOI18N
		jScrollPane36.setViewportView(jList29);

		jScrollPane37.setName("jScrollPane37"); // NOI18N

		jList30.setName("jList30"); // NOI18N
		jList30.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList30MouseClicked(evt);
			}
		});
		jScrollPane37.setViewportView(jList30);

		jScrollPane38.setName("jScrollPane38"); // NOI18N

		jList31.setModel(new javax.swing.AbstractListModel() {
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
		jList31.setName("jList31"); // NOI18N
		jList31.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList31MouseClicked(evt);
			}
		});
		jScrollPane38.setViewportView(jList31);

		jScrollPane39.setName("jScrollPane39"); // NOI18N

		jList32.setModel(new javax.swing.AbstractListModel() {
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
		jList32.setName("jList32"); // NOI18N
		jList32.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList32MouseClicked(evt);
			}
		});
		jScrollPane39.setViewportView(jList32);

		jLabel25.setText("台帐表字段：");
		jLabel25.setName("jLabel25"); // NOI18N

		jLabel26.setText("查询类字段:");
		jLabel26.setName("jLabel26");

		jTabbedPane1.addTab("数据过滤条件设置", jPanel16);
		jPanel16.setLayout(null);
		jPanel16.add(jScrollPane15);
		jPanel16.add(jLabel14);
		jPanel16.add(jPanel22);
		jPanel22.setLayout(null);
		jPanel22.add(jScrollPane36);
		jPanel22.add(jLabel25);
		jPanel22.add(jLabel26);
		jPanel22.add(jScrollPane37);
		jPanel22.add(jScrollPane38);
		jPanel22.add(jScrollPane39);
		setLayout(new BorderLayout(0, 0));
		jPanel12 = new javax.swing.JPanel();
		jPanel12.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(jPanel12, BorderLayout.SOUTH);
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();

		jPanel12.setName("jPanel12");

		jButton3.setText("确定");
		jButton3.setName("jButton3"); // NOI18N
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				save();
			}
		});

		jButton4.setText("取消");
		jButton4.setName("jButton4"); // NOI18N
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
		jPanel12.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		jPanel12.add(jButton3);
		jPanel12.add(jButton4);
		add(jPanel0);
		jPanel0.setLayout(null);
		jPanel0.add(jPanel1);
		jPanel1.setLayout(null);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel1);
		jPanel1.add(serviceName);
		jPanel1.add(jLabel3);
		jPanel1.add(jButton1);
		jPanel1.add(setParamsButton);
		jPanel1.add(jScrollPane1);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(87, 52, 364, 28);
		jPanel1.add(textField);
		textField.setColumns(10);

		button = new JButton("...");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectTable();
			}
		});
		button.setBounds(463, 53, 35, 29);
		jPanel1.add(button);
		jPanel0.add(jPanel2);
		jPanel2.setLayout(null);
		jPanel2.add(jLabel5);
		jPanel2.add(formtitle);
		jPanel2.add(jLabel4);
		jPanel2.add(buildTreeField);
		jPanel2.add(jLabel6);
		jPanel2.add(height);
		jPanel2.add(jLabel7);
		jPanel2.add(width);
		jPanel2.add(jPanel6);
		jPanel6.setLayout(null);
		jPanel6.add(jRadioButton7);
		jPanel6.add(jRadioButton8);
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
		jPanel2.add(jPanel13);
		jPanel13.setLayout(null);
		jPanel13.add(jRadioButton17);
		jPanel13.add(jRadioButton18);
		jPanel2.add(jPanel10);
		jPanel10.setLayout(null);
		jPanel10.add(jRadioButton15);
		jPanel10.add(jRadioButton16);

		checkBox = new JCheckBox("\u662F\u5426\u5206\u9875");
		checkBox.setSelected(true);
		checkBox.setBounds(133, 109, 84, 23);
		jPanel2.add(checkBox);

		checkBox_1 = new JCheckBox("\u5C55\u5F00\u5168\u90E8");
		checkBox_1.setEnabled(false);
		checkBox_1.setBounds(133, 137, 84, 23);
		jPanel2.add(checkBox_1);

		checkBox_2 = new JCheckBox("\u663E\u793A\u4EE3\u7801");
		checkBox_2.setEnabled(false);
		checkBox_2.setBounds(133, 164, 98, 23);
		jPanel2.add(checkBox_2);
		jPanel0.add(jPanel11);
		jPanel11.setLayout(null);
		jPanel11.add(jTabbedPane1);
	}

	private void jRadioButton2ItemStateChanged(java.awt.event.ItemEvent evt) {
		boolean falg = evt.getStateChange() == ItemEvent.SELECTED;
		jRadioButton3.setEnabled(falg);
		jRadioButton4.setEnabled(falg);
		jRadioButton7.setEnabled(falg);
		jRadioButton8.setEnabled(falg);
		checkBox_1.setEnabled(falg);
		checkBox_2.setEnabled(falg);
		buildTreeField.setEnabled(falg);
		checkBox.setEnabled(!falg);
	}

	private void jList30MouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			XMLDto dto = (XMLDto) jList30.getSelectedValue();
			if (dto != null) {
				CompUtils.textareaInsertText(jTextArea4, " " + dto.getValue("itemname"));
			}
		}
	}

	private void jList31MouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			int index = jList31.locationToIndex(evt.getPoint());
			if (index >= 0) {
				Object item = jList31.getModel().getElementAt(index);
				CompUtils.textareaInsertText(jTextArea4, " " + item.toString());
			}
		}
	}

	private void jList32MouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			int index = jList32.locationToIndex(evt.getPoint());
			if (index >= 0) {
				Object item = jList32.getModel().getElementAt(index);
				CompUtils.textareaInsertText(jTextArea4, " " + item.toString());
			}

		}
	}

	private String getSelectedRadioButton(ButtonGroup buttonGroup) {
		int index = 0;
		Enumeration<AbstractButton> buttons = buttonGroup.getElements();
		while (buttons.hasMoreElements()) {
			AbstractButton radioButton = buttons.nextElement();
			if (radioButton.isSelected()) {
				return String.valueOf(index);
			}
			index++;
		}
		return "";
	}

	private void setCustomCondiValue() {
		Map<String, String> props = new HashMap<String, String>();
		props.put("serviceName", serviceName.getText());
		props.put("value", DataParam.getText());
		setotherdata.edit(dialog, props);
		if (setotherdata.isSubmit()) {
			DataParam.setText(props.get("value"));
		}

	}

	private javax.swing.JTextArea DataParam;
	private javax.swing.JTextField buildTreeField;
	private javax.swing.ButtonGroup condiPanel;
	private javax.swing.JTextField formtitle;
	private javax.swing.ButtonGroup getDataTime;
	private javax.swing.JSpinner height;
	private javax.swing.ButtonGroup isBottom;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JList jList29;
	private javax.swing.JList jList30;
	private javax.swing.JList jList31;
	private javax.swing.JList jList32;
	private javax.swing.JPanel jPanel0;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel22;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton15;
	private javax.swing.JRadioButton jRadioButton16;
	private javax.swing.JRadioButton jRadioButton17;
	private javax.swing.JRadioButton jRadioButton18;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JRadioButton jRadioButton3;
	private javax.swing.JRadioButton jRadioButton4;
	private javax.swing.JRadioButton jRadioButton5;
	private javax.swing.JRadioButton jRadioButton6;
	private javax.swing.JRadioButton jRadioButton7;
	private javax.swing.JRadioButton jRadioButton8;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane15;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane36;
	private javax.swing.JScrollPane jScrollPane37;
	private javax.swing.JScrollPane jScrollPane38;
	private javax.swing.JScrollPane jScrollPane39;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTextArea jTextArea4;
	private javax.swing.ButtonGroup needCorp;
	private javax.swing.ButtonGroup selectType;
	private javax.swing.JTextField serviceName;
	private javax.swing.JButton setParamsButton;
	private javax.swing.ButtonGroup showType;
	private javax.swing.JSpinner width;
	private boolean submit;
	private Map<String, String> props;
	private JTextField textField;
	private JButton button;
	private List<XMLDto> tables = null;
	private XMLDto table = null;
	private List<XMLDto> fields;
	private String oldServiceName;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;

	private void save() {
		try {
			CompUtils.stopTabelCellEditor(tableFields);
			String servicename = serviceName.getText().trim();
			String tablename = "";
			if (table == null) {
				GUIUtils.showMsg(dialog, "请选择业务表");
				return;

			}
			tablename = table.getValue("name");
			String dataParam = DataParam.getText();
			String ismultipage = checkBox.isSelected() ? "0" : "1";
			String selecttype = getSelectedRadioButton(selectType);
			String getdataTime = getSelectedRadioButton(getDataTime);
			String isbottom = getSelectedRadioButton(isBottom);
			String showtype = getSelectedRadioButton(showType);
			String expendall = checkBox_1.isSelected() ? "0" : "1";
			String showcode = checkBox_2.isSelected() ? "0" : "1";
			String needcorp = getSelectedRadioButton(needCorp);
			String condipanel = getSelectedRadioButton(condiPanel);
			String buildtreefield = buildTreeField.getText().trim();
			String windowparam = "formtitle:" + formtitle.getText() + "||width:" + width.getValue() + "||height:" + height.getValue();
			StringBuffer fields = new StringBuffer();
			String outPutParams = "";
			int rowCount = tableFields.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				boolean show = (Boolean) tableFields.getValueAt(i, index_visible);
				boolean returnFlg = (Boolean) tableFields.getValueAt(i, index_return);
				if (!returnFlg && !show) {
					continue;
				}
				String formStr = (String) tableFields.getValueAt(i, index_field);
				if (returnFlg && CommonUtils.isStrEmpty(formStr)) {
					GUIUtils.showMsg(dialog, "若返回字段，则对应表单项目不能为空！");
					tableFields.setRowSelectionInterval(i, i);
					return;
				}
				String cname = (String) tableFields.getValueAt(i, index_label);
				String name = (String) tableFields.getValueAt(i, index_name);
				int width = (Integer) tableFields.getValueAt(i, index_width);
				String lab = (String) tableFields.getValueAt(i, index_newlabel);
				String showStr = "no";
				String returnSTr = "no";
				if (returnFlg) {
					returnSTr = "yes";
					outPutParams += formStr + "=" + name + ":" + cname + ",";
				}
				if (show) {
					showStr = "yes";
				}
				fields.append(cname).append("\\b").append(name).append("\\b").append(width).append("\\b").append(showStr).append("\\b").append(returnSTr).append("\\b").append(formStr).append("\\b").append(lab).append(",");
			}
			if (fields.length() > 0) {
				fields.deleteCharAt(fields.length() - 1);
			}
			if (outPutParams.endsWith(",")) {
				outPutParams = outPutParams.substring(0, outPutParams.length() - 1);
			}
			windowparam += "||field:" + fields;
			String othcondi = jTextArea4.getText();
			if (!StringUtils.nullOrBlank(othcondi)) {
				othcondi = new String(CommonUtils.base64Encode(othcondi.getBytes()));
			}
			StringBuffer params = new StringBuffer();
			params.append("serviceName=" + servicename + ";").append("tableName=" + tablename + ";").append("DataParam=" + dataParam + ";").append("isMultiPage=" + ismultipage + ";").append("selectType=" + selecttype + ";").append("getDataTime=" + getdataTime + ";")
					.append("isBottom=" + isbottom + ";").append("showType=" + showtype + ";").append("expendAll=" + expendall + ";").append("showCode=" + showcode + ";").append("needCorp=" + needcorp + ";").append("condiPanel=" + condipanel + ";").append("buildTreeField=" + buildtreefield + ";")
					.append("windowparam=" + windowparam + ";").append("othcondi=" + othcondi);
			props.put("inparam", params.toString());
			props.put("outparam", outPutParams);
			submit = true;
			this.dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}

	}

	private void init() {
		tableFields = new JTable();
		jScrollPane2.setViewportView(tableFields);
		DefaultListModel fieldModel = new DefaultListModel();
		for (XMLDto f : CompUtils.getFields()) {
			fieldModel.addElement(f);
		}
		jList30.setModel(fieldModel);

		// table
		tableModel = new DefaultTableModel(title, 0) {
			private static final long serialVersionUID = 1L;
			private Class<?>[] clazzes = new Class<?>[] { String.class, String.class, Integer.class, Boolean.class, Boolean.class, String.class, String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return clazzes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				Boolean choose = true;
				if (index_name == column || index_field == column && !choose.equals(tableFields.getValueAt(row, index_return))) {
					return false;
				}
				if ((index_width == column || index_newlabel == column || index_label == column) && !choose.equals(tableFields.getValueAt(row, index_visible))) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		};
		tableFields.setModel(tableModel);
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(tableFields);
				int row = tableFields.getSelectedRow();
				int column = tableFields.getSelectedColumn();
				String itemname = (String) tableModel.getValueAt(row, column);
				XMLDto value = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", itemname);
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("wdith", "500");
				pnl.edit(dialog, temp);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					if (value != null) {
						tableModel.setValueAt(value.getValue("itemname"), row, column);
					} else {
						tableModel.setValueAt("", row, column);
					}
				}
				CompUtils.stopTabelCellEditor(tableFields);
			}
		}, true);
		TableColumnModel cm = tableFields.getColumnModel();
		cm.getColumn(index_field).setCellRenderer(editor.getTableCellRenderer());
		cm.getColumn(index_field).setCellEditor(editor);
		tableFields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFields.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = tableFields.rowAtPoint(e.getPoint());
				int col = tableFields.columnAtPoint(e.getPoint());
				Boolean choose = true;
				if (col == index_visible) {
					if (choose.equals(tableFields.getValueAt(row, index_visible))) {
						tableFields.setValueAt(80, row, index_width);
					} else {
						tableFields.setValueAt(0, row, index_width);

					}
				}
				if (col == index_return) {
					if (!choose.equals(tableFields.getValueAt(row, index_return))) {
						tableFields.setValueAt("", row, index_field);

					}
				}
				for (int i = 0; i < tableFields.getRowCount(); i++) {
					if (choose.equals(tableFields.getValueAt(i, index_return)) && CommonUtils.isStrEmpty((String) tableFields.getValueAt(i, index_field)) && row != i) {
						tableFields.setRowSelectionInterval(i, i);
						GUIUtils.showMsg(dialog, "请选择[对应的表单项目]");
						Class<?> clazz = tableModel.getColumnClass(col);
						if (clazz.equals(Boolean.class)) {
							tableModel.setValueAt(!(Boolean) tableModel.getValueAt(row, col), row, col);
						}
						if (tableFields.isEditing()) {
							tableFields.getCellEditor().stopCellEditing();
						}
						return;
					}
				}
			}
		});
		tableFieldModel = new DefaultListModel();
		jList29.setModel(tableFieldModel);
		jList29.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList29.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					XMLDto dto = (XMLDto) jList29.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea4, " " + dto.getValue("name"));
				}
			}
		});
		initCmp();
	}

	private void initCmp() {
		// 把界面的所有控件元素存放到cmpMap
		// ButtonGroup不存放在界上，特殊处理
		cmpMap.put("condiPanel", condiPanel);
		cmpMap.put("getDataTime", getDataTime);
		cmpMap.put("isBottom", isBottom);
		cmpMap.put("needCorp", needCorp);
		cmpMap.put("selectType", selectType);
		cmpMap.put("showType", showType);
		cmpMap.put("DataParam", DataParam);
		cmpMap.put("isMultiPage", checkBox);
		cmpMap.put("showCode", checkBox_2);
		cmpMap.put("expendAll", checkBox_1);
		getCmpChildren(this);
	}

	private void getCmpChildren(JComponent parent) {
		if (parent.getComponentCount() > 0) {
			Component[] children = parent.getComponents();
			for (int i = 0; i < children.length; i++) {
				Component component = children[i];
				if (component instanceof JPanel) {
					getCmpChildren((JComponent) component);
				} else {
					String cmpName = component.getName();
					if (!cmpMap.containsKey(cmpName)) {
						cmpMap.put(cmpName, component);
					}
				}
			}
		}
	}

	private void selectTable() {
		if (tables == null || tables.isEmpty()) {
			GUIUtils.showMsg(dialog, "请获取表信息");
			return;
		}
		XMLDto temp = table;
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(tables);
		pnl.setValue(table);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		table = pnl.getSelect();
		if (table != null && table.equals(temp)) {
			return;
		}
		initByTable();
	}

	private void initByTable() {
		tableModel.setRowCount(0);
		tableFieldModel.clear();
		jTextArea4.setText("");
		if (table == null) {
			textField.setText("");
			return;
		}
		try {
			textField.setText(table.toString());
			List<Element> fieldEles = table.getObject(BackedList.class, "fields");
			if (fieldEles == null || fieldEles.isEmpty()) {
				return;
			}
			fields = CommonUtils.coverEles(fieldEles, new String[] { "cname", "name", "fieldType" }, null, new String[] { "cname", "name" }, null, null, null);
			if (fields == null && fields.isEmpty()) {
				return;
			}
			for (XMLDto dto : fields) {
				tableModel.addRow(new Object[] { dto.getValue("cname"), dto.getValue("name"), 0, false, false, "", "" });
				tableFieldModel.addElement(dto);
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化表格失败");
			return;
		}
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "通用单表选择设置界面", this);
		dialog.setVisible(true);

	}

	// 设置组件的值
	private void setCmpVallue(String cmpName, String value) {
		if (cmpMap.containsKey(cmpName)) {
			Object obj = cmpMap.get(cmpName);
			if (obj instanceof JTextField) {
				((JTextField) obj).setText(value);
			} else if (obj instanceof ButtonGroup) {
				int index = 0;
				Enumeration<AbstractButton> buttons = ((ButtonGroup) obj).getElements();
				while (buttons.hasMoreElements()) {
					AbstractButton radioButton = buttons.nextElement();
					if (index == Integer.valueOf(value)) {
						radioButton.setSelected(true);
						break;
					}
					index++;
				}
			} else if (obj instanceof JSpinner) {
				((JSpinner) obj).getModel().setValue(Integer.valueOf(value));
			} else if (obj instanceof JTextArea) {
				((JTextArea) obj).setText(value);
			} else if (obj instanceof JCheckBox) {
				((JCheckBox) obj).setSelected("0".equals(value));
			}
		}
	}

	private void initData() {
		// 初始化入参数
		String inparam = props.get("inparam");
		if (CommonUtils.isStrEmpty(inparam)) {
			return;
		}
		try {
			String[] params = inparam.split(";");
			String oldReturnFields = "", tableName = "", othcondi = "";
			for (int i = 0; i < params.length; i++) {
				String keyAndValueStr = params[i];
				String value = "";
				if (keyAndValueStr.startsWith("tableName")) {
					value = keyAndValueStr.substring("tableName".length() + 1);
					if (StringUtils.nullOrBlank(value)) {
						continue;
					}
					tableName = value;
				} else if (keyAndValueStr.startsWith("windowparam")) {
					value = keyAndValueStr.substring("windowparam".length() + 1);
					if (StringUtils.nullOrBlank(value)) {
						continue;
					}
					// 初化窗口参数
					String[] windowparamsArray = value.split("\\|\\|");
					for (int j = 0; j < windowparamsArray.length; j++) {
						String windowparam = windowparamsArray[j];
						String[] windowparamKeyAndValue = windowparam.split(":");
						if (windowparamKeyAndValue.length > 1) {
							String windowparamKey = windowparamKeyAndValue[0];
							String windowparamValue = windowparamKeyAndValue[1];
							if ("field".equals(windowparamKey)) {
								// 初始化字段设置表格数据
								oldReturnFields = windowparamValue;
							} else {
								setCmpVallue(windowparamKey, windowparamValue);
							}
						}
					}
				} else if (keyAndValueStr.startsWith("othcondi")) {
					value = keyAndValueStr.substring("othcondi".length() + 1);
					if (StringUtils.nullOrBlank(value)) {
						continue;
					}
					othcondi = new String(CommonUtils.base64Dcode(value));
					jTextArea4.setText(value);
				} else {
					value = keyAndValueStr.substring(keyAndValueStr.indexOf("=") + 1);
					String key = keyAndValueStr.substring(0, keyAndValueStr.indexOf("="));
					setCmpVallue(key, value);
				}
			}
			if (CommonUtils.isStrEmpty(serviceName.getText().trim())) {
				return;
			}
			oldServiceName = serviceName.getText().trim();
			tables = CommonUtils.getTableByService(oldServiceName);
			table = CommonUtils.getXmlDto(tables, "name", tableName);
			if (table == null) {
				return;
			}
			initByTable();
			if (CommonUtils.isStrEmpty(oldReturnFields)) {
				return;
			}
			if (!CommonUtils.isStrEmpty(othcondi)) {
				jTextArea4.setText(othcondi);
			}
			if (CommonUtils.isStrEmpty(oldReturnFields)) {
				return;
			}
			String[] fieldStr = oldReturnFields.split(",");
			if (fieldStr.length <= 0) {
				return;
			}
			for (String field1 : fieldStr) {
				if (CommonUtils.isStrEmpty(field1)) {
					continue;
				}
				String[] field2 = field1.split("\\\\b");
				String fieldname = field2[1];
				XMLDto fieldItem = CommonUtils.getXmlDto(fields, "name", fieldname);
				int index = fields.indexOf(fieldItem);
				if (index < 0) {
					continue;
				}
				if ("yes".equals(field2[3])) {
					tableFields.setValueAt(true, index, index_visible);
					tableFields.setValueAt(field2[0], index, index_label);
					String temp = field2[2];
					int width = 80;
					if (CommonUtils.isNumberString(temp)) {
						width = Integer.parseInt(temp);
					}
					tableFields.setValueAt(width, index, index_width);
					if (field2.length == 7) {
						tableFields.setValueAt(field2[6], index, index_newlabel);
					}
				}
				if ("yes".equals(field2[4])) {
					tableFields.setValueAt(true, index, index_return);
					if (!CommonUtils.isStrEmpty(field2[5])) {
						String[] field3 = field2[5].split("\\\\c");
						tableFields.setValueAt(field3[0], index, index_field);
					}
				}
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
