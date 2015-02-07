package youngfriend.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.BackedList;

import youngfriend.beans.XMLDto;
import youngfriend.common.util.encoding.Base64;

public class CommonUtils {
	private static final Logger logger = LogManager.getLogger(CommonUtils.class.getName());

	public static List<XMLDto> coverEles(List<Element> eles, String[] pNames, String[] attrs, //
			String[] toStringName, Map<String, String> switchProps, Map<String, Object> defaultPros, Map<String, String> xpaths) throws Exception {
		List<String> toStringPnames = Arrays.asList(toStringName);
		List<XMLDto> dtos = new ArrayList<XMLDto>();
		List<String> propNames = null;
		if (pNames != null && pNames.length > 0) {
			propNames = Arrays.asList(pNames);
		}
		List<String> attrPros = null;
		if (attrs != null && attrs.length > 0) {
			attrPros = Arrays.asList(attrs);
		}
		for (Element ele : eles) {
			XMLDto dto = coverEle(ele, propNames, toStringPnames, defaultPros, switchProps, attrPros, xpaths);
			if (dto != null && !dto.getProps().isEmpty()) {
				dtos.add(dto);
			}

		}
		return dtos;
	}

	public static XMLDto coverEle(Element ele, List<String> propNames, List<String> toStringPnames, Map<String, Object> defaultPros, Map<String, String> switchProps, List<String> attrPros, Map<String, String> xpaths) {
		XMLDto dto = new XMLDto(toStringPnames);
		if (defaultPros != null) {
			dto.setDefaults(defaultPros);
		}

		if (switchProps != null) {
			dto.setSwitchProps(switchProps);
		}
		if (ele == null || !ele.hasContent()) {
			return dto;
		}
		if (propNames != null) {
			dto.initProps(ele, propNames);
		}
		if (attrPros != null) {
			dto.initAttrProps(ele, attrPros);
		}
		if (xpaths != null) {
			dto.initXpathProps(ele, xpaths);
		}
		return dto;
	}

	public static boolean isStrEmpty(String str) {
		if (str == null) {
			return true;
		}
		if (str.length() <= 0) {
			return true;
		}
		return false;
	}

