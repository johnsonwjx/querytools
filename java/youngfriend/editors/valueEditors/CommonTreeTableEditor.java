/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CommonTreeTable.java
 *
 * Created on Dec 12, 2011, 7:42:15 PM
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.tree.BackedList;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

/**
 * 通用左树右表设置组建
 * 
 * @author youngfriend
 */
public class CommonTreeTableEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;

	public CommonTreeTableEditor() {
		initComponents();
		init();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(953, 664));
		buttonGroup2 = new javax.swing.ButtonGroup();
		buttonGroup3 = new javax.swing.ButtonGroup();
		buttonGroup5 = new javax.swing.ButtonGroup();
		jPanel6 = new javax.swing.JPanel();

		jPanel6.setName("jPanel6");
		jPanel6.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		setLayout(new BorderLayout(0, 0));
		add(jPanel6, BorderLayout.SOUTH);
		jButton5 = new javax.swing.JButton();
		jButton5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		jPanel6.add(jButton5);

		jButton5.setText("确定");
		jButton5.setName("jButton5"); // NOI18N
		jButton4 = new javax.swing.JButton();
		jPanel6.add(jButton4);

		jButton4.setText("取消");
		jButton4.setName("jButton4"); // NOI18N
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 200));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setBounds(0, 0, 505, 194);
		panel.add(jPanel1);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 24, 69, 16);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(6, 59, 30, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(6, 94, 30, 16);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(6, 130, 69, 16);
		serviceName = new javax.swing.JTextField();
		serviceName.setBounds(93, 18, 311, 28);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(6, 158, 380, 15);
		RelationFieldName = new javax.swing.JTextField();
		RelationFieldName.setBounds(93, 124, 311, 28);
		jButton1 = new javax.swing.JButton();
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getTableInfo(serviceName.getText());
			}
		});
		jButton1.setBounds(404, 19, 97, 29);
		jButton2 = new javax.swing.JButton();
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setMasterParam();
			}
		});
		jButton2.setBounds(404, 54, 97, 29);
		jButton3 = new javax.swing.JButton();
		jButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSlaveParam();
			}
		});
		jButton3.setBounds(404, 94, 97, 29);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("表信息"));
		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("通用服务名:");
		jLabel1.setName("jLabel1"); // NOI18N

		jLabel2.setText("主表:");
		jLabel2.setName("jLabel2"); // NOI18N

		jLabel3.setText("从表:");
		jLabel3.setName("jLabel3"); // NOI18N

		jLabel4.setText("关联字段名:");
		jLabel4.setName("jLabel4"); // NOI18N

		serviceName.setName("serviceName"); // NOI18N

		jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 12));
		jLabel5.setText("说明:关联字段名是从表的字段,点树节点以后用此字段来过滤表格的数据");
		jLabel5.setName("jLabel5");

		RelationFieldName.setName("RelationFieldName"); // NOI18N

		jButton1.setText("获取表信息");
		jButton1.setName("jButton1"); // NOI18N

		jButton2.setText("\u4E3B\u8868\u53C2\u6570");
		jButton2.setName("jButton2"); // NOI18N

		jButton3.setText("\u4ECE\u8868\u53C2\u6570");
		jButton3.setName("jButton3");
		jPanel1.setLayout(null);
		jPanel1.add(jLabel5);
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel3);
		jPanel1.add(jLabel4);
		jPanel1.add(RelationFieldName);
		jPanel1.add(serviceName);
		jPanel1.add(jButton1);
		jPanel1.add(jButton2);
		jPanel1.add(jButton3);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(93, 53, 243, 28);
		jPanel1.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectMasterTable();
			}
		});
		button.setBounds(340, 54, 35, 29);
		jPanel1.add(button);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(93, 93, 243, 28);
		jPanel1.add(textField_1);

		JButton button_1 = new JButton("...");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectSlaveTable();
			}
		});
		button_1.setBounds(340, 94, 35, 29);
		jPanel1.add(button_1);
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(505, 0, 442, 194);
		panel.add(jPanel2);
		jPanel5 = new javax.swing.JPanel();
		jPanel5.setBounds(6, 18, 70, 76);
		jRadioButton5 = new javax.swing.JRadioButton();
		jRadioButton5.setBounds(6, 18, 58, 23);
		jRadioButton6 = new javax.swing.JRadioButton();
		jRadioButton6.setBounds(6, 47, 58, 23);
		jPanel4 = new javax.swing.JPanel();
		jPanel4.setBounds(75, 18, 102, 76);
		jRadioButton3 = new javax.swing.JRadioButton();
		jRadioButton3.setBounds(6, 18, 84, 23);
		jRadioButton4 = new javax.swing.JRadioButton();
		jRadioButton4.setBounds(6, 47, 97, 23);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(6, 117, 56, 16);
		buildTreeField = new javax.swing.JTextField();
		buildTreeField.setBounds(68, 111, 190, 28);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setBounds(264, 117, 56, 16);
		height = new javax.swing.JSpinner();
		height.setBounds(326, 111, 70, 28);
		width = new javax.swing.JSpinner();
		width.setBounds(326, 145, 70, 28);
		jLabel8 = new javax.swing.JLabel();
		jLabel8.setBounds(264, 151, 56, 16);
		formtitle = new javax.swing.JTextField();
		formtitle.setBounds(68, 145, 190, 28);
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setBounds(6, 151, 56, 16);
		jPanel10 = new javax.swing.JPanel();
		jPanel10.setBounds(176, 18, 102, 76);
		jRadioButton15 = new javax.swing.JRadioButton();
		jRadioButton15.setBounds(6, 18, 84, 23);
		jRadioButton16 = new javax.swing.JRadioButton();
		jRadioButton16.setBounds(6, 47, 84, 23);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("参数设置"));
		jPanel2.setName("jPanel2");

		jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("选择方式"));
		jPanel5.setName("jPanel5"); // NOI18N

		buttonGroup2.add(jRadioButton5);
		jRadioButton5.setSelected(true);
		jRadioButton5.setText("单选");
		jRadioButton5.setName("jRadioButton5"); // NOI18N

		buttonGroup2.add(jRadioButton6);
		jRadioButton6.setText("多选");
		jRadioButton6.setName("jRadioButton6");

		jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("建树方式"));
		jPanel4.setName("jPanel4"); // NOI18N

		buttonGroup3.add(jRadioButton3);
		jRadioButton3.setText("分批取数");
		jRadioButton3.setName("jRadioButton3"); // NOI18N

		buttonGroup3.add(jRadioButton4);
		jRadioButton4.setSelected(true);
		jRadioButton4.setText("一次性取数");
		jRadioButton4.setName("jRadioButton4");

		jLabel6.setText("建树字段:");
		jLabel6.setName("jLabel6"); // NOI18N

		buildTreeField.setText("CODE");
		buildTreeField.setName("buildTreeField"); // NOI18N

		jLabel7.setText("默认高度:");
		jLabel7.setName("jLabel7"); // NOI18N

		height.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(800), Integer.valueOf(0), null, Integer.valueOf(10)));
		height.setName("height"); // NOI18N

		width.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(600), Integer.valueOf(0), null, Integer.valueOf(10)));
		width.setName("width"); // NOI18N

		jLabel8.setText("默认宽度:");
		jLabel8.setName("jLabel8"); // NOI18N

		formtitle.setName("formtitle"); // NOI18N

		jLabel9.setText("窗口标题:");
		jLabel9.setName("jLabel9"); // NOI18N

		jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("动态条件来源"));
		jPanel10.setName("jPanel10"); // NOI18N

		buttonGroup5.add(jRadioButton15);
		jRadioButton15.setSelected(true);
		jRadioButton15.setText("条件面板");
		jRadioButton15.setName("jRadioButton15"); // NOI18N

		buttonGroup5.add(jRadioButton16);
		jRadioButton16.setText("结果面板");
		jRadioButton16.setName("jRadioButton16");
		jPanel2.setLayout(null);
		jPanel2.add(jPanel5);
		jPanel5.setLayout(null);
		jPanel5.add(jRadioButton5);
		jPanel5.add(jRadioButton6);
		jPanel2.add(jPanel4);
		jPanel4.setLayout(null);
		jPanel4.add(jRadioButton3);
		jPanel4.add(jRadioButton4);
		jPanel2.add(jLabel9);
		jPanel2.add(formtitle);
		jPanel2.add(jLabel6);
		jPanel2.add(buildTreeField);
		jPanel2.add(jLabel8);
		jPanel2.add(jLabel7);
		jPanel2.add(width);
		jPanel2.add(height);
		jPanel2.add(jPanel10);
		jPanel10.setLayout(null);
		jPanel10.add(jRadioButton15);
		jPanel10.add(jRadioButton16);

		checkBox = new JCheckBox("\u663E\u793A\u4EE3\u7801");
		checkBox.setBounds(290, 53, 84, 23);
		jPanel2.add(checkBox);

		checkBox_1 = new JCheckBox("\u662F\u5426\u5206\u9875");
		checkBox_1.setSelected(true);
		checkBox_1.setBounds(290, 29, 84, 23);
		jPanel2.add(checkBox_1);
		jTabbedPane1 = new javax.swing.JTabbedPane();
		add(jTabbedPane1, BorderLayout.CENTER);
		jScrollPane1 = new javax.swing.JScrollPane();
		tableFields = new javax.swing.JTable();
		jPanel12 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jPanel13 = new javax.swing.JPanel();
		jPanel13.setPreferredSize(new Dimension(0, 250));
		jPanel13.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"\u7528[]\u62EC\u8D77\u6765,\u5B57\u6BB5\u540D\u7528{}\u62EC\u8D77\u6765,\u4E3E\u4F8B:1=1 [and project_code='{project_code}'] \u5982\u679C\u6709\u591A\u4E2A\u63A7\u4EF6\u5B57\u6BB5\u540D\u76F8\u540C\u8BF7\u7528\u63A7\u4EF6Id,\u5982: {project_code} \u66FF\u6362\u6210{\u63A7\u4EF6Id} ",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane3.setViewportBorder(new TitledBorder(null, "\u53F0\u8D26\u8868\u5B57\u6BB5:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane3.setBounds(0, 20, 303, 229);
		jList1 = new javax.swing.JList();
		jScrollPane4 = new javax.swing.JScrollPane();
		jScrollPane4.setViewportBorder(new TitledBorder(null, "\u67E5\u8BE2\u7C7B\u5B57\u6BB5:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane4.setBounds(309, 20, 401, 229);
		jList2 = new javax.swing.JList();
		jScrollPane5 = new javax.swing.JScrollPane();
		jScrollPane5.setBounds(716, 20, 124, 229);
		jList3 = new javax.swing.JList();
		jScrollPane6 = new javax.swing.JScrollPane();
		jScrollPane6.setBounds(850, 20, 125, 220);
		jList4 = new javax.swing.JList();
		jPanel15 = new javax.swing.JPanel();
		jScrollPane7 = new javax.swing.JScrollPane();
		jTextArea2 = new javax.swing.JTextArea();
		jPanel16 = new javax.swing.JPanel();
		jPanel16.setPreferredSize(new Dimension(0, 250));
		jPanel16.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"\u7528[]\u62EC\u8D77\u6765,\u5B57\u6BB5\u540D\u7528{}\u62EC\u8D77\u6765,\u4E3E\u4F8B:1=1 [and project_code='{project_code}'] \u5982\u679C\u6709\u591A\u4E2A\u63A7\u4EF6\u5B57\u6BB5\u540D\u76F8\u540C\u8BF7\u7528\u63A7\u4EF6Id,\u5982: {project_code} \u66FF\u6362\u6210{\u63A7\u4EF6Id} ",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane8 = new javax.swing.JScrollPane();
		jScrollPane8.setViewportBorder(new TitledBorder(null, "\u53F0\u8D26\u8868\u5B57\u6BB5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane8.setBounds(0, 20, 303, 220);
		jList5 = new javax.swing.JList();
		jScrollPane9 = new javax.swing.JScrollPane();
		jScrollPane9.setViewportBorder(new TitledBorder(null, "\u67E5\u8BE2\u7C7B\u5B57\u6BB5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane9.setBounds(309, 20, 401, 220);
		jList6 = new javax.swing.JList();
		jScrollPane10 = new javax.swing.JScrollPane();
		jScrollPane10.setBounds(716, 20, 124, 220);
		jList7 = new javax.swing.JList();
		jScrollPane11 = new javax.swing.JScrollPane();
		jScrollPane11.setBounds(850, 20, 75, 220);
		jList8 = new javax.swing.JList();

		jTabbedPane1.setName("jTabbedPane1"); // NOI18N

		jScrollPane1.setName("jScrollPane1"); // NOI18N

		tableFields.setName("tableFields"); // NOI18N
		jScrollPane1.setViewportView(tableFields);

		jTabbedPane1.addTab("字段设置窗口", jScrollPane1);

		jPanel12.setName("jPanel12"); // NOI18N

		jScrollPane2.setName("jScrollPane2"); // NOI18N

		jTextArea1.setColumns(20);
		jTextArea1.setLineWrap(true);
		jTextArea1.setRows(6);
		jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
		jTextArea1.setName("jTextArea1"); // NOI18N
		jScrollPane2.setViewportView(jTextArea1);

		jPanel13.setName("jPanel13"); // NOI18N

		jScrollPane3.setName("jScrollPane3"); // NOI18N

		jList1.setName("jList1"); // NOI18N
		jScrollPane3.setViewportView(jList1);

		jScrollPane4.setName("jScrollPane4"); // NOI18N

		jList2.setName("jList2"); // NOI18N
		jScrollPane4.setViewportView(jList2);

		jScrollPane5.setName("jScrollPane5"); // NOI18N

		jList3.setModel(new javax.swing.AbstractListModel() {
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
		jList3.setName("jList3"); // NOI18N
		jScrollPane5.setViewportView(jList3);

		jScrollPane6.setName("jScrollPane6"); // NOI18N

		jList4.setModel(new javax.swing.AbstractListModel() {
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
		jList4.setName("jList4"); // NOI18N
		jScrollPane6.setViewportView(jList4);

		jTabbedPane1.addTab("主表过滤条件设置", jPanel12);
		jPanel12.setLayout(new BorderLayout(0, 0));
		jPanel12.add(jScrollPane2);
		jPanel12.add(jPanel13, BorderLayout.SOUTH);
		jPanel13.setLayout(null);
		jPanel13.add(jScrollPane3);
		jPanel13.add(jScrollPane4);
		jPanel13.add(jScrollPane5);
		jPanel13.add(jScrollPane6);

		jPanel15.setName("jPanel15"); // NOI18N

		jScrollPane7.setName("jScrollPane7"); // NOI18N

		jTextArea2.setColumns(20);
		jTextArea2.setLineWrap(true);
		jTextArea2.setRows(6);
		jTextArea2.setName("jTextArea2"); // NOI18N
		jScrollPane7.setViewportView(jTextArea2);

		jPanel16.setName("jPanel16"); // NOI18N

		jScrollPane8.setName("jScrollPane8"); // NOI18N

		jList5.setName("jList5"); // NOI18N
		jScrollPane8.setViewportView(jList5);

		jScrollPane9.setName("jScrollPane9"); // NOI18N

		jList6.setName("jList6"); // NOI18N
		jScrollPane9.setViewportView(jList6);

		jScrollPane10.setName("jScrollPane10"); // NOI18N

		jList7.setModel(new javax.swing.AbstractListModel() {
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
		jList7.setName("jList7"); // NOI18N
		jScrollPane10.setViewportView(jList7);

		jScrollPane11.setName("jScrollPane11"); // NOI18N

		jList8.setModel(new javax.swing.AbstractListModel() {
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
		jList8.setName("jList8"); // NOI18N
		jScrollPane11.setViewportView(jList8);
		jPanel16.setLayout(null);
		jPanel16.add(jScrollPane8);
		jPanel16.add(jScrollPane9);
		jPanel16.add(jScrollPane10);
		jPanel16.add(jScrollPane11);

		jTabbedPane1.addTab("从表过滤条件设置", jPanel15);
		jPanel15.setLayout(new BorderLayout(0, 0));
		jPanel15.add(jScrollPane7);
		jPanel15.add(jPanel16, BorderLayout.SOUTH);
	}

	private javax.swing.JTextField RelationFieldName;
	private javax.swing.JTextField buildTreeField;
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.ButtonGroup buttonGroup3;
	private javax.swing.ButtonGroup buttonGroup5;
	private javax.swing.JTextField formtitle;
	private javax.swing.JSpinner height;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JList jList1;
	private javax.swing.JList jList2;
	private javax.swing.JList jList3;
	private javax.swing.JList jList4;
	private javax.swing.JList jList5;
	private javax.swing.JList jList6;
	private javax.swing.JList jList7;
	private javax.swing.JList jList8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JRadioButton jRadioButton15;
	private javax.swing.JRadioButton jRadioButton16;
	private javax.swing.JRadioButton jRadioButton3;
	private javax.swing.JRadioButton jRadioButton4;
	private javax.swing.JRadioButton jRadioButton5;
	private javax.swing.JRadioButton jRadioButton6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane10;
	private javax.swing.JScrollPane jScrollPane11;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane7;
	private javax.swing.JScrollPane jScrollPane8;
	private javax.swing.JScrollPane jScrollPane9;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextArea jTextArea2;
	private javax.swing.JTextField serviceName;
	private javax.swing.JSpinner width;
	private boolean submit;
	private Map<String, String> props;
	private String oldServiceName;
	private String[] title = { "字段标签", "字段名称", "字段宽度", "是否显示", "是否返回", "对应的表单项目", "新标签" };
	private List<XMLDto> classFields = CompUtils.getFields();
	private final int index_label = 0;
	private final int index_name = 1;
	private final int index_width = 2;
	private final int index_visible = 3;
	private final int index_return = 4;
	private final int index_field = 5;
	private final int index_newlabel = 6;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private JTable tableFields;
	private DefaultTableModel tableModel;
	private DefaultListModel mastListModel;
	private DefaultListModel slaveListModel;
	private List<XMLDto> tables;
	private JTextField textField;
	private JTextField textField_1;
	private XMLDto masterTable;
	private List<XMLDto> masterFields;
	private XMLDto slaveTable;
	private List<XMLDto> slaveFields;
	private String masterParam;
	private String slaveParam;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;

	private void save() {
		CompUtils.stopTabelCellEditor(tableFields);
		if (masterTable == null || slaveTable == null) {
			GUIUtils.showMsg(dialog, "请选择主表和从表");
			return;

		}
		try {
			String servicename = serviceName.getText().trim();
			String relationfieldname = RelationFieldName.getText();
			String tablename = masterTable.getValue("name") + "," + slaveTable.getValue("name");
			String ismultipage = checkBox_1.isSelected() ? "0" : "1";
			String selecttype = jRadioButton5.isSelected() ? "0" : "1";
			String getdataTime = jRadioButton3.isSelected() ? "0" : "1";
			String showcode = checkBox.isSelected() ? "0" : "1";
			String condipanel = jRadioButton15.isSelected() ? "0" : "1";
			String buildtreefield = buildTreeField.getText().trim();
			String windowparam = "formtitle:" + formtitle.getText() + "||width:" + width.getValue() + "||height:" + height.getValue();
			StringBuffer fields = new StringBuffer();
			String outPutParams = "";
			int rowCount = tableFields.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				Boolean returnFlg = (Boolean) tableFields.getValueAt(i, 4);
				Boolean show = (Boolean) tableFields.getValueAt(i, 3);
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
			String mainCondi = CommonUtils.base64Encode(jTextArea1.getText().replaceAll("\\n", " ").getBytes());
			String subCondi = CommonUtils.base64Encode(jTextArea2.getText().replaceAll("\\n", "").getBytes());
			StringBuffer params = new StringBuffer();
			params.append("serviceName=" + servicename + ";").append("tableName=" + tablename + ";").append("DataParam=" + masterParam + ";").append("SubDataParam=" + slaveParam + ";").append("isMultiPage=" + ismultipage + ";").append("selectType=" + selecttype + ";")
					.append("getDataTime=" + getdataTime + ";").append("showCode=" + showcode + ";").append("condiPanel=" + condipanel + ";").append("buildTreeField=" + buildtreefield + ";").append("windowparam=" + windowparam + ";").append("mainCondi=" + mainCondi + ";")
					.append("subCondi=" + subCondi).append(";").append("RelationFieldName=").append(relationfieldname);
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
				XMLDto value = CommonUtils.getXmlDto(classFields, "itemname", itemname);
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(value);
				pnl.edit(dialog, null);
				if (pnl.isChange()) {
					value = pnl.getSelect();
					if (!pnl.isNull()) {
						tableModel.setValueAt(value.getValue("itemname"), row, column);
					} else {
						tableModel.setValueAt("", row, column);
					}
				}
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
						return;
					}
				}
			}
		});
		jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList7.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList8.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String dto = (String) jList3.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea1, " " + dto);
				}
			}
		});
		jList4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String dto = (String) jList4.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea1, " " + dto);
				}
			}
		});
		jList7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String dto = (String) jList7.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea2, " " + dto);
				}
			}
		});
		jList8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String dto = (String) jList8.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea2, " " + dto);
				}
			}
		});
		slaveListModel = new DefaultListModel();
		mastListModel = new DefaultListModel();
		jList5.setModel(slaveListModel);
		jList1.setModel(mastListModel);
		DefaultListModel fieldModel = new DefaultListModel();
		for (XMLDto f : classFields) {
			fieldModel.addElement(f);
		}
		jList2.setModel(fieldModel);
		jList6.setModel(fieldModel);

		jList2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					XMLDto dto = (XMLDto) jList2.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea1, " " + dto.getValue("itemname"));
				}
			}
		});
		jList6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					XMLDto dto = (XMLDto) jList6.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea2, " " + dto.getValue("itemname"));
				}
			}
		});
		jList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					XMLDto dto = (XMLDto) jList1.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea1, " " + dto.getValue("name"));
				}
			}
		});
		jList5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					XMLDto dto = (XMLDto) jList5.getSelectedValue();
					CompUtils.textareaInsertText(jTextArea2, " " + dto.getValue("name"));
				}
			}
		});

	}

	private void setMasterParam() {
		SetOtherDataEditor setotherdata = new SetOtherDataEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("serviceName", serviceName.getText());
		props.put("value", masterParam);
		setotherdata.edit(dialog, props);
		if (setotherdata.isSubmit()) {
			masterParam = props.get("value");
		}
	}

	private void setSlaveParam() {
		SetOtherDataEditor setotherdata = new SetOtherDataEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("serviceName", serviceName.getText());
		props.put("value", slaveParam);
		setotherdata.edit(dialog, props);
		if (setotherdata.isSubmit()) {
			slaveParam = props.get("value");
		}
	}

	private void selectSlaveTable() {
		if (tables == null || tables.isEmpty()) {
			GUIUtils.showMsg(dialog, "请获取表信息");
			return;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(tables);
		pnl.setValue(slaveTable);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		slaveTable = pnl.getSelect();
		initSlaveUI();
	}

	private void selectMasterTable() {
		if (tables == null || tables.isEmpty()) {
			GUIUtils.showMsg(dialog, "请获取表信息");
			return;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(tables);
		pnl.setValue(masterTable);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		masterTable = pnl.getSelect();
		initMasterUI();
	}

	private void initSlaveUI() {
		tableModel.setRowCount(0);
		jTextArea2.setText("");
		if (slaveTable == null) {
			textField_1.setText("");
			return;
		}
		try {
			textField_1.setText(slaveTable.toString());
			List<Element> fieldEles = slaveTable.getObject(BackedList.class, "fields");
			if (fieldEles == null || fieldEles.isEmpty()) {
				return;
			}
			slaveFields = CommonUtils.getFieldsByServiceTable(slaveTable);
			if (slaveFields == null && slaveFields.isEmpty()) {
				return;
			}
			for (XMLDto dto : slaveFields) {
				tableModel.addRow(new Object[] { dto.getValue("cname"), dto.getValue("name"), 0, false, false, "", "" });
				slaveListModel.addElement(dto);
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化表格失败");
			return;
		}
	}

	private void initMasterUI() {
		mastListModel.clear();
		jTextArea1.setText("");
		if (masterTable == null) {
			textField.setText("");
			return;
		}
		try {
			textField.setText(masterTable.toString());

			masterFields = CommonUtils.getFieldsByServiceTable(masterTable);
			if (masterFields == null && masterFields.isEmpty()) {
				return;
			}
			for (XMLDto dto : masterFields) {
				mastListModel.addElement(dto);
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化表格失败");
			return;
		}
	}

	private void getTableInfo(String servicename) {
		if (CommonUtils.isStrEmpty(servicename.trim())) {
			GUIUtils.showMsg(dialog, "请输入服务名，如：store2");
			return;
		}
		if (servicename.equals(oldServiceName)) {
			return;
		}
		oldServiceName = servicename;
		tables = CommonUtils.getTableByService(oldServiceName);
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "通用左树右表设置组件", this);
		dialog.setVisible(true);
	}

	private void initData() {
		try {
			String inparam = props.get("inparam");
			if (CommonUtils.isStrEmpty(inparam)) {
				return;
			}
			String[] params = inparam.split(";");
			if (params.length <= 0) {
				return;
			}
			String tableName = null, oldfields = null, mainCondi = null, slaveCondi = null;
			for (int i = 0; i < params.length; i++) {
				String keyAndValueStr = params[i];
				int index = keyAndValueStr.indexOf("=");
				if (index == -1) {
					continue;
				}
				String[] keyAndValueArray = new String[] { keyAndValueStr.substring(0, index), keyAndValueStr.substring(index + 1) };
				if (keyAndValueArray.length > 1) {
					String key = keyAndValueArray[0];
					String value = keyAndValueArray[1];
					if (!"".equals(value)) {
						if ("serviceName".equals(key)) {
							serviceName.setText(value);
						} else if ("tableName".equals(key)) {
							tableName = value;
						} else if ("RelationFieldName".equals(key)) {
							RelationFieldName.setText(value);
						} else if ("isMultiPage".equals(key)) {
							checkBox_1.setSelected("0".equals(value));
						} else if ("selectType".equals(key)) {
							jRadioButton5.setSelected("0".equals(value));
						} else if ("showCode".equals(key)) {
							checkBox.setSelected("0".equals(value));
						} else if ("getDataTime".equals(key)) {
							jRadioButton3.setSelected("0".equals(value));
						} else if ("condiPanel".equals(key)) {
							jRadioButton15.setSelected("0".equals(value));
						} else if ("buildTreeField".equals(key)) {
							buildTreeField.setText(value);
						} else if ("windowparam".equalsIgnoreCase(key)) {
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
										oldfields = windowparamValue;
									} else if ("formtitle".equals(windowparamKey)) {
										formtitle.setText(windowparamValue);
									} else if ("width".equals(windowparamKey)) {
										width.setValue(Integer.valueOf(windowparamValue));
									} else if ("height".equals(windowparamKey)) {
										height.setValue(Integer.valueOf(windowparamValue));
									}
								}
							}
						} else if ("DataParam".equals(key)) {
							masterParam = value;
						} else if ("SubDataParam".equals(key)) {
							slaveParam = value;
						} else if ("mainCondi".equals(key)) {
							mainCondi = value;
						} else if ("subCondi".equals(key)) {
							slaveCondi = value;
						}
					}
				}
			}
			if (CommonUtils.isStrEmpty(serviceName.getText().trim())) {
				return;
			}
			getTableInfo(serviceName.getText().trim());
			String[] tablenames = tableName.split(",");
			if (tablenames.length != 2) {
				return;
			}
			masterTable = CommonUtils.getXmlDto(tables, "name", tablenames[0]);
			slaveTable = CommonUtils.getXmlDto(tables, "name", tablenames[1]);
			if (masterTable == null || slaveTable == null) {
				return;
			}
			initMasterUI();
			initSlaveUI();
			if (!CommonUtils.isStrEmpty(mainCondi)) {
				jTextArea1.setText(new String(CommonUtils.base64Dcode(mainCondi)));
			}
			if (!CommonUtils.isStrEmpty(slaveCondi)) {
				jTextArea2.setText(new String(CommonUtils.base64Dcode(slaveCondi)));
			}
			if (CommonUtils.isStrEmpty(oldfields)) {
				return;
			}
			String[] fieldStr = oldfields.split(",");
			if (fieldStr.length <= 0) {
				return;
			}
			for (String field1 : fieldStr) {
				if (CommonUtils.isStrEmpty(field1)) {
					continue;
				}
				String[] field2 = field1.split("\\\\b");
				String fieldname = field2[1];
				XMLDto fieldItem = CommonUtils.getXmlDto(slaveFields, "name", fieldname);
				int index = slaveFields.indexOf(fieldItem);
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
			// 初始化业务表数据
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化失败");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
