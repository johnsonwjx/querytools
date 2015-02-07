package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.common.util.net.ServiceInvokerUtil;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.editors.valueEditors.EventModelEditor;
import youngfriend.editors.valueEditors.ModulesSelectEditor;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

public class ModulesEventEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = -9100227290671251800L;
	private javax.swing.JButton businessCmpSettingButton;
	private javax.swing.JTextField businessCmpTextField;
	private javax.swing.JButton cancelButton;
	private javax.swing.JTextArea cmpParamTextArea;
	private final LinkedHashMap<String, XMLDto> cmps = new LinkedHashMap<String, XMLDto>();
	private List<String> keys = new ArrayList<String>();
	private JDialog dialog;

	private EventModelEditor eventModelEditor;

	private javax.swing.JTextArea inParamTextArea;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JLabel jLabel4;

	private javax.swing.JLabel jLabel5;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JPanel jPanel2;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JScrollPane jScrollPane2;

	private javax.swing.JScrollPane jScrollPane3;

	private Logger logger = null;

	private javax.swing.JButton okButton;

	private JTextArea outParamTextArea;
	private JButton paramSettingButton;
	private JButton saveTemplateButton;
	private ModulesSelectEditor selComEditor;
	private JButton selectTemplateButton;
	private JComboBox sysCmpComboBox;
	private DefaultPropEditor defaultpropeditor;
	private static final String MODULE_ROOT_NODENAME = "event";
	private static final String MODULE_TYPE_NODENAME = "type";
	private static final String MODULE_ID_NODENAME = "moduleid";
	private static final String MODULE_INPARAM_NODENAME = "inparam";
	private static final String MODULE_OUTPARAM_NODENAME = "outparam";

	public ModulesEventEditor() {
		initComponents();
		init();
	}

	private void init() {
		cmps.put("", new XMLDto("参数通用设置--", "youngfriend.editors.valueEditors.CommonParamEditor"));
		cmps.put("00005001", new XMLDto("代码中心单表选择--00005001", "youngfriend.editors.valueEditors.CodeCenterSingleTableEditor"));
		cmps.put("00005002", new XMLDto("代码中心主从表选择--00005002", "youngfriend.editors.valueEditors.CodeCenterMasterSlaveTableEditor"));
		cmps.put("06010002", new XMLDto("穿透至自定义查询2.0--06010002", "youngfriend.editors.valueEditors.ToCustomqueryEditor"));
		cmps.put("00003113", new XMLDto("查看表单全貌--00003113", "youngfriend.editors.valueEditors.FormFullEditor"));
		cmps.put("00003114", new XMLDto("查看流程--00003114", "youngfriend.editors.valueEditors.WatchFlowEditor"));
		cmps.put("cq005", new XMLDto("表单修改组件--cq005", "youngfriend.editors.valueEditors.FormEditEditor"));
		cmps.put("00009019", new XMLDto("WORD打印组件--00009019", "youngfriend.editors.valueEditors.WORDPrintEditor"));
		cmps.put("cq006", new XMLDto("通用单表选择设置组件--cq006", "youngfriend.editors.valueEditors.CommonSingleTableSelectEditor"));
		cmps.put("cq007", new XMLDto("通用左树右表设置组件--cq007", "youngfriend.editors.valueEditors.CommonTreeTableEditor"));
		cmps.put("cq009", new XMLDto("打开通用业务设置组件--cq009", "youngfriend.editors.valueEditors.CommonBusinessEditor"));
		cmps.put("cq010", new XMLDto("通用更新台账表组件--cq010", "youngfriend.editors.valueEditors.CommonUpdateAccountEditor"));
		cmps.put("cq011", new XMLDto("通用自定义批量更新台账表组件--cq011", "youngfriend.editors.valueEditors.CustomBatchUpdateEditor"));
		cmps.put("cq012", new XMLDto("批量打开通用业务--cq012", "youngfriend.editors.valueEditors.BatchOpenCBEditor"));
		cmps.put("cq013", new XMLDto("打开消息中的业务单--cq013", "youngfriend.editors.valueEditors.OpenMsgEditor"));
		cmps.put("cq014", new XMLDto("获取系统参数--cq014", "youngfriend.editors.valueEditors.GetSysParamEditor"));
		for (String key : cmps.keySet()) {
			keys.add(key);
			sysCmpComboBox.addItem(cmps.get(key));
		}
		sysCmpComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					moduleSelect();
				}

			}
		});

		cmpParamTextArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				changeParam();
			}

			private void changeParam() {
				outParamTextArea.setText("");
				inParamTextArea.setText("");
				businessCmpTextField.setText("");
				sysCmpComboBox.setSelectedIndex(0);
				String xml = cmpParamTextArea.getText();
				int start = -1, end = -1;
				start = xml.indexOf(CommonUtils.createNodeName(MODULE_ID_NODENAME, false, false, false));
				end = xml.indexOf(CommonUtils.createNodeName(MODULE_ID_NODENAME, true, false, false));
				if (start == -1 || end == -1) {
					return;
				} else {
					String cmpId = xml.substring(start + MODULE_ID_NODENAME.length() + 2, end).trim();
					int index = keys.indexOf(cmpId);
					if (index != -1) {
						sysCmpComboBox.setSelectedIndex(index);
					} else {
						businessCmpTextField.setText(cmpId);
					}
				}
				start = xml.indexOf(CommonUtils.createNodeName(MODULE_INPARAM_NODENAME, false, false, false));
				end = xml.indexOf(CommonUtils.createNodeName(MODULE_INPARAM_NODENAME, true, false, false));
				if (start != -1 && end != -1) {
					inParamTextArea.setText(xml.substring(start + MODULE_INPARAM_NODENAME.length() + 2, end));
				}
				start = xml.indexOf(CommonUtils.createNodeName(MODULE_OUTPARAM_NODENAME, false, false, false));
				end = xml.indexOf(CommonUtils.createNodeName(MODULE_OUTPARAM_NODENAME, true, false, false));
				if (start != -1 && end != -1) {
					outParamTextArea.setText(xml.substring(start + MODULE_OUTPARAM_NODENAME.length() + 2, end));
				}

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changeParam();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(782, 571));
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 10, 52, 16);
		sysCmpComboBox = new javax.swing.JComboBox();
		sysCmpComboBox.setBounds(64, 6, 538, 27);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(6, 45, 52, 16);
		paramSettingButton = new javax.swing.JButton();
		paramSettingButton.setBounds(608, 11, 161, 56);
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setBounds(64, 87, 705, 122);
		cmpParamTextArea = new javax.swing.JTextArea();
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(6, 87, 52, 16);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(6, 209, 52, 16);
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane2.setBounds(64, 221, 705, 162);
		inParamTextArea = new javax.swing.JTextArea();
		inParamTextArea.setEditable(false);
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane3.setBounds(64, 395, 705, 132);
		outParamTextArea = new javax.swing.JTextArea();
		outParamTextArea.setEditable(false);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(6, 395, 52, 16);
		businessCmpTextField = new javax.swing.JTextField();
		businessCmpTextField.setEditable(false);
		businessCmpTextField.setBounds(64, 39, 504, 28);
		businessCmpSettingButton = new javax.swing.JButton();
		businessCmpSettingButton.setBounds(574, 40, 28, 29);
		jPanel2 = new javax.swing.JPanel();

		jPanel1.setName("jPanel1"); // NOI18N

		jLabel1.setText("系统组件");
		jLabel1.setName("jLabel1"); // NOI18N

		sysCmpComboBox.setName("sysCmpComboBox"); // NOI18N

		jLabel2.setText("业务组件");
		jLabel2.setName("jLabel2"); // NOI18N

		paramSettingButton.setText("参数设置");
		paramSettingButton.setName("paramSettingButton"); // NOI18N
		paramSettingButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setParam();
			}

		});

		jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane1.setAutoscrolls(true);
		jScrollPane1.setName("jScrollPane1"); // NOI18N

		cmpParamTextArea.setColumns(20);
		cmpParamTextArea.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		cmpParamTextArea.setLineWrap(true);
		cmpParamTextArea.setRows(3);
		cmpParamTextArea.setName("cmpParamTextArea"); // NOI18N
		jScrollPane1.setViewportView(cmpParamTextArea);

		jLabel3.setText("组件参数");
		jLabel3.setName("jLabel3"); // NOI18N

		jLabel4.setText("入口参数");
		jLabel4.setName("jLabel4"); // NOI18N

		jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane2.setAutoscrolls(true);
		jScrollPane2.setName("jScrollPane2"); // NOI18N

		inParamTextArea.setColumns(20);
		inParamTextArea.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		inParamTextArea.setLineWrap(true);
		inParamTextArea.setRows(3);
		inParamTextArea.setMinimumSize(new java.awt.Dimension(100, 50));
		inParamTextArea.setName("inParamTextArea"); // NOI18N
		jScrollPane2.setViewportView(inParamTextArea);

		jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane3.setAutoscrolls(true);
		jScrollPane3.setName("jScrollPane3"); // NOI18N

		outParamTextArea.setColumns(20);
		outParamTextArea.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
		outParamTextArea.setLineWrap(true);
		outParamTextArea.setRows(3);
		outParamTextArea.setName("outParamTextArea"); // NOI18N
		jScrollPane3.setViewportView(outParamTextArea);

		jLabel5.setText("出口参数");
		jLabel5.setName("jLabel5"); // NOI18N

		businessCmpTextField.setName("businessCmpTextField"); // NOI18N

		businessCmpSettingButton.setText("...");
		businessCmpSettingButton.setName("businessCmpSettingButton"); // NOI18N
		businessCmpSettingButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectBusiness();
			}
		});

		jPanel2.setEnabled(false);
		jPanel2.setName("jPanel2");
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		selectTemplateButton = new JButton();
		jPanel2.add(selectTemplateButton);

		selectTemplateButton.setText("选择模板");
		selectTemplateButton.setName("selectTemplateButton"); // NOI18N
		selectTemplateButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selModel();
			}
		});
		saveTemplateButton = new javax.swing.JButton();
		jPanel2.add(saveTemplateButton);

		saveTemplateButton.setText("存为模板");
		saveTemplateButton.setName("saveTemplateButton"); // NOI18N
		saveTemplateButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveModel();
			}
		});
		setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(200, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		jPanel2.add(separator);
		add(jPanel2, BorderLayout.SOUTH);
		okButton = new javax.swing.JButton();
		jPanel2.add(okButton);

		okButton.setText("确定");
		okButton.setName("okButton"); // NOI18N
		okButton.setPreferredSize(new java.awt.Dimension(96, 29));
		cancelButton = new javax.swing.JButton();
		jPanel2.add(cancelButton);

		cancelButton.setText("取消");
		cancelButton.setName("cancelButton"); // NOI18N
		cancelButton.setPreferredSize(new java.awt.Dimension(96, 29));
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultpropeditor.disposeDialog();
			}
		});
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultpropeditor.save();
			}
		});
		add(jPanel1, BorderLayout.CENTER);
		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jPanel1.add(jLabel3);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel4);
		jPanel1.add(jScrollPane2);
		jPanel1.add(jScrollPane1);
		jPanel1.add(businessCmpTextField);
		jPanel1.add(businessCmpSettingButton);
		jPanel1.add(sysCmpComboBox);
		jPanel1.add(paramSettingButton);
		jPanel1.add(jLabel5);
		jPanel1.add(jScrollPane3);
	}

	private void selectBusiness() {
		if (selComEditor == null) {
			selComEditor = new ModulesSelectEditor();
		}
		Map<String, String> props = new HashMap<String, String>();
		props.put("id", businessCmpTextField.getText().trim());
		selComEditor.edit(dialog, props);
		if (!selComEditor.isSubmit()) {
			return;
		}
		XMLDto dto = selComEditor.getSelect();
		if (dto == null) {
			cmpParamTextArea.setText("");
		} else {
			cmpParamTextArea.setText(getCmpParam(dto.getValue("id"), dto.getValue("inparam"), dto.getValue("outparam")));
		}
	}

	private void setParam() {
		try {
			String inparam = inParamTextArea.getText();
			String moduleid = businessCmpTextField.getText().trim();
			String outparam = outParamTextArea.getText();
			String param = cmpParamTextArea.getText();
			if (!CommonUtils.isStrEmpty(param, true)) {
				int start = param.indexOf(CommonUtils.createNodeName(MODULE_ID_NODENAME, false, false, false));
				int end = param.indexOf(CommonUtils.createNodeName(MODULE_ID_NODENAME, true, false, false));
				if (start != -1 && end != -1) {
					String paramModuleid = param.substring(start + MODULE_ID_NODENAME.length() + 2, end);
					if (CommonUtils.isStrEmpty(moduleid, true)) {
						moduleid = paramModuleid;
					} else {
						if (!paramModuleid.equals(moduleid)) {
							inparam = "";
							outparam = "";
						}
					}
				}
			}
			XMLDto cmpDto = (XMLDto) sysCmpComboBox.getSelectedItem();
			String className = cmpDto.getValue("value");
			Class<?> componentClass = this.getClass().getClassLoader().loadClass(className);
			logger.debug(className);
			Object obj = componentClass.newInstance();
			if (obj instanceof ValueEditor) {
				ValueEditor editor = (ValueEditor) obj;
				Map<String, String> props = new HashMap<String, String>();
				props.put("moduleid", moduleid);
				props.put("inparam", inparam);
				props.put("outparam", outparam);
				editor.edit(dialog, props);
				if (editor.isSubmit()) {
					moduleid = props.get("moduleid");
					inparam = props.get("inparam");
					outparam = props.get("outparam");
					param = getCmpParam(moduleid, inparam, outparam);
					cmpParamTextArea.setText(param);
				}
			}

		} catch (Exception ex) {
			GUIUtils.showMsg(dialog, "参数设置错误");
			logger.error(ex.getMessage(), ex);
		}

	}

	private String getCmpParam(String moduleid, String inparam, String outparam) {
		if (CommonUtils.isStrEmpty(moduleid, true)) {
			cmpParamTextArea.setText("");
			return "";
		}
		StringBuilder sb = new StringBuilder(CommonUtils.createNodeName(MODULE_ROOT_NODENAME, false, false, false));
		sb.append(CommonUtils.createNodeName(MODULE_TYPE_NODENAME, false, false, false)).append("A").append(CommonUtils.createNodeName(MODULE_TYPE_NODENAME, true, false, false));
		sb.append(CommonUtils.createNodeName(MODULE_ID_NODENAME, false, false, false)).append(moduleid.trim()).append(CommonUtils.createNodeName(MODULE_ID_NODENAME, true, false, false));

		if (CommonUtils.isStrEmpty(inparam, true)) {
			sb.append(CommonUtils.createNodeName(MODULE_INPARAM_NODENAME, false, false, true));
		} else {
			sb.append(CommonUtils.createNodeName(MODULE_INPARAM_NODENAME, false, false, false)).append(inparam.trim()).append(CommonUtils.createNodeName(MODULE_INPARAM_NODENAME, true, false, false));
		}

		if (CommonUtils.isStrEmpty(outparam, true)) {
			sb.append(CommonUtils.createNodeName(MODULE_OUTPARAM_NODENAME, false, false, true));
		} else {
			sb.append(CommonUtils.createNodeName(MODULE_OUTPARAM_NODENAME, false, false, false)).append(outparam.trim()).append(CommonUtils.createNodeName(MODULE_OUTPARAM_NODENAME, true, false, false));
		}
		sb.append(CommonUtils.createNodeName(MODULE_ROOT_NODENAME, true, false, false));
		return sb.toString();
	}

	private void saveModel() {// GEN-FIRST:event_saveTemplateButtonActionPerformed
		String comid = businessCmpTextField.getText();
		if (CommonUtils.isStrEmpty(comid)) {
			return;
		}
		String usersetName = JOptionPane.showInputDialog(this.dialog, "", "用户设置组件名称", JOptionPane.INFORMATION_MESSAGE);
		if (CommonUtils.isStrEmpty(usersetName.trim())) {
			GUIUtils.showMsg(dialog, "组件名不能为空");
			return;
		}
		try {
			String comcode = comid, comname = "";
			Hashtable<String, String> result = null;
			if (cmps.containsKey(comid)) {
				comname = cmps.get(comid).getValue("key").split("--")[0];
			} else {
				Properties ht = new Properties();
				ht.put("service", "module.getModuleInfo");
				ht.put("moduleID", comid);
				result = ServiceInvokerUtil.invoker(ht);
				String xml = result.get("moduleData").toString();
				if (xml.indexOf("errorMessage") > 0) {
					GUIUtils.showMsg(dialog, "调用服务【module.getModuleInfo】出错,错误信息为:" + xml);
					return;
				}
				Document doc = DocumentHelper.parseText(xml);
				Element root = doc.getRootElement();
				Element node = (Element) root.elements().get(0);
				if (node.element("name").getText() != null && node.element("name").getText().length() > 0) {
					comname = node.element("name").getText();
					comcode = node.element("code").getText();
				}
			}
			result = null;
			String condi = "COM_ID='" + comid + "' and USERSET_NAME='" + usersetName + "'";
			Properties ht = new Properties();
			ht.put("service", "customquery.com.getlist");
			ht.put("condi", condi);
			result = ServiceInvokerUtil.invoker(ht);
			String xml = result.get("XML").toString();
			String flag = "";
			if (xml != null && !CommonUtils.isStrEmpty(xml)) {
				xml.replaceFirst("UTF-8", "GBK");
				if (xml.indexOf("errorMessage") > 0) {
					GUIUtils.showMsg(dialog, "调用服务【customquery.com.getlis】出错");
					logger.debug(xml);
					return;
				}
				if (GUIUtils.showConfirm(dialog, "此模版已经存在，是否确认覆盖?")) {
					flag = "1";
				}
			}
			result = null;
			String cominfo = cmpParamTextArea.getText();
			String inparam = inParamTextArea.getText();
			String outparam = outParamTextArea.getText();
			ht = new Properties();
			ht.put("service", "customquery.com.save");
			ht.put("comid", comid);
			ht.put("comcode", comcode);
			ht.put("comname", comname);
			ht.put("usersetname", usersetName);
			ht.put("cominfo", cominfo);
			ht.put("inparam", inparam);
			ht.put("outparam", outparam);
			ht.put("flag", flag);
			result = ServiceInvokerUtil.invoker(ht);
			xml = result.get("XML").toString();
			if (CommonUtils.isStrEmpty(xml)) {
				// 模板更新成功，模板树要重新加载
				// modelChange = true;
				GUIUtils.showMsg(dialog, "保存模板成功");
			} else if (xml.indexOf("errorMessage") > 0) {
				GUIUtils.showMsg(dialog, "调用服务【customquery.com.save】出错，错误信息为：" + xml);
				logger.debug(xml);
			}
		} catch (Exception ex) {
			GUIUtils.showMsg(dialog, "保存模板错误");
			logger.error(ex.getMessage(), ex);
		}
	}

	private void selModel() {
		if (eventModelEditor == null) {
			eventModelEditor = new EventModelEditor();
		}
		Map<String, String> props = new HashMap<String, String>();
		props.put("com_id", businessCmpTextField.getText());
		eventModelEditor.edit(dialog, props);
		if (!eventModelEditor.isSubmit()) {
			return;
		}
		String info = props.get("com_info");
		cmpParamTextArea.setText(info);
	}

	private void moduleSelect() {
		int index = sysCmpComboBox.getSelectedIndex();
		if (index < 1) {
			businessCmpTextField.setText("");
			String param = cmpParamTextArea.getText();
			if (!CommonUtils.isStrEmpty(param, true)) {
				int start = param.indexOf(CommonUtils.createNodeName(MODULE_ID_NODENAME, false, false, false));
				int end = param.indexOf(CommonUtils.createNodeName(MODULE_ID_NODENAME, true, false, false));
				if (start != -1 && end != -1) {
					String paramModuleid = param.substring(start + MODULE_ID_NODENAME.length() + 2, end);
					businessCmpTextField.setText(paramModuleid);
				}
			}
		} else {
			XMLDto cmp = (XMLDto) sysCmpComboBox.getSelectedItem();
			businessCmpTextField.setText(cmp.getValue("key").split("--", 2)[1]);
		}
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {
			@Override
			public boolean save() {
				prop.setValue(cmpParamTextArea.getText());
				return true;
			}

			@Override
			public void initData() {
				cmpParamTextArea.setText(prop.getValue());
			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		logger = defaultpropeditor.getLogger();
		dialog = defaultpropeditor.getDialog();
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}
}
