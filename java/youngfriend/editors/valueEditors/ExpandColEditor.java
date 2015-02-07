package youngfriend.editors.valueEditors;

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
import javax.swing.border.EtchedBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;

public class ExpandColEditor extends JPanel implements ValueEditor {
	public ExpandColEditor() {
		this.setPreferredSize(new Dimension(515, 198));
		setLayout(new BorderLayout(0, 0));
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);

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

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u5B57\u6BB5\u6269\u5C55\u524D\u7F00");
		lblNewLabel.setBounds(18, 108, 82, 16);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(142, 105, 281, 28);
		panel.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("\u5217\u4FE1\u606F\u8868\u522B\u540D");
		label.setBounds(18, 69, 82, 16);
		panel.add(label);

		textField_1 = new JTextField();
		textField_1.setBounds(142, 69, 281, 28);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton = new JButton("\u9009\u62E9");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCodeTable(textField_1);
			}
		});
		btnNewButton.setBounds(426, 70, 66, 29);
		panel.add(btnNewButton);

		JLabel label_1 = new JLabel("\u884C\u4FE1\u606F\u8868\u522B\u540D (\u5C42\u6570)");
		label_1.setBounds(6, 30, 124, 16);
		panel.add(label_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(142, 30, 281, 28);
		panel.add(textField_2);

		JButton button_2 = new JButton("\u9009\u62E9");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCodeTable(textField_2);
			}
		});
		button_2.setBounds(426, 31, 66, 29);
		panel.add(button_2);
	}

	private void selectCodeTable(JTextField textF) {
		XMLDto value = null;
		String txt = textF.getText().trim();
		if (!CommonUtils.isStrEmpty(txt)) {
			value = CommonUtils.getXmlDto(CompUtils.getCodeTables(), "alias", txt);
		}
		if (pnl == null) {

			pnl = CompUtils.getCodeTablePnl(value);
		} else {
			pnl.setValue(value);
		}
		pnl.edit(dialog, null);
		if (pnl.isChange()) {
			value = pnl.getSelect();
			if (value != null) {
				textF.setText(value.getValue("alias"));
			} else {
				textF.setText("");
			}
		}
	}

	private static final long serialVersionUID = -4586955508068431414L;
	private boolean submit;
	private JDialog dialog;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private Map<String, String> props;
	private JTextField textField;
	private JTextField textField_1;
	private ObjectSelectPnl<XMLDto> pnl = null;
	private JTextField textField_2;

	private void save() {
		try {
			if (CommonUtils.isStrEmpty(textField_1.getText().trim()) || CommonUtils.isStrEmpty(textField_2.getText().trim())) {
				GUIUtils.showMsg(dialog, "扩展表别名不能为空");
				return;
			}
			props.put("value", textField_2.getText().trim() + "," + textField_1.getText().trim() + "," + textField.getText().trim());
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
		String[] arr = value.split(",");
		if (arr.length == 0) {
			return;
		}
		textField_2.setText(CommonUtils.getArrValue(arr, 0, true));
		textField_1.setText(CommonUtils.getArrValue(arr, 1, true));
		textField.setText(CommonUtils.getArrValue(arr, 2, true));
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		String value = props.get("value");
		initData(value);
		dialog = GUIUtils.getDialog(owner, "扩展列设置", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
