package youngfriend.editors.valueEditors;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.TreeIconEnum;
import youngfriend.beans.TreeObj;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class ReportSelectEditor extends JPanel implements ValueEditor {
	private JTree tree;
	private DefaultMutableTreeNode root;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private TreeModel model;

	public ReportSelectEditor() {
		this.setPreferredSize(new Dimension(533, 576));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("\u62A5\u8868\u6D41\u7A0B");
		panel.add(label);

		textField = new JTextField();
		textField.setText("---");
		textField.setEditable(false);
		panel.add(textField);
		textField.setColumns(38);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		tree = new JTree();
		scrollPane.setViewportView(tree);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel_1.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel_1.add(button_1);

		JButton button_2 = new JButton("\u6E05\u9664");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tree.clearSelection();
				sumbit = true;
				dialog.dispose();
			}
		});
		panel_1.add(button_2);
		init();
	}

	private void init() {
		try {
			tree.setRootVisible(false);
			root = new DefaultMutableTreeNode();
			model = CompUtils.buildReportTreeModel(root);
			tree.setCellRenderer(new DefaultTreeCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
					super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
					this.setClosedIcon(TreeObj.getIcon(TreeIconEnum.CATALOG_IMG));
					this.setOpenIcon(TreeObj.getIcon(TreeIconEnum.CATALOG_OPEN_IMG));
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
					Object obj = node.getUserObject();
					if (obj instanceof XMLDto) {
						XMLDto dto = (XMLDto) obj;
						if ("true".equals(dto.getValue("leaf"))) {
							this.setIcon(TreeObj.getIcon(TreeIconEnum.CIRCLE_GREEN));
						} else {
							this.setIcon(this.getClosedIcon());
							if (expanded) {
								this.setIcon(this.getOpenIcon());
							}
						}
					}
					return this;
				}
			});
			tree.setModel(model);
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (node == null) {
						textField.setText("---");
						return;
					}
					XMLDto userObj = (XMLDto) node.getUserObject();
					if ("true".equals(userObj.getValue("leaf"))) {
						textField.setText(userObj.getValue("reportid") + "---" + userObj.getValue("name"));
					} else {
						textField.setText("---");
					}
				}
			});
			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						XMLDto dto = getSelect();
						if (dto == null || "false".equals(dto.getValue("leaf"))) {
							return;
						}
						sumbit = true;
						dialog.dispose();
					}
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			GUIUtils.showMsg(dialog, "获取报表失败");
		}

	}

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private boolean sumbit;
	private JDialog dialog;

	private void save() {
		sumbit = true;
		dialog.dispose();
	}

	public XMLDto getSelect() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		Object obj = node.getUserObject();
		if (!(obj instanceof XMLDto)) {
			return null;
		}
		return (XMLDto) obj;
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		sumbit = false;
		if (props != null) {
			String value = props.get("value");
			if (!CommonUtils.isStrEmpty(value)) {
				DefaultMutableTreeNode node = CompUtils.getTreeNode(root, "id", value);
				if (node != null) {
					TreePath tp = new TreePath(node.getPath());
					tree.setSelectionPath(tp);
				}
			}
		}
		dialog = GUIUtils.getDialog(owner, "报表选择", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return sumbit;
	}

}
