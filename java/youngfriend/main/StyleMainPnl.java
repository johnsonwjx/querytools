package youngfriend.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import youngfriend.beans.PropDto;
import youngfriend.beans.TreeIconEnum;
import youngfriend.beans.TreeObj;
import youngfriend.beans.Validate;
import youngfriend.beans.XMLDto;
import youngfriend.coms.IStyleCom;
import youngfriend.coms.IStylePanel;
import youngfriend.coms.TNewPanel;
import youngfriend.gui.InputEditor;
import youngfriend.gui.InputSearchPnl;
import youngfriend.gui.PropEditorTable;
import youngfriend.gui.SearchTarget;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.DataUtils;
import youngfriend.utils.Do4objs;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;
import youngfriend.utils.XMLUtils;

/**
 * 
 * @author yf
 */
@SuppressWarnings("unchecked")
public class StyleMainPnl extends JPanel {
	private static final int dividerSize = 6;
	private static final Logger logger = LogManager.getLogger(StyleMainPnl.class);
	private static final long serialVersionUID = 1L;
	private JButton button;
	private JCheckBox checkBox;
	private javax.swing.JButton clearBtn;
	private String currentPage = "page_1";
	private DefaultTableModel fieldModel;
	private ButtonGroup group;
	private JPanel leftpnl;
	private Map<String, Element> pageMap = new HashMap<String, Element>();
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_9;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private List<IStyleCom> selects;
	private JSplitPane rightpnl;
	private javax.swing.JSplitPane spMain;
	private JTabbedPane tabbedPane;
	private JTable table;
	private JTable field_table;
	private JToolBar toolBar;
	private JTree tree;
	private JToggleButton toggleButton_1;
	private String type = "default";
	private JButton button_3;
	private JButton button_4;
	private JCheckBox checkBox_1;
	private Element orignal;
	private boolean backup = false;
	private JPanel panel_8;
	private boolean buildTree = false;
	private JButton button_7;
	private JButton button_8;
	private Map<IStyleCom, String> orignXMLMap = new HashMap<IStyleCom, String>();
	private JToggleButton toggleButton;

