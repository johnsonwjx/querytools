/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SortCatalogPnl.java
 *
 * Created on 2011-12-6, 9:47:53
 */
package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import youngfriend.beans.TreeIconEnum;
import youngfriend.beans.TreeObj;
import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.main.MainFrame;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class SelectClassPnl extends javax.swing.JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private static final ImageIcon CATALOG_IMG = TreeObj.getIcon(TreeIconEnum.CATALOG_IMG);
	private static final ImageIcon CATALOG_OPEN_IMG = TreeObj.getIcon(TreeIconEnum.CATALOG_OPEN_IMG);
	private static final ImageIcon circle_green = TreeObj.getIcon(TreeIconEnum.CIRCLE_GREEN);

	public SelectClassPnl(XMLDto init) {
		this.setPreferredSize(new Dimension(400, 350));
		initComponents();
		setLayout(new BorderLayout(0, 0));
		add(jScrollPane1);
		add(jPanel1, BorderLayout.SOUTH);
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel1.add(okBT);
		jPanel1.add(cancelBT);
		this.init = init;
		initTree();
	}

	/**
	 * 初始化树形
	 */
	public void initTree() {
		tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {

			@Override
			public String validate(XMLDto obj) {
				String dataType = obj.getValue("dataType");
				if ("class".equals(dataType)) {
					return "ingoneChildren";
				}
				return null;
			}
		}, "查询类", true);
		tree.setExpandsSelectedPaths(true);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (!(node.getUserObject() instanceof XMLDto)) {
					return;
				}
				XMLDto obj = (XMLDto) node.getUserObject();
				if (!"class".equals(obj.getValue("type"))) {
					return;
				}
				if (e.getClickCount() < 2) {
					return;
				}
				save();
			}
		});
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				this.setOpenIcon(CATALOG_OPEN_IMG);
				this.setClosedIcon(CATALOG_IMG);
				if (node.isRoot()) {
					return this;
				}
				Object obj = node.getUserObject();
				if (!(obj instanceof XMLDto)) {
					return this;
				}
				if ("class".equals(((XMLDto) obj).getValue("dataType"))) {
					this.setIcon(circle_green);
				} else {
					this.setIcon(CATALOG_IMG);
				}
				return this;
			}
		});
		if (init != null) {
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
			DefaultMutableTreeNode selNode = CompUtils.getTreeNode(root, "id", init.getValue("id"));
			if (selNode != null) {
				tree.setSelectionPath(new TreePath(model.getPathToRoot(selNode)));
			} else {
				tree.expandRow(0);
			}
		} else {
			tree.expandRow(0);
		}
		tree.updateUI();
		jScrollPane1.setViewportView(tree);

	}

	private void initComponents() {
		jScrollPane1 = new javax.swing.JScrollPane();
		jPanel1 = new javax.swing.JPanel();
		okBT = new javax.swing.JButton();
		okBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		cancelBT = new javax.swing.JButton();

		okBT.setText("确定");

		cancelBT.setText("取消");
		cancelBT.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
	}

	private javax.swing.JButton cancelBT;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JButton okBT;
	private boolean submit;
	private JDialog dialog;
	private XMLDto init;

	public XMLDto getSelect() {
		if (!submit) {
			return init;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		return (XMLDto) node.getUserObject();
	}

	private void save() {
		DefaultMutableTreeNode select = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (select == null || select.isRoot() || !"class".equals(((XMLDto) select.getUserObject()).getValue("dataType"))) {
			GUIUtils.showMsg(dialog, "请选择查询类");
			return;
		}
		submit = true;
		dialog.dispose();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		dialog = GUIUtils.getDialog(owner, "选择查询类", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
