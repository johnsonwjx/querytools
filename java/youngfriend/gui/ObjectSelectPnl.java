package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

public class ObjectSelectPnl<T> extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JDialog dialog = null;
	private JList list;
	private DefaultListModel model;
	private T initValue;
	private boolean submit = false;
	private Collection<T> all;

	public ObjectSelectPnl(Collection<T> all) {
		this.setPreferredSize(new Dimension(400, 480));
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2 || list.getSelectedIndex() < 0) {
					return;
				}
				submit = true;
				dialog.dispose();
			}
		});
		model = new DefaultListModel();
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submit = true;
				dialog.dispose();
			}
		});
		panel_1.add(button);
		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		panel_1.add(button_1);

		JButton button_3 = new JButton("\u6E05\u9009");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				list.clearSelection();
				submit = true;
				dialog.dispose();
			}
		});
		panel_1.add(button_3);
		this.all = all;
		if (all == null || all.isEmpty()) {
			return;
		}
		for (T item : all) {
			model.addElement(item);
		}
		this.add(new BtnListSearchPnl<XMLDto>(dialog, list, 30, null), BorderLayout.NORTH);
	}

	public void setValue(T value) {
		initValue = value;
		if (initValue == null || !all.contains(initValue)) {
			list.clearSelection();
			return;
		}
		list.setSelectedValue(value, true);
	}

	public T getSelect() {
		return (T) list.getSelectedValue();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		String title = "";
		if (props != null) {
			title = props.get("title");
			String width = props.get("wdith");
			if (!CommonUtils.isStrEmpty(width) && CommonUtils.isNumberString(width)) {
				this.setPreferredSize(new Dimension(this.getPreferredSize().height, Integer.parseInt(width)));
			}
		}
		if (CommonUtils.isStrEmpty(title)) {
			title = "Ñ¡Ôñ";
		}
		dialog = GUIUtils.getDialog(owner, title, this);
		dialog.setVisible(true);
	}

	public boolean isChange() {
		if (!submit) {
			return false;
		}
		if (initValue == null && getSelect() != null) {
			return true;
		}
		if (initValue != null && getSelect() == null) {
			return true;
		}
		if (getSelect() == null || initValue == null) {
			return false;
		}
		return !initValue.equals(getSelect());
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

	public boolean isNull() {
		return getSelect() == null;
	}
}
