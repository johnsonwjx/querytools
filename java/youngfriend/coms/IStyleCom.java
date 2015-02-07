package youngfriend.coms;

import java.util.Map;

import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.utils.ComEum;

public interface IStyleCom {
	void upateUIByProps();

	void updatePropsByUI();

	String getPropValue(String key);

	PropDto getProp(String key);

	void setPropValue(String key, String value);

	void init(Element propE);

	boolean hasPro(String key);

	ComEum getType();

	boolean isSelect();

	void setSelect(boolean flag);

	Map<String, PropDto> listProp();

	Element cover2Ele(String name);

	boolean isUIProp(String name);

	void setParentPnl(IStylePanel parent);

	IStylePanel getParentPnl();
}