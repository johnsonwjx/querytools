/*
 * ChooseDataTable.java
 *
 * Created on 2007年8月2日, 上午11:22
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.Validate;
import youngfriend.beans.XMLDto;

import youngfriend.common.util.net.ServiceInvokerUtil;
import youngfriend.common.util.net.exception.ServiceInvokerException;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.main.MainFrame;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;
import youngfriend.utils.UUIDHexGenerator;

/**
 * 
 * @author Administrator
 */
public class PrintParamEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	public PrintParamEditor() {
		initComponents();
		init();
	}

	private DefaultListModel model;

	private void initComponents() {
		this.setPreferredSize(new Dimension(639, 426));
		buttonGroup1 = new javax.swing.ButtonGroup();
		jPanel1 = new javax.swing.JPanel();
		sysPageRadio = new javax.swing.JRadioButton();
		sysPageRadio.setBounds(22, 18, 84, 27);
		pageSizeCombo = new javax.swing.JComboBox();
		pageSizeCombo.setBounds(159, 20, 120, 27);
		cutomPageRadio = new javax.swing.JRadioButton();
		cutomPageRadio.setBounds(22, 53, 97, 23);
		jPanel4 = new javax.swing.JPanel();
		jPanel4.setBounds(153, 188, 144, 134);
		directionCombo = new javax.swing.JComboBox();
		rightLineText = new javax.swing.JTextField();
		leftLineText = new javax.swing.JTextField();
		topLineText = new javax.swing.JTextField();
		bottomLineText = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(22, 82, 274, 100);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(18, 27, 65, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(18, 61, 65, 16);
		widthText = new javax.swing.JTextField();
		widthText.setBounds(135, 21, 130, 28);
		heightText = new javax.swing.JTextField();
		heightText.setBounds(135, 55, 130, 28);
		jPanel5 = new javax.swing.JPanel();
		jPanel5.setBounds(22, 188, 119, 131);
		jLabel4 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		SaveBtn = new javax.swing.JButton();
		SaveBtn.setBounds(190, 334, 75, 29);

		jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel1.setLocation(new java.awt.Point(400, 400));
		jPanel1.setSize(new java.awt.Dimension(350, 480));

		buttonGroup1.add(sysPageRadio);
		sysPageRadio.setSelected(true);
		sysPageRadio.setText("系统纸张");

		pageSizeCombo.setMinimumSize(new java.awt.Dimension(52, 20));
		pageSizeCombo.setPreferredSize(new java.awt.Dimension(120, 20));

		buttonGroup1.add(cutomPageRadio);
		cutomPageRadio.setText("自定义纸张");

		jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		directionCombo.setPreferredSize(new java.awt.Dimension(120, 20));
		directionCombo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				directChange();
			}
		});
		jPanel4.add(directionCombo);

		rightLineText.setText("0");
		rightLineText.setPreferredSize(new java.awt.Dimension(120, 20));
		jPanel4.add(rightLineText);

		leftLineText.setText("0");
		leftLineText.setPreferredSize(new java.awt.Dimension(120, 20));
		jPanel4.add(leftLineText);

		topLineText.setText("6.5");
		topLineText.setPreferredSize(new java.awt.Dimension(120, 20));
		jPanel4.add(topLineText);

		bottomLineText.setText("0");
		bottomLineText.setPreferredSize(new java.awt.Dimension(120, 20));
		jPanel4.add(bottomLineText);

		jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jPanel2.setEnabled(false);

		jLabel2.setText("宽（毫米）");

		jLabel3.setText("高（毫米）");

		widthText.setEnabled(false);

		heightText.setEnabled(false);

		jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

		jLabel4.setText("纸张方向");
		jPanel5.add(jLabel4);

		jLabel7.setText("左边距（毫米）");
		jPanel5.add(jLabel7);

		jLabel5.setText("上边距（毫米）");
		jPanel5.add(jLabel5);

		jLabel6.setText("下边距（毫米）");
		jPanel5.add(jLabel6);

		jLabel8.setText("右边距（毫米）");
		jPanel5.add(jLabel8);

		SaveBtn.setText("保存");
		SaveBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveParam();
			}
		});
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setPreferredSize(new Dimension(300, 0));
		panel.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);

		button_2 = new JButton("\u6DFB\u52A0");
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}

		});
		panel_2.add(button_2);

		button_3 = new JButton("\u5220\u9664");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delItem();
			}

		});
		panel_2.add(button_3);
		jScrollPane2 = new javax.swing.JScrollPane();
		panel.add(jScrollPane2, BorderLayout.CENTER);
		model = new DefaultListModel();
		itemsList = new JList(model);
		itemsList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if (lse.getValueIsAdjusting()) {
					return;
				}
				PrintConfigBean select = (PrintConfigBean) itemsList.getSelectedValue();
				if (select == null) {
					return;
				}
				if ("custom".equals(select.getPaperType())) {
					cutomPageRadio.setSelected(true);
					widthText.setText(select.getPaperWidth());
					heightText.setText(select.getPaperWidth());
				} else {
					sysPageRadio.setSelected(true);
					pageSizeCombo.setSelectedItem(select.getPageSize());
				}
				directionCombo.setSelectedIndex(Integer.parseInt(select.getDirection()));
				topLineText.setText(select.getTopMargin().length() <= 0 ? "0" : select.getTopMargin());
				bottomLineText.setText(select.getBottomMargin().length() <= 0 ? "0" : select.getBottomMargin());
				leftLineText.setText(select.getLeftMargin().length() <= 0 ? "0" : select.getLeftMargin());
				rightLineText.setText(select.getRightMargin().length() <= 0 ? "0" : select.getRightMargin());
			}
		});
		jScrollPane2.setViewportView(itemsList);
		add(panel, BorderLayout.WEST);
		add(jPanel1);
		jPanel1.setLayout(null);
		jPanel1.add(cutomPageRadio);
		jPanel1.add(sysPageRadio);
		jPanel1.add(pageSizeCombo);
		jPanel1.add(jPanel2);
		jPanel2.setLayout(null);
		jPanel2.add(jLabel2);
		jPanel2.add(jLabel3);
		jPanel2.add(widthText);
		jPanel2.add(heightText);
		jPanel1.add(SaveBtn);
		jPanel1.add(jPanel5);
		jPanel1.add(jPanel4);

		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);

		button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}
		});
		panel_1.add(button);

		button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel_1.add(button_1);
	}

	public void init() {
		pageSizeCombo.removeAllItems();
		String[] selectItems = new String[] { "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10",//
				"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10" };
		for (String item : selectItems) {
			pageSizeCombo.addItem(item);
		}
		pageSizeCombo.setSelectedIndex(3);
		directionCombo.removeAllItems();
		directionCombo.addItem("纵向");
		directionCombo.addItem("横向");
		sysPageRadio.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				if (sysPageRadio.isSelected()) {
					pageSizeCombo.setEnabled(true);
					widthText.setEnabled(false);
					heightText.setEnabled(false);
					jLabel2.setEnabled(false);
					jLabel3.setEnabled(false);
				} else {
					pageSizeCombo.setEnabled(false);
					widthText.setEnabled(true);
					heightText.setEnabled(true);
					jLabel2.setEnabled(true);
					jLabel3.setEnabled(true);
				}
			}
		});
	}

	private javax.swing.JButton SaveBtn;
	private javax.swing.JTextField bottomLineText;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JRadioButton cutomPageRadio;
	private javax.swing.JComboBox directionCombo;
	private javax.swing.JTextField heightText;
	private javax.swing.JList itemsList;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextField leftLineText;
	private javax.swing.JComboBox pageSizeCombo;
	private javax.swing.JTextField rightLineText;
	private javax.swing.JRadioButton sysPageRadio;
	private javax.swing.JTextField topLineText;
	private javax.swing.JTextField widthText;
	private JPanel panel_1;
	private JButton button;
	private JButton button_1;
	private JPanel panel_2;
	private JButton button_2;
	private JButton button_3;
	private DefaultPropEditor defaultpropeditor;
	private TreeSelectPnl<XMLDto> pnl;

	private class PrintConfigBean {

		private String configId;
		private String styleId;
		private String styleName;
		private String pageSize;
		private String paperType;
		private String paperWidth;
		private String paperHeight;
		private String direction;
		private String leftMargin;
		private String topMargin;
		private String bottomMargin;
		private String rightMargin;

		public PrintConfigBean() {
			configId = "";
			styleId = "";
			styleName = "";
			paperType = "A4";
			pageSize = "";
			paperWidth = "";
			paperHeight = "";
			direction = "0";
			leftMargin = "";
			topMargin = "";
			bottomMargin = "";
			rightMargin = "";
		}

		public String getBottomMargin() {
			return bottomMargin;
		}

		public void setBottomMargin(String bottomMargin) {
			this.bottomMargin = bottomMargin;
		}

		public String getConfigId() {
			return configId;
		}

		public void setConfigId(String configId) {
			this.configId = configId;
		}

		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

		public String getLeftMargin() {
			return leftMargin;
		}

		public void setLeftMargin(String leftMargin) {
			this.leftMargin = leftMargin;
		}

		public String getPageSize() {
			return pageSize;
		}

		public void setPageSize(String pageSize) {
			this.pageSize = pageSize;
		}

		public String getPaperHeight() {
			return paperHeight;
		}

		public void setPaperHeight(String paperHeight) {
			this.paperHeight = paperHeight;
		}

		public String getPaperType() {
			return paperType;
		}

		public void setPaperType(String paperType) {
			this.paperType = paperType;
		}

		public String getPaperWidth() {
			return paperWidth;
		}

		public void setPaperWidth(String paperWidth) {
			this.paperWidth = paperWidth;
		}

		public String getRightMargin() {
			return rightMargin;
		}

		public void setRightMargin(String rightMargin) {
			this.rightMargin = rightMargin;
		}

		public String getStyleId() {
			return styleId;
		}

		public void setStyleId(String styleId) {
			this.styleId = styleId;
		}

		public String getStyleName() {
			return styleName;
		}

		public void setStyleName(String styleName) {
			this.styleName = styleName;
		}

		public String getTopMargin() {
			return topMargin;
		}

		public void setTopMargin(String topMargin) {
			this.topMargin = topMargin;
		}

		public String getParamStr() {
			return "\"configId\":\"" + configId + "\";\"styleId\":\"" + styleId + "\";\"styleName\":\"" + styleName + "\";\"pageSize\":\"" //
					+ pageSize + "\";\"paperType\":\"" + paperType + "\";\"paperWidth\":\"" + paperWidth + "\";\"paperHeight\":\"" + paperHeight + "\";"//
					+ "\"direction\":\"" + direction + "\";\"leftMargin\":\"" + leftMargin + "\";\"topMargin\":\"" + topMargin + "\";\"bottomMargin\":\"" + //
					bottomMargin + "\";\"rightMargin\":\"" + rightMargin + "\"";
		}

		@Override
		public String toString() {
			return getStyleName();
		}
	}

	private void delItem() {
		int index = itemsList.getSelectedIndex();
		if (index < 0) {
			return;
		}
		model.removeElementAt(index);

	}

	private void addItem() {
		if (pnl == null) {
			JTree tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {
				@Override
				public String validate(XMLDto obj) {
					String dataType = obj.getValue("dataType");
					if ("querystylecatalog".equals(dataType)) {
						return "remove";
					}
					return null;
				}
			}, "打印样式", true);
			pnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {

				@Override
				public String validate(XMLDto obj) {
					return "printstyle".equalsIgnoreCase(obj.getValue("dataType")) ? null : "请选择打印样式";
				}
			});
		}
		Map<String, String> prop = new HashMap<String, String>();
		pnl.edit(defaultpropeditor.getDialog(), prop);
		if (!pnl.isChange()) {
			return;
		}
		XMLDto dto = pnl.getSelect();
		PrintConfigBean bean = new PrintConfigBean();
		bean.setConfigId(UUIDHexGenerator.generator());
		bean.setStyleId(dto.getValue("styleid"));
		bean.setStyleName(dto.getValue("stylename"));
		model.addElement(bean);

	}

	private List<PrintConfigBean> getByStr(String str) throws Exception {
		List<PrintConfigBean> result = new ArrayList<PrintConfigBean>();
		if (str == null || str.length() <= 0) {
			return result;
		}
		str = str.replaceAll("\"", "");
		String[] sources = str.split("configId:");
		for (int i = 1; sources.length >= 1 && i < sources.length; i++) {
			String[] temps = sources[i].split(";");
			PrintConfigBean bean = new PrintConfigBean();
			for (int j = 0; j < temps.length; j++) {
				String[] pro = temps[j].split(":");
				if (j == 0) {
					bean.setConfigId(pro[0]);
					continue;
				}
				String proName = pro[0];
				String proValue = pro.length > 1 ? pro[1] : "";

				if ("styleId".equals(proName)) {
					bean.setStyleId(proValue);
				} else if ("styleName".equals(proName)) {
					bean.setStyleName(proValue);
				} else if ("paperType".equals(proName)) {
					bean.setPaperType(proValue);
				} else if ("paperWidth".equals(proName)) {
					bean.setPaperWidth(proValue);
				} else if ("paperHeight".equals(proName)) {
					bean.setPaperHeight(proValue);
				} else if ("direction".equals(proName)) {
					bean.setDirection(proValue);
				} else if ("leftMargin".equals(proName)) {
					bean.setLeftMargin(proValue);
				} else if ("topMargin".equals(proName)) {
					bean.setTopMargin(proValue);
				} else if ("bottomMargin".equals(proName)) {
					bean.setBottomMargin(proValue);
				} else if ("rightMargin".equals(proName)) {
					bean.setRightMargin(proValue);
				}
			}
			try {
				String styleXml = InvokerServiceUtils.getStyledataXml(bean.getStyleId());
				Document doc = DocumentHelper.parseText(styleXml);
				Element pageNode = (Element) doc.selectSingleNode("//pageLine");
				if (pageNode == null) {
					bean.setPageSize("");
				} else {
					bean.setPageSize(pageNode.getText());
				}
			} catch (Exception e) {
				GUIUtils.showMsg(defaultpropeditor.getDialog(), "找不到样式id为:" + bean.getStyleId() + ",名称为:" + bean.getStyleName() + "的样式");
				bean.setPageSize("");
			}
			result.add(bean);
		}

		return result;
	}

	private void saveParamConfig(PrintConfigBean bean) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GBK");
		Element root = doc.addElement("root");
		Element configNode = root.addElement("config");
		configNode.addElement("name").setText("yfpagesize");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("yfpagesize");
		configNode.addElement("printvalue").setText(bean.getPageSize());

		configNode = root.addElement("config");
		configNode.addElement("name").setText("custom");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("custom");
		if (bean.getPaperType() == "custom") {
			configNode.addElement("printvalue").setText("1");
		} else {
			configNode.addElement("printvalue").setText("0");
		}

		configNode = root.addElement("config");
		configNode.addElement("name").setText("pagewidth");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("pagewidth");
		configNode.addElement("printvalue").setText(bean.getPaperWidth());

		configNode = root.addElement("config");
		configNode.addElement("name").setText("pageheight");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("pageheight");
		configNode.addElement("printvalue").setText(bean.getPaperHeight());

		configNode = root.addElement("config");
		configNode.addElement("name").setText("fx");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("fx");
		configNode.addElement("printvalue").setText(bean.getDirection());

		configNode = root.addElement("config");
		configNode.addElement("name").setText("leftmargin");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("leftmargin");
		configNode.addElement("printvalue").setText(bean.getLeftMargin());

		configNode = root.addElement("config");
		configNode.addElement("name").setText("topmargin");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("topmargin");
		configNode.addElement("printvalue").setText(bean.getTopMargin());

		configNode = root.addElement("config");
		configNode.addElement("name").setText("rightmargin");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("rightmargin");
		configNode.addElement("printvalue").setText(bean.getRightMargin());

		configNode = root.addElement("config");
		configNode.addElement("name").setText("buttommargin");
		configNode.addElement("printid").setText(bean.getConfigId());
		configNode.addElement("printkey").setText("buttommargin");
		configNode.addElement("printvalue").setText(bean.getBottomMargin());

		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "yfprintservice.config.save");
		tab.put("xml", doc.asXML());
		try {
			ServiceInvokerUtil.invoker(tab);
		} catch (ServiceInvokerException e) {
			GUIUtils.showMsg(defaultpropeditor.getDialog(), ComEum.SAVE_ERROR);
			defaultpropeditor.getLogger().error(e.getMessage(), e);
		}
		GUIUtils.showMsg(defaultpropeditor.getDialog(), "保存成功");
	}

	private void saveParam() {
		PrintConfigBean item = (PrintConfigBean) itemsList.getSelectedValue();
		if (item == null) {
			return;
		}
		item.setTopMargin(topLineText.getText());
		item.setBottomMargin(bottomLineText.getText());
		item.setLeftMargin(leftLineText.getText());
		item.setRightMargin(rightLineText.getText());
		item.setDirection(directionCombo.getSelectedIndex() + "");

		if (sysPageRadio.isSelected()) {
			item.setPaperHeight("");
			item.setPaperWidth("");
			item.setPaperType(sysPageRadio.getText());
			item.setPageSize(pageSizeCombo.getSelectedItem().toString());
		} else {
			item.setPaperType("custom");
			item.setPaperHeight(heightText.getText());
			item.setPaperWidth(widthText.getText());
		}
		saveParamConfig(item);

	}

	private void directChange() {
		if ("A4".equals(pageSizeCombo.getSelectedItem())) {
			if (directionCombo.getSelectedIndex() == 0) {
				topLineText.setText("6.5");
				bottomLineText.setText("0");
				rightLineText.setText("0");
				leftLineText.setText("0");
			} else if (directionCombo.getSelectedIndex() == 1) {
				topLineText.setText("6");
				bottomLineText.setText("0");
				rightLineText.setText("0");
				leftLineText.setText("4");
			}
		}

	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				if (!model.isEmpty()) {
					StringBuilder value = new StringBuilder();
					for (int i = 0; i < model.getSize(); i++) {
						PrintConfigBean temp = (PrintConfigBean) model.getElementAt(i);
						value.append(temp.getParamStr());
					}
					prop.setValue(CommonUtils.base64Encode(value.toString().getBytes()));
				} else {
					prop.setValue("");
				}
				return true;
			}

			@Override
			public void initData() {
				List<PrintConfigBean> selects;
				try {
					selects = getByStr(new String(CommonUtils.base64Dcode(prop.getValue())));
				} catch (Exception e) {
					throw new RuntimeException(e);
				} // 设置选择面板的值
				if (selects != null && selects.size() > 0) {
					for (PrintConfigBean item : selects) {
						model.addElement(item);
					}
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();

	}
}
