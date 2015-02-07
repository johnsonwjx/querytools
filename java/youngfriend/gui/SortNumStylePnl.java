package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.XMLDto;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class SortNumStylePnl extends JPanel {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private DefaultTableModel model;
	private JButton button_3;
	private JButton button_2;
	private DefaultMutableTreeNode node;
	private JTree tree;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private JTable table;
	private String[] header = { "序号", "样式" };
	private boolean sort = false;

	public SortNumStylePnl(Window window, DefaultMutableTreeNode node, final JTree tree) {
		if (node == null | node.getChildCount() <= 0) {
			return;
		}
		this.setPreferredSize(new Dimension(356, 529));
		setLayout(new BorderLayout(0, 0));
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);

		button_2 = new JButton("\u4E0A\u79FB");
		panel_1.add(button_2);

		button_3 = new JButton("\u4E0B\u79FB");
		panel_1.add(button_3);

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
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		panel_1.add(button_1);
		dialog = GUIUtils.getDialog(window, "调整查询样式顺序 (修改序号可改变顺序)", this);
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		this.node = node;
		this.tree = tree;
		init();
		dialog.setVisible(true);
	}

	private void init() {
		model = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		};
		table.setModel(model);
		table.setRowSelectionAllowed(true);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (0 == e.getColumn() && !sort) {
					int row = e.getFirstRow();
					int column = 0;
					String value = model.getValueAt(row, column).toString();
					sort = true;
					if (CommonUtils.isNumberString(value)) {
						int addRow = Integer.parseInt(value) - 1;
						if (addRow != row) {
							if (addRow >= model.getRowCount()) {
								addRow = model.getRowCount() - 1;
							} else if (addRow < 0) {
								addRow = 0;
							}
							Vector<Vector<?>> data = model.getDataVector();
							Vector<?> obj = data.get(row);
							model.removeRow(row);
							model.insertRow(addRow, obj);
							CompUtils.initSortNum(model, 0);
							table.setRowSelectionInterval(addRow, addRow);
							CompUtils.setTableScrolVisible(table, addRow);
						}
					} else {
						model.setValueAt((row + 1), row, column);
					}
					sort = false;
				}
			}
		});

		CompUtils.setTableWdiths(table, 0.2, null);
		Enumeration<DefaultMutableTreeNode> nodes = node.children();
		while (nodes.hasMoreElements()) {
			DefaultMutableTreeNode node = nodes.nextElement();
			model.addRow(new Object[] { model.getRowCount() + 1, node });
		}
		CompUtils.tableMove(button_2, table, 0, true);
		CompUtils.tableMove(button_3, table, 0, false);
	}

	private void save() {
		StringBuilder param = new StringBuilder();
		for (int i = 0; i < model.getRowCount(); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getValueAt(i, 1);
			XMLDto dto = (XMLDto) node.getUserObject();
			param.append(i).append(":").append(dto.getValue("styleid")).append(";");
		}
		param.deleteCharAt(param.length() - 1);
		try {
			InvokerServiceUtils.sortStyle(param.toString());
			node.removeAllChildren();
			for (int i = 0; i < model.getRowCount(); i++) {
				DefaultMutableTreeNode cNode = (DefaultMutableTreeNode) model.getValueAt(i, 1);
				XMLDto dto = (XMLDto) node.getUserObject();
				dto.setValue("sortnumber", i + "");
				node.add(cNode);
			}
			tree.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}
		dialog.dispose();
	}

}
