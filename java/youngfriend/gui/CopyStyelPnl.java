package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Element;

import youngfriend.beans.XMLDto;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class CopyStyelPnl extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JDialog dailog;
	private JTree tree;
	private DefaultMutableTreeNode node;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private XMLDto parentObj;

	/**
	 * Create the panel.
	 * 
	 * @param node
	 * @param tree
	 */
	public CopyStyelPnl(JTree tree, DefaultMutableTreeNode node) {
		this.setPreferredSize(new Dimension(449, 146));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dailog.dispose();
			}
		});
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel label = new JLabel("\u6837\u5F0F\u540D\u79F0");
		label.setBounds(6, 18, 90, 16);
		panel_1.add(label);

		textField = new JTextField();
		textField.setBounds(108, 12, 301, 28);
		panel_1.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setBounds(108, 58, 269, 28);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		JLabel label_2 = new JLabel("\u590D\u5236\u5230\u67E5\u8BE2\u7C7B\uFF1A");
		label_2.setBounds(6, 64, 90, 16);
		panel_1.add(label_2);

		JButton button_2 = new JButton("...");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectClass();
			}
		});
		button_2.setBounds(389, 58, 33, 29);
		panel_1.add(button_2);
		this.tree = tree;
		this.node = node;
		if (tree == null || node == null) {
			return;
		}
		DefaultMutableTreeNode classNode = (DefaultMutableTreeNode) node.getParent().getParent();
		parentObj = (XMLDto) classNode.getUserObject();
		textField_1.setText(parentObj.getValue("name"));
		XMLDto obj = (XMLDto) node.getUserObject();
		textField.setText("复制" + obj.getValue("stylename"));
		dailog = GUIUtils.getDialog(MainFrame.getInstance(), "复制样式", this);
		dailog.setResizable(false);
		dailog.setVisible(true);
	}

	private void selectClass() {
		SelectClassPnl pnl = new SelectClassPnl(parentObj);
		pnl.edit(dailog, null);
		if (!pnl.isSubmit()) {
			return;
		}
		XMLDto sel = pnl.getSelect();
		if (sel.getValue("classid").equals(parentObj.getValue("classid"))) {
			return;
		}
		parentObj = sel;
		textField_1.setText(sel.getValue("name"));
	}

	private void save() {
		try {
			if (parentObj == null) {
				return;
			}
			Element ele = CompUtils.getStyleMain().getStyleEle();
			if (ele == null || !ele.hasContent()) {
				return;
			}
			XMLDto obj = (XMLDto) node.getUserObject();
			String classid = parentObj.getValue("classid");
			DefaultMutableTreeNode parent = null;
			XMLDto dto = CommonUtils.cloneDto(obj);
			dto.setValue("name", textField.getText());
			dto.setValue("id", "");
			dto.setValue("styleid", "");
			dto.setValue("classid", classid);
			DefaultTreeModel model = ((DefaultTreeModel) tree.getModel());
			if (classid.equals(obj.getValue("classid"))) {
				parent = (DefaultMutableTreeNode) node.getParent();
			} else {
				DefaultMutableTreeNode classNode = CompUtils.getTreeNode((DefaultMutableTreeNode) model.getRoot(), "id", classid);
				if (classNode == null) {
					GUIUtils.showMsg(dailog, "复制到查询类没找到");
					return;
				}
				if ("P".equalsIgnoreCase(obj.getValue("type"))) {
					parent = (DefaultMutableTreeNode) classNode.getLastChild();
				} else {
					parent = (DefaultMutableTreeNode) classNode.getFirstChild();
				}
				parentObj = (XMLDto) classNode.getUserObject();
				dto.setValue("catalogid", parentObj.getValue("catalogid"));
				dto.setValue("catalogcode", parentObj.getValue("catalogcode"));
				dto.setValue("catalogname", parentObj.getValue("catalogid"));
				dto.setValue("catalogdesc", parentObj.getValue("catalogdesc"));
				dto.setValue("classname", parentObj.getValue("classname"));
				dto.setValue("classalias", parentObj.getValue("classalias"));
				dto.setValue("classversion", parentObj.getValue("classversion"));
				dto.setValue("classdesc", parentObj.getValue("classdesc"));
			}
			dto.setValue("sortnumber", InvokerServiceUtils.getMaxSortNumber(classid));
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ele.asXML();
			String newid = InvokerServiceUtils.newStyle(dto, xml);
			dto.setValue("styleid", newid);
			dto.setValue("id", newid);
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(dto);
			parent.add(newNode);
			TreeNode[] nodes = model.getPathToRoot(newNode);
			TreePath path = new TreePath(nodes);
			tree.scrollPathToVisible(path);
			tree.setSelectionPath(path);
			tree.updateUI();
			dailog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dailog, "复制错误");
			logger.error(e.getMessage(), e);
		}
	}
}
