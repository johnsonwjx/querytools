package youngfriend.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.Validate;
import youngfriend.beans.XMLDto;
import youngfriend.common.util.MathUtils;
import youngfriend.coms.BaseCombobox;
import youngfriend.coms.DefaultCom;
import youngfriend.coms.IStyleCom;
import youngfriend.coms.IStylePanel;
import youngfriend.coms.TNewButton;
import youngfriend.coms.TNewChart;
import youngfriend.coms.TNewCheckBox;
import youngfriend.coms.TNewCondiPanel;
import youngfriend.coms.TNewEdit;
import youngfriend.coms.TNewGrid;
import youngfriend.coms.TNewGroupBox;
import youngfriend.coms.TNewGroupPanel;
import youngfriend.coms.TNewLabel;
import youngfriend.coms.TNewMemo;
import youngfriend.coms.TNewPanel;
import youngfriend.coms.TNewRadioBox;
import youngfriend.coms.TNewTreeView;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.gui.SaveNameEditor;
import youngfriend.main.MainFrame;
import youngfriend.main.StyleMainPnl;

public class CompUtils {
	private static StyleMainPnl stylemainpnl;
	private static TNewPanel win;
	private static XMLDto style;
	private static final Logger logger = LogManager.getLogger(CompUtils.class);
	private static List<IStyleCom> copys = new ArrayList<IStyleCom>();
	private static String copystyleid = "";
	public static IStyleCom firstSelect = null;
	public static Timer timer = null;
	public static boolean flag = false;
	private static List<XMLDto> fields = new ArrayList<XMLDto>();
	private static final List<XMLDto> codeTables = InvokerServiceUtils.getCodeTables();

	private static ObjectSelectPnl<XMLDto> fieldsPnl = null;
	static {
		fieldsPnl = new ObjectSelectPnl<XMLDto>(fields);
	}

	public static List<XMLDto> getCodeTables() {
		return codeTables;
	}

	public static ObjectSelectPnl<XMLDto> getCodeTablePnl(XMLDto value) {
		ObjectSelectPnl<XMLDto> codeTablePnl = new ObjectSelectPnl<XMLDto>(codeTables);
		codeTablePnl.setValue(value);
		return codeTablePnl;
	}

	private static boolean isSaveParent(final List<IStyleCom> coms) {
		if (coms == null || coms.size() < 2) {
			return false;
		}
		IStylePanel firstPnl = coms.get(0).getParentPnl();
		for (int i = 1; i < coms.size(); i++) {
			if (!coms.get(i).getParentPnl().equals(firstPnl)) {
				return false;
			}
		}
		return true;
	}

	public static ObjectSelectPnl<XMLDto> getFieldsPnl() {
		if (fieldsPnl != null) {
			fieldsPnl.setValue(null);
		}
		return fieldsPnl;
	}

	public static void copy(List<IStyleCom> temp) {
		copys = temp;
		copystyleid = style.getValue("id");
	}

	public static boolean copysEmpty() {
		return copys.isEmpty();
	}

	public static void paste(IStylePanel parent, Point point) {
		if (copys.isEmpty()) {
			return;
		}
		Point p = SwingUtilities.convertPoint(CompUtils.getWin(), point, (Component) parent);
		List<IStyleCom> newComs = new ArrayList<IStyleCom>();
		int x = p.x;
		int y = p.y;
		if (copys.size() == 1) {
			IStyleCom newCom = pasteCom(parent, copys.get(0), x, y);
			newComs.add(newCom);
		} else {
			Collections.sort(copys, new Comparator<IStyleCom>() {

				@Override
				public int compare(IStyleCom o1, IStyleCom o2) {
					String temp = o1.getPropValue("left");
					if (CommonUtils.isStrEmpty(temp) || !CommonUtils.isNumberString(temp)) {
						return -1;
					}
					int l1 = Integer.parseInt(temp);
					temp = o2.getPropValue("left");
					if (CommonUtils.isStrEmpty(temp) || !CommonUtils.isNumberString(temp)) {
						return 1;
					}
					int l2 = Integer.parseInt(temp);
					return l1 - l2;
				}
			});
			boolean first = true;
			int diffX = 0, diffY = 0;
			for (IStyleCom com : copys) {
				String temp = com.getPropValue("left");
				int ol = Integer.parseInt(temp);
				temp = com.getPropValue("top");
				int ot = Integer.parseInt(temp);
				IStyleCom newCom = pasteCom(parent, com, x, y);
				if (first) {
					first = false;
					diffX = Integer.parseInt(newCom.getPropValue("left")) - ol;
					diffY = Integer.parseInt(newCom.getPropValue("top")) - ot;
				} else {
					newCom.setPropValue("Left", (ol + diffX) + "");
					newCom.setPropValue("Top", (ot + diffY) + "");
				}
				if (newCom != null) {
					newComs.add(newCom);
				}
			}

		}
		CompUtils.getStyleMain().rebuildComTree();
		clearSelect();
		for (IStyleCom newCom : newComs) {
			newCom.setSelect(true);
		}
		CompUtils.updateUI(win);
	}

	public static IStyleCom pasteCom(IStylePanel parent, IStyleCom com, int x, int y) {
		String name = com.getPropValue("Name");
		boolean changeName = true;
		if (!copystyleid.equals(style.getValue("id"))) {
			// 复制在不同样式
			if (hasSaveNameCom(parent, null, name)) {
				// 判断有没有此控件名
				GUIUtils.showMsg(MainFrame.getInstance(), "已存在相同名称控件:" + com + "，该控件名称将被修改");
			} else {
				changeName = false;
			}
		}
		IStyleCom newCom = CompUtils.createCom(x, y, parent, com.getType().toString(), false);
		String left = newCom.getPropValue("Left");
		String top = newCom.getPropValue("Top");
		CompUtils.copyProps(com, newCom);
		newCom.getPropValue("Name");
		newCom.setPropValue("Left", left);
		newCom.setPropValue("Top", top);
		if (changeName && name.indexOf("ok") < 0 && name.indexOf("show") < 0) {
			name = CompUtils.getComName();
			newCom.setPropValue("Name", name);
		}
		return newCom;
	}

