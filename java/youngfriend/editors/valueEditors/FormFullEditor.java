/*
 * FormFullView.java
 *
 * Created on Dec 8, 2011, 11:46:34 AM
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class FormFullEditor extends javax.swing.JPanel implements ValueEditor {
	private final static Logger logger = LogManager.getLogger(FormFullEditor.class.getName());
	private static final long serialVersionUID = -7073583785100108168L;
	private JDialog dialog;
	private List<XMLDto> fields = CompUtils.getFields();
	private XMLDto formStyle = null;
	private XMLDto field = null;
	private List<XMLDto> styleList;

	/** Creates new form FormFullView */
	public FormFullEditor() {
		initComponents();
		init();
	}

	private void init() {
		try {
			styleList = InvokerServiceUtils.getformClassStyle("", "");
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "获取样式失败");
			logger.error(e.getMessage(), e);
		}
		openModeComboBox.addItem("表单功能");
		openModeComboBox.addItem("通用业务功能");
		versionComboBox.addItem("1.0");
		versionComboBox.addItem("2.0");
		versionComboBox.addItem("1.0+2.0");
		versionComboBox.setSelectedIndex(1);
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(528, 246));
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 40, 96, 36);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(16, 76, 82, 36);
		openModeComboBox = new javax.swing.JComboBox();
		openModeComboBox.setBounds(110, 82, 348, 27);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(16, 112, 66, 36);
		styleInfoTextField = new javax.swing.JTextField();
		styleInfoTextField.setEditable(false);
		styleInfoTextField.setBounds(110, 116, 348, 28);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(16, 148, 56, 36);
		versionComboBox = new javax.swing.JComboBox();
		versionComboBox.setBounds(110, 154, 348, 27);
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
				String formQmid = "";
				if (field != null) {
					formQmid = field.getValue("itemname");
				}
				String styleInfo = "";
				if (formStyle != null) {
					styleInfo = formStyle.getValue("styleid") + "--" + formStyle.getValue("name");
				}
				StringBuffer inParam = new StringBuffer();
				inParam.append("formQmid=").append(formQmid).append(";");
				inParam.append("openmode=").append(openModeComboBox.getSelectedIndex() + 1).append(";");
				inParam.append("version=").append(versionComboBox.getSelectedIndex()).append(";").append("styleInfo=").append(styleInfo);
				props.put("inparam", inParam.toString());
				submit = true;
				dialog.dispose();
			}
		});

		setLayout(new java.awt.BorderLayout());
		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("表单ID对应字段:");
		jLabel1.setName("jLabel1");

		jLabel2.setText("全貌打开方式:");
		jLabel2.setName("jLabel2"); // NOI18N

		openModeComboBox.setName("openModeComboBox"); // NOI18N

		jLabel3.setText("\u8868\u5355\u6837\u5F0F:");
		jLabel3.setName("jLabel3"); // NOI18N

		styleInfoTextField.setMinimumSize(new java.awt.Dimension(96, 28));

		jLabel4.setText("表单版本:");
		jLabel4.setName("jLabel4"); // NOI18N

		add(jPanel1, BorderLayout.CENTER);
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel2);
		jPanel1.add(openModeComboBox);
		jPanel1.add(jLabel3);
		jPanel1.add(styleInfoTextField);
		jPanel1.add(jLabel4);
		jPanel1.add(versionComboBox);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(114, 44, 344, 28);
		jPanel1.add(textField);
		textField.setColumns(10);

		button = new JButton("...");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				pnl.setValue(field);
				pnl.edit(dialog, null);
				if (!pnl.isChange()) {
					return;
				}
				field = pnl.getSelect();
				if (pnl.isNull()) {
					textField.setText("");
				} else {
					textField.setText(field.toString());
				}
			}
		});
		button.setBounds(470, 45, 38, 29);
		jPanel1.add(button);

		btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {

			private TreeSelectPnl<XMLDto> pnl;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pnl == null) {
					try {
						JTree tree = new JTree();
						DefaultMutableTreeNode root = new DefaultMutableTreeNode("表单样式");
						DefaultTreeModel model = new DefaultTreeModel(root);
						tree.setModel(model);
						List<XMLDto> catalogList = InvokerServiceUtils.formCatalogGet("", "");
						List<XMLDto> classList = InvokerServiceUtils.getFormClasses("", "", "", "", "");
						Map<String, DefaultMutableTreeNode> catalogNodes = CompUtils.buildTree(catalogList, root, "code", "id");
						Map<String, DefaultMutableTreeNode> classNodes = CompUtils.buildTree(root, catalogNodes, classList, "catalogid", "id");
						CompUtils.buildTree(root, classNodes, styleList, "classid", null);
						pnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {

							@Override
							public String validate(XMLDto obj) {
								return CommonUtils.isStrEmpty(obj.getValue("styleid")) ? "请选择样式" : null;
							}
						});
				} catch (Exception e2) {
						GUIUtils.showMsg(dialog, "建表单样式树失败");
						logger.error(e2.getMessage(), e2);
					}

				}
				Map<String, String> prop = new HashMap<String, String>();
				prop.put("key", "styleid");
				prop.put("value", formStyle == null ? "" : formStyle.getValue("styleid"));
				pnl.edit(dialog, prop);
				if (!pnl.isChange()) {
					return;
				}
				formStyle = pnl.getSelect();
				if (formStyle != null) {
					styleInfoTextField.setText(formStyle.toString());
				} else {
					styleInfoTextField.setText("");
				}
			}
		});
		btnNewButton.setBounds(470, 121, 38, 29);
		jPanel1.add(btnNewButton);
		jPanel2.setName("jPanel2"); // NOI18N

		cancelButton.setText("取消");

		okButton.setText("确定");

		add(jPanel2, BorderLayout.SOUTH);
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel2.add(okButton);
		jPanel2.add(cancelButton);
	}

	private javax.swing.JButton cancelButton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JButton okButton;
	private javax.swing.JComboBox openModeComboBox;
	private javax.swing.JTextField styleInfoTextField;
	private javax.swing.JComboBox versionComboBox;
	private boolean submit = false;
	private Map<String, String> props;
	private JTextField textField;
	private JButton button;
	private JButton btnNewButton;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "查看表单全貌设置", this);
		dialog.setVisible(true);
	}

	private void initData() {
		try {
			String inparam = props.get("inparam");
			if (CommonUtils.isStrEmpty(inparam)) {
				return;
			}
			String[] values = inparam.split(";");
			for (String item : values) {
				String[] temp = item.split("=");
				if (temp.length != 2) {
					continue;
				}
				String k = temp[0];
				String v = temp[1];
				if ("formQmid".equalsIgnoreCase(k)) {
					if (!CommonUtils.isStrEmpty(v)) {
						field = CommonUtils.getXmlDto(fields, "itemname", v);
						if (field != null) {
							textField.setText(field.toString());
						}
					}
				} else if ("openmode".equalsIgnoreCase(k)) {
					if (CommonUtils.isNumberString(v)) {
						openModeComboBox.setSelectedIndex(Integer.parseInt(v) - 1);
					}
				} else if ("version".equalsIgnoreCase(k)) {
					if (CommonUtils.isNumberString(v)) {
						versionComboBox.setSelectedIndex(Integer.parseInt(v));
					}
				} else if ("styleInfo".equalsIgnoreCase(k)) {
					if (!CommonUtils.isStrEmpty(v)) {
						String id = v.substring(0, v.indexOf("--"));
						formStyle = CommonUtils.getXmlDto(styleList, "styleid", id);
						if (formStyle != null) {
							styleInfoTextField.setText(formStyle.toString());
						}
					}
				}

			}
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
