/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;

import youngfriend.beans.ValueEditor;

import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

/**
 * 
 * @author xiong
 */
public class CustomBatchUpdateEditor extends JPanel implements ValueEditor {

	private static final long serialVersionUID = 1L;

	public CustomBatchUpdateEditor() {
		initComponents();
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(527, 413));
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("固定条件(SQL语句的where部分)"));
		jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		jTextArea1.setColumns(20);
		jTextArea1.setLineWrap(true);
		jTextArea1.setRows(5);
		jTextArea1.setAutoscrolls(false);
		jScrollPane1.setViewportView(jTextArea1);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 80));
		panel.setLayout(null);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(17, 52, 82, 16);
		panel.add(jLabel2);

		jLabel2.setText("需要更新的表:");
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(40, 11, 56, 16);
		panel.add(jLabel1);

		jLabel1.setText("服务名称:");
		jTextField1 = new javax.swing.JTextField();
		jTextField1.setBounds(108, 5, 212, 28);
		panel.add(jTextField1);
		jTextField2 = new javax.swing.JTextField();
		jTextField2.setBounds(108, 46, 212, 28);
		panel.add(jTextField2);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setForeground(Color.RED);
		jLabel3.setBounds(332, 52, 186, 16);
		panel.add(jLabel3);

		jLabel3.setText("如果更新代码中心,请输入表别名");
		setLayout(new BorderLayout(0, 0));
		add(panel, BorderLayout.NORTH);
		add(jScrollPane1);

		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		jButton1 = new javax.swing.JButton();
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel_1.add(jButton1);

		jButton1.setText("确定");
		jButton2 = new javax.swing.JButton();
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel_1.add(jButton2);

		jButton2.setText("取消");
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private JDialog dialog;
	private JPanel panel;
	private JPanel panel_1;
	private boolean submit;
	private Map<String, String> props;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		this.props = props;
		this.submit = false;
		initData();
		dialog = GUIUtils.getDialog(owner, "通用自定义批量更新台账表", this);
		dialog.setVisible(true);
	}

	private void save() {
		String serviceName = jTextField1.getText();
		String tableName = jTextField2.getText();
		if (CommonUtils.isStrEmpty(serviceName) || CommonUtils.isStrEmpty(tableName)) {
			GUIUtils.showMsg(dialog, "服务名和表名都不能为空");
			return;
		}
		String constCondiInfo = jTextArea1.getText();
		StringBuilder paramStr = new StringBuilder();
		paramStr.append("serviceName:").append(CommonUtils.base64Encode(serviceName.getBytes())).append(";tableName:")//
				.append(CommonUtils.base64Encode(tableName.getBytes()));
		if (constCondiInfo.length() > 0) {
			paramStr.append(";constCondiInfo:").append(CommonUtils.base64Encode(constCondiInfo.getBytes()));
		}
		props.put("inparam", paramStr.toString());
		submit = true;
		dialog.dispose();

	}

	private void initData() {
		String inparam = props.get("inparam");
		if (CommonUtils.isStrEmpty(inparam)) {
			return;
		}
		String[] params = inparam.split(";");
		for (String paramStr : params) {
			String[] param = paramStr.split(":");
			String value = new String(CommonUtils.base64Dcode(param[1]));
			if ("serviceName".equals(param[0])) {
				jTextField1.setText(value);
			} else if ("tableName".equals(param[0])) {
				jTextField2.setText(value);
			} else if ("constCondiInfo".equals(param[0])) {
				jTextArea1.setText(value);
			}
		}
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
