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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import youngfriend.beans.TreeIconEnum;
import youngfriend.beans.TreeObj;
import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class TreeSelectPnl<T> extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JButton button;
	private JButton cancelButton;
	private JDialog dialog;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JPanel jPanel2;

	private javax.swing.JPanel jPanel3;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JButton okButton;

	private javax.swing.JTextField selectText;

	private boolean submit = false;
	private javax.swing.JTree tree;
	private Validate<XMLDto> validate;
	private DefaultMutableTreeNode initNode;

	public TreeSelectPnl(final JTree tree, final Validate<XMLDto> validate) {
		initComponents();
		setLayout(new BorderLayout(0, 0));
		add(jPanel2, BorderLayout.NORTH);
		jPanel2.setLayout(null);
		jPanel2.add(jLabel1);
		jPanel2.add(selectText);
		add(jPanel3, BorderLayout.SOUTH);
		jPanel3.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		jPanel3.add(okButton);
		jPanel3.add(cancelButton);

		button = new JButton("\u6E05\u7A7A\u9009\u62E9");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tree.clearSelection();
				submit = true;
				dialog.dispose();
			}
		});
		jPanel3.add(button);
		this.tree = tree;
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				this.setClosedIcon(TreeObj.getIcon(TreeIconEnum.CATALOG_IMG));
				this.setOpenIcon(TreeObj.getIcon(TreeIconEnum.CATALOG_OPEN_IMG));
				if (leaf) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
					Object obj = node.getUserObject();
					if (obj instanceof XMLDto) {
						XMLDto dto = (XMLDto) obj;
						if (validate != null && CommonUtils.isStrEmpty(validate.validate(dto))) {
							String type = dto.getValue("type");
							if ("P".equalsIgnoreCase(type) || "R".equalsIgnoreCase(type)) {
								this.setIcon(TreeObj.getIcon(TreeIconEnum.CIRCLE_YELLOW));
							} else {
								this.setIcon(TreeObj.getIcon(TreeIconEnum.CIRCLE_GREEN));
							}

						} else {
							this.setIcon(this.getClosedIcon());
						}
						this.setText(value.toString());
					}
				}
				return this;
			}
		});
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				String str = "";
				if (node != null && node.isLeaf()) {
					str = node.getUserObject().toString();
				}
				selectText.setText(str);
			}
		});
		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node != null && node.isLeaf()) {
					if (event.getClickCount() == 2) {
						submit();
					}
				}
			}
		});
		tree.setScrollsOnExpand(true);
		tree.setExpandsSelectedPaths(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.validate = validate;
		jScrollPane1 = new javax.swing.JScrollPane();
		add(jScrollPane1, BorderLayout.CENTER);
		this.jScrollPane1.setViewportView(tree);
		jScrollPane1.setViewportView(tree);

	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.dialog.dispose();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		if (props != null) {
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
			if (!"false".equals(props.get("init"))) {
				tree.clearSelection();
				CompUtils.collapseTreeLevel1(tree, root);
			}
			String key = props.get("key");
			String value = props.get("value");
			DefaultMutableTreeNode node = null;
			if (tree.getSelectionCount() <= 0 && !CommonUtils.isStrEmpty(value)) {
				node = CompUtils.getTreeNode(root, key, value);
				if (node != null) {
					TreePath tp = new TreePath(node.getPath());
					tree.setSelectionPath(tp);
					tree.expandPath(tp);
				}
			}
			if (node != null && tree.getSelectionCount() <= 0) {
				TreePath tp = new TreePath(node.getPath());
				tree.setSelectionPath(tp);
				tree.expandPath(tp);
			}
		}
		initNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (tree.getSelectionCount() <= 0) {
			tree.expandRow(0);
		} else {
			selectText.setText(tree.getLastSelectedPathComponent().toString());
		}
		tree.updateUI();
		submit = false;
		dialog = GUIUtils.getDialog(owner, "选择", this);
		dialog.setVisible(true);
	}

	public T getSelect() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		return (T) node.getUserObject();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(542, 569));
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setPreferredSize(new Dimension(0, 50));
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(12, 18, 112, 26);
		selectText = new javax.swing.JTextField();
		selectText.setEditable(false);
		selectText.setBounds(130, 19, 357, 26);
		jPanel3 = new javax.swing.JPanel();
		okButton = new javax.swing.JButton();
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		cancelButton = new javax.swing.JButton();

		jLabel1.setFont(new java.awt.Font("文泉驿点阵正黑", 0, 12)); // NOI18N
		jLabel1.setText("已经选择的查询样式:");

		okButton.setText("确定");

		cancelButton.setText("取消");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

	public boolean isChange() {
		if (!submit) {
			return false;
		}
		DefaultMutableTreeNode select = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (select != null) {
			return !select.equals(initNode);
		}
		if (initNode != null) {
			return !initNode.equals(select);
		}
		return false;
	}

	private void submit() {
		DefaultMutableTreeNode select = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (select.isRoot()) {
			GUIUtils.showMsg(dialog, "不能选择根节点");
			return;
		}
		if (validate != null) {
			String msg = validate.validate((XMLDto) select.getUserObject());
			if (!CommonUtils.isStrEmpty(msg)) {
				GUIUtils.showMsg(dialog, msg);
				return;
			}
		}

		submit = true;
		this.dialog.dispose();
	}
}
