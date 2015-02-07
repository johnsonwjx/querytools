/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SetMinSumField.java
 *
 * Created on 2013-2-26, 17:15:43
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.ButtonCellEditor;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class MinSumEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog;
	private JTable jtable;
	private DefaultTableModel model;
	private String[] columnnames;

	public MinSumEditor() {
		initComponents();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(848, 387));
		sp = new javax.swing.JScrollPane();
		jcb = new javax.swing.JCheckBox();
		jcb.setText("是否需要计算小计");
		setLayout(new BorderLayout(0, 0));
		add(jcb, BorderLayout.NORTH);
		add(sp);

		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		addrow = new javax.swing.JButton();
		panel.add(addrow);

		addrow.setText("  \u589E\u52A0  ");
		delrow = new javax.swing.JButton();
		panel.add(delrow);

		delrow.setText("  \u5220\u9664 ");

		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(200, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator);
		okButton = new javax.swing.JButton();
		panel.add(okButton);

		okButton.setText("确认");
		cancelButton = new javax.swing.JButton();
		panel.add(cancelButton);

		cancelButton.setText("取消");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultpropeditor.disposeDialog();
			}
		});
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultpropeditor.save();
			}
		});
		addrow.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addItem();
			}
		});
	}

	private javax.swing.JButton addrow;
	private javax.swing.JButton cancelButton;
	private javax.swing.JButton delrow;
	private javax.swing.JCheckBox jcb;
	private javax.swing.JButton okButton;
	private javax.swing.JScrollPane sp;
	private JPanel panel;
	private JSeparator separator;
	private DefaultPropEditor defaultpropeditor;

	private void init(String groupfield) {
		String[] groupby = groupfield.split(",");
		StringBuffer sb = new StringBuffer();
		try {
			JComboBox groupbox = new JComboBox();
			groupbox.addItem(null);
			int width = getPreferredSize().width;
			for (int i = 0; i < groupby.length; i++) {
				String name = groupby[i];
				XMLDto item = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", name);
				groupbox.addItem(item);
				int index = i + 1;
				if (i < groupby.length - 1) {
					if (groupby.length > 1) {
						sb.append("分类字段" + index).append(",").append("分类字段" + index + "名称").append(",");
					}
				}
				if (i == groupby.length - 1) {
					sb.append("小计显示字段");
				}
				if (i > 1) {
					width += 100;
				}
			}
			this.setPreferredSize(new Dimension(width, this.getPreferredSize().height));
			columnnames = sb.toString().split(",");
			model = new DefaultTableModel(columnnames, 0) {
				private static final long serialVersionUID = 1L;

				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return XMLDto.class;
				}
			};
			jtable = new JTable(model);
			TableColumnModel tcm = jtable.getColumnModel();
			for (int i = 0; i < columnnames.length; i++) {
				if (i % 2 == 0 && i != columnnames.length - 1) {
					tcm.getColumn(i).setCellEditor(new DefaultCellEditor(groupbox));
				} else {
					ButtonCellEditor cellEditor = new ButtonCellEditor(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							CompUtils.stopTabelCellEditor(jtable);
							XMLDto value = (XMLDto) jtable.getValueAt(jtable.getSelectedRow(), jtable.getSelectedColumn());
							ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
							pnl.setValue(value);
							pnl.edit(dialog, null);
							if (pnl.isChange()) {
								value = pnl.getSelect();
								jtable.setValueAt(value, jtable.getSelectedRow(), jtable.getSelectedColumn());
							}
						}
					}, false);
					tcm.getColumn(i).setCellEditor(cellEditor);
					tcm.getColumn(i).setCellRenderer(cellEditor.getTableCellRenderer());
				}
			}
			jtable.setRowSelectionAllowed(true);
			jtable.setColumnSelectionAllowed(false);
			sp.setViewportView(jtable);
			CompUtils.tableDelRow(delrow, jtable, -1);

		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private XMLDto getDtoByStr(String str) {
		if (CommonUtils.isStrEmpty(str)) {
			return null;
		}
		return CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", str.split("=")[0]);
	}

	private void addItem() {
		int rcount = jtable.getRowCount();
		((DefaultTableModel) jtable.getModel()).addRow(new Object[] { null, null, null });
		jtable.setRowSelectionInterval(rcount, rcount);

	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		String groupfield = prop.getCom().getPropValue("GroupByField");
		if (CommonUtils.isStrEmpty(groupfield)) {
			GUIUtils.showMsg(MainFrame.getInstance(), "请先设置分类汇总字段");
			return;
		}
		if (groupfield.split(",").length < 2) {
			GUIUtils.showMsg(MainFrame.getInstance(), "分类汇总字段只有一个不能设小计");
			return;
		}
		init(groupfield);

		IPropEditorOper oper = new IPropEditorOper() {
			@Override
			public boolean save() {
				CompUtils.stopTabelCellEditor(jtable);
				int rowSize = model.getRowCount();
				if (!jcb.isSelected() && rowSize <= 0) {
					prop.setValue("");
				} else if (rowSize <= 0 && jcb.isSelected()) {
					GUIUtils.showMsg(dialog, "请设置");
					return false;
				} else {
					Element root = DocumentHelper.createElement("root");
					root.addElement("needSum").setText(jcb.isSelected() ? "1" : "0");
					Element params = root.addElement("params");
					Element field = null;
					for (int i = 0; i < rowSize; i++) {
						Element param = DocumentHelper.createElement("param");
						int colCount = model.getColumnCount();
						boolean hasValue = false;
						for (int j = 0; j < colCount; j++) {
							XMLDto dto = (XMLDto) model.getValueAt(i, j);
							String str = "";
							if (dto != null) {
								str = dto.getValue("itemname") + "=" + dto.getValue("itemlabel");
							}
							if (j == colCount - 1) {
								param.addElement("caption").setText(str);
								if (dto != null) {
									hasValue = true;
								}
							} else if (j % 2 == 0) {
								if (dto == null) {
									continue;
								}
								field = DocumentHelper.createElement("field");
								field.addElement("index").setText((j / 2 + 1) + "");
								field.addElement("code").setText(str);
								hasValue = true;
							} else {
								if (field == null) {
									continue;
								}
								field.addElement("name").setText(str);
								param.add(field);
								field = null;
							}
						}
						if (hasValue) {
							params.add(param);
						} else {
							GUIUtils.showMsg(dialog, "请设置");
							jtable.setRowSelectionInterval(i, i);
							return false;
						}
					}
					prop.setValue(root.asXML());
				}
				return true;
			}

			@Override
			public void initData() {

				Element root;
				try {
					root = DocumentHelper.parseText(prop.getValue()).getRootElement();
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
				Element params = root.element("params");
				if (params == null) {
					return;
				}
				jcb.setSelected(!"0".equals(root.elementTextTrim("needSum")));
				List<Element> pas = params.elements("param");
				if (pas == null || pas.isEmpty()) {
					return;
				}
				for (int i = 0; i < pas.size(); i++) {
					Element param = pas.get(i);
					model.addRow(new Object[] {});
					XMLDto temp = null;
					String caption = param.elementText("caption");
					temp = getDtoByStr(caption);
					model.setValueAt(temp, i, model.getColumnCount() - 1);
					List<Element> fields = param.elements("field");
					if (fields == null || fields.isEmpty()) {
						continue;
					}
					for (Element field : fields) {
						String index = field.elementText("index");
						String code = field.elementText("code");
						temp = getDtoByStr(code);
						if (temp == null || CommonUtils.isStrEmpty(index) || !CommonUtils.isNumberString(index)) {
							continue;
						}
						int numIndex = Integer.parseInt(index);
						int codeIndex = 2 * (numIndex - 1);
						int nameIndex = codeIndex + 1;
						String name = field.elementText("name");
						model.setValueAt(temp, i, codeIndex);
						temp = getDtoByStr(name);
						model.setValueAt(temp, i, nameIndex);
					}
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		dialog = defaultpropeditor.getDialog();
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();

	}
}
