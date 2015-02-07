package youngfriend.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.BatchPrintStyle;
import youngfriend.gui.BatchUpdateComPropPnl;
import youngfriend.gui.CopyStyelPnl;
import youngfriend.gui.InputEditor;
import youngfriend.gui.InputSearchPnl;
import youngfriend.gui.MoveClassPnl;
import youngfriend.gui.NewClassEditor;
import youngfriend.gui.SelectClassPnl;
import youngfriend.gui.ShowLockStatePnl;
import youngfriend.gui.SortCatalogPnl;
import youngfriend.gui.SortClassPnl;
import youngfriend.gui.SortNumStylePnl;
import youngfriend.gui.StyleTreeCellRender;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.Do4objs;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

/**
 * 
 * @author yf
 */
public class LefttreeStylePnl extends javax.swing.JPanel {

	public final static Logger logger = LogManager.getLogger(LefttreeStylePnl.class.getName());

	private static final long serialVersionUID = 1L;

	public static String GetFormatDate(String Format) {
		Date rightNow = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format);
		return simpleDateFormat.format(rightNow);
	}

	private String currentUUID;
	private JScrollPane jScrollPane1;
	private String LOCK_DESCRIPTION = "";

	private javax.swing.JTree tree;
	private DefaultMutableTreeNode root;

	private DefaultTreeModel model;

	private boolean meLock = false;;

	public LefttreeStylePnl() {
		initComponents();
		init();
	}

	private void init() {
		tree = new JTree();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new StyleTreeCellRender());
		this.jScrollPane1.setViewportView(tree);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				treeNodeSwitch();
			}
		});
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0) && (tree.getSelectionCount() > 0)) {
					ShowMenu(event.getX(), event.getY());
				}
			}
		});
		loadTreeData();
	}

	private void treeNodeSwitch() {
		try {
			InputSearchPnl.menu.setVisible(false);
			StyleMainPnl sm = CompUtils.getStyleMain();
			if (sm.isChange()) {
				if (GUIUtils.showConfirm(MainFrame.getInstance(), "样式已改变,保存吗?")) {
					sm.saveStyle();
				}
			}
			CompUtils.setStyle(null);
			sm.release();
			MainFrame.getInstance().clearRightPnl();
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if (node == null) {
				return;
			}
			if (meLock && !CommonUtils.isStrEmpty(currentUUID)) {
				unLock();
			}
			meLock = false;
			currentUUID = "";
			Object obj = node.getUserObject();
			if (!(obj instanceof XMLDto)) {
				return;
			}
			XMLDto style = (XMLDto) obj;
			String dataType = style.getValue("dataType");
			if ("catalog".equals(dataType) || "querystylecatalog".equals(dataType) || "printstylecatalog".equals(dataType)) {
				MainFrame.getInstance().clearRightPnl();
				return;
			}
			CompUtils.setStyle(style);
			List<String> str = new ArrayList<String>();
			LOCK_DESCRIPTION = transListToString(str);
			String uuid = style.getValue("catalogid") + "|" + style.getValue("classid") + "|" + style.getValue("styleid");
			currentUUID = uuid;
			CheckLock();
			if ("class".equals(dataType)) {
				MainFrame.getInstance().switchDsMain();
			} else if (dataType.indexOf("style") >= 0) {// 调用查询样式和打印样式的
				MainFrame.getInstance().sitchStyleMain();
			}
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "转换工作区域出错");
			logger.error(e.getMessage(), e);
		}

	}

	public void loadTreeData() {
		root = new DefaultMutableTreeNode("全部");
		model = new DefaultTreeModel(root);
		CompUtils.buildStyleTree("", root);
		tree.setModel(model);
		tree.updateUI();

	}

	public void rebuilTree() {
		tree.setVisible(false);
		MainFrame.getInstance().busyDoing(new Do4objs() {
			@Override
			public void do4ojbs(Object... obj1) {
				CompUtils.rebuiTree(tree, new Do4objs() {
					@Override
					public void do4ojbs(Object... obj1) {
						if (obj1.length != 1) {
							return;
						}
						if (!(obj1[0] instanceof JTree)) {
							return;
						}
						loadTreeData();
					}
				});

			}
		}, new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				tree.setVisible(true);
			}
		}, true);

	}

	public JTree getTree() {
		return tree;
	}

	// 删除查询类
	private void delClass() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (!GUIUtils.showConfirm(MainFrame.getInstance(), "确定删除选中的查询类？")) {
			return;
		}
		if (node.getChildAt(0).getChildCount() > 0 || node.getChildAt(1).getChildCount() > 0) {
			if (!GUIUtils.showConfirm(MainFrame.getInstance(), "查询类存在样式，确定删除选中的查询类？")) {
				return;
			}
			if (!GUIUtils.showConfirm(MainFrame.getInstance(), "查询类存在样式，确定删除选中的查询类？")) {
				return;
			}
		}

		try {
			XMLDto selcatalog = (XMLDto) node.getUserObject();
			InvokerServiceUtils.delClass(selcatalog.getValue("classid"));
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
			parentNode.remove(node);
			tree.updateUI();
			MainFrame.getInstance().clearRightPnl();
		} catch (Exception ex) {
			GUIUtils.showMsg(MainFrame.getInstance(), "删除错误");
			logger.error(ex.getMessage(), ex);
		}

	}

	private void copyClass() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (!GUIUtils.showConfirm(MainFrame.getInstance(), "确定复制查询类？")) {
			return;
		}
		try {
			XMLDto select = (XMLDto) node.getUserObject();
			String classid = InvokerServiceUtils.copyClass(select.getValue("classid"));
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
			DefaultMutableTreeNode newNode = CompUtils.cloneNode(node);
			CompUtils.addstyleCatalogs(newNode);
			XMLDto obj = (XMLDto) newNode.getUserObject();
			obj.setValue("id", classid);
			obj.setValue("classid", classid);
			obj.setValue("name", "复制" + obj.getValue("name"));
			DefaultMutableTreeNode queryNode = (DefaultMutableTreeNode) node.getFirstChild();
			DefaultMutableTreeNode newqueryNode = (DefaultMutableTreeNode) newNode.getFirstChild();
			DefaultMutableTreeNode printNode = (DefaultMutableTreeNode) node.getLastChild();
			DefaultMutableTreeNode newprintNode = (DefaultMutableTreeNode) newNode.getLastChild();
			int queryIndex = 0, printIndex = 0;
			if (queryNode.getChildCount() > 0 || printNode.getChildCount() > 0) {
				String xml = InvokerServiceUtils.getClassStyleLst(classid);
				if (!CommonUtils.isStrEmpty(xml)) {
					List<Element> eles = DocumentHelper.parseText(xml).getRootElement().selectNodes("style2");
					if (eles != null && !eles.isEmpty()) {
						List<XMLDto> list = CommonUtils.coverEles(eles, new String[] { "id", "classid", "name", "type", "sortnumber" }, null, new String[] { "name" }, null, null, null);
						for (XMLDto dto : list) {
							DefaultMutableTreeNode tempNode = null;
							DefaultMutableTreeNode newStyleNode = null;
							if ("P".equalsIgnoreCase(dto.getValue("type"))) {
								tempNode = (DefaultMutableTreeNode) printNode.getChildAt(printIndex);
								printIndex++;
								newStyleNode = CompUtils.cloneNode(tempNode);
								newprintNode.add(newStyleNode);
							} else {
								tempNode = (DefaultMutableTreeNode) queryNode.getChildAt(queryIndex);
								queryIndex++;
								newStyleNode = CompUtils.cloneNode(tempNode);
								newqueryNode.add(newStyleNode);
							}
							if (newStyleNode != null) {
								XMLDto tempObj = (XMLDto) newStyleNode.getUserObject();
								tempObj.setValue("id", dto.getValue("id"));
								tempObj.setValue("styleid", dto.getValue("id"));
								tempObj.setValue("classid", dto.getValue("classid"));
							}
						}
					}
				}
			}
			parentNode.add(newNode);
			tree.updateUI();
			MainFrame.getInstance().clearRightPnl();
		} catch (Exception ex) {
			GUIUtils.showMsg(MainFrame.getInstance(), "复制错误");
			logger.error(ex.getMessage(), ex);
		}
	}

	// 删除 目录
	private void deleteCatalog() {
		TreePath tp = tree.getSelectionPath();
		if (tp == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		if (node.getChildCount() > 0) {
			GUIUtils.showMsg(MainFrame.getInstance(), "请删除底下节点");
			return;
		}
		if (!GUIUtils.showConfirm(MainFrame.getInstance(), "确定删除选中的目录？")) {
			return;
		}
		List<XMLDto> all = new ArrayList<XMLDto>();
		getChildCatalog(node, all);
		try {
			for (int i = all.size() - 1; i >= 0; i--) {
				XMLDto obj = all.get(i);
				InvokerServiceUtils.delCatalog(obj.getValue("catalogid"));
			}
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
			parentNode.remove(node);
			DefaultTreeModel model = ((DefaultTreeModel) tree.getModel());
			TreeNode[] nodes = model.getPathToRoot(parentNode);
			TreePath path = new TreePath(nodes);
			tree.scrollPathToVisible(path);
			tree.setSelectionPath(path);
			tree.updateUI();
			MainFrame.getInstance().clearRightPnl();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "未知错误");
			logger.error(e.getMessage(), e);
		}
	}

	private void getChildCatalog(DefaultMutableTreeNode parent, List<XMLDto> all) {
		XMLDto obj = (XMLDto) parent.getUserObject();
		String dataType = obj.getValue("dataType");
		if (!"catalog".equals(dataType)) {
			return;
		}
		all.add(obj);
		if (parent.getChildCount() <= 0) {
			return;
		}
		Enumeration<DefaultMutableTreeNode> children = parent.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode c = children.nextElement();
			getChildCatalog(c, all);
		}
	}

	// 删除样式
	private void delStyleInfo() {
		TreePath tp = tree.getSelectionPath();
		if (tp == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		XMLDto flow = (XMLDto) node.getUserObject();
		if (!GUIUtils.showConfirm(MainFrame.getInstance(), "确定删除选中的查询分析样式？")) {
			return;
		}

		try {
			InvokerServiceUtils.delStyle(flow.getValue("styleid"));
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
			parentNode.remove(node);
			DefaultTreeModel model = ((DefaultTreeModel) tree.getModel());
			TreeNode[] nodes = model.getPathToRoot(parentNode);
			TreePath path = new TreePath(nodes);
			tree.scrollPathToVisible(path);
			tree.setSelectionPath(path);
			tree.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "未知错误");
			logger.error(e.getMessage(), e);
		}
	}

	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
		add(jScrollPane1);
	}

	// 跨目录移动查询类
	private void moveClass() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		new MoveClassPnl(node).edit(MainFrame.getInstance(), null);
	}

	// 移动样式
	private void moveStyleInfo() {
		try {
			XMLDto style = CompUtils.getStyle();
			if (style == null) {
				GUIUtils.showMsg(MainFrame.getInstance(), "查询样式没选择");
				return;
			}
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if (node == null) {
				return;
			}
			XMLDto sNode = (XMLDto) node.getUserObject();
			if (CommonUtils.isStrEmpty(sNode.getValue("styleid"))) {
				GUIUtils.showMsg(MainFrame.getInstance(), "请先保存样式");
				return;
			}
			DefaultMutableTreeNode classNode = (DefaultMutableTreeNode) node.getParent().getParent();
			XMLDto classObj = (XMLDto) classNode.getUserObject();
			SelectClassPnl pnl = new SelectClassPnl(classObj);
			pnl.edit(SwingUtilities.getWindowAncestor(this), null);
			if (!pnl.isSubmit()) {
				return;
			}
			XMLDto sel = pnl.getSelect();
			if (sel == null) {
				return;
			}
			if (classObj.getValue("id").equals(sel.getValue("classid"))) {
				return;
			}
			classNode = CompUtils.getTreeNode(root, "id", sel.getValue("classid"));
			if (classNode == null) {
				GUIUtils.showMsg(MainFrame.getInstance(), "找不到查询类");
				return;
			}
			classObj = (XMLDto) classNode.getUserObject();
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) classNode.getChildAt("查询样式".equals(node.getParent().toString()) ? 0 : 1);
			InvokerServiceUtils.moveStyle(style.getValue("styleid"), classObj.getValue("classid"));
			XMLDto userObject = InvokerServiceUtils.getStyleInfoById(style.getValue("styleid"));
			userObject.setValue("styleid", style.getValue("styleid"));
			userObject.setValue("dataType", style.getValue("dataType"));
			userObject.setValue("stylename", userObject.getValue("name"));
			((DefaultMutableTreeNode) node.getParent()).remove(node);
			DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(userObject);
			parentNode.add(newChild);
			TreePath tp = new TreePath(model.getPathToRoot(newChild));
			tree.setSelectionPath(tp);
			tree.expandPath(tp);
			tree.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "移动错误");
			logger.error(e.getMessage(), e);
		}

	}

	// 新增目录(A新增平级目录，V新增下级目录)
	private void newCatalog(String catalogType) {
		TreePath tp = tree.getSelectionPath();
		tree.expandPath(tp);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		String addCatalogTypeName = "新增下级目录", parentSortcode = "";
		if ("A".equalsIgnoreCase(catalogType)) {// 平级要获取父菜单
			addCatalogTypeName = "新增平级目录";
			node = (DefaultMutableTreeNode) node.getParent();
			if (null == node) {
				node = (DefaultMutableTreeNode) tp.getLastPathComponent();
			}
		}
		if (!node.isRoot()) {
			XMLDto selcatalog = (XMLDto) node.getUserObject();
			parentSortcode = selcatalog.getValue("catalogcode");
		}
		InputEditor pnl = new InputEditor(new Validate<String>() {

			@Override
			public String validate(String obj) {
				if (CommonUtils.isStrEmpty(obj) && CommonUtils.isStrEmpty(obj.trim())) {
					return "不能为空";
				}
				return null;
			}
		});
		Map<String, String> props = new HashMap<String, String>();
		props.put("title", addCatalogTypeName);
		pnl.edit(SwingUtilities.getWindowAncestor(this), props);
		if (!pnl.isSubmit()) {
			return;
		}
		try {
			String name = props.get("value");
			Map<String, String> newcatalog = InvokerServiceUtils.newCatalog("", parentSortcode, name, "");
			XMLDto temp = new XMLDto("name");
			temp.setValue("catalogid", newcatalog.get("id"));
			temp.setValue("catalogcode", newcatalog.get("code"));
			temp.setValue("name", name);
			temp.setValue("dataType", "catalog");
			temp.setValue("id", newcatalog.get("id"));
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(temp);
			node.add(newNode);
			DefaultTreeModel model = ((DefaultTreeModel) tree.getModel());
			TreeNode[] nodes = model.getPathToRoot(newNode);
			TreePath path = new TreePath(nodes);
			tree.scrollPathToVisible(path);
			tree.setSelectionPath(path);
			tree.updateUI();
		} catch (Exception e) {
			GUIUtils.showConfirm(MainFrame.getInstance(), "保存目录信息出错");
			logger.error(e.getMessage(), e);
		}

	}

	// 新增查询累
	private void newClass() {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		XMLDto parent = (XMLDto) node.getUserObject();
		if (!"catalog".equals(parent.getValue("dataType"))) {
			GUIUtils.showMsg(MainFrame.getInstance(), "请在目录下添加");
			return;
		}
		String catalogid = parent.getValue("catalogid");
		String catalogname = parent.getValue("name");
		if (CommonUtils.isStrEmpty(catalogid)) {
			GUIUtils.showMsg(MainFrame.getInstance(), "目录id为空");
			return;
		}
		NewClassEditor editor = new NewClassEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("catalogname", catalogname);
		editor.edit(MainFrame.getInstance(), props);
		if (!editor.isSubmit()) {
			return;
		}
		String name = props.get("name");
		String alias = props.get("alias");
		String description = props.get("description");

		Element root = DocumentHelper.createElement("root");
		Element classes = root.addElement("classes");
		classes.addElement("id").setText("");
		classes.addElement("name").setText(name);
		classes.addElement("alias").setText(alias);
		classes.addElement("catalogid").setText(catalogid);
		classes.addElement("description").setText(description);
		classes.addElement("version").setText("");
		try {
			String classid = InvokerServiceUtils.updateClass("", root.asXML());
			XMLDto classInfo = new XMLDto("name");
			classInfo.setValue("dataType", "class");
			classInfo.setValue("classid", classid);
			classInfo.setValue("classname", name);
			classInfo.setValue("id", classid);
			classInfo.setValue("name", name);
			classInfo.setValue("classalias", alias);
			classInfo.setValue("classdesc", description);
			classInfo.setValue("dataType", "class");
			classInfo.setValue("catalogid", catalogid);
			classInfo.setValue("catalogname", catalogname);
			classInfo.setValue("catalogdesc", parent.getValue("catalogdesc"));
			CompUtils.setStyle(classInfo);
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(classInfo);
			CompUtils.addstyleCatalogs(child);
			node.add(child);
			tree.updateUI();
			selectNode(child);
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "保存出错");
			logger.error(e.getMessage(), e);
		}

	}

	private void copyStyle() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		XMLDto obj = (XMLDto) node.getUserObject();
		String type = obj.getValue("type");
		if (!("P".equals(type) || "C".equals(type) || "R".equals(type))) {
			return;
		}
		new CopyStyelPnl(tree, node);
	}

	/**
	 * 新增样式
	 * 
	 * @param type
	 * 
	 *            打印样式：P； 条件样式：C； 结果样式：R；
	 */
	private void newStyleInfo(String type) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		Map<String, DefaultMutableTreeNode> parentNode = new HashMap<String, DefaultMutableTreeNode>();
		XMLDto selcatalog = (XMLDto) node.getUserObject();

		XMLDto cxyscatalog = null;// 查询样式目录
		XMLDto dyyscatalog = null;// 打印样式目录

		// 先判断 该目录是不是 查询样式或者打印样式
		if ("查询样式".equals(selcatalog.getValue("name")) || "打印样式".equals(selcatalog.getValue("name"))) {
			parentNode.put("parentNode", node);
		} else {
			// 判断 是否有 查询样式和打印样式这两个目录，如果没有就先生成这两个目录
			int childCount = node.getChildCount();

			for (int i = 0; i < childCount; i++) {
				DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
				XMLDto chobj = (XMLDto) childNode.getUserObject();
				if ("catalog".equals(chobj)) {
					if ("查询样式".equals(chobj.getValue("name"))) {
						cxyscatalog = chobj;
						if (type.equalsIgnoreCase("C") || type.equalsIgnoreCase("R")) {
							parentNode.put("parentNode", childNode);
						}

					} else if ("打印样式".equals(chobj.getValue("name"))) {
						dyyscatalog = chobj;
						if (type.equalsIgnoreCase("P")) {
							parentNode.put("parentNode", childNode);
						}
					}
				}
			}
			XMLDto nodeInfo = (XMLDto) node.getUserObject();
			if (null == cxyscatalog) {// 查询样式为空，需要创建查询样式目录
				XMLDto tmpInfo = new XMLDto("name");
				tmpInfo.setValue("dataType", "querystylecatalog");
				tmpInfo.setValue("name", "查询样式");
				tmpInfo.setValue("classid", nodeInfo.getValue("classid"));
				tmpInfo.setValue("classname", nodeInfo.getValue("classname"));
				tmpInfo.setValue("catalogid", nodeInfo.getValue("catalogid"));
				tmpInfo.setValue("catalogcode", nodeInfo.getValue("catalogcode"));
				tmpInfo.setValue("catalogname", nodeInfo.getValue("catalogname"));
				DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(tmpInfo);
				node.add(tmpNode);
				DefaultTreeModel model = ((DefaultTreeModel) tree.getModel());
				TreeNode[] nodes = model.getPathToRoot(tmpNode);
				TreePath path = new TreePath(nodes);
				tree.scrollPathToVisible(path);
				tree.setSelectionPath(path);
				tree.updateUI();
				if (type.equalsIgnoreCase("C") || type.equalsIgnoreCase("R")) {
					parentNode.put("parentNode", tmpNode);
				}
			}
			if (null == dyyscatalog) {// 打印样式为空，需要创建打印样式目录
				XMLDto tmpInfo = new XMLDto("name");
				tmpInfo.setValue("dataType", "printstylecatalog");
				tmpInfo.setValue("name", "打印样式");
				tmpInfo.setValue("classid", nodeInfo.getValue("classid"));
				tmpInfo.setValue("classname", nodeInfo.getValue("classname"));
				tmpInfo.setValue("catalogid", nodeInfo.getValue("catalogid"));
				tmpInfo.setValue("catalogcode", nodeInfo.getValue("catalogcode"));
				tmpInfo.setValue("catalogname", nodeInfo.getValue("catalogname"));
				DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode(tmpInfo);
				node.add(tmpNode);
				DefaultTreeModel model = ((DefaultTreeModel) tree.getModel());
				TreeNode[] nodes = model.getPathToRoot(tmpNode);
				TreePath path = new TreePath(nodes);
				tree.scrollPathToVisible(path);
				tree.setSelectionPath(path);
				tree.updateUI();
				if (type.equalsIgnoreCase("P")) {
					parentNode.put("parentNode", tmpNode);
				}
			}

		}

		String typeName = "", dataType = null;

		if (type.equalsIgnoreCase("C")) {
			typeName = "新增条件样式";
			dataType = "condistyle";
		} else if (type.equalsIgnoreCase("R")) {
			typeName = "新增结果样式";
			dataType = "resultstyle";
		} else if (type.equalsIgnoreCase("P")) {
			typeName = "新增打印样式";
			dataType = "printstyle";
		}

		DefaultMutableTreeNode addNode = parentNode.get("parentNode");

		XMLDto addCatalog = (XMLDto) addNode.getUserObject();
		InputEditor pnl = new InputEditor(new Validate<String>() {

			@Override
			public String validate(String obj) {
				if ("新增条件样式".equals(obj) || "新增结果样式".equals(obj) || "新增打印样式".equals(obj)) {
					return "请修改样式名";
				}
				return null;
			}
		});
		Map<String, String> props = new HashMap<String, String>();
		props.put("title", typeName);
		props.put("value", typeName);
		pnl.edit(SwingUtilities.getWindowAncestor(this), props);
		if (!pnl.isSubmit()) {
			return;
		}
		try {
			String name = props.get("value");
			XMLDto style = new XMLDto("name");
			String classid = addCatalog.getValue("classid");
			style.setValue("classid", classid);
			style.setValue("type", type);
			style.setValue("styleid", "");
			style.setValue("dataType", dataType);
			style.setValue("name", name);
			style.setValue("stylename", name);
			style.setValue("sortnumber", InvokerServiceUtils.getMaxSortNumber(classid));
			String newid = InvokerServiceUtils.newStyle(style, null);
			style.setValue("styleid", newid);
			style.setValue("id", newid);
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(style);
			addNode.add(newNode);
			DefaultTreeModel model = ((DefaultTreeModel) tree.getModel());
			TreeNode[] nodes = model.getPathToRoot(newNode);
			TreePath path = new TreePath(nodes);
			tree.scrollPathToVisible(path);
			tree.setSelectionPath(path);
			tree.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "未知错误");
			logger.error(e.getMessage(), e);
		}
	}

	private void ShowMenu(int x, int y) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem addCondiStyleItem = new JMenuItem("新增条件样式");
		JMenuItem addstyleItem = new JMenuItem("新增结果样式");
		JMenuItem addprintItem = new JMenuItem("新增打印样式");

		JMenuItem updatestyleItem = new JMenuItem("修改样式名称");
		JMenuItem delstyleItem = new JMenuItem("删除样式");
		JMenuItem movestyleItem = new JMenuItem("移动样式");
		JMenuItem copystyleItem = new JMenuItem("复制样式");

		JMenuItem sortStyleItem = new JMenuItem("调整样式顺序");

		JMenuItem delClassItem = new JMenuItem("删除查询类");
		JMenuItem addClassItem = new JMenuItem("新增查询类");
		JMenuItem sortClassItem = new JMenuItem("调整查询类顺序");
		JMenuItem moveClassItem = new JMenuItem("移动查询类");
		JMenuItem copyClassItem = new JMenuItem("复制查询类");

		JMenuItem updateCatalog = new JMenuItem("修改目录名称");
		JMenuItem deleteCatalog = new JMenuItem("删除目录");
		JMenuItem sortCatalog = new JMenuItem("调整目录顺序");
		JMenuItem addSameLevelCatalog = new JMenuItem("新增平级目录");
		JMenuItem addLowerCatalog = new JMenuItem("新增下级目录");
		JMenuItem collTree = new JMenuItem("折叠树所有节点");
		JMenuItem refleshTree = new JMenuItem("刷新树");
		JMenuItem batchPrintStyle = new JMenuItem("批量生成打印样式");

		JMenuItem batchUpdateComProp = new JMenuItem("批量修改当前目录下的样式属性");

		TreePath path = tree.getSelectionPath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		Object obj = node.getUserObject();
		if (obj instanceof String) {
			// 根节点
			popupMenu.add(addLowerCatalog);
			popupMenu.add(batchUpdateComProp);
			popupMenu.addSeparator();// 分割符
			popupMenu.add(refleshTree);
		} else if (obj instanceof XMLDto) {
			// 目录节点
			XMLDto treeNode = (XMLDto) obj;
			if ("catalog".equals(treeNode.getValue("dataType"))) {// 目录
				popupMenu.add(updateCatalog);
				popupMenu.add(addSameLevelCatalog);
				popupMenu.add(addLowerCatalog);
				popupMenu.add(deleteCatalog);
				popupMenu.add(sortCatalog);
				popupMenu.addSeparator();// 分割符
				popupMenu.add(addClassItem);
				popupMenu.add(batchUpdateComProp);
				popupMenu.addSeparator();// 分割符
				popupMenu.add(collTree);
				popupMenu.add(refleshTree);
			} else if ("class".equals(treeNode.getValue("dataType"))) {// 查询类
				popupMenu.add(delClassItem);
				popupMenu.add(copyClassItem);
				popupMenu.add(sortClassItem);
				popupMenu.add(moveClassItem);
				popupMenu.addSeparator();// 分割符
				popupMenu.add(collTree);
				popupMenu.add(refleshTree);
			} else if ("querystylecatalog".equals(treeNode.getValue("dataType"))) {// 查询样式目录
				popupMenu.add(addCondiStyleItem);
				popupMenu.add(addstyleItem);
				popupMenu.add(sortStyleItem);
				popupMenu.addSeparator();// 分割符
				popupMenu.add(batchPrintStyle);
				popupMenu.add(collTree);
				popupMenu.add(refleshTree);

			} else if ("printstylecatalog".equals(treeNode.getValue("dataType"))) {// 打印样式目录
				popupMenu.add(addprintItem);
				popupMenu.add(sortStyleItem);
				popupMenu.addSeparator();// 分割符
				popupMenu.add(collTree);
				popupMenu.add(refleshTree);
			} else {// 样式结点
				popupMenu.add(updatestyleItem);
				popupMenu.add(delstyleItem);
				popupMenu.add(movestyleItem);
				popupMenu.add(copystyleItem);
				popupMenu.addSeparator();// 分割符
				popupMenu.add(collTree);
				popupMenu.add(refleshTree);
			}

		}
		popupMenu.show(tree, x, y);

		// 批量生成打印样式
		batchPrintStyle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null) {
					return;
				}
				List<XMLDto> dtos = new ArrayList<XMLDto>();
				Enumeration<DefaultMutableTreeNode> children = node.children();
				while (children.hasMoreElements()) {
					DefaultMutableTreeNode cNode = children.nextElement();
					XMLDto dto = (XMLDto) cNode.getUserObject();
					if ("resultstyle".equals(dto.getValue("dataType")) && !CommonUtils.isStrEmpty(dto.getValue("name"), true)) {
						dtos.add(dto);
					}
				}
				ValueEditor pnl = new BatchPrintStyle(dtos);
				Map<String, String> props = new HashMap<String, String>();
				pnl.edit(MainFrame.getInstance(), props);
				if (pnl.isSubmit()) {
					XMLDto temp = dtos.get(0);
					String classid = temp.getValue("classid");
					String classname = temp.getValue("classname");
					String catalogid = temp.getValue("catalogid");
					String catalogcode = temp.getValue("catalogcode");
					String catalogname = temp.getValue("catalogname");
					DefaultMutableTreeNode printParent = node.getNextSibling();
					String[] ids = props.get("ids").split(",");
					String[] names = props.get("names").split(",");
					for (int i = 0; i < ids.length; i++) {
						String id = ids[i];
						XMLDto tmpInfo = new XMLDto("name");
						tmpInfo.setValue("dataType", "printstyle");
						tmpInfo.setValue("name", names[i]);
						tmpInfo.setValue("id", id);
						tmpInfo.setValue("styleid", id);
						tmpInfo.setValue("classid", classid);
						tmpInfo.setValue("classname", classname);
						tmpInfo.setValue("catalogid", catalogid);
						tmpInfo.setValue("catalogcode", catalogcode);
						tmpInfo.setValue("catalogname", catalogname);
						DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(tmpInfo);
						printParent.add(newChild);
					}
					updateTreeUI();
				}
			}
		});

		// 添加平级目录事件
		addSameLevelCatalog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
				newCatalog("A");
			}
		});
		// 添加下级目录事件
		addLowerCatalog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
				newCatalog("V");
			}
		});

		// 修改 目录
		updateCatalog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
				updataCatalog();
			}
		});

		// 删除目录
		deleteCatalog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
				deleteCatalog();
			}
		});

		// 调整目录
		sortCatalog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
				sortCatalog();
			}
		});
		batchUpdateComProp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String catalogid = "", catalogName = "根目录";
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (!node.isRoot()) {
					Object obj = node.getUserObject();
					if (!(obj instanceof XMLDto)) {
						return;
					}
					XMLDto dto = (XMLDto) obj;
					if (!"catalog".equals(dto.getValue("dataType"))) {
						return;
					}
					catalogid = dto.getValue("id");
					catalogName = dto.getValue("name");
				}
				new BatchUpdateComPropPnl(catalogid, catalogName);
			}
		});
		// 刷新树

		refleshTree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rebuilTree();
			}
		});
		collTree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CompUtils.collapseTreeLevel1(tree, root);
				tree.expandRow(0);
				tree.updateUI();
			}
		});
		copystyleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copyStyle();
			}
		});
		// 添加条件样式
		addCondiStyleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				newStyleInfo("C");
			}
		});
		// 添加结果样式
		addstyleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				newStyleInfo("R");
			}
		});
		// 添加 打印样式
		addprintItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
				newStyleInfo("P");
			}
		});
		updatestyleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				updateStyleInfo();
			}
		});

		// 删除样式
		delstyleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				delStyleInfo();
			}
		});

		// 移动样式
		movestyleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				moveStyleInfo();
			}
		});
		sortStyleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sortStyle();
			}
		});
		// 新增查询类
		addClassItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newClass();
			}
		});

		// 删除查询类
		delClassItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delClass();
			}
		});
		copyClassItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copyClass();

			}
		});

		// 调整顺序查询类
		sortClassItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sortClass();
			}
		});

		// 跨目录移动查询类
		moveClassItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
				moveClass();
			}
		});
	}

	// 调整目录
	private void sortCatalog() {
		TreePath tp = tree.getSelectionPath();
		if (tp == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		XMLDto catalog = (XMLDto) node.getUserObject();
		if (CommonUtils.isStrEmpty(catalog.getValue("catalogid"))) {
			GUIUtils.showMsg(MainFrame.getInstance(), "请先保存目录");
			return;
		}
		SortCatalogPnl pnl = new SortCatalogPnl(catalog.getValue("catalogid"));
		if (pnl.isOk()) {
			String selid = pnl.getSelid();
			String sortType = pnl.getSortType();
			try {
				InvokerServiceUtils.sortCatalog(catalog.getValue("catalogid"), selid, sortType);
				rebuilTree();
			} catch (Exception e) {
				GUIUtils.showMsg(MainFrame.getInstance(), "调整目录信息出错");
				logger.error(e.getMessage(), e);
			}
		}
	}

	// 调整类顺序
	private void sortClass() {
		TreePath tp = tree.getSelectionPath();
		if (tp == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
		if (parent.getChildCount() < 2) {
			return;
		}
		new SortClassPnl(MainFrame.getInstance(), parent);
	}

	// 调整样式顺序
	private void sortStyle() {
		tree.getSelectionPath();
		TreePath tp = tree.getSelectionPath();
		if (tp == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		new SortNumStylePnl(MainFrame.getInstance(), node, tree);
	}

	public String transListToString(List<String> list) {
		String str = "";
		for (int i = list.size() - 1; i >= 0; i--) {
			String temp = list.get(i);
			if (CommonUtils.isStrEmpty(temp)) {
				continue;
			}
			if (!CommonUtils.isStrEmpty(str)) {
				str += "\\";
			}
			str += temp;
		}
		return str;
	}

	// 除了自己还有人吗
	public boolean CheckLock() {
		try {
			String sql = "SELECT UUID,DESCRIPTION,COMPUTER_NAME,COMPUTER_IP,OPEN_TIME,CLOSE_TIME,STATE FROM CUSTOMQUERY_LOCK WHERE UUID='" + currentUUID + "' AND STATE='1' " + " AND COMPUTER_IP<>'" + CommonUtils.getIp() + "'";
			String xml = InvokerServiceUtils.querySql(sql);
			if (CommonUtils.isStrEmpty(xml)) {
				Lock();
				return true;
			}
			Element ele = (Element) DocumentHelper.parseText(xml).selectSingleNode("//querydata");
			if (ele == null) {
				Lock();
				return true;
			}
			XMLDto lock = CommonUtils.coverEle(ele, Arrays.asList(new String[] { "uuid", "computer_name", "computer_ip", //
					"open_time", "close_time", "state", "description" }), Arrays.asList(new String[] { "uuid" }),//
					null, null, null, null);

			ShowLockStatePnl pnl = new ShowLockStatePnl(lock);
			pnl.edit(MainFrame.getInstance(), null);
			if (pnl.isSubmit()) {
				unLock();
				Lock();
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public void unLock() throws Exception {
		String sql = " UPDATE CUSTOMQUERY_LOCK SET STATE='0',CLOSE_TIME='" + GetFormatDate("yyyy-MM-dd HH:mm:ss") + "' WHERE UUID='" + currentUUID + "'";
		InvokerServiceUtils.executeSql(sql);
	}

	public void Lock() throws Exception {
		// 插入当前数据
		String sql = "DELETE FROM CUSTOMQUERY_LOCK WHERE  UUID='" + currentUUID + "' ";
		InvokerServiceUtils.executeSql(sql);
		// 插入当前数据
		sql = "INSERT INTO CUSTOMQUERY_LOCK(UUID,DESCRIPTION,COMPUTER_NAME,COMPUTER_IP,OPEN_TIME) VALUES ('" + currentUUID + "','" + LOCK_DESCRIPTION + "','" + CommonUtils.getHostName() + "','" + CommonUtils.getIp() + "','" + GetFormatDate("yyyy-MM-dd HH:mm:ss") + "')";
		InvokerServiceUtils.executeSql(sql);
		meLock = true;
	}

	// 修改目录
	private void updataCatalog() {
		TreePath tp = tree.getSelectionPath();
		if (tp == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		XMLDto selcatalog = (XMLDto) node.getUserObject();
		String parentSortcode = "";
		InputEditor pnl = new InputEditor(new Validate<String>() {

			@Override
			public String validate(String obj) {
				if (CommonUtils.isStrEmpty(obj) && CommonUtils.isStrEmpty(obj.trim())) {
					return "不能为空";
				}
				return null;
			}
		});
		Map<String, String> props = new HashMap<String, String>();
		props.put("title", "修改目录");
		props.put("value", selcatalog.getValue("catalogname"));
		pnl.edit(SwingUtilities.getWindowAncestor(this), props);
		if (!pnl.isSubmit() || !pnl.isChange()) {
			return;
		}
		try {
			String name = props.get("value");
			InvokerServiceUtils.newCatalog(selcatalog.getValue("catalogid"), parentSortcode, name, selcatalog.getValue("catalogdesc"));
			selcatalog.setValue("name", name);
			selcatalog.setValue("catalogname", name);
			tree.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "保存目录信息出错");
			logger.error(e.getMessage(), e);
		}
	}

	private void updateStyleInfo() {
		TreePath tp = tree.getSelectionPath();
		if (tp == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		XMLDto style = (XMLDto) node.getUserObject();
		InputEditor pnl = new InputEditor(null);
		Map<String, String> props = new HashMap<String, String>();
		props.put("title", "修改样式名称");
		props.put("width", "35");
		String name = style.getValue("name");
		props.put("value", name);
		pnl.edit(SwingUtilities.getWindowAncestor(this), props);
		if (!pnl.isSubmit() || !pnl.isChange()) {
			return;
		}
		name = props.get("value");
		try {
			InvokerServiceUtils.updateStyle(style.getValue("styleid"), name, "", "", "");
			style.setValue("stylename", name);
			style.setValue("name", name);
			node.setUserObject(style);
			tree.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "未知错误");
			logger.error(e.getMessage(), e);
		}
	}

	public void updateTreeUI() {
		tree.updateUI();
	}

	public void selectNode(DefaultMutableTreeNode child) {
		TreeNode[] rp = model.getPathToRoot(child);
		TreePath path = new TreePath(rp);
		tree.setSelectionPath(path);
		tree.expandPath(path);
		tree.scrollPathToVisible(path);
	}
}
