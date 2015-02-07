package youngfriend.coms;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.utils.ComEum;
import youngfriend.utils.CompUtils;

public class TNewGroupBox extends JPanel implements IStylePanel {
	private static final long serialVersionUID = 1L;
	private DefaultCom defaultCom = null;
	private static final List<ComEum> forbidTypes = new ArrayList<ComEum>();
	static {
		forbidTypes.add(ComEum.TNewTreeView);
		forbidTypes.add(ComEum.TNewGrid);
		forbidTypes.add(ComEum.TNewCondiPanel);
		forbidTypes.add(ComEum.TNewChart);
		forbidTypes.add(ComEum.TNewGroupBox);
	}

	public TNewGroupBox() {
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		defaultCom = new DefaultCom(this);
		setLayout(null);
	}

	@Override
	public void init(Element propE) {
		defaultCom.init(propE);
		if (propE.getParent().element("childs") != null && propE.getParent().element("childs").hasContent()) {
			List<Element> cEles = propE.getParent().element("childs").elements();
			for (Element cEle : cEles) {
				CompUtils.createCom(cEle.attribute("classname").getText(), this, false, cEle);
			}
		}
	}

	@Override
	public Element cover2Ele(String name) {
		Element ele = defaultCom.cover2Ele(name);
		Element childs = ele.addElement("childs");
		List<IStyleCom> children = getChilds();
		if (!children.isEmpty()) {
			for (IStyleCom child : children) {
				childs.add(child.cover2Ele(null));
			}
		}
		return ele;
	}

	@Override
	public void upateUIByProps() {
		TitledBorder border = (TitledBorder) getBorder();
		border.setTitle(this.getPropValue("Caption"));
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
	public List<IStyleCom> getChilds() {
		List<IStyleCom> childs = new ArrayList<IStyleCom>();
		Component[] coms = this.getComponents();
		for (Component c : coms) {
			if (c instanceof IStyleCom) {
				childs.add((IStyleCom) c);
			}
		}
		return childs;
	}

	@Override
	public void addChild(Component child) {
		this.add(child);
	}

	@Override
	public boolean forbid(ComEum type) {
		return forbidTypes.contains(type);
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