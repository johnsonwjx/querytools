package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import youngfriend.beans.ValueEditor;
import youngfriend.gui.Lst2LstSelPnl.Action4Lst;
import youngfriend.utils.GUIUtils;

public class ListChooseListPnl<T> extends JPanel implements ValueEditor {
	private JDialog dialog;
	private boolean submit = false;
	private Lst2LstSelPnl<T> pnl;
	private String title;

	public Collection<T> getValues() {
		if (submit) {
			return pnl.getValues();
		} else {
			return pnl.getInitValues();
		}
	}

	public void setValues(Collection<T> values) {
		pnl.setValue(values);
	}

	public ListChooseListPnl(Window owner, String title, List<T> all, Action4Lst action) {
		this(owner, title, all, action, "", "");
	}

	public ListChooseListPnl(Window owner, String title, List<T> all, Action4Lst action, String title1, String title2) {
		this.title = title;
		this.setPreferredSize(new Dimension(604, 601));
		setLayout(new BorderLayout(0, 0));
		pnl = new Lst2LstSelPnl<T>(dialog, all, 600, 0, title1, title2);
		this.add(pnl, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submit = true;
				dialog.dispose();
			}
		});
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submit = false;
				dialog.dispose();
			}
		});
		panel.add(button_1);
		if (action != null) {
			pnl.setActon(action);
		}
	}

	private static final long serialVersionUID = 1L;

	public boolean isSubmit() {
		return submit;
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		dialog = GUIUtils.getDialog(owner, title, this);
		dialog.setVisible(true);
	}

}
