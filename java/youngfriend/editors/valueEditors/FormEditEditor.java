/*
 * FormEditPnl.java
 *
 * Created on Dec 29, 2011, 6:59:32 PM
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class FormEditEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LogManager.getLogger(FormEditEditor.class.getName());
	private JDialog dialog;
	private List<XMLDto> fields = CompUtils.getFields();
	private XMLDto field;
	private XMLDto formStyle;

	public FormEditEditor() {
		initComponents();
		initComboBox();
	}

	private void initComboBox() {
		formversionComboBox.removeAllItems();
		formversionComboBox.addItem("1");
		formversionComboBox.addItem("2");
		usermodeComboBox.removeAllItems();
		usermodeComboBox.addItem("发送给原申请人(默认之前处理)");
		usermodeComboBox.addItem("发送给修改流程的申请人");

	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(562, 173));
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(12, 24, 95, 16);
		flowValueTextField = new javax.swing.JTextField();
		flowValueTextField.setBounds(125, 18, 359, 28);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(12, 56, 44, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(12, 88, 150, 16);
		formversionComboBox = new javax.swing.JComboBox();
		formversionComboBox.setBounds(61, 84, 101, 27);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(222, 92, 56, 16);
		usermodeComboBox = new javax.swing.JComboBox();
		usermodeComboBox.setBounds(284, 88, 245, 27);
		jPanel3 = new javax.swing.JPanel();
		jPanel3.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
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
				String formid = field == null ? "" : field.getValue("itemname");
				int version = formversionComboBox.getSelectedItem() == null ? 2 : Integer.parseInt(String.valueOf(formversionComboBox.getSelectedItem()));
				int usermode = usermodeComboBox.getSelectedIndex();
				String flowValue = formStyle == null ? "" : formStyle.getValue("code");
				props.put("inparam", "flowValue=" + flowValue + ";formid=" + formid + ";formversion=" + version + ";usermode=" + usermode);
				submit = true;
				dialog.dispose();
			}
		});

		jPanel2.setName("jPanel2"); // NOI18N

		jLabel1.setText("已经选择的流程:");
		jLabel1.setName("jLabel1"); // NOI18N

		flowValueTextField.setName("flowValueTextField"); // NOI18N

		jLabel2.setText("表单ID:");
		jLabel2.setName("jLabel2");

		jLabel3.setText("版本号:");
		jLabel3.setName("jLabel3"); // NOI18N

		formversionComboBox.setName("formversionComboBox"); // NOI18N

		jLabel4.setText("发送模式:");
		jLabel4.setName("jLabel4"); // NOI18N

		usermodeComboBox.setName("usermodeComboBox");

		jPanel3.setName("jPanel3"); // NOI18N

		cancelButton.setText("取消");

		okButton.setText("确定");
		setLayout(new BorderLayout(0, 0));
		add(jPanel2, BorderLayout.CENTER);
		jPanel2.setLayout(null);
		jPanel2.add(jLabel1);
		jPanel2.add(flowValueTextField);
		jPanel2.add(jLabel2);
		jPanel2.add(jLabel3);
		jPanel2.add(formversionComboBox);
		jPanel2.add(jLabel4);
		jPanel2.add(usermodeComboBox);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(125, 50, 359, 28);
		jPanel2.add(textField);
		textField.setColumns(10);

		JButton button_1 = new JButton("...");
		button_1.addActionListener(new ActionListener() {
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
		button_1.setBounds(496, 51, 33, 29);
		jPanel2.add(button_1);

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			private TreeSelectPnl<XMLDto> pnl;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pnl == null) {
					try {
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
						pnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {

							@Override
							public String validate(XMLDto obj) {
								return CommonUtils.isStrEmpty(obj.getValue("catalogcode")) ? "请选择工作流类" : null;
							}
						});
					} catch (Exception e2) {
						GUIUtils.showMsg(dialog, "建表单样式树失败");
						logger.error(e2.getMessage(), e2);
					}

				}
				Map<String, String> prop = new HashMap<String, String>();
				prop.put("init", "false");
				pnl.edit(dialog, prop);
				if (!pnl.isChange()) {
					return;
				}
				formStyle = pnl.getSelect();
				if (formStyle != null) {
					flowValueTextField.setText(formStyle.toString());
				} else {
					flowValueTextField.setText("");
				}
			}
		});
		button.setBounds(496, 19, 33, 29);
		jPanel2.add(button);
		add(jPanel3, BorderLayout.SOUTH);
		jPanel3.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		jPanel3.add(okButton);
		jPanel3.add(cancelButton);
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

	private javax.swing.JButton cancelButton;
	private javax.swing.JTextField flowValueTextField;
	private javax.swing.JComboBox formversionComboBox;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JButton okButton;
	private javax.swing.JComboBox usermodeComboBox;
	private boolean submit = false;
	private Map<String, String> props;
	private JTextField textField;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		initData();
		dialog = GUIUtils.getDialog(owner, "表单修改组件设置", this);
		dialog.setVisible(true);

	}

	// flowValue=2ee6568b012eec712bf3110325175603;formid=xx;formversion=2;usermode=0
	private void initData() {
		try {
			String inparam = props.get("inparam");
			if (CommonUtils.isStrEmpty(inparam)) {
				return;
			}
			String[] temp = inparam.split(";");
			for (String item : temp) {
				String[] kv = item.split("=");
				if (kv.length != 2) {
					continue;
				}
				String key = kv[0];
				String v = kv[1];
				if ("flowValue".equals(key)) {
					if (!CommonUtils.isStrEmpty(v)) {
						List<XMLDto> f = InvokerServiceUtils.getFlowClasses("", v);
						if (f != null && !f.isEmpty()) {
							formStyle = f.get(0);
							flowValueTextField.setText(formStyle.toString());
						}
					}
				} else if ("formid".equals(key)) {
					if (!CommonUtils.isStrEmpty(v)) {
						field = CommonUtils.getXmlDto(fields, "itemname", v);
						if (field != null) {
							textField.setText(field.toString());
						}
					}
				} else if ("formversion".equals(key)) {
					if (CommonUtils.isNumberString(v)) {
						formversionComboBox.setSelectedItem(v);
					}
				} else if ("usermode".equals(key)) {
					if (CommonUtils.isNumberString(v)) {
						usermodeComboBox.setSelectedIndex(Integer.parseInt(v));
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
