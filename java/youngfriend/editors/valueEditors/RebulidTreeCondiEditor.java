/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RebulidTreePnl.java
 *
 * Created on 2011-12-28, 11:15:09
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class RebulidTreeCondiEditor extends JPanel implements ValueEditor {

	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private Map<String, String> fieldstype = new LinkedHashMap<String, String>();
	private Map<String, String> symbol = new LinkedHashMap<String, String>();
	private Map<String, String> datasource = new LinkedHashMap<String, String>();
	private boolean submit = false;
	private Map<String, String> props;
	private String[] header = { "条件值字段", "字段类型", "树字段", "操作符", "数据来源" };
	private DefaultTableModel dataModel;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	public RebulidTreeCondiEditor() {
		initComponents();
		init();
	}

	private void init() {
		fieldstype.put("C", "字符型");
		fieldstype.put("N", "数字型");
		symbol.put("llike", "左匹配");
		symbol.put("=", "等于");
		symbol.put(">", "大于");
		symbol.put(">=", "大于等于");
		symbol.put("<", "小于");
		symbol.put("<=", "小于等于");
		datasource.put("0", "条件界面");
		datasource.put("1", "表格");
		datasource.put("2", "条件面板(结果界面)");
		jTable1 = new javax.swing.JTable();
		jScrollPane1.setViewportView(jTable1);
		dataModel = new DefaultTableModel(header, 0);
		jTable1.setModel(dataModel);
		jTable1.setRowSelectionAllowed(true);
		jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		CompUtils.setTableWdiths(jTable1, null, 0.2, null, 0.2, 0.3);

		TableColumnModel cm = jTable1.getColumnModel();

		TableColumn c0 = cm.getColumn(0);
		ButtonCellEditor cellEditor0 = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(jTable1);
				int row = jTable1.getSelectedRow();
				int column = jTable1.getSelectedColumn();
				String value = CompUtils.getCellValue(String.class, jTable1, row, column);
				ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
				XMLDto field = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", value);
				pnl.setValue(field);
				pnl.edit(dialog, null);
				if (pnl.isSubmit()) {
					field = pnl.getSelect();
					if (pnl.isNull()) {
						jTable1.setValueAt("", row, column);
					} else {
						jTable1.setValueAt(field.getValue("itemname"), row, column);
					}
				}
			}
		}, true);
		c0.setCellEditor(cellEditor0);
		c0.setCellRenderer(cellEditor0.getTableCellRenderer());

		TableColumn c1 = cm.getColumn(1);
		JComboBox fieldstypeCombo = new JComboBox(fieldstype.values().toArray());
		c1.setCellEditor(new DefaultCellEditor(fieldstypeCombo));

		TableColumn c3 = cm.getColumn(3);
		JComboBox symbolCombo = new JComboBox(symbol.values().toArray());
		c3.setCellEditor(new DefaultCellEditor(symbolCombo));

		TableColumn c4 = cm.getColumn(4);
		JComboBox datasourceCombo = new JComboBox(datasource.values().toArray());
		c4.setCellEditor(new DefaultCellEditor(datasourceCombo));
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(568, 346));
		setLayout(new BorderLayout(0, 0));
		jScrollPane1 = new javax.swing.JScrollPane();
		add(jScrollPane1, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("\u589E\u52A0");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataModel.addRow(new Object[] { null, fieldstype.values().iterator().next(), "", symbol.values().iterator().next(), datasource.values().iterator().next() });
			}
		});
		panel.add(btnNewButton);

		JButton button = new JButton("\u5220\u9664");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = jTable1.getSelectedRow();
				if (index < 0) {
					return;
				}
				CompUtils.stopTabelCellEditor(jTable1);
				dataModel.removeRow(index);
				if (dataModel.getRowCount() > 0) {
					if (index > 0) {
						index--;
					}
					jTable1.setRowSelectionInterval(index, index);
				}

			}
		});
		panel.add(button);

		JButton button_3 = new JButton("清空");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!GUIUtils.showConfirm(dialog, "确认清空吗")) {
					return;
				}
				CompUtils.stopTabelCellEditor(jTable1);
				dataModel.setRowCount(0);
			}
		});
		panel.add(button_3);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(100, 0));
		panel.add(separator);

		JButton button_1 = new JButton("\u786E\u5B9A");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel.add(button_1);

		JButton button_2 = new JButton("\u53D6\u6D88");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(button_2);
	}

	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTable1;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		this.props = props;
		submit = false;
		initData();
		dialog = GUIUtils.getDialog(owner, "根据条件动态建树", this);
		dialog.setVisible(true);
	}

	private void save() {
		CompUtils.stopTabelCellEditor(jTable1);
		StringBuilder sb = new StringBuilder();
		if (dataModel.getRowCount() > 0) {
			for (int i = 0; i < dataModel.getRowCount(); i++) {
				String field = (String) dataModel.getValueAt(i, 0);
				String type = (String) dataModel.getValueAt(i, 1);
				String treeField = (String) dataModel.getValueAt(i, 2);
				String oper = (String) dataModel.getValueAt(i, 3);
				String source = (String) dataModel.getValueAt(i, 4);
				if (CommonUtils.isStrEmpty(field) || CommonUtils.isStrEmpty(type) || CommonUtils.isStrEmpty(treeField) || CommonUtils.isStrEmpty(oper) || CommonUtils.isStrEmpty(source)) {
					GUIUtils.showMsg(dialog, "存在空设置");
					jTable1.setRowSelectionInterval(i, i);
					return;
				}
				sb.append(field).append(",")//
						.append(CommonUtils.coverNull(CommonUtils.getMapKey(fieldstype, type))).append(",")//
						.append(treeField).append(",")//
						.append(CommonUtils.coverNull(CommonUtils.getMapKey(symbol, oper))).append(",")//
						.append(CommonUtils.coverNull(CommonUtils.getMapKey(datasource, source))).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		props.put("value", sb.toString());
		submit = true;
		dialog.dispose();

	}

	private void initData() {
		try {
			String value = props.get("value");
			if (CommonUtils.isStrEmpty(value)) {
				return;
			}
			String[] items = value.split(";");
			if (items.length <= 0) {
				return;
			}
			for (String item : items) {
				String[] sets = item.split(",");
				logger.debug(sets);
				if (sets.length < 4) {
					continue;
				}
				String ds = "条件界面";
				if (sets.length > 4) {
					ds = datasource.get(sets[4]);
				}
				dataModel.addRow(new Object[] { sets[0], fieldstype.get(sets[1]), sets[2], symbol.get(sets[3]), ds });
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.INIT_ERROR);
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
