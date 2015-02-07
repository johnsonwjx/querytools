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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.Validate;
import youngfriend.beans.XMLDto;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

/**
 * 
 * @author yf
 */
public class SortCatalogPnl extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SortCatalogPnl.class.getName());
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JButton cancelBT;
	private javax.swing.JRadioButton downRB;
	private javax.swing.JLabel jLabel1;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JRadioButton nextRB;

	private boolean ok = false;

	private javax.swing.JButton okBT;

	private String sortType = "0";

	private JTree tree;
	private javax.swing.JRadioButton upRB;
	private String catalogid;
	private String selid;
	private JDialog dialog = null;

	public SortCatalogPnl(String catalogid) {
		this.catalogid = catalogid;
		initComponents();
		setPreferredSize(new Dimension(400, 350));
		setLayout(new BorderLayout(0, 0));
		add(jScrollPane1);
		add(jPanel1, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jLabel1 = new javax.swing.JLabel();
		panel.add(jLabel1);

		jLabel1.setText("移动方式：");
		nextRB = new javax.swing.JRadioButton();
		panel.add(nextRB);
		nextRB.setSelected(true);

		buttonGroup1.add(nextRB);
		nextRB.setText("位置下级");
		upRB = new javax.swing.JRadioButton();
		panel.add(upRB);

		buttonGroup1.add(upRB);
		upRB.setText("位置前");
		downRB = new javax.swing.JRadioButton();
		panel.add(downRB);

		buttonGroup1.add(downRB);
		downRB.setText("位置后");
		initTree();
		dialog = GUIUtils.getDialog(null, "调整目录", this);
		dialog.setVisible(true);
	}

	public String getSelid() {
		return selid;
	}

	public void addChildNode(DefaultMutableTreeNode root, List<XMLDto> cataloglist) {
		if (null == cataloglist)
			return;
		Map<String, DefaultMutableTreeNode> catalogmap = new HashMap<String, DefaultMutableTreeNode>();
		for (XMLDto catalog : cataloglist) {
			String sortcode = catalog.getValue("code");
			String id = catalog.getValue("id");
			if (id.equals(catalogid))
				continue;
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(catalog);
			catalogmap.put(sortcode, child);
			if (sortcode.length() <= 2) {
				root.add(child);
			} else {
				String psortcode = sortcode.substring(0, sortcode.length() - 2);
				DefaultMutableTreeNode pnode = catalogmap.get(psortcode);
				if (pnode != null)
					pnode.add(child);
			}
		}

	}

	// 取消事件
	private void cancelBTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelBTActionPerformed
		setOk(false);
		dialog.dispose();
	}

	/**
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * @return the tree
	 */
	public JTree getTree() {
		return tree;
	}

	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		jScrollPane1 = new javax.swing.JScrollPane();
		jPanel1 = new javax.swing.JPanel();
		okBT = new javax.swing.JButton();
		cancelBT = new javax.swing.JButton();

		okBT.setText("确定");
		okBT.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okBTActionPerformed(evt);
			}
		});

		cancelBT.setText("取消");
		cancelBT.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelBTActionPerformed(evt);
			}
		});
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel1.add(okBT);
		jPanel1.add(cancelBT);
	}

	/**
	 * 初始化树形
	 */
	public void initTree() {
		tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {

			@Override
			public String validate(XMLDto obj) {
				if ("class".equals(obj.getValue("dataType")) || catalogid.equals(obj.getValue("id"))) {
					return "remove";
				}
				return null;
			}
		}, "目录", true);

		// 展开第一级节点
		tree.expandRow(0);
		tree.updateUI();
		this.jScrollPane1.setViewportView(tree);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				treeSelectedNodeChanged(e);
			}
		});

	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	// 确定事件
	private void okBTActionPerformed(java.awt.event.ActionEvent evt) {
		if (CommonUtils.isStrEmpty(selid)) {
			GUIUtils.showMsg(dialog, "请选择一个目录节点");
			return;
		}
		if (downRB.isSelected())
			setSortType("0");
		if (upRB.isSelected())
			setSortType("1");
		if (downRB.isSelected())
			setSortType("2");
		try {
			InvokerServiceUtils.sortCatalog(catalogid, selid, sortType);
			setOk(true);
		} catch (Exception ex) {
			setOk(false);
			GUIUtils.showMsg(dialog, "调整目录顺序错误!");
			logger.error(ex.getMessage(), ex);
		}
		dialog.dispose();
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @param sortType
	 *            the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	/**
	 * @param tree
	 *            the tree to set
	 */
	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public void treeSelectedNodeChanged(TreeSelectionEvent e) {
		TreePath tp = e.getNewLeadSelectionPath();
		if (tp == null)
			return;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		Object obj = node.getUserObject();
		if (obj instanceof XMLDto)
			selid = ((XMLDto) obj).getValue("id");
		else
			selid = "";

	}
}