/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xiong
 */
public class MyColorFactory {

    private static MyColorFactory Instance = new MyColorFactory();
    private Map<String, Color> colors = new HashMap<String, Color>();
    private Map<String, String> cNames = new HashMap<String, String>();

    private MyColorFactory() {
        colors.put("black", Color.BLACK);
        colors.put("blue", Color.BLUE);
        colors.put("aqua", Color.CYAN);
        colors.put("gray", Color.GRAY);
        colors.put("light_gray", Color.LIGHT_GRAY);
        colors.put("maroon", new Color(176, 48, 96));
        colors.put("orange", Color.ORANGE);
        colors.put("pink", Color.PINK);
        colors.put("red", Color.RED);
        colors.put("white", Color.WHITE);
        colors.put("yellow", Color.YELLOW);
        colors.put("green", Color.GREEN);
        colors.put("olive", new Color(128, 128, 0));
        colors.put("navy", new Color(0, 0, 128));
        colors.put("purple", new Color(128, 0, 128));
        colors.put("silver", new Color(192, 192, 192));
        colors.put("lime", new Color(0, 255, 0));
        colors.put("fuchsia", new Color(255, 0, 255));
        colors.put("teal", new Color(0, 128, 128));

        cNames.put("海军蓝", "navy");
        cNames.put("褐紫色", "maroon");
        cNames.put("紫色", "purple");
        cNames.put("银白色", "silver");
        cNames.put("橄榄色", "olive");
        cNames.put("酸橙色", "lime");
        cNames.put("黑色", "black");
        cNames.put("青色", "teal");
        cNames.put("蓝色", "blue");
        cNames.put("水绿色", "aqua");
        cNames.put("灰色", "gray");
        cNames.put("紫红色", "fuchsia");
        cNames.put("红色", "red");
        cNames.put("白色", "white");
        cNames.put("黄色", "yellow");
        cNames.put("绿色", "green");
    }

    public static MyColorFactory getInstance() {
        return Instance;
    }

    public Color getColorByWebColor(String webColor) {
        return colors.get(webColor);
    }

    public Color getColorByDeliphy(String dColor) {
        if (dColor.startsWith("cl")) {
            return colors.get(dColor.substring(2, dColor.length()).toLowerCase());
        }
        return null;
    }

    public Color getColorByCnName(String cnName) {
        String name = cNames.get(cnName);
        if (name != null) {
            return colors.get(name);
        }
        return null;
    }

    public List<String> getCnNames() {
        return new ArrayList<String>(cNames.keySet());
    }

    public String getDelphiNameByColor(Color color) {
        String deliphiName = "clWindowText";
        for (String key : colors.keySet()) {
            Color c = colors.get(key);
            if (c != null && c.equals(color)) {
                deliphiName = "cl" + key.substring(0, 1).toUpperCase() + key.substring(1);
                return deliphiName;
            }
        }
        return deliphiName;
    }

    public String getCnNameByColor(Color color) {
        for (String key : colors.keySet()) {
            Color c = colors.get(key);
            if (c != null && c.equals(color)) {
                return getCnNameBykey(key);
            }
        }
        return null;
    }

    public String getCnNameBykey(String webName) {

        for (String key : cNames.keySet()) {
            String web = cNames.get(key);
            if (web != null && webName.equals(web)) {
                return key;
            }
        }
        return null;
    }
}
