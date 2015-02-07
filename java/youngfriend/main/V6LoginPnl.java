/*
 * NewJPanel.java
 *
 * Created on 2011年9月29日, 上午10:13
 */
package youngfriend.main;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

/**
 * 
 * @author Administrator
 */
public class V6LoginPnl extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	private boolean isLoginSucessed = false;
	private int errLoginNum = 0;
	private static int LOGIN_SUCESSED = 2;
	private static int LOGIN_ERR_OUT = 1;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	public V6LoginPnl() {
		initComponents();
		String webAddr = getParam(V6LoginUtil.V6LOGIN_WEB_ADD);
		String login_user = getParam(V6LoginUtil.V6LOGIN_USER);
		String login_pwd = getParam(V6LoginUtil.V6LOGIN_PWD);
		if (!V6LoginUtil.stringNull(login_pwd)) {
			try {
				login_pwd = new String(CommonUtils.base64Dcode(login_pwd), "utf-8");
			} catch (UnsupportedEncodingException ex) {
				login_pwd = "";
			}
		}
		username.setText(V6LoginUtil.stringNull(login_user) ? "" : login_user);
		serverurl.setText(V6LoginUtil.stringNull(webAddr) ? "" : webAddr);
		password.setText(login_pwd);
		setLayout(null);
		add(jLabel8);
		add(jLabel7);
		add(jLabel9);
		add(jcbSavePassword);
		add(jButton1);
		add(jButton2);
		add(username);
		add(password);
		add(serverurl);
		add(useInvokermodelCK);
	}

	private void checkLogin() {
		try {
			boolean useInvokermodel = useInvokermodelCK.isSelected();
			saveParam(V6LoginUtil.V6LOGIN_SERVER_PROXY, "1");
			if (!"ok".equals(V6LoginUtil.iniRuntimeEnv(useInvokermodel, serverurl.getText()))) {
				JOptionPane.showMessageDialog(null, "服务器地址连接失败！");
			} else {
				saveParam(V6LoginUtil.V6LOGIN_WEB_ADD, serverurl.getText());
				// 登录
				int re = login();
				if (re == LOGIN_SUCESSED) {// 登录成功
					String encodePwd = "";
					try {
						encodePwd = password.getPassword().length <= 0 ? "" : new String(CommonUtils.base64Encode(new String(password.getPassword()).getBytes("utf-8")));
					} catch (UnsupportedEncodingException ex) {
					}
					saveParam(V6LoginUtil.V6LOGIN_USER, username.getText());
					saveParam(V6LoginUtil.V6LOGIN_PWD, jcbSavePassword.isSelected() ? encodePwd : "");
					isLoginSucessed = true;
					Window w = SwingUtilities.getWindowAncestor(this);
					w.dispose();
				}
				if (re == LOGIN_ERR_OUT) {// 登录失败关闭窗口
					saveParam(V6LoginUtil.V6LOGIN_USER, username.getText());
					saveParam(V6LoginUtil.V6LOGIN_PWD, "");
					isLoginSucessed = false;
					Window w = SwingUtilities.getWindowAncestor(this);
					w.dispose();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "登陆失败");
			logger.error(e.getMessage(), e);
		}

	}

	private int login() {
		// 调用服务“useraccess.login”验证用户权限,并把服务信息写入系统内存
		try {
			InetAddress ipAdd = InetAddress.getLocalHost();
			V6LoginUtil.loginSystem(username.getText(), new String(password.getPassword()), ipAdd.getHostAddress(), ipAdd.getHostName());
		} catch (Exception e) {
			errLoginNum++;// 累加失败次数
			if (errLoginNum > 2) {// 登录3次失败退出系统
				GUIUtils.showMsg(null, e.getMessage() + "\n系统将自动关闭！");
				return LOGIN_ERR_OUT;
			}
			logger.error(e.getMessage(), e);
			GUIUtils.showMsg(null, e.getMessage());
			return 0;
		}
		return LOGIN_SUCESSED;
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(470, 207));
		jButton2 = new javax.swing.JButton();
		jButton2.setBounds(355, 164, 78, 29);
		jButton1 = new javax.swing.JButton();
		jButton1.setBounds(275, 164, 78, 29);
		serverurl = new javax.swing.JTextField();
		serverurl.setBounds(122, 90, 266, 28);
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setBounds(20, 96, 90, 16);
		jLabel8 = new javax.swing.JLabel();
		jLabel8.setBounds(58, 54, 52, 16);
		password = new javax.swing.JPasswordField();
		password.setBounds(122, 48, 266, 28);
		username = new javax.swing.JTextField();
		username.setBounds(122, 6, 266, 28);
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setBounds(71, 12, 39, 16);
		useInvokermodelCK = new javax.swing.JCheckBox();
		useInvokermodelCK.setBounds(122, 129, 546, 23);
		jcbSavePassword = new javax.swing.JCheckBox();
		jcbSavePassword.setBounds(122, 153, 84, 23);

		jButton2.setText("取  消");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton1.setText("登  录");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		serverurl.setText("http://127.0.0.1:8080/yfengine");
		serverurl.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				serverurlKeyPressed(evt);
			}
		});

		jLabel9.setText("web服务器地址");

		jLabel8.setText("登录密码");

		password.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				passwordKeyPressed(evt);
			}
		});

		username.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				usernameKeyPressed(evt);
			}
		});

		jLabel7.setText("用户名");
		jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

		useInvokermodelCK.setText("启用加密方式传送数据(仅对参数需要加密的单位显示)");

		jcbSavePassword.setSelected(true);
		jcbSavePassword.setText("记住密码");
	}// </editor-fold>//GEN-END:initComponents

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		Window w = SwingUtilities.getWindowAncestor(this);
		w.dispose();
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		checkLogin();
	}

	private void passwordKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_passwordKeyPressed
		// TODO add your handling code here:
		enterKeyCheckLogin(evt);
	}// GEN-LAST:event_passwordKeyPressed

	private void usernameKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_usernameKeyPressed
		// TODO add your handling code here:
		enterKeyCheckLogin(evt);
	}// GEN-LAST:event_usernameKeyPressed

	private void serverurlKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_serverurlKeyPressed
		// TODO add your handling code here:
		enterKeyCheckLogin(evt);
	}// GEN-LAST:event_serverurlKeyPressed

	private void saveParam(String name, String value) {
		try {
			V6LoginUtil.getInstance().saveParam(name, value);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "发生错误");
			logger.error(e.getMessage(), e);
		}
	}

	private String getParam(String name) {
		try {
			return V6LoginUtil.getInstance().getParam(name);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "发生错误");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private void enterKeyCheckLogin(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			checkLogin();
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JCheckBox jcbSavePassword;
	private javax.swing.JPasswordField password;
	private javax.swing.JTextField serverurl;
	private javax.swing.JCheckBox useInvokermodelCK;
	private javax.swing.JTextField username;

	// End of variables declaration//GEN-END:variables

	public boolean isIsLoginSucessed() {
		return isLoginSucessed;
	}

	public void setIsLoginSucessed(boolean isOk) {
		this.isLoginSucessed = isOk;
	}
}
