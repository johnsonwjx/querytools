package youngfriend.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import youngfriend.beans.PropDto;
import youngfriend.coms.IStyleCom;

public class PropUtils {
	private static final Logger logger = LogManager.getLogger(PropUtils.class);

	public static List<Element> getProEle(String filepath) {
		SAXReader reader = new SAXReader();
		Document document;
		try {
			logger.debug(filepath);
			document = reader.read(new File(filepath));
			Element root = document.getRootElement();
			return root.elements();
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public static Map<String, PropDto> initPropDto(List<Element> proEles, JComponent com, Boolean include, String... proNames) {
		try {
			List<String> pNameLst = new ArrayList<String>();
			if (include != null && proNames.length >= 0) {
				pNameLst = Arrays.asList(proNames);
			}
			Map<String, PropDto> dtos = new LinkedHashMap<String, PropDto>();
			for (Element e : proEles) {
				List<Element> ps = e.elements();
				PropDto dto = new PropDto();
				if (com != null && (com instanceof IStyleCom)) {
					dto.setCom((IStyleCom) com);
				}
				for (Element p : ps) {
					String key = p.getName();
					if ("enumerate".equals(key)) {
						dto.setEnum(p.element("name").getText(), p.element("code").getText());
						continue;
					}
					Object value = p.getText();
					BeanUtils.copyProperty(dto, key, value);

				}
				if (include != null) {
					boolean has = pNameLst.contains(dto.getPropname().toLowerCase());
					if (include) {
						if (has) {
							dtos.put(dto.getPropname().toLowerCase(), dto);
						}
					} else {
						if (!has) {
							dtos.put(dto.getPropname().toLowerCase(), dto);
						}
					}
				} else {
					dtos.put(dto.getPropname().toLowerCase(), dto);
				}

			}
			return dtos;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static String null2Empty(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}
}
