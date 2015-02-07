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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class ModulesSelectEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	JDialog dialog;
	DefaultMutableTreeNode treeroot;
	// 当前选中的module
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private boolean sumbit = false;
	private XMLDto select;

	public ModulesSelectEditor() {
		initComponents();
		setLayout(new BorderLayout(0, 0));
		add(panel, BorderLayout.SOUTH);
		jPanel2.setLayout(new BorderLayout(0, 0));
		jPanel2.add(panel_1);
		jPanel2.add(jTextField1, BorderLayout.NORTH);
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		add(splitPane, BorderLayout.CENTER);
		splitPane.setRightComponent(jPanel2);
		splitPane.setLeftComponent(jScrollPane1);
		initTree();

	}

	private void addChild(DefaultMutableTreeNode treeroot, List<XMLDto> modulelist, HashMap<String, DefaultMutableTreeNode> catalogMap, int[] levelLengths) {
		for (int i = 0; i < levelLengths.length; i++) {
			List<XMLDto> needRemoves = new ArrayList<XMLDto>();
			for (XMLDto module : modulelist) {
				String code = module.getValue("code");
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(module);
				if (code.length() == levelLengths[i]) {
					if (catalogMap.isEmpty()) {
						treeroot.add(child);
					} else {
						DefaultMutableTreeNode parentNode = null;
						List<String> parentCodes = new ArrayList<String>(catalogMap.keySet());
						for (int k = parentCodes.size() - 1; k >= 0; k--) {
							if (code.indexOf(parentCodes.get(k)) != -1) {
								parentNode = catalogMap.get(parentCodes.get(k));
								parentNode.add(child);
								break;
							}
						}
						if (parentNode == null) {
							treeroot.add(child);
						}
					}
					String type = module.getValue("type");
					String subtype = module.getValue("subtype");
					if (!CommonUtils.isStrEmpty(type) && "#".equals(subtype)) {
						catalogMap.put(code, child);
					}
					needRemoves.add(module);
				}
			}
			modulelist.removeAll(needRemoves);
			needRemoves.clear();
			if (modulelist.size() <= 0) {
				break;
			}
		}
	}

	private void initTree() {
		try {
			tree = new JTree();
			// 根据组件code添加，如果遗漏了节点可以根据位数继续添加
			treeroot = new DefaultMutableTreeNode("组件目录");
			DefaultTreeModel model = new DefaultTreeModel(treeroot);
			tree.setModel(model);
			HashMap<String, DefaultMutableTreeNode> catalogMap = new HashMap<String, DefaultMutableTreeNode>();
			List<XMLDto> modulelist = InvokerServiceUtils.getModuleLs("", "T", "");
			int[] levelLengths = { 4, 6, 8, 10, 12, 13, 16 };
			addChild(treeroot, modulelist, catalogMap, levelLengths);
			tree.addTreeSelectionListener(new TreeSelectionListener() {

				@Override
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (selectedNode == null || selectedNode.isRoot()) {
						select = null;
						jTextField1.setText("");
						jTextArea1.setText("");
						jTextArea2.setText("");
						return;
					}
					XMLDto selectedObj = (XMLDto) selectedNode.getUserObject();
					select = (XMLDto) selectedObj;
					jTextField1.setText(select.getValue("id"));
					jTextArea1.setText(select.getValue("inparam"));
					jTextArea2.setText(select.getValue("outparam"));
				}
			});
			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
						if (selectedNode == null) {
							return;
						}
						if (selectedNode.isLeaf()) {
							save();
						}
					}
				}
			});
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			this.jScrollPane1.setViewportView(tree);
		} catch (Exception ex) {
			GUIUtils.showMsg(dialog, "建树失败");
			logger.error(ex.getMessage(), ex);
		}
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(796, 532));
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setPreferredSize(new Dimension(380, 0));
		jPanel2.setMinimumSize(new Dimension(300, 0));
		jTextField1 = new javax.swing.JTextField();
		jTextField1.setEditable(false);
		panel_1 = new JPanel();
		panel_1.setLayout(new GridLayout(2, 1, 0, 0));
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane2.setViewportBorder(new TitledBorder(null, "\u5165\u53E3\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(jScrollPane2);
		jTextArea1 = new javax.swing.JTextArea();
		jTextArea1.setEditable(false);
		jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jTextArea1.setColumns(20);
		jTextArea1.setLineWrap(true);
		jTextArea1.setRows(5);
		jScrollPane2.setViewportView(jTextArea1);
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane3.setViewportBorder(new TitledBorder(null, "\u51FA\u53E3\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(jScrollPane3);
		jTextArea2 = new javax.swing.JTextArea();
		jTextArea2.setEditable(false);

		jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane3.setPreferredSize(new java.awt.Dimension(244, 84));
		jScrollPane3.setSize(new java.awt.Dimension(0, 0));

		jTextArea2.setColumns(20);
		jTextArea2.setLineWrap(true);
		jTextArea2.setRows(5);
		jScrollPane3.setViewportView(jTextArea2);

		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		jButton1 = new javax.swing.JButton();
		panel.add(jButton1);

		jButton1.setText("确认");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				save();
			}
		});
		jButton2 = new javax.swing.JButton();
		panel.add(jButton2);

		jButton2.setText("取消");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
	}

	public XMLDto getSelect() {
		return select;
	}

	private void save() {
		if (select == null) {
			GUIUtils.showMsg(dialog, "请选择组件");
			return;
		}
		if (CommonUtils.isStrEmpty(jTextArea1.getText().trim())) {
			GUIUtils.showMsg(dialog, "请选择有出口参数组件");
			return;
		}
		sumbit = true;
		dialog.dispose();
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextArea jTextArea2;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTree tree;
	private JPanel panel;
	private JPanel panel_1;
	private JSplitPane splitPane;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		sumbit = false;
		CompUtils.collapseTreeLevel1(tree, treeroot);
		String id = props.get("id");
		if (!CommonUtils.isStrEmpty(id)) {
			DefaultMutableTreeNode selectNode = CompUtils.getTreeNode(treeroot, "id", id);
			if (selectNode != null) {
				TreePath tp = new TreePath(selectNode.getPath());
				tree.setSelectionPath(tp);
				tree.expandPath(tp);
				tree.scrollPathToVisible(tp);
			} else {
				tree.expandRow(0);
			}
		} else {
			tree.expandRow(0);
		}
		dialog = GUIUtils.getDialog(null, "组件选择", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return sumbit;
	}
}
