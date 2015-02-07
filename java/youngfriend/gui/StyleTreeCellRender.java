package youngfriend.gui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import youngfriend.beans.TreeIconEnum;
import youngfriend.beans.TreeObj;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;

public class StyleTreeCellRender extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;
	private static final ImageIcon CATALOG_IMG = TreeObj.getIcon(TreeIconEnum.CATALOG_IMG);
	private static final ImageIcon CATALOG_OPEN_IMG = TreeObj.getIcon(TreeIconEnum.CATALOG_OPEN_IMG);
	private static final ImageIcon folder_green = TreeObj.getIcon(TreeIconEnum.FOLDER_GREEN);
	private static final ImageIcon folder_red = TreeObj.getIcon(TreeIconEnum.FOLDER_RED);
	private static final ImageIcon circle_green = TreeObj.getIcon(TreeIconEnum.CIRCLE_GREEN);
	private static final ImageIcon circle_yellow = TreeObj.getIcon(TreeIconEnum.CIRCLE_YELLOW);
	private static final ImageIcon class_Img = TreeObj.getIcon(TreeIconEnum.CLASS_IMG);

	public StyleTreeCellRender() {
		super();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		if (node.isRoot()) {
			if (expanded) {
				setIcon(CATALOG_OPEN_IMG);
			} else {
				setIcon(CATALOG_IMG);
			}
			return this;
		}
		Object obj = node.getUserObject();
		if (!(obj instanceof XMLDto)) {
			return this;
		}
		XMLDto style = (XMLDto) obj;
		String type = style.getValue("dataType");
		if (type.equals("catalog")) {
			if (expanded) {
				setIcon(CATALOG_OPEN_IMG);
			} else {
				setIcon(CATALOG_IMG);
			}
		} else if (type.equals("class")) {
			setIcon(class_Img);
		} else if (type.equals("querystylecatalog")) {
			setIcon(folder_red);
		} else if (type.equals("printstylecatalog")) {
			setIcon(folder_green);
		} else if (type.equals("condistyle")) {
			setIcon(circle_green);
		} else if (type.equals("resultstyle") || type.equals("printstyle")) {
			setIcon(circle_yellow);
		}
		String txt = node.toString();
		if (CommonUtils.isStrEmpty(txt, true)) {
			txt = "                              ";
		}
		setText(txt);
		return this;
	}
}
