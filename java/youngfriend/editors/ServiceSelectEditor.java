package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import youngfriend.beans.PropDto;
import youngfriend.beans.TreeIconEnum;
import youngfriend.beans.TreeObj;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;

public class ServiceSelectEditor extends JPanel implements PropEditor {
	private JTree tree;
	private JTextArea textArea;

	public ServiceSelectEditor() {
		this.setPreferredSize(new Dimension(740, 523));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}
		});
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel.add(button_1);

		JScrollPane scrollPane = new JScrollPane();
		tree = new JTree();
		scrollPane.setViewportView(tree);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(320, 0));
		panel_1.setMinimumSize(new Dimension(320, 0));
		panel_1.setBorder(new TitledBorder(null, "\u5DF2\u9009\u670D\u52A1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);

		JLabel label = new JLabel("\u4E2D\u6587\u540D:");
		label.setBounds(20, 20, 61, 16);
		panel_1.add(label);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(20, 37, 279, 28);
		panel_1.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(20, 97, 279, 28);
		panel_1.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(20, 156, 279, 28);
		panel_1.add(textField_3);
		textField_3.setColumns(10);

		JLabel label_1 = new JLabel("\u7F16\u7801");
		label_1.setBounds(20, 137, 61, 16);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("\u63CF\u8FF0");
		label_2.setBounds(20, 194, 61, 16);
		panel_1.add(label_2);

		textArea = new JTextArea();
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBounds(20, 216, 279, 246);
		panel_1.add(textArea);

		JLabel label_3 = new JLabel("\u540D\u79F0");
		label_3.setBounds(20, 77, 61, 16);
		panel_1.add(label_3);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		add(splitPane, BorderLayout.CENTER);
		scrollPane.setPreferredSize(new Dimension(400, 0));
		splitPane.setRightComponent(panel_1);
		splitPane.setLeftComponent(scrollPane);
		init();
	}

	private void init() {
		tree.setExpandsSelectedPaths(true);
		tree.setScrollsOnExpand(true);
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
					String sName = dto.getValue("servicename");
					if (CommonUtils.isStrEmpty(sName) || sName.indexOf("#") >= 0) {
						this.setIcon(this.getClosedIcon());
						if (expanded) {
							this.setIcon(this.getOpenIcon());
						}
					} else {
						this.setIcon(TreeObj.getIcon(TreeIconEnum.CIRCLE_GREEN));
					}
				}
				return this;
			}
		});

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				String servicecname = "", servicename = "", description = "", code = "";
				if (node != null) {
					Object obj = node.getUserObject();
					if (obj != null && (obj instanceof XMLDto)) {
						XMLDto dto = (XMLDto) obj;
						String sName = dto.getValue("servicename");
						if (!CommonUtils.isStrEmpty(sName) && sName.indexOf("#") < 0) {
							servicename = sName;
							servicecname = dto.getValue("servicecname");
							description = dto.getValue("description");
							code = dto.getValue("code");
						}
					}
				}
				textField_1.setText(servicecname);
				textField_2.setText(servicename);
				textField_3.setText(code);
				textArea.setText(description);
			}
		});

	}

	private static final long serialVersionUID = 1L;
	private DefaultPropEditor defaultpropeditor;
	private DefaultTreeModel model;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	@Override
	public void edit(final PropDto prop, Window owner) {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("服务");
		model = new DefaultTreeModel(root);
		tree.setModel(model);
		List<XMLDto> list = null;
		try {
			list = InvokerServiceUtils.getServices();
			if (list.isEmpty()) {
				GUIUtils.showMsg(owner, "没获取到服务");
				return;
			}
		} catch (Exception e) {
			GUIUtils.showMsg(owner, "请更新最新的Module系统模块服务");
			defaultpropeditor.getLogger().error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		CompUtils.buildTree(list, root, "code", null);
		tree.expandRow(0);
		IPropEditorOper oper = new IPropEditorOper() {
			@Override
			public boolean save() {
				prop.setValue(textField_2.getText().trim());
				return true;
			}

			@Override
			public void initData() {
				String value = prop.getValue();
				DefaultMutableTreeNode node = CompUtils.getTreeNode(root, "servicename", value.trim());
				if (node != null) {
					TreePath path = new TreePath(node.getPath());
					tree.setSelectionPath(path);
				}
			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();

	}
}
