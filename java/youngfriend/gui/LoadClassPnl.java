package youngfriend.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.BackedList;

import youngfriend.beans.XMLDto;
import youngfriend.editors.valueEditors.ReportSelectEditor;
import youngfriend.main.DsMainPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;
import youngfriend.utils.XMLUtils;

public class LoadClassPnl extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private int select = 2;
	private JDialog dialog = null;
	private DsMainPnl dsp;
	private JTextField textField_1;
	private JButton button_1;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton;
	private List<Element> items;
	private static final Logger logger = LogManager.getLogger(LoadClassPnl.class.getName());
	private ReportSelectEditor reportEditor = null;

	class TableDto {
		private int index;
		private String name;
		private String description;

		public TableDto(int index, String name, String description) {
			this.index = index;
			this.name = name;
			this.description = description;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return description + "--" + name;
		}
	}

	private void loadTables(Document doc) {
		try {
			items = null;
			textField_1.setText("");
			List<Element> tables = doc.selectNodes("//Table");
			if (tables.size() < 0) {
				JOptionPane.showMessageDialog(dialog, "读不到表格");
			}
			List<XMLDto> dtos = CommonUtils.coverEles(tables, new String[] { "TABLE_NAME", "TABLE_DESC" }, null, new String[] { "TABLE_NAME", "TABLE_DESC" }, null, null, null);
			ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(dtos);
			pnl.edit(dialog, null);
			if (!pnl.isChange()) {
				return;
			}
			XMLDto value = pnl.getSelect();
			if (value != null) {
				int index = dtos.indexOf(value);
				Element ele = tables.get(index);
				items = ele.elements("Field");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "错误");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	private void sumbit() {
		try {
			File file = null;
			SAXReader reader = new SAXReader();
			Document doc = null;
			Element root = null;
			XMLDto dto;
			List<XMLDto> itemList;
			HashMap<String, Object> defaults;
			HashMap<String, String> xpaths;
			JFileChooser jc = XMLUtils.getFileChooser();
			Map<String, String> switchPros = new HashMap<String, String>();
			switchPros.put("name", "itemname");
			switchPros.put("cname", "itemlabel");
			switchPros.put("fieldType", "itemtype");
			defaults = new HashMap<String, Object>();
			defaults.put("itemdescription", "");
			defaults.put("id", "");
			jc.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));
			switch (select) {
			case 1:
				break;
			case 2:
				jc.showOpenDialog(dialog);
				file = jc.getSelectedFile();
				if (file == null) {
					return;
				}
				reader.setEncoding(XMLUtils.getXmlEncoding(file));
				doc = reader.read(file);
				root = doc.getRootElement();
				dto = new XMLDto("name");
				dto.initProps(root.element("classes"), Arrays.asList(new String[] { "id", "name", "alias", "catalogid", "description", "version" }));
				dsp.initTextField(dto.getValue("name"), dto.getValue("alias"), dto.getValue("description"));
				itemList = CommonUtils.coverEles(root.selectNodes("//classitem"), new String[] { "id", "itemno", "itemname", //
						"itemlabel", "itemtype", "itemdescription", "classid" }, null, new String[] { "itemname" }, null, null, null);
				dsp.initItemdata(itemList, false, true);
				JOptionPane.showMessageDialog(dialog, "导入成功");
				break;
			case 3:
				jc.showOpenDialog(dialog);
				file = jc.getSelectedFile();
				if (file == null) {
					return;
				}
				reader.setEncoding(XMLUtils.getXmlEncoding(file));
				doc = reader.read(file);
				loadTables(doc);
				if (items != null) {
					xpaths = new HashMap<String, String>();
					xpaths.put("itemname", "Para[@name='FIELD_NAME']");
					xpaths.put("itemlabel", "Para[@name='FIELD_DESC']");
					xpaths.put("itemtype", "Para[@name='FIELD_TYPE']");
					defaults = new HashMap<String, Object>();
					defaults.put("itemdescription", "");
					defaults.put("id", "");
					itemList = CommonUtils.coverEles(items, null, null, new String[] { "itemname" }, null, defaults, xpaths);
					dsp.initItemdata(itemList, false, false);
					JOptionPane.showMessageDialog(dialog, "导入成功");
				}
				break;
			case 4:
				// 代码中心
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getCodeTablePnl(null);
				pnl.edit(dialog, null);
				if (!pnl.isSubmit()) {
					return;
				}
				XMLDto codeTable = pnl.getSelect();
				if (codeTable == null) {
					return;
				}
				List<XMLDto> codeFields = InvokerServiceUtils.getCodeFields(codeTable.getValue("id"));
				if (codeFields == null || codeFields.isEmpty()) {
					return;
				}
				itemList = new ArrayList<XMLDto>();
				for (XMLDto field : codeFields) {
					XMLDto item = new XMLDto("cname");
					item.setValue("itemname", field.getValue("fieldname"));
					item.setValue("itemlabel", field.getValue("fieldlabel"));
					item.setValue("itemtype", field.getValue("fieldtype"));
					item.setValue("itemdescription", field.getValue("fielddescription"));
					itemList.add(item);
				}
				dsp.initItemdata(itemList, !GUIUtils.showConfirm(dialog, "覆盖式吗？"), false);
				JOptionPane.showMessageDialog(dialog, "导入成功");
				break;
			case 5:
				if (items != null && !items.isEmpty()) {
					itemList = CommonUtils.coverEles(items, new String[] { "name", "cname", "fieldType" }, null, new String[] { "itemlabel" }, switchPros, defaults, null);
					LinkedHashMap<String, String> showmap = new LinkedHashMap<String, String>();
					showmap.put("itemname", "英文名");
					ObjectMutiSelectPnl<XMLDto> selectFields = new ObjectMutiSelectPnl<XMLDto>(itemList, "中文名", showmap);
					selectFields.edit(dialog, null);
					if (!selectFields.isSubmit()) {
						return;
					}
					itemList = selectFields.getSelects();
					dsp.initItemdata(itemList, radioButton.isSelected(), false);
					JOptionPane.showMessageDialog(dialog, "导入成功");
				}
				break;
			case 6:
				if (reportEditor == null) {
					reportEditor = new ReportSelectEditor();
				}
				reportEditor.edit(dialog, null);
				if (!reportEditor.isSubmit()) {
					return;
				}
				dto = reportEditor.getSelect();
				if (dto == null || "false".equals(dto.getValue("leaf"))) {
					return;
				}
				String reportId = dto.getValue("reportid");
				String xml = InvokerServiceUtils.getReportFields(reportId);
				if (CommonUtils.isStrEmpty(xml)) {
					return;
				}
				doc = DocumentHelper.parseText(xml);
				items = doc.selectNodes("//field");
				if (items == null || items.isEmpty()) {
					return;
				}
				if (items.isEmpty()) {
					GUIUtils.showMsg(dialog, "字段为空");
					return;
				}
				itemList = CommonUtils.coverEles(items, new String[] { "name", "cname", "fieldType" }, null, new String[] { "cname" }, switchPros, defaults, null);
				dsp.initItemdata(itemList, !GUIUtils.showConfirm(dialog, "总共" + itemList.size() + "个字段,是否覆盖式(否新增式)?"), false);
				JOptionPane.showMessageDialog(dialog, "导入成功");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(dialog, "导入失败" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	private void serverSelect(boolean b) {
		if (select == 5) {
			button_1.setEnabled(b);
			radioButton_1.setEnabled(b);
			radioButton.setEnabled(b);
		}
	}

	/**
	 * Create the panel.
	 */
	public LoadClassPnl(DsMainPnl dsp) {
		this.dsp = dsp;
		setPreferredSize(new Dimension(364, 488));
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u5BFC\u5165\u9009\u9879", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 6, 348, 297);
		add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JRadioButton rdbtnxml = new JRadioButton("\u5BFC\u5165\u3010\u81EA\u5B9A\u4E49\u67E5\u8BE21.0\u3011\u5BFC\u51FA\u7684XML");
		rdbtnxml.setEnabled(false);
		rdbtnxml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				serverSelect(false);
				select = 1;
			}
		});
		panel.add(rdbtnxml);

		JRadioButton rdbtnxml_1 = new JRadioButton("\u5BFC\u5165\u3010\u81EA\u5B9A\u4E49\u67E5\u8BE22.0\u3011\u5BFC\u51FA\u7684XML");
		rdbtnxml_1.setSelected(true);
		rdbtnxml_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				serverSelect(false);
				select = 2;
			}
		});
		panel.add(rdbtnxml_1);

		JRadioButton rdbtnxml_2 = new JRadioButton("\u5BFC\u5165\u3010\u521B\u5EFA\u6570\u636E\u8868\u7ED3\u6784\u3011\u7684XML");
		rdbtnxml_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverSelect(false);
				select = 3;
			}
		});
		panel.add(rdbtnxml_2);

		JRadioButton rdbtnxml_3 = new JRadioButton("\u5BFC\u5165\u3010\u4EE3\u7801\u4E2D\u5FC3\u8868\u7ED3\u6784\u3011\u5BFC\u51FA\u7684XML");
		rdbtnxml_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverSelect(false);
				select = 4;
			}
		});
		panel.add(rdbtnxml_3);

		JRadioButton radioButton_3 = new JRadioButton("\u901A\u8FC7\u670D\u52A1\u540D\u83B7\u53D6\u8868\u7ED3\u6784");
		radioButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				select = 5;
				serverSelect(true);
			}
		});
		panel.add(radioButton_3);

		JRadioButton radioButton_2 = new JRadioButton("\u83B7\u53D6\u62A5\u8868\u6570\u636E\u6E90");
		radioButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverSelect(false);
				select = 6;
			}
		});
		panel.add(radioButton_2);

		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sumbit();
			}
		});
		btnNewButton.setBounds(111, 453, 117, 29);
		add(btnNewButton);

		JButton button = new JButton("\u5173\u95ED");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		button.setBounds(237, 453, 117, 29);
		add(button);

		JLabel label = new JLabel("\u670D\u52A1\u540D");
		label.setBounds(16, 315, 61, 16);
		add(label);

		textField = new JTextField();
		textField.setBounds(77, 309, 202, 28);
		add(textField);
		textField.setColumns(10);

		JLabel label_1 = new JLabel("\u6570\u636E\u8868\u540D");
		label_1.setBounds(16, 343, 61, 16);
		add(label_1);

		button_1 = new JButton("\u83B7\u53D6");
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {
			private String serviceName;
			private List<XMLDto> tables;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!textField.getText().trim().equals(serviceName)) {
					items = null;
					textField_1.setText("");
					serviceName = textField.getText().trim();
					if (CommonUtils.isStrEmpty(serviceName)) {
						return;
					}
					tables = CommonUtils.getTableByService(serviceName);
					if (tables == null) {
						GUIUtils.showMsg(dialog, "读不到表格");
						return;
					}
				}
				ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(tables);
				pnl.edit(dialog, null);
				if (!pnl.isChange()) {
					return;
				}
				XMLDto value = pnl.getSelect();
				if (pnl.isNull()) {
					return;
				}
				items = value.getObject(BackedList.class, "fields");
				textField_1.setText(value.getValue("name"));
			}
		});
		button_1.setBounds(278, 310, 61, 29);
		add(button_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBounds(16, 371, 338, 55);
		add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		radioButton_1 = new JRadioButton("\u8986\u76D6\u5F0F\uFF08\u5220\u9664\u539F\u6709\u6570\u636E\u540E\u5BFC\u5165\uFF09");
		radioButton_1.setSelected(true);
		panel_1.add(radioButton_1);

		radioButton = new JRadioButton("\u65B0\u589E\u5F0F\uFF08\u65B0\u589E\u5E76\u5220\u9664\u91CD\u590D\u8BB0\u5F55\uFF09");
		panel_1.add(radioButton);
		ButtonGroup group2 = new ButtonGroup();
		radioButton_1.setEnabled(false);
		radioButton.setEnabled(false);
		group2.add(radioButton);
		group2.add(radioButton_1);
		ButtonGroup group = new ButtonGroup();
		Component[] comps = panel.getComponents();
		for (Component c : comps) {
			group.add((AbstractButton) c);
		}
		JList list = new JList();
		list.setBounds(0, 0, 1, 1);
		add(list);
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(77, 337, 262, 28);
		add(textField_1);
		textField_1.setColumns(10);
		dialog = GUIUtils.getDialog(dialog, "导入", this);
		dialog.setVisible(true);
	}
}
