/*
 * ConfigIO.java
 *
 * Created on 2009年3月4日, 下午2:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package youngfriend.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author Administrator
 */
public class ConfigIO {

	private static final Logger logger = LogManager.getLogger(ConfigIO.class.getName());
	// D:\v6\src\tools\datatool\config
	static {
		File tmpFile = new File(new File("").getAbsolutePath() + File.separator + "config");
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		File historyFile = new File(new File("").getAbsolutePath() + File.separator + "history");
		if (!historyFile.exists()) {
			historyFile.mkdir();
		}
	}

	/** Creates a new instance of ConfigIO */
	public ConfigIO() {
	}

	public static Document readConfigFile(String fileName) {
		// String connectPara = "";
		Document doc = null;
		try {
			String serviceConfigFileName = new File("").getAbsolutePath() + File.separator + "config" + File.separator + fileName;
			File serviceConfigFile = new File(serviceConfigFileName);
			if (!serviceConfigFile.exists())
				return doc;
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(serviceConfigFile);

			return doc;
		} catch (Exception e) {
			logger.debug("获取服务连接参数出错");
			// e.printStackTrace();
		}
		return doc;
	}

	public static boolean saveConfigFile(String type, ServiceConfig config, String flag) {
		try {
			String configFileName = new File("").getAbsolutePath() + File.separator + "config/config.xml";
			File configFile = new File(configFileName);
			Document src = null;
			if (configFile.exists()) {
				SAXReader saxReader = new SAXReader();
				src = saxReader.read(configFile);
			}
			if (src == null) {
				src = DocumentHelper.createDocument();
			}
			Element root = src.getRootElement();
			if (root == null) {
				root = src.addElement("config");
			}

			Element serviceEle = root.element("service");
			if (serviceEle == null) {
				serviceEle = root.addElement("service");
			}

			Element addressEle = serviceEle.element("address");
			if (addressEle == null) {
				serviceEle.addElement("address").setText(config.getServiceAdd());
			} else {
				addressEle.setText(config.getServiceAdd());
			}
			Element portEle = serviceEle.element("port");
			if (portEle == null) {
				serviceEle.addElement("port").setText(config.getServicePort());
			} else {
				portEle.setText(config.getServicePort());
			}

			Element history = root.element("history");
			if (history == null) {
				history = root.addElement("history");
			}

			boolean isExists = false;
			for (int i = 0; i < history.elements().size(); i++) {
				Element tmpEl = (Element) history.elements().get(i);
				if (tmpEl.element("ip").getText().equals(config.getServiceAdd())) {
					isExists = true;
					break;
				}
			}
			if (!isExists) {
				Element tmpEle = history.addElement("address");
				tmpEle.addElement("ip").setText(config.getServiceAdd());
				tmpEle.addElement("port").setText(config.getServicePort());
			}

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(new FileOutputStream(configFileName), format);
			xmlWriter.write(src);
			xmlWriter.close();
			return true;
		} catch (Exception e) {
			logger.debug("保存参数出错");
		}
		return false;
	}

	public static boolean updateMailFile(String mailto, String emailsend, String compileusername) {
		try {
			String mails = mailto + ";" + emailsend;
			String[] mailarr = mails.split(";");
			String configFileName = new File("").getAbsolutePath() + File.separator + "config/email.xml";
			File configFile = new File(configFileName);
			Document src = null;
			if (configFile.exists()) {
				SAXReader saxReader = new SAXReader();
				src = saxReader.read(configFile);
			}
			if (src == null) {
				src = DocumentHelper.createDocument();
			}
			Element root = src.getRootElement();
			Element mailsele = null;
			Element sendmailsele = null;
			Element tomailsele = null;
			Element compileusernamesele = null;
			if (root != null) {
				mailsele = root.element("mails");
				sendmailsele = root.element("sendmail");
				if (!emailsend.equals(""))
					sendmailsele.setText(emailsend);
				tomailsele = root.element("tomail");
				if (!mailto.equals(""))
					tomailsele.setText(mailto);
				compileusernamesele = root.element("compileusername");
				if (compileusernamesele == null)
					compileusernamesele = root.addElement("compileusername");
				if (!compileusername.equals(""))
					compileusernamesele.setText(compileusername);
			}
			if (root == null) {
				root = src.addElement("root");
				mailsele = root.addElement("mails");
				sendmailsele = root.addElement("sendmail");
				sendmailsele.setText(emailsend);
				tomailsele = root.addElement("tomail");
				tomailsele.setText(mailto);
				compileusernamesele = root.addElement("compileusername");
				compileusernamesele.setText(compileusername);
			}
			if (!mailto.equals("") && !emailsend.equals("")) {
				List list = mailsele.elements("mail");
				for (int i = 0; i < mailarr.length; i++) {
					String mail = mailarr[i];
					boolean existflag = false;
					if (list != null && list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							Element ele = (Element) list.get(j);
							String email = ele.elementText("email");
							if (email.equals(mail)) {
								existflag = true;
								break;
							}
						}
					}
					if (!existflag) {
						Element ele = mailsele.addElement("mail");
						ele.addElement("email").setText(mail);
						ele.addElement("name").setText("");
					}
				}
			}

			// }
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(new FileOutputStream(configFileName), format);
			xmlWriter.write(src);
			xmlWriter.close();
			return true;
		} catch (Exception e) {
			logger.debug("保存参数出错");
		}
		return false;
	}

	public static boolean saveMailFile(List maillist, String emailsend, String emailto, String username) {
		try {
			String configFileName = new File("").getAbsolutePath() + File.separator + "config/email.xml";
			File configFile = new File(configFileName);
			Document src = null;
			if (configFile.exists()) {
				SAXReader saxReader = new SAXReader();
				src = saxReader.read(configFile);
			}
			// if (src == null) {
			src = DocumentHelper.createDocument();
			// }
			Element mailsele = null;
			Element root = src.getRootElement();
			if (root == null) {
				root = src.addElement("root");
				mailsele = root.addElement("mails");
				Element sendmailsele = root.addElement("sendmail");
				sendmailsele.setText(emailsend);
				Element tomailsele = root.addElement("tomail");
				tomailsele.setText(emailto);
				Element compileusernameele = root.addElement("compileusername");
				compileusernameele.setText(username);
			}
			for (int i = 0; i < maillist.size(); i++) {
				Object[] obj = (Object[]) maillist.get(i);
				Element ele = mailsele.addElement("mail");
				ele.addElement("email").setText((String) obj[1]);
				ele.addElement("name").setText((String) obj[2]);
			}

			// }
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(new FileOutputStream(configFileName), format);
			xmlWriter.write(src);
			xmlWriter.close();
			return true;
		} catch (Exception e) {
			logger.debug("保存参数出错");
		}
		return false;
	}

	public static Document readFile(String fileName) {
		// String connectPara = "";
		Document doc = null;
		try {
			File workTableFile = new File(fileName);
			if (!workTableFile.exists())
				return doc;
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(workTableFile);
			return doc;
		} catch (Exception e) {
			logger.debug("读取数据表文件出错出错");
		}
		return doc;
	}
}
