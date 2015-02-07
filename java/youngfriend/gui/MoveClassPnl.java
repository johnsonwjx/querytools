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
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.TreeIconEnum;
import youngfriend.beans.TreeObj;
import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.main.MainFrame;
import youngfriend.utils.ComEum;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

/**
 * 
 * @author yf
 */
public class MoveClassPnl extends javax.swing.JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private DefaultMutableTreeNode node;
	private static final ImageIcon CATALOG_IMG = TreeObj.getIcon(TreeIconEnum.CATALOG_IMG);
	private static final ImageIcon CATALOG_OPEN_IMG = TreeObj.getIcon(TreeIconEnum.CATALOG_OPEN_IMG);

	public MoveClassPnl(DefaultMutableTreeNode node) {
		this.node = node;
		if (node == null) {
			return;
		}
		this.setPreferredSize(new Dimension(400, 350));
		initComponents();
		setLayout(new BorderLayout(0, 0));
		add(jScrollPane1);
		add(jPanel1, BorderLayout.SOUTH);
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel1.add(okBT);
		jPanel1.add(cancelBT);
		initTree();
	}

	/**
	 * 初始化树形
	 */
	public void initTree() {
		tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {
			@Override
			public String validate(XMLDto obj) {
				if ("class".equals(obj.getValue("dataType"))) {
					return "remove";
				}
				return null;
			}
		}, "目录", true);
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				if (expanded) {
					this.setIcon(CATALOG_OPEN_IMG);
				} else {
					this.setIcon(CATALOG_IMG);
				}
				return this;
			}
		});
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		try {
			// 展开第一级节点
			tree.expandRow(0);
			tree.setShowsRootHandles(true);
			this.jScrollPane1.setViewportView(tree);
			tree.updateUI();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			GUIUtils.showMsg(dialog, "读取目录信息出错");
		}
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

	private void save() {
		try {
			DefaultMutableTreeNode select = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if (select == null) {
				GUIUtils.showMsg(dialog, "请选择目录");
				return;
			}
			XMLDto selectObj = (XMLDto) select.getUserObject();
			DefaultMutableTreeNode oldParentNode = (DefaultMutableTreeNode) node.getParent();
			XMLDto xmldto = (XMLDto) oldParentNode.getUserObject();
			if (xmldto.getValue("catalogid").equals(selectObj.getValue("id"))) {
				GUIUtils.showMsg(dialog, "请选择其他目录");
				return;
			}
			JTree styleTree = MainFrame.getInstance().getLeftTree().getTree();
			DefaultMutableTreeNode styleParentNode = CompUtils.getTreeNode((DefaultMutableTreeNode) styleTree.getModel().getRoot(), "id", selectObj.getValue("id"));
			if (styleParentNode == null) {
				GUIUtils.showMsg(dialog, "找不到查询目录");
				return;
			}
			XMLDto parentObj = (XMLDto) styleParentNode.getUserObject();
			XMLDto userObj = (XMLDto) node.getUserObject();
			InvokerServiceUtils.moveClass(parentObj.getValue("id"), userObj.getValue("classid"));
			oldParentNode.remove(node);
			userObj.setValue("catalogid", parentObj.getValue("id"));
			userObj.setValue("catalogcode", parentObj.getValue("catalogcode"));
			userObj.setValue("catalogname", parentObj.getValue("catalogname"));
			userObj.setValue("catalogdesc", parentObj.getValue("catalogdesc"));
			styleParentNode.add(node);
			styleTree.updateUI();
			styleTree.setSelectionPath(new TreePath(node.getPath()));
			submit = true;
			dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		dialog = GUIUtils.getDialog(owner, "移动查询类", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
