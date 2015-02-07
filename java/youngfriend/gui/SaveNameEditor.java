package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import youngfriend.beans.PropDto;
import youngfriend.coms.IStyleCom;
import youngfriend.coms.IStylePanel;
import youngfriend.editors.MutiFieldsEditor;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class SaveNameEditor extends JPanel {
	private JDialog dialog;

	public SaveNameEditor(Map<String, List<IStyleCom>> saveComs) {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		JButton button_2 = new JButton("\u7CFB\u7EDF\u5EFA\u8BAE\u540D");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				suggesName();
			}
		});
		panel.add(button_2);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(100, 0));
		panel.add(separator);
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(button_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "\u540D\u79F0\u76F8\u540C\uFF0C\u7236\u4EB2\u4E0D\u540C \u5408\u6CD5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		scrollPane.setViewportView(table);
		init();
		initData(saveComs);
		dialog = GUIUtils.getDialog(MainFrame.getInstance(), "名称批量修改", this);
		dialog.setVisible(true);
	}

	private final String[] title = { "序号", "名称", "父亲控件", "多字段组合条件", "描述" };
	private DefaultTableModel model;
	private final int index_sortnum = 0;
	private final int index_name = 1;
	private final int index_compfiels = 3;
	private final int index_info = 4;

	private void init() {
		this.setPreferredSize(new Dimension(500, 500));
		model = new DefaultTableModel(title, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (index_sortnum == columnIndex) {
					return Integer.class;
				}
				return super.getColumnClass(columnIndex);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == index_name) {
					return true;
				}
				if (column == index_compfiels && !"无".equals(table.getValueAt(row, column))) {
					return true;
				}
				return false;
			}
		};
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(model);
		CompUtils.setTableWdiths(table, 0.1, 0.2, 0.3, 0.3);
		TableColumn c = table.getColumnModel().getColumn(index_compfiels);
		ButtonCellEditor editor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				MutiFieldsEditor editor = new MutiFieldsEditor();
				PropDto temp = new PropDto();
				temp.setValue((String) table.getValueAt(row, column));
				editor.edit(temp, MainFrame.getInstance());
				table.setValueAt(temp.getValue(), row, column);
				CompUtils.stopTabelCellEditor(table);
			}
		}, true);
		c.setCellEditor(editor);
		c.setCellRenderer(editor.getTableCellRenderer());
	}

	private void suggesName() {
		CompUtils.stopTabelCellEditor(table);
		int row = table.getSelectedRow();
		if (row < 0) {
			return;
		}
		String name = CompUtils.getComName();
		table.setValueAt(name, row, index_name);
	}

	private void initData(Map<String, List<IStyleCom>> saveComs) {
		int i = 1;
		for (String key : saveComs.keySet()) {
			List<IStyleCom> coms = saveComs.get(key);
			for (IStyleCom com : coms) {
				IStylePanel parent = com.getParentPnl();
				PropDto temp = com.getProp("FieldsCondi");
				String fieldsCondi = "无";
				if (temp != null) {
					fieldsCondi = temp.getValue();
				}
				if (CommonUtils.isStrEmpty(fieldsCondi)) {
					fieldsCondi = "无";
				}
				model.addRow(new Object[] { i, com.getPropValue("Name"), parent == null ? "" : parent, fieldsCondi, com });
			}
			i++;
		}
	}

	private void save() {
		CompUtils.stopTabelCellEditor(table);
		for (int i = 0; i < model.getRowCount(); i++) {
			IStyleCom com = (IStyleCom) model.getValueAt(i, index_info);
			com.setPropValue("Name", (String) model.getValueAt(i, index_name));
			Object FieldsCondi = model.getValueAt(i, index_compfiels);
			if ("无".equals(FieldsCondi)) {
				continue;
			}
			com.setPropValue("FieldsCondi", (String) FieldsCondi);
		}
		CompUtils.getStyleMain().updateUIComTree();
		dialog.dispose();
	}

	private static final long serialVersionUID = 1L;
	private JTable table;

}
