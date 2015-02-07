package youngfriend.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.PropDto;
import youngfriend.coms.DefaultCom;
import youngfriend.coms.IStyleCom;
import youngfriend.editors.PropEditor;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

/**
 * 属性编辑器
 * 
 * @author xiong
 * 
 */
public class PropEditorTable extends JTable {
	private static final long serialVersionUID = 1L;
	private Logger logger = LogManager.getLogger(PropEditorTable.class.getName());
	private static final String[] forbitProps = { "Colums", };

	class MyEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private ButtonEditorCom editorPnl;

		@Override
		public Object getCellEditorValue() {
			return editorPnl.getValue();
		}

		@Override
		public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, final int column) {
			TableCellRenderer render = table.getCellRenderer(row, column);
			editorPnl = (ButtonEditorCom) render.getTableCellRendererComponent(table, value, isSelected, true, row, column);
			editorPnl.addAction(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PropDto p = (PropDto) getValueAt(row, 0);
					String editClass = p.getEditclass();
					if (!CommonUtils.isStrEmpty(editClass)) {
						try {
							PropEditor editor = (PropEditor) Class.forName(editClass).newInstance();
							editor.edit(p, MainFrame.getInstance());
						} catch (Exception ex) {
							GUIUtils.showMsg(MainFrame.getInstance(), "编辑器导入失败");
							logger.error(ex.getMessage(), ex);
							throw new RuntimeException(ex);
						}
					}
					ButtonEditorCom editorCom = (ButtonEditorCom) PropEditorTable.this.getEditorComponent();
					editorCom.setValue(p.getValue());
					CompUtils.stopTabelCellEditor(PropEditorTable.this);
				}
			});
			return editorPnl;
		}
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		if (column != 1) {
			return super.getCellEditor(row, column);
		}
		final PropDto p = (PropDto) getValueAt(row, 0);
		Object value = null;
		String rendertype = p.getRenderertype();
		if ("boolean".equals(rendertype)) {
			value = "true".equals(p.getValue());
		} else {
			value = p.getValue();
		}
		final Component editorCom = getCellRenderer(row, column).getTableCellRendererComponent(PropEditorTable.this, value, true, true, row, column);
		p.getRenderertype();
		if ("combo".equalsIgnoreCase(rendertype)) {
			JComboBox c = (JComboBox) editorCom;
			return new DefaultCellEditor(c) {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
					return editorCom;
				}
			};
		} else if ("boolean".equalsIgnoreCase(rendertype)) {
			JCheckBox c = (JCheckBox) editorCom;
			return new DefaultCellEditor(c) {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
					return editorCom;
				}
			};
		} else if ("custom".equalsIgnoreCase(rendertype)) {
			return new MyEditor();
		} else {
			JTextField c = (JTextField) editorCom;
			if ("no".equals(rendertype)) {
				c.setEditable(false);
			}
			return new DefaultCellEditor(c) {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
					return editorCom;
				}
			};
		}
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		return new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, final int row, final int column) {

				if (column != 1) {
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				}
				final PropDto p = (PropDto) getValueAt(row, 0);
				String pValue = p.getValue();
				String rendertype = p.getRenderertype();
				Component editorCom = null;
				if ("combo".equalsIgnoreCase(rendertype)) {
					final JComboBox c = new JComboBox();
					DefaultComboBoxModel model = new DefaultComboBoxModel();
					final Map<String, String> enums = p.getEnums();
					int index = 0;
					int count = 0;
					for (String key : enums.keySet()) {
						String temp = enums.get(key);
						if (temp.equalsIgnoreCase(pValue)) {
							index = count;
						}
						model.addElement(key);
						count++;
					}
					c.setModel(model);
					c.setSelectedIndex(index);
					editorCom = c;
				} else if ("boolean".equalsIgnoreCase(rendertype)) {
					JCheckBox c = new JCheckBox();
					c.setSelected("true".equalsIgnoreCase(pValue));
					editorCom = c;
				} else if ("custom".equalsIgnoreCase(rendertype)) {
					boolean fobitEdit = Arrays.asList(forbitProps).contains(p.getPropname());
					ButtonEditorCom c = new ButtonEditorCom(!fobitEdit, null);
					if (fobitEdit) {
						c.setValue("复合字段");
					} else {
						c.setValue(pValue);
					}
					editorCom = c;
				} else {
					JTextField c = new JTextField();
					c.setText(pValue);
					editorCom = c;
				}
				return editorCom;
			}
		};
	}

	@Override
	public void editingStopped(ChangeEvent e) {
		super.editingStopped(e);
		TableCellEditor editor = (TableCellEditor) e.getSource();
		Object value = editor.getCellEditorValue();
		int row = getSelectedRow();
		final PropDto p = (PropDto) getValueAt(row, 0);
		String rendertype = p.getRenderertype();
		p.getRenderertype();
		List<IStyleCom> selects = CompUtils.getSelect();
		String pValue = "";
		if ("boolean".equalsIgnoreCase(rendertype)) {
			pValue = (Boolean) value ? "true" : "false";
		} else if ("combo".equals(rendertype)) {
			Map<String, String> enums = p.getEnums();
			for (String key : enums.keySet()) {
				if (key.equalsIgnoreCase((String) value)) {
					pValue = enums.get(key);
					break;
				}
			}
		} else {
			pValue = (String) value;
			if (selects.size() == 1 && p.getPropname().equals("Name")) {
				if (CompUtils.hasSaveNameCom(selects.get(0).getParentPnl(), selects.get(0), pValue)) {
					if (!GUIUtils.showConfirm(MainFrame.getInstance(), "存在相同控件名称，是否系统自动生成新id?")) {
						return;
					} else {
						pValue = CompUtils.getComName();
					}
				}

			}
		}
		String pName = p.getPropname();
		for (IStyleCom com : selects) {
			com.setPropValue(pName, pValue);
			if (com.isUIProp(pName)) {
				com.upateUIByProps();
			}
		}
		if (Arrays.asList(DefaultCom.toStringProps).contains(pName.toLowerCase())) {
			// 更新控件树
			CompUtils.getStyleMain().updateUIComTree();
		}
	}
}