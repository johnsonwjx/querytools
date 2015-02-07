package youngfriend.coms;

import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.utils.ComEum;

public class TNewColumn implements IStyleCom {
	private DefaultCom defaultCom = null;
	private TNewGrid grid;

	public TNewColumn(TNewGrid grid) {
		this.grid = grid;
		defaultCom = new DefaultCom(ComEum.TNewColumn);
	}

	@Override
	public void init(Element propE) {
		defaultCom.init(propE);
	}

	@Override
	public void upateUIByProps() {
		grid.upateUIByProps();
	}

	@Override
	public PropDto getProp(String key) {
		return defaultCom.getProp(key);
	}

	@Override
	public boolean hasPro(String key) {
		return defaultCom.hasPro(key);
	}

	@Override
	public String getPropValue(String key) {
		return defaultCom.getPropValue(key);
	}

	@Override
	public void setPropValue(String key, String value) {
		defaultCom.setPropValue(key, value);
	}

	@Override
	public ComEum getType() {
		return defaultCom.getType();
	}

	@Override
	public boolean isSelect() {
		// 不用
		return false;
	}

	@Override
	public void setSelect(boolean flag) {
	}

	@Override
	public void updatePropsByUI() {
		// 不用有鼠标事件才用
	}

	@Override
	public String toString() {
		return defaultCom.toString();
	}

	@Override
	public Map<String, PropDto> listProp() {
		return defaultCom.listProp();
	}

	@Override
	public Element cover2Ele(String name) {
		Element ele = DocumentHelper.createElement(name);
		for (PropDto pdo : listProp().values()) {
			ele.addElement(pdo.getPropname()).setText(pdo.getValue());
		}
		return ele;
	}

	@Override
	public boolean isUIProp(String name) {
		return defaultCom.isUIProp(name);
	}

	@Override
	public void setParentPnl(IStylePanel parent) {
		defaultCom.setParentPnl(parent);
	}

	@Override
	public IStylePanel getParentPnl() {
		return defaultCom.getParentPnl();
	}

}
