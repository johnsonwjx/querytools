package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import youngfriend.beans.PropDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class ProValueEditor extends JPanel implements PropEditor {
	public ProValueEditor() {
		this.setPreferredSize(new Dimension(643, 358));
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), " \u7279\u6B8A\u5904\u7406:YFNOTNULL\u8868\u793A\u4E3A\u975E\u7A7A,YFNULL\u8868\u793A\u4E3A\u7A7A,\u591A\u4E2A\u503C\u7528(\u534A\u89D2)\u5192\u53F7\u5206\u9694,\u4F8B1:2:3",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}

		});

		JButton button_2 = new JButton("\u589E\u52A0");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});
		panel.add(button_2);

		JButton button_3 = new JButton("\u5220\u9664");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delItem();
			}

		});
		panel.add(button_3);

		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(150, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator);
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel.add(button_1);
		init();
	}

	private static final long serialVersionUID = 1L;
	private JTable table;
	private String[] headers = { " Ù–‘", "÷µ" };
	private DefaultTableModel model;
	private DefaultPropEditor defaultpropeditor;

	private void init() {
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model = new DefaultTableModel(headers, 0);
		table.setModel(model);

	}

	private void addItem() {
		CompUtils.stopTabelCellEditor(table);
		int index = table.getSelectedRow();
		if (index < 0) {
			index = model.getRowCount();
		} else {
			index++;
		}
		model.insertRow(index, new String[] { "", "" });
		table.setRowSelectionInterval(index, index);
	}

	private void delItem() {
		CompUtils.stopTabelCellEditor(table);
		int index = table.getSelectedRow();
		if (index < 0) {
			return;
		}
		model.removeRow(index);
		if (model.getRowCount() <= 0) {
			return;
		}
		if (index > 0) {
			index--;
		}
		table.setRowSelectionInterval(index, index);
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				CompUtils.stopTabelCellEditor(table);
				if (model.getRowCount() <= 0) {
					prop.setValue("");
				} else {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < model.getRowCount(); i++) {
						String p = (String) model.getValueAt(i, 0);
						String v = (String) model.getValueAt(i, 1);
						if (CommonUtils.isStrEmpty(p) && CommonUtils.isStrEmpty(v)) {
							continue;
						}
						sb.append(p).append("=").append(v).append(",");
					}
					sb.deleteCharAt(sb.length() - 1);
					prop.setValue(sb.toString());
				}
				return true;
			}

			@Override
			public void initData() {
				String[] arr = prop.getValue().split(",");
				if (arr.length <= 0) {
					return;
				}
				for (String item : arr) {
					String[] temp = item.split("=");
					if (temp.length == 1) {
						model.addRow(new String[] { temp[0], "" });
					} else if (temp.length == 2) {
						model.addRow(temp);
					}

				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
