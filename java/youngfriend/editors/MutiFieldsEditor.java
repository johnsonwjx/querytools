/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClearFieldSetWindow.java
 *
 * Created on 2013-2-28, 9:56:57
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import youngfriend.beans.PropDto;
import youngfriend.coms.IStyleCom;
import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class MutiFieldsEditor extends JPanel implements PropEditor {
	private DefaultListModel m1 = new DefaultListModel();
	private DefaultListModel m2 = new DefaultListModel();

	public MutiFieldsEditor() {
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "\u53EF\u9009", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setLeftComponent(scrollPane);

		final JList list = new JList();
		list.setModel(m1);
		scrollPane.setViewportView(list);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new TitledBorder(null, "\u5DF2\u9009", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setRightComponent(scrollPane_1);

		final JList list_1 = new JList();

		list_1.setModel(m2);
		scrollPane_1.setViewportView(list_1);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}
		});
		panel.add(btnNewButton);
		JButton button = new JButton("\u53D6\u6D88");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel.add(button);
		this.setPreferredSize(new Dimension(744, 425));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				Object value = list.getSelectedValue();
				if (value == null) {
					return;
				}
				m2.addElement(value);
				m1.removeElement(value);
			}
		});

		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				Object value = list_1.getSelectedValue();
				if (value == null) {
					return;
				}
				m1.addElement(value);
				m2.removeElement(value);
			}
		});
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultPropEditor defaultpropeditor;

	class Bean {
		private String title;
		private String id;
		private String filed;
		private String type;

		public Bean(String title, String filed, String id, String type) {
			this.title = title;
			this.id = id;
			this.filed = filed;
			this.type = type;
		}

		@Override
		public String toString() {
			return CommonUtils.coverNull(title) + "," + CommonUtils.coverNull(filed) + "," + CommonUtils.coverNull(id) + "," + CommonUtils.coverNull(type);
		}
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		List<IStyleCom> coms = CompUtils.getWinComs();
		for (IStyleCom com : coms) {
			if (com.equals(prop.getCom()) || ComEum.TNewButton == com.getType() || ComEum.TNewLabel == com.getType()) {
				continue;
			}

			String field = com.getPropValue("FieldName");
			if (CommonUtils.isStrEmpty(field)) {
				continue;
			}

			String title = com.getPropValue("caption");
			if (CommonUtils.isStrEmpty(title)) {
				title = com.getPropValue("Caption");
			}
			String name = com.getPropValue("Name");
			Bean bean = new Bean(title, field, name, com.getType().getCName());
			m1.addElement(bean);
		}

		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				StringBuilder propValue = new StringBuilder();
				if (!m2.isEmpty()) {
					for (int i = 0; i < m2.getSize(); i++) {
						propValue.append(m2.getElementAt(i)).append(";");
					}
					propValue.deleteCharAt(propValue.length() - 1);
				}
				prop.setValue(propValue.toString());
				return true;
			}

			@Override
			public void initData() {
				if (m1.getSize() <= 0) {
					return;
				}
				Map<String, String> map = new HashMap<String, String>();
				String[] items = prop.getValue().split(";");
				for (String item : items) {
					String[] temp = item.split(",");
					if (temp.length > 3) {
						String id = temp[2];
						String field = temp[1];
						map.put(id, field);
					}
				}
				for (int i = 0; i < m1.getSize(); i++) {
					Bean bean = (Bean) m1.getElementAt(i);
					String name = bean.id;
					String field = bean.filed;
					if (map.containsKey(name) && field.equalsIgnoreCase(map.get(name))) {
						m2.addElement(bean);
						m1.removeElement(bean);
					}
				}
			}
		};

		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
