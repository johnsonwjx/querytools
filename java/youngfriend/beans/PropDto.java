package youngfriend.beans;

import java.util.LinkedHashMap;
import java.util.Map;

import youngfriend.coms.IStyleCom;

public class PropDto {
	private String propname;
	private String propcname;
	private String renderertype;
	private String valuetype;
	private String editclass;
	private String value;
	private Object valueObj;
	private String relationPros;
	private Map<String, String> enums = new LinkedHashMap<String, String>();
	private IStyleCom com;

	public PropDto() {
	}

	public IStyleCom getCom() {
		return com;
	}

	public void setCom(IStyleCom com) {
		this.com = com;
	}

	public PropDto(IStyleCom com) {
		this.com = com;
	}

	public void setEnum(String key, String value) {
		enums.put(key, value);
	}

	public Map<String, String> getEnums() {
		return enums;
	}

	public String getRelationPros() {
		return relationPros;
	}

	public void setRelationPros(String relationPros) {
		this.relationPros = relationPros;
	}

	public String getPropname() {
		return propname;
	}

	public void setPropname(String propname) {
		this.propname = propname;
	}

	public String getPropcname() {
		return propcname;
	}

	public void setPropcname(String propcname) {
		this.propcname = propcname;
	}

	public String getRenderertype() {
		return renderertype;
	}

	public void setRenderertype(String renderertype) {
		this.renderertype = renderertype;
	}

	public String getValuetype() {
		return valuetype;
	}

	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}

	public String getEditclass() {
		return editclass;
	}

	public void setEditclass(String editclass) {
		this.editclass = editclass;
	}

	public String getValue() {
		if (value == null) {
			value = "";
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Object getValueObj() {
		return valueObj;
	}

	public void setValueObj(Object valueObj) {
		this.valueObj = valueObj;
	}

	@Override
	public String toString() {
		return this.getPropcname();
	}
}
