/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.main;

import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.common.util.net.ServiceInvokerUtil;
import youngfriend.common.util.net.exception.ServiceInvokerException;
import youngfriend.utils.GUIUtils;

/**
 * 单例登陆通用类
 * 
 * @author yfcd
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public enum V6LoginUtil {
	INSTANCE;
	public static final String V6LOGIN_USER = "user";
	public static final String V6LOGIN_PWD = "pwd";
	public static final String V6LOGIN_WEB_ADD = "web-add";
	public static final String V6LOGIN_SERVER_ADD = "server-add";
	public static final String V6LOGIN_SERVER_PROXY = "web-proxy";
	private File defaultfile = null;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	public static V6LoginUtil getInstance() {
		return INSTANCE;
	}

	public static void login(Frame frame, String title, boolean bln) throws Exception {
		setUIFont();
		V6LoginPnl pnl = new V6LoginPnl();
		JDialog dlg = GUIUtils.getDialog(frame, title, pnl);
		dlg.setResizable(false);
		dlg.setVisible(true);
		if (!pnl.isIsLoginSucessed()) {// 登录失败
			System.exit(0);
		}
	}

	public static void setUIFont() throws Exception {
		Font myFont = new Font("宋体", Font.PLAIN, 12);
		javax.swing.plaf.FontUIResource fontRes = new javax.swing.plaf.FontUIResource(myFont);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
		if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {// 苹果系统换皮肤
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}

	}

	public static boolean stringNull(String val) {
		return val == null || "".equals(val) ? true : false;
	}

	public static void loginSystem(String registerName, String password, String ip, String computerName) throws Exception {

		Hashtable sendTab = new Hashtable();
		sendTab.put("service", "useraccess.login");
		sendTab.put("registerName", registerName);
		sendTab.put("password", password);
		sendTab.put("ip", ip);
		sendTab.put("computerName", computerName);
		sendTab.put("keyNum", "");
		sendTab.put("ptype", "client");
		// UserAccess服务地址为空的时候报错
		if (stringNull(System.getProperties().getProperty("useraccess"))) {
			throw new ServiceInvokerException(V6LoginUtil.class, "验证用户失败！", "身份认证服务没有启用！");
		}
		Hashtable reTab = ServiceInvokerUtil.invoker(sendTab);
		String assID = (String) reTab.get("sysAccessID");
		System.getProperties().put("sysAccessID", assID);
	}

	public static String iniRuntimeEnv(boolean useInvokermodel, String url) throws Exception {
		boolean proxy = true;// 始终通过web代理连接
		return setAllServiceInfo(url, proxy, useInvokermodel);
	}

	public static boolean connected() {
		return true;
	}

	public static String sendData(String msgVar, String serviceUrl) throws Exception {
		return youngfriend.common.util.net.ServiceInvokerUtil.httpGet(serviceUrl, msgVar);
	}

	public static String setAllServiceInfo(String serviceUrl, boolean useWebProxy, boolean useInvokermodel) throws Exception {

		if (serviceUrl == null) {
			serviceUrl = "http://127.0.0.1:8080/yfengine";
		}
		// 检验地址的合法性
		if (!serviceUrl.toLowerCase().startsWith("http://")) {
			serviceUrl = "http://" + serviceUrl;
		}
		if (useWebProxy) {
			serviceUrl = serviceUrl + "/webproxy";
		}
		if (useInvokermodel) {
			ServiceInvokerUtil.invokermodel = "2";
		}

		String msgVar = "service := system.poperties.serviceslocation\nxml := ";
		String reMsg = sendData(msgVar, serviceUrl);// 调用服务并返回值

		if (stringNull(reMsg)) {
			throw new Exception("取所有服务的地址映射失败!");
		} else {
			reMsg = reMsg.substring(reMsg.indexOf(":=") + 2).trim();
			if (stringNull(reMsg)) {
				throw new Exception("取所有服务的地址映射失败!");
			}
		}

		Document doc = DocumentHelper.parseText(reMsg);
		String serverUrl = null;
		// 循环所有服务,并把服务名和地址写入内存
		for (Iterator iter = doc.getRootElement().elements("service").iterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			String key = element.attributeValue("name");
			// String value = serviceUrl;//用输入的地址作为服务地址
			// 因为有可能有服务器集群，所以不能用输入的地址作为服务地址
			String value = "http://" + element.attributeValue("addr");
			if (serverUrl == null) {
				serverUrl = value;
			}
			if (useWebProxy)// 使用集群，那样所有服务都用web地址
			{
				value = serviceUrl;
			}
			System.getProperties().put(key, value);
		}
		V6LoginUtil.getInstance().saveParam(V6LoginUtil.V6LOGIN_SERVER_ADD, serverUrl);
		return "ok";
	}

	private Properties loadp() throws Exception {
		Properties pro = new Properties();
		if (defaultfile == null) {
			defaultfile = new File("config" + File.separator + "config.properties");
			defaultfile.createNewFile();
		}
		if (defaultfile.exists()) {
			FileInputStream is = new FileInputStream(defaultfile);
			pro.load(is);
			is.close();
			is = null;
		}
		return pro;
	}

	public String getParam(String name) throws Exception {
		Properties pro = loadp();
		return pro.getProperty(name);
	}

	public String getParam(String name, String defaultvalue) throws Exception {
		String val = getParam(name);
		return val == null ? defaultvalue : val;
	}

	public void saveParam(String name, String value) throws Exception {
		FileOutputStream os = null;
		try {
			Properties prop = loadp();
			prop.setProperty(name, value);
			os = new FileOutputStream(defaultfile);
			prop.store(os, "");
		} finally {
			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
}
