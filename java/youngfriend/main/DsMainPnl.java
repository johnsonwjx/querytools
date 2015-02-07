/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DsMainPnl.java
 *
 * Created on 2011-7-14, 15:58:56
 */
package youngfriend.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import youngfriend.beans.XMLDto;
import youngfriend.gui.InputSearchPnl;
import youngfriend.gui.LoadClassPnl;
import youngfriend.gui.SearchTarget;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;
import youngfriend.utils.XMLUtils;
import java.awt.Color;

/**
 * 
 * 
 * @author yf
 */
public class DsMainPnl extends javax.swing.JPanel {
	enum Datatype {
		C {
			@Override
			public String getValue() {
				return "C";
			}

			@Override
			public String toString() {
				return "字符类型";
			}
		},
		D {
			@Override
			public String getValue() {
				return "D";
			}

			@Override
			public String toString() {
				return "日期类型";
			}
		},
		N {
			@Override
			public String getValue() {
				return "N";
			}

			@Override
			public String toString() {
				return "数字类型";
			}
		};
		public abstract String getValue();
	}

	public final static Logger logger = LogManager.getLogger(DsMainPnl.class.getName());

	private static final long serialVersionUID = 1L;

	private JButton button;
	private JScrollPane dsItemSP;
	private String[] fieldName_titles = new String[] { "序号", "数据项英文名", "数据项中文名", "数据类型", "描述", "id" };
	private int index_datatype = 3;
	private int index_description = 4;

	private int index_fieldnameEN = 1;
	private int index_fieldnameenCN = 2;
	private int index_id = 5;

	private int index_sortcode = 0;

	private javax.swing.JButton jButton1;

	private javax.swing.JButton jButton2;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JLabel jLabel4;

	private javax.swing.JLabel jLabel5;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JTextField jtfClassAlias;

	private javax.swing.JTextField jtfClassCatalogName;

	private javax.swing.JTextField jtfClassDes;

	private javax.swing.JTextField jtfClassId;

	private javax.swing.JTextField jtfClassName;

	private javax.swing.JToolBar jToolBar1;

	private DefaultTableModel model;

	private JPanel panel_1;

	private JTable tab;

	private javax.swing.JButton viewBT;

	private String catalogid;

	private JSpinner spinner;
	private JPanel panel_2;
	private JToolBar toolBar;
	private JButton newBT;
	private JButton delBT;
	private JButton upBtn;
	private JButton downBtn;
	private JCheckBox checkBox;

	private JButton copyBtn;

	private JButton parseBtn;
	private static Vector<Vector<?>> copys = new Vector<Vector<?>>();
	private JButton button_1;