	public static void copyProps(IStyleCom com, IStyleCom newCom) {
		Map<String, PropDto> propMap = newCom.listProp();
		for (String key : propMap.keySet()) {
			newCom.setPropValue(key, com.getPropValue(key));
		}
		if (com.getType() == ComEum.TNewGrid) {
			TNewGrid grid = (TNewGrid) com;
			TNewGrid newgrid = (TNewGrid) newCom;
			newgrid.setColumnMap(grid.getColumnMap());
		} else if (com instanceof IStylePanel) {
			IStylePanel pnl = (IStylePanel) com;
			IStylePanel newPnl = (IStylePanel) newCom;
			for (IStyleCom c : pnl.getChilds()) {
				IStyleCom newChild = pasteCom(newPnl, c, 0, 0);
				newChild.setPropValue("Left", c.getPropValue("Left"));
				newChild.setPropValue("Top", c.getPropValue("Top"));
			}
		}
	}

	public static XMLDto getStyle() {
		return style;
	}

	public static void setStyle(XMLDto style) {
		CompUtils.style = style;
		fields.clear();
		fieldsPnl = null;
		if (style == null) {
			return;
		}
		if (!CommonUtils.isStrEmpty(style.getValue("classid"))) {
			try {
				fields = InvokerServiceUtils.getClassItemList(style.getValue("classid"));
				fieldsPnl = new ObjectSelectPnl<XMLDto>(fields);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}

	}

	public static List<XMLDto> getFields() {
		return fields;
	}

	public static final Properties EDITORCOMS = new OrderedProperties();

	static {
		try {
			InputStream in = new FileInputStream(new File("config" + File.separator + "editorComs.properties"));
			EDITORCOMS.load(in);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	public static Cursor getCursor(JComponent com, Point locationOnScreen) {
		int width = com.getWidth(), height = com.getHeight();

		Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		if ((com instanceof IStyleCom) && ((IStyleCom) com).isSelect()) {
			Point p1 = com.getLocationOnScreen();
			Point p2 = new Point(p1.x + width / 2, p1.y);
			Point p3 = new Point(p1.x + width, p1.y);
			//
			Point p4 = new Point(p1.x, p1.y + height / 2);
			Point p5 = new Point(p4.x + width, p4.y);
			//
			Point p6 = new Point(p1.x, p1.y + height);
			Point p7 = new Point(p6.x + width / 2, p6.y);
			Point p8 = new Point(p6.x + width, p6.y);
			Point[] ps = new Point[] { p1, p2, p3, p4, p5, p6, p7, p8 };
			boolean flag = false;
			int focusIndex = 0;
			for (int i = 0; i < ps.length; i++) {
				Point p = ps[i];
				flag = CompUtils.isOnPoint(locationOnScreen, p, DefaultCom.RESIZESIZE);
				if (flag == true) {
					focusIndex = i + 1;
					break;
				}
			}
			switch (focusIndex) {
			case 1:
				// 西北
				if (!com.equals(CompUtils.getWin())) {
					cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
				}
				break;
			case 2:
				// 北
				cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
				break;
			case 3:
				// 东北
				cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
				break;
			case 4:
				// 西
				cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
				break;
			case 5:
				// 东
				cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
				break;
			case 6:
				// 西南
				cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
				break;
			case 7:
				// 南
				cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
				break;
			case 8:
				// 东南
				cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
				break;
			}

		}
		if (cursor.equals(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))) {
			if ((!com.equals(CompUtils.getWin())) && isOnPoint(locationOnScreen, com, 0)) {
				cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			}
		}
		return cursor;
	}

	public static StyleMainPnl getStyleMain() {
		return stylemainpnl;
	}

	public static TNewPanel getWin() {
		return win;
	}

	/**
	 * 知否在以toPoint为中心，resizesize为边的正方行内
	 * 
	 * @param point
	 * @param toPoint
	 * @param resizesize
	 * @return
	 */
	private static boolean isOnPoint(Point point, Point toPoint, int resizesize) {
		double x = point.getX();
		double y = point.getY();
		double tx = toPoint.getX();
		double ty = toPoint.getY();
		return Math.abs(x - tx) <= resizesize / 2 && Math.abs(y - ty) <= resizesize / 2;
	}

	/**
	 * 某点是否在控件内
	 * 
	 * @param pointOnScrent
	 *            点在屏幕
	 * @param c
	 * @param resizesize
	 *            向内伸展
	 * @return
	 */
	public static boolean isOnPoint(Point pointOnScrent, Component c, int resizesize) {
		Rectangle a = new Rectangle();
		a.setBounds(c.getLocationOnScreen().x, c.getLocationOnScreen().y, c.getWidth(), c.getHeight());
		a.setBounds(a.x + resizesize, a.y + resizesize, a.width - 2 * resizesize, a.height - 2 * resizesize);
		Rectangle b = new Rectangle();
		b.setBounds(pointOnScrent.x, pointOnScrent.y, 0, 0);
		return SwingUtilities.isRectangleContainingRectangle(a, b);
	}

	public static void setStyleMain(StyleMainPnl sm) {
		stylemainpnl = sm;
	}

	public static void setWin(TNewPanel w) {
		win = w;
	}

	public static List<IStyleCom> getSelect() {
		if (win == null) {
			return null;
		}
		final List<IStyleCom> selects = new ArrayList<IStyleCom>();
		if (win.isSelect()) {
			selects.add(win);
		} else {
			do2AllComs(win, new ComActionHandler() {
				@Override
				public void action(IStyleCom com) {
					if (com.isSelect()) {
						selects.add(com);
					}
				}

			});
		}
		return selects;
	}

	public static List<IStyleCom> getSelect(IStylePanel parent) {
		final List<IStyleCom> selects = new ArrayList<IStyleCom>();
		do2AllComs(parent, new ComActionHandler() {
			@Override
			public void action(IStyleCom com) {
				if (com.isSelect()) {
					selects.add(com);
				}
			}

		});
		return selects;
	}

	public static void do2AllComs(IStylePanel parent, ComActionHandler handler) {
		List<IStyleCom> cs = parent.getChilds();
		for (IStyleCom c : cs) {
			handler.action(c);
			if (c instanceof IStylePanel) {
				do2AllComs((IStylePanel) c, handler);
			}
		}
	}

	public static List<IStyleCom> getComs(IStylePanel parent) {
		List<IStyleCom> ls = new ArrayList<IStyleCom>();
		List<IStyleCom> cs = parent.getChilds();
		for (IStyleCom c : cs) {
			if (!(c instanceof IStyleCom)) {
				continue;
			}
			if (c instanceof IStylePanel) {
				ls.addAll(getComs((IStylePanel) c));
			}
			ls.add(c);
		}
		return ls;
	}

	// 控件是否和选择的控件在同一父界面上
	public static boolean isBothPanl(Component com) {
		List<IStyleCom> selects = getSelect(win);
		if (selects.size() <= 0) {
			// 没选时
			return true;
		}
		IStyleCom first = selects.get(0);
		if (((Component) first).getParent().equals(com.getParent())) {
			return true;
		}
		return false;
	}

	public static List<IStyleCom> getWinComs() {
		win = CompUtils.getWin();
		if (win == null) {
			return new ArrayList<IStyleCom>();
		}
		List<IStyleCom> ls = win.getChilds();
		for (int i = 0; i < ls.size(); i++) {
			IStyleCom c = ls.get(i);
			if (c instanceof IStylePanel) {
				ls.addAll(CompUtils.getComs((IStylePanel) c));
			}
		}
		return ls;
	}

	public static void clearSelect() {
		win.setSelect(false);
		List<IStyleCom> ls = CompUtils.getWinComs();
		if (ls == null) {
			return;
		}
		for (IStyleCom c : ls) {
			c.setSelect(false);
		}
	}

	public static String getComName() {
		String name = "ctrl_";
		List<IStyleCom> coms = CompUtils.getWinComs();
		List<String> names = new ArrayList<String>();
		names.add(CompUtils.getWin().getPropValue("Name"));
		for (IStyleCom com : coms) {
			names.add(com.getPropValue("Name"));
		}
		int count = coms.size();
		String temp = name + count;
		while (names.contains(temp)) {
			temp = name + (++count);
		}
		return temp;
	}

	public static IStyleCom createCom(String typeStr, IStylePanel parent, boolean create, Element c) {
		logger.debug(typeStr);
		ComEum type = ComEum.getTypeByStr(typeStr);

		if (parent != null) {
			if (parent.forbid(type)) {
				GUIUtils.showMsg(MainFrame.getInstance(), "不允许添加此控件");
				return null;
			}
		}
		if (type == null) {
			return null;
		}
		IStyleCom com = null;

		switch (type) {
		case TNewLabel:
			com = new TNewLabel();
			break;
		case TNewButton:
			com = new TNewButton();
			break;
		case TNewCheckBox:
			com = new TNewCheckBox();
			break;
		case TNewEdit:
			com = new TNewEdit();
			break;
		case TNewRadioBox:
			com = new TNewRadioBox();
			break;
		case TNewMemo:
			com = new TNewMemo();
			break;
		case TNewCondiPanel:
			com = new TNewCondiPanel();
			if (create) {
				IStyleCom b1 = CompUtils.createCom(ComEum.TNewButton.toString(), (IStylePanel) com, true, null);
				IStyleCom b2 = CompUtils.createCom(ComEum.TNewButton.toString(), (IStylePanel) com, true, null);
				b1.setPropValue("Caption", "确定");
				b2.setPropValue("Caption", "更多条件");
				b1.setPropValue("Name", "cgp_1ok");
				b2.setPropValue("Name", "cgp_1show");
				b1.setPropValue("Left", "400");
				b1.setPropValue("Top", "10");
				b2.setPropValue("Left", "400");
				b2.setPropValue("Top", "35");
				b1.upateUIByProps();
				b2.upateUIByProps();
			}
			break;
		case TNewCombobox:
			com = new BaseCombobox(ComEum.TNewCombobox);
			break;
		case TMonthCombobox:
			com = new BaseCombobox(ComEum.TMonthCombobox);
			break;
		case TFinishStatusCombobox:
			com = new BaseCombobox(ComEum.TFinishStatusCombobox);
			break;
		case TGroupCombobox:
			com = new BaseCombobox(ComEum.TGroupCombobox);
			break;
		case TLevelCombobox:
			com = new BaseCombobox(ComEum.TLevelCombobox);
			break;
		case TTreeCombobox:
			com = new BaseCombobox(ComEum.TTreeCombobox);
			break;
		case TYearCombobox:
			com = new BaseCombobox(ComEum.TYearCombobox);
			break;
		case TNewTreeView:
			com = new TNewTreeView();
			break;
		case TNewGrid:
			com = new TNewGrid();
			break;
		case TNewGroupPanel:
			com = new TNewGroupPanel();
			break;
		case TNewChart:
			com = new TNewChart();
			break;
		case TNewGroupBox:
			com = new TNewGroupBox();
			break;
		default:
			break;
		}
		if (com != null) {
			if (parent != null) {
				parent.addChild((Component) com);
				com.setParentPnl(parent);
			}
			if (!create && c != null) {
				Element propEle = c.element("property");
				com.init(propEle);
			}

		}
		return com;
	}

	public static void setFlxColumnWidth2Table(JTable table, int column, int width) {
		TableColumn tc = table.getTableHeader().getColumnModel().getColumn(column);
		tc.setMaxWidth(width);
		tc.setPreferredWidth(width);
		tc.setWidth(width);
		tc.setMinWidth(width);
		table.getTableHeader().getColumnModel().getColumn(column).setMaxWidth(width);
		table.getTableHeader().getColumnModel().getColumn(column).setMinWidth(width);
	}

	public static void jTAblesetSelects(JTable table, List<Integer> newSelectIndex) {
		if (table == null) {
			return;
		}
		table.clearSelection();
		if (newSelectIndex.size() <= 0) {
			return;
		}
		if (newSelectIndex.size() == 1) {
			table.setRowSelectionInterval(newSelectIndex.get(0), newSelectIndex.get(0));
			return;
		}
		CompUtils.stopTabelCellEditor(table);
		Collections.sort(newSelectIndex);
		int min = newSelectIndex.get(0), max = newSelectIndex.get(newSelectIndex.size() - 1);
		table.setRowSelectionInterval(min, max);

		for (; min <= max; min++) {
			if (newSelectIndex.contains(min)) {
				continue;
			}
			table.removeRowSelectionInterval(min, min);
		}
	}

	public static void setColumnWidth(JTable table, int column, int width) {
		TableColumnModel cm = table.getColumnModel();
		TableColumn c = cm.getColumn(column);
		c.setMaxWidth(width);
		c.setMinWidth(width);
		c.setPreferredWidth(width);
	}

	public static void updateUI(IStyleCom com) {
		if (com instanceof IStylePanel) {
			IStylePanel panel = (IStylePanel) com;
			for (IStyleCom child : panel.getChilds()) {
				updateUI(child);
			}
		}
		com.upateUIByProps();
	}

	public static AbstractButton getGroupSelect(ButtonGroup bg) {
		Enumeration<AbstractButton> enume = bg.getElements();
		while (enume.hasMoreElements()) {
			AbstractButton btn = enume.nextElement();
			if (btn.isSelected()) {
				return btn;
			}
		}
		return null;
	}

	public static List<IStyleCom> getInterCom(final int x, final int y, final int width, final int height) {
		final List<IStyleCom> coms = new ArrayList<IStyleCom>();
		ComActionHandler handler = new ComActionHandler() {
			@Override
			public void action(IStyleCom com) {
				JComponent comp = (JComponent) com;
				Rectangle dest = comp.getBounds();
				dest = SwingUtilities.convertRectangle(comp.getParent(), dest, win);
				Rectangle result = SwingUtilities.computeIntersection(x, y, width, height, dest);
				if (result.getWidth() != 0 || result.getHeight() != 0) {
					coms.add(com);
				}

			}
		};
		do2AllComs(win, handler);
		return coms;
	}

	public static Object[] getFontProps(String fonttext) {
		Object[] pros = new Object[2];
		try {
			HashMap<TextAttribute, Object> hm = new HashMap<TextAttribute, Object>();
			String[] fontArr = fonttext.split(":");
			Color color = ComEum.FONT_DEFAULT_COLOR;
			String family = ComEum.FONT_DEFAULT_FIMALY;
			int size = ComEum.FONT_DEFAULT_SIZE;
			boolean newcolor = false;// 自定义颜色
			String delphiName = null;
			for (String fontatt : fontArr) {
				String[] att = fontatt.split("=");
				if (att.length != 2) {
					continue;
				}
				String key = att[0], value = att[1];

				if ("name".equals(key)) {
					family = value;
				}

				if ("size".equals(key)) {
					size = MathUtils.strToInt(value) - ComEum.FONT_SIZE_GAP;
				}
				if ("style".equals(key)) {
					if (value.indexOf(",fsBold") != -1) {
						hm.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
					}
					if (value.indexOf(",fsItalic") != -1) {
						hm.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
					}
					if (value.indexOf(",fsUnderline") != -1) {
						hm.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON); // 定义是否有下划线
					}
					if (value.indexOf(",fsStrikeOut") != -1) {
						hm.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON); // 定义是否有删除线
					}
				}

				if ("color".equals(key)) {
					delphiName = value;
				}
				if ("newcolor".equals(key)) {
					String[] rgbStr = value.split(",");
					if (rgbStr.length == 3) {
						color = new Color(Integer.parseInt(rgbStr[0]), Integer.parseInt(rgbStr[1]), Integer.parseInt(rgbStr[2]));
						newcolor = true;
					}
				}
			}
			if (!newcolor && delphiName != null && !"clWindowText".equals(delphiName)) {
				Color temp = MyColorFactory.getInstance().getColorByDeliphy(delphiName);
				if (temp != null) {
					color = temp;
				}
			}
			hm.put(TextAttribute.FAMILY, family);
			hm.put(TextAttribute.SIZE, size);
			pros[0] = new Font(hm);
			pros[1] = color;
		} catch (Exception e) {
			pros[0] = new Font(ComEum.FONT_DEFAULT_FIMALY, Font.PLAIN, ComEum.FONT_DEFAULT_SIZE);
			pros[1] = Color.BLACK;
			logger.debug(fonttext);
			logger.error(e.getMessage(), e);
		}
		return pros;
	}

