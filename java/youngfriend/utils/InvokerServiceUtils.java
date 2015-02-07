package youngfriend.utils;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.XMLDto;
import youngfriend.common.util.StringUtils;
import youngfriend.common.util.net.ServiceInvokerUtil;
import youngfriend.common.util.net.exception.ServiceInvokerException;
import youngfriend.main.MainFrame;
import youngfriend.tool.bidserviceutil.ServiceMessageUtil;

public class InvokerServiceUtils {
	public static final Logger logger = LogManager.getLogger(InvokerServiceUtils.class.getName());

	public static void delCatalog(String id) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.catalog.delete");
		tab.put("id", id);
		ServiceInvokerUtil.invoker(tab);
	}

	public static List<XMLDto> getClassItemList(String classId) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.class.get");
		tab.put("classID", classId);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			throw new RuntimeException("获取数据为空");
		}
		Document doc = DocumentHelper.parseText(xml);
		List<Element> eles = doc.selectNodes("//classitem");
		return CommonUtils.coverEles(eles, new String[] { "id", "itemno", "itemname", //
				"itemlabel", "itemtype", "itemdescription", "classid" }, null, new String[] { "itemname", "itemlabel" }, null, null, null);
	}

	public static List<XMLDto> getClassByCatalogId(String catalogid) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.class.get");
		tab.put("catalog", catalogid);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			throw new RuntimeException("获取数据为空");
		}
		Document doc = DocumentHelper.parseText(xml);
		List<Element> eles = doc.selectNodes("//classes");
		return CommonUtils.coverEles(eles, new String[] { "id", "name" }, null, new String[] { "name" }, null, null, null);
	}

	public static List<XMLDto> getCodeTables() {
		try {
			Hashtable<String, String> in = new Hashtable<String, String>();
			in.put("service", "codecenter.table.getlist");
			in.put("hasFields", "false");
			Hashtable<String, String> out = ServiceInvokerUtil.invoker(in);
			String xml = out.get("XML");
			if (CommonUtils.isStrEmpty(xml)) {
				return null;
			}
			Document doc = DocumentHelper.parseText(xml);
			List<Element> eles = doc.getRootElement().elements("codetable");
			return CommonUtils.coverEles(eles, new String[] { "id", "name", "tablename", "alias" }, null, new String[] { "name", "alias" }, null, null, null);
		} catch (Exception e) {
			GUIUtils.showMsg(null, "获取代码中心表格失败");
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static List<XMLDto> getCodeFields(String id) throws Exception {
		Hashtable<String, String> in = new Hashtable<String, String>();
		in.put("service", "codecenter.table.get");// moduleID
		in.put("id", id);
		in.put("hasFields", "true");
		Hashtable<String, String> out = ServiceInvokerUtil.invoker(in);
		String xml = out.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		Element fields = (Element) DocumentHelper.parseText(xml).selectSingleNode("//codefields");
		if (fields == null) {
			return null;
		}
		List<Element> eles = fields.elements("codefield");
		return CommonUtils.coverEles(eles, new String[] { "fieldname", "fieldlabel", "fieldtype", "fielddescription" }, null, new String[] { "fieldname", "fieldlabel" }, null, null, null);
	}

	/**
	 * 得到目录树
	 * 
	 * @param agrs
	 * @throws Exception
	 */
	public static String getReportCatalogXml() throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "report2.catalog.get");
		tab.put("version", "");
		tab.put("id", "");
		tab.put("catalogtype", "0");
		tab.put("accessID", "");
		Hashtable re = ServiceInvokerUtil.invoker(tab);
		return (String) re.get("XML");
	}

	/**
	 * 得到目录树
	 * 
	 * @param reportId
	 * 
	 * @param agrs
	 * @throws Exception
	 */
	public static String getReportFields(String reportId) throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "report2.getaccountstruct");
		tab.put("id", reportId);// 报表流程ID,必传
		Hashtable re = ServiceInvokerUtil.invoker(tab);
		List<XMLDto> result = new ArrayList<XMLDto>();
		String xml = (String) re.get("XML");
		return xml;

	}

	/**
	 * 得到报表树
	 * 
	 * @param agrs
	 * @throws Exception
	 */
	public static String getReportFlowXml() throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "report2.flow.get");
		tab.put("id", "");
		tab.put("hasdata", "F");
		tab.put("prop", "0");
		tab.put("type", "1");
		Hashtable re = ServiceInvokerUtil.invoker(tab);
		return (String) re.get("XML");
	}

	public static String querySql(String sql) throws ServiceInvokerException, Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.simpleQuery");
		tab.put("querysql", sql);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("XML");

		return xml;
	}

	/**
	 * 取目录信息
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public static List<XMLDto> getCatalogList(String id) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.catalog.get");// "customquery.getclassandstyle"
		tab.put("id", id);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("catalog");
		return CommonUtils.coverEles(eles, new String[] { "id", "code", "name" }, null, new String[] { "name" }, null, null, null);
	}

	/**
	 * 调整策分析目录信息
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public static void sortCatalog(String id, String aimid, String sortType) throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "customquery.catalog.sortcode");
		tab.put("id", id);
		tab.put("aimid", aimid);
		tab.put("sortType", sortType);
		ServiceInvokerUtil.invoker(tab);
	}

	public static void executeSql(String sql) throws ServiceInvokerException, Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.execsql");
		tab.put("sql", sql);
		ServiceInvokerUtil.invoker(tab);
	}

	public static String getMaxSortNumber(String classid) throws Exception {
		String sortNumber = "10";
		if (CommonUtils.isStrEmpty(classid)) {
			return sortNumber;
		}
		String xml = InvokerServiceUtils.getClassStyleLst(classid);
		List<Element> lst = DocumentHelper.parseText(xml).getRootElement().selectNodes("style2");
		if (lst == null || lst.isEmpty()) {
			sortNumber = "0";
		} else {
			Element ele = lst.get(lst.size() - 1);
			sortNumber = ele.elementText("sortnumber");
			sortNumber = (Integer.valueOf(sortNumber) + 1) + "";
		}
		return sortNumber;
	}

	/*
	 * 新增样式
	 */
	public static String newStyle(XMLDto style, String xml) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.style2.new");
		tab.put("classID", style.getValue("classid"));
		tab.put("name", style.getValue("name"));
		tab.put("type", style.getValue("type"));
		if (!CommonUtils.isStrEmpty(xml)) {
			tab.put("XML", xml);
		}
		tab.put("sortNumber", style.getValue("sortnumber") == null ? "10" : style.getValue("sortnumber"));
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String retid = re.get("id");
		return retid;
	}

	/*
	 * 跨目录移动查询类
	 */
	public static void moveClass(String catalogId, String classId) throws ServiceInvokerException {
		Hashtable tab = new Hashtable();
		tab.put("service", "customquery.update.moveClass");
		tab.put("catalogId", catalogId);
		tab.put("classId", classId);
		Hashtable re = ServiceInvokerUtil.invoker(tab);
	}

	/**
	 * 删除查询类
	 */
	public static void delClass(String classID) throws ServiceInvokerException {
		Hashtable tab = new Hashtable();
		tab.put("service", "customquery.class.delete");
		tab.put("classID", classID);
		tab.put("includeChild", "true");
		Hashtable re = ServiceInvokerUtil.invoker(tab);
	}

	public static String getClassStyleLst(String classID) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.style2.getlist");
		tab.put("classID", classID);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String retid = re.get("XML");
		return retid;
	}

	/**
	 * 新增修改策分析目录信息
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public static Map<String, String> newCatalog(String id, String fatherSortCode, String name, String description) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.catalog.new");
		tab.put("id", id);
		tab.put("name", name);
		tab.put("description", description);
		tab.put("fatherCode", fatherSortCode);
		return ServiceInvokerUtil.invoker(tab);
	}

	public static void moveStyle(String styleId, String classId) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.update.moveStyle");
		tab.put("styleId", styleId);
		tab.put("classId", classId);
		ServiceInvokerUtil.invoker(tab);
	}

	/*
	 * 调整查询样式顺序
	 */
	public static void sortStyle(String param) throws ServiceInvokerException {
		Hashtable tab = new Hashtable();
		tab.put("service", "customquery.style2.updateSortNumber");
		tab.put("param", param);
		ServiceInvokerUtil.invoker(tab);
	}

	public static Object[] getClassAndcatalogList() throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "customquery.getcatalogandclass");
		Hashtable re = ServiceInvokerUtil.invoker(tab);
		String xml = (String) re.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		List<Element> eles = DocumentHelper.parseText(xml).getRootElement().selectNodes("data");
		List<XMLDto> lst = CommonUtils.coverEles(eles, new String[] { "catalogname", "catalogid", "catalogcode", "classid", "classalias", "classname" }, null, new String[] { "name" }, null, null, null);
		List<XMLDto> catalogs = new ArrayList<XMLDto>();
		List<XMLDto> classes = new ArrayList<XMLDto>();
		for (XMLDto item : lst) {
			if (CommonUtils.isStrEmpty(item.getValue("classid"))) {
				item.remove("classid");
				item.setValue("name", item.getValue("catalogname"));
				item.remove("catalogname");
				item.setValue("type", "catalog");
				catalogs.add(item);
			} else {
				item.setValue("name", item.getValue("classname"));
				item.remove("classname");
				item.setValue("type", "class");
				classes.add(item);
			}
		}
		return new Object[] { catalogs, classes };
	}

	public static List<XMLDto> getTreeAllData(String condi) throws ServiceInvokerException, Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.getclassandstyle");
		if (condi != null && !"".equals(condi)) {
			tab.put("condi", condi);
		}
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("XML");
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("data");
		List<XMLDto> list = CommonUtils.coverEles(eles, new String[] { "catalogid", "catalogcode", "catalogname", "catalogdesc", "classid", "classname",//
				"classalias", "classversion", "classdesc", "styleid", "stylename", "styleversion", "sortnumber", "type" }, null,//
				new String[] { "name" }, null, null, null);

		for (XMLDto dto : list) {
			if (CommonUtils.isStrEmpty(dto.getValue("classid"))) {
				dto.setValue("dataType", "catalog");
				dto.setValue("name", dto.getValue("catalogname"));
				dto.setValue("id", dto.getValue("catalogid"));
			} else if (CommonUtils.isStrEmpty(dto.getValue("styleid"))) {
				dto.setValue("dataType", "class");
				dto.setValue("name", dto.getValue("classname"));
				dto.setValue("id", dto.getValue("classid"));
			} else {
				dto.setValue("name", dto.getValue("stylename"));
				String type = dto.getValue("type");
				dto.setValue("id", dto.getValue("styleid"));
				if (type.equalsIgnoreCase("R")) {
					dto.setValue("dataType", "resultstyle");
				} else if (type.equalsIgnoreCase("C")) {
					dto.setValue("dataType", "condistyle");
				} else {
					dto.setValue("dataType", "printstyle");
				}
			}

		}
		return list;
	}

	public static Object[] getTreeAllArr(String condi) throws ServiceInvokerException, Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.getclassandstyle");
		if (condi != null && !"".equals(condi)) {
			tab.put("condi", condi);
		}
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("XML");
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("data");
		List<XMLDto> list = CommonUtils.coverEles(eles, new String[] { "catalogid", "catalogcode", "catalogname", "catalogdesc", "classid", "classname",//
				"classalias", "classversion", "classdesc", "styleid", "stylename", "styleversion", "sortnumber", "type" }, null,//
				new String[] { "name" }, null, null, null);

		if (list == null || list.isEmpty()) {
			return null;
		}
		List<XMLDto> catalogs = new ArrayList<XMLDto>();
		List<XMLDto> classes = new ArrayList<XMLDto>();
		List<XMLDto> styles = new ArrayList<XMLDto>();
		Object[] result = new Object[] { catalogs, classes, styles };
		for (XMLDto dto : list) {
			if (CommonUtils.isStrEmpty(dto.getValue("classid"))) {
				dto.setValue("dataType", "catalog");
				dto.setValue("name", dto.getValue("catalogname"));
				dto.setValue("id", dto.getValue("catalogid"));
				catalogs.add(dto);
			} else if (CommonUtils.isStrEmpty(dto.getValue("styleid"))) {
				dto.setValue("dataType", "class");
				dto.setValue("name", dto.getValue("classname"));
				dto.setValue("id", dto.getValue("classid"));
				classes.add(dto);
			} else {
				dto.setValue("name", dto.getValue("stylename"));
				String type = dto.getValue("type");
				dto.setValue("id", dto.getValue("styleid"));
				if (type.equalsIgnoreCase("R")) {
					dto.setValue("dataType", "resultstyle");
				} else if (type.equalsIgnoreCase("C")) {
					dto.setValue("dataType", "condistyle");
				} else {
					dto.setValue("dataType", "printstyle");
				}
				styles.add(dto);
			}

		}
		list = null;
		return result;
	}

	/*
	 * 根据目录id获取查询类列表
	 */
	public static List<XMLDto> getClassLstByCatalog(String catalogId, String classId) throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "customquery.simpleQuery");
		tab.put("querysql", "select * from customquery_class where catalogid='" + catalogId + "' and id <> '" + classId + "'");
		Hashtable re = ServiceInvokerUtil.invoker(tab);
		String xml = (String) re.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		List<Element> eles = DocumentHelper.parseText(xml).getRootElement().selectNodes("querydatas/querydata");
		List<XMLDto> list = CommonUtils.coverEles(eles, new String[] { "id", "alias", "name" }, null, new String[] { "name" }, null, null, null);
		return list;
	}

	/*
	 * 调整查询类顺序
	 */
	public static void sortClass(String aimClassId, String curClassId, String curCatalogId, String moveFlag) throws ServiceInvokerException {
		Hashtable tab = new Hashtable();
		tab.put("service", "customquery.modifyclasssort");
		tab.put("aimClassId", aimClassId);
		tab.put("curClassId", curClassId);
		tab.put("curCatalogId", curCatalogId);
		tab.put("moveFlag", moveFlag);
		ServiceInvokerUtil.invoker(tab);
	}

	public static String updateClass(String classId, String xml) throws ServiceInvokerException {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.class.new");
		tab.put("classID", classId);
		tab.put("XML", xml);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		return re.get("classID");
	}

	public static String copyClass(String classid) throws ServiceInvokerException {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.class.copy");
		tab.put("srcid", classid);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		return re.get("classid");
	}

	public static String getStrutByServiceName(String servicename) throws Exception {
		String tablexml = "";
		Hashtable<String, String> paramsIn = new Hashtable<String, String>();
		paramsIn.put("service", servicename + ".getaccountstruct");
		Hashtable<String, String> out = ServiceInvokerUtil.invoker(paramsIn);
		if (out.get("errorMessage") != null)
			throw new Exception(out.get("errorMessage").toString());
		else
			tablexml = out.get("tableXml");
		return tablexml;
	}

	public static void updateStyle(String id, String name, String width, String xml, String height) throws Exception {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("service", "customquery.style2.update");
		map.put("id", id);
		map.put("name", name);
		map.put("width", width);
		map.put("XML", xml);
		map.put("version", "2");
		map.put("height", height);
		Hashtable result = ServiceInvokerUtil.invoker(map);
		if (result.get("errorMessage") != null) {
			throw new Exception(result.get("errorMessage").toString());
		}
	}

	/**
	 * 获取目录和查询类
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getCatalogAndClass() throws Exception {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("service", "customquery.getcatalogandclass");
		map.put("version", "2");
		Hashtable result = ServiceInvokerUtil.invoker(map);
		if (result.get("errorMessage") != null) {
			throw new Exception(result.get("errorMessage").toString());
		} else {
			return (String) result.get("XML");
		}
	}

	public static String getStyledataXml(String styleid) throws Exception {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("service", "customquery.style2.get");
		map.put("id", styleid);
		Hashtable result = ServiceInvokerUtil.invoker(map);
		if (result.get("errorMessage") != null) {
			throw new Exception(result.get("errorMessage").toString());
		} else {
			return (String) result.get("XML");
		}

	}

	/**
	 * 根据样式id获取简单的样式信息（不含样式xml）
	 * 
	 * @param styleid
	 * @return
	 * @throws Exception
	 */
	public static XMLDto getStyleInfoById(String styleid) throws Exception {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("service", "customquery.style2.getlist");
		map.put("id", styleid);

		Hashtable<String, String> result = ServiceInvokerUtil.invoker(map);
		if (result.get("errorMessage") != null) {
			throw new Exception(result.get("errorMessage").toString());
		}
		String xml = result.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		Element styleEle = DocumentHelper.parseText(xml).getRootElement().element("style2");
		if (styleEle == null) {
			return null;
		}
		return CommonUtils.coverEle(styleEle, Arrays.asList("id", "classid", "name", "type", "version", "sortnumber"), //
				Arrays.asList("name"), null, null, null, null);
	}

	public static String getClassItemByClassId(String classid) throws Exception {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("service", "customquery.class.getclass");
		map.put("classID", classid);

		Hashtable result = ServiceInvokerUtil.invoker(map);
		if (result.get("errorMessage") != null) {
			throw new Exception(result.get("errorMessage").toString());
		} else {
			return (String) result.get("XML");
		}
	}

	public static void delStyle(String id) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.style2.delete");
		tab.put("id", id);
		ServiceInvokerUtil.invoker(tab);
	}

	/**
	 * 取指定类型的组件信息
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public static List<XMLDto> getModuleLs(String type, String subtype, String moduleCode) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "module.getModuleList");
		tab.put("type", type);
		tab.put("subtype", subtype);
		tab.put("moduleCode", moduleCode);
		tab.put("allflag", "1");
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("moduleData");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("module");
		List<XMLDto> list = CommonUtils.coverEles(eles, new String[] { "id", "code", "name", "type", "subtype", "inparam", "outparam" }, null, new String[] { "name" }, null, null, null);
		Collections.sort(list, new Comparator<XMLDto>() {

			@Override
			public int compare(XMLDto o1, XMLDto o2) {
				String c1 = o1.getValue("code");
				String c2 = o2.getValue("code");
				return c1.compareTo(c2);
			}
		});
		return list;
	}

	/**
	 * 取指定类型的组件信息
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public static XMLDto getModule(String id) throws Exception {

		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "module.getModuleInfo");
		tab.put("moduleID", id);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("moduleData");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("module");
		if (eles.isEmpty()) {
			return null;
		}
		Element ele = eles.get(0);
		return CommonUtils.coverEle(ele, Arrays.asList("id", "code", "name", "type", "subtype", "inparam", "outparam"), Arrays.asList("name"), null, null, null, null);
	}

	/**
	 * 取指定code的表单目录(L1)
	 * 
	 * @param id
	 * @param contain
	 * @return
	 * @throws ServiceInvokerException
	 */
	public static List<XMLDto> formCatalogGet(String id, String contain) throws Exception {
		String xml = "service:=form2.catalog.get\n" + "id:=" + id + "\n" + "contain:=" + contain;

		String serevrHttp = System.getProperties().getProperty("form2");
		// form服务地址为空的时候报错
		if (StringUtils.nullOrBlank(serevrHttp)) {
			GUIUtils.showMsg(MainFrame.getInstance(), "取指定code的表单目录失败,表单服务没有启用！！！");
		}
		String reVal = ServiceMessageUtil.serviceInvoke(xml, "form2", "XML");
		String data = getReturnVal(reVal, "取指定code的表单目录");
		Document doc = DocumentHelper.parseText(data);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("catalog");
		if (eles == null || eles.isEmpty())
			return null;
		return CommonUtils.coverEles(eles, new String[] { "id", "code", "name" }, null, new String[] { "code", "name" }, null, null, null);
	}

	/**
	 * 取得指定目录下的表单类和表单项目
	 * 
	 * @param catalog
	 *            目录信息
	 * @param classid
	 *            表单类id
	 * @param hasItem
	 *            是否包含表单类
	 */
	public static List<XMLDto> getFormClasses(String catalog, String classid, String hasItem, String hasChildren, String hasAllVersion) throws Exception {
		String xml = "service:=form2.class.get\n";
		xml += "Catalog:=" + catalog + "\n";
		xml += "ClassID:=" + classid + "\n";
		xml += "hasItem:=" + hasItem + "\n";
		xml += "hasChildren:=" + hasChildren + "\n";
		xml += "hasAllVersion:=" + hasAllVersion + "\n";

		String reVal = ServiceMessageUtil.serviceInvoke(xml, "form2", "XML");
		String xmlStr = getReturnVal(reVal, "取得指定目录下的表单类和表单项目失败");
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("Classes");
		if (eles == null || eles.isEmpty())
			return null;
		return CommonUtils.coverEles(eles, new String[] { "id", "catalogid", "name" }, null, new String[] { "id", "name" }, null, null, null);
	}

	/**
	 * 取表单类的所有表单样式列表
	 * 
	 * @param classId
	 *            表单类id
	 */
	public static List<XMLDto> getformClassStyle(String classId, String styleId) throws Exception {
		String xml = "service:=form2.style.getlist\n";
		xml += "ClassID:=" + classId + "\n";
		xml += "StyleID:=" + styleId + "\n";

		String xmlStr = ServiceMessageUtil.serviceInvoke(xml, "form2", "XML");
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("style");
		if (eles == null || eles.isEmpty())
			return null;
		if (eles == null || eles.isEmpty())
			return null;
		Map<String, String> switchProp = new HashMap<String, String>();
		switchProp.put("id", "styleid");
		return CommonUtils.coverEles(eles, new String[] { "id", "classid", "name", "sortcode" }, null, new String[] { "styleid", "name" }, switchProp, null, null);
	}

	public static List<XMLDto> getFlowCatalogs() {
		try {
			Hashtable<String, String> tab = new Hashtable<String, String>();
			tab.put("service", "flow.catalog.getlist");
			tab.put("code", "");
			tab.put("hasflowinfo", "1");
			Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
			String xml = re.get("catalogData");
			if (CommonUtils.isStrEmpty(xml)) {
				return null;
			}
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			if (!root.hasContent()) {
				return null;
			}
			List<Element> eles = root.elements();
			return CommonUtils.coverEles(eles, new String[] { "id", "code", "name", }, null, new String[] { "code", "name" }, null, null, null);
		} catch (Exception e) {
			GUIUtils.showMsg(null, "获取工作流目录失败");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static List<XMLDto> getFlowClasses(String catalogCode, String flowCode) {
		try {
			Hashtable<String, String> tab = new Hashtable<String, String>();
			tab.put("service", "flow.flowclass.getlist");
			tab.put("CatalogCode", catalogCode);
			tab.put("FlowCode", flowCode);
			tab.put("hasOldVersion", "0");
			Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
			String xml = re.get("flowListData");
			if (CommonUtils.isStrEmpty(xml)) {
				return null;
			}
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			if (!root.hasContent()) {
				return null;
			}
			List<Element> eles = root.elements();
			return CommonUtils.coverEles(eles, new String[] { "id", "code", "name", "catalogcode" }, null, new String[] { "code", "name" }, null, null, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 得到从服务器中返回的值
	 * 
	 * @param reData
	 *            服务器返回的数据
	 */
	private static String getReturnVal(String reData, String msg) throws ServiceInvokerException {
		if (!StringUtils.nullOrBlank(reData)) {
			if (reData.indexOf("服务已停止") > 0) {
				throw new RuntimeException(msg + reData);
			}
			return reData;
		}
		throw new RuntimeException("服务器返回信息有误");
	}

	// 通用业务
	public static List<XMLDto> getWorkPubFormeleAll() throws Exception {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("service", "workpubservice.formele.read");
		map.put("moduleid", "");
		map.put("code", "");
		map.put("onlyfuncinfo", "true");
		Hashtable<String, String> result = ServiceInvokerUtil.invoker(map);
		String xml = result.get("xml");
		if (CommonUtils.isStrEmpty(xml))
			return null;
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("funcdata");
		if (eles == null || eles.isEmpty()) {
			return null;
		}
		return CommonUtils.coverEles(eles, new String[] { "id", "code", "name", "type" }, null, new String[] { "name" }, null, null, null);
	}

	// 通用业务表
	public static List<Element> getWorkPubFormeleTables(String moduleid) throws Exception {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("service", "workpubservice.formele.read");
		map.put("moduleid", moduleid);
		map.put("code", "");
		map.put("onlyfuncinfo", "false");
		Hashtable<String, String> result = ServiceInvokerUtil.invoker(map);
		String xml = result.get("xml");
		if (CommonUtils.isStrEmpty(xml))
			return null;
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		Element funcdata = root.element("funcdata");
		if (funcdata == null) {
			return null;
		}
		xml = funcdata.elementText("eledata");
		if (CommonUtils.isStrEmpty(xml))
			return null;
		doc = DocumentHelper.parseText(xml);
		root = doc.getRootElement();
		return root.elements();
	}

	/**
	 * 取指定类型的组件模板信息
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public static List<XMLDto> getComModels(String iscatalog) throws Exception {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.com.getlist");
		tab.put("condi", "");
		tab.put("iscatalog", iscatalog);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String xml = re.get("XML");
		if (CommonUtils.isStrEmpty(xml)) {
			return null;
		}
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<Element> eles = root.elements("resultdata");
		if (eles == null || eles.isEmpty()) {
			return null;
		}
		if ("1".equals(iscatalog)) {
			return CommonUtils.coverEles(eles, new String[] { "com_name", "com_code", "com_id" }, null, new String[] { "com_name" }, null, null, null);
		} else {
			return CommonUtils.coverEles(eles, new String[] { "com_name", "com_code", "com_id", "inparam", "outparam", "com_info", "userset_name" }, null, new String[] { "com_name", "userset_name" }, null, null, null);
		}

	}

	/**
	 * 升级系统（把旧数据移殖过来）
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public static void upgrade() throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "report2.upgrade");
		Hashtable re = ServiceInvokerUtil.invoker(tab);
		String success = (String) re.get("success");
		if ("true".equals(success)) {
			GUIUtils.showMsg(MainFrame.getInstance(), "导入成功");
		} else {
			GUIUtils.showMsg(MainFrame.getInstance(), "导入失败,请确认是否已经升级，若还有问题请联系研发部");
		}
	}

	/**
	 * 获取服务名
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<XMLDto> getServices() throws Exception {
		Hashtable tab = new Hashtable();
		tab.put("service", "module.service.getservice");
		tab.put("type", "Q");
		Hashtable re = ServiceInvokerUtil.invoker(tab);
		String xml = (String) re.get("xml");
		List<XMLDto> list = new ArrayList<XMLDto>();
		if (CommonUtils.isStrEmpty(xml)) {
			return list;
		}
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		if (!root.hasContent()) {
			return list;
		}
		return CommonUtils.coverEles(root.elements(), new String[] { "id", "servicecname", "servicename", "description", "code" }, null, new String[] { "code", "servicecname" }, null, null, null);
	}

	public static void getAndSetService(String serviceUrl) throws Exception {

		String msgVar = "service := system.poperties.serviceslocation\nxml := ";
		try {
			String reMsg = sendData(msgVar, serviceUrl);// 调用服务并返回值

			if (CommonUtils.isStrEmpty(reMsg)) {
				throw new Exception("取所有服务的地址映射失败!");
			} else {

				reMsg = reMsg.substring(reMsg.indexOf(":=") + 2).trim();

				if (CommonUtils.isStrEmpty(reMsg)) {
					throw new Exception("取所有服务的地址映射失败!");
				}

			}
			// 解析xml数据
			Document doc = DocumentHelper.parseText(reMsg);
			Element root = doc.getRootElement();
			List eLs = root.elements("service");
			// 循环所有服务,并把服务名和地址写入内存
			for (Iterator iter = eLs.iterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				String key = element.attributeValue("name");
				String value = serviceUrl;
				System.getProperties().put(key, value);
			}
		} catch (Exception e) {
			throw new Exception("取所有服务的地址映射失败:" + e.getMessage());
		}

	}

	/**
	 * 发送信息到服务器
	 * 
	 * @param msgVar
	 *            消息字符串
	 * @param serviceUrl
	 *            服务地址
	 * @return 返回消息结果
	 */
	public static String sendData(String msgVar, String serviceUrl) throws Exception {
		String output = null;
		URL url = new URL(serviceUrl);
		URLConnection client = url.openConnection();
		client.setDoOutput(true);
		client.getOutputStream().write(msgVar.getBytes());
		client.getOutputStream().flush();
		client.getOutputStream().close();
		// client.connect();
		int dataLen = 0;
		dataLen = client.getContentLength();
		if (dataLen > 0) {
			byte[] data = new byte[dataLen];
			int p = 0;
			int r = -1;
			while (p < dataLen) {
				r = client.getInputStream().read(data, p, dataLen - p);
				if (r < 0) {
					break;
				}
				p = p + r;
			}
			output = new String(data);
		}
		return output;
	}

	public static String updateComPropinCatalog(String catalogid, String setPropName, String regex, String replaceTxt, String matchTxt) throws ServiceInvokerException {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.style2.updateComPropinCatalog");
		tab.put("catalogid", catalogid);
		tab.put("matchTxt", matchTxt);
		tab.put("setPropName", setPropName);
		tab.put("regex", regex);
		tab.put("replaceTxt", replaceTxt);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String errorMessage = re.get("errorMessage");
		if (!CommonUtils.isStrEmpty(errorMessage)) {
			throw new RuntimeException(errorMessage);
		}
		return re.get("result");
	}

	public static String updateEvent4CustomQuery(String catalogid, String classid, String autodescinfo, String autotitle, String condi) throws ServiceInvokerException {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.style2.updateEvent4CustomQuery");
		tab.put("catalogid", catalogid);
		tab.put("classid", classid);
		tab.put("autodescinfo", autodescinfo);
		tab.put("autotitle", autotitle);
		tab.put("condi", condi);
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String errorMessage = re.get("errorMessage");
		if (!CommonUtils.isStrEmpty(errorMessage)) {
			throw new RuntimeException(errorMessage);
		}
		return re.get("result");
	}

	public static String createPrintStyles(final List<String> styleids, final List<String> names) throws ServiceInvokerException {
		Hashtable<String, String> tab = new Hashtable<String, String>();
		tab.put("service", "customquery.style2.createPrintStyles");
		tab.put("styleids", styleids.toString().replace("[", "").replace("]", "").replaceAll(" ", ""));
		StringBuilder sb = new StringBuilder();
		for (String name : names) {
			sb.append(name).append(",");
		}
		sb.substring(0, sb.length() - 1);
		tab.put("names", names.toString().replace("[", "").replace("]", ""));
		Hashtable<String, String> re = ServiceInvokerUtil.invoker(tab);
		String errorMessage = re.get("errorMessage");
		if (!CommonUtils.isStrEmpty(errorMessage)) {
			throw new RuntimeException(errorMessage);
		}
		return re.get("result");
	}

}
