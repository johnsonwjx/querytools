package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.PropDto;
import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DynamicTitleEditor;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ListChooseListPnl;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.main.MainFrame;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class ToCustomqueryEditor extends javax.swing.JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private final Map<String, Component> allcomp = new HashMap<String, Component>();
	private String[] tableHeader = { "序号", "目标字段", "操作符", "当前查询类字段", "固定值", "字段位置", "值为空强制转为YFNULL" };
	private List<XMLDto> fields = CompUtils.getFields();
	private List<XMLDto> targets = null;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private DefaultTableModel model;
	public static List<XMLDto> opers = null;
	private final int index_sortnum = 0;
	private final int index_target = 1;
	private final int index_oper = 2;
	private final int index_curent = 3;
	private final int index_fix = 4;
	private final int index_location = 5;
	private final int index_yfnull = 6;
	private final Map<String, String> locations = new LinkedHashMap<String, String>();
	private JComboBox comboBox;
	private List<XMLDto> mergercolDtos;
	static {
		opers = new LinkedList<XMLDto>();
		opers.add(new XMLDto("=(等于 例: code='01')", "="));
		opers.add(new XMLDto("<>(不等于 例: code<>'01')", "&lt;&gt;"));
		opers.add(new XMLDto(">(大于 例: code>'01')", "&gt;"));
		opers.add(new XMLDto("<(小于 例: code<'01')", "&lt;"));
		opers.add(new XMLDto(">=(大于等于 例: code>='01')", "&gt;="));
		opers.add(new XMLDto("<=(小于等于 例: code<='01')", "&lt;="));
		opers.add(new XMLDto("llike(左匹配 例: code like '23%')", "Llike"));
		opers.add(new XMLDto("rlike(右匹配 例: code like '%23')", "Rlike"));
		opers.add(new XMLDto("alike(部分匹配 例: code like '%23%')", "Alike"));
		opers.add(new XMLDto("notlike(不匹配 例: code not like '23%')", "notlike"));
		opers.add(new XMLDto("in(在于 例: code in ('01','02))", "in"));
		opers.add(new XMLDto("notin(不在于 例: code not in ('01','02'))", "notin"));
		opers.add(new XMLDto("yfnull(为空或为NULL 例: yfnull(code))", "YFNULL"));
		opers.add(new XMLDto("yfnotnull(不为空也不为NULL  例:  yfnotnull(code))", "yfnotnull"));
	}

	public ToCustomqueryEditor() {
		initComponents();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 270));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setBounds(0, 0, 616, 273);
		panel.add(jPanel1);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(32, 24, 52, 16);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(18, 60, 66, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(19, 94, 65, 16);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(19, 128, 65, 16);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(6, 164, 78, 16);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(6, 200, 78, 16);
		stylename = new javax.swing.JTextField();
		stylename.setBounds(90, 18, 392, 28);
		styleid = new javax.swing.JTextField();
		styleid.setBounds(90, 54, 392, 28);
		classid = new javax.swing.JTextField();
		classid.setBounds(90, 88, 392, 28);
		rebuildtreeinfo = new javax.swing.JTextField();
		rebuildtreeinfo.setBounds(90, 122, 392, 28);
		autodescinfo = new javax.swing.JTextField();
		autodescinfo.setBounds(90, 194, 392, 28);
		autocaptioninfo = new javax.swing.JTextField();
		autocaptioninfo.setBounds(90, 158, 392, 28);
		chooseStyleButton = new javax.swing.JButton();
		chooseStyleButton.addActionListener(new ActionListener() {
			private TreeSelectPnl<XMLDto> pnl;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (pnl == null) {
					JTree tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {
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
					}, "结果样式", true);
					pnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {

						@Override
						public String validate(XMLDto obj) {
							return "resultstyle".equalsIgnoreCase(obj.getValue("dataType")) ? null : "请选择结果样式";
						}

					});

				}

				Map<String, String> prop = new HashMap<String, String>();
				prop.put("key", "styleid");
				prop.put("value", styleid.getText());
				pnl.edit(dialog, prop);
				if (!pnl.isChange()) {
					return;
				}
				setStyle(pnl.getSelect());
			}
		});
		chooseStyleButton.setBounds(488, 19, 122, 29);
		activeTitleButton = new javax.swing.JButton();
		activeTitleButton.setBounds(488, 159, 122, 29);
		jButton3 = new javax.swing.JButton();
		jButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComIdExpEditor editor = new ComIdExpEditor();
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("value", autodescinfo.getText());
				temp.put("addCurDesc", "true");
				editor.edit(dialog, temp);
				autodescinfo.setText(temp.get("value"));
			}
		});
		jButton3.setBounds(488, 195, 122, 29);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("穿透查询参数设置"));
		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("目标样式");
		jLabel1.setName("jLabel1"); // NOI18N

		jLabel2.setText("目标样式ID");
		jLabel2.setName("jLabel2"); // NOI18N

		jLabel3.setText("目标查询类");
		jLabel3.setName("jLabel3"); // NOI18N

		jLabel4.setText("重建树设置");
		jLabel4.setName("jLabel4"); // NOI18N

		jLabel5.setText("动态标题设置");
		jLabel5.setName("jLabel5"); // NOI18N

		jLabel6.setText("动态说明设置");
		jLabel6.setName("jLabel6"); // NOI18N

		stylename.setEditable(false);
		stylename.setName("stylename"); // NOI18N

		styleid.setEditable(false);
		styleid.setName("styleid"); // NOI18N

		classid.setEditable(false);
		classid.setName("classid"); // NOI18N

		rebuildtreeinfo.setName("rebuildtreeinfo"); // NOI18N

		autodescinfo.setName("autodescinfo");
		autocaptioninfo.setName("autocaptioninfo"); // NOI18N

		chooseStyleButton.setText("选择查询样式");
		chooseStyleButton.setName("chooseStyleButton");

		activeTitleButton.setText("动态标题设置");
		activeTitleButton.setName("activeTitleButton"); // NOI18N
		activeTitleButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				activeTitleButtonActionPerformed(evt);
			}
		});

		jButton3.setText("动态说明设置");
		jButton3.setName("jButton3");
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel4);
		jPanel1.add(jLabel3);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel6);
		jPanel1.add(jLabel5);
		jPanel1.add(styleid);
		jPanel1.add(classid);
		jPanel1.add(rebuildtreeinfo);
		jPanel1.add(autocaptioninfo);
		jPanel1.add(autodescinfo);
		jPanel1.add(stylename);
		jPanel1.add(chooseStyleButton);
		jPanel1.add(activeTitleButton);
		jPanel1.add(jButton3);

		JLabel label = new JLabel("\u6837\u5F0F\u65B0\u6807\u9898");
		label.setBounds(16, 234, 65, 16);
		jPanel1.add(label);

		textField = new JTextField();
		textField.setName("autodescinfo");
		textField.setBounds(90, 234, 392, 28);
		jPanel1.add(textField);

		JButton button = new JButton("\u91CD\u5EFA\u6811\u6761\u4EF6");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RebulidTreeCondiEditor pnl = new RebulidTreeCondiEditor();
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("value", rebuildtreeinfo.getText());
				pnl.edit(dialog, temp);
				if (!pnl.isSubmit()) {
					return;
				}
				rebuildtreeinfo.setText(temp.get("value"));
			}
		});
		button.setBounds(493, 123, 117, 29);
		jPanel1.add(button);

		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(616, 0, 334, 273);
		panel.add(jPanel2);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setBounds(12, 24, 52, 16);
		jLabel8 = new javax.swing.JLabel();
		jLabel8.setBounds(12, 58, 52, 16);
		addoldcondi = new javax.swing.JCheckBox();
		addoldcondi.setBounds(19, 136, 136, 23);
		addoldtreeCondi = new javax.swing.JCheckBox();
		addoldtreeCondi.setBounds(179, 136, 149, 23);
		relationTableName = new javax.swing.JTextField();
		relationTableName.setBounds(70, 18, 254, 28);
		relationFieldName = new javax.swing.JTextField();
		relationFieldName.setBounds(70, 52, 254, 28);
		notfields = new javax.swing.JTextField();
		notfields.setBounds(12, 185, 162, 28);
		setFieldsButton = new javax.swing.JButton();
		setFieldsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (targets == null || targets.isEmpty()) {
					GUIUtils.showMsg(dialog, "目标查询类字段为空");
					return;
				}
				FieldMappingEditor editor = new FieldMappingEditor(targets);
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("value", condiMapFieldStr.getText());
				editor.edit(dialog, temp);
				if (editor.isSubmit()) {
					condiMapFieldStr.setText(temp.get("value"));
				}
			}
		});
		setFieldsButton.setBounds(178, 86, 150, 29);
		notFieldsButton = new javax.swing.JButton();
		notFieldsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Collection<XMLDto> values = new ArrayList<XMLDto>();
				String temp = notfields.getText();
				String[] split = temp.split(",");
				if (split.length > 0) {
					for (String s : split) {
						XMLDto obj = CommonUtils.getXmlDto(fields, "itemname", s);
						if (obj != null) {
							values.add(obj);
						}
					}
				}
				ListChooseListPnl<XMLDto> pnl = new ListChooseListPnl<XMLDto>(dialog, "选择", fields, null);
				pnl.setValues(values);
				pnl.edit(dialog, null);
				if (!pnl.isSubmit()) {
					return;
				}
				if (pnl.getValues() == null || pnl.getValues().isEmpty()) {
					notfields.setText("");
				} else {
					StringBuilder sb = new StringBuilder();
					for (XMLDto dto : pnl.getValues()) {
						sb.append(dto.getValue("itemname")).append(",");
					}
					sb.deleteCharAt(sb.length() - 1);
					notfields.setText(sb.toString());
				}
			}
		});
		notFieldsButton.setBounds(186, 184, 148, 29);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("参数设置"));
		jPanel2.setName("jPanel2"); // NOI18N

		jLabel7.setText("关联表名");
		jLabel7.setName("jLabel7"); // NOI18N

		jLabel8.setText("关联字段");
		jLabel8.setName("jLabel8"); // NOI18N

		addoldcondi.setText("\u52A0\u8F7D\u5F53\u524D\u6761\u4EF6");
		addoldcondi.setName("addoldcondi"); // NOI18N

		addoldtreeCondi.setText("\u52A0\u8F7D\u5F53\u524D\u3010\u6811\u3011\u6761\u4EF6");
		addoldtreeCondi.setName("addoldtreeCondi"); // NOI18N

		relationTableName.setName("relationTableName"); // NOI18N

		relationFieldName.setName("relationFieldName"); // NOI18N

		notfields.setName("notfields"); // NOI18N

		setFieldsButton.setText("设置字段对应");
		setFieldsButton.setName("setFieldsButton"); // NOI18N

		notFieldsButton.setText("不作为条件的字段");
		notFieldsButton.setName("notFieldsButton");
		jPanel2.setLayout(null);
		jPanel2.add(addoldtreeCondi);
		jPanel2.add(addoldcondi);
		jPanel2.add(setFieldsButton);
		jPanel2.add(jLabel7);
		jPanel2.add(relationTableName);
		jPanel2.add(jLabel8);
		jPanel2.add(relationFieldName);
		jPanel2.add(notfields);
		jPanel2.add(notFieldsButton);

		condiMapFieldStr = new JTextField();
		condiMapFieldStr.setName("notfields");
		condiMapFieldStr.setBounds(6, 85, 165, 28);
		jPanel2.add(condiMapFieldStr);

		comboBox = new JComboBox();
		comboBox.setBounds(166, 225, 158, 27);
		jPanel2.add(comboBox);

		JLabel label_1 = new JLabel("\u6750\u6599\u89C4\u683C\u5217\u663E\u793A\u63A7\u5236\uFF1A");
		label_1.setBounds(22, 229, 133, 16);
		jPanel2.add(label_1);
		init();
	}

	private void init() {
		mergercolDtos = new ArrayList<XMLDto>();
		mergercolDtos.add(new XMLDto("", ""));
		mergercolDtos.add(new XMLDto("只显示规格型号", "0"));
		mergercolDtos.add(new XMLDto("显示规格下所有", "1"));

		locations.put("0", "表格内");
		locations.put("1", "表格外");

		allcomp.put("condiexpress", condiexpress);
		allcomp.put("styleid", styleid);
		allcomp.put("stylename", stylename);
		allcomp.put("classid", classid);
		allcomp.put("rebuildtreeinfo", rebuildtreeinfo);
		allcomp.put("autocaptioninfo", autocaptioninfo);
		allcomp.put("autodescinfo", autodescinfo);
		allcomp.put("newcaption", textField);
		allcomp.put("relationTableName", relationTableName);
		allcomp.put("relationFieldName", relationFieldName);
		allcomp.put("addoldcondi", addoldcondi);
		allcomp.put("addoldtreeCondi", addoldtreeCondi);
		allcomp.put("notfields", notfields);
		allcomp.put("condiMapFieldStr", condiMapFieldStr);
		allcomp.put("mergercol", comboBox);

		comboBox.setModel(new DefaultComboBoxModel(mergercolDtos.toArray()));
		comboBox.setSelectedIndex(0);

		// table
		model = new DefaultTableModel(tableHeader, 0) {
			private static final long serialVersionUID = 1L;
			private Class<?>[] cClass = new Class<?>[] { Integer.class, XMLDto.class, XMLDto.class, XMLDto.class, String.class, String.class, Boolean.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return cClass[columnIndex];
			}
		};
		condiexpress.setModel(model);
		CompUtils.setTableWdiths(condiexpress, 0.1, null, 0.3, null, null, 0.15, 0.3);
		ButtonCellEditor cellEditor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(condiexpress);
				int row = condiexpress.getSelectedRow();
				int column = condiexpress.getSelectedColumn();
				Collection<XMLDto> all = null;
				ObjectSelectPnl<XMLDto> pnl = null;
				if (column == index_target) {
					all = targets;
					pnl = new ObjectSelectPnl<XMLDto>(all);
				} else if (column == index_oper) {
					all = opers;
					pnl = new ObjectSelectPnl<XMLDto>(all);
				} else if (column == index_curent) {
					all = fields;
					pnl = CompUtils.getFieldsPnl();
				}
				if (all != null && !all.isEmpty()) {
					XMLDto value = CompUtils.getCellValue(XMLDto.class, condiexpress, row, column);
					if (value != null && value.toString().indexOf("未知") != -1) {
						value = CommonUtils.getXmlDto(all, "itemname", value.getValue("itemname"));
					}
					pnl.setValue(value);
					pnl.edit(dialog, null);
					if (pnl.isSubmit()) {
						condiexpress.setValueAt(pnl.getSelect(), row, column);
					}
				}
				CompUtils.stopTabelCellEditor(condiexpress);
			}
		}, false);
		TableColumnModel cm = condiexpress.getColumnModel();
		TableColumn c1 = cm.getColumn(index_target);
		TableColumn c2 = cm.getColumn(index_oper);
		TableColumn c3 = cm.getColumn(index_curent);
		c1.setCellEditor(cellEditor);
		c2.setCellEditor(cellEditor);
		c3.setCellEditor(cellEditor);
		c1.setCellRenderer(cellEditor.getTableCellRenderer());
		c2.setCellRenderer(cellEditor.getTableCellRenderer());
		c3.setCellRenderer(cellEditor.getTableCellRenderer());
		TableColumn location = cm.getColumn(index_location);
		JComboBox comboBox = new JComboBox(locations.values().toArray());
		location.setCellEditor(new DefaultCellEditor(comboBox));
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(970, 722));
		jPanel3 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		condiexpress = new javax.swing.JTable();
		jPanel4 = new javax.swing.JPanel();
		jPanel4.setPreferredSize(new Dimension(0, 50));
		cancelButton = new javax.swing.JButton();
		cancelButton.setBounds(878, 8, 75, 29);
		addButton = new javax.swing.JButton();
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Object[] { model.getRowCount() + 1, null, null, null, "", locations.values().iterator().next() });
			}
		});
		addButton.setBounds(16, 9, 65, 29);
		delButton = new javax.swing.JButton();
		delButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(condiexpress);
				int index = condiexpress.getSelectedRow();
				if (index < 0) {
					return;
				}
				model.removeRow(index);
				if (model.getRowCount() > 0) {
					if (index > 0) {
						index--;
					}
					condiexpress.setRowSelectionInterval(index, index);
				}
			}
		});
		delButton.setBounds(82, 9, 54, 29);
		jLabel10 = new javax.swing.JLabel();
		jLabel10.setBounds(374, 6, 243, 48);

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("条件相关设置"));
		jPanel3.setName("jPanel3"); // NOI18N

		jScrollPane1.setName("jScrollPane1"); // NOI18N

		condiexpress.setName("condiexpress"); // NOI18N
		condiexpress.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(condiexpress);

		jPanel4.setName("jPanel4"); // NOI18N

		cancelButton.setText("取消");
		cancelButton.setName("cancelButton"); // NOI18N
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		addButton.setFont(new java.awt.Font("Lucida Grande", 1, 13));
		addButton.setText("\u589E\u52A0");

		delButton.setFont(new java.awt.Font("Lucida Grande", 1, 13));
		delButton.setText("\u5220\u9664");
		setLayout(new BorderLayout(0, 0));

		jLabel10.setForeground(new java.awt.Color(204, 0, 0));
		jLabel10.setText("<html>注:<br/>1:当字段与固定之都不为空时,以字段优先<br/>2:当固定值为多个值时,值与值中间用*分开</html>"); // NOI18N
		jLabel10.setName("jLabel10");
		add(jPanel4, BorderLayout.SOUTH);
		jPanel4.setLayout(null);
		jPanel4.add(addButton);
		jPanel4.add(delButton);
		jPanel4.add(jLabel10);
		jPanel4.add(cancelButton);

		JButton button = new JButton("\u6392\u5E8F");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.sortTable(condiexpress, 0);
			}
		});
		button.setBounds(136, 9, 65, 29);
		jPanel4.add(button);

		JButton button_1 = new JButton("\u5F53\u524D\u987A\u5E8F\u8BBE\u7F6E\u5E8F\u53F7");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < model.getRowCount(); i++) {
					model.setValueAt(i + 1, i, 0);
				}
			}
		});
		button_1.setBounds(213, 9, 137, 29);
		jPanel4.add(button_1);

		JButton button_2 = new JButton("\u786E\u5B9A");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		button_2.setBounds(764, 9, 92, 29);
		jPanel4.add(button_2);
		add(jPanel3);
		jPanel3.setLayout(new BorderLayout(0, 0));
		jPanel3.add(jScrollPane1);
	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		this.dialog.dispose();
	}

	private void activeTitleButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_activeTitleButtonActionPerformed
		DynamicTitleEditor editor = new DynamicTitleEditor();
		PropDto prop = new PropDto();
		prop.setValue(autocaptioninfo.getText());
		editor.edit(prop, this.dialog);
		autocaptioninfo.setText(prop.getValue());
	}

	private javax.swing.JButton activeTitleButton;
	private javax.swing.JButton addButton;
	private javax.swing.JCheckBox addoldcondi;
	private javax.swing.JCheckBox addoldtreeCondi;
	private javax.swing.JTextField autocaptioninfo;
	private javax.swing.JTextField autodescinfo;
	private javax.swing.JButton cancelButton;
	private javax.swing.JButton chooseStyleButton;
	private javax.swing.JTextField classid;
	private javax.swing.JTable condiexpress;
	private javax.swing.JButton delButton;
	private javax.swing.JButton jButton3;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JButton notFieldsButton;
	private javax.swing.JTextField notfields;
	private javax.swing.JTextField rebuildtreeinfo;
	private javax.swing.JTextField relationFieldName;
	private javax.swing.JTextField relationTableName;
	private javax.swing.JButton setFieldsButton;
	private javax.swing.JTextField styleid;
	private javax.swing.JTextField stylename;
	private JTextField textField;
	private Map<String, String> props;
	private boolean submit = false;
	private JTextField condiMapFieldStr;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "设置穿透至自定义查询的组件", this);
		dialog.setVisible(true);
	}

	private void setStyle(XMLDto dto) {
		if (dto == null) {
			stylename.setText("");
			styleid.setText("");
			classid.setText("");
			targets = null;
		} else {
			stylename.setText(dto.getValue("name"));
			String id = dto.getValue("id");
			if (CommonUtils.isStrEmpty(id)) {
				id = dto.getValue("styleid");
			}
			styleid.setText(id);
			classid.setText(dto.getValue("classid"));
			try {
				targets = InvokerServiceUtils.getClassItemList(dto.getValue("classid"));
			} catch (Exception e1) {
				GUIUtils.showMsg(dialog, "获取目标字段失败");
				logger.error(e1.getMessage(), e1);
			}
		}

	}

	private void save() {
		try {
			CompUtils.stopTabelCellEditor(condiexpress);
			String style = styleid.getText();
			String oldcondi = "0";
			String oldtreecondi = "0";
			if (addoldcondi.isSelected()) {
				oldcondi = "1";
			}
			if (addoldtreeCondi.isSelected()) {
				oldtreecondi = "1";
			}

			StringBuilder condi = new StringBuilder();
			int rowCount = condiexpress.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				// 第1列：序号
				Integer sortnumber = (Integer) condiexpress.getValueAt(i, index_sortnum);
				// 第2列："目标字段"
				XMLDto target = CompUtils.getCellValue(XMLDto.class, condiexpress, i, index_target);
				// 第3列："操作符"
				XMLDto operation = CompUtils.getCellValue(XMLDto.class, condiexpress, i, index_oper);
				// 第4列："当前查询类字段"
				XMLDto currClassField = CompUtils.getCellValue(XMLDto.class, condiexpress, i, index_curent);
				// 第5列："固定值"
				String fixedValue = CommonUtils.coverNull((String) condiexpress.getValueAt(i, index_fix));
				// 第6列："字段位置"
				String fieldPlace = CommonUtils.getMapKey(locations, (String) condiexpress.getValueAt(i, index_location));
				Boolean yfnull = (Boolean) condiexpress.getValueAt(i, index_yfnull);
				if (yfnull == null) {
					yfnull = false;
				}
				if (target == null || fieldPlace == null || operation == null) {
					GUIUtils.showMsg(dialog, "目标字段,字段位置,操作符不能为空");
					condiexpress.setRowSelectionInterval(i, i);
					return;
				}
				if (currClassField == null && CommonUtils.isStrEmpty(fixedValue) && operation.toString().indexOf("NULL") < 0) {
					GUIUtils.showMsg(dialog, "请设置当前查询类或固定值");
					condiexpress.setRowSelectionInterval(i, i);
					return;
				}
				if (currClassField != null && !CommonUtils.isStrEmpty(fixedValue)) {
					GUIUtils.showMsg(dialog, "不能同时设置前查询类和固定值");
					condiexpress.setRowSelectionInterval(i, i);
					return;
				}
				condi.append(sortnumber).append(":").append(target.getValue("itemname")).append(":")//
						.append(operation.getValue("value")).append(":");
				if (CommonUtils.isStrEmpty(fixedValue)) {
					if (currClassField != null) {
						condi.append(currClassField.getValue("itemname"));
					}

				} else {
					condi.append(fixedValue).append("(常量)");
				}
				condi.append(":").append(fieldPlace);
				condi.append(":").append(yfnull.toString());
				if (i < rowCount - 1) {
					condi.append(",");
				}
			}

			String rebuildtree = rebuildtreeinfo.getText();
			String autocaption = autocaptioninfo.getText();
			String autodesc = autodescinfo.getText();
			StringBuilder inParam = new StringBuilder();
			inParam.append("styleid=").append(style).append(";");
			inParam.append("addoldcondi=").append(oldcondi).append(";");
			inParam.append("addoldtreeCondi=").append(oldtreecondi).append(";");
			inParam.append("comboxNo=").append(";");
			inParam.append("relationFieldName=").append(relationFieldName.getText()).append(";");
			inParam.append("relationTableName=").append(relationTableName.getText()).append(";");
			inParam.append("condiMapFieldStr=").append(CommonUtils.base64Encode(condiMapFieldStr.getText().getBytes())).append(";");
			inParam.append("condiexpress=").append(CommonUtils.base64Encode(condi.toString().getBytes())).append(";");
			String newTitle = textField.getText();
			if (!CommonUtils.isStrEmpty(newTitle)) {
				newTitle = CommonUtils.base64Encode(newTitle.getBytes());
			} else {
				newTitle = "";
			}
			inParam.append("newcaption=").append(newTitle);
			if (!rebuildtree.equals("")) {
				inParam.append(";").append("rebuildtreeinfo=").append(CommonUtils.base64Encode(rebuildtree.getBytes()));
			}
			if (!autocaption.equals("")) {
				inParam.append(";").append("autocaptioninfo=").append(CommonUtils.base64Encode(autocaption.getBytes()));
			}
			if (!autodesc.equals("")) {
				inParam.append(";").append("autodescinfo=").append(CommonUtils.base64Encode(autodesc.getBytes()));
			}
			if (!notfields.getText().equals("")) {
				inParam.append(";").append("notfields=").append(CommonUtils.base64Encode(notfields.getText().getBytes()));
			}
			if (comboBox.getSelectedIndex() > 0) {
				inParam.append(";").append("mergercol=").append(((XMLDto) comboBox.getSelectedItem()).getValue("value"));
			}
			props.put("inparam", inParam.toString());
			submit = true;
			dialog.dispose();
		} catch (Exception e2) {
			GUIUtils.showMsg(dialog, "保存出错");
			logger.error(e2.getMessage(), e2);
		}

	}

	private void initData() {
		String inparam = props.get("inparam");
		if (CommonUtils.isStrEmpty(inparam)) {
			return;
		}
		try {
			String[] params = inparam.split(";");// 分割第一层参数
			for (String param : params) {
				String[] keyandvalue = param.split("=", 2);
				if (keyandvalue.length > 1) {
					String key = keyandvalue[0];
					String value = keyandvalue[1];
					if ("mergercol".equals(key)) {
						if (!CommonUtils.isStrEmpty(value)) {
							XMLDto dto = CommonUtils.getXmlDto(mergercolDtos, "value", value);
							if (dto != null) {
								comboBox.setSelectedItem(dto);
							}
						}
						continue;
					}
					if (allcomp.containsKey(key)) {
						Component com = allcomp.get(key);
						if (com instanceof JCheckBox) {
							((JCheckBox) com).setSelected("1".equals(value) ? true : false);
						} else if (com instanceof JTextField) {
							if ("styleid".equalsIgnoreCase(key) && !CommonUtils.isStrEmpty(value)) {
								XMLDto dto = InvokerServiceUtils.getStyleInfoById(value);
								setStyle(dto);
							} else {
								if ("condiMapFieldStr".equalsIgnoreCase(key) || "rebuildtreeinfo".equalsIgnoreCase(key) || "autocaptioninfo".equalsIgnoreCase(key) || "autodescinfo".equalsIgnoreCase(key) || "notfields".equalsIgnoreCase(key) || "newcaption".equalsIgnoreCase(key)) {
									value = new String(CommonUtils.base64Dcode(value));
								}
								((JTextField) com).setText(value);
							}
						} else if (com instanceof JTable) {
							if (CommonUtils.isStrEmpty(value)) {
								continue;
							}
							value = new String(CommonUtils.base64Dcode(value));
							String[] rowParams = value.split(",");
							for (int i = 0; i < rowParams.length; i++) {
								String row = rowParams[i];
								Object[] rowData = new Object[tableHeader.length];
								String[] columnValue = row.split(":");
								for (int j = 0; j < columnValue.length; j++) {
									String v = columnValue[j];
									XMLDto temp = null;
									switch (j) {
									case 0:
										if (CommonUtils.isNumberString(v)) {
											rowData[index_sortnum] = Integer.parseInt(v);
										} else {
											rowData[index_sortnum] = rowParams.length + i;
										}
										break;
									case 1:
										if (targets == null) {
											temp = new XMLDto(Arrays.asList("itemlabel", "itemname"));
											temp.setValue("itemlabel", "未知");
											temp.setValue("itemname", v);
										} else {
											temp = CommonUtils.getXmlDto(targets, "itemname", v);
										}
										rowData[index_target] = temp;
										break;
									case 2:
										temp = CommonUtils.getXmlDto(opers, "value", v);
										rowData[index_oper] = temp;
										break;
									case 3:
										temp = CommonUtils.getXmlDto(fields, "itemname", v);
										if (temp != null) {
											rowData[index_curent] = temp;
											rowData[index_fix] = "";
										} else {
											if (v.matches(".+\\(常量\\)")) {
												rowData[index_fix] = v.substring(0, v.length() - 4);
											} else {
												rowData[index_fix] = v;
											}
										}
										break;
									case 4:
										rowData[index_location] = locations.get(v);
										break;
									case 5:
										rowData[index_yfnull] = "true".equals(v);
										break;

									default:
										break;
									}
								}
								model.addRow(rowData);
							}
						}
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
