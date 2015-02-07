package youngfriend.coms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.common.util.StringUtils;
import youngfriend.utils.ComEum;
import youngfriend.utils.CompUtils;

public class TNewPanel extends JPanel implements IStylePanel {
	private static final long serialVersionUID = 1L;
	private DefaultCom defaultCom = null;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	public static final int HEADER_HIGHT = 50;

	@Override
	public void init(Element propE) {
		defaultCom.init(propE);
		this.setPropValue("Left", "20");
		this.setPropValue("Top", "20");
	}

	private void setQueryTitle(String querytitle, Font font, Color color) {
		lblNewLabel.setText(querytitle);
		lblNewLabel.setFont(font);
		lblNewLabel.setForeground(color);
	}

	/**
	 * Create the panel.
	 */
	public TNewPanel() {
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setPreferredSize(new Dimension(0, HEADER_HIGHT));
		add(panel, BorderLayout.NORTH);

		lblNewLabel = new JLabel("\u6837\u5F0F");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		panel.add(lblNewLabel);
		panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		// ≥ı ºªØ  Ù–‘dto
		defaultCom = new DefaultCom(this);
		// uiprop
		defaultCom.addUIProp(Arrays.asList("QueryTitle"));
	}

	@Override
	public void upateUIByProps() {
		int Width = 0, Height = 0;
		String temp = this.getPropValue("Width");
		if (StringUtils.isNumberString(temp)) {
			Width = Integer.parseInt(temp);
		}

		temp = this.getPropValue("Height");
		if (StringUtils.isNumberString(temp)) {
			Height = Integer.parseInt(temp);
		}

		this.setBounds(20, 20, Width, Height);
		Object[] styles = CompUtils.getFontProps(this.getPropValue("TitleFont"));
		setQueryTitle(this.getPropValue("QueryTitle"), (Font) styles[0], (Color) styles[1]);
		JPanel parent = (JPanel) this.getParent();
		parent.setPreferredSize(new Dimension(this.getWidth() + 50, this.getHeight() + 50));
		parent.paintComponents(parent.getGraphics());
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
	public List<IStyleCom> getChilds() {
		List<IStyleCom> childs = new ArrayList<IStyleCom>();
		Component[] coms = panel_1.getComponents();
		for (Component c : coms) {
			if (c instanceof IStyleCom) {
				childs.add((IStyleCom) c);
			}
		}
		return childs;
	}

	@Override
	public void addChild(Component child) {
		panel_1.add(child);
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
		this.requestFocus();
		defaultCom.setSelect(flag);
	}

	@Override
	public void paint(Graphics g) {
		Image img = this.createImage(this.getWidth(), this.getHeight());
		Graphics imgGrap = img.getGraphics();
		super.paint(imgGrap);
		if (isSelect()) {
			defaultCom.paintPoint(imgGrap);
		}
		g.drawImage(img, 0, 0, null);
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
	public boolean forbid(ComEum type) {
		return false;
	}

	public void clear() {
		panel_1.removeAll();
	}

	@Override
	public Element cover2Ele(String name) {
		return defaultCom.cover2Ele(name);
	}

	public Element getPageEle(String name) {
		Element page = DocumentHelper.createElement(name);
		List<IStyleCom> children = getChilds();
		if (!children.isEmpty()) {
			for (IStyleCom child : children) {
				page.add(child.cover2Ele(null));
			}
		}
		return page;
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