	public DsMainPnl() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setPreferredSize(new Dimension(0, 150));
		panel.add(jPanel1, BorderLayout.NORTH);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 22, 73, 28);
		jtfClassCatalogName = new javax.swing.JTextField();
		jtfClassCatalogName.setBounds(76, 22, 199, 28);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(574, 22, 85, 28);
		jtfClassName = new javax.swing.JTextField();
		jtfClassName.setBounds(646, 22, 199, 28);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(290, 22, 85, 28);
		jtfClassId = new javax.swing.JTextField();
		jtfClassId.setBounds(363, 22, 199, 28);
		jtfClassDes = new javax.swing.JTextField();
		jtfClassDes.setBounds(363, 62, 482, 28);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("数据源信息"));

		jLabel1.setText("\u76EE\u5F55\u540D\u79F0\uFF1A");

		jtfClassCatalogName.setEditable(false);

		jLabel5.setText("查询类名称：");

		jLabel2.setText("查询类编号：");

		jtfClassId.setEditable(false);
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(jtfClassCatalogName);
		jPanel1.add(jLabel2);
		jPanel1.add(jtfClassId);
		jPanel1.add(jLabel5);
		jPanel1.add(jtfClassName);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(6, 62, 85, 28);

		jLabel4.setText("查询类别名：");
		jPanel1.add(jLabel4);
		jtfClassAlias = new javax.swing.JTextField();
		jtfClassAlias.setBounds(76, 62, 199, 28);
		jPanel1.add(jtfClassAlias);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(290, 62, 85, 28);

		jLabel3.setText("备          注：");
		jPanel1.add(jLabel3);
		jPanel1.add(jtfClassDes);

		JLabel label = new JLabel("\u6539\u53D8\u9009\u4E2D\u5B57\u6BB5\u987A\u5E8F\uFF1A");
		label.setBounds(595, 109, 119, 16);
		jPanel1.add(label);

		JButton button_2 = new JButton("...");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeIndex();
			}

		});
		button_2.setBounds(772, 103, 73, 29);
		jPanel1.add(button_2);

		spinner = new JSpinner();
		spinner.setBounds(709, 103, 61, 28);
		jPanel1.add(spinner);

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		dsItemSP = new javax.swing.JScrollPane();
		panel_2.add(dsItemSP, BorderLayout.CENTER);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel_2.add(toolBar, BorderLayout.NORTH);

		newBT = new JButton();
		newBT.setForeground(Color.BLUE);
		newBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});
		newBT.setVerticalTextPosition(SwingConstants.BOTTOM);
		newBT.setText("  \u65B0\u589E\u6570\u636E\u9879  ");
		newBT.setHorizontalTextPosition(SwingConstants.CENTER);
		newBT.setFocusable(false);
		toolBar.add(newBT);

		delBT = new JButton();
		delBT.setForeground(Color.BLUE);
		delBT.setVerticalTextPosition(SwingConstants.BOTTOM);
		delBT.setText("  \u5220\u9664\u6570\u636E\u9879  ");
		delBT.setHorizontalTextPosition(SwingConstants.CENTER);
		delBT.setFocusable(false);
		toolBar.add(delBT);

		upBtn = new JButton();
		upBtn.setForeground(Color.BLUE);
		upBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		upBtn.setText("  \u4E0A\u79FB\u6570\u636E\u9879  ");
		upBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		upBtn.setFocusable(false);
		toolBar.add(upBtn);

		downBtn = new JButton();
		downBtn.setForeground(Color.BLUE);
		downBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		downBtn.setText("\u4E0B\u79FB\u6570\u636E\u9879");
		downBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		downBtn.setFocusable(false);
		toolBar.add(downBtn);
		toolBar.addSeparator(new Dimension(20, 0));

		button_1 = new JButton(" \u5168\u9009 ");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getRowCount() > 0) {
					tab.setRowSelectionInterval(0, model.getRowCount() - 1);
				}
			}
		});
		toolBar.add(button_1);
		toolBar.addSeparator();
		checkBox = new JCheckBox("\u542F\u7528\u590D\u5236");
		toolBar.add(checkBox);
		copyBtn = new JButton("\u590D\u5236");
		toolBar.add(copyBtn);

		parseBtn = new JButton("\u7C98\u8D34");
		toolBar.add(parseBtn);

		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(15, 0));
		add(panel_1, BorderLayout.EAST);
		jToolBar1 = new javax.swing.JToolBar();
		add(jToolBar1, BorderLayout.NORTH);
		viewBT = new javax.swing.JButton();
		viewBT.setForeground(Color.BLUE);
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();

		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);

		jButton1.setText("导出查询类");
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exportClass();
			}
		});

		button = new JButton("\u5BFC\u5165\u67E5\u8BE2\u7C7B");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new LoadClassPnl(DsMainPnl.this);
			}
		});
		jToolBar1.add(button);
		jToolBar1.add(jButton1);

		jButton2.setText("导出标准格式XML");
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exportStandXML();
			}

		});
		jToolBar1.add(jButton2);
		jToolBar1.addSeparator(new Dimension(30, 0));

		viewBT.setText("保存查询类"); // NOI18N
		viewBT.setActionCommand("查看组件信息");
		viewBT.setFocusable(false);
		viewBT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		viewBT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		viewBT.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveClass();
			}
		});
		jToolBar1.add(viewBT);
		init();
	}

	private void init() {
		parseBtn.setVisible(false);
		copyBtn.setVisible(false);
		checkBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				copyBtn.setVisible(checkBox.isSelected());
				parseBtn.setVisible(checkBox.isSelected());
				if (!checkBox.isSelected()) {
					copys.clear();
					parseBtn.setEnabled(false);
				}

			}
		});
		copyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = tab.getSelectedRows();
				if (rows.length <= 0) {
					GUIUtils.showMsg(MainFrame.getInstance(), "请选择复制行");
					return;
				}
				copys.clear();
				Vector<Vector<?>> all = model.getDataVector();
				for (int row : rows) {
					copys.add(all.get(row));
				}
				parseBtn.setEnabled(true);
			}
		});
		parseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!copys.isEmpty()) {
					int newSelectIndex = model.getRowCount();
					for (Vector<?> item : copys) {
						model.addRow(item);
					}
					CompUtils.initSortNum(model, index_sortcode);
					tab.setRowSelectionInterval(newSelectIndex, newSelectIndex);
				}
				parseBtn.setEnabled(false);
			}
		});

		parseBtn.setEnabled(!copys.isEmpty());
		checkBox.setSelected(parseBtn.isEnabled());

		JComboBox combo = new JComboBox(Datatype.values());
		model = new DefaultTableModel(fieldName_titles, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == index_sortcode) {
					return Integer.class;
				}
				return super.getColumnClass(columnIndex);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column > 0) {
					return true;
				} else {
					return false;
				}
			}
		};
		tab = new JTable(model);
		tab.setRowSelectionAllowed(true);
		tab.setColumnSelectionAllowed(false);
		tab.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tab.getTableHeader().setReorderingAllowed(false);
		tab.getColumnModel().getColumn(index_datatype).setCellEditor(new DefaultCellEditor(combo));
		tab.getColumnModel().getColumn(index_datatype).setResizable(false);
		tab.getColumnModel().getColumn(index_description).setMinWidth(200);
		CompUtils.setColumnWidth(tab, index_datatype, 70);
		CompUtils.setColumnWidth(tab, index_sortcode, 40);
		CompUtils.setColumnWidth(tab, index_id, 0);
		this.dsItemSP.setViewportView(tab);
		CompUtils.tableMove(upBtn, tab, index_sortcode, true);
		CompUtils.tableMove(downBtn, tab, index_sortcode, false);

		// 删除按钮
		CompUtils.tableDelRow(delBT, tab, index_sortcode);
		final InputSearchPnl searchPnl = new InputSearchPnl(new SearchTarget() {
			@Override
			public void search(String txt) {
				if (model == null || model.getRowCount() <= 0) {
					return;
				}
				txt = txt.trim().toLowerCase();
				Map<String, Integer> map = new LinkedHashMap<String, Integer>();
				for (int i = 0; i < model.getRowCount(); i++) {
					String temp = model.getValueAt(i, index_sortcode) + ". " + CommonUtils.coverNull((String) model.getValueAt(i, index_fieldnameenCN)) + "->" + CommonUtils.coverNull((String) model.getValueAt(i, index_fieldnameEN));
					if (CommonUtils.isStrEmpty(temp.trim())) {
						continue;
					}
					temp = temp.toLowerCase();
					if (temp.indexOf(txt) >= 0) {
						map.put(temp.toString(), i);
					}
				}
				if (map.isEmpty()) {
					return;
				}
				for (String key : map.keySet()) {
					JMenuItem item = new JMenuItem(key);
					final int value = map.get(key);
					item.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tab.setRowSelectionInterval(value, value);
							CompUtils.setTableScrolVisible(tab, value);
							InputSearchPnl.menu.setVisible(false);
						}
					});
					InputSearchPnl.menu.add(item);
					if (InputSearchPnl.menu.getPreferredSize().height > dsItemSP.getPreferredSize().height) {
						InputSearchPnl.menu.add(new JMenuItem("......"));
						break;
					}
				}
			}
		}, 400, 30);
		searchPnl.setLocation(6, 102);
		jPanel1.add(searchPnl);
		jPanel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				InputSearchPnl.menu.setVisible(false);
			}
		});
	}

	private void addItem() {
		int[] rows = tab.getSelectedRows();
		if (rows.length > 1) {
			GUIUtils.showMsg(MainFrame.getInstance(), "请单选一个添加");
			return;
		}
		CompUtils.stopTabelCellEditor(tab);
		int insertIndex = 0;
		if (rows.length == 0) {
			insertIndex = model.getRowCount();
			this.model.addRow(new Object[] { model.getRowCount() + 1, null, null, Datatype.C, null });
		} else {
			insertIndex = rows[0] + 1;
			model.insertRow(insertIndex, new Object[] { rows[0] + 2, null, null, Datatype.C, null });
		}
		CompUtils.initSortNum(model, index_sortcode);
		tab.setRowSelectionInterval(insertIndex, insertIndex);

	}

	private void changeIndex() {
		if (tab.getSelectedRowCount() > 1 || tab.getSelectedRowCount() <= 0) {
			GUIUtils.showMsg(MainFrame.getInstance(), "请单选一列");
			return;
		}

		int inserIndex = (Integer) spinner.getValue();
		if (inserIndex <= 0 || inserIndex > model.getRowCount()) {
			GUIUtils.showMsg(MainFrame.getInstance(), "输入不正确");
			return;
		}
		inserIndex--;
		int index = tab.getSelectedRow();
		if (inserIndex == index) {
			return;
		}
		if (tab.getCellEditor() != null) {
			tab.getCellEditor().stopCellEditing();
		}
		Vector<Vector<?>> data = model.getDataVector();
		Vector<?> obj = data.get(index);
		model.removeRow(index);
		model.insertRow(inserIndex, obj);
		CompUtils.initSortNum(model, index_sortcode);
		tab.setRowSelectionInterval(inserIndex, inserIndex);
		CompUtils.setTableScrolVisible(tab, inserIndex);

	}

	private void buildFieldsElement(Element fieldsEle) {
		if (model.getRowCount() > 0) {
			CompUtils.stopTabelCellEditor(tab);
			for (int i = 0; i < model.getRowCount(); i++) {
				Element fieldItem = fieldsEle.addElement("field");
				fieldItem.addAttribute("name", (String) model.getValueAt(i, index_fieldnameEN));
				fieldItem.addAttribute("label", (String) model.getValueAt(i, index_fieldnameenCN));
				fieldItem.addAttribute("type", model.getValueAt(i, index_datatype).toString());
				fieldItem.addAttribute("length", "");
				fieldItem.addAttribute("dec", "");
				fieldItem.addAttribute("decription", (String) model.getValueAt(i, index_description));
				fieldItem.addAttribute("default", "");
				fieldItem.addAttribute("required", "");
				fieldItem.addAttribute("ishow", "");
				fieldItem.addAttribute("null", "");
				fieldItem.addAttribute("isdept", "");
				fieldItem.addAttribute("ischeckrepeat", "");
				fieldItem.addAttribute("foreignkey", "");
				fieldItem.addAttribute("classid", jtfClassId.getText());
				fieldItem.addAttribute("worktype", "");
				fieldItem.addAttribute("id", (String) model.getValueAt(i, index_id));
				fieldItem.addAttribute("version", "");
				fieldItem.addAttribute("no", (String) model.getValueAt(i, index_sortcode));
				fieldItem.addAttribute("itemprop", "");
				fieldItem.addAttribute("valuecondition", "");
			}
		}
	}

	private Document crateStandClass() {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("root");
		doc.setRootElement(root);
		Element tableEle = root.addElement("table");
		tableEle.addAttribute("id", jtfClassId.getText());
		tableEle.addAttribute("fatherid", "");
		tableEle.addAttribute("name", jtfClassName.getText());
		tableEle.addAttribute("description", jtfClassDes.getText());
		tableEle.addAttribute("alias", jtfClassAlias.getText());
		tableEle.addAttribute("codecontinuous", "");
		tableEle.addAttribute("catalogid", catalogid);
		tableEle.addAttribute("billtablename", "");
		tableEle.addAttribute("codefmt", "");
		tableEle.addAttribute("componentid", "");
		tableEle.addAttribute("cname", "");
		tableEle.addAttribute("fathertablename", "");
		tableEle.addAttribute("mode", "");
		tableEle.addAttribute("keyfieldname", "");
		tableEle.addAttribute("code", "");
		tableEle.addAttribute("activeversion", "");
		tableEle.addAttribute("status", "");
		Element fieldsEle = tableEle.addElement("fields");
		buildFieldsElement(fieldsEle);
		return doc;
	}

	private Element getXML() throws Exception {
		XMLDto style = CompUtils.getStyle();
		Element root = DocumentHelper.createElement("root");
		Element classes = root.addElement("classes");
		classes.addElement("id").setText(style.getValue("classid"));
		classes.addElement("name").setText(jtfClassName.getText());
		classes.addElement("alias").setText(jtfClassAlias.getText());
		classes.addElement("catalogid").setText(style.getValue("catalogid"));
		classes.addElement("description").setText(jtfClassDes.getText());
		classes.addElement("version").setText("");
		// 获取 表格数据
		int rowCount = model.getRowCount();
		if (rowCount > 0) {
			CompUtils.stopTabelCellEditor(tab);
			Element classitems = classes.addElement("classitems");

			List<String> fileNames = new ArrayList<String>();
			for (int i = 0; i < rowCount; i++) {
				String fieldname = ((String) this.model.getValueAt(i, index_fieldnameEN)).trim().toUpperCase();
				if (fileNames.contains(fieldname)) {
					tab.setRowSelectionInterval(i, i);
					GUIUtils.showMsg(MainFrame.getInstance(), "数据项名【" + fieldname + "】重复，请检查!");
					return null;
				}
				fileNames.add(fieldname);
				XMLDto dto = new XMLDto("itemname");
				dto.setValue("itemdescription", this.model.getValueAt(i, index_description));
				dto.setValue("itemno", getNo(i));
				dto.setValue("itemname", this.model.getValueAt(i, index_fieldnameEN));
				dto.setValue("itemlabel", this.model.getValueAt(i, index_fieldnameenCN));
				dto.setValue("itemtype", ((Datatype) this.model.getValueAt(i, index_datatype)).getValue());
				dto.setValue("id", this.model.getValueAt(i, index_id));
				dto.setValue("classid", jtfClassId.getText());
				classitems.add(dto.cover2Ele("classitem"));
			}
		}
		return root;
	}

	private String getNo(int i) {
		if (i < 10) {
			return "00" + i;
		}
		if (i > 9 && i < 100) {
			return "0" + i;
		}
		return i + "";
	}

	private void exportStandXML() {
		JFileChooser chooser = XMLUtils.getFileChooser();
		String filename = jtfClassAlias.getText() + "(标准格式)";
		filename = filename.replaceAll("[\\*.]", "");
		chooser.setSelectedFile(new File(filename + ".xml"));
		int flag = chooser.showSaveDialog(MainFrame.getInstance());
		if (flag != JFileChooser.APPROVE_OPTION) {
			return;
		}
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			Document doc = crateStandClass();
			XMLUtils.saveFile(doc, chooser.getSelectedFile(), format);
			GUIUtils.showMsg(MainFrame.getInstance(), "导出成功");
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "导出失败");
			logger.error(e.getMessage(), e);
		}

	}

	private void exportClass() {
		JFileChooser chooser = XMLUtils.getFileChooser();
		String filename = jtfClassName.getText() + "(自定义查询类2.0)";
		filename = filename.replaceAll("[\\*.]", "");
		chooser.setSelectedFile(new File(filename + ".xml"));
		int flag = chooser.showSaveDialog(MainFrame.getInstance());
		if (flag != JFileChooser.APPROVE_OPTION) {
			return;
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		try {
			Element root = getXML();
			if (root == null) {
				return;
			}
			Document doc = DocumentHelper.createDocument();
			doc.setRootElement(root);
			File file = chooser.getSelectedFile();
			if (file.exists() && !GUIUtils.showConfirm(MainFrame.getInstance(), "文件已存在，是否继续导出")) {
				return;
			}
			XMLUtils.saveFile(doc, file, format);
			GUIUtils.showMsg(MainFrame.getInstance(), "导出成功");
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "保存失败");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	public void initItemdata(List<XMLDto> itemList, boolean append, boolean sort) {
		if (!append) {
			clear();
		}
		if (sort) {
			CommonUtils.sortXmlDtos(itemList, "itemno");
		}
		List<String> typeLst = new ArrayList<String>();
		Datatype[] types = Datatype.values();
		for (Datatype t : types) {
			typeLst.add(t.getValue());
		}
		if (null != itemList && !itemList.isEmpty()) {
			for (int i = 0; i < itemList.size(); i++) {
				XMLDto dto = itemList.get(i);
				Object[] newRow = new Object[this.fieldName_titles.length];
				newRow[index_sortcode] = model.getRowCount() + 1;
				newRow[index_fieldnameEN] = dto.getValue("itemname");
				newRow[index_fieldnameenCN] = dto.getValue("itemlabel");
				Datatype datatype = Datatype.C;
				if ("I".equalsIgnoreCase(dto.getValue("itemtype"))) {
					datatype = Datatype.N;
				} else {
					if (typeLst.contains(dto.getValue("itemtype"))) {
						datatype = Datatype.valueOf(dto.getValue("itemtype"));
					}
				}
				logger.debug(dto.getValue("itemtype"));
				newRow[index_datatype] = datatype;
				newRow[index_description] = dto.getValue("itemdescription");
				newRow[index_id] = dto.getValue("id");
				model.addRow(newRow);
			}
		}
	}

	private void saveClass() {
		if (CommonUtils.isStrEmpty(jtfClassName.getText()) || CommonUtils.isStrEmpty(jtfClassAlias.getText())) {
			GUIUtils.showMsg(MainFrame.getInstance(), "查询类名称或别名不能为空!");
			return;
		}
		if (!MainFrame.getInstance().getLeftTree().CheckLock()) {
			return;
		}
		XMLDto style = CompUtils.getStyle();
		try {
			Element root = getXML();
			if (root == null) {
				return;
			}
			String classId = InvokerServiceUtils.updateClass(jtfClassId.getText(), root.asXML());
			jtfClassId.setText(classId);
			style.setValue("name", jtfClassName.getText());
			style.setValue("classname", jtfClassName.getText());
			style.setValue("classalias", jtfClassAlias.getText());
			style.setValue("classdesc", jtfClassDes.getText());
			MainFrame.getInstance().getLeftTree().updateTreeUI();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "保存查询了失败");
			logger.error(e.getMessage(), e);
			return;
		}
		GUIUtils.showMsg(MainFrame.getInstance(), "保存成功");

	}

	private void clearText() {
		jtfClassCatalogName.setText("");
		jtfClassId.setText("");
		jtfClassName.setText("");
		this.jtfClassAlias.setText("");
		this.jtfClassDes.setText("");
	}

	public void initTextField(String catalogid, String catalogname, String classid, String name, String alias, String desc) {
		clearText();
		if (!CommonUtils.isStrEmpty(catalogid)) {
			this.catalogid = catalogid;
			jtfClassCatalogName.setText(catalogname);
		}
		if (!CommonUtils.isStrEmpty(classid)) {
			jtfClassId.setText(classid);
		}
		if (!CommonUtils.isStrEmpty(name)) {
			this.jtfClassName.setText(name);
		}
		if (!CommonUtils.isStrEmpty(alias)) {
			this.jtfClassAlias.setText(alias);
		}
		if (!CommonUtils.isStrEmpty(desc)) {
			this.jtfClassDes.setText(desc);
		}

	}

	public void initTextField(String name, String alias, String desc) {
		if (!CommonUtils.isStrEmpty(name)) {
			this.jtfClassName.setText(name);
		}
		if (!CommonUtils.isStrEmpty(alias)) {
			this.jtfClassAlias.setText(alias);
		}
		if (!CommonUtils.isStrEmpty(desc)) {
			this.jtfClassDes.setText(desc);
		}
	}

	public void clear() {
		CompUtils.stopTabelCellEditor(tab);
		model.setRowCount(0);
	}
}
