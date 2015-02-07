package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.main.MainFrame;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.Do4objs;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;
import youngfriend.utils.PropUtils;

public class BatchUpdateComPropPnl extends JPanel {
	private static final Logger logger = LogManager.getLogger(BatchUpdateComPropPnl.class.getName());
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private List<XMLDto> types = new ArrayList<XMLDto>();
	private JDialog dialog;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private Map<ComEum, Collection<PropDto>> propMap = new HashMap<ComEum, Collection<PropDto>>();
	private JComboBox comboBox_3;
	private JComboBox comboBox_2;
	private String catalogid;
	private JTextArea textArea;
	private JTextField textField_3;
	private JComboBox comboBox_4;
	private JComboBox comboBox_5;
	private JTabbedPane tabbedPane;
	private String result;

	public BatchUpdateComPropPnl(String catalogid, String catalogName) {
		this.setPreferredSize(new Dimension(938, 420));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}

		});
		panel.add(button);
		JButton button_1 = new JButton("\u5173\u95ED");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(button_1);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new TitledBorder(null, "更新目录为" + catalogName, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tabbedPane, BorderLayout.NORTH);
		JPanel panel_0 = new JPanel();
		tabbedPane.addTab("\u6279\u91CF\u8BBE\u7F6E", null, panel_0, null);
		panel_0.setLayout(null);

		JLabel label_5 = new JLabel("\u63A7\u4EF6\u7C7B\u578B");
		label_5.setBounds(17, 24, 61, 16);
		panel_0.add(label_5);

		comboBox_4 = new JComboBox();
		comboBox_4.setBounds(85, 19, 234, 27);
		panel_0.add(comboBox_4);

		comboBox_5 = new JComboBox();
		comboBox_5.setBounds(85, 61, 234, 27);
		panel_0.add(comboBox_5);

		JLabel label_7 = new JLabel("\u66F4\u6539\u5C5E\u6027");
		label_7.setBounds(17, 65, 61, 16);
		panel_0.add(label_7);

		JLabel label_8 = new JLabel("\u66F4\u6539\u503C");
		label_8.setBounds(17, 101, 53, 16);
		panel_0.add(label_8);

		textField_3 = new JTextField();
		textField_3.setBounds(85, 95, 372, 28);
		panel_0.add(textField_3);
		textField_3.setColumns(10);
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\u9AD8\u7EA7\u6279\u91CF\u8BBE\u7F6E", null, panel_1, null);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setPreferredSize(new Dimension(0, 120));
		panel_1.setLayout(null);

		JLabel label = new JLabel("\u63A7\u4EF6\u7C7B\u578B");
		label.setBounds(6, 34, 61, 16);
		panel_1.add(label);

		comboBox = new JComboBox();
		comboBox.setBounds(64, 29, 164, 27);
		panel_1.add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(271, 29, 123, 27);
		panel_1.add(comboBox_1);

		JLabel label_1 = new JLabel("\u7684");
		label_1.setBounds(240, 34, 19, 16);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("\u5C5E\u6027\u6EE1\u8DB3");
		label_2.setBounds(406, 34, 61, 16);
		panel_1.add(label_2);

		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(461, 29, 101, 27);
		panel_1.add(comboBox_2);

		textField = new JTextField();
		textField.setBounds(585, 28, 211, 28);
		panel_1.add(textField);
		textField.setColumns(10);

		JLabel label_3 = new JLabel("\u5C06\u5176\u5C5E\u6027");
		label_3.setBounds(6, 92, 61, 16);
		panel_1.add(label_3);

		comboBox_3 = new JComboBox();
		comboBox_3.setBounds(79, 88, 144, 27);
		panel_1.add(comboBox_3);

		JLabel label_4 = new JLabel("\u5305\u542B\u7684");
		label_4.setBounds(235, 92, 48, 16);
		panel_1.add(label_4);

		textField_1 = new JTextField();
		textField_1.setBounds(281, 86, 180, 28);
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblTihuancheng = new JLabel("\u4FEE\u6539\u4E3A");
		lblTihuancheng.setBounds(473, 92, 61, 16);
		panel_1.add(lblTihuancheng);

		textField_2 = new JTextField();
		textField_2.setBounds(519, 86, 211, 28);
		panel_1.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel = new JLabel("\u5305\u542B\u503C\u4E3A\u7A7A\uFF0C\u5219\u5C5E\u6027\u503C\u4FEE\u6539\u4E3A\u4FEE\u6539\u503C");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(64, 119, 498, 16);
		panel_1.add(lblNewLabel);

		JLabel label_6 = new JLabel("\u67E5\u627E\u63A7\u4EF6\u8FC7\u6EE4:");
		label_6.setBounds(6, 6, 101, 16);
		panel_1.add(label_6);

		JLabel label_9 = new JLabel("\u503C\u4FEE\u6539:");
		label_9.setBounds(6, 71, 61, 16);
		panel_1.add(label_9);
		this.catalogid = catalogid;
		init();
		dialog = GUIUtils.getDialog(MainFrame.getInstance(), "批量修改控件属性", this);
		dialog.setVisible(true);
	}

	private void save() {
		result = "";
		if (!GUIUtils.showConfirm(dialog, "确定修改吗？样式设置将修改")) {
			return;

		}
		if (!GUIUtils.showConfirm(dialog, "确定修改吗？样式设置将修改")) {
			return;
		}
		MainFrame.getInstance().busyDoing(new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				try {
					String setPropName = "";
					String regex = "";
					String replaceTxt = "";
					String matchTxt = "";

					XMLDto temp = null;
					PropDto prop = null;
					String comType = "";
					switch (tabbedPane.getSelectedIndex()) {
					case 0:
						temp = (XMLDto) comboBox_4.getSelectedItem();
						if (temp == null) {
							GUIUtils.showMsg(dialog, "存在空设置");
							return;
						}
						comType = temp.getValue("value").toLowerCase();
						matchTxt = "//*[translate(@classname,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + comType + "']";
						prop = (PropDto) comboBox_5.getSelectedItem();
						if (prop == null) {
							GUIUtils.showMsg(dialog, "设置属性不能为空");
							return;
						}
						setPropName = prop.getPropname();
						replaceTxt = textField_3.getText();
						break;
					case 1:
						temp = (XMLDto) comboBox.getSelectedItem();
						if (temp == null) {
							GUIUtils.showMsg(dialog, "存在空设置");
							return;
						}
						comType = temp.getValue("value").toLowerCase();
						temp = (XMLDto) comboBox_2.getSelectedItem();
						if (temp == null) {
							GUIUtils.showMsg(dialog, "存在空设置");
							return;
						}
						String serachValue = textField.getText().trim();
						if (CommonUtils.isStrEmpty(serachValue, true)) {
							serachValue = "''";
						}
						prop = (PropDto) comboBox_1.getSelectedItem();
						if (prop == null) {
							GUIUtils.showMsg(dialog, "匹配属性不能为空");
							return;
						}
						String operMatch = "";
						switch (comboBox_2.getSelectedIndex()) {
						case 0:
							// 等于
							operMatch = prop.getPropname() + temp.getValue("value") + serachValue;
							break;
						case 1:
							operMatch = temp.getValue("value") + "(" + prop.getPropname() + ",'" + serachValue + "')";
							break;
						default:
							break;
						}

						matchTxt = "//*[translate(@classname,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + comType + "' and property[" + operMatch + "] ]";

						prop = (PropDto) comboBox_3.getSelectedItem();
						if (prop == null) {
							GUIUtils.showMsg(dialog, "设置属性不能为空");
							return;
						}
						setPropName = prop.getPropname();
						regex = textField_1.getText();
						replaceTxt = textField_2.getText();
						break;
					default:
						break;
					}
					result = InvokerServiceUtils.updateComPropinCatalog(catalogid, setPropName, regex, replaceTxt, matchTxt);
				} catch (Exception e) {
					GUIUtils.showMsg(dialog, "更新失败");
					logger.error(e.getMessage(), e);
				}

			}
		}, new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				textArea.append(result);
				textArea.append("\n");

			}
		}, true);

	}

	private void init() {
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateProp();
			}
		});

		ComEum[] coms = ComEum.values();
		for (ComEum c : coms) {
			if (c == ComEum.TNewColumn) {
				continue;
			}
			types.add(new XMLDto(c.getCName(), c.toString()));
		}

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}
				updateProp();
			}
		});
		comboBox_4.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}
				updateProp();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(types.toArray()));
		comboBox_4.setModel(new DefaultComboBoxModel(types.toArray()));
		updateProp();
		XMLDto[] opers = new XMLDto[] { new XMLDto("等于", "="), new XMLDto("包含有文本", "contains") };
		comboBox_2.setModel(new DefaultComboBoxModel(opers));
	}

	private void updateProp() {
		XMLDto dto = null;
		ComEum type = null;
		Collection<PropDto> props = null;
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			dto = (XMLDto) comboBox_4.getSelectedItem();
			type = ComEum.getTypeByStr(dto.getValue("value"));
			if (type == null) {
				DefaultComboBoxModel m1 = (DefaultComboBoxModel) comboBox_5.getModel();
				m1.removeAllElements();
				return;
			}
			if (!propMap.containsKey(type)) {
				propMap.put(type, PropUtils.initPropDto(type.getPropList(), null, null).values());
			}
			props = propMap.get(type);
			comboBox_5.setModel(new DefaultComboBoxModel(props.toArray()));
			break;
		case 1:
			dto = (XMLDto) comboBox.getSelectedItem();
			type = ComEum.getTypeByStr(dto.getValue("value"));
			if (type == null) {
				DefaultComboBoxModel m1 = (DefaultComboBoxModel) comboBox_1.getModel();
				m1.removeAllElements();
				DefaultComboBoxModel m2 = (DefaultComboBoxModel) comboBox_3.getModel();
				m2.removeAllElements();
				return;
			}
			if (!propMap.containsKey(type)) {
				propMap.put(type, PropUtils.initPropDto(type.getPropList(), null, null).values());
			}
			props = propMap.get(type);
			comboBox_1.setModel(new DefaultComboBoxModel(props.toArray()));
			comboBox_3.setModel(new DefaultComboBoxModel(props.toArray()));
			break;
		default:
			break;
		}

	}
}
