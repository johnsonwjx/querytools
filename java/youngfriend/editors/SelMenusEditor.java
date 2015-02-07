/*
 * ChooseSortField.java
 *
 * Created on 2008年8月1日, 下午2:21
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.Validate;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.InputEditor;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class SelMenusEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;
	private JTree tree;

	public SelMenusEditor() {
		initComponents();
		init();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(411, 365));
		jPanel2 = new javax.swing.JPanel();
		okBT = new javax.swing.JButton();
		okBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}
		});
		jButton2 = new javax.swing.JButton();
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});

		setLayout(new java.awt.BorderLayout());

		jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

		okBT.setText("确定");
		jPanel2.add(okBT);

		jButton2.setText("取消");
		jPanel2.add(jButton2);

		add(jPanel2, java.awt.BorderLayout.SOUTH);
		sp = new javax.swing.JScrollPane();
		add(sp, BorderLayout.CENTER);
		jPanel3 = new javax.swing.JPanel();
		jPanel3.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		jPanel3.setPreferredSize(new Dimension(80, 0));
		add(jPanel3, BorderLayout.EAST);
		btnAdd = new javax.swing.JButton();
		btnAdd.setBounds(0, 18, 96, 30);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});
		jPanel3.setLayout(null);

		btnAdd.setText("添加菜单");
		jPanel3.add(btnAdd);
		btnUpdate = new javax.swing.JButton();
		btnUpdate.setBounds(0, 58, 96, 40);
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editItem();
			}
		});

		btnUpdate.setText("修改菜单");
		jPanel3.add(btnUpdate);
		btnDel = new javax.swing.JButton();
		btnDel.setBounds(0, 98, 96, 40);
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delItem();
			}
		});

		btnDel.setText("删除菜单");
		jPanel3.add(btnDel);

		button = new JButton();
		button.setBounds(0, 138, 96, 40);
		button.setText("\u4E0A\u79FB");
		jPanel3.add(button);

		button_1 = new JButton();
		button_1.setBounds(0, 178, 96, 40);
		button_1.setText("\u4E0B\u79FB");
		jPanel3.add(button_1);

		label = new JLabel("");
		label.setBounds(0, 203, 96, 40);
		jPanel3.add(label);
		btnEvent = new javax.swing.JButton();
		btnEvent.setBounds(0, 243, 96, 40);
		btnEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventSet();
			}

		});

		btnEvent.setText("事件设置");
		jPanel3.add(btnEvent);
		clearEvent = new javax.swing.JButton();
		clearEvent.setBounds(0, 283, 96, 40);
		clearEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventClear();
			}

		});

		clearEvent.setText("清除事件");
		jPanel3.add(clearEvent);
	}

	private javax.swing.JButton btnAdd;
	private javax.swing.JButton btnDel;
	private javax.swing.JButton btnEvent;
	private javax.swing.JButton btnUpdate;
	private javax.swing.JButton clearEvent;
	private javax.swing.JButton jButton2;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JButton okBT;
	private javax.swing.JScrollPane sp;
	private JLabel label;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel model;
	private DefaultPropEditor defaultpropeditor;
	private JButton button;
	private JButton button_1;

	public void init() {
		// 初始化界面
		tree = new JTree();
		sp.setViewportView(tree);
		root = new DefaultMutableTreeNode("菜单事件");
		root.setAllowsChildren(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		model = new DefaultTreeModel(root);
		tree.setModel(model);
		tree.setExpandsSelectedPaths(true);
		for (Component com : jPanel3.getComponents()) {
			com.setSize(80, 25);
		}
		CompUtils.treeMove(button, tree, true);
		CompUtils.treeMove(button_1, tree, false);
	}

	private void addChildEle(Element rootEle, DefaultMutableTreeNode node) {
		if (node.getChildCount() < 0) {
			return;
		}
		for (Enumeration<DefaultMutableTreeNode> enumNodes = node.children(); enumNodes.hasMoreElements();) {
			DefaultMutableTreeNode c = enumNodes.nextElement();
			XMLDto dto = (XMLDto) c.getUserObject();
			Element item = rootEle.addElement("item");
			item.addElement("name").setText(dto.getValue("name"));
			String eventTxt = dto.getValue("moduleinfo");
			Element moduleinfo = item.addElement("moduleinfo");
			if (!CommonUtils.isStrEmpty(eventTxt)) {
				try {
					moduleinfo.add(DocumentHelper.parseText(eventTxt).getRootElement());
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
			}
			if (c.getChildCount() > 0) {
				addChildEle(item, c);
			}
		}
	}

	private void eventClear() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null || node.isRoot()) {
			return;
		}
		XMLDto dto = (XMLDto) node.getUserObject();
		dto.setValue("hasEvent", "");
		dto.setValue("moduleinfo", "");
		tree.updateUI();
	}

	private void eventSet() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null || node.isRoot()) {
			return;
		}
		XMLDto dto = (XMLDto) node.getUserObject();
		ModulesEventEditor editor = new ModulesEventEditor();
		PropDto temp = new PropDto();
		temp.setValue(dto.getValue("moduleinfo"));
		editor.edit(temp, defaultpropeditor.getDialog());
		String eventXml = temp.getValue();
		if (CommonUtils.isStrEmpty(eventXml)) {
			dto.setValue("hasEvent", "");
		} else {
			dto.setValue("hasEvent", "已挂事件");
			eventXml = eventXml.replaceAll("\n", "");
		}
		dto.setValue("moduleinfo", eventXml);
		tree.updateUI();
	}

	private void delItem() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null || node.isRoot()) {
			return;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
		parent.remove(node);
		tree.updateUI();
	}

	private void editItem() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null || node.isRoot()) {
			return;
		}
		InputEditor editor = new InputEditor(new Validate<String>() {

			@Override
			public String validate(String obj) {
				return CommonUtils.isStrEmpty(obj) ? "不能为空" : null;
			}
		});
		XMLDto dto = (XMLDto) node.getUserObject();
		Map<String, String> props = new HashMap<String, String>();
		props.put("value", dto.getValue("name"));
		editor.edit(defaultpropeditor.getDialog(), props);
		if (!editor.isSubmit()) {
			return;
		}
		String name = props.get("value");

		dto.setValue("name", name);
		tree.updateUI();
	}

	private void addItem() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			node = root;
		}
		if (node.getLevel() >= 2) {
			GUIUtils.showMsg(defaultpropeditor.getDialog(), "最大深度为2");
			return;
		}
		InputEditor editor = new InputEditor(new Validate<String>() {

			@Override
			public String validate(String obj) {
				return CommonUtils.isStrEmpty(obj) ? "不能为空" : null;
			}

		});
		Map<String, String> props = new HashMap<String, String>();
		editor.edit(defaultpropeditor.getDialog(), props);
		if (!editor.isSubmit()) {
			return;
		}
		String name = props.get("value");
		List<String> toString = new ArrayList<String>();
		toString.add("name");
		toString.add("hasEvent");
		XMLDto dto = new XMLDto(toString);
		dto.setValue("name", name);
		dto.setValue("hasEvent", "");
		dto.setValue("moduleinfo", "");
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(dto);
		node.add(newChild);
		TreeNode[] nodes = model.getPathToRoot(newChild);
		TreePath path = new TreePath(nodes);
		tree.setSelectionPath(path);
		tree.updateUI();
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				if (root.getChildCount() < 0) {
					prop.setValue("");
				} else {
					Element rootEle = DocumentHelper.createElement("root");
					addChildEle(rootEle, root);
					prop.setValue(rootEle.asXML());
				}
				return true;
			}

			@Override
			public void initData() {
				Document doc;
				try {
					doc = DocumentHelper.parseText(prop.getValue());
					Element rootEle = doc.getRootElement();
					initTree(rootEle, root);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				if (root.getChildCount() > 0) {
					CompUtils.expandAll(tree, new TreePath(root), true);
					tree.updateUI();
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

	private void initTree(Element ele, DefaultMutableTreeNode node) throws Exception {
		List<Element> items = ele.elements("item");
		if (items.isEmpty()) {
			return;
		}
		for (Element item : items) {
			String name = item.elementText("name");
			Element moduleinfo = item.element("moduleinfo");
			List<String> toString = new ArrayList<String>();
			toString.add("name");
			toString.add("hasEvent");
			XMLDto dto = new XMLDto(toString);
			dto.setValue("name", name);
			dto.setValue("moduleinfo", "");
			dto.setValue("hasEvent", "");
			if (moduleinfo != null && moduleinfo.hasContent()) {
				Element event = moduleinfo.element("event");
				if (event != null && event.hasContent()) {
					dto.setValue("hasEvent", "已挂事件");
					dto.setValue("moduleinfo", event.asXML());
				}
			}
			DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(dto);
			node.add(newChild);
			List<Element> cc = item.elements("item");
			if (cc != null && !cc.isEmpty()) {
				initTree(item, newChild);
			}
		}
	}
}
