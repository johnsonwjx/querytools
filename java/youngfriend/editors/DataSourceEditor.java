package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;

import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class DataSourceEditor extends JPanel implements PropEditor {
	private JTextArea textArea;
	private JList list;
	private JList list_1;
	private JList list_2;
	private JList list_3;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private ButtonGroup bg;
	private JButton button_2;

	public DataSourceEditor() {
		this.setPreferredSize(new Dimension(778, 500));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 120));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(6, 17, 91, 68);
		panel.add(panel_4);

		radioButton = new JRadioButton("\u4EE3\u7801\u4E2D\u5FC3");
		radioButton.setSelected(true);
		panel_4.add(radioButton);

		radioButton_1 = new JRadioButton("\u901A\u8FC7\u670D\u52A1");
		panel_4.add(radioButton_1);
		bg = new ButtonGroup();
		bg.add(radioButton);
		bg.add(radioButton_1);
		JLabel label = new JLabel("\u670D\u52A1\u540D");
		label.setBounds(109, 17, 61, 16);
		panel.add(label);

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(167, 11, 158, 28);
		panel.add(textField);
		textField.setColumns(10);

		button_2 = new JButton("\u83B7\u53D6");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String service = textField.getText().trim();
				if (CommonUtils.isStrEmpty(service)) {
					GUIUtils.showMsg(defaultpropeditor.getDialog(), "请输入服务名(像 store2)");
					return;
				}
				if (service.equals(oldServiceName)) {
					return;
				}
				tables = CommonUtils.getTableByService(service);
				oldServiceName = service;
			}

		});
		button_2.setEnabled(false);
		button_2.setBounds(333, 12, 83, 29);
		panel.add(button_2);

		textField_1 = new JTextField();
		textField_1.setBounds(522, 11, 182, 28);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel label_1 = new JLabel("\u6570\u636E\u8868\u540D");
		label_1.setBounds(461, 17, 61, 16);
		panel.add(label_1);

		JLabel label_2 = new JLabel("\u4EE3\u7801\u5B57\u6BB5");
		label_2.setBounds(109, 69, 61, 16);
		panel.add(label_2);

		textField_2 = new JTextField();
		textField_2.setBounds(167, 63, 198, 28);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JButton button_3 = new JButton("...");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				codeFieldSelect();
			}

		});
		button_3.setBounds(377, 63, 39, 29);
		panel.add(button_3);

		JLabel label_3 = new JLabel("\u663E\u793A\u5B57\u6BB5");
		label_3.setBounds(461, 69, 61, 16);
		panel.add(label_3);

		JButton button_4 = new JButton("...");
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayFieldSelect();
			}

		});
		button_4.setBounds(706, 63, 39, 29);
		panel.add(button_4);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(522, 63, 182, 28);
		panel.add(textField_3);

		JButton button_5 = new JButton("...");
		button_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableSelect();
			}

		});
		button_5.setBounds(706, 11, 39, 29);
		panel.add(button_5);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new TitledBorder(null, "\u6E90\u8868\u5B57\u6BB5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(scrollPane_1);

		list = new JList();
		scrollPane_1.setViewportView(list);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportBorder(new TitledBorder(null, "\u6761\u4EF6\u503C\u8868\u5B57\u6BB5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(scrollPane_2);

		list_1 = new JList();
		scrollPane_2.setViewportView(list_1);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setPreferredSize(new Dimension(80, 0));
		panel_3.add(scrollPane_3);

		list_2 = new JList();
		scrollPane_3.setViewportView(list_2);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setPreferredSize(new Dimension(80, 0));
		list_3 = new JList();
		scrollPane_4.setViewportView(list_3);
		panel_3.add(scrollPane_4);

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "\u8FC7\u6EE4\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setPreferredSize(new Dimension(0, 100));
		scrollPane.setViewportView(textArea);
		panel_1.add(scrollPane, BorderLayout.NORTH);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_2, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}

		});
		panel_2.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel_2.add(button_1);
		init();
	}

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private List<XMLDto> tables = new ArrayList<XMLDto>();
	private List<XMLDto> fields = new ArrayList<XMLDto>();
	private XMLDto table;
	private XMLDto codefield;
	private XMLDto displayfield;
	private String oldServiceName;
	private DefaultListModel model_src;
	private final String[] opers = { "=", ">", ">=", "<", "<=", "!=", "like" };
	private final String[] link = { "and", "or" };
	private DefaultPropEditor defaultpropeditor;
	private JDialog dialog;

	private void init() {
		radioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textArea.setText("");
				model_src.clear();
				fields = null;
				codefield = null;
				displayfield = null;
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textField.setText("");
					textField.setEnabled(false);
					button_2.setEnabled(false);
					tables = CompUtils.getCodeTables();
				} else {
					textField.setEnabled(true);
					button_2.setEnabled(true);
					table = null;

				}

			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		model_src = new DefaultListModel();
		list.setModel(model_src);
		list_1.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public int getSize() {
				return CompUtils.getFields().size();
			}

			@Override
			public Object getElementAt(int index) {
				return CompUtils.getFields().get(index);
			}
		});

		list_2.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public int getSize() {
				return opers.length;
			}

			@Override
			public Object getElementAt(int index) {
				return opers[index];
			}
		});
		list_3.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public int getSize() {
				return link.length;
			}

			@Override
			public Object getElementAt(int index) {
				return link[index];
			}
		});
		list_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					CompUtils.textareaInsertText(textArea, " " + list_2.getSelectedValue());
				}
			}
		});
		list_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					CompUtils.textareaInsertText(textArea, " " + list_3.getSelectedValue());
				}
			}
		});
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					XMLDto dto = (XMLDto) list_1.getSelectedValue();
					if (dto == null) {
						return;
					}
					CompUtils.textareaInsertText(textArea, " " + dto.getValue("itemname"));
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					XMLDto dto = (XMLDto) list.getSelectedValue();
					if (dto == null) {
						return;
					}
					String temp = "";
					if (radioButton.isSelected()) {
						temp = dto.getValue("fieldname");
					} else {
						temp = dto.getValue("name");
					}
					if (CommonUtils.isStrEmpty(temp)) {
						return;
					}
					CompUtils.textareaInsertText(textArea, " " + temp);
				}
			}
		});
	}

	private void tableSelect() {
		if (tables == null || tables.isEmpty()) {
			GUIUtils.showMsg(dialog, "表格为空");
			return;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(tables);
		pnl.setValue(table);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		table = pnl.getSelect();
		updateByTable();
	}

	private void updateByTable() {
		fields = null;
		codefield = null;
		displayfield = null;
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textArea.setText("");
		model_src.clear();
		if (table != null) {
			if (radioButton.isSelected()) {
				try {
					fields = InvokerServiceUtils.getCodeFields(table.getValue("id"));
				} catch (Exception e) {
					defaultpropeditor.getLogger().debug(table.getValue("id"));
					GUIUtils.showMsg(dialog, "获取代码中心字段失败");
					defaultpropeditor.getLogger().error(e.getMessage(), e);
				}
			} else {
				fields = CommonUtils.getFieldsByServiceTable(table);
			}
			textField_1.setText(table.toString());
			if (fields == null || fields.isEmpty()) {
				return;
			}
			for (XMLDto f : fields) {
				model_src.addElement(f);
			}
		}
	}

	private void codeFieldSelect() {
		if (fields == null || fields.isEmpty()) {
			GUIUtils.showMsg(dialog, "字段集为空");
			return;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(fields);
		pnl.setValue(codefield);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		codefield = pnl.getSelect();
		textField_2.setText("");
		if (codefield != null) {
			textField_2.setText(codefield.toString());
		}
	}

	private void displayFieldSelect() {
		if (fields == null || fields.isEmpty()) {
			GUIUtils.showMsg(dialog, "字段集为空");
			return;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(fields);
		pnl.setValue(displayfield);
		pnl.edit(dialog, null);
		if (!pnl.isChange()) {
			return;
		}
		displayfield = pnl.getSelect();
		textField_3.setText("");
		if (displayfield != null) {
			textField_3.setText(displayfield.toString());
		}
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		tables = CompUtils.getCodeTables();
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				String ServiceName = textField.getText();
				if (table == null || codefield == null || displayfield == null) {
					GUIUtils.showMsg(defaultpropeditor.getDialog(), "存在空设置");
					return false;
				}
				String tableName = "", CodeField = "", DisplayField = "", Condi = "";
				if (radioButton.isSelected()) {
					tableName = table.getValue("alias");
					CodeField = codefield.getValue("fieldname");
					DisplayField = displayfield.getValue("fieldname");
				} else {
					tableName = table.getValue("name");
					CodeField = codefield.getValue("name");
					DisplayField = displayfield.getValue("name");
				}
				if (!CommonUtils.isStrEmpty(textArea.getText().trim())) {
					Condi = CommonUtils.base64Encode(textArea.getText().trim().getBytes());
				}
				Document doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("root");
				doc.setRootElement(root);
				root.addElement("ServiceName").setText(ServiceName);
				root.addElement("TableName").setText(tableName);
				root.addElement("CodeField").setText(CodeField);
				root.addElement("DisplayField").setText(DisplayField);
				root.addElement("Condi").setText(Condi);
				prop.setValue(doc.asXML());
				return true;
			}

			@Override
			public void initData() {
				Document doc = null;
				try {
					doc = DocumentHelper.parseText(prop.getValue());
				} catch (DocumentException e) {
					new RuntimeException(e);
				}
				Element root = doc.getRootElement();
				String ServiceName = root.elementText("ServiceName");
				boolean codeCendder = true;
				if (!CommonUtils.isStrEmpty(ServiceName)) {
					// 服务取
					codeCendder = false;
					radioButton_1.setSelected(true);
					textField.setText(ServiceName);
					tables = CommonUtils.getTableByService(ServiceName);
					oldServiceName = ServiceName;
				}
				String TableName = root.elementText("TableName");
				if (CommonUtils.isStrEmpty(TableName)) {
					return;
				}
				if (codeCendder) {
					table = CommonUtils.getXmlDto(tables, "alias", TableName);
				} else {
					table = CommonUtils.getXmlDto(tables, "name", TableName);
				}
				if (table == null) {
					return;
				}
				updateByTable();
				if (fields == null || fields.isEmpty()) {
					return;
				}
				String CodeField = root.elementText("CodeField");
				if (!CommonUtils.isStrEmpty(CodeField)) {
					if (codeCendder) {
						codefield = CommonUtils.getXmlDto(fields, "fieldname", CodeField);
					} else {
						codefield = CommonUtils.getXmlDto(fields, "name", CodeField);
					}
					if (codefield != null) {
						textField_2.setText(codefield.toString());
					}
				}
				String DisplayField = root.elementText("DisplayField");
				if (!CommonUtils.isStrEmpty(DisplayField)) {
					if (codeCendder) {
						displayfield = CommonUtils.getXmlDto(fields, "fieldname", DisplayField);
					} else {
						displayfield = CommonUtils.getXmlDto(fields, "name", DisplayField);
					}
					if (displayfield != null) {
						textField_3.setText(displayfield.toString());
					}
				}
				String Condi = root.elementText("Condi");
				if (!CommonUtils.isStrEmpty(Condi)) {
					textArea.setText(new String(CommonUtils.base64Dcode(Condi)));
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		dialog = defaultpropeditor.getDialog();
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();

	}
}
