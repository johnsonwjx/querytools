package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import youngfriend.beans.ValueEditor;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

public class NewClassEditor extends JPanel implements ValueEditor {
	public NewClassEditor() {
		this.setPreferredSize(new Dimension(378, 216));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel label = new JLabel("\u76EE\u5F55\uFF1A");
		label.setBounds(22, 20, 61, 16);
		panel_1.add(label);

		JLabel label_1 = new JLabel("\u540D\u79F0\uFF1A");
		label_1.setBounds(22, 56, 61, 16);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("\u522B\u540D\uFF1A");
		label_2.setBounds(22, 92, 61, 16);
		panel_1.add(label_2);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(81, 11, 259, 28);
		panel_1.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(81, 50, 259, 28);
		panel_1.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(81, 89, 259, 28);
		panel_1.add(textField_2);

		JLabel label_3 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_3.setBounds(22, 128, 61, 16);
		panel_1.add(label_3);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(81, 128, 259, 28);
		panel_1.add(textField_3);
	}

	private static final long serialVersionUID = 1L;
	private boolean submit;
	private JDialog dialog;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private Map<String, String> props;

	private void save() {
		String catalog = textField.getText().trim();
		String name = textField_1.getText().trim();
		String alias = textField_2.getText().trim();
		String desc = textField_3.getText().trim();
		if (CommonUtils.isStrEmpty(catalog)) {
			GUIUtils.showMsg(dialog,"目录名为空");
			return;
		}
		if (CommonUtils.isStrEmpty(name)) {
			GUIUtils.showMsg(dialog,"名称为空");
			textField_1.requestFocus();
			return;
		}
		if (CommonUtils.isStrEmpty(alias)) {
			GUIUtils.showMsg(dialog, "别名为空");
			textField_2.requestFocus();
			return;
		}
		props.put("name", name);
		props.put("alias", alias);
		props.put("description", desc);
		submit = true;
		dialog.dispose();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		textField.setText(props.get("catalogname"));
		;
		dialog = GUIUtils.getDialog(owner, "新增查询类", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