	public static List<DefaultMutableTreeNode> searchTreeNodes(DefaultMutableTreeNode root, String value) {
		if (CommonUtils.isStrEmpty(value) || (CommonUtils.isStrEmpty(value.trim()))) {
			return null;
		}
		List<DefaultMutableTreeNode> result = new ArrayList<DefaultMutableTreeNode>();
		value = value.trim().toLowerCase();
		Enumeration<DefaultMutableTreeNode> nodes = root.breadthFirstEnumeration();
		while (nodes.hasMoreElements()) {
			DefaultMutableTreeNode node = nodes.nextElement();
			if (node.toString().toLowerCase().indexOf(value) >= 0) {
				result.add(node);
			}
		}
		return result;
	}

	public static void addstyleCatalogs(DefaultMutableTreeNode pNode) {
		XMLDto nodeInfo = (XMLDto) pNode.getUserObject();
		XMLDto tmpInfo = new XMLDto("name");
		tmpInfo.setValue("dataType", "querystylecatalog");
		tmpInfo.setValue("name", "查询样式");
		tmpInfo.setValue("classid", nodeInfo.getValue("classid"));
		tmpInfo.setValue("classname", nodeInfo.getValue("classname"));
		tmpInfo.setValue("catalogid", nodeInfo.getValue("catalogid"));
		tmpInfo.setValue("catalogcode", nodeInfo.getValue("catalogcode"));
		tmpInfo.setValue("catalogname", nodeInfo.getValue("catalogname"));
		DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(tmpInfo);
		pNode.add(tmpNode);

		XMLDto tmpInfo2 = new XMLDto("name");
		tmpInfo2.setValue("dataType", "printstylecatalog");
		tmpInfo2.setValue("name", "打印样式");
		tmpInfo2.setValue("classid", nodeInfo.getValue("classid"));
		tmpInfo2.setValue("classname", nodeInfo.getValue("classname"));
		tmpInfo2.setValue("catalogid", nodeInfo.getValue("catalogid"));
		tmpInfo2.setValue("catalogcode", nodeInfo.getValue("catalogcode"));
		tmpInfo2.setValue("catalogname", nodeInfo.getValue("catalogname"));
		DefaultMutableTreeNode tmpNode2 = new DefaultMutableTreeNode(tmpInfo2);
		pNode.add(tmpNode2);
	}

