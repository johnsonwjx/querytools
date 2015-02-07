/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowLockStatePnl.java
 *
 * Created on 2011-12-12, 11:07:58
 */
package youngfriend.gui;

import java.awt.Dimension;
import java.awt.Window;
import java.util.Map;

import javax.swing.JDialog;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.GUIUtils;

/**
 * 
 * @author yf
 */
public class ShowLockStatePnl extends javax.swing.JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;

	public ShowLockStatePnl(XMLDto lock) {
		initComponents();
		setLayout(null);
		add(jLabel1);
		add(description);
		add(jLabel6);
		add(jLabel2);
		add(jLabel4);
		add(sate);
		add(opentime);
		add(ip);
		add(jLabel3);
		add(jLabel5);
		add(closetime);
		add(hostname);
		cencel = new javax.swing.JButton();
		cencel.setBounds(521, 114, 73, 29);
		add(cencel);
		cencel.setText("\u53D6\u6D88");
		unlockBT = new javax.swing.JButton();
		unlockBT.setBounds(430, 114, 75, 29);
		add(unlockBT);

		unlockBT.setText("解锁");
		unlockBT.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				submit = true;
				dialog.dispose();
			}
		});
		cencel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dialog.dispose();
			}
		});
		initData(lock);
	}

	private void initData(XMLDto lock) {
		this.description.setText(lock.getValue("description"));
		this.hostname.setText(lock.getValue("computer_name"));
		this.ip.setText(lock.getValue("computer_ip"));
		if ("0".equals(lock.getValue("state")))
			this.sate.setText("未锁定");
		else if ("1".equals(lock.getValue("state")))
			this.sate.setText("已锁定");
		this.opentime.setText(lock.getValue("open_time"));
		this.closetime.setText(lock.getValue("close_time"));
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(600, 156));
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(6, 12, 65, 16);
		description = new javax.swing.JTextField();
		description.setBounds(77, 6, 517, 28);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(20, 46, 51, 16);
		ip = new javax.swing.JTextField();
		ip.setBounds(77, 40, 279, 28);
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(362, 46, 65, 16);
		hostname = new javax.swing.JTextField();
		hostname.setBounds(433, 40, 161, 28);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(6, 80, 65, 16);
		opentime = new javax.swing.JTextField();
		opentime.setBounds(77, 74, 279, 28);
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(362, 80, 65, 16);
		closetime = new javax.swing.JTextField();
		closetime.setBounds(433, 74, 161, 28);
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(32, 114, 39, 16);
		sate = new javax.swing.JTextField();
		sate.setBounds(77, 108, 279, 28);

		jLabel1.setText("描述信息：");

		description.setEditable(false);

		jLabel2.setText("ip地址：");

		ip.setEditable(false);

		jLabel3.setText("机器名称：");

		hostname.setEditable(false);

		jLabel4.setText("访问时间：");

		opentime.setEditable(false);

		jLabel5.setText("关闭时间：");

		closetime.setEditable(false);

		jLabel6.setText("状态：");

		sate.setEditable(false);
	}

	private javax.swing.JTextField closetime;
	private javax.swing.JTextField description;
	private javax.swing.JTextField hostname;
	private javax.swing.JTextField ip;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JButton cencel;
	private javax.swing.JTextField opentime;
	private javax.swing.JTextField sate;
	private javax.swing.JButton unlockBT;
	private boolean submit;
	private JDialog dialog;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		dialog = GUIUtils.getDialog(owner, "锁信息", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}
