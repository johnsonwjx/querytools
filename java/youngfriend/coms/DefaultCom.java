package youngfriend.coms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.gui.InputSearchPnl;
import youngfriend.main.MainFrame;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.PropUtils;

/*
 * 基础控件委托对象
 */
@SuppressWarnings("unchecked")
public class DefaultCom implements IStyleCom {
	private static final int ponitSize = 4;
	public static final int RESIZESIZE = 12;
	public static String[] toStringProps = { "name", "caption", "fieldname" };
	private JComponent com;
	private Map<String, PropDto> proMap = null;
	private ComEum type;
	private boolean select = false;
	private Point start;
	private Point end;
	private Point dropStart;
	private boolean drogged = false;
	private final Logger logger = LogManager.getLogger(DefaultCom.class.getName());
	private static final JPopupMenu menu;
	private static final JPopupMenu comboselect;
	private static final Map<String, String> comboselectMap = new LinkedHashMap<String, String>();
	private static Point showPoint;
	private static JComponent clickCom;
	private boolean selectPrint = false;
	private List<String> uiProps = new ArrayList<String>();
	private static IStylePanel editorPnl;
	private static JPopupMenu dropCreateMenu;
	private IStylePanel parent = null;
	static {
		dropCreateMenu = new JPopupMenu();
		String[] createName = { ComEum.TNewEdit.getCName(), ComEum.TNewCombobox.getCName(), ComEum.TNewCheckBox.getCName(), ComEum.TNewRadioBox.getCName() };
		for (String name : createName) {
			final JMenuItem item = new JMenuItem(name);
			item.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					IStylePanel parent = (IStylePanel) dropCreateMenu.getInvoker();
					Point point = SwingUtilities.convertPoint(item, e.getPoint(), (Component) parent);
					IStyleCom newCom = CompUtils.createCom(point.x, point.y, parent, ComEum.getTypeByCName(item.getText()).name(), true);
					JTable fieldTable = CompUtils.getStyleMain().getField_table();
					XMLDto dto = (XMLDto) fieldTable.getValueAt(fieldTable.getSelectedRow(), 1);
					if (dto != null) {
						newCom.setPropValue("FieldName", dto.getValue("itemname"));
						newCom.setPropValue("Caption", dto.getValue("itemlabel"));
					}
					dropCreateMenu.setVisible(false);
				}
			});
			dropCreateMenu.add(item);
		}

		comboselectMap.put("普通下拉框", "TNewCombobox");
		comboselectMap.put("切换浏览方式的下拉框", "TTreeCombobox");
		comboselectMap.put("切换统计口径的下拉框", "TGroupCombobox");
		comboselectMap.put("切换数据级次的下拉框", "TLevelCombobox");
		comboselectMap.put("选择年份的下拉框", "TYearCombobox");
		comboselectMap.put("选择月份的下拉框", "TMonthCombobox");
		comboselectMap.put("选择审批状态", "TFinishStatusCombobox");
		comboselect = new JPopupMenu();
		for (String key : comboselectMap.keySet()) {
			JMenuItem item = new JMenuItem(key);
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JMenuItem temp = (JMenuItem) e.getSource();
					CompUtils.createCom(showPoint.x, showPoint.y, editorPnl, comboselectMap.get(temp.getText()), true);
				}
			});
			comboselect.add(item);
		}

		menu = new JPopupMenu();
		String[] menus = new String[] { "左对齐", "右对齐", "水平居中对齐", "上对齐", "下对齐", "垂直居中对齐", null, "相同宽(最小为标准)", "相同宽(最大为标准)", "相同高(最小为标准)", "相同高(最大为标准)", null, "相同间距(纵向)", "相同间距(横向)", null, "复制", "粘贴", "删除", null, "自动转为打印样式" };
		for (String item : menus) {
			if (null == item) {
				menu.add(new JSeparator());
			}
			JMenuItem itemMenu = new JMenuItem(item);
			if ("删除".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						CompUtils.getStyleMain().delSelects();
					}
				});
			} else if ("复制".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.isEmpty()) {
							return;
						}
						CompUtils.copy(selects);
					}
				});
			} else if ("粘贴".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (!(clickCom instanceof IStylePanel)) {
							return;
						}
						CompUtils.paste((IStylePanel) clickCom, showPoint);
					}
				});
			} else if ("左对齐".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						IStyleCom first = CompUtils.firstSelect;
						String left = first.getPropValue("Left");
						for (IStyleCom s : selects) {
							s.setPropValue("Left", left);
							s.upateUIByProps();
						}
					}
				});
			} else if ("右对齐".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						JComponent first = (JComponent) CompUtils.firstSelect;
						int temp = first.getX() + first.getWidth();

						for (IStyleCom s : selects) {
							if (s != first) {
								s.setPropValue("Left", (temp - ((JComponent) s).getWidth()) + "");
								s.upateUIByProps();
							}
						}
					}
				});
			} else if ("水平居中对齐".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						JComponent first = (JComponent) CompUtils.firstSelect;
						int temp = first.getX() + first.getWidth() / 2;

						for (IStyleCom s : selects) {
							if (s != first) {
								s.setPropValue("Left", (temp - ((JComponent) s).getWidth() / 2) + "");
								s.upateUIByProps();
							}
						}
					}
				});
			} else if ("上对齐".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						String top = CompUtils.firstSelect.getPropValue("Top");

						for (IStyleCom s : selects) {
							s.setPropValue("Top", top);
							s.upateUIByProps();
						}
					}
				});
			} else if ("下对齐".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						JComponent first = (JComponent) CompUtils.firstSelect;
						int temp = first.getY() + first.getHeight();
						for (IStyleCom s : selects) {

							if (s != first) {
								s.setPropValue("Top", (temp - ((JComponent) s).getHeight()) + "");
								s.upateUIByProps();
							}
						}
					}
				});
			} else if ("垂直居中对齐".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						JComponent first = (JComponent) CompUtils.firstSelect;
						int temp = first.getY() + first.getHeight() / 2;
						for (IStyleCom s : selects) {

							if (s != first) {
								s.setPropValue("Top", (temp - ((JComponent) s).getHeight() / 2) + "");
								s.upateUIByProps();
							}
						}
					}
				});
			} else if ("相同宽(最小为标准)".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						List<Integer> lst = new ArrayList<Integer>();

						for (IStyleCom s : selects) {
							String width = s.getPropValue("Width");
							if (CommonUtils.isNumberString(width)) {
								lst.add(Integer.parseInt(width));
							}
						}
						Collections.sort(lst);
						for (IStyleCom s : selects) {
							s.setPropValue("Width", lst.get(0) + "");
							s.upateUIByProps();
						}
					}
				});
			} else if ("相同宽(最大为标准)".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						List<Integer> lst = new ArrayList<Integer>();

						for (IStyleCom s : selects) {
							String width = s.getPropValue("Width");
							if (CommonUtils.isNumberString(width)) {
								lst.add(Integer.parseInt(width));
							}
						}
						Collections.sort(lst);
						for (IStyleCom s : selects) {
							s.setPropValue("Width", lst.get(lst.size() - 1) + "");
							s.upateUIByProps();
						}
					}
				});
			} else if ("相同高(最小为标准)".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						List<Integer> lst = new ArrayList<Integer>();

						for (IStyleCom s : selects) {
							String height = s.getPropValue("Height");
							if (CommonUtils.isNumberString(height)) {
								lst.add(Integer.parseInt(height));
							}
						}
						Collections.sort(lst);
						for (IStyleCom s : selects) {
							s.setPropValue("Height", lst.get(0) + "");
							s.upateUIByProps();
						}
					}
				});
			} else if ("相同高(最大为标准)".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						List<Integer> lst = new ArrayList<Integer>();

						for (IStyleCom s : selects) {
							String height = s.getPropValue("Height");
							if (CommonUtils.isNumberString(height)) {
								lst.add(Integer.parseInt(height));
							}
						}
						Collections.sort(lst);
						for (IStyleCom s : selects) {
							s.setPropValue("Height", lst.get(lst.size() - 1) + "");
							s.upateUIByProps();
						}
					}
				});
			} else if ("相同间距(纵向)".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						Collections.sort(selects, new Comparator<IStyleCom>() {

							@Override
							public int compare(IStyleCom o1, IStyleCom o2) {
								String t1 = o1.getPropValue("Top");
								String t2 = o2.getPropValue("Top");
								int top1 = 0, top2 = 0;
								if (CommonUtils.isNumberString(t1)) {
									top1 = Integer.parseInt(t1);
								}
								if (CommonUtils.isNumberString(t2)) {
									top2 = Integer.parseInt(t2);
								}
								return top1 - top2;
							}
						});

						// 求最大 和 最小
						int min = Integer.parseInt(selects.get(0).getPropValue("Top"));
						int max = Integer.parseInt(selects.get(selects.size() - 1).getPropValue("Top"));
						// 平均
						int avg = (max - min) / (selects.size() - 1);

						// 迭代 每个 最小 +当前*平均
						int i = 0;
						for (IStyleCom s : selects) {
							int temp = min + avg * i;
							s.setPropValue("Top", temp + "");
							s.upateUIByProps();
							i++;
						}
					}
				});
			} else if ("相同间距(横向)".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						if (selects.size() < 2) {
							return;
						}
						Collections.sort(selects, new Comparator<IStyleCom>() {

							@Override
							public int compare(IStyleCom o1, IStyleCom o2) {
								String l1 = o1.getPropValue("Left");
								String l2 = o2.getPropValue("Left");
								int left1 = 0, left2 = 0;
								if (CommonUtils.isNumberString(l1)) {
									left1 = Integer.parseInt(l1);
								}
								if (CommonUtils.isNumberString(l2)) {
									left2 = Integer.parseInt(l2);
								}
								return left1 - left2;
							}
						});

						// 求最大 和 最小
						int min = Integer.parseInt(selects.get(0).getPropValue("Left"));
						int max = Integer.parseInt(selects.get(selects.size() - 1).getPropValue("Left"));
						// 平均
						int avg = (max - min) / (selects.size() - 1);

						// 迭代 每个 最小 +当前*平均
						int i = 0;
						for (IStyleCom s : selects) {
							int temp = min + avg * i;
							s.setPropValue("Left", temp + "");
							s.upateUIByProps();
							i++;
						}
					}
				});
			} else if ("自动转为打印样式".equals(item)) {
				itemMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						TNewPanel win = CompUtils.getWin();
						if (win != null) {
							win.setPropValue("width", "780");
							win.setPropValue("height", "1065");
							for (IStyleCom c : win.getChilds()) {
								if (c.getType() == ComEum.TNewGrid) {
									c.setPropValue("left", "15");
									c.setPropValue("top", "15");
									c.setPropValue("width", "755");
									c.setPropValue("height", "1005");
									continue;
								}
								JComponent curC = (JComponent) c;
								curC.getParent().remove(curC);
							}
							CompUtils.updateUI(CompUtils.getWin());
							CompUtils.getStyleMain().rebuildComTree();
						}

					}
				});

			}
			menu.add(itemMenu);
		}
	}

	// 只有一种类型
	public DefaultCom(JComponent com) {
		this.com = com;
		type = ComEum.valueOf(com.getClass().getSimpleName());
		before();
		swingComBefore();
	}

	// 多种类型 像combobox
	public DefaultCom(JComponent com, ComEum type) {
		this.com = com;
		this.type = type;
		before();
		swingComBefore();
	}

	//
	public DefaultCom(JComponent com, String typeStr) {
		this.com = com;
		this.type = ComEum.getTypeByStr(typeStr);
		before();
		swingComBefore();
	}

	// 代理对象不是 swing控件
	public DefaultCom(ComEum type) {
		this.type = type;
		before();
	}

	private void before() {

		if (getType().equals(ComEum.TNewPanel) && "condistyle".equalsIgnoreCase(CompUtils.getStyle().getValue("dataType"))) {
			this.proMap = PropUtils.initPropDto(type.getPropList(), com, true, "left", "top", "width", "height", "name", "querytitle", "titlefont");
		} else {
			this.proMap = PropUtils.initPropDto(type.getPropList(), com, null);
		}
		uiProps.add("Left");
		uiProps.add("Top");
		uiProps.add("Height");
		uiProps.add("Width");
		uiProps.add("Width");
		uiProps.add("MyFont");
		uiProps.add("TitleFont");
		uiProps.add("Caption");
		if (com instanceof IStylePanel) {
			com.setDropTarget(new DropTarget() {
				private static final long serialVersionUID = 1L;

				@Override
				public synchronized void drop(DropTargetDropEvent dtde) {
					dtde.dropComplete(true);
					dropCreateMenu.show(com, dtde.getLocation().x, dtde.getLocation().y);
				}
			});
		}
	}

	private void swingComBefore() {
		com.setOpaque(false);
		// 删除控件原有的鼠标事件
		for (MouseListener l : com.getMouseListeners()) {
			com.removeMouseListener(l);
		}
		for (MouseMotionListener l : com.getMouseMotionListeners()) {
			com.removeMouseMotionListener(l);
		}
		// 添加事件
		com.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				end = e.getLocationOnScreen();
				clickCom = com;
				String editorType = CompUtils.getStyleMain().getComType();
				if ((com instanceof IStylePanel) && !"default".equals(editorType)) {
					if ("select".equals(editorType)) {
						editorPnl = (IStylePanel) com;
						showPoint = e.getPoint();
						comboselect.show(com, e.getX(), e.getY());
					} else {
						CompUtils.createCom(e.getX(), e.getY(), (IStylePanel) com, editorType, true);
					}
					CompUtils.getStyleMain().resetComType();
				} else {
					// 左
					if (SwingUtilities.isLeftMouseButton(e)) {
						if (drogged) {
							if (selectPrint) {
								Point startWin = (Point) start.clone();
								Point endWin = (Point) end.clone();
								SwingUtilities.convertPointFromScreen(startWin, CompUtils.getWin());
								SwingUtilities.convertPointFromScreen(endWin, CompUtils.getWin());
								int x = Math.min(startWin.x, endWin.x);
								int y = Math.min(startWin.y, endWin.y);
								int width = Math.abs(endWin.x - startWin.x);
								int height = Math.abs(endWin.y - startWin.y);
								List<IStyleCom> coms = CompUtils.getInterCom(x, y, width, height);
								CompUtils.clearSelect();
								for (IStyleCom c : coms) {
									c.setSelect(true);
								}
								selectPrint = false;
							} else {
								List<IStyleCom> selects = CompUtils.getSelect();
								if (selects != null && !selects.isEmpty()) {
									for (IStyleCom select : selects) {
										select.updatePropsByUI();
										if (select instanceof JScrollPane || select instanceof TNewPanel) {
											// 表格等 大小变化 里面的viewpoint 也扩大
											select.upateUIByProps();
										}
									}
								}
							}
							drogged = false;
						}
					} else {
						Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), CompUtils.getWin());
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						List<String> shows = new ArrayList<String>();
						if (CompUtils.getStyle().getValue("dataType").indexOf("printstyle") >= 0) {
							shows.add("自动转为打印样式");
						}
						if (!selects.isEmpty()) {
							shows.add("复制");
							shows.add("删除");
						}
						if (selects.size() > 1) {
							shows.add("左对齐");
							shows.add("右对齐");
							shows.add("水平居中对齐");
							shows.add("上对齐");
							shows.add("下对齐");
							shows.add("垂直居中对齐");
							shows.add("相同宽(最小为标准)");
							shows.add("相同宽(最大为标准)");
							shows.add("相同高(最小为标准)");
							shows.add("相同高(最大为标准)");
							shows.add("相同间距(纵向)");
							shows.add("相同间距(横向)");

						}
						if (!CompUtils.copysEmpty() && clickCom instanceof IStylePanel) {
							shows.add("粘贴");
						}
						showMenu(point, shows);
					}
				}

				// 鼠标放下时改变对象的显示prodto

			}

			private void showMenu(Point point, List<String> shows) {
				showPoint = point;
				MenuElement[] items = menu.getSubElements();
				for (MenuElement item : items) {
					JMenuItem m = (JMenuItem) item;
					logger.debug(m.getText());
					if (!shows.contains(m.getText())) {
						m.setEnabled(false);
					} else {
						m.setEnabled(true);
					}
				}
				menu.show(CompUtils.getWin(), point.x - 60, point.y - 10);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				start = e.getLocationOnScreen();
				dropStart = start;
				dropCreateMenu.setVisible(false);
				InputSearchPnl.menu.setVisible(false);

				// 左
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (com.equals(CompUtils.getWin())) {
						CompUtils.clearSelect();
						setSelect(true);
						return;
					}
					// 带shit键
					if (CommonUtils.isMac() && e.isMetaDown() || e.isControlDown()) {
						// 是否和选择的控件同一父亲
						if (CompUtils.isBothPanl(com)) {
							setSelect(!isSelect());
						} else {
							GUIUtils.showMsg(MainFrame.getInstance(), "请选择同一平面上的控件");
							return;
						}
					} else {
						if (!isSelect()) {
							CompUtils.clearSelect();
							setSelect(true);
						}
					}
				}
			}

		});

		com.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				drogged = true;
				end = e.getLocationOnScreen();
				String editorType = CompUtils.getStyleMain().getComType();
				int changeX = end.x - dropStart.x;
				int changeY = end.y - dropStart.y;
				// 鼠标向外扩张 则图像变大 改变x ，y 都是 负数
				switch (com.getCursor().getType()) {
				case Cursor.NW_RESIZE_CURSOR:
					com.setBounds(com.getX() + changeX, com.getY() + changeY, com.getWidth() - changeX, com.getHeight() - changeY);
					break;
				case Cursor.N_RESIZE_CURSOR:
					com.setBounds(com.getX(), com.getY() + changeY, com.getWidth(), com.getHeight() - changeY);
					break;
				case Cursor.NE_RESIZE_CURSOR:
					com.setBounds(com.getX(), com.getY() + changeY, com.getWidth() + changeX, com.getHeight() - changeY);
					break;
				case Cursor.W_RESIZE_CURSOR:
					com.setBounds(com.getX() + changeX, com.getY(), com.getWidth() - changeX, com.getHeight());
					break;
				case Cursor.E_RESIZE_CURSOR:
					com.setBounds(com.getX(), com.getY(), com.getWidth() + changeX, com.getHeight());
					break;
				case Cursor.SW_RESIZE_CURSOR:
					com.setBounds(com.getX() + changeX, com.getY(), com.getWidth() - changeX, com.getHeight() + changeY);
					break;
				case Cursor.S_RESIZE_CURSOR:
					com.setBounds(com.getX(), com.getY(), com.getWidth(), com.getHeight() + changeY);
					break;
				case Cursor.SE_RESIZE_CURSOR:
					com.setBounds(com.getX(), com.getY(), com.getWidth() + changeY, com.getHeight() + changeY);
					break;
				default:
					if (ComEum.TNewPanel == getType() && "default".equals(editorType)) {
						paintSelect();
					} else {
						List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
						for (IStyleCom s : selects) {
							JComponent comps = (JComponent) s;
							comps.setLocation(comps.getX() + changeX, comps.getY() + changeY);
						}
					}
					break;
				}
				if (ComEum.TNewPanel == getType()) {
					updatePropsByUI();
				} else {
					List<IStyleCom> selects = CompUtils.getSelect(CompUtils.getWin());
					for (IStyleCom s : selects) {
						s.updatePropsByUI();
					}
				}
				CompUtils.getStyleMain().updateUIPropEditor();
				dropStart = end;
			}

			private void paintSelect() {
				Graphics g = CompUtils.getWin().getGraphics();
				CompUtils.getWin().paint(g);
				Graphics2D g2 = (Graphics2D) g;
				float[] dash = { 5f, 5f };
				g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
				Point startWin = (Point) start.clone();
				Point endWin = (Point) end.clone();
				SwingUtilities.convertPointFromScreen(startWin, CompUtils.getWin());
				SwingUtilities.convertPointFromScreen(endWin, CompUtils.getWin());
				g.drawRect(Math.min(startWin.x, endWin.x), Math.min(startWin.y, endWin.y), Math.abs(endWin.x - startWin.x), Math.abs(endWin.y - startWin.y));
				selectPrint = true;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				Cursor cursor = CompUtils.getCursor(com, e.getLocationOnScreen());
				com.setCursor(cursor);
			}
		});

	}

	@Override
	public PropDto getProp(String key) {
		if (key == null) {
			return null;
		}
		return proMap.get(key.toLowerCase());
	}

	@Override
	public String getPropValue(String key) {
		if (hasPro(key)) {
			key = key.toLowerCase();
			return proMap.get(key).getValue();
		}
		return "";
	}

	@Override
	public ComEum getType() {
		return type;
	}

	@Override
	public boolean hasPro(String key) {
		return proMap.containsKey(key.toLowerCase());
	}

	@Override
	public void init(Element propE) {
		// 兼容去掉的属性
		Map<String, String> ingoreMap = new HashMap<String, String>();
		// 包xml的值填充到控件的 propDtos中 以dtomap为准，即控件的属性定义xml
		List<Element> ps = propE.elements();
		for (Element p : ps) {
			String key = p.getName();
			if (CommonUtils.isStrEmpty(key)) {
				continue;
			}
			key = key.toLowerCase();
			if ("columns".equals(key)) {
				((TNewGrid) com).initColumn(p);
				continue;
			}
			if (proMap.containsKey(key)) {
				proMap.get(key).setValue(p.getText());
			} else {
				if ("hideprintbtn".equals(key) && p.getText().equalsIgnoreCase("true")) {
					ingoreMap.put("hideprintbtn", "true");
				}
			}
		}
		if (ingoreMap.containsKey("hideprintbtn")) {
			String value = proMap.get("hiddenoperabtns").getValue();
			if (value.indexOf("print") == -1) {
				proMap.get("hiddenoperabtns").setValue(value += ",print");
			}
		}
	}

	@Override
	public Element cover2Ele(String name) {
		if (name == null) {
			name = getPropValue("Name");
		}
		Element ele = DocumentHelper.createElement(name);
		ele.addAttribute("classname", getType().toString());
		Element property = ele.addElement("property");
		for (PropDto pdo : proMap.values()) {
			property.addElement(pdo.getPropname()).setText(pdo.getValue());
		}
		return ele;
	}

	@Override
	public void setPropValue(String key, String value) {
		if (hasPro(key)) {
			proMap.get(key.toLowerCase()).setValue(value);
		} else {
			logger.debug("没有属性:" + key);
		}
	}

	@Override
	public void upateUIByProps() {
		if (com == null) {
			return;
		}
		int Width = 0, Height = 0, Left = 0, Top = 0;
		String temp = this.getPropValue("Width");
		if (CommonUtils.isNumberString(temp)) {
			Width = Integer.parseInt(temp);
		}

		temp = this.getPropValue("Height");
		if (CommonUtils.isNumberString(temp)) {
			Height = Integer.parseInt(temp);
		}
		temp = this.getPropValue("Left");
		if (CommonUtils.isNumberString(temp)) {
			Left = Integer.parseInt(temp);
		}

		temp = this.getPropValue("Top");
		if (CommonUtils.isNumberString(temp)) {
			Top = Integer.parseInt(temp);
		}
		temp = this.getPropValue("MyFont");
		if (!CommonUtils.isStrEmpty(temp)) {
			Object[] fontprops = CompUtils.getFontProps(temp);
			com.setForeground((Color) fontprops[1]);
			com.setFont((Font) fontprops[0]);
		} else {
			com.setForeground(ComEum.FONT_DEFAULT_COLOR);
			if (getType() == ComEum.TNewRadioBox || getType() == ComEum.TNewCheckBox) {
				com.setFont(new Font(ComEum.FONT_DEFAULT_FIMALY, Font.PLAIN, 10));
			} else {
				com.setFont(new Font(ComEum.FONT_DEFAULT_FIMALY, Font.PLAIN, ComEum.FONT_DEFAULT_SIZE));
			}

		}
		com.setBounds(Left, Top, Width, Height);
	}

	@Override
	public void updatePropsByUI() {
		if (com == null) {
			return;
		}
		setPropValue("Left", com.getX() + "");
		setPropValue("Top", com.getY() + "");
		setPropValue("Width", com.getWidth() + "");
		setPropValue("Height", com.getHeight() + "");
	}

	@Override
	public boolean isSelect() {
		return select;
	}

	@Override
	public void setSelect(boolean flag) {
		if (this.select == flag) {
			return;
		}
		if (CompUtils.getSelect().isEmpty()) {
			CompUtils.firstSelect = (IStyleCom) com;
		}
		if (!flag) {
			CompUtils.getStyleMain().stopPropTableEditor();
		}
		this.select = flag;
		com.repaint();
		if (flag) {
			com.getParent().setComponentZOrder(com, 0);
			// 设置属性编辑器
			CompUtils.getStyleMain().initPropEditor();
			CompUtils.getStyleMain().updateTree();
		} else {
			if (CompUtils.getSelect().isEmpty()) {
				CompUtils.firstSelect = null;
			}
		}
	}

	public void paintPoint(Graphics g) {
		Rectangle[] rec = new Rectangle[8];
		rec[0] = new Rectangle(0, 0, ponitSize, ponitSize);
		rec[1] = new Rectangle((com.getWidth() - ponitSize) / 2, 0, ponitSize, ponitSize);
		rec[2] = new Rectangle(com.getWidth() - ponitSize, 0, ponitSize, ponitSize);
		rec[3] = new Rectangle(com.getWidth() - ponitSize, (com.getHeight() - ponitSize) / 2, ponitSize, ponitSize);
		rec[4] = new Rectangle(com.getWidth() - ponitSize, com.getHeight() - ponitSize, ponitSize, ponitSize);
		rec[5] = new Rectangle((com.getWidth() - ponitSize) / 2, com.getHeight() - ponitSize, ponitSize, ponitSize);
		rec[6] = new Rectangle(0, com.getHeight() - ponitSize, ponitSize, ponitSize);
		rec[7] = new Rectangle(0, (com.getHeight() - ponitSize) / 2, ponitSize, ponitSize);
		Color c = g.getColor();
		g.setColor(Color.RED);
		for (int i = 0; i < rec.length; i++) {
			if (com.equals(CompUtils.getWin()) && i == 0) {
				continue;
			}
			g.fill3DRect(rec[i].x, rec[i].y, rec[i].width, rec[i].height, false);
		}
		g.setColor(c);
	}

	@Override
	public String toString() {
		String name = getPropValue("Name");
		String cName = getType().getCName();
		String caption = getPropValue("Caption");
		String FieldName = CommonUtils.coverNull(getPropValue("FieldName"));
		if (CommonUtils.isStrEmpty(FieldName)) {
			XMLDto dto = CommonUtils.getXmlDto(CompUtils.getFields(), "FieldName", FieldName);
			if (dto != null) {
				FieldName = dto.getValue("itemlabel") + " " + dto.getValue("itemname");
			}
		}
		return FieldName + " " + PropUtils.null2Empty(name) + "  " + PropUtils.null2Empty(caption) + " (" + PropUtils.null2Empty(cName) + ")";
	}

	@Override
	public Map<String, PropDto> listProp() {
		return this.proMap;
	}

	protected void addUIProp(List<String> names) {
		uiProps.addAll(names);
	}

	@Override
	public boolean isUIProp(String name) {
		return uiProps.contains(name);
	}

	@Override
	public void setParentPnl(IStylePanel parent) {
		this.parent = parent;
	}

	@Override
	public IStylePanel getParentPnl() {
		return parent;
	}

}
