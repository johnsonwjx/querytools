package youngfriend.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.utils.CommonUtils;

public class XMLDto {

	private Map<String, Object> props = new LinkedHashMap<String, Object>();
	private List<String> toStringProNames = new ArrayList<String>();
	private Map<String, String> switchProps = new HashMap<String, String>();

	public XMLDto(String toStringName) {
		toStringProNames.add(toStringName);
	}

	public XMLDto(String key, Object value) {
		toStringProNames.add("key");
		props.put("key", key);
		props.put("value", value);
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setSwitchProps(Map<String, String> switchProps) {
		this.switchProps = switchProps;
	}

	public List<String> getToStringProNames() {
		return toStringProNames;
	}

	public XMLDto(List<String> toStringPros) {
		if (toStringPros == null || toStringPros.size() <= 0) {
			throw new IllegalArgumentException("toString²ÎÊý´íÎó");
		}
		this.toStringProNames = toStringPros;
	}

	public XMLDto(List<String> toStringPros, Map<String, Object> defaultPros) {
		this(toStringPros);
		this.props.putAll(defaultPros);
	}

	public void setDefaults(Map<String, Object> defaultPros) {
		this.props.putAll(defaultPros);
	}

	public void initProps(Element ele, List<String> pNames) {
		for (String pName : pNames) {
			String value = ele.elementText(pName);
			if (value == null) {
				throw new IllegalArgumentException(pName);
			}
			if (switchProps.containsKey(pName)) {
				pName = switchProps.get(pName);
			}
			props.put(pName, value);
		}
	}

	public void initXpathProps(Element ele, Map<String, String> xpaths) {
		for (String prop : xpaths.keySet()) {
			Element valueEle = (Element) ele.selectSingleNode(xpaths.get(prop));
			String value = "";
			if (valueEle != null) {
				value = valueEle.getText();
			}
			props.put(prop, value);
		}
	}

	public void initAttrProps(Element ele, List<String> atts) {
		for (String pName : atts) {
			String value = ele.attributeValue(pName);
			if (value == null) {
				throw new IllegalArgumentException(pName);
			}
			if (switchProps.containsKey(pName)) {
				pName = switchProps.get(pName);
			}
			props.put(pName, value);
		}

	}

	public Element cover2Ele(String name) throws Exception {
		Element ele = DocumentHelper.createElement(name);
		for (String pName : props.keySet()) {
			ele.addElement(pName).setText(CommonUtils.coverNull((String) props.get(pName)));
		}
		return ele;
	}

	public void setValue(String pName, Object value) {
		if (switchProps.containsKey(pName)) {
			pName = switchProps.get(pName);
			System.out.println(pName);
		}
		props.put(pName, value);
	}

	public void remove(Object key) {
		props.remove(key);
	}

	public String getValue(String pName) {
		Object obj = props.get(pName);
		if (obj instanceof String) {
			return (String) obj;
		}
		return null;
	}

	public <T> T getObject(Class<T> clazz, String pName) {
		Object obj = props.get(pName);
		if (clazz.isAssignableFrom(obj.getClass())) {
			return (T) obj;
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (toStringProNames.size() > 0) {
			for (String prop : toStringProNames) {
				String value = getValue(prop);
				if (value == null) {
					continue;
				}
				sb.append(value).append("->");
			}
			if (sb.length() > 1) {
				sb.delete(sb.length() - 2, sb.length());
			}
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		String key = this.toString();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLDto other = (XMLDto) obj;
		if (toString() == null) {
			if (other.toString() != null)
				return false;
		} else if (!toString().equals(other.toString()))
			return false;
		return true;
	}
}
