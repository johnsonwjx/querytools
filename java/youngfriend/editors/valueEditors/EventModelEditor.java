/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

/**
 * 
 * @author xiong
 */
public class EventModelEditor extends javax.swing.JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;

	public EventModelEditor() {
		initComponents();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent tse) {
				if (tse.getNewLeadSelectionPath() != null) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tse.getNewLeadSelectionPath().getLastPathComponent();
					if (treeNode.isLeaf()) {
						Object obj = treeNode.getUserObject();
						if (obj instanceof XMLDto) {
							XMLDto treeobj = (XMLDto) obj;
							com_idTxt.setText(treeobj.getValue("com_id"));
							com_codeTxt.setText(treeobj.getValue("com_code"));
							com_nameTxt.setText(treeobj.getValue("com_name"));
							com_nameTxt.setText(treeobj.getValue("com_name"));
							userset_nameTxt.setText(treeobj.getValue("userset_name"));
							com_infoTxt.setText(treeobj.getValue("com_info"));
							inparamTa.setText(treeobj.getValue("inparam"));
							outparamTa.setText(treeobj.getValue("outparam"));
						}
					} else {
						com_idTxt.setText("");
						com_codeTxt.setText("");
						com_nameTxt.setText("");
						userset_nameTxt.setText("");
						com_infoTxt.setText("");
						inparamTa.setText("");
						outparamTa.setText("");
					}
				}

			}
		});
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(794, 705));
		jPanel2 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jPanel3.setBounds(6, 6, 524, 652);
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane2.setViewportBorder(new TitledBorder(null, "\u51FA\u53E3\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane2.setBounds(12, 537, 474, 109);
		outparamTa = new javax.swing.JTextArea();
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane3.setViewportBorder(new TitledBorder(null, "\u5165\u53E3\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jScrollPane3.setBounds(12, 405, 474, 109);
		inparamTa = new javax.swing.JTextArea();
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(12, 18, 474, 18);
		com_idTxt = new javax.swing.JTextField();
		com_idTxt.setBounds(12, 42, 474, 28);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(12, 76, 474, 16);
		com_codeTxt = new javax.swing.JTextField();
		com_codeTxt.setBounds(12, 98, 474, 28);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(12, 132, 474, 16);
		com_nameTxt = new javax.swing.JTextField();
		com_nameTxt.setBounds(12, 154, 474, 28);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(12, 188, 474, 16);
		userset_nameTxt = new javax.swing.JTextField();
		userset_nameTxt.setBounds(12, 210, 474, 28);
		submitBtn = new javax.swing.JButton();
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		submitBtn.setBounds(378, 670, 75, 29);
		cencle = new javax.swing.JButton();
		cencle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		cencle.setBounds(465, 670, 75, 29);

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("组件具体信息"));

		jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		outparamTa.setColumns(20);
		outparamTa.setLineWrap(true);
		outparamTa.setRows(5);
		jScrollPane2.setViewportView(outparamTa);

		jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		inparamTa.setColumns(20);
		inparamTa.setLineWrap(true);
		inparamTa.setRows(5);
		jScrollPane3.setViewportView(inparamTa);

		jLabel3.setText("组件ID");

		jLabel4.setText("组件代码");

		jLabel5.setText("组件名称");

		jLabel6.setText("用户设置组件名称");

		submitBtn.setText("确定");

		cencle.setText("取消");
		setLayout(new BorderLayout(0, 0));
		add(jPanel2);
		jPanel2.setLayout(null);
		jPanel2.add(submitBtn);
		jPanel2.add(cencle);
		jPanel2.add(jPanel3);
		jPanel3.setLayout(null);
		jPanel3.add(jScrollPane3);
		jPanel3.add(jScrollPane2);
		jPanel3.add(userset_nameTxt);
		jPanel3.add(jLabel6);
		jPanel3.add(com_nameTxt);
		jPanel3.add(jLabel5);
		jPanel3.add(com_codeTxt);
		jPanel3.add(jLabel4);
		jPanel3.add(com_idTxt);
		jPanel3.add(jLabel3);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u7EC4\u4EF6\u57FA\u672C\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(12, 272, 474, 109);
		jPanel3.add(scrollPane_1);

		com_infoTxt = new JTextArea();
		com_infoTxt.setLineWrap(true);
		scrollPane_1.setViewportView(com_infoTxt);

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.WEST);
		tree = new javax.swing.JTree();
		scrollPane.setViewportView(tree);
		scrollPane.setPreferredSize(new Dimension(250, 0));
		tree.setBorder(javax.swing.BorderFactory.createTitledBorder("组件模板树"));
	}

	private javax.swing.JButton cencle;
	private javax.swing.JTextField com_codeTxt;
	private javax.swing.JTextField com_idTxt;
	private javax.swing.JTextField com_nameTxt;
	private javax.swing.JTextArea inparamTa;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTextArea outparamTa;
	private javax.swing.JButton submitBtn;
	private javax.swing.JTree tree;
	private javax.swing.JTextField userset_nameTxt;
	private JScrollPane scrollPane;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private boolean sbumit;
	private Map<String, String> props;
	private JDialog dialog;
	private JScrollPane scrollPane_1;
	private JTextArea com_infoTxt;

	private void buildTree(List<XMLDto> catalogs, List<XMLDto> leafs) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("所有模板");
		DefaultTreeModel tm = new DefaultTreeModel(root);
		for (XMLDto s : catalogs) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(s);
			root.add(child);
		}
		for (XMLDto s : leafs) {
			DefaultMutableTreeNode catalog = getCatalog(root, s);
			if (catalog != null) {
				catalog.add(new DefaultMutableTreeNode(s));
			} else {
				root.add(new DefaultMutableTreeNode(s));
			}
		}

		tree.setModel(tm);
	}

	private DefaultMutableTreeNode getCatalog(DefaultMutableTreeNode root, XMLDto s) {
		String com_name = s.getValue("com_name");
		if (CommonUtils.isStrEmpty(com_name)) {
			return null;
		}
		for (Enumeration<DefaultMutableTreeNode> enums = root.children(); enums.hasMoreElements();) {
			DefaultMutableTreeNode c = enums.nextElement();
			Object userObj = c.getUserObject();
			if (userObj instanceof XMLDto) {
				XMLDto treeNode = (XMLDto) userObj;
				if (com_name.equals(treeNode.getValue("com_name")))
					return c;
			}
		}
		return null;
	}

	private void save() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null || !node.isLeaf()) {
			GUIUtils.showMsg(dialog, "请选择底层模板");
			return;
		}
		XMLDto dto = (XMLDto) node.getUserObject();
		props.put("com_id", dto.getValue("com_id"));
		props.put("inparam", dto.getValue("inparam"));
		props.put("outparam", dto.getValue("outparam"));
		props.put("com_info", dto.getValue("com_info"));
		sbumit = true;
		dialog.dispose();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		sbumit = false;
		this.props = props;
		try {
			List<XMLDto> catalogs = InvokerServiceUtils.getComModels("1");
			if (catalogs == null) {
				GUIUtils.showMsg(dialog, "模板为空");
				return;
			}
			List<XMLDto> leafs = InvokerServiceUtils.getComModels("0");
			if (leafs == null || leafs.isEmpty()) {
				GUIUtils.showMsg(dialog, "模板为空");
				return;
			}
			buildTree(catalogs, leafs);
			DefaultMutableTreeNode node = CompUtils.getTreeNode((DefaultMutableTreeNode) tree.getModel().getRoot(), "com_id", props.get("com_id"));
			if (node != null) {
				TreePath tp = new TreePath(node.getPath());
				tree.setSelectionPath(tp);
			}
			dialog = GUIUtils.getDialog(owner, "模板选择", this);
			dialog.setVisible(true);
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "初始化错误");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean isSubmit() {
		return sbumit;
	}
}
