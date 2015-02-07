/*
 * YfFontChooser.java
 *
 * 版权所有 (C) 2002-2005 珠海远方软件有限公司
 * 保留所有权利
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import youngfriend.beans.PropDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.MyColorFactory;

public class FontStyleEditor extends JPanel implements PropEditor {
	// 定义返回的字体

	private static final long serialVersionUID = 1L;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JComboBox comboBox;

	public FontStyleEditor() {
		initComponents();

	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(598, 444));
		setLayout(new BorderLayout(0, 0));

		jPanel1 = new javax.swing.JPanel();
		FlowLayout flowLayout = (FlowLayout) jPanel1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(jPanel1, BorderLayout.SOUTH);

		button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}

		});
		jPanel1.add(button);

		button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});

		button_3 = new JButton("\u6E05\u7A7A");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.clear();
			}
		});
		jPanel1.add(button_3);
		jPanel1.add(button_1);

		panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		pnlSet = new javax.swing.JPanel();
		panel_1.add(pnlSet, BorderLayout.CENTER);
		pnlZt = new javax.swing.JPanel();
		tfZt = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		lstZt = new javax.swing.JList();
		pnlZx = new javax.swing.JPanel();
		tfZx = new javax.swing.JTextField();
		jScrollPane2 = new javax.swing.JScrollPane();
		lstZx = new javax.swing.JList();
		pnlZh = new javax.swing.JPanel();
		tfZh = new javax.swing.JTextField();
		jScrollPane3 = new javax.swing.JScrollPane();
		lstZh = new javax.swing.JList();

		pnlSet.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		pnlSet.setLayout(new BoxLayout(pnlSet, BoxLayout.X_AXIS));

		pnlZt.setBorder(javax.swing.BorderFactory.createTitledBorder("字体"));
		pnlZt.setPreferredSize(new Dimension(180, 10));
		pnlZt.setLayout(new java.awt.BorderLayout());

		tfZt.setEditable(false);
		tfZt.setForeground(new java.awt.Color(102, 102, 102));
		pnlZt.add(tfZt, java.awt.BorderLayout.NORTH);

		jScrollPane1.setPreferredSize(new java.awt.Dimension(260, 86));

		lstZt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(lstZt);

		pnlZt.add(jScrollPane1, BorderLayout.CENTER);

		pnlSet.add(pnlZt);

		pnlZx.setBorder(javax.swing.BorderFactory.createTitledBorder("字形"));
		pnlZx.setPreferredSize(new Dimension(100, 10));
		pnlZx.setLayout(new java.awt.BorderLayout());

		tfZx.setEditable(false);
		tfZx.setForeground(new java.awt.Color(102, 102, 102));
		tfZx.setFocusable(false);
		pnlZx.add(tfZx, java.awt.BorderLayout.NORTH);

		jScrollPane2.setPreferredSize(new java.awt.Dimension(260, 86));

		lstZx.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane2.setViewportView(lstZx);

		pnlZx.add(jScrollPane2, BorderLayout.CENTER);

		pnlSet.add(pnlZx);

		pnlZh.setBorder(javax.swing.BorderFactory.createTitledBorder("字号"));
		pnlZh.setPreferredSize(new Dimension(50, 10));
		pnlZh.setLayout(new java.awt.BorderLayout());

		tfZh.setFont(new java.awt.Font("宋体", 0, 11)); // NOI18N
		pnlZh.add(tfZh, java.awt.BorderLayout.NORTH);

		jScrollPane3.setPreferredSize(new java.awt.Dimension(260, 86));

		lstZh.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane3.setViewportView(lstZh);

		pnlZh.add(jScrollPane3, BorderLayout.CENTER);

		pnlSet.add(pnlZh);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 200));
		panel_1.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(250, 80));
		panel_2.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(null);

		panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(146, 6, 206, 67);
		panel_3.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		checkBox_2 = new JCheckBox("\u5E38\u7528\u989C\u8272");
		checkBox_2.setSelected(true);
		panel_5.add(checkBox_2);

		comboBox = new JComboBox();
		panel_5.add(comboBox);

		button_2 = new JButton("\u81EA\u5B9A\u4E49\u989C\u8272");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				customColor();
			}

		});
		button_2.setEnabled(false);
		panel_5.add(button_2);

		panel_6 = new JPanel();
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setBounds(18, 5, 116, 68);
		panel_3.add(panel_6);

		checkBox = new JCheckBox("\u5220\u9664\u7EBF");
		panel_6.add(checkBox);

		checkBox_1 = new JCheckBox("\u4E0B\u5212\u7EBF");
		panel_6.add(checkBox_1);

		comboBox_1 = new JComboBox(new String[] { "CHINESE_GB2312", "西方" });
		comboBox_1.setBounds(432, 6, 139, 27);
		panel_3.add(comboBox_1);

		label = new JLabel("\u5B57\u7B26\u96C6");
		label.setBounds(374, 10, 61, 16);
		panel_3.add(label);

		panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.CENTER);
		panel_4.setBorder(new TitledBorder(null, "\u9884\u89C8", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setLayout(new BorderLayout(0, 0));

		lblHello = new JLabel("hello");
		panel_4.add(lblHello, BorderLayout.CENTER);
		init();
	}

	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JList lstZh;
	private javax.swing.JList lstZt;
	private javax.swing.JList lstZx;
	private javax.swing.JPanel pnlSet;
	private javax.swing.JPanel pnlZh;
	private javax.swing.JPanel pnlZt;
	private javax.swing.JPanel pnlZx;
	private javax.swing.JTextField tfZh;
	private javax.swing.JTextField tfZt;
	private javax.swing.JTextField tfZx;
	private JButton button;
	private JButton button_1;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_4;
	private JLabel lblHello;
	private JPanel panel_5;
	private JCheckBox checkBox_2;
	private JPanel panel_6;
	private JComboBox comboBox_1;
	private JLabel label;
	private JButton button_2;
	private JButton button_3;
	private DefaultPropEditor defaultpropeditor;

	private void init() {
		DefaultListModel ztModel = new DefaultListModel();
		String[] fn = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {
			ztModel.addElement("宋体");
		}
		for (String f : fn) {
			ztModel.addElement(f);
		}
		lstZt.setModel(ztModel);

		DefaultListModel zxModel = new DefaultListModel();
		zxModel.addElement("常规");
		zxModel.addElement("斜体");
		zxModel.addElement("粗体");
		zxModel.addElement("粗斜体");
		lstZx.setModel(zxModel);

		DefaultListModel zhModel = new DefaultListModel();
		Integer fs[] = { 3, 5, 8, 9, 10, 11, 12, 14, 16, 18, 20, 24, 26, 36, 48 };
		for (Integer z : fs) {
			zhModel.addElement(z);
		}
		lstZh.setModel(zhModel);
		lstZh.setSelectedValue(12, true);
		lstZt.setSelectedValue("宋体", true);
		lstZx.setSelectedIndex(0);

		ListSelectionListener listener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				updatePreFont();
			}
		};
		lstZt.addListSelectionListener(listener);
		lstZx.addListSelectionListener(listener);
		lstZh.addListSelectionListener(listener);
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreFont();

			}
		});
		checkBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreFont();
			}
		});

		DefaultComboBoxModel colorModel = new DefaultComboBoxModel(MyColorFactory.getInstance().getCnNames().toArray());
		comboBox.setModel(colorModel);
		comboBox.setSelectedItem("黑色");
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreFont();
			}
		});

		checkBox_2.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				button_2.setEnabled(!checkBox_2.isSelected());
				comboBox.setEnabled(checkBox_2.isSelected());
				updatePreFont();
			}
		});
		updatePreFont();

	}

	private void customColor() {
		Color color = button_2.getForeground();
		JColorChooser colorChooser = new JColorChooser();
		color = JColorChooser.showDialog(this, "选择颜色", color);
		button_2.setForeground(color);
		updatePreFont();
	}

	public void updatePreFont() {
		tfZt.setText((String) lstZt.getSelectedValue());
		tfZh.setText(lstZh.getSelectedValue().toString());
		tfZx.setText((String) lstZx.getSelectedValue());

		// 先得到指定font值
		String fName = tfZt.getText();
		int fStyle = lstZx.getSelectedIndex();
		int fSize = (Integer) lstZh.getSelectedValue();
		if (CommonUtils.isStrEmpty(fName) || fStyle == -1 || fSize <= 0) {
			return;
		}

		HashMap<TextAttribute, Object> hm = new HashMap<TextAttribute, Object>();
		if (checkBox_1.isSelected()) {
			hm.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON); // 定义是否有下划线
		}

		if (checkBox.isSelected()) {
			hm.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON); // 定义是否有shanchu
		}

		hm.put(TextAttribute.SIZE, fSize); // 定义字号
		hm.put(TextAttribute.FAMILY, fName); // 定义字体名

		if (fStyle == 1 || fStyle == 3) {
			hm.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
		}

		if (fStyle == 2 || fStyle == 3) {
			hm.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
		}

		// 生成字号为12，字体为宋体，字形带有下划线的字体
		lblHello.setForeground(getColor());
		lblHello.setFont(new Font(hm));
		lblHello.setText(tfZt.getText() + " " + tfZx.getText() + " " + tfZh.getText());
	}

	public Color getColor() {
		String colorStr = (String) comboBox.getSelectedItem();
		return checkBox_2.isSelected() ? MyColorFactory.getInstance().getColorByCnName(colorStr) : button_2.getForeground();

	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				StringBuilder fontAttr = new StringBuilder();
				StringBuilder fontStyle = new StringBuilder();
				int chartsetIndex = comboBox_1.getSelectedIndex();
				String chartset = "";
				if (chartsetIndex == 0) {
					chartset = "134";
				} else if (chartsetIndex == 1) {
					chartset = "0";
				}
				String colorStr = "";
				Color color = getColor(); 
				if (color != null) {
					colorStr = color.getRed() + "," + color.getGreen() + "," + color.getBlue();
				}
				String deiphiName = MyColorFactory.getInstance().getDelphiNameByColor(color);
				int fStyle = lstZx.getSelectedIndex();
				// 样式字符串
				if (fStyle == 2 || fStyle == 3) {
					fontStyle.append(",fsBold");
				}

				if (fStyle == 1 || fStyle == 3) {
					fontStyle.append(",fsItalic");
				}

				if (checkBox.isSelected()) {
					fontStyle.append(",fsStrikeOut");
				}

				if (checkBox_1.isSelected()) {
					fontStyle.append(",fsUnderline");
				}

				fontAttr.append("charset=").append(chartset).append(":name=").append(lstZt.getSelectedValue())//
						.append(":size=").append(lstZh.getSelectedValue()).append(":style=").append(fontStyle)//
						.append(":color=").append(deiphiName).append(":newcolor=").append(colorStr);
				prop.setValue(fontAttr.toString());
				if (prop.getCom() != null) {
					prop.getCom().upateUIByProps();
				}
				return true;
			}

			@Override
			public void initData() {
				Object[] data = CompUtils.getFontProps(prop.getValue());
				Font font = (Font) data[0];
				Color color = (Color) data[1];
				String cname = MyColorFactory.getInstance().getCnNameByColor(color);
				if (CommonUtils.isStrEmpty(cname)) {
					checkBox_2.setSelected(false);
					button_2.setForeground(color);
				} else {
					comboBox.setSelectedItem(cname);
				}
				Map<TextAttribute, ?> attr = font.getAttributes();
				boolean posture_oblique = false, weight_bold = false;

				for (TextAttribute key : attr.keySet()) {
					Object obj = attr.get(key);
					if (key.equals(TextAttribute.UNDERLINE)) {
						if (TextAttribute.UNDERLINE_ON.equals(obj)) {
							checkBox_1.setSelected(true);
						}
					}
					if (key.equals(TextAttribute.STRIKETHROUGH)) {
						if (TextAttribute.STRIKETHROUGH_ON.equals(obj)) {
							checkBox.setSelected(true);
						}
					}
					if (key.equals(TextAttribute.POSTURE)) {
						if (TextAttribute.POSTURE_OBLIQUE.equals(obj)) {
							posture_oblique = true;
						}
					}
					if (key.equals(TextAttribute.WEIGHT)) {
						if (TextAttribute.WEIGHT_BOLD.equals(obj)) {
							weight_bold = true;
						}
					}

				}
				if (posture_oblique && weight_bold) {
					lstZx.setSelectedIndex(3);
				} else if (posture_oblique) {
					lstZx.setSelectedIndex(1);
				} else if (weight_bold) {
					lstZx.setSelectedIndex(2);
				}
				int size = font.getSize() + ComEum.FONT_SIZE_GAP;
				lstZh.setSelectedValue(size, true);
				lstZt.setSelectedValue(font.getName(), true);

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
