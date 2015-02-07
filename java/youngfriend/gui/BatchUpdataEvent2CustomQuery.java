package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.Validate;
import youngfriend.beans.XMLDto;
import youngfriend.editors.valueEditors.ToCustomqueryEditor;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.Do4objs;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

import com.google.gson.Gson;

import javax.swing.JRadioButton;

public class BatchUpdataEvent2CustomQuery extends JPanel {
	private JDialog dialog;
	private final Logger logger = LogManager.getLogger(BatchUpdataEvent2CustomQuery.class.getName());
	private JTextArea textArea;
	private JRadioButton radioButton_2;
	private JButton button_4;
	private JButton button_3;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JScrollPane scrollPane;
	private JTextArea textArea1;

	public BatchUpdataEvent2CustomQuery() {
		this.setPreferredSize(new Dimension(626, 303));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}

		});
		panel.add(button);

		JButton button_1 = new JButton("\u5173\u95ED");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);

		JLabel label = new JLabel("\u9009\u62E9\u66FF\u6362\u76EE\u5F55\u6216\u67E5\u8BE2\u7C7B");
		panel_1.add(label);

		textField = new JTextField();
		textField.setEditable(false);
		panel_1.add(textField);
		textField.setColumns(30);

		JButton button_2 = new JButton("...");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCataLogorClass();
			}
		});
		panel_1.add(button_2);
		ButtonGroup bg = new ButtonGroup();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("\u52A8\u6001\u8BF4\u660E", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		autodescinfoTable = new JTable();
		scrollPane.setViewportView(autodescinfoTable);
		panel_2.add(panel_3, BorderLayout.SOUTH);
		button_3 = new JButton("\u589E\u52A0");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) autodescinfoTable.getModel();
				model.addRow(new String[] { "", "", "" });
				autodescinfoTable.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
			}
		});

		radioButton_2 = new JRadioButton("\u666E\u901A\u8BBE\u7F6E");
		radioButton_2.setSelected(true);
		panel_3.add(radioButton_2);
		radioButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (radioButton_2.isSelected()) {
					scrollPane.setViewportView(autodescinfoTable);
					autodescinfoTable.setModel(new DefaultTableModel(new String[] { "字段英文名", "替换英文名", "替换中文名" }, 0));
					button_4.setVisible(true);
					button_3.setVisible(true);
				}

			}
		});
		textArea1 = new JTextArea();
		textArea1.setLineWrap(true);
		radioButton = new JRadioButton("\u5B57\u7B26\u4E32\u66FF\u6362");
		panel_3.add(radioButton);

		panel_3.add(radioButton);
		radioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (radioButton.isSelected()) {
					scrollPane.setViewportView(autodescinfoTable);
					autodescinfoTable.setModel(new DefaultTableModel(new String[] { "需替换字符", "替换后字符" }, 0));
					button_4.setVisible(true);
					button_3.setVisible(true);
				}

			}
		});
		bg.add(radioButton);
		radioButton_1 = new JRadioButton("\u5168\u90E8\u66FF\u6362");
		panel_3.add(radioButton_1);
		radioButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (radioButton_1.isSelected()) {
					textArea1.setText("");
					scrollPane.setViewportView(textArea1);
					button_4.setVisible(false);
					button_3.setVisible(false);
				}

			}
		});
		panel_3.add(button_3);

		bg.add(radioButton_2);
		bg.add(radioButton_1);
		button_4 = new JButton("\u5220\u9664");
		panel_3.add(button_4);
		CompUtils.tableDelRow(button_4, autodescinfoTable, -1);
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("\u6761\u4EF6\u76F8\u5173\u8BBE\u7F6E", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.SOUTH);

		JButton button_5 = new JButton("\u589E\u52A0");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) condiTable.getModel();
				model.addRow(new Object[] { "", null });
				condiTable.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
			}
		});
		panel_5.add(button_5);

		JButton button_6 = new JButton("\u5220\u9664");
		panel_5.add(button_6);

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, BorderLayout.CENTER);

		condiTable = new JTable();
		scrollPane_1.setViewportView(condiTable);
		condiTable.setModel(new DefaultTableModel(new String[] { "目标字段", "操作符替换为" }, 0));
		TableColumnModel cm = condiTable.getColumnModel();
		JComboBox box = new JComboBox(ToCustomqueryEditor.opers.toArray());
		box.insertItemAt(null, 0);
		cm.getColumn(1).setCellEditor(new DefaultCellEditor(box));
		CompUtils.tableDelRow(button_6, condiTable, -1);

		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("\u52A8\u6001\u6807\u9898", null, panel_6, null);
		panel_6.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportBorder(new TitledBorder(null, "\u8BBE\u7F6E\u4E3A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.add(scrollPane_2, BorderLayout.CENTER);
		scrollPane.setViewportView(autodescinfoTable);
		autodescinfoTable.setModel(new DefaultTableModel(new String[] { "字段英文名", "替换英文名", "替换中文名" }, 0));
		button_4.setVisible(true);
		button_3.setVisible(true);
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane_2.setViewportView(textArea);
		dialog = GUIUtils.getDialog(MainFrame.getInstance(), "批量修改穿透到自定义查询设置", this);
		dialog.setVisible(true);
	}

	private XMLDto dto = null;
	private TreeSelectPnl<XMLDto> pnl;

	private void selectCataLogorClass() {

		if (pnl == null) {
			JTree tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {
				@Override
				public String validate(XMLDto obj) {
					String dataType = obj.getValue("dataType");
					if ("class".equals(dataType)) {
						return "ingoneChildren";
					}
					return null;
				}
			}, "选择", true);
			pnl = new TreeSelectPnl<XMLDto>(tree, null);
		}

		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		dto = pnl.getSelect();
		if (dto == null) {
			textField.setText("");
		} else {
			textField.setText(dto.toString());
		}
	}

	private void save() {
		CompUtils.stopTabelCellEditor(autodescinfoTable);
		CompUtils.stopTabelCellEditor(condiTable);
		if (dto == null || !(dto instanceof XMLDto)) {
			GUIUtils.showMsg(dialog, "请选择更新目录或查询类");
			return;
		}
		if (!GUIUtils.showConfirm(dialog, "确定修改吗？样式设置将修改")) {
			return;
		}
		if (!GUIUtils.showConfirm(dialog, "确定修改吗？样式设置将修改")) {
			return;
		}
		result = "";
		MainFrame.getInstance().busyDoing(new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				try {
					String classid = "", catalogid = "", autodescinfo = "", autotitle = textArea.getText().trim(), condi = "";
					if ("class".equals(dto.getValue("dataType"))) {
						classid = dto.getValue("id");
					} else if ("catalog".equals(dto.getValue("dataType"))) {
						catalogid = dto.getValue("id");
					}
					Gson gson = new Gson();
					if (radioButton_1.isSelected()) {
						autodescinfo = textArea1.getText().trim();
					} else if (radioButton.isSelected()) {
						if (autodescinfoTable.getRowCount() > 0) {
							Map<String, String> autodescinfoMap = new HashMap<String, String>();
							for (int i = 0; i < autodescinfoTable.getRowCount(); i++) {
								String fieldName = (String) autodescinfoTable.getValueAt(i, 0);
								if (CommonUtils.isStrEmpty(fieldName, true)) {
									GUIUtils.showMsg(dialog, "替换前字符为空");
									autodescinfoTable.setRowSelectionInterval(i, i);
									return;
								}
								autodescinfoMap.put(fieldName, CommonUtils.coverNull((String) autodescinfoTable.getValueAt(i, 1)));
							}
							if (!autodescinfoMap.isEmpty()) {
								autodescinfo = gson.toJson(autodescinfoMap);
							}
						}
					} else if (radioButton_2.isSelected()) {
						if (autodescinfoTable.getRowCount() > 0) {
							Map<String, String> autodescinfoMap = new HashMap<String, String>();
							for (int i = 0; i < autodescinfoTable.getRowCount(); i++) {
								String fieldName = (String) autodescinfoTable.getValueAt(i, 0);
								if (CommonUtils.isStrEmpty(fieldName, true)) {
									GUIUtils.showMsg(dialog, "动态说明英文字段名有空");
									autodescinfoTable.setRowSelectionInterval(i, i);
									return;
								}
								String engRp = CommonUtils.coverNull((String) autodescinfoTable.getValueAt(i, 1));
								String cnRp = CommonUtils.coverNull((String) autodescinfoTable.getValueAt(i, 2));
								autodescinfoMap.put(fieldName, engRp + "," + cnRp);
							}
							if (!autodescinfoMap.isEmpty()) {
								autodescinfo = gson.toJson(autodescinfoMap);
							}
						}
					}

					if (condiTable.getRowCount() > 0) {
						Map<String, Map<String, String>> condiMap = new HashMap<String, Map<String, String>>();
						for (int i = 0; i < condiTable.getRowCount(); i++) {
							String fieldName = (String) condiTable.getValueAt(i, 0);
							if (CommonUtils.isStrEmpty(fieldName, true)) {
								GUIUtils.showMsg(dialog, "条件相关设置字段名有空");
								condiTable.setRowSelectionInterval(i, i);
								return;
							}
							Map<String, String> itemMap = new HashMap<String, String>();
							XMLDto oper = (XMLDto) condiTable.getValueAt(i, 1);
							if (oper != null) {
								itemMap.put("oper", oper.getValue("value"));
							}
							if (!itemMap.isEmpty()) {
								condiMap.put(fieldName, itemMap);
							}
						}
						if (!condiMap.isEmpty()) {
							condi = gson.toJson(condiMap);
						}
					}
					result = InvokerServiceUtils.updateEvent4CustomQuery(catalogid, classid, autodescinfo, autotitle, condi);
				} catch (Exception e) {
					GUIUtils.showMsg(dialog, e.getMessage());
					logger.error(e.getMessage(), e);
				}

			}
		}, new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				if (!CommonUtils.isStrEmpty(result)) {
					GUIUtils.showMsg(dialog, result);
				}

			}
		}, true);

	}

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable autodescinfoTable;
	private JTable condiTable;
	private String result;
}
