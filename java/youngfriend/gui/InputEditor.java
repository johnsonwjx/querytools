package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

public class InputEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JDialog dialog = null;
	private boolean submit;
	private Map<String, String> props;
	private Validate<String> validate;
	private boolean change;
	private String oldValue;

	private void save() {
		if (validate != null) {
			String msg = validate.validate(textField.getText());
			if (!CommonUtils.isStrEmpty(msg)) {
				GUIUtils.showMsg(dialog, msg);
				return;
			}
		}
		if (textField.getText().trim().equals(oldValue)) {
			change = false;
		} else {
			change = true;
		}
		props.put("value", textField.getText());
		submit = true;
		dialog.dispose();

	}

	public InputEditor(Validate<String> validate) {
		this.validate = validate;
		init();
	}

	public InputEditor() {
		init();
	}

	private void init() {
		this.setPreferredSize(new Dimension(589, 87));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		panel.add(button_1);

		final JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		add(panel_1, BorderLayout.CENTER);

		JLabel label = new JLabel("             \u8F93\u5165\uFF1A");
		textField = new JTextField();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addComponent(label).addPreferredGap(ComponentPlacement.RELATED).addComponent(textField, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE).addGap(36)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panel_1.createSequentialGroup().addContainerGap(8, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(label, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addComponent(textField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		panel_1.setLayout(gl_panel_1);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save();
				}
			}

		});
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		change = false;
		this.props = props;
		String title = props.get("title");
		if (CommonUtils.isStrEmpty(title)) {
			title = " ‰»Î";
		}
		String width = props.get("width");
		if (!CommonUtils.isStrEmpty(width)) {
			this.textField.setColumns(Integer.parseInt(width));
		}
		oldValue = props.get("value");
		if (CommonUtils.isStrEmpty(oldValue)) {
			oldValue = "";
		} else {
			textField.setText(oldValue);
		}
		dialog = GUIUtils.getDialog(owner, title, this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

	public boolean isChange() {
		return change;
	}

}
