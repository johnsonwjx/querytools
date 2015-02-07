package youngfriend.coms;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.dom4j.Element;

import youngfriend.beans.PropDto;
import youngfriend.common.util.StringUtils;
import youngfriend.gui.ColumnGroup;
import youngfriend.gui.GroupableTableHeader;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;

@SuppressWarnings({ "unchecked", "unused" })
public class TNewGrid extends JScrollPane implements IStyleCom {
	private static final long serialVersionUID = 1L;
	private DefaultCom defaultCom = null;
	private LinkedHashMap<String, TNewColumn> columnMap = new LinkedHashMap<String, TNewColumn>();
	private Map<Object, TNewColumn> bottomsMap = new HashMap<Object, TNewColumn>();

	public TNewGrid() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		defaultCom = new DefaultCom(this);
	}

	@Override
	public void init(Element propE) {
		defaultCom.init(propE);
	}

	@Override
	public void upateUIByProps() {
		this.getViewport().removeAll();
		JTable table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);
		bottomsMap.clear();
		if (!columnMap.isEmpty()) {
			// 复杂列开始
			// 底部复杂列，添加复杂列时候，要最低下的add this.getColumn(index)
			// bottomGroup.key=index
			List<ColumnTree> columnTrees = new ArrayList<ColumnTree>();
			Map<ColumnTree, List<Integer>> bottomColumns = new HashMap<ColumnTree, List<Integer>>();
			int i = 0;
			for (String cName : columnMap.keySet()) {
				TNewColumn tnewcolumn = columnMap.get(cName);
				String[] headers = cName.split("\\|\\|");
				if (headers.length > 1) {
					List<String> headList = new ArrayList<String>();
					for (String n : headers) {
						headList.add(n);
					}
					ColumnTree parent = null;
					if (columnTrees.size() > 0) {
						parent = columnTrees.get(columnTrees.size() - 1).getChildByNamePaths(null, headList);
					}
					String topName = headList.remove(0);
					ColumnTree tree = null;
					ColumnTree bottomColumn = null;
					if (parent == null) {
						tree = new ColumnTree(topName);
						bottomColumn = tree.buildTree(headList);
						columnTrees.add(tree);
					} else {
						tree = new ColumnTree(topName);
						if (headList.size() > 0) {
							bottomColumn = tree.buildTree(headList);
							parent.setChildren(topName, tree, true);
						} else {
							bottomColumn = parent;
							parent.setChildren(topName, tree, false);

						}
					}
					if (bottomColumn != null) {
						if (bottomColumns.containsKey(bottomColumn)) {
							List<Integer> value = bottomColumns.get(bottomColumn);
							value.add(i);
							bottomColumns.put(bottomColumn, value);
						} else {
							List<Integer> value = new ArrayList<Integer>();
							value.add(i);
							bottomColumns.put(bottomColumn, value);
						}
					}

				}
				String widthStr = tnewcolumn.getPropValue("Width");
				int width = 0;
				if (StringUtils.isNumberString(widthStr)) {
					width = Integer.parseInt(widthStr);
				} else {
					tnewcolumn.setPropValue("Width", "130");
					width = 130;
				}
				TableColumn c = new TableColumn();
				c.setHeaderValue(headers[headers.length - 1]);
				c.setPreferredWidth(width);
				bottomsMap.put(c.getHeaderValue(), tnewcolumn);
				c.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (!"width".equals(evt.getPropertyName())) {
							return;
						}
						TableColumn column = (TableColumn) evt.getSource();
						Object title = column.getHeaderValue();
						TNewColumn columnDto = bottomsMap.get(title);
						if (columnDto != null) {
							if ("false".equalsIgnoreCase(columnDto.getPropValue("visible"))) {
								return;
							}
							if (Integer.parseInt(columnDto.getPropValue("width")) <= 15) {
								return;
							}
							columnDto.setPropValue("width", evt.getNewValue() + "");
						}
					}
				});
				table.addColumn(c);
				i++;
			}
			for (Map.Entry<ColumnTree, List<Integer>> entry : bottomColumns.entrySet()) {
				ColumnTree bottomColumn = entry.getKey();
				for (Integer index : entry.getValue()) {
					TableColumn column = table.getColumnModel().getColumn(index);
					bottomColumn.getMe().add(column);
				}
			}
			GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
			for (ColumnTree top : columnTrees) {
				header.addColumnGroup(top.getMe());
			}
		}
		// 复杂列结束

		int Width = 0, Height = 0, Left = 0, Top = 0;
		String temp = this.getPropValue("Width");
		if (StringUtils.isNumberString(temp)) {
			Width = Integer.parseInt(temp);
		}

		temp = this.getPropValue("Height");
		if (StringUtils.isNumberString(temp)) {
			Height = Integer.parseInt(temp);
		}
		temp = this.getPropValue("Left");
		if (StringUtils.isNumberString(temp)) {
			Left = Integer.parseInt(temp);
		}

		temp = this.getPropValue("Top");
		if (StringUtils.isNumberString(temp)) {
			Top = Integer.parseInt(temp);
		}
		this.setBounds(Left, Top, Width, Height);
		setViewportView(table);
		this.updateUI();
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

	public void initColumn(Element p) {
		columnMap = new LinkedHashMap<String, TNewColumn>();

		List<Element> cls = p.elements();
		if (cls.size() <= 0) {
			return;
		}
		for (Element c : cls) {
			TNewColumn column = new TNewColumn(this);
			column.init(c);
			String title = column.getPropValue("title");
			columnMap.put(title, column);
		}
	}

	public LinkedHashMap<String, TNewColumn> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(LinkedHashMap<String, TNewColumn> columnMap) {
		this.columnMap = columnMap;
	}

	private class ColumnTree {

		private ColumnGroup me;
		private String name;
		private Map<String, ColumnTree> children = new HashMap<String, ColumnTree>();

		public ColumnTree(String name) {
			this.me = new ColumnGroup(name);
			this.name = name;
		}

		public ColumnTree getChildren(String name) {
			return this.children.get(name);
		}

		public void setChildren(String name, ColumnTree child, boolean isGroup) {
			children.put(name, child);
			if (isGroup) {
				me.add(child.getMe());
			}
		}

		public Iterator<String> getKeyIter() {
			return children.keySet().iterator();
		}

		public ColumnGroup getMe() {
			return me;
		}

		public void setMe(ColumnGroup me) {
			this.me = me;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ColumnTree buildTree(List<String> namePaths) {
			if (namePaths.size() < 0) {
				return null;
			}
			ColumnTree tree = this;
			ColumnTree bottomGroup = this;
			boolean isBottom = false;
			while (namePaths.size() > 0) {
				if (namePaths.size() == 2) {
					isBottom = true;
				} else {
					isBottom = false;
				}
				String topname = namePaths.remove(0);
				ColumnTree children = new ColumnTree(topname);
				boolean isGroup = true;
				if (namePaths.size() == 0) {
					isGroup = false;
				}
				tree.setChildren(topname, children, isGroup);
				if (isBottom) {
					bottomGroup = children;
				}
				tree = children;
			}
			return bottomGroup;
		}

		public ColumnTree getChildByNamePaths(ColumnTree result, List<String> namePaths) {
			if (namePaths.size() > 1) {
				String topName = namePaths.get(0);
				if (this.getName().equals(topName)) {
					namePaths.remove(0);
					result = this;
					if (namePaths.size() > 1) {
						topName = namePaths.get(0);
						ColumnTree child = this.getChildren(topName);
						if (child != null) {
							namePaths.remove(0);
							result = child;
							result = child.getChildByNamePaths(child, namePaths);
						}
					}
				}
			}
			return result;
		}
	}

	@Override
	public Element cover2Ele(String name) {
		Element ele = defaultCom.cover2Ele(null);
		Element pEle = ele.element("property");
		Element Columns = pEle.element("Columns");
		if (!columnMap.isEmpty()) {
			int i = 0;
			for (TNewColumn column : columnMap.values()) {
				String cName = "columns_" + i;
				Element cEle = column.cover2Ele(cName);
				Columns.add(cEle);
				i++;
			}
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