	public StyleMainPnl() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		initComponents();
		initKeyAction();
		initUICom();
		rightpnl.setDividerLocation(0.7);
	}

	private void initKeyAction() {
		InputMap inputMap = panel_6.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.clear();
		ActionMap actionMap = panel_6.getActionMap();
		actionMap.clear();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "del");
		// 删除
		actionMap.put("del", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				delSelects();
			}
		});

		// 左移
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ComEum.MODIFIER), "left");
		actionMap.put("left", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
				if (selects.isEmpty()) {
					return;
				}
				for (IStyleCom c : selects) {
					JComponent comp = (JComponent) c;
					c.setPropValue("Left", comp.getX() - 1 + "");
				}
				table.updateUI();
				CompUtils.updateUI(CompUtils.getWin());
			}
		});
		// 右移
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ComEum.MODIFIER), "right");
		actionMap.put("right", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
				if (selects.isEmpty()) {
					return;
				}
				for (IStyleCom c : selects) {
					JComponent comp = (JComponent) c;
					c.setPropValue("Left", comp.getX() + 1 + "");
				}
				table.updateUI();
				CompUtils.updateUI(CompUtils.getWin());
			}
		});
		// 上移
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, ComEum.MODIFIER), "up");
		actionMap.put("up", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
				if (selects.isEmpty()) {
					return;
				}
				for (IStyleCom c : selects) {
					JComponent comp = (JComponent) c;
					c.setPropValue("Top", comp.getY() - 1 + "");
				}
				table.updateUI();
				CompUtils.updateUI(CompUtils.getWin());
			}
		});
		// 下移
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ComEum.MODIFIER), "down");
		actionMap.put("down", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
				if (selects.isEmpty()) {
					return;
				}
				for (IStyleCom c : selects) {
					JComponent comp = (JComponent) c;
					c.setPropValue("Top", comp.getY() + 1 + "");
				}
				table.updateUI();
				CompUtils.updateUI(CompUtils.getWin());
			}
		});

	}

	private void addChildNode(DefaultMutableTreeNode pNode, IStylePanel parent) {
		List<IStyleCom> cs = parent.getChilds();
		if (cs.size() < 0) {
			return;
		}
		for (IStyleCom c : cs) {
			DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(c);
			pNode.add(cNode);
			if (c instanceof IStylePanel) {
				addChildNode(cNode, (IStylePanel) c);
			}
		}
	}

	public Element getStyleEle() {
		updateCurrentPage();
		Element root = DocumentHelper.createElement("root");
		Element winEle = CompUtils.getWin().cover2Ele("root_panel");
		Element fields = winEle.addElement("fields");
		StringBuilder fieldSb = new StringBuilder();
		for (int i = 0; i < fieldModel.getRowCount(); i++) {
			XMLDto xmlDto = (XMLDto) fieldModel.getValueAt(i, 1);
			String itemname = xmlDto.getValue("itemname");
			String itemtype = xmlDto.getValue("itemtype");
			fieldSb.append(itemname).append(":").append(itemtype).append(",");
		}
		if (fieldSb.length() > 0) {
			fieldSb.deleteCharAt(fieldSb.length() - 1);
		}
		fields.setText(fieldSb.toString());
		Element controlreturnfield = winEle.addElement("controlreturnfield");
		controlreturnfield.setText(checkBox.isSelected() ? "1" : "0");
		root.add(winEle);
		Element pages = winEle.addElement("pages");
		if (!pageMap.isEmpty()) {
			for (String key : pageMap.keySet()) {
				Element pageEle = pageMap.get(key);
				if (pageEle.hasContent()) {
					pages.add((Element) pageEle.clone());
				}
			}
		}
		return root;
	}

	public String getComType() {
		return type;
	}

	public void resetComType() {
		Enumeration<AbstractButton> eles = group.getElements();
		AbstractButton first = eles.nextElement();
		first.setSelected(true);
		type = first.getName();
	}

	public JTable getField_table() {
		return field_table;
	}

	private void initComponents() {
		spMain = new javax.swing.JSplitPane();
		spMain.setResizeWeight(1.0);
		spMain.setDividerSize(dividerSize);
		JToolBar jToolBar1 = new javax.swing.JToolBar();
		clearBtn = new javax.swing.JButton();
		rightpnl = new JSplitPane();
		rightpnl.setPreferredSize(new Dimension(300, 0));
		rightpnl.setMinimumSize(new Dimension(300, 0));
		spMain.setRightComponent(rightpnl);
		rightpnl.setResizeWeight(0.7);
		rightpnl.setDividerSize(dividerSize);
		rightpnl.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		rightpnl.setLeftComponent(tabbedPane);
		scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("\u5C5E\u6027", null, scrollPane_1, null);
		panel_8 = new JPanel();
		panel_8.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("\u5B57\u6BB5\u5217\u8868", null, panel_8, null);
		scrollPane_3 = new JScrollPane();
		panel_8.add(scrollPane_3, BorderLayout.CENTER);
		field_table = new JTable();
		field_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		field_table.setDragEnabled(true);

		scrollPane_3.setViewportView(field_table);
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel_8.add(toolBar, BorderLayout.SOUTH);

		checkBox = new JCheckBox("\u4E0D\u63A7\u5236\u8FD4\u56DE\u5B57\u6BB5");
		toolBar.add(checkBox);
		toolBar.addSeparator();
		button = new JButton("\u5168\u9009");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean all = "全选".equals(button.getText());
				for (int i = 0; i < field_table.getRowCount(); i++) {
					field_table.setValueAt(all, i, 0);
				}
				if (all) {
					button.setText("全否");
				} else {
					button.setText("全选");
				}
			}
		});
		toolBar.add(button);

		panel_1 = new JPanel();
		rightpnl.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		tree = new JTree();
		tree.setScrollsOnExpand(true);
		scrollPane.setViewportView(tree);

		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);
		clearBtn.setText("\u6E05\u7A7A\u754C\u9762");
		clearBtn.setFocusable(false);
		clearBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		clearBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		clearBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!GUIUtils.showConfirm(MainFrame.getInstance(), "确定删除")) {
					return;
				}
				pageMap.put(currentPage, null);
				initWin(CompUtils.getWin(), currentPage);
			}
		});
		jToolBar1.addSeparator();
		jToolBar1.add(clearBtn);
		setLayout(new BorderLayout(0, 0));
		add(jToolBar1, BorderLayout.NORTH);
		jToolBar1.addSeparator();
		toggleButton = new JToggleButton("\u591A\u9875\u9762\u677F");
		toggleButton.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				panel_5.setVisible(toggleButton.isSelected());
			}
		});
		JButton button_1 = new JButton("\u5BFC\u51FA\u6837\u5F0F\u6587\u4EF6");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = XMLUtils.getFileChooser();
				String fileName = CompUtils.getStyle().toString() + ".xml";
				fileName = fileName.replaceAll("\\*", "＊");
				chooser.setSelectedFile(new File(fileName));
				int flag = chooser.showSaveDialog(StyleMainPnl.this);
				if (flag != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File file = chooser.getSelectedFile();
				if (file.exists() && !GUIUtils.showConfirm(MainFrame.getInstance(), "文件已存在，是否继续导出")) {
					return;
				}
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLUtils.saveFile(getStyleEle(), file, format);
				GUIUtils.showMsg(MainFrame.getInstance(), "导出成功");
			}
		});
		jToolBar1.add(button_1);
		jToolBar1.addSeparator();
		JButton button_2 = new JButton("\u5BFC\u5165\u6837\u5F0F\u6587\u4EF6");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = XMLUtils.getFileChooser();
				int flag = chooser.showOpenDialog(StyleMainPnl.this);
				if (flag != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File file = chooser.getSelectedFile();
				if (file == null) {
					return;
				}
				Document doc = XMLUtils.readFile(file);
				Element root = null;
				if (doc != null) {
					root = doc.getRootElement();
				}
				initMain(root);
				GUIUtils.showMsg(MainFrame.getInstance(), "导入成功");

			}
		});
		jToolBar1.add(button_2);
		jToolBar1.addSeparator();
		jToolBar1.add(toggleButton);
		jToolBar1.addSeparator();
		jToolBar1.addSeparator();
		checkBox_1 = new JCheckBox("\u542F\u7528\u5907\u4EFD");
		checkBox_1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean select = e.getStateChange() == ItemEvent.SELECTED;
				button_4.setVisible(select);
				button_3.setVisible(select);
				button_8.setVisible(select);
			}
		});
		jToolBar1.add(checkBox_1);

		button_3 = new JButton("\u5907\u4EFD\u6062\u590D");
		button_3.setVisible(false);
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backup();
			}
		});
		jToolBar1.add(button_3);
		jToolBar1.addSeparator();
		button_4 = new JButton("\u6E05\u7A7A\u5907\u4EFD");
		button_4.setVisible(false);
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataUtils.clearDatas();
				GUIUtils.showMsg(MainFrame.getInstance(), "清空成功");
			}
		});
		jToolBar1.add(button_4);

		button_8 = new JButton("\u8BBE\u7F6E\u5907\u4EFD\u6700\u5927\u503C");
		button_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InputEditor editor = new InputEditor(new Validate<String>() {

					@Override
					public String validate(String obj) {
						if (CommonUtils.isStrEmpty(obj)) {
							return "不能为空";
						}
						if (!CommonUtils.isNumberString(obj)) {
							return "请设置数字";
						}
						int maxSize = Integer.parseInt(obj);
						if (maxSize > 50) {
							return "不能超过50";
						}
						if (maxSize <= 0) {
							return "不能小于1";
						}
						return null;
					}
				});
				Map<String, String> props = new HashMap<String, String>();
				props.put("title", "设置最大缓存数量（0<num<=50）");
				props.put("value", DataUtils.maxSize + "");
				editor.edit(MainFrame.getInstance(), props);
				String value = props.get("value");
				int maxSize = Integer.parseInt(value);
				DataUtils.maxSize = maxSize;

			}
		});
		button_8.setVisible(false);
		jToolBar1.add(button_8);
		jToolBar1.addSeparator();

		button_7 = new JButton("\u68C0\u67E5\u76F8\u540C\u63A7\u4EF6\u540D");
		button_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.checkSaveName();
			}
		});
		jToolBar1.add(button_7);

		add(spMain);

		leftpnl = new JPanel();
		leftpnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		spMain.setLeftComponent(leftpnl);
		leftpnl.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(91, 91, 91));
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_2.setLayout(new BorderLayout());
		leftpnl.add(panel_2, BorderLayout.NORTH);
		panel_2.setPreferredSize(new Dimension(0, 50));
		panel_3 = new JPanel();
		leftpnl.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		panel_5 = new JPanel();
		panel_5.setVisible(false);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		panel_5.setPreferredSize(new Dimension(60, 0));
		panel_5.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_3.add(panel_5, BorderLayout.EAST);

		panel_9 = new JPanel();
		panel_9.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_9.setMaximumSize(new Dimension(60, 300));
		panel_5.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 1, 0, 0));

		toggleButton_1 = new JToggleButton("\u7B2C1\u9875");
		toggleButton_1.setSelected(true);
		panel_9.add(toggleButton_1);

		JToggleButton toggleButton_2 = new JToggleButton("\u7B2C2\u9875");
		panel_9.add(toggleButton_2);

		JToggleButton toggleButton_3 = new JToggleButton("\u7B2C3\u9875");
		panel_9.add(toggleButton_3);

		JToggleButton toggleButton_4 = new JToggleButton("\u7B2C4\u9875");
		panel_9.add(toggleButton_4);

		JToggleButton toggleButton_5 = new JToggleButton("\u7B2C5\u9875");
		panel_9.add(toggleButton_5);

		ButtonGroup bg = new ButtonGroup();
		int i = 1;
		for (Component c : panel_9.getComponents()) {
			c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			AbstractButton btn = (AbstractButton) c;
			btn.setName("page_" + i);
			i++;
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AbstractButton source = (AbstractButton) e.getSource();
					if (source.isSelected() && !source.getName().equals(currentPage)) {
						updateCurrentPage();
					}
					initWin(CompUtils.getWin(), source.getName());
				}
			});
			bg.add(btn);
		}
		scrollPane_2 = new JScrollPane();
		panel_3.add(scrollPane_2, BorderLayout.CENTER);

		panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane_2.setViewportView(panel_6);
		panel_6.setLayout(null);
	}

	public void buildComTree() {
		buildTree = true;
		TNewPanel win = CompUtils.getWin();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(win);
		DefaultTreeModel model = new DefaultTreeModel(root);
		List<IStyleCom> cs = win.getChilds();
		if (!cs.isEmpty()) {
			for (IStyleCom c : cs) {
				DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(c);
				root.add(cNode);
				if (c instanceof IStylePanel) {
					addChildNode(cNode, (IStylePanel) c);
				}
			}
		}
		tree.setModel(model);
		buildTree = false;
		updateTree();
	}

	public void rebuildComTree() {
		CompUtils.rebuiTree(tree, new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				if (obj1.length != 1) {
					return;
				}
				if (!(obj1[0] instanceof JTree)) {
					return;
				}
				buildComTree();
			}
		});
	}

	private void updateCurrentPage() {
		Element pageEle = CompUtils.getWin().getPageEle(currentPage);
		pageMap.put(currentPage, pageEle);
	}

	public void delSelects() {
		List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
		if (selects.isEmpty()) {
			return;
		}
		for (IStyleCom c : selects) {
			JComponent temp = ((JComponent) c);
			temp.getParent().remove(temp);
		}
		CompUtils.updateUI(CompUtils.getWin());
		rebuildComTree();
	}

	private void backup() {
		Element element = DataUtils.getSelectData();
		if (element == null) {
			return;
		}
		backup = true;
		initMain(element);
		backup = false;
	}

	public void initMain(Element root) {
		try {
			if (panel_6.getComponentCount() > 0) {
				release();
			}
			TNewPanel win = new TNewPanel();
			CompUtils.setWin(win);
			panel_6.add(win);
			Map<String, String> filedMap = new HashMap<String, String>();
			if (root != null) {
				Element root_panel = root.element("root_panel");
				String temp = root_panel.elementTextTrim("fields");
				if (!CommonUtils.isStrEmpty(temp)) {
					String[] fieldItem = temp.split(",");
					if (fieldItem.length > 0) {
						for (String f : fieldItem) {
							String[] item = f.split(":");
							if (item.length == 2) {
								filedMap.put(item[0].toUpperCase(), item[1].toUpperCase());
							}
						}
					}
				}
				temp = root_panel.elementTextTrim("controlreturnfield");
				if (!CommonUtils.isStrEmpty(temp)) {
					checkBox.setSelected("1".equals(temp));
				}
				Element propE = root_panel.element("property");
				win.init(propE);
				Element pages = root_panel.element("pages");
				if (pages != null && pages.hasContent()) {
					List<Element> pageEles = pages.elements();
					for (Element page : pageEles) {
						pageMap.put(page.getName(), page);
					}
					if (pageMap.size() > 1) {
						toggleButton.setSelected(true);
						for (String key : pageMap.keySet()) {
							initWin(win, key);
							updateCurrentPage();
						}
					}
				} else {
					Element childs = root_panel.element("childs");
					if (childs != null) {
						pageMap.put("page_1", childs);
					}
				}
			} else {
				checkBox.setSelected(true);
			}
			initWin(win, "page_1");
			button.setText("全选");
			for (XMLDto f : CompUtils.getFields()) {
				boolean select = true;
				if (!checkBox.isSelected()) {
					select = filedMap.containsKey(f.getValue("itemname").toUpperCase());
				}
				fieldModel.addRow(new Object[] { select, f });
			}
			win.setSelect(true);
			if (!backup && checkBox_1.isSelected()) {
				orignal = getStyleEle();
			}
			panel_6.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "初始话面板错误");
			logger.error(e.getMessage(), e);
		}
	}

	public void updateUIPropEditor() {
		table.updateUI();
	}

	public void updateUIComTree() {
		tree.updateUI();
	}

	// 初始化编辑器
	public void initPropEditor() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		selects = CompUtils.getSelect();
		if (!selects.isEmpty()) {
			if (selects.size() > 1) {
				// 选了多个
				Map<String, PropDto> propMap = selects.get(0).listProp();
				// 找属性最短的
				for (int i = 1; i < selects.size(); i++) {
					Map<String, PropDto> temp = selects.get(i).listProp();
					if (temp.size() < propMap.size()) {
						propMap = temp;
					}
				}
				ConcurrentLinkedQueue<PropDto> props = new ConcurrentLinkedQueue<PropDto>(propMap.values());
				for (IStyleCom sel : selects) {
					for (PropDto prop : props) {
						if (!sel.listProp().keySet().contains(prop.getPropname().toLowerCase())) {
							props.remove(prop);
						}
					}
				}
				for (PropDto p : props) {
					if (p.getPropname().toLowerCase().equals("name")) {
						continue;
					}
					model.addRow(new Object[] { p, null });
				}
			} else if (selects.size() == 1) {
				IStyleCom select = selects.get(0);
				Map<String, PropDto> map = select.listProp();
				for (PropDto p : map.values()) {
					model.addRow(new Object[] { p, null });
				}

			}
		}
	}

	private void reloadStyle() {
		toggleButton_1.setSelected(true);
		currentPage = "page_1";
		pageMap.clear();
		MainFrame.getInstance().sitchStyleMain();
	}

	public void stopPropTableEditor() {
		CompUtils.stopTabelCellEditor(table);
	}

	public void saveStyle() {
		CompUtils.stopTabelCellEditor(table);
		try {
			if (!MainFrame.getInstance().getLeftTree().CheckLock()) {
				return;
			}
			if (checkBox_1.isSelected()) {
				DataUtils.addData(orignal);
			}
			XMLDto style = CompUtils.getStyle();
			Document doc = DocumentHelper.createDocument();
			Element root = getStyleEle();
			if (checkBox_1.isSelected()) {
				orignal = root.createCopy();
			}
			root.addElement("classid").setText(style.getValue("classid"));
			root.addElement("styleid").setText(style.getValue("styleid"));
			doc.setRootElement(root);
			String width = doc.selectSingleNode("//root_panel//property//Width").getText();
			String height = doc.selectSingleNode("//root_panel//property//Height").getText();
			String xml = root.asXML();
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
			// 调用服务保存
			InvokerServiceUtils.updateStyle(style.getValue("styleid"), style.getValue("stylename"), width, xml, height);
			reAddOrigXML();
			GUIUtils.showMsg(MainFrame.getInstance(), "样式保存成功");
		} catch (Exception ex) {
			GUIUtils.showMsg(MainFrame.getInstance(), "样式保存错误");
			logger.error(ex.getMessage(), ex);
		}
	}

	private void reAddOrigXML() {
		orignXMLMap.clear();
		TNewPanel win = CompUtils.getWin();
		List<IStyleCom> cs = win.getChilds();
		cs.add(win);
		for (IStyleCom c : cs) {
			orignXMLMap.put(c, c.cover2Ele(null).asXML());
		}
	}

	private void initUICom() {

		// 添加控件编辑器
		final String resoureParent = "images" + File.separator + "editor" + File.separator + "style_";
		group = new ButtonGroup();
		boolean first = true;
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		JButton reBtn = new JButton("重新加载");
		reBtn.setForeground(Color.BLACK);
		reBtn.setPreferredSize(new Dimension(100, 30));
		reBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadStyle();
			}

		});
		p2.add(reBtn);
		JButton saveBtn = new JButton("保存");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveStyle();
			}
		});
		saveBtn.setPreferredSize(new Dimension(100, 30));
		p2.add(saveBtn);
		panel_2.add(p2, BorderLayout.EAST);
		if (!CompUtils.EDITORCOMS.isEmpty()) {
			JPanel p1 = new JPanel();
			p1.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel_2.add(p1, BorderLayout.CENTER);
			for (Object temp : CompUtils.EDITORCOMS.keySet()) {
				String key = (String) temp;
				final JToggleButton toggleButton = new JToggleButton();
				toggleButton.setName(key);
				toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				toggleButton.setPreferredSize(new java.awt.Dimension(40, 40));
				toggleButton.setToolTipText(CompUtils.EDITORCOMS.get(key).toString());
				if (!CommonUtils.isMac()) {
					toggleButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
				logger.debug(key);
				toggleButton.setIcon(new ImageIcon(resoureParent + toggleButton.getName() + ".png"));

				toggleButton.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						if (toggleButton.isSelected()) {
							toggleButton.setBackground(new Color(91, 91, 91));
						} else {
							toggleButton.setBackground(Color.WHITE);
						}

					}
				});
				toggleButton.setBackground(Color.WHITE);
				if (first) {
					toggleButton.setSelected(true);
					first = false;
				}

				toggleButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String name = ((JToggleButton) e.getSource()).getName();
						if (name.equals(type)) {
							resetComType();
							return;
						}
						type = name;
					}
				});
				group.add(toggleButton);
				p1.add(toggleButton);
			}
		}

		// 字段列表
		InputSearchPnl fieldSearchPnl = new InputSearchPnl(new SearchTarget() {
			@Override
			public void search(String txt) {
				if (fieldModel == null || fieldModel.getRowCount() <= 0) {
					return;
				}
				txt = txt.trim().toLowerCase();
				Map<String, Integer> map = new LinkedHashMap<String, Integer>();
				for (int i = 0; i < fieldModel.getRowCount(); i++) {
					Object temp = fieldModel.getValueAt(i, 1);
					if (temp == null) {
						continue;
					}
					if (temp.toString().toLowerCase().indexOf(txt) >= 0) {
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
							field_table.setRowSelectionInterval(value, value);
							CompUtils.setTableScrolVisible(field_table, value);
							InputSearchPnl.menu.setVisible(false);
						}
					});
					InputSearchPnl.menu.add(item);
					if (InputSearchPnl.menu.getPreferredSize().height > scrollPane_3.getHeight()) {
						InputSearchPnl.menu.add(new JMenuItem("......"));
						break;
					}
				}
			}
		}, 0, 25);
		panel_8.add(fieldSearchPnl, BorderLayout.NORTH);

		fieldModel = new DefaultTableModel(new String[] { "选择", "字段" }, 0) {
			private static final long serialVersionUID = 1L;

			private Class<?>[] types = new Class<?>[] { Boolean.class, String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column > 0) {
					return false;
				}
				return true;
			}
		};
		field_table.setModel(fieldModel);
		CompUtils.setFlxColumnWidth2Table(field_table, 0, 40);

		// 控件树
		// 图标
		DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
		render.setClosedIcon(TreeObj.getIcon(TreeIconEnum.CATALOG_IMG));
		render.setOpenIcon(TreeObj.getIcon(TreeIconEnum.CATALOG_OPEN_IMG));
		render.setLeafIcon(TreeObj.getIcon(TreeIconEnum.CIRCLE_GREEN));
		tree.setCellRenderer(render);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		InputSearchPnl comSearchPnl = new InputSearchPnl(new SearchTarget() {
			@Override
			public void search(String txt) {
				final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
				if (root == null) {
					return;
				}
				List<DefaultMutableTreeNode> result = CompUtils.searchTreeNodes(root, txt);
				if (result == null || result.isEmpty()) {
					return;
				}
				for (final DefaultMutableTreeNode node : result) {
					JMenuItem item = new JMenuItem(node.toString());
					item.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							TreeNode[] nodepaths = model.getPathToRoot(node);
							TreePath path = new TreePath(nodepaths);
							tree.setSelectionPath(path);
							tree.scrollPathToVisible(path);
							InputSearchPnl.menu.setVisible(false);
						}
					});
					InputSearchPnl.menu.add(item);
					if (InputSearchPnl.menu.getPreferredSize().height > tree.getHeight()) {
						InputSearchPnl.menu.add(new JMenuItem("......"));
						break;
					}
				}

			}
		}, 0, 25);
		panel_1.add(comSearchPnl, BorderLayout.NORTH);

		// 选择事件
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (buildTree) {
					return;
				}
				TreePath tp = e.getPath();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
				IStyleCom c = (IStyleCom) node.getUserObject();
				if (c.isSelect()) {
					return;
				}
				CompUtils.clearSelect();
				c.setSelect(true);
				((JComponent) c).repaint();
			}
		});
		// 属性编辑器
		table = new PropEditorTable();
		table.setRowHeight(20);
		scrollPane_1.setViewportView(table);
		DefaultTableModel model = new DefaultTableModel(new String[] { "属性", "值" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column != 1) {
					return false;
				}
				return true;
			}
		};
		table.setModel(model);
	}

	private void initWin(TNewPanel win, String pageIndex) {
		currentPage = pageIndex;
		Element page = pageMap.get(pageIndex);
		win.clear();
		orignXMLMap.clear();
		if (page != null && page.hasContent()) {
			List<Element> comEles = page.elements();
			for (Element c : comEles) {
				IStyleCom styleCom = CompUtils.createCom(c.attribute("classname").getText(), win, false, c);
				if (styleCom == null) {
					continue;
				}
				orignXMLMap.put(styleCom, styleCom.cover2Ele(null).asXML());
			}
		}
		orignXMLMap.put(win, win.cover2Ele(null).asXML());
		CompUtils.updateUI(win);
		buildComTree();
	}

	public boolean isChange() {
		TNewPanel win = CompUtils.getWin();
		if (win == null || orignXMLMap.isEmpty()) {
			return false;
		}
		List<IStyleCom> cs = win.getChilds();
		cs.add(win);
		if (orignXMLMap.size() != cs.size()) {
			return true;
		}
		for (IStyleCom c : cs) {
			if (!orignXMLMap.containsKey(c)) {
				return true;
			}
			String oXML = orignXMLMap.get(c);
			String cXML = c.cover2Ele(null).asXML();
			if (!oXML.equals(cXML)) {
				return true;
			}
		}
		return false;
	}

	private void getSelectNodes(DefaultMutableTreeNode node, List<DefaultMutableTreeNode> nodes) {
		IStyleCom com = (IStyleCom) node.getUserObject();
		if (com.isSelect()) {
			nodes.add(node);
		}
		if (node.getChildCount() > 0) {
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultMutableTreeNode c = (DefaultMutableTreeNode) node.getChildAt(i);
				getSelectNodes(c, nodes);
			}
		}
	}

	public void updateTree() {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
		getSelectNodes(root, nodes);
		TreePath[] paths = new TreePath[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			DefaultMutableTreeNode node = nodes.get(i);
			paths[i] = new TreePath(node.getPath());
		}
		if (paths.length < 1) {
			return;
		}
		tree.setSelectionPaths(paths);
		tree.scrollPathToVisible(paths[0]);
	}

	public void release() {
		CompUtils.stopTabelCellEditor(table);
		orignal = null;
		orignXMLMap.clear();
		pageMap.clear();
		panel_6.removeAll();
		pageMap.clear();
		fieldModel.setRowCount(0);
		toggleButton_1.setSelected(true);
	}
}
