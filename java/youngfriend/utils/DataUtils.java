package youngfriend.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.main.MainFrame;

public class DataUtils {
	public static final Logger logger = LogManager.getLogger(DataUtils.class.getName());
	public static File file = new File("data.xml");
	public static final String rootNode = "styleitems";
	public static final String itemNode = "styleitem";
	public static int maxSize = 10;

	public static void addData(Element styleEle) {
		Document doc = XMLUtils.readFile(file);
		Element root = null;
		if (doc == null) {
			doc = DocumentHelper.createDocument();
			root = DocumentHelper.createElement(rootNode);
			doc.setRootElement(root);
		}
		root = doc.getRootElement();
		List<Element> itemEles = root.elements();
		if (itemEles.isEmpty()) {
			itemEles = new ArrayList<Element>();
		} else if (itemEles.size() >= maxSize) {
			itemEles.subList(itemEles.size() - maxSize + 1, itemEles.size());
		}

		XMLDto style = CompUtils.getStyle();
		Element item = DocumentHelper.createElement(itemNode);
		item.addAttribute("id", style.getValue("styleid"));
		item.addAttribute("name", style.getValue("stylename"));
		item.addAttribute("time", new SimpleDateFormat("MM月dd日HH:mm").format(Calendar.getInstance(Locale.CHINESE).getTime()));
		item.add(styleEle);
		itemEles.add(item);
		root.setContent(itemEles);
		XMLUtils.saveFile(root, file, OutputFormat.createCompactFormat());
	}

	public static void clearDatas() {
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement(rootNode);
		doc.setRootElement(root);
		XMLUtils.saveFile(doc, file, OutputFormat.createCompactFormat());

	}

	public static List<XMLDto> getDatas() {
		Document doc = XMLUtils.readFile(file);
		if (doc == null) {
			return null;
		}
		Element root = doc.getRootElement();
		List<Element> itemEles = root.elements();
		if (itemEles.size() <= 0) {
			return null;
		}
		if (itemEles.size() > maxSize) {
			itemEles = itemEles.subList(itemEles.size() - maxSize, itemEles.size());
		}
		List<XMLDto> datas = new ArrayList<XMLDto>();
		for (Element item : itemEles) {
			List<String> toString = Arrays.asList("name", "id", "time");
			XMLDto dto = new XMLDto(toString);
			dto.setValue("name", item.attributeValue("name"));
			dto.setValue("id", item.attributeValue("id"));
			dto.setValue("time", item.attributeValue("time"));
			Element xml = item.element("root");
			if (xml == null) {
				continue;
			}
			dto.setValue("xml", xml);
			datas.add(dto);
		}
		if (datas.isEmpty()) {
			return null;
		}
		return datas;
	}

	public static Element getSelectData() {
		List<XMLDto> datas = getDatas();
		if (datas == null) {
			GUIUtils.showMsg(MainFrame.getInstance(), "备份为空");
			return null;
		}
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(datas);
		Map<String, String> props = new HashMap<String, String>();
		props.put("width", "500");
		pnl.edit(null, props);
		if (!pnl.isSubmit()) {
			return null;
		}
		XMLDto select = pnl.getSelect();
		if (select == null) {
			return null;
		}
		Element ele = select.getObject(Element.class, "xml");
		return ele;
	}
}
