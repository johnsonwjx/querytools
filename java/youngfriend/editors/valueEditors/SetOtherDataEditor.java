/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;

import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

public class SetOtherDataEditor extends javax.swing.JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	public SetOtherDataEditor() {
		initComponents();
	}

	private void initData() {
		String dataParams = props.get("value");
		if (CommonUtils.isStrEmpty(dataParams)) {
			String service = props.get("serviceName");
			if (!CommonUtils.isStrEmpty(service)) {
				serviceName.setText(service + ".simplequery");
			}
			return;
		}
		try {
			dataParams = new String(CommonUtils.base64Dcode(dataParams));
			String[] paramsArray = dataParams.split(",");
			for (int i = 0; i < paramsArray.length; i++) {
				String params = paramsArray[i];
				String key = params.substring(0, params.indexOf(":"));
				String value = params.substring(params.indexOf(":") + 1);
				if (key.equals("serviceName"))
					serviceName.setText(value);
				else if ("idParamName".equals(key))
					idParamName.setText(value);
				else if ("condiParamName".equals(key))
					condiParamName.setText(value);
				else if ("returnParamName".equals(key))
					returnParamName.setText(value);
				else if ("nameField".equals(key))
					nameField.setText(value);
				else if ("customFieldName".equals(key))
					customFieldName.setText(value);
				else if ("isSelectSQL".equals(key))
					isSelectSQL.setSelected(Boolean.valueOf(value));
				else if ("customCondiValue".equals(key)) {
					value = new String(CommonUtils.base64Dcode(value));
					customCondiValue.setText(value);
				} else if ("queryTableName".equals(key))
					queryTableName.setText(value);
				else if ("codeField".equals(key))
					codeField.setText(value);
				else if ("recordsNodeParam".equals(key))
					recordsNodeParam.setText(value);
				else if ("recordNodeParam".equals(key))
					recordNodeParam.setText(value);
				else if ("operator".equals(key)) {
					if ("左匹配".equals(value))
						rbLlike.setSelected(true);
					else if ("全匹配".equals(value))
						rbAlike.setSelected(true);
					else
						rbEq.setSelected(true);
				} else if ("idParamName".equals(key))
					idParamName.setText(value);

			}
		} catch (Exception ex) {
			GUIUtils.showMsg(dialog, ComEum.INIT_ERROR);
			logger.error(ex.getMessage(), ex);
		}
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(752, 504));
		buttonGroup1 = new javax.swing.ButtonGroup();
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		jPanel1 = new javax.swing.JPanel();
		panel.add(jPanel1);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(12, 24, 78, 16);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(12, 53, 118, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(12, 82, 117, 16);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(12, 111, 78, 16);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(12, 140, 78, 16);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(12, 169, 78, 17);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setBounds(12, 203, 78, 16);
		jLabel8 = new javax.swing.JLabel();
		jLabel8.setBounds(12, 226, 78, 23);
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setBounds(12, 261, 195, 16);
		jLabel10 = new javax.swing.JLabel();
		jLabel10.setBounds(12, 278, 234, 16);
		jLabel12 = new javax.swing.JLabel();
		jLabel12.setBounds(12, 319, 260, 16);
		jLabel11 = new javax.swing.JLabel();
		jLabel11.setBounds(12, 299, 78, 16);
		serviceName = new javax.swing.JTextField();
		serviceName.setLocation(96, 18);
		idParamName = new javax.swing.JTextField();
		idParamName.setBounds(136, 47, 228, 28);
		condiParamName = new javax.swing.JTextField();
		condiParamName.setBounds(135, 76, 229, 28);
		queryTableName = new javax.swing.JTextField();
		queryTableName.setBounds(96, 105, 267, 28);
		codeField = new javax.swing.JTextField();
		codeField.setBounds(96, 134, 267, 28);
		nameField = new javax.swing.JTextField();
		nameField.setBounds(96, 163, 267, 28);
		customFieldName = new javax.swing.JTextField();
		customFieldName.setBounds(96, 197, 268, 28);
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setBounds(96, 226, 274, 30);
		rbLlike = new javax.swing.JRadioButton();
		rbEq = new javax.swing.JRadioButton();
		rbAlike = new javax.swing.JRadioButton();
		isSelectSQL = new javax.swing.JCheckBox();
		isSelectSQL.setBounds(96, 295, 188, 23);
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setBounds(12, 343, 352, 84);
		customCondiValue = new javax.swing.JTextArea();
		customCondiValue.setLineWrap(true);
		jLabel13 = new javax.swing.JLabel();
		jLabel13.setBounds(12, 426, 312, 16);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("服务参数设置"));

		jLabel1.setText("服 务 名 称：");

		jLabel2.setText("用户认证ID参数名：");

		jLabel3.setText("自定义条件参数名：");

		jLabel4.setText("数据源表名：");

		jLabel5.setText("建 树 字 段：");

		jLabel6.setText("树显示字段：");

		jLabel7.setText("关联字段名：");

		jLabel8.setText("操   作   符：");

		jLabel9.setForeground(new java.awt.Color(0, 153, 153));
		jLabel9.setText("说明：关联字段名是从表的字段；");

		jLabel10.setForeground(new java.awt.Color(0, 153, 153));
		jLabel10.setText("点树节点以后用此字段来过滤表格的数据");

		jLabel12.setBackground(new java.awt.Color(204, 255, 204));
		jLabel12.setText("注意：如果用于建树，必须要按建树字段排序");
		jLabel12.setOpaque(true);

		jLabel11.setText("自定义条件：");

		serviceName.setSize(new Dimension(267, 28));

		idParamName.setText("accid");

		condiParamName.setText("querysql");

		buttonGroup1.add(rbLlike);
		rbLlike.setText("左匹配");
		rbLlike.setSize(new java.awt.Dimension(30, 10));

		buttonGroup1.add(rbEq);
		rbEq.setText("等于");

		buttonGroup1.add(rbAlike);
		rbAlike.setText("全匹配");

		isSelectSQL.setText("自定义条件是完整检索语句");

		jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		customCondiValue.setColumns(20);
		customCondiValue.setRows(5);
		jScrollPane1.setViewportView(customCondiValue);

		jLabel13.setForeground(new java.awt.Color(255, 0, 0));
		jLabel13.setText("说明：当外面的条件不为空时，将强制加入到本语句中");
		jPanel1.setLayout(null);
		jPanel1.add(jLabel7);
		jPanel1.add(jLabel8);
		jPanel1.add(jPanel2);
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel2.add(rbLlike);
		jPanel2.add(rbAlike);
		jPanel2.add(rbEq);
		jPanel1.add(customFieldName);
		jPanel1.add(jScrollPane1);
		jPanel1.add(jLabel3);
		jPanel1.add(condiParamName);
		jPanel1.add(jLabel2);
		jPanel1.add(idParamName);
		jPanel1.add(jLabel1);
		jPanel1.add(serviceName);
		jPanel1.add(jLabel4);
		jPanel1.add(jLabel5);
		jPanel1.add(jLabel6);
		jPanel1.add(nameField);
		jPanel1.add(codeField);
		jPanel1.add(queryTableName);
		jPanel1.add(jLabel9);
		jPanel1.add(jLabel10);
		jPanel1.add(jLabel11);
		jPanel1.add(isSelectSQL);
		jPanel1.add(jLabel12);
		jPanel1.add(jLabel13);
		jPanel3 = new javax.swing.JPanel();
		panel.add(jPanel3);
		jLabel14 = new javax.swing.JLabel();
		jLabel14.setBounds(12, 30, 104, 16);
		jLabel15 = new javax.swing.JLabel();
		jLabel15.setBounds(12, 59, 104, 16);
		jLabel16 = new javax.swing.JLabel();
		jLabel16.setBounds(12, 88, 104, 16);
		recordsNodeParam = new javax.swing.JTextField();
		recordsNodeParam.setLocation(128, 24);
		recordNodeParam = new javax.swing.JTextField();
		recordNodeParam.setBounds(128, 53, 182, 28);
		returnParamName = new javax.swing.JTextField();
		returnParamName.setBounds(128, 82, 182, 28);

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("返回参数设置"));

		jLabel14.setText("\u603B\u8BB0\u5F55\u884C\u8282\u70B9\u540D\uFF1A");

		jLabel15.setText("\u8BB0\u5F55\u884C\u8282\u70B9\u540D\uFF1A");

		jLabel16.setText("返回数据参数名：");

		recordsNodeParam.setText("querydatas");
		recordsNodeParam.setSize(new Dimension(182, 28));

		returnParamName.setText("XML");
		jPanel3.setLayout(null);
		jPanel3.add(jLabel14);
		jPanel3.add(recordsNodeParam);
		jPanel3.add(jLabel16);
		jPanel3.add(jLabel15);
		jPanel3.add(recordNodeParam);
		jPanel3.add(returnParamName);

		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		OKButton = new javax.swing.JButton();
		panel_1.add(OKButton);

		OKButton.setText("确认");
		cancelButton = new javax.swing.JButton();
		panel_1.add(cancelButton);

		cancelButton.setText("取消");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
		OKButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OKButtonActionPerformed(evt);
			}
		});

	}

	public String getParamTxt(String sName) {
		try {
			StringBuilder value = new StringBuilder();
			String idparamname = idParamName.getText();
			String condiparamname = condiParamName.getText();
			String returnparamname = returnParamName.getText();
			String isselectsql = isSelectSQL.isSelected() ? "true" : "false";
			String customcondivalue = customCondiValue.getText().replaceAll("\\n", " ");
			if (!customcondivalue.equals("")) {
				customcondivalue = new String(CommonUtils.base64Encode(customcondivalue.getBytes()));
			}
			String querytablename = queryTableName.getText();
			String codefield = codeField.getText();
			String namefield = nameField.getText();
			String customfieldname = customFieldName.getText();
			String recordsnodeparam = recordsNodeParam.getText();
			String recordnodeparam = recordNodeParam.getText();
			String operator = "左匹配";
			if (rbAlike.isSelected()) {
				operator = "全匹配";
			}
			if (rbEq.isSelected()) {
				operator = "等于";
			}
			value.append("serviceName:").append(sName).append(",");
			value.append("idParamName:").append(idparamname).append(",");
			value.append("condiParamName:").append(condiparamname).append(",");
			value.append("returnParamName:").append(returnparamname).append(",");
			value.append("isSelectSQL:").append(isselectsql).append(",");
			value.append("customCondiValue:").append(customcondivalue).append(",");
			value.append("queryTableName:").append(querytablename).append(",");
			value.append("codeField:").append(codefield).append(",");
			value.append("nameField:").append(namefield).append(",");
			value.append("customFieldName:").append(customfieldname).append(",");
			value.append("recordsNodeParam:").append(recordsnodeparam).append(",");
			value.append("recordNodeParam:").append(recordnodeparam).append(",");
			value.append("operator:").append(operator);
			return new String(CommonUtils.base64Encode(value.toString().getBytes()).getBytes(), "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			props.put("value", getParamTxt(serviceName.getText()));
			submit = true;
			dialog.dispose();
		} catch (Exception ex) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(ex.getMessage(), ex);
		}
	}

	private javax.swing.JButton OKButton;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JButton cancelButton;
	private javax.swing.JTextField codeField;
	private javax.swing.JTextField condiParamName;
	private javax.swing.JTextArea customCondiValue;
	private javax.swing.JTextField customFieldName;
	private javax.swing.JTextField idParamName;
	private javax.swing.JCheckBox isSelectSQL;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextField nameField;
	private javax.swing.JTextField queryTableName;
	private javax.swing.JRadioButton rbAlike;
	private javax.swing.JRadioButton rbEq;
	private javax.swing.JRadioButton rbLlike;
	private javax.swing.JTextField recordNodeParam;
	private javax.swing.JTextField recordsNodeParam;
	private javax.swing.JTextField returnParamName;
	private javax.swing.JTextField serviceName;
	private boolean submit = false;
	private Map<String, String> props;
	private JPanel panel;
	private JPanel panel_1;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "其他数据源设置", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

}
