package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class BackgroupEditor extends JPanel implements ValueEditor {
	public BackgroupEditor() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);

		JButton button_2 = new JButton("\u589E\u52A0");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}

		});
		panel_1.add(button_2);

		JButton button_3 = new JButton("\u5220\u9664");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delItem();
			}

		});
		panel_1.add(button_3);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(180, 0));
		panel_1.add(separator);

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

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.NORTH);

		JLabel label = new JLabel("\u5217\u80CC\u666F\u989C\u8272");
		panel_2.add(label);

		textField = new JTextField();
		textField.setEditable(false);
		panel_2.add(textField);
		textField.setColumns(20);

		JButton button_4 = new JButton("\u9009\u62E9");
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectRowColor();
			}
		});
		panel_2.add(button_4);

		JButton button_5 = new JButton("\u6E05\u7A7A\u5217\u989C\u8272");
		button_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearRowColor();
			}

		});
		panel_2.add(button_5);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u884C\u80CC\u666F\u989C\u8272\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		table = new JTable();
		scrollPane.setViewportView(table);
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.EAST);

		final JList list = new JList();
		scrollPane_1.setViewportView(list);
		DefaultListModel listModel = new DefaultListModel();
		for (XMLDto dto : CompUtils.getFields()) {
			listModel.addElement(dto);
		}
		list.setModel(listModel);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2 && table.isEditing()) {
					XMLDto dto = (XMLDto) list.getSelectedValue();
					JTextField text = (JTextField) table.getEditorComponent();
					if (dto != null && text != null) {
						text.replaceSelection(" {" + dto.getValue("itemname").toLowerCase() + "} ");
						text.requestFocus();
					}
				}
			}
		});
		JTextPane txtpnnn = new JTextPane();
		txtpnnn.setPreferredSize(new Dimension(0, 65));
		txtpnnn.setText("\u6761\u4EF6\u683C\u5F0F\u4F8B\u5B50\uFF1A\n    \u5176\u4E2D@\u5F53\u524D\u503C, {\u5B57\u6BB5\u540D}\u4E3A\u5176\u5B83\u503C {start_year}>2014 and {start_month} !=07\n    \u5B57\u7B26\u578B\uFF1A@ == '\u542F\u52A8'      @  != '\u542F\u52A8'    \u6570\u5B57\u578B\uFF1A@<2000 or @>3000\n    \u65E5\u671F\u6BD4\u8F83: {start_data:dateformat:Y-m-d}  \u5927\u5199Y\u4EE3\u8868\u5E74,m\u4EE3\u8868\u6708,d\u4EE3\u8868\u65E5\n");
		panel.add(txtpnnn, BorderLayout.SOUTH);

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_3, BorderLayout.NORTH);

		JButton button_6 = new JButton("\u63D2\u5165\u65E5\u671F\u6807\u5FD7");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initDataType();
			}
		});
		panel_3.add(button_6);
		init();
	}

	private void initDataType() {
		if (table.isEditing()) {
			JTextField text = (JTextField) table.getEditorComponent();
			if (text != null) {
				text.replaceSelection(":dateformat:");
				text.requestFocus();
			}
		}
	}

	private static final long serialVersionUID = -4586955508068431414L;
	private boolean submit;
	private JDialog dialog;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private Map<String, String> props;

	private void init() {
		model = new DefaultTableModel(new String[] { "条件", "颜色" }, 0);
		table.setModel(model);
		TableColumnModel cm = table.getColumnModel();
		TableColumn c1 = cm.getColumn(1);
		ButtonCellEditor cellEditor = new ButtonCellEditor(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.stopTabelCellEditor(table);
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				String colorTxt = (String) model.getValueAt(row, column);
				Color color = getColor(colorTxt);
				color = JColorChooser.showDialog(dialog, "选择颜色", color);
				if (color != null) {
					model.setValueAt(getColorRgb(color), row, column);
				}
				CompUtils.stopTabelCellEditor(table);
			}
		}, false);
		c1.setCellEditor(cellEditor);
		c1.setCellRenderer(cellEditor.getTableCellRenderer());
	}

	private void addItem() {
		CompUtils.stopTabelCellEditor(table);
		model.addRow(new String[] { "", "" });
		CompUtils.stopTabelCellEditor(table);
	}

	private void delItem() {
		CompUtils.stopTabelCellEditor(table);
		int[] rows = table.getSelectedRows();
		if (rows.length > 0) {
			int removeCount = 0;
			for (int i : rows) {
				model.removeRow(i - removeCount);
				removeCount++;
			}
		}
		CompUtils.stopTabelCellEditor(table);
	}

	private void clearRowColor() {
		textField.setForeground(Color.BLACK);
		textField.setText("");
	}

	private void selectRowColor() {
		Color color = textField.getForeground();
		color = JColorChooser.showDialog(dialog, "选择颜色", textField.getForeground());
		textField.setForeground(color);
		textField.setText(getColorRgb(color));
	}

	private String getColorRgb(Color color) {
		if (color == null) {
			return "";
		}
		return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
	}

	private Color getColor(String colorTxt) {
		try {
			if (CommonUtils.isStrEmpty(colorTxt)) {
				return null;
			}
			String[] rgbTxt = colorTxt.split(",");
			if (rgbTxt.length == 3) {
				return new Color(Integer.parseInt(rgbTxt[0]), Integer.parseInt(rgbTxt[1]), Integer.parseInt(rgbTxt[2]));
			} else {
				return Color.decode("#" + colorTxt.trim().toUpperCase());
			}
		} catch (Exception e) {
			logger.debug(colorTxt);
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private void save() {
		try {
			CompUtils.stopTabelCellEditor(table);
			if (model.getRowCount() <= 0 && CommonUtils.isStrEmpty(textField.getText().trim())) {
				props.put("value", "");
			} else {
				Element root = DocumentHelper.createElement("root");
				root.addElement("columns").setText(textField.getText().trim());
				Element rows = root.addElement("rows");
				if (model.getRowCount() > 0) {
					for (int i = 0; i < model.getRowCount(); i++) {
						Element item = rows.addElement("item");
						String condi = (String) model.getValueAt(i, 0);
						String color = (String) model.getValueAt(i, 1);
						if (CommonUtils.isStrEmpty(condi) || CommonUtils.isStrEmpty(color)) {
							GUIUtils.showMsg(dialog, "必须同时设置颜色和条件");
							table.setRowSelectionInterval(i, i);
							return;
						}
						condi = condi.trim();
						if (!condi.startsWith("@")) {
							GUIUtils.showMsg(dialog, "条件必须以@开头");
							table.setRowSelectionInterval(i, i);
							return;
						}
						item.addElement("condi").setText(CommonUtils.base64Encode(condi.getBytes()));
						item.addElement("color").setText(color);
					}
				}
				props.put("value", root.asXML());
			}

			submit = true;
			dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}

	}

	private void initData(String value) {
		if (CommonUtils.isStrEmpty(value)) {
			return;
		}
		try {
			Document doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			String columns = root.elementText("columns");
			if (!CommonUtils.isStrEmpty(columns)) {
				Color color = getColor(columns);
				if (color != null) {
					textField.setForeground(color);
					textField.setText(getColorRgb(color));
				}
			}
			Element rows = root.element("rows");
			List<Element> items = rows.elements("item");
			if (items.isEmpty()) {
				return;
			}
			for (Element item : items) {
				String[] rowData = { "", "" };
				String condi = item.elementText("condi");
				if (!CommonUtils.isStrEmpty(condi)) {
					rowData[0] = new String(CommonUtils.base64Dcode(condi));
				}
				String colorStr = item.elementText("color");
				if (!CommonUtils.isStrEmpty(colorStr)) {
					Color color = getColor(colorStr);
					if (color != null) {
						rowData[1] = getColorRgb(color);
					}
				}
				model.addRow(rowData);
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.INIT_ERROR);
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		String value = props.get("value");
		initData(value);
		dialog = GUIUtils.getDialog(owner, "表格背景颜色", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

}
