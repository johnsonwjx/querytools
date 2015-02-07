package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class BtnListSearchPnl<T> extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	private DefaultListModel listModel;
	private DefaultTableModel tableModel;
	private String oldMatchTxt;
	private JList jList;
	private JTable table;
	private boolean matchFlag = false;
	private Window owner;
	private int dataSize = 0;
	private Integer valueColumn = null;

	public BtnListSearchPnl(Window owner, JComponent com, int height, Integer valueColumn) {
		this.owner = owner;
		this.setPreferredSize(new Dimension(0, height));
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		initComponents();
		btnNewButton = new JButton("\u641C\u7D22");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		add(btnNewButton, BorderLayout.EAST);
		this.valueColumn = valueColumn;
		if (valueColumn == null) {
			this.jList = (JList) com;
			listModel = (DefaultListModel) jList.getModel();
		} else {
			this.table = (JTable) com;
			tableModel = (DefaultTableModel) table.getModel();
			if (valueColumn < 0 || valueColumn > tableModel.getColumnCount() - 1) {
				return;
			}

		}
	}

	private void initComponents() {
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setText("  ");
		searchTxt = new javax.swing.JTextField();
		searchTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				}
			}
		});
		setLayout(new BorderLayout(0, 0));
		add(jLabel1, BorderLayout.WEST);
		add(searchTxt);
	}

	private void search() {
		if (valueColumn != null) {
			dataSize = tableModel.getRowCount();
		} else {
			dataSize = listModel.getSize();
		}

		String match = searchTxt.getText();
		if (CommonUtils.isStrEmpty(match) || dataSize <= 0) {
			return;
		}
		int startIndex = 0;
		if (match.equalsIgnoreCase(oldMatchTxt)) {
			if (!matchFlag) {
				GUIUtils.showMsg(owner, "此搜索没记录");
				return;
			}
			int selectIndex = -1;
			if (valueColumn != null) {
				selectIndex = table.getSelectedRow();
			} else {
				selectIndex = jList.getSelectedIndex();
			}
			if (selectIndex < dataSize - 1) {
				startIndex = selectIndex + 1;
			}
			matchFlag = true;
		} else {
			matchFlag = true;
			oldMatchTxt = match;
		}

		int seachCount = 0;

		for (; startIndex < dataSize; startIndex++) {
			T temp = null;
			if (valueColumn == null) {
				temp = (T) listModel.get(startIndex);
			} else {
				temp = (T) tableModel.getValueAt(startIndex, valueColumn);
			}
			if (temp != null) {
				if (temp.toString().toLowerCase().indexOf(match.toLowerCase()) >= 0) {
					if (valueColumn == null) {
						jList.setSelectedValue(temp, true);
					} else {
						table.setRowSelectionInterval(startIndex, startIndex);
						CompUtils.setTableScrolVisible(table, startIndex);
					}
					break;
				}
				if (startIndex >= dataSize - 1) {
					if (seachCount >= dataSize - 1) {
						GUIUtils.showMsg(owner, "没有匹配记录");
						matchFlag = false;
						return;
					} else {
						startIndex = -1;
					}
				}
			}

			seachCount++;
		}
	}

	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField searchTxt;
	private JButton btnNewButton;
}
