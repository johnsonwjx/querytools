/*
 * ChooseDataTable.java
 *
 * Created on 2007Äê8ÔÂ2ÈÕ, ÉÏÎç11:22
 */
package youngfriend.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;

import youngfriend.editors.DefaultPropEditor.IPropEditorOper;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

/**
 * 
 * @author Administrator
 */
public class ChartDefaultEditor extends JPanel implements PropEditor {

	private static final long serialVersionUID = 1L;

	public ChartDefaultEditor() {
		initComponents();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(514, 459));
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		jPanel2 = new javax.swing.JPanel();
		panel.add(jPanel2);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(12, 24, 65, 16);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(12, 52, 65, 16);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Öù×´Í¼È±Ê¡×Ö¶ÎÉèÖÃ"));

		jLabel5.setText("ºáÖá×Ö¶Î£º");

		jLabel6.setText("×ÝÖá×Ö¶Î£º");
		jPanel2.setLayout(null);
		jPanel2.add(jLabel5);
		jPanel2.add(jLabel6);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(78, 18, 253, 28);
		jPanel2.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(78, 52, 253, 28);
		jPanel2.add(textField_1);
		textField_1.setColumns(10);

		JButton button_2 = new JButton("...");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField);
			}
		});
		button_2.setBounds(374, 19, 93, 29);
		jPanel2.add(button_2);

		JButton button_3 = new JButton("...");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField_1);
			}
		});
		button_3.setBounds(374, 47, 93, 29);
		jPanel2.add(button_3);
		jPanel1 = new javax.swing.JPanel();
		panel.add(jPanel1);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(6, 24, 65, 16);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(6, 52, 65, 16);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("±ý×´Í¼È±Ê¡×Ö¶ÎÉèÖÃ"));

		jLabel4.setText("·ÖÀà×Ö¶Î£º");

		jLabel3.setText("ºÏ¼Æ×Ö¶Î£º");

		textField_2 = new JTextField();
		textField_2.setBounds(83, 24, 253, 28);
		textField_2.setEditable(false);
		textField_2.setColumns(10);

		button_4 = new JButton("...");
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField_2);
			}
		});
		button_4.setBounds(379, 25, 93, 29);

		textField_3 = new JTextField();
		textField_3.setBounds(83, 58, 253, 28);
		textField_3.setEditable(false);
		textField_3.setColumns(10);

		button_5 = new JButton("...");
		button_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField_3);
			}
		});
		button_5.setBounds(379, 53, 93, 29);
		jPanel1.setLayout(null);
		jPanel1.add(jLabel4);
		jPanel1.add(jLabel3);
		jPanel1.add(textField_2);
		jPanel1.add(textField_3);
		jPanel1.add(button_4);
		jPanel1.add(button_5);
		jPanel3 = new javax.swing.JPanel();
		panel.add(jPanel3);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setBounds(12, 24, 65, 16);
		jLabel8 = new javax.swing.JLabel();
		jLabel8.setBounds(12, 52, 65, 16);

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ïß×´Í¼È±Ê¡×Ö¶ÎÉèÖÃ"));

		jLabel7.setText("ºáÖá×Ö¶Î£º");

		jLabel8.setText("×ÝÖá×Ö¶Î£º");

		textField_4 = new JTextField();
		textField_4.setBounds(83, 24, 253, 28);
		textField_4.setEditable(false);
		textField_4.setColumns(10);

		button_6 = new JButton("...");
		button_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField_4);
			}
		});
		button_6.setBounds(381, 24, 93, 29);

		textField_5 = new JTextField();
		textField_5.setBounds(83, 58, 253, 28);
		textField_5.setEditable(false);
		textField_5.setColumns(10);

		button_7 = new JButton("...");
		button_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField_5);
			}
		});
		button_7.setBounds(381, 52, 93, 29);
		jPanel3.setLayout(null);
		jPanel3.add(jLabel7);
		jPanel3.add(jLabel8);
		jPanel3.add(textField_4);
		jPanel3.add(textField_5);
		jPanel3.add(button_7);
		jPanel3.add(button_6);
		jPanel4 = new javax.swing.JPanel();
		panel.add(jPanel4);
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setBounds(12, 24, 65, 16);
		jLabel10 = new javax.swing.JLabel();
		jLabel10.setBounds(12, 52, 65, 16);

		jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("ºáµÀÍ¼È±Ê¡×Ö¶ÎÉèÖÃ"));

		jLabel9.setText("ºáÖá×Ö¶Î£º");

		jLabel10.setText("×ÝÖá×Ö¶Î£º");

		textField_6 = new JTextField();
		textField_6.setBounds(83, 24, 253, 28);
		textField_6.setEditable(false);
		textField_6.setColumns(10);

		button_8 = new JButton("...");
		button_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField_6);
			}
		});
		button_8.setBounds(379, 25, 93, 29);

		textField_7 = new JTextField();
		textField_7.setBounds(83, 58, 253, 28);
		textField_7.setEditable(false);
		textField_7.setColumns(10);

		button_9 = new JButton("...");
		button_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setField(textField_7);
			}
		});
		button_9.setBounds(379, 53, 93, 29);
		jPanel4.setLayout(null);
		jPanel4.add(jLabel9);
		jPanel4.add(jLabel10);
		jPanel4.add(textField_6);
		jPanel4.add(textField_7);
		jPanel4.add(button_8);
		jPanel4.add(button_9);

		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.save();
			}

		});
		panel_1.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultpropeditor.disposeDialog();
			}
		});
		panel_1.add(button_1);
	}

	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton button_4;
	private JTextField textField_3;
	private JButton button_5;
	private JTextField textField_4;
	private JButton button_6;
	private JTextField textField_5;
	private JButton button_7;
	private JTextField textField_6;
	private JButton button_8;
	private JTextField textField_7;
	private JButton button_9;
	private DefaultPropEditor defaultpropeditor;

	private void setField(JTextField textField) {
		XMLDto value = null;
		String temp = textField.getText();
		if (!CommonUtils.isStrEmpty(temp)) {
			value = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", temp);
		}
		ObjectSelectPnl<XMLDto> editor = CompUtils.getFieldsPnl();
		editor.setValue(value);
		editor.edit(defaultpropeditor.getDialog(), null);
		if (!editor.isChange()) {
			return;
		}
		value = editor.getSelect();
		if (!editor.isNull()) {
			temp = value.getValue("itemname");
		} else {
			temp = "";
		}
		textField.setText(temp);
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		IPropEditorOper oper = new IPropEditorOper() {

			@Override
			public boolean save() {
				StringBuilder sb = new StringBuilder();
				if (isEmpty()) {
					prop.setValue("");
				} else {
					sb.append(getItemValue("bzt:", textField, textField_1)).append(";");
					sb.append(getItemValue("zzt:", textField_2, textField_3)).append(";");
					sb.append(getItemValue("hdt:", textField_4, textField_5)).append(";");
					sb.append(getItemValue("xzt:", textField_6, textField_7));
					prop.setValue(sb.toString());
				}
				return true;
			}

			protected String getItemValue(String title, JTextField t1, JTextField t2) {
				String temp1 = t1.getText();
				String temp2 = t2.getText();
				if (CommonUtils.isStrEmpty(temp1) && CommonUtils.isStrEmpty(temp2)) {
					return title;
				}
				title += CommonUtils.base64Encode(temp1.getBytes()) + "," + CommonUtils.base64Encode(temp2.getBytes());
				return title;
			}

			protected boolean isEmpty() {
				String temp = textField.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				temp = textField_1.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				temp = textField_2.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				temp = textField_3.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				temp = textField_4.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				temp = textField_5.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				temp = textField_6.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				temp = textField_7.getText();
				if (!CommonUtils.isStrEmpty(temp)) {
					return false;
				}
				return true;
			}

			@Override
			public void initData() {
				String[] arr = prop.getValue().split(";");
				if (arr.length <= 0) {
					return;
				}
				for (String temp : arr) {
					String[] itemArr = temp.split(":");
					if (itemArr.length < 2) {
						continue;
					}
					String[] tempArr = itemArr[1].split(",");
					if (tempArr.length != 2) {
						continue;
					}
					String field1 = new String(CommonUtils.base64Dcode(tempArr[0]));
					String field2 = new String(CommonUtils.base64Dcode(tempArr[1]));
					if ("bzt".equalsIgnoreCase(itemArr[0])) {
						textField.setText(field1);
						textField_1.setText(field2);
					} else if ("zzt".equalsIgnoreCase(itemArr[0])) {
						textField_2.setText(field1);
						textField_3.setText(field2);
					} else if ("hdt".equalsIgnoreCase(itemArr[0])) {
						textField_4.setText(field1);
						textField_5.setText(field2);
					} else if ("xzt".equalsIgnoreCase(itemArr[0])) {
						textField_6.setText(field1);
						textField_7.setText(field2);
					}

				}
			}
		};
		defaultpropeditor = new DefaultPropEditor(prop, this, oper, owner);
		defaultpropeditor.innitData();
		defaultpropeditor.showDialog();
	}

}