	public static DefaultMutableTreeNode cloneNode(DefaultMutableTreeNode node) {
		try {
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode();
			XMLDto obj = (XMLDto) node.getUserObject();
			XMLDto newObj = CommonUtils.cloneDto(obj);
			newNode.setUserObject(newObj);
			return newNode;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static void buildStyleTree(String condi, DefaultMutableTreeNode root) {
		try {
			List<XMLDto> allDataList = InvokerServiceUtils.getTreeAllData(condi);
			DefaultMutableTreeNode parentNode = root;
			for (XMLDto nodeInfo : allDataList) {
				parentNode = addChildNode(parentNode, nodeInfo);
				if ("class".equals(nodeInfo.getValue("dataType"))) {
					addstyleCatalogs(parentNode);
				}
			}
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "建样式树出错");
			logger.error(e.getMessage(), e);
		}
	}

	private static DefaultMutableTreeNode addChildNode(DefaultMutableTreeNode parentNode, XMLDto nodeInfo) {
		if (!(parentNode.getUserObject() instanceof XMLDto)) {// 根节点
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeInfo);
			parentNode.add(child);
			return child;
		} else {
			XMLDto parentInfo = (XMLDto) parentNode.getUserObject();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeInfo);
			if ("catalog".equals(nodeInfo.getValue("dataType"))) { // 分类
				if (nodeInfo.getValue("catalogcode").indexOf(parentInfo.getValue("catalogcode")) == 0 && CommonUtils.isStrEmpty(parentInfo.getValue("classid"))) {
					parentNode.add(child);
					return child;
				} else {
					return addChildNode((DefaultMutableTreeNode) parentNode.getParent(), nodeInfo);
				}
			} else if ("class".equals(nodeInfo.getValue("dataType"))) {// 查询类
				if (CommonUtils.isStrEmpty(parentInfo.getValue("classid"))) {
					XMLDto parentOjb = (XMLDto) parentNode.getUserObject();
					nodeInfo.setValue("catalogdesc", parentOjb.getValue("description"));
					nodeInfo.setValue("catalogname", parentOjb.getValue("name"));
					parentNode.add(child);
					return child;
				} else {
					return addChildNode((DefaultMutableTreeNode) parentNode.getParent(), nodeInfo);
				}
			} else {
				if ("class".equals(parentInfo.getValue("dataType"))) {// 样式
					// 有查询样式，打印样式目录
					if (nodeInfo.getValue("dataType").equals("printstyle")) {
						DefaultMutableTreeNode lastChild = (DefaultMutableTreeNode) parentNode.getLastChild();
						lastChild.add(child);
					} else {
						DefaultMutableTreeNode firstChild = (DefaultMutableTreeNode) parentNode.getFirstChild();
						firstChild.add(child);
					}
					return child;
				} else {
					return addChildNode((DefaultMutableTreeNode) parentNode.getParent(), nodeInfo);
				}
			}
		}
	}

	public static IStyleCom createCom(int x, int y, IStylePanel com, String editorType, boolean create) {
		String name = CompUtils.getComName();
		IStyleCom newCom = CompUtils.createCom(editorType, com, create, null);
		if (newCom != null) {
			CompUtils.clearSelect();

			if (com instanceof TNewPanel) {
				y -= TNewPanel.HEADER_HIGHT;
			}
			if (CommonUtils.isNumberString(newCom.getPropValue("Width"))) {
				x -= Integer.parseInt(newCom.getPropValue("Width")) / 2;
			}

			if (CommonUtils.isNumberString(newCom.getPropValue("Height"))) {
				y -= Integer.parseInt(newCom.getPropValue("Height")) / 2;
			}
			newCom.setPropValue("Left", x + "");
			newCom.setPropValue("Top", y + "");
			newCom.setPropValue("Name", name);
			newCom.setSelect(true);
			newCom.upateUIByProps();
			CompUtils.getStyleMain().rebuildComTree();
		}
		return newCom;
	}

	public static void setTableWdiths(JTable table, Double... widths) {
		TableColumnModel cm = table.getColumnModel();
		int sumWidth = table.getPreferredSize().width;
		int columnCount = cm.getColumnCount();
		for (int i = 0; i < widths.length; i++) {
			if (i >= columnCount) {
				break;
			}
			if (widths[i] == null) {
				continue;
			}
			int width = (int) (sumWidth * widths[i]);
			if (width < 0) {
				continue;
			}
			TableColumn c = cm.getColumn(i);
			c.setPreferredWidth(width);
			c.setMinWidth(width);
			c.setMaxWidth(width);
		}
	}

	public static void sortTable(JTable table, final int sumColumn) {
		if (table.getRowCount() <= 0) {
			return;
		}
		CompUtils.stopTabelCellEditor(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Vector<Vector<?>> datas = (Vector<Vector<?>>) model.getDataVector().clone();
		Collections.sort(datas, new Comparator<Vector<?>>() {
			@Override
			public int compare(Vector<?> o1, Vector<?> o2) {
				Object s1 = o1.get(sumColumn);
				Object s2 = o2.get(sumColumn);

				if (!(s1 instanceof Integer)) {
					if (s1 instanceof String) {
						if (CommonUtils.isNumberString((String) s1)) {
							s1 = Integer.parseInt((String) s1);
						}
					} else {
						return -1;
					}

				}
				if (!(s2 instanceof Integer)) {
					if (s2 instanceof String) {
						if (CommonUtils.isNumberString((String) s2)) {
							s2 = Integer.parseInt((String) s2);
						}
					} else {
						return 1;
					}
				}
				int n1 = (Integer) s1;
				int n2 = (Integer) s2;
				return n1 - n2;
			}
		});

		model.setRowCount(0);
		for (int i = 0; i < datas.size(); i++) {
			model.addRow(datas.get(i));
		}
	}

	public static <T> T getCellValue(Class<T> clazz, JTable table, int row, int column) {
		if (row < 0 || column < 0 || row > table.getRowCount() - 1 || column > table.getColumnCount() - 1) {
			return null;
		}
		Object obj = table.getValueAt(row, column);
		if (obj == null || clazz == null) {
			return null;
		}
		if (clazz.equals(obj.getClass())) {
			return (T) obj;
		}
		return null;
	}

	public static DefaultMutableTreeNode getTreeNode(DefaultMutableTreeNode parent, String key, String value) {
		if (parent.getChildCount() <= 0) {
			return null;
		}
		Enumeration<DefaultMutableTreeNode> children = parent.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode c = children.nextElement();
			Object obj = c.getUserObject();
			if (!(obj instanceof XMLDto)) {
				continue;
			}
			XMLDto dto = (XMLDto) obj;
			if (value.equals(dto.getValue(key))) {
				return c;
			}
			if (c.getChildCount() > 0) {
				DefaultMutableTreeNode node = getTreeNode(c, key, value);
				if (node != null) {
					return node;
				}
			}
		}
		return null;
	}

	public static void stopTabelCellEditor(JTable table) {
		if (table.getRowCount() <= 0) {
			return;
		}
		TableCellEditor editor = table.getCellEditor();
		if (editor != null) {
			if (table.isEditing()) {
				editor.stopCellEditing();
			}
		}
	}

	public static void initSortNum(AbstractTableModel model, int index) {
		if (model.getRowCount() < 0 || index < 0 || index > model.getColumnCount() - 1) {
			return;
		}
		for (int i = 0; i < model.getRowCount(); i++) {
			model.setValueAt(i + 1, i, index);
		}
	}

	public static void expandTree(JTree tree, Validate<DefaultMutableTreeNode> validate) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		Enumeration<DefaultMutableTreeNode> childs = root.children();
		if (root.getChildCount() > 0) {
			iterExpand(tree, model, validate, root);
		}
	}

	private static void iterExpand(JTree tree, DefaultTreeModel model, Validate<DefaultMutableTreeNode> validate, DefaultMutableTreeNode parent) {
		if (CommonUtils.isStrEmpty(validate.validate(parent))) {
			tree.expandPath(new TreePath(model.getPathToRoot(parent)));
		}
		Enumeration<DefaultMutableTreeNode> childs = parent.children();
		while (childs.hasMoreElements()) {
			DefaultMutableTreeNode c = childs.nextElement();
			if (CommonUtils.isStrEmpty(validate.validate(c))) {
				tree.expandPath(new TreePath(model.getPathToRoot(c)));
			}
			if (c.getChildCount() > 0) {
				iterExpand(tree, model, validate, c);
			}
		}
	}

	public static void collapseTreeLevel1(JTree tree, DefaultMutableTreeNode root) {
		Enumeration<DefaultMutableTreeNode> cs = root.children();
		while (cs.hasMoreElements()) {
			tree.collapsePath(new TreePath(cs.nextElement().getPath()));
		}
	}

	public static void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() > 0) {
			for (Enumeration<TreeNode> e = node.children(); e.hasMoreElements();) {
				TreeNode n = e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	public static void setTableScrolVisible(JTable table, Integer currentIndex) {
		if (table.getRowCount() <= 0 || currentIndex < 0 || currentIndex >= table.getRowCount()) {
			return;
		}
		Container parent = table.getParent().getParent();
		if (!(parent instanceof JScrollPane)) {
			return;
		}
		JScrollPane scroll = (JScrollPane) parent;
		JScrollBar jScrollBar = scroll.getVerticalScrollBar();
		int indexValue = table.getRowHeight() * currentIndex;
		int currentValue = jScrollBar.getValue();
		if (indexValue < currentValue) {
			jScrollBar.setValue(indexValue);
			return;
		}
		if (indexValue >= currentValue + jScrollBar.getVisibleAmount()) {
			jScrollBar.setValue(indexValue - jScrollBar.getVisibleAmount() + table.getRowHeight());
		}

	}

	public static void listMove(JButton btn, final JList list, final Do4objs do4, final boolean up) {
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						move();
					}

					private void move() {
						DefaultListModel model = (DefaultListModel) list.getModel();
						int[] sels = list.getSelectedIndices();

						if (sels.length <= 0 || up && sels[0] == 0 || !up && sels[sels.length - 1] == model.getSize() - 1) {
							return;
						}
						List<Object> datas = new ArrayList<Object>();
						for (int i = 0; i < model.getSize(); i++) {
							datas.add(model.getElementAt(i));
						}
						int[] newSels = new int[sels.length];
						for (int i = 0; i < sels.length; i++) {
							int sel = sels[i];
							Object obj1 = datas.get(sel);
							int temp = sel + (up ? -1 : 1);
							Object obj2 = datas.get(temp);
							datas.set(temp, obj1);
							datas.set(sel, obj2);
							if (do4 != null) {
								do4.do4ojbs(obj1, obj2);
							}
							newSels[i] = temp;
						}
						model.clear();
						for (Object obj : datas) {
							model.addElement(obj);
						}
						list.setSelectedIndices(newSels);
					}
				}, 0, 200);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				timer.cancel();
			}
		});

	}

	public static void tableMove(JButton btn, final JTable table, final int sortIndex, final boolean up) {
		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						move();
					}

					private void move() {
						int[] selectIndexs = table.getSelectedRows();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						if (selectIndexs.length <= 0 || up && selectIndexs[0] == 0 || !up && selectIndexs[selectIndexs.length - 1] == model.getRowCount() - 1) {
							return;
						}
						stopTabelCellEditor(table);
						Vector<Vector<?>> data = model.getDataVector();
						List<Integer> newSelectIndex = new ArrayList<Integer>();

						if (!up && selectIndexs.length > 1) {
							int[] temp = new int[selectIndexs.length];
							for (int i = 0; i < selectIndexs.length; i++) {
								temp[i] = selectIndexs[selectIndexs.length - 1 - i];
							}
							selectIndexs = temp;
						}
						for (int i = 0; i < selectIndexs.length; i++) {
							int index = selectIndexs[i];
							int newIndex = index + (up ? -1 : 1);
							Vector<?> obj = data.get(index);
							model.removeRow(index);
							model.insertRow(newIndex, obj);
							newSelectIndex.add(newIndex);
						}
						CompUtils.jTAblesetSelects(table, newSelectIndex);
						CompUtils.setTableScrolVisible(table, newSelectIndex.get(0));
						CompUtils.initSortNum(model, sortIndex);

					}
				}, 0, 200);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				timer.cancel();
			}
		});

	}

	public static void tableDelRow(JButton btn, final JTable table, final int index_sortcode) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selIndexs[] = table.getSelectedRows();
				if (selIndexs.length <= 0) {
					return;
				}
				CompUtils.stopTabelCellEditor(table);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				for (int i = 0; i < selIndexs.length; i++) {
					model.removeRow(selIndexs[i] - i);// 因为移除后，表格的总行数也随着变化，所以要
				}
				if (index_sortcode >= 0 && index_sortcode < model.getColumnCount() - 1) {
					CompUtils.initSortNum(model, index_sortcode);
				}
				if (model.getRowCount() > 0) {
					int index = selIndexs[selIndexs.length - 1] - selIndexs.length;
					if (index < 0) {
						index = 0;
					}
					table.setRowSelectionInterval(index, index);
				}
			}
		});
	}

	public static boolean hasSaveNameCom(IStylePanel parent, IStyleCom com, String newName) {
		List<IStyleCom> coms = CompUtils.getWinComs();
		for (IStyleCom c : coms) {
			if (!parent.equals(c.getParentPnl()) || com != null && c.equals(com)) {
				continue;
			}
			String temp = c.getPropValue("Name");
			if (temp.equals(newName)) {
				return true;
			}
		}
		return false;
	}

	public static void checkSaveName() {
		List<IStyleCom> coms = CompUtils.getWinComs();
		if (coms.isEmpty()) {
			return;
		}
		Map<String, IStyleCom> nameComs = new HashMap<String, IStyleCom>();
		Map<String, List<IStyleCom>> saveComs = new HashMap<String, List<IStyleCom>>();
		for (IStyleCom c : coms) {
			String name = c.getPropValue("Name");
			if (nameComs.containsKey(name)) {
				// 如果有
				if (saveComs.containsKey(name)) {
					// 如果saveComs 有 直接 拿来加
					saveComs.get(name).add(c);
				} else {
					// saveComs没有 新增 加
					List<IStyleCom> value = new ArrayList<IStyleCom>();
					value.add(nameComs.get(name));
					value.add(c);
					saveComs.put(name, value);
				}
			} else {
				nameComs.put(name, c);
			}
		}
		if (saveComs.isEmpty()) {
			return;
		}
		if (!GUIUtils.showConfirm(MainFrame.getInstance(), "存在相同名称,是否修改")) {
			return;
		}
		// 如果有相同names
		new SaveNameEditor(saveComs);
	}

	public static void rebuiTree(JTree tree, Do4objs do4) {
		final List<String> expands = new ArrayList<String>();
		Enumeration<TreePath> paths = tree.getExpandedDescendants(new TreePath(tree.getModel().getRoot()));
		if (paths != null) {
			while (paths.hasMoreElements()) {
				TreePath temp = paths.nextElement();
				expands.add(temp.toString());
			}
		}
		do4.do4ojbs(tree);
		CompUtils.expandTree(tree, new Validate<DefaultMutableTreeNode>() {
			@Override
			public String validate(DefaultMutableTreeNode node) {
				if (expands.contains(new TreePath(node.getPath()).toString())) {
					return null;
				}
				return "继续";
			}
		});
	}

	public static TreeModel buildReportTreeModel(DefaultMutableTreeNode root) throws Exception {
		// 创建目录树
		String xml = InvokerServiceUtils.getReportCatalogXml();
		List<String> props = new ArrayList<String>();
		props.add("id");
		props.add("name");
		props.add("sortcode");
		props.add("type");
		DefaultTreeModel model = new DefaultTreeModel(root);
		Document doc = DocumentHelper.parseText(xml);
		Element xmlRoot = doc.getRootElement();
		List<Element> elements = xmlRoot.elements("catalog");
		// 父节点
		Map<String, DefaultMutableTreeNode> parents = new HashMap<String, DefaultMutableTreeNode>();

		Map<String, DefaultMutableTreeNode> flowCatalog = new HashMap<String, DefaultMutableTreeNode>();
		List<String> toString = Arrays.asList("sortcode", "name", "hasFlow", "id");
		for (Element element : elements) {
			XMLDto dto = new XMLDto(toString);
			for (String prop : props) {
				// 每个属性的值
				String propValue = element.elementText(prop);
				if (propValue == null) {
					propValue = "";
				}
				dto.setValue(prop, propValue);
			}
			dto.setValue("leaf", "false");
			dto.setValue("hasFlow", "");
			String sortcodeVal = dto.getValue("sortcode");
			DefaultMutableTreeNode node = new DefaultMutableTreeNode();
			node.setUserObject(dto);
			if (sortcodeVal.length() <= 2) {
				root.add(node);
			}
			String parentCode = sortcodeVal.substring(0, sortcodeVal.length() - 2);
			DefaultMutableTreeNode parent = parents.get(parentCode);
			if (parent != null) {
				parent.add(node);
			}
			if ("0".equals(dto.getValue("type"))) {
				flowCatalog.put(dto.getValue("id"), node);
			}
			parents.put(sortcodeVal, node);
		}

		parents = null;

		String flow = InvokerServiceUtils.getReportFlowXml();
		doc = DocumentHelper.parseText(flow);
		xmlRoot = doc.getRootElement();
		elements = xmlRoot.elements("flow");
		props.add("reportid");
		props.add("catalogid");
		props.add("name");
		for (Element element : elements) {
			XMLDto dto = new XMLDto("name");
			// 比较树关系属性
			for (String prop : props) {
				// 每个属性的值
				String propValue = element.elementText(prop);
				if (propValue == null) {
					propValue = "";
				}
				dto.setValue(prop, propValue);
			}
			dto.setValue("leaf", "true");
			DefaultMutableTreeNode node = new DefaultMutableTreeNode();
			node.setUserObject(dto);
			DefaultMutableTreeNode parent = flowCatalog.get(dto.getValue("catalogid"));
			if (parent != null) {
				parent.add(node);
				while (parent != null) {
					XMLDto pboj = (XMLDto) parent.getUserObject();
					pboj.setValue("hasFlow", "有流程");
					parent = (DefaultMutableTreeNode) parent.getParent();
					if (parent.isRoot()) {
						break;
					}
				}
			}
		}
		flowCatalog = null;
		return model;
	}

	public static Map<String, DefaultMutableTreeNode> buildTree(List<XMLDto> list, DefaultMutableTreeNode root, String treeCode, String key) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		Map<String, DefaultMutableTreeNode> allNode = null;
		if (!CommonUtils.isStrEmpty(key)) {
			allNode = new HashMap<String, DefaultMutableTreeNode>();
		}
		DefaultMutableTreeNode parent = null;
		for (XMLDto dto : list) {
			String codeValue = dto.getValue(treeCode);
			DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(dto);
			if (allNode != null) {
				allNode.put(dto.getValue(key), newChild);
			}
			if (parent != null && !parent.isRoot()) {
				while (parent != null && !parent.isRoot()) {
					if (parent.isRoot()) {
						break;
					}
					XMLDto pDto = (XMLDto) parent.getUserObject();
					if (codeValue.startsWith(pDto.getValue(treeCode))) {
						break;
					}
					parent = (DefaultMutableTreeNode) parent.getParent();
				}
			}
			if (parent == null) {
				parent = root;
			}
			parent.add(newChild);
			parent = newChild;
		}
		return allNode;
	}

	public static Map<String, DefaultMutableTreeNode> buildTree(DefaultMutableTreeNode root, Map<String, DefaultMutableTreeNode> parentNodes, List<XMLDto> dtos, String treeCode, String key) {
		if (dtos == null || dtos.isEmpty()) {
			return null;
		}
		Map<String, DefaultMutableTreeNode> allNode = null;
		if (!CommonUtils.isStrEmpty(key)) {
			allNode = new HashMap<String, DefaultMutableTreeNode>();
		}
		DefaultMutableTreeNode parent = null;
		String parentCode = null;
		for (XMLDto dto : dtos) {
			DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(dto);
			if (allNode != null) {
				allNode.put(dto.getValue(key), newChild);
			}
			String temp = dto.getValue(treeCode);
			if (parent != null && !parent.isRoot()) {
				if (parentCode.equals(temp)) {
					parent.add(newChild);
					continue;
				}
				parentNodes.remove(parentCode);
			}
			if (parentNodes.containsKey(temp)) {
				parent = parentNodes.get(temp);
			} else {
				parent = root;
			}
			parent.add(newChild);
			parentCode = temp;
		}
		parentNodes = null;
		return allNode;
	}

	public static void treeMove(final JButton btn, final JTree tree, final boolean up) {
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						move();
					}

					private void move() {
						DefaultMutableTreeNode select = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
						if (select == null) {
							return;
						}
						if (select.isRoot()) {
							return;
						}
						if (up && select.getPreviousSibling() == null) {
							return;
						}
						if (!up && select.getNextSibling() == null) {
							return;
						}
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode) select.getParent();
						int index = parent.getIndex(select);
						if (up) {
							index--;
						} else {
							index++;
						}
						parent.remove(select);
						parent.insert(select, index);
						tree.updateUI();
					}
				}, 0, 200);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				timer.cancel();
			}
		});

	}

	public static XMLDto getComboValue(DefaultComboBoxModel fieldModel, String value) {
		if (CommonUtils.isStrEmpty(value) || fieldModel.getSize() <= 0) {
			return null;
		}
		for (int i = 0; i < fieldModel.getSize(); i++) {
			XMLDto dto = (XMLDto) fieldModel.getElementAt(i);
			if (value.equalsIgnoreCase(dto.getValue("value"))) {
				return dto;
			}
		}
		return null;
	}

	public static JTree copyTree(JTree s_tree, Validate<XMLDto> validate, String rootName, boolean rootVisible) {
		try {
			DefaultTreeModel s_model = (DefaultTreeModel) s_tree.getModel();
			DefaultMutableTreeNode s_root = (DefaultMutableTreeNode) s_model.getRoot();
			JTree tree = new JTree();
			tree.setCellRenderer(s_tree.getCellRenderer());
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootName);
			DefaultTreeModel model = new DefaultTreeModel(root);
			tree.setModel(model);
			tree.setRootVisible(rootVisible);
			copyNode(s_root, root, validate);
			return tree;
		} catch (Exception e) {
			GUIUtils.showMsg(null, "建树失败");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	private static void copyNode(DefaultMutableTreeNode s_root, DefaultMutableTreeNode root, Validate<XMLDto> validate) {
		Enumeration<DefaultMutableTreeNode> cs = s_root.children();
		while (cs.hasMoreElements()) {
			DefaultMutableTreeNode s_c = cs.nextElement();
			XMLDto s_obj = (XMLDto) s_c.getUserObject();
			String valide = validate.validate(s_obj);
			if ("remove".equals(valide)) {
				// 删除此节点，和它下的儿子
				continue;
			}
			if (valide == null) {
				XMLDto obj = CommonUtils.cloneDto(s_obj);
				DefaultMutableTreeNode c = new DefaultMutableTreeNode(obj);
				root.add(c);
				copyNode(s_c, c, validate);
			} else if ("ingone".equals(valide)) {
				// 跳过此节点，把它的儿子建立在它的父亲上
				if (s_c.getChildCount() > 0) {
					copyNode(s_c, root, validate);
				}
			} else if ("ingoneChildren".equals(valide)) {
				// 只保留此节点 忽略其儿子
				XMLDto obj = CommonUtils.cloneDto(s_obj);
				DefaultMutableTreeNode c = new DefaultMutableTreeNode(obj);
				root.add(c);
			}

		}
	}

	public static void textareaInsertText(JTextArea textArea, String txt) {
		if (txt == null || textArea == null) {
			return;
		}
		textArea.replaceSelection(txt);
		textArea.requestFocus();
	}
}
