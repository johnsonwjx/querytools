/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CommonParameterSetting.java
 *
 * Created on Dec 12, 2011, 8:11:38 PM
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.Do4objs;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

//通用参数设置

public class CommonParamEditor extends javax.swing.JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	// 组件信息选择
	private ModulesSelectEditor moduleselectpnl;
	private JDialog dialog;
	private javax.swing.JButton jButton2;

	private javax.swing.JButton jButton3;

	private javax.swing.JComboBox jComboBox1;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JPanel jPanel14;

	private javax.swing.JPanel jPanel2;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JTable jTable1;

	private javax.swing.JTextField moduleIdTxt;

	private Map<Integer, Map<String, String>> paramMap = new HashMap<Integer, Map<String, String>>();
	private JButton TextButton = new JButton("...");
	private JSeparator separator;
	private DefaultTableModel table1Model;
	private JPanel panel;
	private final int index_param = 2;
	private final int index_label = 3;
	private final int index_name = 1;
	private List<XMLDto> fields = null;
	private Map<String, String> props;
	private boolean submit = false;
	private XMLDto module;
	private TreeSelectPnl<XMLDto> stylePnl = null;
	private TreeSelectPnl<XMLDto> formPnl = null;
	private TreeSelectPnl<XMLDto> flowPnl = null;

	public CommonParamEditor() {
		initComponents();
		this.setPreferredSize(new Dimension(838, 589));
		jComboBox1.setSelectedItem(null);
		setLayout(new BorderLayout(0, 0));
		jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setText("组件ID:");
		jPanel2.add(jLabel1);
		jLabel1.setName("jLabel1"); // NOI18N
		moduleIdTxt = new javax.swing.JTextField();
		moduleIdTxt.setEditable(false);
		moduleIdTxt.setColumns(25);
		jPanel2.add(moduleIdTxt);

		moduleIdTxt.setName("moduleIdTxt");

		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(20, 0));
		jPanel2.add(separator);
		jLabel2 = new javax.swing.JLabel();
		this.add(jPanel2, BorderLayout.NORTH);
		jLabel2.setText("通用选择模块:");
		jPanel2.add(jLabel2);
		jLabel2.setName("jLabel2"); // NOI18N
		jPanel2.add(jComboBox1);
		jPanel14 = new javax.swing.JPanel();
		jPanel14.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		add(jPanel14, BorderLayout.SOUTH);
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();

		jPanel14.setName("jPanel14"); // NOI18N

		jButton2.setText("确定");
		jButton2.setName("jButton2"); // NOI18N
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				save();
			}
		});

		jButton3.setText("取消");
		jButton3.setName("jButton3"); // NOI18N
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
		jPanel14.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		jPanel14.add(jButton2);
		jPanel14.add(jButton3);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u5165\u53E3\u53C2\u6570\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		jScrollPane1 = new javax.swing.JScrollPane();
		panel.add(jScrollPane1);
		jScrollPane1.setViewportBorder(null);
		jTable1 = new javax.swing.JTable() {

			@Override
			public boolean isCellEditable(int row, int column) {
				return index_param == column;
			}

			private static final long serialVersionUID = 1L;

			@Override
			public TableCellEditor getCellEditor(int row, int column) {
				if (column == index_param && (jComboBox1.getSelectedIndex() > 0 || paramMap.containsKey(row))) {
					return new ButtonCellEditor(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int row = jTable1.getSelectedRow();
							String value = (String) jTable1.getValueAt(row, jTable1.getSelectedColumn());
							if (jComboBox1.getSelectedIndex() == 1) {
								// 选择工作流
								if (flowPnl == null) {
									GUIUtils.showMsg(dialog, "请稍等");
									return;
								}
								Map<String, String> propMap = new HashMap<String, String>();
								propMap.put("key", "id");
								propMap.put("value", value);
								flowPnl.edit(dialog, propMap);
								if (flowPnl.isChange()) {
									XMLDto dto = flowPnl.getSelect();
									if (dto == null) {
										jTable1.setValueAt("", row, index_param);
										jTable1.setValueAt("", row, index_label);
									} else {
										jTable1.setValueAt(dto.getValue("id"), row, index_param);
										jTable1.setValueAt(dto.getValue("name"), row, index_label);
									}
								}
							} else if (jComboBox1.getSelectedIndex() == 2) {
								if (formPnl == null) {
									GUIUtils.showMsg(dialog, "请稍等");
									return;
								}
								Map<String, String> propMap = new HashMap<String, String>();
								propMap.put("key", "styleid");
								propMap.put("value", value);
								formPnl.edit(dialog, propMap);
								if (formPnl.isChange()) {
									XMLDto dto = formPnl.getSelect();
									if (dto == null) {
										jTable1.setValueAt("", row, index_param);
										jTable1.setValueAt("", row, index_label);
									} else {
										jTable1.setValueAt(dto.getValue("styleid"), row, index_param);
										jTable1.setValueAt(dto.getValue("name"), row, index_label);
									}
								}
							} else if (jComboBox1.getSelectedIndex() == 3) {
								// 选择查询样式
								if (stylePnl == null) {
									GUIUtils.showMsg(dialog, "请稍等");
									return;
								}
								Map<String, String> propMap = new HashMap<String, String>();
								propMap.put("key", "id");
								propMap.put("value", value);
								stylePnl.edit(dialog, propMap);
								if (stylePnl.isChange()) {
									XMLDto dto = stylePnl.getSelect();
									if (dto == null) {
										jTable1.setValueAt("", row, index_param);
										jTable1.setValueAt("", row, index_label);
									} else {
										jTable1.setValueAt(dto.getValue("id"), row, index_param);
										jTable1.setValueAt(dto.getValue("stylename"), row, index_label);
									}
								}
							} else {
								Map<String, String> itemParams = paramMap.get(row);
								if (itemParams.size() <= 0) {
									GUIUtils.showMsg(dialog, "初始化参数个数为0个");
									return;
								}
								if (itemParams.containsKey("V")) {
									XMLDto itemValue = null;
									if (!CommonUtils.isStrEmpty(value)) {
										itemValue = CommonUtils.getXmlDto(fields, "itemname", value);
									}
									ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
									pnl.setValue(itemValue);
									pnl.edit(dialog, null);
									if (pnl.isChange()) {
										itemValue = pnl.getSelect();
										if (itemValue != null) {
											jTable1.setValueAt(itemValue.getValue("itemname"), row, index_param);
											jTable1.setValueAt(itemValue.getValue("itemlabel"), row, index_label);
										} else {
											jTable1.setValueAt("", row, index_param);
											jTable1.setValueAt("", row, index_label);
										}
									}
								} else {
									Map.Entry<String, String> itemValue = null;
									List<Map.Entry<String, String>> paramsItem = new ArrayList<Map.Entry<String, String>>();
									for (Map.Entry<String, String> entry : itemParams.entrySet()) {
										paramsItem.add(entry);
										if (!CommonUtils.isStrEmpty(value) && entry.getKey().equals(value)) {
											itemValue = entry;
										}
									}
									if (itemParams.containsKey(value)) {
										value = value + "=" + itemParams.get(value);
									}
									ObjectSelectPnl<Map.Entry<String, String>> pnl = new ObjectSelectPnl<Map.Entry<String, String>>(paramsItem);
									pnl.setValue(itemValue);
									pnl.edit(dialog, null);
									if (pnl.isChange()) {
										itemValue = pnl.getSelect();
										if (!pnl.isNull()) {
											jTable1.setValueAt(itemValue.getKey(), row, index_param);
											jTable1.setValueAt(itemValue.getValue(), row, index_label);
										} else {
											jTable1.setValueAt("", row, index_param);
											jTable1.setValueAt("", row, index_label);
										}
									}

								}
							}
							CompUtils.stopTabelCellEditor(jTable1);
						}
					}, false);
				} else {
					return new DefaultCellEditor(new JTextField());
				}
			}

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				TableCellEditor editor = getCellEditor(row, column);
				if (editor instanceof ButtonCellEditor) {
					ButtonCellEditor btnEditor = (ButtonCellEditor) editor;
					return btnEditor.getTableCellRenderer();
				} else {
					return new DefaultTableCellRenderer();
				}
			}
		};

		jScrollPane1.setName("jScrollPane1"); // NOI18N
		jTable1.setModel(table1Model);
		jTable1.setName("jTable1"); // NOI18N
		jTable1.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(jTable1);
		initJTextButton();
	}

	private void addNextChild(DefaultTreeModel model, DefaultMutableTreeNode parentNode) {
		Enumeration<DefaultMutableTreeNode> children = parentNode.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode c = children.nextElement();
			if (c.isLeaf()) {
				XMLDto dto = (XMLDto) c.getUserObject();
				if (!CommonUtils.isStrEmpty(dto.getValue("catalogcode"))) {
					continue;
				}
				List<XMLDto> classes = InvokerServiceUtils.getFlowClasses(dto.getValue("code"), "");
				if (classes == null || classes.isEmpty()) {
					continue;
				}
				for (XMLDto d : classes) {
					c.add(new DefaultMutableTreeNode(d));
				}
			}
		}
		model.reload(parentNode);
	}

	private void initComponents() {
		jPanel2 = new javax.swing.JPanel();
		jComboBox1 = new javax.swing.JComboBox(new String[] { "", "选择工作流", "选择表单样式", "选择查询样式" });
		jComboBox1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int index = jComboBox1.getSelectedIndex();
					if (index == 1) {
						if (flowPnl == null) {
							MainFrame.getInstance().busyDoing(new Do4objs() {

								@Override
								public void do4ojbs(Object... obj1) {
									JTree tree = new JTree();
									DefaultMutableTreeNode root = new DefaultMutableTreeNode("工作流");
									final DefaultTreeModel model = new DefaultTreeModel(root);
									tree.setModel(model);
									List<XMLDto> catalogs = InvokerServiceUtils.getFlowCatalogs();
									if (catalogs == null) {
										GUIUtils.showMsg(dialog, "工作流目录为空");
										return;
									}
									CompUtils.buildTree(catalogs, root, "code", null);
									tree.addTreeExpansionListener(new TreeExpansionListener() {

										@Override
										public void treeExpanded(TreeExpansionEvent event) {
											DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
											addNextChild(model, node);
										}

										@Override
										public void treeCollapsed(TreeExpansionEvent event) {
											// TODO Auto-generated method stub

										}
									});
									flowPnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {

										@Override
										public String validate(XMLDto obj) {
											return CommonUtils.isStrEmpty(obj.getValue("catalogcode")) ? "请选择工作流类" : null;
										}
									});

								}
							}, null, false);
						}
					} else if (index == 2) {
						if (formPnl == null) {
							MainFrame.getInstance().busyDoing(new Do4objs() {

								@Override
								public void do4ojbs(Object... obj1) {
									try {
										JTree tree = new JTree();
										DefaultMutableTreeNode root = new DefaultMutableTreeNode("表单样式");
										DefaultTreeModel model = new DefaultTreeModel(root);
										tree.setModel(model);
										List<XMLDto> styleList = InvokerServiceUtils.getformClassStyle("", "");
										List<XMLDto> catalogList = InvokerServiceUtils.formCatalogGet("", "");
										List<XMLDto> classList = InvokerServiceUtils.getFormClasses("", "", "", "", "");
										Map<String, DefaultMutableTreeNode> catalogNodes = CompUtils.buildTree(catalogList, root, "code", "id");
										Map<String, DefaultMutableTreeNode> classNodes = CompUtils.buildTree(root, catalogNodes, classList, "catalogid", "id");
										CompUtils.buildTree(root, classNodes, styleList, "classid", null);
										formPnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {

											@Override
											public String validate(XMLDto obj) {
												return CommonUtils.isStrEmpty(obj.getValue("styleid")) ? "请选择样式" : null;
											}
										});
									} catch (Exception e2) {
										GUIUtils.showMsg(dialog, "建立表单样式树失败");
										logger.error(e2.getMessage(), e2);
										throw new RuntimeException(e2);
									}

								}
							}, null, false);
						}
					} else if (index == 3) {
						MainFrame.getInstance().busyDoing(new Do4objs() {

							@Override
							public void do4ojbs(Object... obj1) {
								JTree tree;
								tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {

									@Override
									public String validate(XMLDto obj) {
										String dataType = obj.getValue("dataType");
										if ("printstylecatalog".equals(dataType) || "condistyle".equals(dataType)) {
											return "remove";
										}
										if ("querystylecatalog".equals(dataType)) {
											return "ingone";
										}
										return null;
									}
								}, "样式", true);
								stylePnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {
									@Override
									public String validate(XMLDto obj) {
										return CommonUtils.isStrEmpty(obj.getValue("styleid")) ? "请选择样式" : null;
									}
								});

							}
						}, null, false);
					}
					jTable1.updateUI();
				}

			}
		});
		table1Model = new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "参数标签", "参数名称", "参数值", "参数值说明" }) {
			private static final long serialVersionUID = 1L;
			Class<?>[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};
		jPanel2.setName("jPanel2"); // NOI18N
		jComboBox1.setName("jComboBox1");
	}

	private void initData() {
		String id = props.get("moduleid");
		if (CommonUtils.isStrEmpty(id)) {
			return;
		}
		try {
			module = InvokerServiceUtils.getModule(id);
		} catch (Exception e) {
			GUIUtils.showMsg(null, "获取Module失败");
			logger.error(e.getMessage(), e);
			return;
		}
		if (module == null) {
			return;
		}
		initByModel(module);
		String inparam = props.get("inparam");
		if (CommonUtils.isStrEmpty(inparam)) {
			return;
		}
		String[] initItems = inparam.split(";");
		if (initItems.length <= 0) {
			return;
		}
		for (String initItem : initItems) {
			String[] keyValue = initItem.split("=");
			if (keyValue.length < 2) {
				continue;
			}
			String key = keyValue[0];
			String value = keyValue[1];
			if (CommonUtils.isStrEmpty(value)) {
				continue;
			}
			for (int i = 0; i < table1Model.getRowCount(); i++) {
				String paramName = (String) table1Model.getValueAt(i, index_name);
				if (key.equalsIgnoreCase(paramName)) {
					table1Model.setValueAt(value, i, index_param);
					if (paramMap.containsKey(i)) {
						Map<String, String> itemMap = paramMap.get(i);
						if (itemMap.containsKey("V")) {
							XMLDto field = CommonUtils.getXmlDto(fields, "itemname", value);
							if (field == null) {
								break;
							}
							table1Model.setValueAt(field.getValue("itemlabel"), i, index_label);
						} else {
							String label = itemMap.get(value);
							table1Model.setValueAt(label, i, index_label);
						}
					}
				}
			}
		}
	}

	private void initJTextButton() {
		moduleIdTxt.setLayout(new BorderLayout());
		TextButton.setText(".....");
		TextButton.setBorder(javax.swing.BorderFactory.createLineBorder(Color.black));
		// 文本按钮，点击事件
		moduleIdTxt.add(BorderLayout.EAST, TextButton);
		TextButton.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Map<String, String> props = new HashMap<String, String>();
				props.put("id", moduleIdTxt.getText().trim());
				moduleselectpnl.edit(dialog, props);
				if (!moduleselectpnl.isSubmit()) {
					return;
				}
				module = moduleselectpnl.getSelect();
				initByModel(module);
			}

		});
	}

	private void initByModel(XMLDto module) {
		table1Model.setRowCount(0);
		moduleIdTxt.setText("");
		if (module == null) {
			return;
		}
		moduleIdTxt.setText(module.getValue("id"));
		String inParam = module.getValue("inparam");
		if (CommonUtils.isStrEmpty(inParam)) {
			return;
		}
		for (int i = 0; !inParam.equals(""); i++) {
			int index1 = inParam.indexOf("[");
			int index2 = inParam.indexOf("]");
			String substr = inParam.substring(index1 + 1, index2);
			inParam = inParam.substring(index2 + 1);
			String[] params = substr.split("[|]");
			if (params[0].indexOf("module_id=") >= 0) {
				Object[] oj = { params[1], params[0], params[0].split("=")[1], "" };
				if (params.length >= 4) {
					if (params[3].startsWith("module_id="))// 如果是已经定义好的了就不用插入
					{
						continue;
					}
				}
				table1Model.addRow(oj);
			} else {
				Object[] oj = { params[1], params[0], "", "" };
				if (params.length >= 4) {
					if (params[3].startsWith("module_id="))// 如果是已经定义好的了就不用插入
					{
						continue;
					}
				}
				table1Model.addRow(oj);
			}
			if (params.length >= 4 && !CommonUtils.isStrEmpty(params[3])) {
				String paramStr = params[3];
				Map<String, String> itemsMap = new LinkedHashMap<String, String>();
				if ("V".equalsIgnoreCase(paramStr)) {
					itemsMap.put("V", "V");
				} else {
					String[] itemParams = paramStr.split(";");
					if (itemParams.length > 0) {
						for (String itemParam : itemParams) {
							String[] keyValue = itemParam.split("=");
							if (keyValue.length >= 2) {
								itemsMap.put(keyValue[1], keyValue[0]);
							}
						}
					}

				}
				paramMap.put(i, itemsMap);
			}
			if ("26020020".equals(moduleIdTxt.getText())) {
				Map<String, String> itemsMap = new LinkedHashMap<String, String>();
				itemsMap.put("V", "V");
				paramMap.put(i, itemsMap);
			}
		}
	}

	private void save() {// GEN-FIRST:event_jButton2ActionPerformed
		CompUtils.stopTabelCellEditor(jTable1);
		StringBuffer inParam = new StringBuffer();
		StringBuilder fields = new StringBuilder();
		int row = jTable1.getModel().getRowCount();
		for (int i = 0; i < row; i++) {
			String key = (String) jTable1.getValueAt(i, index_name);
			String value = (String) jTable1.getValueAt(i, index_param);
			inParam.append(key).append("=").append(value);
			inParam.append(";");

		}
		inParam.append("module_id=").append(moduleIdTxt.getText()).append(";");
		inParam.append(fields.toString()).append(";moduleId=").append(moduleIdTxt.getText());
		props.put("inparam", inParam.toString());
		props.put("moduleid", moduleIdTxt.getText());
		submit = true;
		dialog.dispose();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		fields = CompUtils.getFields();
		this.props = props;
		initData();
		MainFrame.getInstance().busyDoing(new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				moduleselectpnl = new ModulesSelectEditor();
			}
		}, null, false);

		dialog = GUIUtils.getDialog(owner, "通用参数设置", this);
		dialog.setVisible(true);

	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

}
