/*
 * ChooseSortField.java
 *
 * Created on 2008年8月1日, 下午2:21
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.Validate;
import youngfriend.beans.XMLDto;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.InputEditor;
import youngfriend.gui.ListChooseListPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class FieldEventEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	public FieldEventEditor() {
		initComponents();
		init();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(411, 365));
		jPanel2 = new javax.swing.JPanel();
		okBT = new javax.swing.JButton();
		okBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}
		});
		jButton2 = new javax.swing.JButton();
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});

		setLayout(new java.awt.BorderLayout());

		jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

		okBT.setText("确定");
		jPanel2.add(okBT);

		jButton2.setText("取消");
		jPanel2.add(jButton2);

		add(jPanel2, java.awt.BorderLayout.SOUTH);
		sp = new javax.swing.JScrollPane();
		add(sp, BorderLayout.CENTER);

		list = new JList();
		sp.setViewportView(list);
		jPanel3 = new javax.swing.JPanel();
		add(jPanel3, BorderLayout.EAST);
		jPanel3.setLayout(new GridLayout(0, 1, 0, 0));
		btnAdd = new javax.swing.JButton();
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});

		btnAdd.setText("\u6DFB\u52A0\u5B57\u6BB5");
		jPanel3.add(btnAdd);
		btnUpdate = new javax.swing.JButton();
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editItem();
			}
		});

		btnUpdate.setText("\u4FEE\u6539");
		jPanel3.add(btnUpdate);
		btnDel = new javax.swing.JButton();
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delItem();
			}
		});

		btnDel.setText("\u5220\u9664");
		jPanel3.add(btnDel);

		label = new JLabel("");
		jPanel3.add(label);
		btnEvent = new javax.swing.JButton();
		btnEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventSet();
			}

		});

		btnEvent.setText("事件设置");
		jPanel3.add(btnEvent);
		clearEvent = new javax.swing.JButton();
		clearEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventClear();
			}

		});

		clearEvent.setText("清除事件");
		jPanel3.add(clearEvent);
	}

	private javax.swing.JButton btnAdd;
	private javax.swing.JButton btnDel;
	private javax.swing.JButton btnEvent;
	private javax.swing.JButton btnUpdate;
	private javax.swing.JButton clearEvent;
	private javax.swing.JButton jButton2;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JButton okBT;
	private javax.swing.JScrollPane sp;
	private JLabel label;
	private DefaultListModel model;
	private JList list;
	private DefaultPropEditor defaultpropeditor;

	public void init() {
		model = new DefaultListModel();
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void eventClear() {
		XMLDto dto = (XMLDto) list.getSelectedValue();
		if (dto == null) {
			return;
		}
		dto.setValue("hasEvent", "");
		dto.setValue("moduleinfo", "");
		list.updateUI();
	}

	private void eventSet() {
		XMLDto dto = (XMLDto) list.getSelectedValue();
		if (dto == null) {
			return;
		}
		ModulesEventEditor editor = new ModulesEventEditor();
		PropDto temp = new PropDto();
		temp.setValue(dto.getValue("moduleinfo"));
		editor.edit(temp, defaultpropeditor.getDialog());
		String eventXml = temp.getValue();
		if (CommonUtils.isStrEmpty(eventXml)) {
			dto.setValue("hasEvent", "");
		} else {
			dto.setValue("hasEvent", "已挂事件");
			eventXml = eventXml.replaceAll("\n", "");
		}
		dto.setValue("moduleinfo", eventXml);
	}

	private void delItem() {
		int index = list.getSelectedIndex();
		if (index < 0) {
			return;
		}
		model.removeElementAt(index);
		if (model.getSize() <= 0) {
			return;
		}
		if (index != 0) {
			index--;
		}
		list.setSelectedIndex(index);
	}

	private void editItem() {
		XMLDto dto = (XMLDto) list.getSelectedValue();
		if (dto == null) {
			return;
		}
		InputEditor editor = new InputEditor(new Validate<String>() {

			@Override
			public String validate(String obj) {
				return CommonUtils.isStrEmpty(obj) ? "不能为空" : null;
			}
		});
		Map<String, String> props = new HashMap<String, String>();
		props.put("value", dto.getValue("name"));
		editor.edit(defaultpropeditor.getDialog(), props);
		if (!editor.isSubmit()) {
			return;
		}
		String name = props.get("value");
		dto.setValue("name", name);
	}

	private void addItem() {
		ListChooseListPnl<XMLDto> pnl = new ListChooseListPnl<XMLDto>(defaultpropeditor.getDialog(), "选择", CompUtils.getFields(), null);
		pnl.edit(defaultpropeditor.getDialog(), null);
		if (!pnl.isSubmit()) {
			return;
		}
		Collection<XMLDto> values = pnl.getValues();
		if (values == null || values.isEmpty()) {
			return;
		}
		int index = list.getSelectedIndex();
		if (index < 0) {
			index = model.getSize();
		} else {
			index++;
		}
		for (XMLDto value : values) {
			List<String> toString = new ArrayList<String>();
			toString.add("name");
			toString.add("hasEvent");
			XMLDto dto = new XMLDto(toString);
			String name = value.getValue("itemlabel") + "[" + value.getValue("itemname") + "]";
			dto.setValue("name", name);
			dto.setValue("hasEvent", "");
			dto.setValue("moduleinfo", "");
			model.insertElementAt(dto, index);
			index++;
		}
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				if (model.getSize() <= 0) {
					prop.setValue("");
				} else {
					Element rootEle = DocumentHelper.createElement("root");
					for (int i = 0; i < model.getSize(); i++) {
						XMLDto dto = (XMLDto) model.getElementAt(i);
						Element item = rootEle.addElement("item");
						item.addElement("name").setText(dto.getValue("name"));
						item.addElement("moduleinfo").setText(dto.getValue("moduleinfo"));
					}
					prop.setValue(rootEle.asXML());
				}
				return true;
			}

			@Override
			public void initData() {
				Document doc;
				try {
					doc = DocumentHelper.parseText(prop.getValue());
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}
				Element rootEle = doc.getRootElement();
				List<Element> items = rootEle.elements("item");
				if (items.isEmpty()) {
					return;
				}
				for (Element item : items) {
					String name = item.elementText("name");
					String moduleinfo = item.elementText("moduleinfo");
					String hasEvent = CommonUtils.isStrEmpty(moduleinfo) ? "" : "已挂事件";
					List<String> toString = new ArrayList<String>();
					toString.add("name");
					toString.add("hasEvent");
					XMLDto dto = new XMLDto(toString);
					dto.setValue("name", name);
					dto.setValue("hasEvent", hasEvent);
					dto.setValue("moduleinfo", moduleinfo);
					model.addElement(dto);
				}

			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
