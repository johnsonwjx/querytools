package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class WORDPrintEditor extends javax.swing.JPanel implements ValueEditor {
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private static final long serialVersionUID = -7448267083968282974L;
	private JDialog dialog;
	private XMLDto field;

	public WORDPrintEditor() {
		initComponents();
		initComboBox();
	}

	private void initComboBox() {
		isGroupComboBox.addItem("是");
		isGroupComboBox.addItem("否");

		generationModeComboBox.addItem("批量生成");
		generationModeComboBox.addItem("非批量生成");
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(356, 299));
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 12, 56, 16);
		serviceNameTextField = new javax.swing.JTextField();
		serviceNameTextField.setBounds(94, 6, 250, 28);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(6, 58, 69, 16);
		tableNameTextField = new javax.swing.JTextField();
		tableNameTextField.setBounds(94, 52, 250, 28);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(6, 102, 56, 16);
		isGroupComboBox = new javax.swing.JComboBox();
		isGroupComboBox.setBounds(94, 98, 250, 27);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(6, 147, 82, 16);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(6, 188, 56, 16);
		generationModeComboBox = new javax.swing.JComboBox();
		generationModeComboBox.setBounds(94, 188, 250, 27);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(6, 227, 56, 16);
		sortFieldsTextField = new javax.swing.JTextField();
		sortFieldsTextField.setBounds(94, 221, 250, 28);
		jPanel2 = new javax.swing.JPanel();
		cancelButton = new javax.swing.JButton();
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		okButton = new javax.swing.JButton();
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String modulefieldname = "";
				if (field != null) {
					modulefieldname = field.getValue("itemname");
				}
				StringBuffer paramStr = new StringBuffer(20);
				paramStr.append("dataservicename=").append(serviceNameTextField.getText()).append(";");
				paramStr.append("tablename=").append(tableNameTextField.getText()).append(";");
				paramStr.append("groupby=").append(isGroupComboBox.getSelectedItem()).append(";");
				paramStr.append("modulefieldname=").append(modulefieldname).append(";");
				paramStr.append("flag=").append(generationModeComboBox.getSelectedIndex()).append(";");
				paramStr.append("orderby=").append(sortFieldsTextField.getText());
				props.put("inparam", paramStr.toString());
				submit = true;
				dialog.dispose();
			}
		});

		setEnabled(false);
		setPreferredSize(new Dimension(350, 310));

		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("服务名称:");
		jLabel1.setName("jLabel1"); // NOI18N

		serviceNameTextField.setName("serviceNameTextField"); // NOI18N

		jLabel2.setText("数据表名称:");
		jLabel2.setName("jLabel2"); // NOI18N

		tableNameTextField.setName("tableNameTextField"); // NOI18N

		jLabel3.setText("是否分组:");
		jLabel3.setName("jLabel3"); // NOI18N

		isGroupComboBox.setName("isGroupComboBox"); // NOI18N

		jLabel4.setText("模板字段名称:");
		jLabel4.setName("jLabel4");

		jLabel5.setText("生成模式:");
		jLabel5.setName("jLabel5"); // NOI18N

		generationModeComboBox.setName("generationModeComboBox"); // NOI18N

		jLabel6.setText("排序字段:");
		jLabel6.setName("jLabel6"); // NOI18N

		sortFieldsTextField.setName("sortFieldsTextField");
		setLayout(new BorderLayout(0, 0));

		jPanel2.setName("jPanel2");

		cancelButton.setText("取消");
		cancelButton.setName("cancelButton"); // NOI18N

		okButton.setText("确定");
		add(jPanel2, BorderLayout.SOUTH);
		jPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		jPanel2.add(okButton);
		jPanel2.add(cancelButton);
		add(jPanel1);
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel3);
		jPanel1.add(jLabel4);
		jPanel1.add(jLabel5);
		jPanel1.add(jLabel6);
		jPanel1.add(serviceNameTextField);
		jPanel1.add(tableNameTextField);
		jPanel1.add(isGroupComboBox);
		jPanel1.add(generationModeComboBox);
		jPanel1.add(sortFieldsTextField);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(93, 141, 203, 28);
		jPanel1.add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(field);
				pnl.edit(dialog, null);
				if (!pnl.isChange()) {
					return;
				}
				field = pnl.getSelect();
				if (!pnl.isNull()) {
					textField.setText(field.toString());
				} else {
					textField.setText("");
				}
			}
		});
		btnNewButton.setBounds(307, 142, 37, 29);
		jPanel1.add(btnNewButton);
	}

	private javax.swing.JButton cancelButton;
	private javax.swing.JComboBox generationModeComboBox;
	private javax.swing.JComboBox isGroupComboBox;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JButton okButton;
	private javax.swing.JTextField serviceNameTextField;
	private javax.swing.JTextField sortFieldsTextField;
	private javax.swing.JTextField tableNameTextField;
	private boolean submit = false;
	private Map<String, String> props;
	private JTextField textField;
	private JButton btnNewButton;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "word打印参数", this);
		dialog.setVisible(true);

	}

	private void initData() {
		String inparam = props.get("inparam");
		if (CommonUtils.isStrEmpty(inparam)) {
			return;
		}
		try {
			String[] params = inparam.split(";");
			Map<String, String> paramMap = new HashMap<String, String>();
			for (String param : params) {
				if (!"".equals(param.trim())) {
					String[] keyValue = param.split("=");
					if (keyValue.length < 2) {
						paramMap.put(keyValue[0], "");
					} else {
						paramMap.put(keyValue[0], keyValue[1]);
					}
				}
			}
			String modulefieldname = paramMap.get("modulefieldname");
			if (!CommonUtils.isStrEmpty(modulefieldname)) {
				field = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", modulefieldname);
				if (field != null) {
					textField.setText(field.toString());
				}
			}
			int genModeIndex = 0;
			String flag = paramMap.get("flag");
			if (CommonUtils.isNumberString(flag)) {
				genModeIndex = Integer.valueOf(flag);
			}
			serviceNameTextField.setText(paramMap.get("dataservicename"));
			tableNameTextField.setText(paramMap.get("tablename"));
			isGroupComboBox.setSelectedItem(paramMap.get("groupby"));
			generationModeComboBox.setSelectedIndex(genModeIndex);
			sortFieldsTextField.setText(paramMap.get("orderby"));
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
