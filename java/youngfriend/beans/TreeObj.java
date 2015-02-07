/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.beans;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class TreeObj {

	public static volatile Map<TreeIconEnum, ImageIcon> icons = new EnumMap<TreeIconEnum, ImageIcon>(TreeIconEnum.class);

	public static ImageIcon getIcon(TreeIconEnum treeIconEnum) {
		ImageIcon icon = icons.get(treeIconEnum);
		if (icon == null) {
			synchronized (TreeObj.class) {
				if (icon == null) {
					icon = new ImageIcon(treeIconEnum.getPath());
				}
			}
		}
		return icon;
	}

}
