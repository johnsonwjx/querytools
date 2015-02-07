package youngfriend.coms;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JCheckBox;

import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.utils.ComEum;

public class TNewCheckBox extends JCheckBox implements IStyleCom {
	private static final long serialVersionUID = 1L;
	private DefaultCom defaultCom = null;

	public TNewCheckBox() {
		defaultCom = new DefaultCom(this);
		defaultCom.addUIProp(Arrays.asList("Checked"));
	}

	@Override
	public void init(Element propE) {
		defaultCom.init(propE);
	}

	@Override
	public void upateUIByProps() {
		this.setText(this.getPropValue("Caption"));
		this.setSelected("true".equalsIgnoreCase(this.getPropValue("Checked")));
		defaultCom.upateUIByProps();
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
		return defaultCom.isSelect();
	}

	@Override
	public void setSelect(boolean flag) {
		defaultCom.setSelect(flag);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (isSelect()) {
			defaultCom.paintPoint(g);
		}
	}

	@Override
	public void updatePropsByUI() {
		defaultCom.updatePropsByUI();
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
		return defaultCom.cover2Ele(name);
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