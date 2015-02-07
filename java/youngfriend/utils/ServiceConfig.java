/*
 * ServiceConfig.java
 *
 * Created on 2009年3月4日, 下午2:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package youngfriend.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 
 * @author Administrator
 */
public class ServiceConfig {

	private static Logger logger = LogManager.getLogger(ServiceConfig.class.getName());
	private static String configFileName = new File("").getAbsolutePath() + File.separator + "config" + File.separator + "config.xml";

	private String serviceAdd = "127.0.0.1";
	private String servicePort = "12345";

	private List addressList = new ArrayList();

	private Document doc = null;

	/** Creates a new instance of ServiceConfig */
	public ServiceConfig() {
		doc = ConfigIO.readConfigFile("config.xml");
	}

	public void getServiceConnectPara() {
		try {
			if (doc == null)
				doc = ConfigIO.readConfigFile("config.xml");
			if (doc != null) {
				Element serviceEle = (Element) doc.selectSingleNode("/config/service");
				this.serviceAdd = serviceEle.element("address") == null ? "127.0.0.1" : serviceEle.elementText("address");
				this.servicePort = serviceEle.element("port") == null ? "12345" : serviceEle.elementText("port");

				Element history = (Element) doc.selectSingleNode("/config/history");
				if (history != null) {
					for (int i = 0; i < history.elements().size(); i++) {
						Element element = (Element) history.elements().get(i);
						addressList.add(element.element("ip").getText());
					}
				}
			}
		} catch (Exception e) {
			logger.debug("获取服务连接参数出错");
		}
	}

	public String getServiceAdd() {
		return serviceAdd;
	}

	public void setServiceAdd(String serviceAdd) {
		this.serviceAdd = serviceAdd;
	}

	public String getServicePort() {
		return servicePort;
	}

	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}

	/**
	 * @return the addressList
	 */
	public List getAddressList() {
		return addressList;
	}

	/**
	 * @param addressList
	 *            the addressList to set
	 */
	public void setAddressList(List addressList) {
		this.addressList = addressList;
	}

}