	public static boolean isStrEmpty(String str, boolean trim) {
		if (str == null) {
			return true;
		}
		if (trim) {
			if (str.trim().length() <= 0) {
				return true;
			}
		} else {
			if (str.length() <= 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNumberString(String str) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static void sortXmlDtos(List<XMLDto> list, final String sortName) {
		Collections.sort(list, new Comparator<XMLDto>() {
			@Override
			public int compare(XMLDto o1, XMLDto o2) {
				String sort1 = o1.getValue(sortName);
				String sort2 = o2.getValue(sortName);

				if (!isNumberString(sort1) && !isNumberString(sort2)) {
					return 0;
				} else {
					if (!isNumberString(sort1)) {
						return 1;
					}
					if (!isNumberString(sort2)) {
						return -1;
					}
					int s1 = Integer.parseInt(sort1);
					int s2 = Integer.parseInt(sort2);
					return s1 - s2;
				}
			}
		});
	}

	public static String coverNull(String string) {
		return string == null ? "" : string;
	}

	public static boolean isMac() {
		Properties props = System.getProperties(); // 获得系统属性集
		String osName = props.getProperty("os.name"); // 操作系统名称
		return osName.indexOf("Mac") >= 0;
	}

	public static String getIp() {
		String ip = "";
		Properties props = System.getProperties(); // 获得系统属性集
		String osName = props.getProperty("os.name"); // 操作系统名称
		if (osName.indexOf("Windows") >= 0 || osName.indexOf("Mac") >= 0) {
			ip = getWindowsIP();
		} else {
			ip = getHostIPInLinux();
		}
		return ip;
	}

	private static String getHostIPInLinux() {

		String ret = "";
		Enumeration allNetInterfaces = null;
		try {

			allNetInterfaces = NetworkInterface.getNetworkInterfaces();// 获取此机器上的所有接口
			InetAddress inet = null;

			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				String addressName = netInterface.getName();
				if ("eth1".equals(addressName)) {
					Enumeration addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						inet = (InetAddress) addresses.nextElement();
						if (inet != null && inet instanceof Inet4Address) {
							String ip = inet.getHostAddress();
							ret = ip;
						}
					}
				}

			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 获取WindowsIP
	 * 
	 */
	private static String getWindowsIP() {
		String IP = "";
		try {
			InetAddress net = InetAddress.getLocalHost();
			IP = net.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return IP;
	}

	/**
	 * 不区分大小写 找xmldto
	 * 
	 * @param dtos
	 * @param pName
	 * @param value
	 * @return
	 */
	public static XMLDto getXmlDto(Collection<XMLDto> dtos, String pName, String value) {
		if (dtos.isEmpty() || isStrEmpty(pName) || value == null) {
			return null;
		}
		for (XMLDto temp : dtos) {
			if (value.equalsIgnoreCase(temp.getValue(pName))) {
				return temp;
			}
		}
		return null;
	}

	public static String getModuleXML(String type, XMLDto module) {
		if (module == null) {
			return null;
		}
		Element root = DocumentHelper.createElement("event");
		Document doc = DocumentHelper.createDocument(root);
		root.addElement("type").setText(type);
		root.addElement("moduleid").setText(module.getValue("id"));
		root.addElement("inparam").setText(module.getValue("inparam"));
		root.addElement("outparam").setText(module.getValue("outparam"));
		String result = doc.asXML();
		return result.substring(result.indexOf("<event>")).trim();
	}

	public static <K, V> K getMapKey(Map<K, V> map, V value) {
		if (value == null || map.isEmpty()) {
			return null;
		}
		for (K k : map.keySet()) {
			if (map.get(k).equals(value)) {
				return k;
			}
		}
		return null;
	}

	/**
	 * 把参数字符串转换成对应key-value的Map
	 * 
	 * @param paramStr
	 *            参数字符串,格式如:"formQmid=BILLID;openmode=1;version=0"
	 * @return 参数Map
	 */
	public static HashMap<String, String> paramStrToMap(String paramStr) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		String[] params = paramStr.split(";");
		for (String param : params) {
			if (!"".equals(param.trim())) {
				String[] keyValue = param.split("=", 2);// 只分割一次,以防value中含有base64编码后的"="号
				if (keyValue.length < 2) {
					paramMap.put(keyValue[0], "");
				} else {
					paramMap.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return paramMap;
	}

	public static String compareStr(String s1, String s2, int length) {
		if (s1.length() <= length || s2.length() <= length) {
			return "";
		}
		String temp1 = "";
		String temp2 = "";
		while (s1.length() >= length && s2.length() >= length) {
			temp1 = s1.substring(0, 0 + length);
			s1 = s1.substring(length);
			temp2 = s2.substring(0, 0 + length);
			s2 = s2.substring(length);
			if (!temp1.equals(temp2)) {
				System.out.println(temp1);
				System.out.println(temp2);
				return null;
			}
		}
		System.out.println(temp1);
		System.out.println(temp2);
		return null;
	}

	public static String getHostName() {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostName().toString(); // 获取本机计算
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}

	}

	public static XMLDto cloneDto(XMLDto dto) {
		XMLDto newObj = new XMLDto(dto.getToStringProNames());
		Map<String, Object> pMap = dto.getProps();
		if (!pMap.isEmpty()) {
			for (String key : pMap.keySet()) {
				newObj.setValue(key, pMap.get(key));
			}
		}
		return newObj;
	}

	public static List<XMLDto> getTableByService(String service) {
		try {
			if (CommonUtils.isStrEmpty(service, true)) {
				return null;
			}
			String xml = InvokerServiceUtils.getStrutByServiceName(service.trim());
			if (CommonUtils.isStrEmpty(xml)) {
				return null;
			}
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			List<Element> tableEles = root.elements("table");
			if (tableEles.size() <= 0) {
				return null;
			}
			List<XMLDto> tables = new ArrayList<XMLDto>();
			List<String> toString = Arrays.asList("cname", "name");
			for (Element tableEle : tableEles) {
				XMLDto table = new XMLDto(toString);
				String name = tableEle.attributeValue("name");
				String cname = tableEle.attributeValue("cname");
				List<Element> fieldEles = tableEle.elements();
				table.setValue("name", name);
				table.setValue("cname", cname);
				table.setValue("fields", fieldEles);
				tables.add(table);
			}
			return tables;
		} catch (Exception e) {
			GUIUtils.showMsg(null, "获取表格数据失败");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	public static List<XMLDto> getFieldsByServiceTable(XMLDto table) {
		try {
			if (table == null) {
				return null;
			}
			List<Element> fieldEles = table.getObject(BackedList.class, "fields");
			if (fieldEles == null || fieldEles.isEmpty()) {
				return null;
			}
			List<XMLDto> fields = CommonUtils.coverEles(fieldEles, new String[] { "cname", "name", "fieldType" }, null, new String[] { "cname", "name" }, null, null, null);
			return fields;
		} catch (Exception e) {
			GUIUtils.showMsg(null, "获取字段失败");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static String createNodeName(String name, boolean end, boolean upperCase, boolean empty) {
		if (name == null || CommonUtils.isStrEmpty(name, true)) {
			return null;
		}
		name = upperCase ? name.toUpperCase() : name.toLowerCase();
		if (empty) {
			return "<" + name + "/>";
		}
		return "<" + (end ? "/" : "") + name + ">";
	}

	private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	private static int[] toInt = new int[128];

	static {
		for (int i = 0; i < ALPHABET.length; i++) {
			toInt[ALPHABET[i]] = i;
		}
	}

	/**
	 * Translates the specified byte array into Base64 string.
	 * 
	 * @param buf
	 *            the byte array (not null)
	 * @return the translated Base64 string (not null)
	 */
	public static String base64Encode(byte[] buf) {
		int size = buf.length;
		char[] ar = new char[((size + 2) / 3) * 4];
		int a = 0;
		int i = 0;
		while (i < size) {
			byte b0 = buf[i++];
			byte b1 = (i < size) ? buf[i++] : 0;
			byte b2 = (i < size) ? buf[i++] : 0;

			int mask = 0x3F;
			ar[a++] = ALPHABET[(b0 >> 2) & mask];
			ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
			ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
			ar[a++] = ALPHABET[b2 & mask];
		}
		switch (size % 3) {
		case 1:
			ar[--a] = '=';
		case 2:
			ar[--a] = '=';
		}
		return new String(ar);
	}

	/**
	 * Translates the specified Base64 string into a byte array.
	 * 
	 * @param s
	 *            the Base64 string (not null)
	 * @return the byte array (not null)
	 */
	public static byte[] base64Dcode(String s) {
		try {
			int delta = s.endsWith("==") ? 2 : s.endsWith("=") ? 1 : 0;
			byte[] buffer = new byte[s.length() * 3 / 4 - delta];
			int mask = 0xFF;
			int index = 0;
			for (int i = 0; i < s.length(); i += 4) {
				int c0 = toInt[s.charAt(i)];
				int c1 = toInt[s.charAt(i + 1)];
				buffer[index++] = (byte) (((c0 << 2) | (c1 >> 4)) & mask);
				if (index >= buffer.length) {
					return buffer;
				}
				int c2 = toInt[s.charAt(i + 2)];
				buffer[index++] = (byte) (((c1 << 4) | (c2 >> 2)) & mask);
				if (index >= buffer.length) {
					return buffer;
				}
				int c3 = toInt[s.charAt(i + 3)];
				buffer[index++] = (byte) (((c2 << 6) | c3) & mask);
			}
			return buffer;
		} catch (StringIndexOutOfBoundsException e) {
			return Base64.decode(s);
		}

	}

	public static String getArrValue(String[] objs, int index, boolean null2Empty) {
		if (index < 0 || index > objs.length - 1) {
			if (null2Empty) {
				return "";
			} else {
				return null;
			}
		}
		String value = objs[index];
		if (value == null && null2Empty) {
			return "";
		}
		return value;
	}

	public static String[] getKeyVal(String item, String split) {
		if (isStrEmpty(item, true)) {
			return null;
		}
		int index = item.indexOf(split);
		if (index < 1) {
			return null;
		}
		String[] result = new String[2];
		result[0] = item.substring(0, index);
		result[1] = item.substring(index + 1);
		return result;
	}
}
