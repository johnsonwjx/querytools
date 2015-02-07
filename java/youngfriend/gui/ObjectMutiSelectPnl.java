package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class ObjectMutiSelectPnl<T> extends JPanel implements ValueEditor {
	private JDialog dialog;
	private String[] columnTitle;
	private JButton button_2;
	private List<T> selects = new ArrayList<T>();
	private DefaultTableModel model;
	private List<T> all;

	public List<T> getSelects() {
		if (submit) {
			selects = getTableSelects();

		}
		return selects;
	}

	public void setSelects(List<T> selects) {
		this.selects = selects;
		for (int i = 0; i < model.getRowCount(); i++) {
			Object obj = model.getValueAt(i, 1);
			if (selects.contains(obj)) {
				model.setValueAt(true, i, 0);
			} else {
				model.setValueAt(false, i, 0);
			}
		}
	}

	public ObjectMutiSelectPnl(List<T> all, String columnTitle, LinkedHashMap<String, String> showmap) {
		this.setPreferredSize(new Dimension(461, 440));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		button_2 = new JButton("\u5168\u9009");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}
		});
		panel.add(button_2);

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
				submit = false;
				dialog.setVisible(false);
			}
		});
		panel.add(button_1);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		if (showmap == null || showmap.isEmpty()) {
			this.columnTitle = new String[] { "选择", columnTitle };
		} else {
			this.columnTitle = new String[showmap.size() + 2];
			this.columnTitle[0] = "选择";
			this.columnTitle[1] = columnTitle;
			int index = 2;
			for (String s : showmap.values()) {
				this.columnTitle[index] = s;
				index++;
			}
		}

		this.all = all;
		init(showmap.keySet());
		this.add(new BtnListSearchPnl<XMLDto>(dialog, table, 30, 1), BorderLayout.NORTH);
	}

	private void selectAll() {
		if ("全选".equals(button_2.getText())) {
			setAllSelect(true);
		} else {
			setAllSelect(false);
		}

	}

	private void setAllSelect(boolean flag) {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.setValueAt(flag, i, 0);
		}
	}

	private List<T> getTableSelects() {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
				list.add((T) model.getValueAt(i, 1));
			}
		}
		return list;
	}

	private void init(Set<String> keys) {
		model = new DefaultTableModel(columnTitle, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) {
					return Boolean.class;
				}
				return super.getColumnClass(columnIndex);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1) {
					return false;
				}
				return super.isCellEditable(row, column);
			}
		};
		table.setModel(model);
		CompUtils.setFlxColumnWidth2Table(table, 0, 40);
		for (int i = 0; i < all.size(); i++) {
			T obj = all.get(i);
			if (keys == null || keys.isEmpty()) {
				model.addRow(new Object[] { false, obj });
			} else {
				List<Object> temp = new ArrayList<Object>();
				temp.add(Boolean.FALSE);
				temp.add(obj);
				if (obj instanceof XMLDto) {
					XMLDto dto = (XMLDto) obj;
					for (String key : keys) {
						temp.add(dto.getValue(key));
					}
				}

				model.addRow(temp.toArray());
			}

		}
		model.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 0) {
					if (getTableSelects().size() == all.size()) {
						button_2.setText("反选");
					} else {
						button_2.setText("全选");
					}
				}
			}
		});
	}

	private static final long serialVersionUID = 1L;
	private JTable table;
	private boolean submit = false;

	private void save() {
		submit = true;
		dialog.setVisible(false);
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		String title = "";
		if (props != null) {
			title = props.get("title");
			String width = props.get("wdith");
			if (!CommonUtils.isStrEmpty(width) && CommonUtils.isNumberString(width)) {
				this.setPreferredSize(new Dimension(this.getPreferredSize().height, Integer.parseInt(width)));
			}
		}
		if (CommonUtils.isStrEmpty(title)) {
			title = "选择";
		}
		dialog = GUIUtils.getDialog(owner, title, this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

}
